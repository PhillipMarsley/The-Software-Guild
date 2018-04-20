/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.dbacms.dao;

import com.sg.dbacms.model.ApprovalStatus;
import com.sg.dbacms.model.Category;
import com.sg.dbacms.model.Comment;
import com.sg.dbacms.model.Post;
import com.sg.dbacms.model.Role;
import com.sg.dbacms.model.User;
import java.util.List;

/**
 *
 * @author Alex
 */
public interface DBACMSDao {
    
    // ****************   user methods   *********************************
    public User addUser(User user) throws DBACMSPersistenceException;
    
    public int deleteUser(int userId) throws DBACMSPersistenceException ;
    
    public User updateUser(User user) throws DBACMSPersistenceException;
    
    public User getUserById(int userId) throws DBACMSPersistenceException;
    
    public List<User> getAllUsers() throws DBACMSPersistenceException;
    
    public List<User> getAllUsersByRoleId(int roleId) throws DBACMSPersistenceException;
    
    // ********************   role methods   **********************************
    public Role addRole(Role role) throws DBACMSPersistenceException;
    
    public int deleteRole(int roleId) throws DBACMSPersistenceException;
    
    public Role updateRole(Role role) throws DBACMSPersistenceException;
    
    public Role getRoleById(int roleId) throws DBACMSPersistenceException;
    
    public List<Role> getAllRoles() throws DBACMSPersistenceException;
    
    // *******************   post methods   *******************************
    public Post addPost(Post post) throws DBACMSPersistenceException;
    
    public int deletePost(int postId) throws DBACMSPersistenceException;
    
    public int deleteAllPostsByUserId(int userId) throws DBACMSPersistenceException;
    
    public Post updatePost(Post post) throws DBACMSPersistenceException;
    
    public Post getPostById(int postId) throws DBACMSPersistenceException;
    
    public List<Post> getAllPostsByStaticCategory() throws DBACMSPersistenceException;
    
    public List<Post> getAllPosts() throws DBACMSPersistenceException;
    
    public List<Post> getAllPostsByUserId(int userId) 
            throws DBACMSPersistenceException;
    
    public List<Post> getAllPostsByApprovalStatusId(int statusId) 
            throws DBACMSPersistenceException;
    
    public List<Post> getThreeMostRecentPosts() throws DBACMSPersistenceException;
    
    public List<Post> getTwelveMostRecentPosts() throws DBACMSPersistenceException;
    
    public List<Post> getTwelveMostRecentApprovedPosts() throws DBACMSPersistenceException;
    
    public List<Post> getMostRecentPostsAfterFirstTwelve() 
            throws DBACMSPersistenceException;
    
    // ******************   comment methods   ******************************
    public Comment addComment(Comment comment) throws DBACMSPersistenceException;
    
    public int deleteComment(int commentId) throws DBACMSPersistenceException;
    
    public int deleteAllCommentsByUserId(int userId) 
            throws DBACMSPersistenceException;
    
    public int deleteAllCommentsByPostId(int postId) 
            throws DBACMSPersistenceException;
    
    public Comment updateComment(Comment comment) throws DBACMSPersistenceException;
    
    public Comment getCommentById(int commentId) throws DBACMSPersistenceException;
    
    public List<Comment> getAllCommentsByPostId(int postId) 
            throws DBACMSPersistenceException;
    
    public List<Comment> getAllCommentsByUserId(int userId) 
            throws DBACMSPersistenceException;
    
    public List<Comment> getAllCommentsByPostIdAndUserId(int postId, int userId) 
            throws DBACMSPersistenceException;
    
    public List<Comment> getAllComments() throws DBACMSPersistenceException;
    
    // ***********************   category methods   ***************************
    public Category addCategory(Category category) throws DBACMSPersistenceException;
    
    public int deleteCategory(int categoryId)throws DBACMSPersistenceException;
    
    public Category updateCategory(Category category) throws DBACMSPersistenceException;
    
    public Category getCategorById(int categoryId) throws DBACMSPersistenceException;
    
    public List<Category> getAllCategories() throws DBACMSPersistenceException;
    
    
    // *****************   approval status queries   **************************
    public ApprovalStatus addApprovalStatus(ApprovalStatus status) 
            throws DBACMSPersistenceException;
    
    public int deleteApprovalStatus(int id) throws DBACMSPersistenceException;
    
    public ApprovalStatus updateApprovalStatus(ApprovalStatus status) 
            throws DBACMSPersistenceException;
    
    public ApprovalStatus getApprovalStatusById(int id) 
            throws DBACMSPersistenceException;
    
    public List<ApprovalStatus> getAllApprovalStatus() throws DBACMSPersistenceException;
    
    public ApprovalStatus getApprovalStatusByDescription(String description) 
            throws DBACMSPersistenceException;
    
}
