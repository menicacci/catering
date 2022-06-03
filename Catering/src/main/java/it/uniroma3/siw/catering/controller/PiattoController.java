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
import it.uniroma3.siw.catering.model.Piatto;
import it.uniroma3.siw.catering.service.BuffetService;
import it.uniroma3.siw.catering.service.IngredienteService;
import it.uniroma3.siw.catering.service.PiattoService;
import it.uniroma3.siw.catering.validator.PiattoValidator;

@Controller
public class PiattoController {

	@Autowired private PiattoService piattoService;
	@Autowired private BuffetService buffetService;
	@Autowired private IngredienteService ingredienteService;
	
	@Autowired private PiattoValidator piattoValidator;
	
	@GetMapping("/administration/piatti")
	public String listPiatti(Model model) {
		model.addAttribute("piatti", piattoService.findAll());

		return "admin/piatto/piatti.html";
	}
	
	@GetMapping("/administration/piatti/buffet/{buffet_id}")
	public String listPiattiBuffet(@PathVariable Long buffet_id, Model model) {
		Buffet buffet = buffetService.findById(buffet_id);
		model.addAttribute("piatti", piattoService.findAllByBuffet(buffet));
		model.addAttribute("buffet", buffet);
		
		return "admin/piatto/piatti_per_buffet.html";
	}
	
	@GetMapping("/administration/piatti/new/{buffet_id}")
	public String createPiattoForm(@PathVariable Long buffet_id, Model model) {
		Piatto piatto = new Piatto();
		model.addAttribute("piatto", piatto);
		model.addAttribute("buffet_id", buffet_id);
		model.addAttribute("tuttiGliIngredienti", ingredienteService.findAll());
		
		return "/admin/piatto/create_piatto.html";
	}

	@PostMapping("/administration/piatti/{buffet_id}")
	public String addPiatto(@PathVariable Long buffet_id, @Valid @ModelAttribute("piatto") Piatto piatto, BindingResult bindingResults, Model model) {
		Buffet buffet = buffetService.findById(buffet_id);
		buffet.addPiatto(piatto);
		piatto.setBuffet(buffet);
		this.piattoValidator.validate(piatto, bindingResults);
		if(!bindingResults.hasErrors()) {
			piattoService.save(piatto);
			model.addAttribute("piatto", model);
			return "redirect:/administration/buffets";
		}
		else {
			model.addAttribute("tuttiGliIngredienti", ingredienteService.findAll());
			return "admin/piatto/create_piatto.html";
		}
	}
	
	@GetMapping("/administration/piatti/del/{id}")
	public String deletePiatto(@PathVariable Long id) {
		piattoService.deletePiatto(id);
		return "redirect:/administration/piatti";
	}
	
	@GetMapping("/show_piatti/{id}")
	public String showPiatti(@PathVariable Long id, Model model) {
		model.addAttribute("buffet", buffetService.findById(id));
		
		return "show_piatti.html";
	}
}
