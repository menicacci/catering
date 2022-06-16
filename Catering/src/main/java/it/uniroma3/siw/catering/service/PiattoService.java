package it.uniroma3.siw.catering.service;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.uniroma3.siw.catering.model.Buffet;
import it.uniroma3.siw.catering.model.Ingrediente;
import it.uniroma3.siw.catering.model.Piatto;
import it.uniroma3.siw.catering.repository.PiattoRepository;

@Service
public class PiattoService {
	
	@Autowired private PiattoRepository piattoRepository;
	
	@Transactional
	public void save(Piatto piatto) {
		piattoRepository.save(piatto);
	}
	
	public Piatto findById(Long id) {
		return piattoRepository.findById(id).get();
	}
	
	public List<Piatto> findAllByBuffet(Buffet buffet) {
		List<Piatto> piatti = new ArrayList<>();
		for(Piatto p : piattoRepository.findByBuffet(buffet)) {
			piatti.add(p);
		}
		return piatti;
	}

	
	public List<Piatto> findAll(){
		List<Piatto> piatti = new ArrayList<>();
		for(Piatto p : piattoRepository.findAll()) {
			piatti.add(p);
		}
		return piatti;
	}
	
	public void deletePiatto(Long id) {
		piattoRepository.deleteById(id);
	}
	
	public void deletePiatti(Ingrediente i) {
		List<Piatto> piatti = piattoRepository.findByIngredienti(i);
		for(Piatto p: piatti)
			this.deletePiatto(p.getId());
	}

	public boolean alredyExists(Piatto piatto) {
		return this.piattoRepository.existsByNomeAndBuffet(piatto.getNome(), piatto.getBuffet());
	}

	public List<Piatto> findAllByIngrediente(Ingrediente ingrediente) {
		return this.piattoRepository.findByIngredienti(ingrediente);
	}

	public void removeIngredienteFromPiatto(List<Piatto> piattiConIngrediente, Ingrediente ingrediente) {
		for(Piatto p: piattiConIngrediente) {
			p.removeIngrediente(ingrediente);
		}
	}

	public void addIngrediente(Long id_p, Ingrediente ingrediente) {
		Piatto piatto = this.findById(id_p);
		
		piatto.addIngrediente(ingrediente);
		this.save(piatto);
	}

	public void removeIngredienteFromPiatto(Long id_p, Ingrediente ingrediente) {
		Piatto piatto = this.findById(id_p);
		piatto.removeIngrediente(ingrediente);
		this.save(piatto);
	}

	

}
