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
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 *
 * @author blake
 */
public class DBACMSDaoTest {

    private DBACMSDao dao;

    public DBACMSDaoTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
        ApplicationContext ctx
                = new ClassPathXmlApplicationContext("test-applicationContext.xml");

        dao = ctx.getBean("dbacmsDao", DBACMSDao.class);
        
        try {
            // delete comment table
            List<Comment> comments = dao.getAllComments();
            for (Comment currentComment : comments) {
                dao.deleteComment(currentComment.getCommentId());
            }
            
            // delete post table
            List<Post> posts = dao.getAllPosts();
            for (Post currentPost : posts) {
                dao.deletePost(currentPost.getPostId());
            }
            
            // delete category table
            List<Category> cats = dao.getAllCategories();
            for (Category currentCat : cats) {
                dao.deleteCategory(currentCat.getCategoryId());
            }
            
            // delete approval status table
            List<ApprovalStatus> status = dao.getAllApprovalStatus();
            for (ApprovalStatus currentStatus : status) {
                dao.deleteApprovalStatus(currentStatus.getApprovalStatusId());
            }
            
            // delete user table
            List<User> users = dao.getAllUsers();
            for (User currentUser : users) {
                dao.deleteUser(currentUser.getUserId());
            }
            
            // delete role table
            List<Role> roles = dao.getAllRoles();
            for (Role currentRole : roles) {
                dao.deleteRole(currentRole.getRoleId());
            }
            
        } catch (DBACMSPersistenceException e) {
            System.out.println("setup throwing error");

        }
    }

    @After
    public void tearDown() {
    }
    
    // *****************************************************************
    // *******************   Role Tests  *******************************
    // *****************************************************************

    /**
     * Test of addRole method, of class DBACMSDao.
     */
    @Test
    public void testAddGetDeleteRole() throws Exception {
        Role role = new Role("Test Role 1");
        role = dao.addRole(role);

        Role fromDb = dao.getRoleById(role.getRoleId());
        assertEquals(fromDb, role);

        assertEquals(1, dao.deleteRole(role.getRoleId()));
        assertEquals(0, dao.deleteRole(role.getRoleId()));
    }
    
    /**
     * Test getAllRoles method
     */
    @Test
    public void testGetAllRoles() throws Exception {
        Role role1 = new Role("Test Role 1");
        Role role2 = new Role("Test Role 2");
        Role role3 = new Role("Test Role 3");
        
        assertEquals(0, dao.getAllRoles().size());
        
        dao.addRole(role1);
        assertEquals(1, dao.getAllRoles().size());
        
        dao.addRole(role2);
        assertEquals(2, dao.getAllRoles().size());
        
        dao.addRole(role3);
        assertEquals(3, dao.getAllRoles().size());
        
        dao.deleteRole(role1.getRoleId());
        dao.deleteRole(role2.getRoleId());
        dao.deleteRole(role3.getRoleId());
        assertEquals(0, dao.getAllRoles().size());
    }
    
    @Test
    public void testUpdateRole() throws Exception {
        Role role = new Role("Test Role 1");
        role = dao.addRole(role);
        
        Role updatedRole = new Role("Updated Role");
        updatedRole.setRoleId(role.getRoleId());
        role = dao.updateRole(updatedRole);
        
        assertEquals(role, updatedRole);
    }
    
    // *****************************************************************
    // *******************   User Tests  *******************************
    // *****************************************************************

    /**
     * Test of addUser method, of class DBACMSDao.
     */
    @Test
    public void testAddGetDeleteUser() throws Exception {
        User user = new User();
        Role role1 = new Role();
        Role role2 = new Role();
        List<Role> roles = new ArrayList<>();
        
        role1.setName("Test Role 1");
        role2.setName("Test Role 2");
        role1 = dao.addRole(role1);
        role2 = dao.addRole(role2);
        roles.add(role1);
        roles.add(role2);
        
        user.setName("Test Name");
        user.setUserName("Test username");
        user.setPassword("Testpassword");
        user.setEmail("test@email.com");
        user.setRoles(roles);
        
        user = dao.addUser(user);
        
        User fromDb = dao.getUserById(user.getUserId());
        assertEquals(fromDb, user);
        
        // test delete user
        assertEquals(1, dao.deleteUser(user.getUserId()));
        assertEquals(0, dao.deleteUser(user.getUserId()));
        
        // Test user with no roles
        User userNoRoles = new User();
        userNoRoles.setName("Test No Role User");
        userNoRoles.setUserName("Test No Role username");
        userNoRoles.setPassword("TestNoRolepassword");
        userNoRoles.setEmail("TestNoRoles@email.com");
        
        userNoRoles = dao.addUser(userNoRoles);
        
        fromDb = dao.getUserById(userNoRoles.getUserId());
        assertEquals(fromDb, userNoRoles);
        
        // Test delete
        assertEquals(1, dao.deleteUser(userNoRoles.getUserId()));
        assertEquals(0, dao.deleteUser(userNoRoles.getUserId()));
    }
    
    /**
     * Test getAllUsers method
     */
    @Test
    public void testGetAllUsers() throws Exception {
        User user1 = new User();
        User user2 = new User();
        User user3 = new User();
        Role role1 = new Role("Test Role 1");
        Role role2 = new Role("Test Role 2");
        List<Role> roles = new ArrayList<>();
        roles.add(role1);
        dao.addRole(role1);
        dao.addRole(role2);
        
        user1.setName("Test user 1");
        user1.setUserName("User1TestUserName");
        user1.setPassword("User1TestPW");
        user1.setEmail("user1Test@email.com");
        user1.setRoles(roles);
        
        user2.setName("Test user 2");
        user2.setUserName("User2TestUserName");
        user2.setPassword("User2TestPW");
        user2.setEmail("user2Test@email.com");
        roles.add(role2);
        user2.setRoles(roles);
        
        // No role user
        user3.setName("Test user 3");
        user3.setUserName("User3TestUserName");
        user3.setPassword("User3TestPW");
        user3.setEmail("user3Test@email.com");
        
        assertEquals(0, dao.getAllUsers().size());
        
        dao.addUser(user1);
        assertEquals(1, dao.getAllUsers().size());
        
        dao.addUser(user2);
        assertEquals(2, dao.getAllUsers().size());
        
        dao.addUser(user3);
        assertEquals(3, dao.getAllUsers().size());
        
        // test if user objects coming from db are complete
        List<User> users = dao.getAllUsers();
        
        assertEquals(users.get(0), user1);
        assertEquals(users.get(1), user2);
        assertEquals(users.get(2), user3);
    }
    
    /**
     * Test getAllUsersByRoleId method
     */
    @Test
    public void testGetAllUsersByRoleId() throws Exception {
        // create roles
        User user1 = new User();
        User user2 = new User();
        User user3 = new User();
        User user4 = new User();
        Role role1 = new Role("Test Role 1");
        Role role2 = new Role("Test Role 2");
        Role role3 = new Role("Test Role 3");
        Role role4 = new Role("Test Role 4");
        dao.addRole(role1);
        dao.addRole(role2);
        dao.addRole(role3);
        dao.addRole(role4);
        
        List<Role> user1Roles = new ArrayList<>();
        List<Role> user2Roles = new ArrayList<>();
        List<Role> user3Roles = new ArrayList<>();
        List<Role> user4Roles = new ArrayList<>();
        
        user1Roles.add(role1);
        user1Roles.add(role2);
        user1Roles.add(role3);
        user1Roles.add(role4);
        
        user2Roles.add(role2);
        user2Roles.add(role3);
        
        user3Roles.add(role4);
        
        user4Roles.add(role2);
        user4Roles.add(role4);
        
        // create users
        user1.setName("User 1");
        user1.setUserName("user1username");
        user1.setPassword("user1password");
        user1.setEmail("user1test@email.com");
        user1.setRoles(user1Roles);
        user1 = dao.addUser(user1);
        
        user2.setName("User 2");
        user2.setUserName("user2username");
        user2.setPassword("user2password");
        user2.setEmail("user2test@email.com");
        user2.setRoles(user2Roles);
        user2 = dao.addUser(user2);
        
        user3.setName("User 3");
        user3.setUserName("user3username");
        user3.setPassword("user3password");
        user3.setEmail("user3test@email.com");
        user3.setRoles(user3Roles);
        user3 = dao.addUser(user3);
        
        user4.setName("User 4");
        user4.setUserName("user4username");
        user4.setPassword("user4password");
        user4.setEmail("user4test@email.com");
        user4.setRoles(user4Roles);
        user4 = dao.addUser(user4);
        
        // get users by role 1, validate size and data
        List<User> role1Users = dao.getAllUsersByRoleId(role1.getRoleId());
        assertEquals(1, dao.getAllUsersByRoleId(role1.getRoleId()).size());
        assertEquals(user1, role1Users.get(0));
        
        // get users by role 2, validate size and data
        List<User> role2Users = dao.getAllUsersByRoleId(role2.getRoleId());
        assertEquals(3, dao.getAllUsersByRoleId(role2.getRoleId()).size());
        assertEquals(user1, role2Users.get(0));
        assertEquals(user2, role2Users.get(1));
        assertEquals(user4, role2Users.get(2));
        
        // get users by role 3, validate size and data
        List<User> role3Users = dao.getAllUsersByRoleId(role3.getRoleId());
        assertEquals(2, dao.getAllUsersByRoleId(role3.getRoleId()).size());
        assertEquals(user1, role3Users.get(0));
        assertEquals(user2, role3Users.get(1));
        
        // get users by role 4, validate size and data
        List<User> role4Users = dao.getAllUsersByRoleId(role4.getRoleId());
        assertEquals(3, dao.getAllUsersByRoleId(role4.getRoleId()).size());
        assertEquals(user1, role4Users.get(0));
        assertEquals(user3, role4Users.get(1));
        assertEquals(user4, role4Users.get(2));
    }
    
    /**
     * Test updateUser method 
     */
    @Test
    public void testUpdateUser() throws Exception {
        User user = new User();
        Role role1 = new Role();
        Role role2 = new Role();
        List<Role> roles = new ArrayList<>();
        
        role1.setName("Test Role 1");
        role2.setName("Test Role 2");
        role1 = dao.addRole(role1);
        role2 = dao.addRole(role2);
        roles.add(role1);
        roles.add(role2);
        
        user.setName("Test Name");
        user.setUserName("Test username");
        user.setPassword("Testpassword");
        user.setEmail("test@email.com");
        user.setRoles(roles);
        
        user = dao.addUser(user);
        
        User updatedUser = new User();
        Role updatedRole1 = new Role();
        Role updatedRole2 = new Role();
        List<Role> updatedRoles = new ArrayList<>();
        
        updatedRole1.setName("Updated Role 1");
        updatedRole1.setRoleId(role1.getRoleId());
        updatedRole2.setName("Updated Role 2");
        updatedRole2.setRoleId(role2.getRoleId());
        updatedRoles.add(updatedRole1);
        updatedRoles.add(updatedRole2);
        
        updatedUser.setUserId(user.getUserId());
        updatedUser.setName("Updated Test Name");
        updatedUser.setUserName("Updated Test username");
        updatedUser.setPassword("UpdatedTestpassword");
        updatedUser.setEmail("updatedTest@email.com");
        updatedUser.setRoles(updatedRoles);
        
        user = dao.updateUser(updatedUser);
        
        assertEquals(user, updatedUser);
    }
    
    // *****************************************************************
    // *******************   Aoproval Status Tests  ********************
    // *****************************************************************
    
    /**
     * Test addApprovalStatus, getApprovalStatusById, deleteApprovalStatus methods
     * @throws Exception 
     */
    @Test
    public void testAddGetDeleteApprovalStatus() throws Exception {
        ApprovalStatus as = new ApprovalStatus("Test Approval Status");
        as = dao.addApprovalStatus(as);
        
        ApprovalStatus fromDb = 
                dao.getApprovalStatusById(as.getApprovalStatusId());
        
        assertEquals(fromDb, as);
        
        assertEquals(1, dao.deleteApprovalStatus(as.getApprovalStatusId()));
        assertEquals(0, dao.deleteApprovalStatus(as.getApprovalStatusId()));
    }
    
    /**
     * Test getAllApprovalStatus method
     */
    @Test
    public void getAllApprovalStatus() throws Exception {
        ApprovalStatus as1 = new ApprovalStatus("Test as1");
        ApprovalStatus as2 = new ApprovalStatus("Test as2");
        ApprovalStatus as3 = new ApprovalStatus("Test as3");
        
        assertEquals(0, dao.getAllApprovalStatus().size());
        
        dao.addApprovalStatus(as1);
        assertEquals(1, dao.getAllApprovalStatus().size());
        
        dao.addApprovalStatus(as2);
        assertEquals(2, dao.getAllApprovalStatus().size());
        
        dao.addApprovalStatus(as3);
        assertEquals(3, dao.getAllApprovalStatus().size());
        
        // test if user objects coming from db are complete
        List<ApprovalStatus> status = dao.getAllApprovalStatus();
        
        assertEquals(as1, status.get(0));
        assertEquals(as2, status.get(1));
        assertEquals(as3, status.get(2));
    }
    
    /**
     * Test udpateApprovalStatus method
     * @throws Exception 
     */
    @Test
    public void testUpdateApprovalStatus() throws Exception {
        ApprovalStatus as = new ApprovalStatus("Test AS");
        as = dao.addApprovalStatus(as);
        
        ApprovalStatus updatedAs = new ApprovalStatus("Updated AS");
        updatedAs.setApprovalStatusId(as.getApprovalStatusId());
        
        as = dao.updateApprovalStatus(updatedAs);
        
        assertEquals(as, updatedAs);
    }
    
    
    // *****************************************************************
    // *******************   Category Tests  ***************************
    // *****************************************************************
    
    /**
     * Test addCategory, getCategoryById, deleteCategory methods
     * @throws Exception 
     */
    @Test
    public void testAddGetDeleteCategory() throws Exception {
        Category cat = new Category("Test Category");
        cat = dao.addCategory(cat);
        
        Category fromDb = dao.getCategorById(cat.getCategoryId());
        
        assertEquals(fromDb, cat);
        
        assertEquals(1, dao.deleteCategory(cat.getCategoryId()));
        assertEquals(0, dao.deleteCategory(cat.getCategoryId()));
    }
    
    /**
     * Test getAllCategories method
     * @throws Exception 
     */
    @Test
    public void testGetAllCategories() throws Exception {
        Category cat1 = new Category("Test Cat1");
        Category cat2 = new Category("Test Cat2");
        Category cat3 = new Category("Test Cat3");
        
        assertEquals(0, dao.getAllCategories().size());
        
        dao.addCategory(cat1);
        assertEquals(1, dao.getAllCategories().size());
        
        dao.addCategory(cat2);
        assertEquals(2, dao.getAllCategories().size());
        
        dao.addCategory(cat3);
        assertEquals(3, dao.getAllCategories().size());
        
        // test if user objects coming from db are complete
        List<Category> categories = dao.getAllCategories();
        
        assertEquals(cat1, categories.get(0));
        assertEquals(cat2, categories.get(1));
        assertEquals(cat3, categories.get(2));
    }
    
    /**
     * Test udpateCategory method
     * @throws Exception 
     */
    @Test
    public void testUpdateCategory() throws Exception {
        Category cat = new Category("Test Cat");
        cat = dao.addCategory(cat);
        
        Category updatedCat = new Category("Updated Cat");
        updatedCat.setCategoryId(cat.getCategoryId());
        
        cat = dao.updateCategory(updatedCat);
        
        assertEquals(cat, updatedCat);
    }
    
    // *****************************************************************
    // *******************   Post Tests  *******************************
    // *****************************************************************
    
    /**
     * Test addPost, getPostById, deletePost, 
     *      getAllCommentsByPostId, getAllCommentsByUserId methods
     * @throws Exception 
     */
    @Test
    public void testAddGetDeletePost() throws Exception {
        
        User user = new User();
        Role role1 = new Role();
        Role role2 = new Role();
        List<Role> roles = new ArrayList<>();
        
        role1.setName("Test Role 1");
        role2.setName("Test Role 2");
        role1 = dao.addRole(role1);
        role2 = dao.addRole(role2);
        roles.add(role1);
        roles.add(role2);
        
        user.setName("Test Name");
        user.setUserName("Test username");
        user.setPassword("Testpassword");
        user.setEmail("test@email.com");
        user.setRoles(roles);
        
        user = dao.addUser(user);
        
        ApprovalStatus as = new ApprovalStatus("Test Approval Status");
        as = dao.addApprovalStatus(as);
        
        Category cat1 = new Category("Test Cat1");
        Category cat2 = new Category("Test Cat2");
        Category cat3 = new Category("Test Cat3");
        List<Category> categories = new ArrayList<>();
        categories.add(cat1);
        categories.add(cat2);
        categories.add(cat3);
        cat1 = dao.addCategory(cat1);
        cat2 = dao.addCategory(cat2);
        cat3 = dao.addCategory(cat3);
       
        Post post = new Post();
        post.setHeadline("Test Headline");
        post.setContent("Test Content");
        post.setApprovalStatus(as);
        post.setNumLikes(20);
        post.setPostingDate(LocalDateTime.now());
        post.setExpirationDate(null);
        post.setImgLink("Test imglink");
        post.setUser(user);
        post.setCategories(categories);
        
        post = dao.addPost(post);
        
        // add comments to post
        Comment comment = new Comment();
        comment.setContent("Test Comment");
        comment.setPostingDate(LocalDateTime.now());
        comment.setPost(post);
        comment.setUser(user);
        
        comment = dao.addComment(comment);
        
        Post fromDb = dao.getPostById(post.getPostId());
        assertEquals(fromDb, post);
        
        // validate comment is in DB
        assertEquals(1, dao.getAllCommentsByPostId(post.getPostId()).size());
        assertEquals(1, dao.getAllCommentsByUserId(user.getUserId()).size());
        
        assertEquals(1, dao.deletePost(post.getPostId()));
        assertEquals(0, dao.deletePost(post.getPostId()));
        
        // validate comments were delted
        assertEquals(0, dao.getAllCommentsByPostId(post.getPostId()).size());
        assertEquals(0, dao.getAllCommentsByUserId(user.getUserId()).size());
    }
    
    /**
     * Test getAllPosts method
     * @throws Exception 
     */
    @Test
    public void testGetAllPosts() throws Exception {
        
        User user = new User();
        Role role1 = new Role();
        Role role2 = new Role();
        List<Role> roles = new ArrayList<>();
        
        role1.setName("Test Role 1");
        role2.setName("Test Role 2");
        role1 = dao.addRole(role1);
        role2 = dao.addRole(role2);
        roles.add(role1);
        roles.add(role2);
        
        user.setName("Test Name");
        user.setUserName("Test username");
        user.setPassword("Testpassword");
        user.setEmail("test@email.com");
        user.setRoles(roles);
        
        user = dao.addUser(user);
        
        ApprovalStatus as = new ApprovalStatus("Test Approval Status");
        as = dao.addApprovalStatus(as);
        
        Category cat1 = new Category("Test Cat1");
        Category cat2 = new Category("Test Cat2");
        Category cat3 = new Category("Test Cat3");
        List<Category> categories = new ArrayList<>();
        categories.add(cat1);
        categories.add(cat2);
        categories.add(cat3);
        cat1 = dao.addCategory(cat1);
        cat2 = dao.addCategory(cat2);
        cat3 = dao.addCategory(cat3);
       
        Post post = new Post();
        post.setHeadline("Test Headline");
        post.setContent("Test Content");
        post.setApprovalStatus(as);
        post.setNumLikes(20);
        post.setPostingDate(LocalDateTime.now());
        post.setExpirationDate(null);
        post.setImgLink("Test imglink");
        post.setUser(user);
        post.setCategories(categories);
        
        Post post2 = new Post();
        post2.setHeadline("Test Headline 2");
        post2.setContent("Test Content 2");
        post2.setApprovalStatus(as);
        post2.setNumLikes(20);
        post2.setPostingDate(LocalDateTime.now());
        post2.setExpirationDate(LocalDateTime.of(2020, 1, 1, 1, 1));
        post2.setImgLink("Test imglink 2");
        post2.setUser(user);
        categories.clear();
        categories.add(cat2);
        categories.add(cat3);
        post2.setCategories(categories);
        
        assertEquals(0, dao.getAllPosts().size());
        
        post = dao.addPost(post);
        assertEquals(1, dao.getAllPosts().size());
        
        post2 = dao.addPost(post2);
        assertEquals(2, dao.getAllPosts().size());
        
        List<Post> posts = dao.getAllPosts();
        assertEquals(post, posts.get(0));
        assertEquals(post2, posts.get(1));
    }
    
    @Test
    public void testGetAllPostsByApprovalStatusId() throws Exception {
        // create approval status
        ApprovalStatus as1 = new ApprovalStatus("Approval Status 1");
        ApprovalStatus as2 = new ApprovalStatus("Approval Status 2");
        as1 = dao.addApprovalStatus(as1);
        as2 = dao.addApprovalStatus(as2);
        
        // create posts and add status
        User user = new User();
        user.setName("User");
        user.setUserName("username");
        user.setPassword("password");
        user.setEmail("test@email.com");
        user.setRoles(new ArrayList<>());
        user = dao.addUser(user);
        
        Post post1 = new Post();
        post1.setHeadline("Post 1");
        post1.setContent("Post 1 Content");
        post1.setApprovalStatus(as1);
        post1.setNumLikes(20);
        post1.setPostingDate(LocalDateTime.now());
        post1.setExpirationDate(null);
        post1.setImgLink("Post 1 imglink");
        post1.setUser(user);
        post1.setCategories(new ArrayList<>());
        
        post1 = dao.addPost(post1);
        
        Post post2 = new Post();
        post2.setHeadline("Post 2");
        post2.setContent("Post 2 Content");
        post2.setApprovalStatus(as1);
        post2.setNumLikes(20);
        post2.setPostingDate(LocalDateTime.now());
        post2.setExpirationDate(null);
        post2.setImgLink("Post 2 imglink");
        post2.setUser(user);
        post2.setCategories(new ArrayList<>());
        
        post2 = dao.addPost(post2);
        
        Post post3 = new Post();
        post3.setHeadline("Post 3");
        post3.setContent("Post 3 Content");
        post3.setApprovalStatus(as2);
        post3.setNumLikes(20);
        post3.setPostingDate(LocalDateTime.now());
        post3.setExpirationDate(null);
        post3.setImgLink("Post 3 imglink");
        post3.setUser(user);
        post3.setCategories(new ArrayList<>());
        
        post3 = dao.addPost(post3);
        
        Post post4 = new Post();
        post4.setHeadline("Post 4");
        post4.setContent("Post 4 Content");
        post4.setApprovalStatus(as2);
        post4.setNumLikes(20);
        post4.setPostingDate(LocalDateTime.now());
        post4.setExpirationDate(null);
        post4.setImgLink("Post 4 imglink");
        post4.setUser(user);
        post4.setCategories(new ArrayList<>());
        
        post4 = dao.addPost(post4);
        
        // get posts by status 1, validate size and data
        List<Post> as1Posts = 
                dao.getAllPostsByApprovalStatusId(as1.getApprovalStatusId());
        
        assertEquals(2, as1Posts.size());
        assertEquals(post1, as1Posts.get(0));
        assertEquals(post2, as1Posts.get(1));
        
        // get posts by status 2, validate size and data
        List<Post> as2Posts = 
                dao.getAllPostsByApprovalStatusId(as2.getApprovalStatusId());
        
        assertEquals(2, as2Posts.size());
        assertEquals(post3, as2Posts.get(0));
        assertEquals(post4, as2Posts.get(1));
    }
    
    /**
     * Test getTwelveMostRecentPosts and getMostRecentPostsAfterFirstTwelve
     * and getTwelveMostRecentApprovedPosts
     */
    @Test
    public void testGetTwelveMostRecentPostsAndPostsAfterFirstTwelve() throws Exception {
        // create 4 posts with different posting dates
        ApprovalStatus as1 = new ApprovalStatus("Unapproved");
        as1 = dao.addApprovalStatus(as1);
        
        ApprovalStatus as2 = new ApprovalStatus("Approved");
        as2 = dao.addApprovalStatus(as2);
        
        User user = new User();
        user.setName("User");
        user.setUserName("username");
        user.setPassword("password");
        user.setEmail("test@email.com");
        user.setRoles(new ArrayList<>());
        user = dao.addUser(user);
        
        Post post1 = new Post();
        post1.setHeadline("Post 1");
        post1.setContent("Post 1 Content");
        post1.setApprovalStatus(as1);
        post1.setNumLikes(20);
        post1.setPostingDate(LocalDateTime.of(1901, 1, 1, 1, 1));
        post1.setExpirationDate(null);
        post1.setImgLink("Post 1 imglink");
        post1.setUser(user);
        post1.setCategories(new ArrayList<>());
        
        post1 = dao.addPost(post1);
        
        Post post2 = new Post();
        post2.setHeadline("Post 2");
        post2.setContent("Post 2 Content");
        post2.setApprovalStatus(as1);
        post2.setNumLikes(20);
        post2.setPostingDate(LocalDateTime.of(1902, 1, 1, 1, 1));
        post2.setExpirationDate(null);
        post2.setImgLink("Post 2 imglink");
        post2.setUser(user);
        post2.setCategories(new ArrayList<>());
        
        post2 = dao.addPost(post2);
        
        Post post3 = new Post();
        post3.setHeadline("Post 3");
        post3.setContent("Post 3 Content");
        post3.setApprovalStatus(as1);
        post3.setNumLikes(20);
        post3.setPostingDate(LocalDateTime.of(1903, 1, 1, 1, 1));
        post3.setExpirationDate(null);
        post3.setImgLink("Post 3 imglink");
        post3.setUser(user);
        post3.setCategories(new ArrayList<>());
        
        post3 = dao.addPost(post3);
        
        Post post4 = new Post();
        post4.setHeadline("Post 4");
        post4.setContent("Post 4 Content");
        post4.setApprovalStatus(as1);
        post4.setNumLikes(20);
        post4.setPostingDate(LocalDateTime.of(1904, 1, 1, 1, 1));
        post4.setExpirationDate(null);
        post4.setImgLink("Post 4 imglink");
        post4.setUser(user);
        post4.setCategories(new ArrayList<>());
        
        post4 = dao.addPost(post4);
        
        Post post5 = new Post();
        post5.setHeadline("Post 5");
        post5.setContent("Post 5 Content");
        post5.setApprovalStatus(as1);
        post5.setNumLikes(20);
        post5.setPostingDate(LocalDateTime.of(1905, 1, 1, 1, 1));
        post5.setExpirationDate(null);
        post5.setImgLink("Post 5 imglink");
        post5.setUser(user);
        post5.setCategories(new ArrayList<>());
        
        post5 = dao.addPost(post5);
        
        Post post6 = new Post();
        post6.setHeadline("Post 6");
        post6.setContent("Post 6 Content");
        post6.setApprovalStatus(as1);
        post6.setNumLikes(20);
        post6.setPostingDate(LocalDateTime.of(1906, 1, 1, 1, 1));
        post6.setExpirationDate(null);
        post6.setImgLink("Post 6 imglink");
        post6.setUser(user);
        post6.setCategories(new ArrayList<>());
       
        post6 = dao.addPost(post6);
        
        Post post7 = new Post();
        post7.setHeadline("Post 7");
        post7.setContent("Post 7 Content");
        post7.setApprovalStatus(as2);
        post7.setNumLikes(20);
        post7.setPostingDate(LocalDateTime.of(1907, 1, 1, 1, 1));
        post7.setExpirationDate(null);
        post7.setImgLink("Post 7 imglink");
        post7.setUser(user);
        post7.setCategories(new ArrayList<>());
        
        post7 = dao.addPost(post7);
        
        Post post8 = new Post();
        post8.setHeadline("Post 8");
        post8.setContent("Post 8 Content");
        post8.setApprovalStatus(as2);
        post8.setNumLikes(20);
        post8.setPostingDate(LocalDateTime.of(1908, 1, 1, 1, 1));
        post8.setExpirationDate(null);
        post8.setImgLink("Post 8 imglink");
        post8.setUser(user);
        post8.setCategories(new ArrayList<>());
        
        post8 = dao.addPost(post8);
        
        Post post9 = new Post();
        post9.setHeadline("Post 9");
        post9.setContent("Post 9 Content");
        post9.setApprovalStatus(as2);
        post9.setNumLikes(20);
        post9.setPostingDate(LocalDateTime.of(1909, 1, 1, 1, 1));
        post9.setExpirationDate(null);
        post9.setImgLink("Post 9 imglink");
        post9.setUser(user);
        post9.setCategories(new ArrayList<>());
        
        post9 = dao.addPost(post9);
        
        Post post10 = new Post();
        post10.setHeadline("Post 10");
        post10.setContent("Post 10 Content");
        post10.setApprovalStatus(as2);
        post10.setNumLikes(20);
        post10.setPostingDate(LocalDateTime.of(1910, 1, 1, 1, 1));
        post10.setExpirationDate(null);
        post10.setImgLink("Post 10 imglink");
        post10.setUser(user);
        post10.setCategories(new ArrayList<>());
        
        post10 = dao.addPost(post10);
        
        Post post11 = new Post();
        post11.setHeadline("Post 11");
        post11.setContent("Post 11 Content");
        post11.setApprovalStatus(as2);
        post11.setNumLikes(20);
        post11.setPostingDate(LocalDateTime.of(1911, 1, 1, 1, 1));
        post11.setExpirationDate(null);
        post11.setImgLink("Post 11 imglink");
        post11.setUser(user);
        post11.setCategories(new ArrayList<>());
        
        post11 = dao.addPost(post11);
        
        Post post12 = new Post();
        post12.setHeadline("Post 12");
        post12.setContent("Post 12 Content");
        post12.setApprovalStatus(as2);
        post12.setNumLikes(20);
        post12.setPostingDate(LocalDateTime.of(1912, 1, 1, 1, 1));
        post12.setExpirationDate(null);
        post12.setImgLink("Post 12 imglink");
        post12.setUser(user);
        post12.setCategories(new ArrayList<>());
        
        post12 = dao.addPost(post12);
        
        Post post13 = new Post();
        post13.setHeadline("Post 13");
        post13.setContent("Post 13 Content");
        post13.setApprovalStatus(as1);
        post13.setNumLikes(20);
        post13.setPostingDate(LocalDateTime.of(1913, 1, 1, 1, 1));
        post13.setExpirationDate(null);
        post13.setImgLink("Post 13 imglink");
        post13.setUser(user);
        post13.setCategories(new ArrayList<>());
        
        post13 = dao.addPost(post13);
        
        Post post14 = new Post();
        post14.setHeadline("Post 14");
        post14.setContent("Post 14 Content");
        post14.setApprovalStatus(as1);
        post14.setNumLikes(20);
        post14.setPostingDate(LocalDateTime.of(1914, 1, 1, 1, 1));
        post14.setExpirationDate(null);
        post14.setImgLink("Post 14 imglink");
        post14.setUser(user);
        post14.setCategories(new ArrayList<>());
        
        post14 = dao.addPost(post14);
        
        // get three most recent, validate size and data
        List<Post> mostRecentPosts = dao.getTwelveMostRecentPosts();
        assertEquals(12, mostRecentPosts.size());
        assertEquals(post1, mostRecentPosts.get(0));
        assertEquals(post2, mostRecentPosts.get(1));
        assertEquals(post3, mostRecentPosts.get(2));
        assertEquals(post4, mostRecentPosts.get(3));
        assertEquals(post5, mostRecentPosts.get(4));
        assertEquals(post6, mostRecentPosts.get(5));
        assertEquals(post7, mostRecentPosts.get(6));
        assertEquals(post8, mostRecentPosts.get(7));
        assertEquals(post9, mostRecentPosts.get(8));
        assertEquals(post10, mostRecentPosts.get(9));
        assertEquals(post11, mostRecentPosts.get(10));
        assertEquals(post12, mostRecentPosts.get(11));
        
        // add new post with date prior to first 4 posts
        Post post15 = new Post();
        post15.setHeadline("Post 15");
        post15.setContent("Post 15 Content");
        post15.setApprovalStatus(as1);
        post15.setNumLikes(20);
        post15.setPostingDate(LocalDateTime.of(1900, 1, 1, 1, 1));
        post15.setExpirationDate(null);
        post15.setImgLink("Post 15 imglink");
        post15.setUser(user);
        post15.setCategories(new ArrayList<>());
        
        post15 = dao.addPost(post15);
        
        mostRecentPosts = dao.getTwelveMostRecentPosts();
        assertEquals(12, mostRecentPosts.size());
        assertEquals(post15, mostRecentPosts.get(0));
        assertEquals(post1, mostRecentPosts.get(1));
        assertEquals(post2, mostRecentPosts.get(2));
        assertEquals(post3, mostRecentPosts.get(3));
        assertEquals(post4, mostRecentPosts.get(4));
        assertEquals(post5, mostRecentPosts.get(5));
        assertEquals(post6, mostRecentPosts.get(6));
        assertEquals(post7, mostRecentPosts.get(7));
        assertEquals(post8, mostRecentPosts.get(8));
        assertEquals(post9, mostRecentPosts.get(9));
        assertEquals(post10, mostRecentPosts.get(10));
        assertEquals(post11, mostRecentPosts.get(11));
        
        
        // get posts after first 12, validate size and data
        List<Post> mostRecentPostsAfter12 = 
                dao.getMostRecentPostsAfterFirstTwelve();
        
        assertEquals(3, mostRecentPostsAfter12.size());
        assertEquals(post12, mostRecentPostsAfter12.get(0));
        assertEquals(post13, mostRecentPostsAfter12.get(1));
        assertEquals(post14, mostRecentPostsAfter12.get(2));
        
        // get twelve most recent approved posts
        List<Post> mostRecentApprovedPosts = 
                dao.getTwelveMostRecentApprovedPosts();
        
        assertEquals(6, mostRecentApprovedPosts.size());
        assertEquals(post7, mostRecentApprovedPosts.get(0));
        assertEquals(post8, mostRecentApprovedPosts.get(1));
        assertEquals(post9, mostRecentApprovedPosts.get(2));
        assertEquals(post10, mostRecentApprovedPosts.get(3));
        assertEquals(post11, mostRecentApprovedPosts.get(4));
        assertEquals(post12, mostRecentApprovedPosts.get(5));
    }
    
    /**
     * Test getThreeMostRecentPosts method
     * @throws Exception 
     */
    @Test
    public void testGetThreeMostRecentPosts() throws Exception {
        // create 14 posts with different posting dates
        ApprovalStatus as1 = new ApprovalStatus("Unapproved");
        as1 = dao.addApprovalStatus(as1);
        
        User user = new User();
        user.setName("User");
        user.setUserName("username");
        user.setPassword("password");
        user.setEmail("test@email.com");
        user.setRoles(new ArrayList<>());
        user = dao.addUser(user);
        
        Post post1 = new Post();
        post1.setHeadline("Post 1");
        post1.setContent("Post 1 Content");
        post1.setApprovalStatus(as1);
        post1.setNumLikes(20);
        post1.setPostingDate(LocalDateTime.of(1901, 1, 1, 1, 1));
        post1.setExpirationDate(null);
        post1.setImgLink("Post 1 imglink");
        post1.setUser(user);
        post1.setCategories(new ArrayList<>());
        
        post1 = dao.addPost(post1);
        
        Post post2 = new Post();
        post2.setHeadline("Post 2");
        post2.setContent("Post 2 Content");
        post2.setApprovalStatus(as1);
        post2.setNumLikes(20);
        post2.setPostingDate(LocalDateTime.of(1902, 1, 1, 1, 1));
        post2.setExpirationDate(null);
        post2.setImgLink("Post 2 imglink");
        post2.setUser(user);
        post2.setCategories(new ArrayList<>());
        
        post2 = dao.addPost(post2);
        
        Post post3 = new Post();
        post3.setHeadline("Post 3");
        post3.setContent("Post 3 Content");
        post3.setApprovalStatus(as1);
        post3.setNumLikes(20);
        post3.setPostingDate(LocalDateTime.of(1903, 1, 1, 1, 1));
        post3.setExpirationDate(null);
        post3.setImgLink("Post 3 imglink");
        post3.setUser(user);
        post3.setCategories(new ArrayList<>());
        
        post3 = dao.addPost(post3);
        
        Post post4 = new Post();
        post4.setHeadline("Post 4");
        post4.setContent("Post 4 Content");
        post4.setApprovalStatus(as1);
        post4.setNumLikes(20);
        post4.setPostingDate(LocalDateTime.of(1904, 1, 1, 1, 1));
        post4.setExpirationDate(null);
        post4.setImgLink("Post 4 imglink");
        post4.setUser(user);
        post4.setCategories(new ArrayList<>());
        
        post4 = dao.addPost(post4);
        
        // get 12 most recent, validate size and data
        List<Post> mostRecentPosts = dao.getThreeMostRecentPosts();
        assertEquals(3, mostRecentPosts.size());
        assertEquals(post1, mostRecentPosts.get(0));
        assertEquals(post2, mostRecentPosts.get(1));
        assertEquals(post3, mostRecentPosts.get(2));
        
        // add new post with date prior to first 14 posts
        Post post5 = new Post();
        post5.setHeadline("Post 5");
        post5.setContent("Post 5 Content");
        post5.setApprovalStatus(as1);
        post5.setNumLikes(20);
        post5.setPostingDate(LocalDateTime.of(1900, 1, 1, 1, 1));
        post5.setExpirationDate(null);
        post5.setImgLink("Post 5 imglink");
        post5.setUser(user);
        post5.setCategories(new ArrayList<>());
        
        post5 = dao.addPost(post5);
        
        // get 12 most recent, validate size and data
        mostRecentPosts = dao.getThreeMostRecentPosts();
        assertEquals(3, mostRecentPosts.size());
        assertEquals(post5, mostRecentPosts.get(0));
        assertEquals(post1, mostRecentPosts.get(1));
        assertEquals(post2, mostRecentPosts.get(2));
        
        // get get most recent posts after first 12
        // validate size and data
    }
    
    /**
     * Test updatePost method
     * @throws Exception 
     */
    @Test 
    public void testUdpatePost() throws Exception {
        // create original post
        User user = new User();
        Role role1 = new Role();
        Role role2 = new Role();
        List<Role> roles = new ArrayList<>();
        
        role1.setName("Test Role 1");
        role2.setName("Test Role 2");
        role1 = dao.addRole(role1);
        role2 = dao.addRole(role2);
        roles.add(role1);
        roles.add(role2);
        
        user.setName("Test Name");
        user.setUserName("Test username");
        user.setPassword("Testpassword");
        user.setEmail("test@email.com");
        user.setRoles(roles);
        
        user = dao.addUser(user);
        
        ApprovalStatus as = new ApprovalStatus("Test Approval Status");
        as = dao.addApprovalStatus(as);
        
        Category cat1 = new Category("Test Cat1");
        Category cat2 = new Category("Test Cat2");
        Category cat3 = new Category("Test Cat3");
        List<Category> categories = new ArrayList<>();
        categories.add(cat1);
        categories.add(cat2);
        categories.add(cat3);
        cat1 = dao.addCategory(cat1);
        cat2 = dao.addCategory(cat2);
        cat3 = dao.addCategory(cat3);
       
        Post post = new Post();
        post.setHeadline("Test Headline");
        post.setContent("Test Content");
        post.setApprovalStatus(as);
        post.setNumLikes(20);
        post.setPostingDate(LocalDateTime.now());
        post.setExpirationDate(null);
        post.setImgLink("Test imglink");
        post.setUser(user);
        post.setCategories(categories);
        
        post = dao.addPost(post);
        
        Post updatedPost = new Post();
        updatedPost.setHeadline("Udpated Headline");
        updatedPost.setContent("Udpated Content");
        updatedPost.setApprovalStatus(as);
        updatedPost.setNumLikes(20);
        updatedPost.setPostingDate(LocalDateTime.now());
        updatedPost.setExpirationDate(LocalDateTime.of(2025, 1, 1, 1, 1));
        updatedPost.setImgLink("Udpated imglink");
        updatedPost.setUser(user);
        updatedPost.setCategories(categories);
        updatedPost.setPostId(post.getPostId());
        
        post = dao.updatePost(updatedPost);
        assertEquals(post, updatedPost);
    }
    
    /**
     * Test getAllPostsByUserId, deleteAllPostsByuserId method
     * @throws Exception 
     */
    @Test
    public void testGetDeleteAllPostsByUserId() throws Exception {
        // create user 1
        User user1 = new User();
        Role role1 = new Role();
        Role role2 = new Role();
        List<Role> roles = new ArrayList<>();
        
        role1.setName("Test Role 1");
        role2.setName("Test Role 2");
        role1 = dao.addRole(role1);
        role2 = dao.addRole(role2);
        roles.add(role1);
        roles.add(role2);
        
        user1.setName("Test Name");
        user1.setUserName("Test username");
        user1.setPassword("Testpassword");
        user1.setEmail("test@email.com");
        user1.setRoles(roles);
        
        user1 = dao.addUser(user1);
        
        // create user 2
        User user2 = new User();
        user2.setName("Test Name 2");
        user2.setUserName("Test username 2");
        user2.setPassword("Testpassword2");
        user2.setEmail("test2@email.com");
        user2.setRoles(roles);
        
        user2 = dao.addUser(user2);
        
        ApprovalStatus as = new ApprovalStatus("Test Approval Status");
        as = dao.addApprovalStatus(as);
        
        Category cat1 = new Category("Test Cat1");
        Category cat2 = new Category("Test Cat2");
        Category cat3 = new Category("Test Cat3");
        List<Category> categories1 = new ArrayList<>();
        List<Category> categories2 = new ArrayList<>();
        cat1 = dao.addCategory(cat1);
        cat2 = dao.addCategory(cat2);
        cat3 = dao.addCategory(cat3);
        
        categories1.add(cat1);
        categories1.add(cat2);
        categories1.add(cat3);
        
        categories2.add(cat1);
        categories2.add(cat2);
        
        
        // create posts for user 1
        Post post1 = new Post();
        post1.setHeadline("Test Headline 1");
        post1.setContent("Test Content 1");
        post1.setApprovalStatus(as);
        post1.setNumLikes(20);
        post1.setPostingDate(LocalDateTime.now());
        post1.setExpirationDate(LocalDateTime.of(2030, 1, 1, 1, 1));
        post1.setImgLink("Test imglink 1");
        post1.setUser(user1);
        post1.setCategories(categories1);
        post1 = dao.addPost(post1);
        
        Post post2 = new Post();
        post2.setHeadline("Test Headline 2");
        post2.setContent("Test Content 2");
        post2.setApprovalStatus(as);
        post2.setNumLikes(20);
        post2.setPostingDate(LocalDateTime.now());
        post2.setExpirationDate(null);
        post2.setImgLink("Test imglink 2");
        post2.setUser(user1);
        post2.setCategories(categories2);
        post2 = dao.addPost(post2);
        
        // create posts for user 2
        Post post3 = new Post();
        post3.setHeadline("Test Headline 3");
        post3.setContent("Test Content 3");
        post3.setApprovalStatus(as);
        post3.setNumLikes(20);
        post3.setPostingDate(LocalDateTime.now());
        post3.setExpirationDate(LocalDateTime.of(2030, 1, 1, 1, 1));
        post3.setImgLink("Test imglink 3");
        post3.setUser(user2);
        post3.setCategories(categories1);
        post3 = dao.addPost(post3);
        
        Post post4 = new Post();
        post4.setHeadline("Test Headline 4");
        post4.setContent("Test Content 4");
        post4.setApprovalStatus(as);
        post4.setNumLikes(20);
        post4.setPostingDate(LocalDateTime.now());
        post4.setExpirationDate(null);
        post4.setImgLink("Test imglink 4");
        post4.setUser(user2);
        post4.setCategories(categories2);
        post4 = dao.addPost(post4);
        
        // get posts for users and validate contents
        assertEquals(2, dao.getAllPostsByUserId(user1.getUserId()).size());
        assertEquals(2, dao.getAllPostsByUserId(user2.getUserId()).size());
        assertEquals(4, dao.getAllPosts().size());
        
        List<Post> user1Posts = dao.getAllPostsByUserId(user1.getUserId());
        List<Post> user2Posts = dao.getAllPostsByUserId(user2.getUserId());
        
        assertEquals(post1, user1Posts.get(0));
        assertEquals(post2, user1Posts.get(1));
        
        assertEquals(post3, user2Posts.get(0));
        assertEquals(post4, user2Posts.get(1));
        
        // delete posts for users
        assertEquals(2, dao.deleteAllPostsByUserId(user1.getUserId()));
        assertEquals(0, dao.deleteAllPostsByUserId(user1.getUserId()));
        
        assertEquals(2, dao.deleteAllPostsByUserId(user2.getUserId()));
        assertEquals(0, dao.deleteAllPostsByUserId(user2.getUserId()));
        
        assertEquals(0, dao.getAllPosts().size());
        
    }
    
    // *****************************************************************
    // *******************   Comment Tests  ****************************
    // *****************************************************************
    
    /**
     * test addComment, getCommentById, deleteComment methods
     * @throws Exception 
     */
    @Test
    public void testAddGetDeleteComment() throws Exception {
        User user = new User();
        Role role1 = new Role();
        Role role2 = new Role();
        List<Role> roles = new ArrayList<>();
        
        role1.setName("Test Role 1");
        role2.setName("Test Role 2");
        role1 = dao.addRole(role1);
        role2 = dao.addRole(role2);
        roles.add(role1);
        roles.add(role2);
        
        user.setName("Test Name");
        user.setUserName("Test username");
        user.setPassword("Testpassword");
        user.setEmail("test@email.com");
        user.setRoles(roles);
        
        user = dao.addUser(user);
        
        ApprovalStatus as = new ApprovalStatus("Test Approval Status");
        as = dao.addApprovalStatus(as);
        
        Category cat1 = new Category("Test Cat1");
        Category cat2 = new Category("Test Cat2");
        Category cat3 = new Category("Test Cat3");
        List<Category> categories = new ArrayList<>();
        categories.add(cat1);
        categories.add(cat2);
        categories.add(cat3);
        cat1 = dao.addCategory(cat1);
        cat2 = dao.addCategory(cat2);
        cat3 = dao.addCategory(cat3);
       
        Post post = new Post();
        post.setHeadline("Test Headline");
        post.setContent("Test Content");
        post.setApprovalStatus(as);
        post.setNumLikes(20);
        post.setPostingDate(LocalDateTime.now());
        post.setExpirationDate(null);
        post.setImgLink("Test imglink");
        post.setUser(user);
        post.setCategories(categories);
        
        post = dao.addPost(post);
        
        Comment comment = new Comment();
        comment.setContent("Test Comment");
        comment.setPostingDate(LocalDateTime.now());
        comment.setPost(post);
        comment.setUser(user);
        
        comment = dao.addComment(comment);
        
        Comment fromDb = dao.getCommentById(comment.getCommentId());
        assertEquals(fromDb, comment);
        
        assertEquals(1, dao.deleteComment(comment.getCommentId()));
        assertEquals(0, dao.deleteComment(comment.getCommentId()));
        
    }
    
    /**
     * Test getAllComments method
     * @throws Exception 
     */
    @Test
    public void testGetAllComments() throws Exception {
        // create comment 1
        User user = new User();
        Role role1 = new Role();
        Role role2 = new Role();
        List<Role> roles = new ArrayList<>();
        
        role1.setName("Test Role 1");
        role2.setName("Test Role 2");
        role1 = dao.addRole(role1);
        role2 = dao.addRole(role2);
        roles.add(role1);
        roles.add(role2);
        
        user.setName("Test Name");
        user.setUserName("Test username");
        user.setPassword("Testpassword");
        user.setEmail("test@email.com");
        user.setRoles(roles);
        
        user = dao.addUser(user);
        
        ApprovalStatus as = new ApprovalStatus("Test Approval Status");
        as = dao.addApprovalStatus(as);
        
        Category cat1 = new Category("Test Cat1");
        Category cat2 = new Category("Test Cat2");
        Category cat3 = new Category("Test Cat3");
        List<Category> categories = new ArrayList<>();
        categories.add(cat1);
        categories.add(cat2);
        categories.add(cat3);
        cat1 = dao.addCategory(cat1);
        cat2 = dao.addCategory(cat2);
        cat3 = dao.addCategory(cat3);
       
        Post post = new Post();
        post.setHeadline("Test Headline");
        post.setContent("Test Content");
        post.setApprovalStatus(as);
        post.setNumLikes(20);
        post.setPostingDate(LocalDateTime.now());
        post.setExpirationDate(null);
        post.setImgLink("Test imglink");
        post.setUser(user);
        post.setCategories(categories);
        
        post = dao.addPost(post);
        
        Comment comment = new Comment();
        comment.setContent("Test Comment");
        comment.setPostingDate(LocalDateTime.now());
        comment.setPost(post);
        comment.setUser(user);
        
        // create comment 2
        User user2 = new User();
        List<Role> roles2 = new ArrayList<>();
        roles2.add(role1);
        roles2.add(role2);
        
        user2.setName("Test Name2");
        user2.setUserName("Test username2");
        user2.setPassword("Testpassword2");
        user2.setEmail("test@email.com2");
        user2.setRoles(roles2);
        
        user2 = dao.addUser(user2);
        
        Category cat4 = new Category("Test Cat4");
        Category cat5 = new Category("Test Cat5");
        Category cat6 = new Category("Test Cat6");
        List<Category> categories2 = new ArrayList<>();
        categories2.add(cat4);
        categories2.add(cat5);
        categories2.add(cat6);
        cat4 = dao.addCategory(cat4);
        cat5 = dao.addCategory(cat5);
        cat6 = dao.addCategory(cat6);
       
        Post post2 = new Post();
        post2.setHeadline("Test Headline2");
        post2.setContent("Test Content2");
        post2.setApprovalStatus(as);
        post2.setNumLikes(20);
        post2.setPostingDate(LocalDateTime.now());
        post2.setExpirationDate(LocalDateTime.of(2020, 1, 1, 1, 1));
        post2.setImgLink("Test imglink2");
        post2.setUser(user2);
        post2.setCategories(categories2);
        
        post2 = dao.addPost(post2);
        
        Comment comment2 = new Comment();
        comment2.setContent("Test Comment 2");
        comment2.setPostingDate(LocalDateTime.now());
        comment2.setPost(post2);
        comment2.setUser(user2);
        
        assertEquals(0, dao.getAllComments().size());
        
        comment = dao.addComment(comment);
        assertEquals(1, dao.getAllComments().size());
        
        comment2 = dao.addComment(comment2);
        assertEquals(2, dao.getAllComments().size());
        
        List<Comment> comments = dao.getAllComments();
        assertEquals(comment, comments.get(0));
        assertEquals(comment2, comments.get(1));
    }
    
    /**
     * Test updateComment method
     * @throws Exception 
     */
    @Test
    public void testUpdateComment() throws Exception {
        // create original comment
        User user = new User();
        Role role1 = new Role();
        Role role2 = new Role();
        List<Role> roles = new ArrayList<>();
        
        role1.setName("Test Role 1");
        role2.setName("Test Role 2");
        role1 = dao.addRole(role1);
        role2 = dao.addRole(role2);
        roles.add(role1);
        roles.add(role2);
        
        user.setName("Test Name");
        user.setUserName("Test username");
        user.setPassword("Testpassword");
        user.setEmail("test@email.com");
        user.setRoles(roles);
        
        user = dao.addUser(user);
        
        ApprovalStatus as = new ApprovalStatus("Test Approval Status");
        as = dao.addApprovalStatus(as);
        
        Category cat1 = new Category("Test Cat1");
        Category cat2 = new Category("Test Cat2");
        Category cat3 = new Category("Test Cat3");
        List<Category> categories = new ArrayList<>();
        categories.add(cat1);
        categories.add(cat2);
        categories.add(cat3);
        cat1 = dao.addCategory(cat1);
        cat2 = dao.addCategory(cat2);
        cat3 = dao.addCategory(cat3);
       
        Post post = new Post();
        post.setHeadline("Test Headline");
        post.setContent("Test Content");
        post.setApprovalStatus(as);
        post.setNumLikes(20);
        post.setPostingDate(LocalDateTime.now());
        post.setExpirationDate(null);
        post.setImgLink("Test imglink");
        post.setUser(user);
        post.setCategories(categories);
        
        post = dao.addPost(post);
        
        Comment comment = new Comment();
        comment.setContent("Test Comment");
        comment.setPostingDate(LocalDateTime.now());
        comment.setPost(post);
        comment.setUser(user);
        
        comment = dao.addComment(comment);
        
        // create updated comment
        User user2 = new User();
        List<Role> roles2 = new ArrayList<>();
        roles2.add(role1);
        roles2.add(role2);
        
        user2.setName("Updated User");
        user2.setUserName("Updated username");
        user2.setPassword("Updatedpassword");
        user2.setEmail("updated@email.com");
        user2.setRoles(roles2);
        
        user2 = dao.addUser(user2);
        
        Category cat4 = new Category("Test Cat4");
        Category cat5 = new Category("Test Cat5");
        Category cat6 = new Category("Test Cat6");
        List<Category> categories2 = new ArrayList<>();
        categories2.add(cat4);
        categories2.add(cat5);
        categories2.add(cat6);
        cat4 = dao.addCategory(cat4);
        cat5 = dao.addCategory(cat5);
        cat6 = dao.addCategory(cat6);
       
        Post post2 = new Post();
        post2.setHeadline("Updated Headline");
        post2.setContent("Updated Content");
        post2.setApprovalStatus(as);
        post2.setNumLikes(20);
        post2.setPostingDate(LocalDateTime.now());
        post2.setExpirationDate(LocalDateTime.of(2020, 2, 2, 1, 1));
        post2.setImgLink("Updated imglink");
        post2.setUser(user2);
        post2.setCategories(categories2);
        
        post2 = dao.addPost(post2);
        
        Comment updatedComment = new Comment();
        updatedComment.setContent("Updated Comment");
        updatedComment.setPostingDate(LocalDateTime.now());
        updatedComment.setPost(post2);
        updatedComment.setUser(user2);
        updatedComment.setCommentId(comment.getCommentId());
        
        comment = dao.updateComment(updatedComment);
        assertEquals(comment, updatedComment);
    }
    
    /**
     * test getAllCommentsByPostIdAndUserId
     */
    @Test
    public void testGetAllCommentsByPostIdAndUserId() throws Exception {
        // create users
        User user1 = new User();
        user1.setName("User 1");
        user1.setUserName("username1");
        user1.setPassword("password1");
        user1.setEmail("user1test@email.com");
        user1 = dao.addUser(user1);
        
        User user2 = new User();
        user2.setName("User 2");
        user2.setUserName("username2");
        user2.setPassword("password2");
        user2.setEmail("user2test@email.com");
        user2 = dao.addUser(user2);
        
        ApprovalStatus as = new ApprovalStatus("Test Approval Status");
        as = dao.addApprovalStatus(as);
       
        // create posts for each user
        Post post1 = new Post();
        post1.setHeadline("Post 1");
        post1.setContent("Post 1 Content");
        post1.setApprovalStatus(as);
        post1.setNumLikes(20);
        post1.setPostingDate(LocalDateTime.now());
        post1.setExpirationDate(null);
        post1.setImgLink("Post 1 imglink");
        post1.setUser(user1);
        post1 = dao.addPost(post1);
        
        Post post2 = new Post();
        post2.setHeadline("Post 2");
        post2.setContent("Post 2 Content");
        post2.setApprovalStatus(as);
        post2.setNumLikes(20);
        post2.setPostingDate(LocalDateTime.now());
        post2.setExpirationDate(null);
        post2.setImgLink("Post 2 imglink");
        post2.setUser(user1);
        post2 = dao.addPost(post2);
        
        // create comments for each post
        
        // comment1 on post1 by user2
        Comment comment1 = new Comment();
        comment1.setContent("Comment 1");
        comment1.setPostingDate(LocalDateTime.now());
        comment1.setPost(post1);
        comment1.setUser(user2);
        comment1 = dao.addComment(comment1);
        
        // comment2 on post1 by user1
        Comment comment2 = new Comment();
        comment2.setContent("Comment 2");
        comment2.setPostingDate(LocalDateTime.now());
        comment2.setPost(post1);
        comment2.setUser(user1);
        comment2 = dao.addComment(comment2);
        
        // comment3 on post2 by user2
        Comment comment3 = new Comment();
        comment3.setContent("Comment 3");
        comment3.setPostingDate(LocalDateTime.now());
        comment3.setPost(post2);
        comment3.setUser(user2);
        comment3 = dao.addComment(comment3);
        
        // comment4 new comment on post2 by user2
        Comment comment4 = new Comment();
        comment4.setContent("Comment 4");
        comment4.setPostingDate(LocalDateTime.now());
        comment4.setPost(post2);
        comment4.setUser(user2);
        comment4 = dao.addComment(comment4);
        
        
        // get comments by post and user, validate size and contents
        // post1, user1
        List<Comment> comments = dao.getAllCommentsByPostIdAndUserId(
                        post1.getPostId(), user1.getUserId());
        
        assertEquals(1, comments.size());
        assertEquals(comment2, comments.get(0));
        
        // post1, user2
        comments = dao.getAllCommentsByPostIdAndUserId(
                        post1.getPostId(), user2.getUserId());
        
        assertEquals(1, comments.size());
        assertEquals(comment1, comments.get(0));
        
        // post2, user1
        comments = dao.getAllCommentsByPostIdAndUserId(
                post2.getPostId(), user1.getUserId());
        
        assertEquals(0, comments.size());
        
        // post2, user2
        comments = dao.getAllCommentsByPostIdAndUserId(
                post2.getPostId(), user2.getUserId());
        
        assertEquals(2, comments.size());
        assertEquals(comment3, comments.get(0));
        assertEquals(comment4, comments.get(1));
    }
    
}
