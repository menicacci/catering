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

import it.uniroma3.siw.catering.model.Ingrediente;
import it.uniroma3.siw.catering.model.Piatto;
import it.uniroma3.siw.catering.service.IngredienteService;
import it.uniroma3.siw.catering.service.PiattoService;
import it.uniroma3.siw.catering.validator.IngredienteValidator;

@Controller
public class IngredienteController {
	
	@Autowired private IngredienteService ingredienteService;
	@Autowired private PiattoService piattoService;
	
	@Autowired private IngredienteValidator ingredienteValidator;
	
	@GetMapping("/administration/ingredienti")
	public String listBuffets(Model model) {
		model.addAttribute("ingredienti", ingredienteService.findAll());

		return "admin/ingrediente/ingredienti.html";
	}
	
	@GetMapping("/administration/ingredienti/piatto/{piatto_id}")
	public String listIngredientiPiatto(@PathVariable Long piatto_id, Model model) {
		Piatto piatto = piattoService.findById(piatto_id);
		model.addAttribute("piatto", piatto);
		model.addAttribute("ingredienti", piatto.getIngredienti());
		
		return "admin/ingrediente/ingredienti_per_piatto.html";
	}
	
	@GetMapping("/administration/ingredienti/new")
	public String createIngredienteForm(Model model) {
		Ingrediente ingrediente = new Ingrediente();
		model.addAttribute("ingrediente", ingrediente);
		return "admin/ingrediente/create_ingrediente.html";
	}

	@PostMapping("/administration/ingredienti")
	public String addIngrediente(@Valid @ModelAttribute("ingrediente") Ingrediente ingrediente, BindingResult bindingResults, Model model) {
		this.ingredienteValidator.validate(ingrediente, bindingResults);
		if(!bindingResults.hasErrors()) {
			ingredienteService.save(ingrediente);
			model.addAttribute("ingrediente", model);
			return "redirect:/administration/ingredienti";
		}
		else
			return "admin/ingrediente/create_ingrediente.html";
	}
	
	@GetMapping("/administration/ingredienti/del/{id}")
	public String deleteIngrediente(@PathVariable Long id) {
		ingredienteService.deleteIngrediente(id);
		return "redirect:/administration/ingredienti";
	}
	
	@GetMapping("/administration/ingredienti/add_ingr")
	public String addIngredienteToPiatto(Model model) {
		return null;
	}
}