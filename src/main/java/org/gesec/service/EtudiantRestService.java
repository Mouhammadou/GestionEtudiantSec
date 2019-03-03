package org.gesec.service;

import org.gesec.dao.EtudiantRepository;
import org.gesec.entities.Etudiant;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EtudiantRestService {

    private EtudiantRepository etudiantRepository;

    @RequestMapping(value = "/saveEtudiant", method = RequestMethod.GET)
    public Etudiant saveEtudiant(Etudiant etudiant){
        return etudiantRepository.save(etudiant);
    }

    @RequestMapping(value = "/etudiants")
    public Page<Etudiant> listesEtudiants(int page, int size){
        return etudiantRepository.findAll(new PageRequest(page, size));
    }
}
