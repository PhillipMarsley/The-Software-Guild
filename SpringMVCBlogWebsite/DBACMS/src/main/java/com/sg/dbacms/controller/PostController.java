/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.dbacms.controller;

import com.sg.dbacms.dao.DBACMSDao;
import com.sg.dbacms.dao.DBACMSPersistenceException;
import com.sg.dbacms.model.ApprovalStatus;
import com.sg.dbacms.model.Category;
import com.sg.dbacms.model.Post;
import com.sg.dbacms.model.User;
import java.security.Principal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

/**
 *
 * @author Alex
 */
@Controller
public class PostController {
    
    @Inject
    private DBACMSDao dao;
    
    @PostMapping("/makeAThing")
    public String createPost(
//            @Valid @RequestBody Post post) 
            HttpServletRequest request, Principal principal)
            throws DBACMSPersistenceException {
        
        String userName = principal.getName();
        int currentUserId = 1;
        
        for (User currentUser : dao.getAllUsers()) {
            if (userName.equals(currentUser.getUserName())) {
                currentUserId = currentUser.getUserId();
                break;
            }
        }
        
        Post post = new Post();
        String headline = request.getParameter("headline");
        String content = request.getParameter("postHtml");
        String dateString = request.getParameter("date");
        dateString += " 00:00";
        String imgLink = request.getParameter("thumbnail");
        String tags = request.getParameter("tag");
        Category cat = new Category(tags);
        List<Category> cats = new ArrayList<>();
        cats.add(cat);
        
        ApprovalStatus as = dao.getApprovalStatusById(1);
        
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        LocalDateTime postingDate = LocalDateTime.parse(dateString, formatter);
        
        post.setHeadline(headline);
        post.setContent(content);
        post.setApprovalStatus(as);
        post.setPostingDate(postingDate);
        post.setImgLink(imgLink);
        post.setUser(dao.getUserById(currentUserId));
        post.setCategories(cats);
        
        
//        int i = 1;
        
        dao.addPost(post);
        
        return "redirect:/post.html";
    }
}
