package it.uniroma3.siw.catering.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.uniroma3.siw.catering.model.Admin;
import it.uniroma3.siw.catering.repository.AdminRepository;

/**
 * The UserService handles logic for Users.
 */
@Service
public class AdminService {

    @Autowired
    protected AdminRepository adminRepository;

    /**
     * This method retrieves a User from the DB based on its ID.
     * @param id the id of the User to retrieve from the DB
     * @return the retrieved User, or null if no User with the passed ID could be found in the DB
     */
    @Transactional
    public Admin getAdmin(Long id) {
        Optional<Admin> result = this.adminRepository.findById(id);
        return result.orElse(null);
    }

        @Transactional
    public Admin saveAdmin(Admin admin) {
        return this.adminRepository.save(admin);
    }
    
    @Transactional
    public List<Admin> getAllAdmins() {
        List<Admin> result = new ArrayList<>();
        Iterable<Admin> iterable = this.adminRepository.findAll();
        for(Admin admin : iterable)
            result.add(admin);
        return result;
    }
}