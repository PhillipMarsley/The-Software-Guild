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
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

/**
 *
 * @author Alex
 */
@Controller
public class LoginController {
    
    @GetMapping("/login")
    public String showLoginForm() {
        return "login";
    }
}
