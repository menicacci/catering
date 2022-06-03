package it.uniroma3.siw.catering;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;

import it.uniroma3.siw.catering.model.Admin;
import it.uniroma3.siw.catering.model.Buffet;
import it.uniroma3.siw.catering.model.Chef;
import it.uniroma3.siw.catering.model.Credentials;
import it.uniroma3.siw.catering.model.Ingrediente;
import it.uniroma3.siw.catering.model.Piatto;
import it.uniroma3.siw.catering.repository.BuffetRepository;
import it.uniroma3.siw.catering.repository.ChefRepository;
import it.uniroma3.siw.catering.repository.CredentialsRepository;
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
	@Autowired private CredentialsRepository credR;
	
	@Autowired protected PasswordEncoder passwordEncoder;
	
	@Override
	public void run(String... args) throws Exception {
		Chef c1 = new Chef();
		c1.setNome("Marcos");
		c1.setCognome("Amicos");
		c1.setNazionalita("Spagna");
		
		Chef c2 = new Chef();
		c2.setNome("Andrea");
		c2.setCognome("Amico");
		c2.setNazionalita("Italia");
		
		chefR.save(c1);
		chefR.save(c2);
		
		Buffet b1 = new Buffet();
		b1.setNome("Comunione speciale");
		b1.setDescrizione("per mesi caldi");
		b1.setChef(c1);
		
		Buffet b2 = new Buffet();
		b2.setNome("Matrimonio invernale");
		b2.setDescrizione("per mesi freddi");
		b2.setChef(c1);
		
		Buffet b3 = new Buffet();
		b3.setNome("Matrimonio sotto il sole");
		b3.setDescrizione("estivo, adatto anche ai mesi primaverili");
		b3.setChef(c2);
		
		buffR.save(b1);
		buffR.save(b2);
		buffR.save(b3);
		
		Ingrediente i1 = new Ingrediente();
		i1.setNome("Pasta - Spaghetti");
		i1.setOrigine("Lazio");
		i1.setDescrizione("Paese coltivazione grano: ITALIA");
		
		Ingrediente i2 = new Ingrediente();
		i2.setNome("Pasta - Mezze Maniche");
		i2.setOrigine("Lazio");
		i2.setDescrizione("Paese coltivazione grano: ITALIA");
		
		Ingrediente i3 = new Ingrediente();
		i3.setNome("Pasta - Rigatoni");
		i3.setOrigine("Lazio");
		i3.setDescrizione("Paese coltivazione grano: ITALIA");
		
		Ingrediente i4 = new Ingrediente();
		i4.setNome("Sugo");
		i4.setOrigine("Marche");
		i4.setDescrizione("Pomodori italiani");
		
		Ingrediente i5 = new Ingrediente();
		i5.setNome("Pesto");
		i5.setOrigine("Lompardia");
		i5.setDescrizione("Pinoli italiani");
		
		Ingrediente i6 = new Ingrediente();
		i6.setNome("Olio");
		i6.setOrigine("Sicilia");
		i6.setDescrizione("Extravergine d'oliva, DOP");
		
		Ingrediente i7 = new Ingrediente();
		i7.setNome("Pancetta");
		i7.setOrigine("Sardegna");
		i7.setDescrizione("Allevamento sostenibile");
		
		Ingrediente i8 = new Ingrediente();
		i8.setNome("Parmigiano Reggiano");
		i8.setOrigine("Emilia-Romagna");
		i8.setDescrizione("Formaggio stagionato 12 mesi");
		
		ingrR.save(i1);
		ingrR.save(i2);
		ingrR.save(i3);
		ingrR.save(i4);
		ingrR.save(i5);
		ingrR.save(i6);
		ingrR.save(i7);
		ingrR.save(i8);
		
		Piatto p1 = new Piatto();
		p1.setNome("Pasta in bianco");
		p1.setDescrizione("Delicata e adatta a tutti");
		p1.setBuffet(b1);
		p1.addIngrediente(i6);
		p1.addIngrediente(i1);
		p1.addIngrediente(i8);
		
		Piatto p2 = new Piatto();
		p2.setNome("Pasta al sugo");
		p2.setDescrizione("Buona e saporita");
		p2.setBuffet(b1);
		p2.addIngrediente(i2);
		p2.addIngrediente(i4);
		p2.addIngrediente(i8);
		
		Piatto p3 = new Piatto();
		p3.setNome("Pasta al pesto");
		p3.setDescrizione("Adatta ai più piccoli");
		p3.setBuffet(b2);
		p3.addIngrediente(i3);
		p3.addIngrediente(i5);
		p3.addIngrediente(i8);
		
		Piatto p4 = new Piatto();
		p4.setNome("Pasta con la pancetta sarda");
		p4.setDescrizione("Saporita e originale");
		p4.setBuffet(b3);
		p4.addIngrediente(i2);
		p4.addIngrediente(i5);
		p4.addIngrediente(i6);
		p4.addIngrediente(i8);
		
		piattR.save(p1);
		piattR.save(p2);
		piattR.save(p3);
		piattR.save(p4);
		
		Admin u = new Admin();
		u.setNome("Orso");
		u.setCognome("Mengo");
		
		Credentials c = new Credentials();
		c.setAdmin(u);
		c.setPassword(this.passwordEncoder.encode("orso"));
		c.setUsername("orso");
		c.setRole("ADMIN");
		
		credR.save(c);	
	}

}
