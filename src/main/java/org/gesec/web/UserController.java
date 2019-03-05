package org.gesec.web;

import org.gesec.dao.RoleRepository;
import org.gesec.dao.UserRepository;
import org.gesec.entities.Role;
import org.gesec.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@Secured(value = "ROLE_ADMIN")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @RequestMapping(value = "/addUser")
    public User save(User user){
        return userRepository.save(user);
    }

    @RequestMapping(value = "/findUsers")
    public List<User> findAllUsers(){
        return userRepository.findAll();
    }

    @RequestMapping(value = "/addRole")
    public Role save(Role role){
        return roleRepository.save(role);
    }

    @RequestMapping(value = "/findRoles")
    public List<Role> findAllRoles(){
        return roleRepository.findAll();
    }

    /*@RequestMapping(value = "/addRoleToUser")
    public Optional<User> addRoleToUser(String username, String role){
        Optional<User> u = userRepository.findById(username);
        Optional<Role> r = roleRepository.findById(role);
        u.get().getRoles().add(r);
        userRepository.save(u);
        return  u;
    }*/
}
