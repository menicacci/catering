package it.uniroma3.siw.catering.service;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.uniroma3.siw.catering.model.Ingrediente;
import it.uniroma3.siw.catering.model.Piatto;
import it.uniroma3.siw.catering.repository.IngredienteRepository;

@Service
public class IngredienteService {
	
	@Autowired private IngredienteRepository ingredienteRepository;
	@Autowired private PiattoService piattoService;
	
	@Transactional
	public void save(Ingrediente ingrediente) {
		ingredienteRepository.save(ingrediente);
	}
	
	public Ingrediente findById(Long id) {
		return this.ingredienteRepository.findById(id).get();
	}
	
	public List<Ingrediente> findAll(){
		List<Ingrediente> ingredienti = new ArrayList<>();
		for(Ingrediente i: ingredienteRepository.findAll()) {
			ingredienti.add(i);
		}
		return ingredienti;
	}
	
	public void deleteIngrediente(Long id) {
		Ingrediente ingrediente = this.findById(id);
		List<Piatto> piattiConIngrediente = piattoService.findAllByIngrediente(ingrediente);
		piattoService.removeIngredienteFromPiatto(piattiConIngrediente,ingrediente);
		
		ingredienteRepository.deleteById(id);
	}

	public boolean alreadyExists(Ingrediente ingrediente) {
		return ingredienteRepository.existsByNomeAndOrigineAndDescrizione(ingrediente.getNome(), 
				ingrediente.getOrigine(), ingrediente.getDescrizione());

	}

	public List<Ingrediente> getIngredientiNonInPiatto(Piatto piatto) {
		List<Ingrediente> ingredienti = this.findAll();
		ingredienti.removeAll(piatto.getIngredienti());
		
		return ingredienti;
	}

	public void addToPiatto(Long id_p, Long id_i) {
		this.piattoService.addIngrediente(id_p, this.findById(id_i));		
	}

	public void removeFromPiatto(Long id_p, Long id_i) {
		piattoService.removeIngredienteFromPiatto(id_p,this.findById(id_i));
	}
}
