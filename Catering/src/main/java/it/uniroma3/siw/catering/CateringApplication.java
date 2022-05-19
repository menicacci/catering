package it.uniroma3.siw.catering;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import it.uniroma3.siw.catering.model.Buffet;
import it.uniroma3.siw.catering.model.Chef;
import it.uniroma3.siw.catering.model.Ingrediente;
import it.uniroma3.siw.catering.model.Piatto;
import it.uniroma3.siw.catering.repository.BuffetRepository;
import it.uniroma3.siw.catering.repository.ChefRepository;
import it.uniroma3.siw.catering.repository.IngredienteRepository;
import it.uniroma3.siw.catering.repository.PiattoRepository;

@SpringBootApplication
public class CateringApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(CateringApplication.class, args);
	}

	@Autowired private ChefRepository chefR;
	@Autowired private IngredienteRepository ingrR;
	@Autowired private BuffetRepository buffR;
	@Autowired private PiattoRepository piattR;
	
	@Override
	public void run(String... args) throws Exception {
		Chef c1 = new Chef();
		c1.setNome("Marcos");
		c1.setCognome("Amicos");
		c1.setNazionalita("Spagna");
		
		Chef c2 = new Chef();
		c2.setNome("Andrea");
		c2.setCognome("Valenti");
		c2.setNazionalita("Italia");
		
		chefR.save(c1);
		chefR.save(c2);
		
		Buffet b1 = new Buffet();
		b1.setNome("Comunione Festiva");
		b1.setDescrizione("Per mesi caldi");
		b1.setChef(c1);
		
		Buffet b2 = new Buffet();
		b2.setNome("Comunione Triste");
		b2.setDescrizione("Per mesi freddi");
		b2.setChef(c1);
		
		Buffet b3 = new Buffet();
		b3.setNome("Matrimonio");
		b3.setDescrizione("Estivo");
		b3.setChef(c2);
		
		buffR.save(b1);
		buffR.save(b2);
		buffR.save(b3);
		
		Ingrediente i1 = new Ingrediente();
		i1.setNome("Pasta");
		i1.setOrigine("Lazio");
		i1.setDescrizione("Paese coltivazione grano: ITALIA");
		
		ingrR.save(i1);
		
		
		Piatto p1 = new Piatto();
		p1.setNome("Carbonara");
		p1.setDescrizione("Buona");
		p1.setBuffet(b1);
		p1.addIngrediente(i1);
		piattR.save(p1);
	}

}
