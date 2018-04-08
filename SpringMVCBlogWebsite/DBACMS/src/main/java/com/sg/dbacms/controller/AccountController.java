/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.dbacms.controller;

import com.sg.dbacms.dao.DBACMSDao;
import com.sg.dbacms.dao.DBACMSPersistenceException;
import com.sg.dbacms.model.Role;
import com.sg.dbacms.model.User;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

/**
 *
 * @author Alex
 */
@Controller
public class AccountController {
    
    @Inject 
    private DBACMSDao dao;
    @Inject
    private PasswordEncoder encoder;
    
    @PostMapping("/user")
    public String createUser(HttpServletRequest request) throws DBACMSPersistenceException {
        // create user then redirect
        String clearPw = request.getParameter("password");
        String hashPw = encoder.encode(clearPw);
        User user = new User();
        user.setPassword(hashPw);
        
        Role defaultRole = dao.getRoleById(1);
        List<Role> roles = new ArrayList<>();
        roles.add(defaultRole);
        user.setRoles(roles);
        
        user.setName(request.getParameter("name"));
        user.setEmail(request.getParameter("email"));
        user.setUserName(request.getParameter("userName"));
        dao.addUser(user);
        
        return "redirect:/login";
    }
    
}
