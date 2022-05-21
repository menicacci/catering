package it.uniroma3.siw.catering.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import it.uniroma3.siw.catering.model.Buffet;
import it.uniroma3.siw.catering.model.Chef;
import it.uniroma3.siw.catering.service.BuffetService;
import it.uniroma3.siw.catering.service.ChefService;
import it.uniroma3.siw.catering.validator.BuffetValidator;

@Controller
public class BuffetController {

	@Autowired private BuffetService buffetService;
	@Autowired private ChefService chefService;
	@Autowired private BuffetValidator buffetValidator;

	@GetMapping("/administration/buffets")
	public String listBuffets(Model model) {
		model.addAttribute("buffets", buffetService.findAll());

		return "admin/buffet/buffets.html";
	}
	
	@GetMapping("/administration/buffets/chef/{chef_id}")
	public String listBuffetsChef(@PathVariable Long chef_id, Model model) {
		Chef chef = chefService.findById(chef_id);
		model.addAttribute("buffets", buffetService.findAllByChef(chef));
		model.addAttribute("chef", chef);
		
		return "admin/buffet/buffets_per_chef.html";
	}
	
	@GetMapping("/administration/buffets/new/{chef_id}")
	public String createBuffetForm(@PathVariable Long chef_id, Model model) {
		Buffet buffet = new Buffet();
		model.addAttribute("buffet", buffet);
		model.addAttribute("chef_id", chef_id);
		
		return "admin/buffet/create_buffet.html";
	}
	
	@PostMapping("/administration/buffets/{chef_id}")
	public String addBuffet(@PathVariable Long chef_id, @Valid @ModelAttribute("buffet") Buffet buffet, BindingResult bindingResults, Model model) {
		this.buffetValidator.validate(buffet, bindingResults);
		
		if(!bindingResults.hasErrors()) {
			Chef chef = chefService.findById(chef_id);
			chef.addBuffet(buffet);
			buffet.setChef(chef);
			buffetService.save(buffet);
			model.addAttribute("buffet", model);
			return "redirect:/administration/chefs";
		}
		else
			return "admin/buffet/create_buffet.html";
	}
	
	@GetMapping("/administration/buffets/del/{id}")
	public String deleteBuffet(@PathVariable Long id) {
		buffetService.deleteBuffet(id);
		return "redirect:/administration/buffets";
	}
}
