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
import com.sg.dbacms.model.Comment;
import com.sg.dbacms.model.Post;
import com.sg.dbacms.model.Role;
import com.sg.dbacms.model.User;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Alex
 */
@CrossOrigin
@RestController
public class DBACMSRESTController {

    @Inject
    private DBACMSDao dao;
    @Inject
    private PasswordEncoder encoder;

    @GetMapping("/posts/static")
    public List<Post> getAllStaticPosts() throws DBACMSPersistenceException {
	return dao.getAllPostsByStaticCategory();
    }

//    @GetMapping("/")
//    public String indexPage() {
//        return "index";
//    }
    // User methods
    @GetMapping("/users")
    public List<User> getAllUsers() throws DBACMSPersistenceException {
	return dao.getAllUsers();
    }

//    @GetMapping("/user/new")
//    public void displayUserCreateForm() {
//        // add user create form page
//    }
//    
//    @PostMapping("/user")
//    @ResponseStatus(HttpStatus.CREATED)
//    public User createUser(@Valid @RequestBody User user) throws DBACMSPersistenceException {
//        // create user then redirect
//        String clearPw = user.getPassword();
//        String hashPw = encoder.encode(clearPw);
//        user.setPassword(hashPw);
//        Role defaultRole = new Role("ROLE_USER");
//        List<Role> roles = new ArrayList<>();
//        roles.add(defaultRole);
//        user.setRoles(roles);
//        return dao.addUser(user);
//    }
    @GetMapping("/user/{id}")
    public User getUserById(@PathVariable("id") int id) throws DBACMSPersistenceException {
	return dao.getUserById(id);
    }

    @GetMapping("/user/{id}/edit")
    public void displayUserEditForm() {
	//display user edit form with user{id} data entered
    }

    @PutMapping("/user/{id}")
    public void udpateUser(@PathVariable("id") int id, @Valid @RequestBody User user)
	    throws DBACMSPersistenceException {
	// update user then redirect
	dao.updateUser(user);
    }

    @DeleteMapping("/user/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUserById(@PathVariable("id") int id) throws DBACMSPersistenceException {
	dao.deleteUser(id);
    }

    // Post Methods
    @GetMapping("/posts")
    public List<Post> getAllPosts() throws DBACMSPersistenceException {
	return dao.getAllPosts();
    }
    
    @GetMapping("/posts/{userId}")
    public List<Post> getPostsByUserId(@PathVariable("userId") int id) throws DBACMSPersistenceException {
        return dao.getAllPostsByUserId(id);
    }
    
    @GetMapping("/12posts")
    public List<Post> get12RecentApprovedPosts() throws DBACMSPersistenceException {
        return dao.getTwelveMostRecentApprovedPosts();
    }

    @GetMapping("/post/new")
    public void displayPostCreateForm() {
	// add user create form page
    }

//    @PostMapping("/makeAThing")
//    @ResponseStatus(HttpStatus.CREATED)
//    public void createPost(
////            @Valid @RequestBody Post post) 
//            HttpServletRequest request)
//            throws DBACMSPersistenceException {
//        
//        Post post = new Post();
//        String headline = request.getParameter("headline");
//        String content = request.getParameter("postHtml");
//        String dateString = request.getParameter("date");
//        dateString += " 00:00";
//        String imgLink = request.getParameter("thumbnail");
//        String tags = request.getParameter("tag");
//        Category cat = new Category(tags);
//        List<Category> cats = new ArrayList<>();
//        cats.add(cat);
//        
//        ApprovalStatus as = dao.getApprovalStatusById(1);
//        
//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
//        LocalDateTime postingDate = LocalDateTime.parse(dateString, formatter);
//        
//        post.setHeadline(headline);
//        post.setContent(content);
//        post.setApprovalStatus(as);
//        post.setPostingDate(postingDate);
//        post.setImgLink(imgLink);
//        post.setUser(dao.getUserById(1));
//        post.setCategories(cats);
//        
//        
////        int i = 1;
//        
//        dao.addPost(post);
//    }
//    public Post createPost(@Valid @RequestBody Post post, HttpServletRequest request,
//            Principal principal) throws DBACMSPersistenceException {
//        String userName = principal.getName();
//        int currentUserId = 1;
//        
//        for (User currentUser : dao.getAllUsers()) {
//            if (userName.equals(currentUser.getUserName())) {
//                currentUserId = currentUser.getUserId();
//                break;
//            }
//        }
//        
//        User currentUser = dao.getUserById(currentUserId);
//        
//        post.setUser(currentUser);
//        
//        List<Category> categories = new ArrayList<>();
//        
//        String tags = request.getParameter("tags");
//        String temp = tags.replaceAll("[,;\\s+]", "");
//        String[] tagArray = temp.split("#");
//        
//        for (String currentTag : tagArray) {
//            Category category = new Category(currentTag);
//            categories.add(category);
//        }
//        
//        post.setCategories(categories);
//        
//        return dao.addPost(post);
//    }
    @GetMapping("/post/{id}")
    public Post getPostById(@PathVariable("id") int id) throws DBACMSPersistenceException {
	return dao.getPostById(id);
    }

    @GetMapping("/post/{id}/edit")
    public void displayPostEditForm() throws DBACMSPersistenceException {
	//display user edit form with user{id} data entered
    }

    @PutMapping("/post/{id}")
    public Post udpatePost(@PathVariable("id") int id, @Valid @RequestBody Post post)
	    throws DBACMSPersistenceException {
	// update user then redirect
	return dao.updatePost(post);
    }

    @PostMapping("/approvePost/{id}")
    public Post approvePost(@PathVariable("id") int postId) throws DBACMSPersistenceException {
	Post post = dao.getPostById(postId);
	ApprovalStatus as = dao.getApprovalStatusById(2);
	post.setApprovalStatus(as);
	return dao.updatePost(post);
    }

    @DeleteMapping("/post/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public int deletePost(@PathVariable("id") int id) throws DBACMSPersistenceException {
	return dao.deletePost(id);
    }

    @GetMapping("/getUnapprovedPosts")
    public List<Post> getUnapprovedPosts() throws DBACMSPersistenceException {
	return dao.getAllPostsByApprovalStatusId(1);
    }

    // Comments methods
    @GetMapping("/comments")
    public List<Comment> getAllComments() throws DBACMSPersistenceException {
	// need to implement comments methods
	return dao.getAllComments();
    }

    @GetMapping("/comment/new")
    public void displayCommentCreateForm() throws DBACMSPersistenceException {
	// add user create form page
    }

    @PostMapping("/comment")
    @ResponseStatus(HttpStatus.CREATED)
    public Comment createComment(@Valid @RequestBody Comment comment) throws DBACMSPersistenceException {
	// create user then redirect
	return dao.addComment(comment);
    }

    @GetMapping("/comment/{id}")
    public Comment getCommentById(@PathVariable("id") int id) throws DBACMSPersistenceException {
	return dao.getCommentById(id);
    }

    @GetMapping("/comment/{id}/edit")
    public void displayCommentEditForm() throws DBACMSPersistenceException {
	//display user edit form with user{id} data entered
    }

    @PutMapping("/comment/{id}")
    public void udpateComment(@PathVariable("id") int id,
	    @Valid @RequestBody Comment comment) throws DBACMSPersistenceException {
	// update user then redirect
	dao.updateComment(comment);
    }

    @DeleteMapping("/comment/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteComment(@PathVariable("id") int id) throws DBACMSPersistenceException {
	dao.deleteComment(id);
    }

    // Category methods
    @GetMapping("/categories")
    public List<Category> getAllCategories() throws DBACMSPersistenceException {
	return dao.getAllCategories();
    }

    @GetMapping("/category/new")
    public void displayCategoryCreateForm() throws DBACMSPersistenceException {
	// add user create form page
    }

    @PostMapping("/category")
    @ResponseStatus(HttpStatus.CREATED)
    public Category createCategory(@Valid @RequestBody Category category)
	    throws DBACMSPersistenceException {
	// create user then redirect
	return dao.addCategory(category);
    }

    @GetMapping("/category/{id}")
    public Category getCategoryById(@PathVariable("id") int id)
	    throws DBACMSPersistenceException {
	return dao.getCategorById(id);
    }

    @GetMapping("/category/{id}/edit")
    public void displayCategoryEditForm() {
	//display user edit form with user{id} data entered
    }

    @PutMapping("/category/{id}")
    public void udpateCategory(@PathVariable("id") int id,
	    @Valid @RequestBody Category category) throws DBACMSPersistenceException {

	dao.updateCategory(category);
    }

    @DeleteMapping("/category/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public int deleteCategory(@PathVariable("id") int id)
	    throws DBACMSPersistenceException {

	return dao.deleteCategory(id);
    }

    // Role methods
    @GetMapping("/roles")
    public List<Role> getAllRoles() throws DBACMSPersistenceException {
	return dao.getAllRoles();
    }

    @GetMapping("/role/new")
    public void displayRoleCreateForm() throws DBACMSPersistenceException {
	// add user create form page
    }

    @PostMapping("/role")
    @ResponseStatus(HttpStatus.CREATED)
    public Role createRole(@Valid @RequestBody Role role)
	    throws DBACMSPersistenceException {

	return dao.addRole(role);
    }

    @GetMapping("/role/{id}")
    public Role getRoleById(@PathVariable("id") int id)
	    throws DBACMSPersistenceException {
	return dao.getRoleById(id);
    }

    @GetMapping("/role/{id}/edit")
    public void displayRoleEditForm() {
	//display user edit form with user{id} data entered
    }

    @PutMapping("/role/{id}")
    public Role udpateRole(@PathVariable("id") int id,
	    @Valid @RequestBody Role role) throws DBACMSPersistenceException {

	return dao.updateRole(role);
    }

    @DeleteMapping("/role/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public int deleteRole(@PathVariable("id") int id) throws DBACMSPersistenceException {
	return dao.deleteRole(id);
    }

    // Aproval status methods
    @GetMapping("/approvals")
    public List<ApprovalStatus> getAllApprovalStatus() throws DBACMSPersistenceException {
	return dao.getAllApprovalStatus();
    }

    @GetMapping("/approval/new")
    public void displayApprovalStatusCreateForm() {
	// add user create form page
    }

    @PostMapping("/approval")
    @ResponseStatus(HttpStatus.CREATED)
    public ApprovalStatus createApprovalStatus(
	    @Valid @RequestBody ApprovalStatus status) throws DBACMSPersistenceException {
	// create user then redirect
	return dao.addApprovalStatus(status);
    }

    @GetMapping("/approval/{id}")
    public ApprovalStatus getApprovalStatusById(@PathVariable("id") int id)
	    throws DBACMSPersistenceException {
	return dao.getApprovalStatusById(id);
    }

    @GetMapping("/approval/{id}/edit")
    public void displayApprovalStatusEditForm() {
	//display user edit form with user{id} data entered
    }

    @PutMapping("/approval/{id}")
    public ApprovalStatus udpateApprovalStatus(@PathVariable("id") int id,
	    @Valid @RequestBody ApprovalStatus status) throws DBACMSPersistenceException {

	return dao.updateApprovalStatus(status);
    }

    @DeleteMapping("/approval/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public int deleteApprovalStatus(@PathVariable("id") int id)
	    throws DBACMSPersistenceException {
	return dao.deleteApprovalStatus(id);
    }
}
