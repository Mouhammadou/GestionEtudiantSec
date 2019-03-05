package org.gesec.web;

import org.gesec.dao.EtudiantRepository;
import org.gesec.entities.Etudiant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.*;

@Controller
public class EtudiantController {

    @Autowired
    private EtudiantRepository etudiantRepository;

    @Secured(value = {"ROLE_ADMIN","ROLE_SCOLARITE"})
    @RequestMapping(value = "/addEtudiant", method = RequestMethod.POST)
    public String saveEtudiant(Etudiant etudiant){
        etudiantRepository.save(etudiant);
        return "redirect:/findStudents";
    }

    @RequestMapping(value = "studentForm")
    public String StudentPageForm(){
        return "studentForm";
    }

    @RequestMapping(value = "deleteStudent/{id}")
    public String deleteStudent(@PathVariable Long id){
        etudiantRepository.deleteById(id);
        return "redirect:/findStudents";
    }

    @RequestMapping(value = "etudiants/edit/{id}", method = RequestMethod.GET)
    public String edit(@PathVariable Long id, Model model) {
        return "etudiantForm";
    }

    @Secured(value = {"ROLE_ADMIN","ROLE_SCOLARITE","ROLE_PROF"})
    @RequestMapping(value = "/findStudents", method = RequestMethod.GET)
    public String listesEtudiants(Model model,
                                  @RequestParam(name = "page", defaultValue = "0") int page,
                                  @RequestParam(name = "size", defaultValue = "5") int size){
        try {
            Page<Etudiant> pageEtudiants = etudiantRepository.findAll(new PageRequest(page, size));
            int[] pages = new int[pageEtudiants.getTotalPages()];
            model.addAttribute("pages", pages);
            model.addAttribute("listEtudiants", pageEtudiants.getContent());
        } catch (Exception e){
            model.addAttribute("exception", e);
        }
       return "students/listStudents";
    }

    @RequestMapping(value = "/etudiants/{id}", method = RequestMethod.GET)
    public Optional<Etudiant> afficherUnEtudiant(Model model, @PathVariable Long id) {
        model.addAttribute("etudiant", etudiantRepository.findById(id));
        return etudiantRepository.findById(id);
    }

    @RequestMapping(value = "/getLogedUser")
    public Map<String, Object> getLogedUser(HttpServletRequest httpServletRequest){
        HttpSession httpSession = httpServletRequest.getSession();
        SecurityContext securityContext = (SecurityContext) httpSession.getAttribute("SPRING_SECURITY_CONTEXT");
        String username = securityContext.getAuthentication().getName();
        List<String> roles = new ArrayList<>();
        for (GrantedAuthority ga :  securityContext.getAuthentication().getAuthorities()){
            roles.add(ga.getAuthority());
        }
        Map<String, Object> params = new HashMap<>();
        params.put("username", username);
        params.put("roles", roles);
        return params;
    }
}
