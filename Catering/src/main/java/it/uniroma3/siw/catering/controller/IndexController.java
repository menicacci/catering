package it.uniroma3.siw.catering.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import it.uniroma3.siw.catering.model.Credentials;
import it.uniroma3.siw.catering.service.CredentialsService;

@Controller
public class IndexController {
	
	@Autowired private CredentialsService credentialsService;
	
//	@Autowired private AdminValidator adminValidator;
//	@Autowired private CredentialsValidator credentialsValidator;

	@RequestMapping(value = {"/", "index"}, method = RequestMethod.GET)
	public String index(Model model) {
		return "index";
	}

	@GetMapping("/administration")
	public String getAdminPage(Model model) {
		UserDetails userDetails = (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Credentials credentials = credentialsService.getCredentials(userDetails.getUsername());
		if (credentials.getRole().equals(Credentials.DEFAULT_ROLE)) {
			model.addAttribute("admin", credentials.getAdmin());
			return "administration.html";
		}

		return "index";
	}

	@GetMapping("/login")
	public String getAdminLogin(Model model) {
		return "adminLogin";
	}
	
	/* Not needed
	@GetMapping("/register")
	public String getAdminRegister(Model model) {
		model.addAttribute("admin", new Admin());
		model.addAttribute("credentials", new Credentials());
		
		return "adminRegister";
	}
	*/

	@RequestMapping(value = {"/logout"}, method = RequestMethod.GET) 
	public String logout(HttpServletRequest request, HttpServletResponse response) {
		SecurityContextHolder.clearContext();
		HttpSession session= request.getSession(false);
		if(session != null)
			session.invalidate();

		return "index";
	}

	@PostMapping("/login")
	public String loginAdmin(Model model) {
		return "redirect:/administration";
	}
	
	/* Not needed
	@RequestMapping(value = { "/register" }, method = RequestMethod.POST)
    public String registerUser(@ModelAttribute("admin") Admin admin,
                 BindingResult bindingResult,
                 @ModelAttribute("credentials") Credentials credentials,
                 BindingResult credentialsBindingResult,
                 Model model) {

        // validate user and credentials fields
        this.adminValidator.validate(admin, bindingResult);
        this.credentialsValidator.validate(credentials, credentialsBindingResult);

        // if neither of them had invalid contents, store the User and the Credentials into the DB
        if(!bindingResult.hasErrors() && ! credentialsBindingResult.hasErrors()) {
            // set the user and store the credentials;
            // this also stores the User, thanks to Cascade.ALL policy
        	credentials.setRole("ADMIN");
            credentials.setAdmin(admin);
            credentialsService.saveCredentials(credentials);
            return "index";
        }
        return "adminRegister";
    }
    */
}
