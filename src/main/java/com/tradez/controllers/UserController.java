package com.tradez.controllers;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.DataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.tradez.models.User;
import com.tradez.services.ListingService;
import com.tradez.services.UserService;
import com.tradez.validation.UserValidator;

@Controller
public class UserController {

	@Autowired
	private UserService service;
	@Autowired
	private ListingService listingService;

	@InitBinder
	public void initBinder(DataBinder binder) {
		binder.addValidators(new UserValidator());
	}

	@GetMapping("/signup")
	public String signUpPage(Model model) {
		User user = new User();
		model.addAttribute("user", user);
		model.addAttribute("authUser", this.service.getAuthUser());
		return "signup";
	}

	@PostMapping("/signup")
	public String signUp(@Valid User user, BindingResult bindingResult, Model model) {

		String fieldName = service.validateUsernameAndEmail(user);
		if(fieldName != null) {
			bindingResult.rejectValue(fieldName, null, "The " + fieldName + " has already been registered, you must choose a different one");
		}
		
		if (bindingResult.hasErrors()) {
			return "signup";
		}
		service.save(user);
		return "signup_confirmation";
	}

	@RequestMapping("/login")
	public String Login(Model model) {
		return "login";
	}

	@GetMapping("/profile/{username}")
	public String getUserProfile(@PathVariable String username, Model model) {
		model.addAttribute("user", this.service.getByUsername(username));
//		model.addAttribute("listings", this.listingService.findByUsername(username));
		model.addAttribute("listings", this.listingService.findByCreatedByAndStatus(username, "available", 12));
		model.addAttribute("authUser", this.service.getAuthUser());
		return "profile";
	}

	@GetMapping("/dashboard/profile/edit")
	public String editProfile(Model model) {
		model.addAttribute("authUser", this.service.getAuthUser());
		model.addAttribute("readonly", true);
		return "profile_edit";
	}

	@PostMapping("/dashboard/profile/edit")
	public String updateProfile(@Valid @ModelAttribute(name = "authUser") User authUser, BindingResult bindingResult, Model model) {
		if (bindingResult.hasErrors()) {
			printValidationErrors(bindingResult);
			return "profile_edit";
		}
		authUser.setId(this.service.getAuthUser().getId());
		service.save(authUser);
		return "redirect:/dashboard";
	}

	@RequestMapping("/dashboard")
	public String dashboardPage(HttpServletRequest request, Model model) {
		model.addAttribute("authUser", this.service.getAuthUser());
		model.addAttribute("listings", this.listingService.findByUsername());
		return "dashboard";
	}
	
	//helper function to print validation errors in console
		public void printValidationErrors(BindingResult bindingResult) {
			System.out.println("validation errors:");
			bindingResult.getFieldErrors().stream()
					.forEach(f -> System.out.println(f.getField() + ": " + f.getDefaultMessage()));
		}

}
