package it.uniroma3.siw.catering.repository;

import org.springframework.data.repository.CrudRepository;

import it.uniroma3.siw.catering.model.Admin;

public interface AdminRepository extends CrudRepository<Admin, Long> {

}
