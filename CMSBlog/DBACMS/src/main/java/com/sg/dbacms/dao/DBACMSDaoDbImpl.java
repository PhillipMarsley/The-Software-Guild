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
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Alex
 */
//@Component
public class DBACMSDaoDbImpl implements DBACMSDao {

    private JdbcTemplate jt;

    public void setJdbcTemplate(JdbcTemplate jt) {
        this.jt = jt;
    }

    // ****************    User queries    *****************************
    private static final String INSERT_USER
            = "insert into user (name, userName, password, email) "
            + "values (?, ?, ?, ?)";

    private static final String DELETE_USER
            = "delete from user where userId = ?";

    private static final String UPDATE_USER
            = "update user "
            + "set name = ?, userName = ?, password = ?, email = ? "
            + "where userId = ?";

    private static final String SELECT_USER_BY_ID
            = "select * from user where UserId = ?";

    private static final String SELECT_ALL_USERS
            = "select * from user";

    private static final String SELECT_ALL_USERS_BY_ROLE_ID
            = "select * from user u "
            + "join user_role ur on u.userId = ur.userId "
            + "where ur.roleId = ?";

    // ****************    Role queries    *****************************
    private static final String INSERT_ROLE
            = "insert into role (Name) values (?)";

    private static final String DELETE_ROLE
            = "delete from role where RoleId = ?";

    private static final String UPDATE_ROLE
            = "update role "
            + "set Name = ? "
            + "where RoleId = ?";

    private static final String SELECT_ROLE_BY_ID
            = "select * from role where RoleId = ?";

    private static final String SELECT_ALL_ROLES
            = "select * from role";

    private static final String SELECT_ROLES_BY_USER_ID
            = "select roleId from user_role where userId = ?";

    // user_role table queries
    private static final String INSERT_USER_ROLE
            = "insert into user_role (userId, roleId) "
            + "values (?, ?)";

    private static final String DELETE_USER_ROLE_BY_ROLE_ID
            = "delete from user_role where roleId = ?";

    private static final String DELETE_USER_ROLE_BY_USER_ID
            = "delete from user_role where userId = ?";

    // ****************    Post queries    *****************************
    private static final String INSERT_POST
            = "insert into post "
            + "(headline, content, approvalStatusId, numLikes, postingDate, "
            + "expirationDate, imgLink, userId) "
            + "values (?, ?, ?, ?, ?, ?, ?, ?)";

    private static final String DELETE_POST
            = "delete from post where postId = ?";

    private static final String DELETE_ALL_POSTS_BY_USER_ID
            = "delete from post where userId = ?";

    private static final String UPDATE_POST
            = "update post "
            + "set headline = ?, content = ?, approvalStatusId = ?, numLikes = ?, "
            + "postingDate = ?, expirationDate = ?, imgLink = ?, userId = ? "
            + "where postId = ?";

    private static final String SELECT_POST_BY_ID
            = "select * from post where postId = ?";

    private static final String SELECT_ALL_POSTS_BY_USER_ID
            = "select * from post where userId = ?";

    private static final String SELECT_ALL_STATIC_POSTS
            = "select p.* from post p join post_category pc on pc.postId = p.postId "
            + "join category c on c.categoryId = pc.categoryId where c.name = 'static'";

    private static final String SELECT_ALL_POSTS
            = "select * from post";

    private static final String SELECT_APPROVAL_ID_BY_POST_ID
            = "select approvalStatusId "
            + "from post "
            + "where postId = ?";

    private static final String SELECT_USER_ID_BY_POST_ID
            = "select userId from post where postId = ?";

    private static final String SELECT_POSTS_BY_APPROVAL_STATUS_ID
            = "select * from post "
            + "where approvalStatusId = ?";

    private static final String SELECT_THREE_MOST_RECENT_POSTS
            = "select * from post "
            + "order by postingDate ASC "
            + "limit 3";

    private static final String SELECT_TWELVE_MOST_RECENT_POSTS
            = "select * from post "
            + "order by postingDate ASC "
            + "limit 12";
    
    private static final String SELECT_TWELVE_MOST_RECENT_APPROVED_POSTS 
            = "select * from post p "
            + "join approval_status app on p.approvalStatusId = app.approvalStatusId "
            + "where app.description = 'Approved' "
            + "order by postingDate ASC "
            + "limit 12";

    private static final String SELECT_RECENT_POSTS_AFTER_FIRST_TWELVE
            = "select * from post "
            + "order by postingDate ASC "
            + "limit 10000 offset 12";

    // ****************    Comment queries    *****************************
    private static final String INSERT_COMMENT
            = "insert into comment "
            + "(content, postingDate, postId, userId) "
            + "values (?, ?, ?, ?)";

    private static final String DELETE_COMMENT
            = "delete from comment where commentId = ?";

    private static final String DELETE_ALL_COMMENTS_BY_USER_ID
            = "delete from comment where userId = ?";

    private static final String DELETE_ALL_COMMENTS_BY_POST_ID
            = "delete from comment where postId = ?";

    private static final String UPDATE_COMMENT
            = "update comment "
            + "set content = ?, postingDate = ?, postId = ?, userId = ? "
            + "where commentId = ?";

    private static final String SELECT_ALL_COMMENTS
            = "select * from comment";

    private static final String SELECT_COMMENT_BY_ID
            = "select * from comment where commentId = ?";

    private static final String SELECT_COMMENTS_BY_USER_ID
            = "select * from comment where userId = ?";

    private static final String SELECT_COMMENTS_BY_POST_ID
            = "select * from comment where postId = ?";

    private static final String SELECT_ALL_POST_COMMENTS_BY_USER_ID
            = "select c.commentId "
            + "from comment c "
            + "join post p on c.userId = p.userId "
            + "where p.userId = ?";

    private static final String SELECT_POST_ID_BY_COMMENT_ID
            = "select postId from comment where commentId = ?";

    private static final String SELECT_USER_ID_BY_COMMENT_ID
            = "select userId from comment where commentId = ?";

    private static final String SELECT_COMMENTS_BY_POST_ID_AND_USER_ID
            = "select * from comment "
            + "where postId = ? and userId = ?";

    // ****************    Category queries    *****************************
    private static final String INSERT_CATEGORY
            = "insert into category (name) values (?)";

    private static final String DELETE_CATEGORY
            = "delete from category where categoryId = ?";

    private static final String UPDATE_CATEGORY
            = "update category "
            + "set name = ? "
            + "where categoryId = ?";

    private static final String SELECT_CATEGORY_BY_ID
            = "select * from category where categoryId = ?";

    private static final String SELECT_ALL_CATEGORIES
            = "select * from category";

    // ****************    Approval Status queries    **********************
    private static final String INSERT_APPROVAL_STATUS
            = "insert into approval_status (description) "
            + "values (?)";

    private static final String DELETE_APPROVAL_STATUS
            = "delete from approval_status where approvalStatusId = ?";

    private static final String UPDATE_APPROVAL_STATUS
            = "update approval_status "
            + "set description = ? "
            + "where approvalStatusId = ?";

    private static final String SELECT_APPROVAL_STATUS_BY_ID
            = "select * from approval_status where approvalStatusId = ?";

    private static final String SELECT_ALL_APPROVAL_STATUS
            = "select * from approval_status";
    
    private static final String SELECT_APPROVAL_STATUS_BY_DESCRIPTION 
            = "select * from approval_status where description like ?";

    // ****************    Bridge Table queries   *************************
    private static final String INSERT_POST_CATEGORY
            = "insert into post_category (postId, categoryId) "
            + "values (?, ?)";

    private static final String SELECT_CATEGORY_ID_BY_POST_ID
            = "select categoryId "
            + "from post_category "
            + "where postId = ?";

    private static final String DELETE_POST_CATEGORY_BY_POST_ID
            = "delete from post_category where postId = ?";

    private static final String DELETE_POST_CATEGORY_BY_CATEGORY_ID
            = "delete from post_category where categoryId = ?";

    // ************************************************************************
    // ************************************************************************
    // ***********************************************************************
    // ***********************  User Methods  ********************************
    // ***********************************************************************
    @Override
    @Transactional
    public User addUser(User user) throws DBACMSPersistenceException {
        try {
            jt.update(INSERT_USER,
                    user.getName(),
                    user.getUserName(),
                    user.getPassword(),
                    user.getEmail());

            user.setUserId(jt.queryForObject("select LAST_INSERT_ID()",
                    Integer.class));

            // add user_role table entries
            insertUserRoleEntries(user);

            return user;
        } catch (DataAccessException e) {
            throw new DBACMSPersistenceException(e.getMessage());
        }

    }

    private void insertUserRoleEntries(User user) throws DBACMSPersistenceException {
        try {
            List<Role> roles = user.getRoles();

            if (roles != null) {
                for (Role currentRole : roles) {
                    jt.update(INSERT_USER_ROLE,
                            user.getUserId(),
                            currentRole.getRoleId());
                }
            }
        } catch (DataAccessException e) {
            throw new DBACMSPersistenceException(e.getMessage());
        }
    }

    @Override
    @Transactional
    public User getUserById(int userId) throws DBACMSPersistenceException {
        try {
            User user = jt.queryForObject(SELECT_USER_BY_ID,
                    new UserMapper(),
                    userId);

            associateRolesWithUser(user);

            return user;
        } catch (DataAccessException e) {
            throw new DBACMSPersistenceException(e.getMessage());
        }
    }

    @Override
    @Transactional
    public List<User> getAllUsers() throws DBACMSPersistenceException {
        try {
            List<User> users = jt.query(SELECT_ALL_USERS, new UserMapper());

            if (users.size() > 0) {
                for (User currentUser : users) {
                    this.associateRolesWithUser(currentUser);
                }
            }

            return users;
        } catch (DataAccessException e) {
            throw new DBACMSPersistenceException(e.getMessage());
        }
    }

    @Override
    @Transactional
    public List<User> getAllUsersByRoleId(int roleId)
            throws DBACMSPersistenceException {
        try {
            List<User> users = jt.query(SELECT_ALL_USERS_BY_ROLE_ID,
                    new UserMapper(),
                    roleId);

            if (users.size() > 0) {
                for (User currentUser : users) {
                    this.associateRolesWithUser(currentUser);
                }
            }

            return users;

        } catch (DataAccessException e) {
            throw new DBACMSPersistenceException(e.getMessage());
        }
    }

    private void associateRolesWithUser(User user) {
        List<Role> roles = new ArrayList<>();

        List<Integer> roleIds = jt.query(SELECT_ROLES_BY_USER_ID,
                new RoleIdMapper(),
                user.getUserId());

        if (roleIds.size() > 0) {
            for (Integer currentId : roleIds) {
                roles.add(jt.queryForObject(SELECT_ROLE_BY_ID,
                        new RoleMapper(),
                        currentId));
            }
        }

        user.setRoles(roles);
    }

    @Override
    @Transactional
    public int deleteUser(int userId) throws DBACMSPersistenceException {
        try {
            // delete all comments by user
            this.deleteAllCommentsByUserId(userId);

            // delete all posts by user
            this.deleteAllPostsByUserId(userId);

            // delete user_role entries
            jt.update(DELETE_USER_ROLE_BY_USER_ID, userId);

            // delete user
            return jt.update(DELETE_USER, userId);
        } catch (DataAccessException e) {
            throw new DBACMSPersistenceException(e.getMessage());
        }
    }

    @Override
    @Transactional
    public User updateUser(User user) throws DBACMSPersistenceException {
        try {
            jt.update(UPDATE_USER,
                    user.getName(),
                    user.getUserName(),
                    user.getPassword(),
                    user.getEmail(),
                    user.getUserId());

            // update roles
            List<Role> roles = user.getRoles();

            if (roles.size() > 0) {
                for (Role currentRole : roles) {
                    this.updateRole(currentRole);
                }
            }

            return this.getUserById(user.getUserId());
        } catch (DataAccessException e) {
            throw new DBACMSPersistenceException(e.getMessage());
        }

    }

    // ***********************************************************************
    // ***********************  Role Methods  ********************************
    // ***********************************************************************
    @Override
    @Transactional
    public Role addRole(Role role) throws DBACMSPersistenceException {
        try {
            jt.update(INSERT_ROLE,
                    role.getName());

            role.setRoleId(jt.queryForObject("select LAST_INSERT_ID()",
                    Integer.class));

            return role;
        } catch (DataAccessException e) {
            throw new DBACMSPersistenceException(e.getMessage());
        }
    }

    @Override
    @Transactional
    public int deleteRole(int roleId) throws DBACMSPersistenceException {
        try {
            // Delete user_role table entries
            jt.update(DELETE_USER_ROLE_BY_ROLE_ID, roleId);

            return jt.update(DELETE_ROLE, roleId);
        } catch (DataAccessException e) {
            throw new DBACMSPersistenceException(e.getMessage());
        }
    }

    @Override
    @Transactional
    public Role updateRole(Role role) throws DBACMSPersistenceException {
        try {
            jt.update(UPDATE_ROLE,
                    role.getName(),
                    role.getRoleId());

            return getRoleById(role.getRoleId());
        } catch (DataAccessException e) {
            throw new DBACMSPersistenceException(e.getMessage());
        }
    }

    @Override
    public Role getRoleById(int roleId) throws DBACMSPersistenceException {
        try {
            return jt.queryForObject(SELECT_ROLE_BY_ID,
                    new RoleMapper(),
                    roleId);
        } catch (DataAccessException e) {
            throw new DBACMSPersistenceException(e.getMessage());
        }
    }

    @Override
    public List<Role> getAllRoles() throws DBACMSPersistenceException {
        try {
            return jt.query(SELECT_ALL_ROLES, new RoleMapper());
        } catch (DataAccessException e) {
            throw new DBACMSPersistenceException(e.getMessage());
        }
    }

    // ***********************************************************************
    // ***********************  Post Methods  ********************************
    // ***********************************************************************
    @Override
    @Transactional
    public Post addPost(Post post) throws DBACMSPersistenceException {
        try {
            String expDateString = null;

            if (post.getExpirationDate() != null) {
                expDateString = post.getExpirationDate().toString();
            }

            jt.update(INSERT_POST,
                    post.getHeadline(),
                    post.getContent(),
                    post.getApprovalStatus().getApprovalStatusId(),
                    post.getNumLikes(),
                    post.getPostingDate().toString(),
                    expDateString,
                    post.getImgLink(),
                    post.getUser().getUserId());

            post.setPostId(jt.queryForObject("select LAST_INSERT_ID()",
                    Integer.class));

            insertCategories(post.getCategories());

            insertPostCategoryEntries(post);

            return post;

        } catch (DataAccessException e) {
            throw new DBACMSPersistenceException(e.getMessage());
        }
    }

    private void insertCategories(List<Category> categories)
            throws DBACMSPersistenceException {
        try {
            for (Category currentCat : categories) {
                this.addCategory(currentCat);
            }
        } catch (DataAccessException e) {
            throw new DBACMSPersistenceException(e.getMessage());
        }
    }

    private void insertPostCategoryEntries(Post post)
            throws DBACMSPersistenceException {
        List<Category> categories = post.getCategories();

        if (categories.size() > 0) {
            for (Category currentCat : categories) {
                try {
                    jt.update(INSERT_POST_CATEGORY,
                            post.getPostId(),
                            currentCat.getCategoryId());
                } catch (DataAccessException e) {
                    throw new DBACMSPersistenceException(e.getMessage());
                }
            }
        }
    }

    @Override
    @Transactional
    public int deletePost(int postId) throws DBACMSPersistenceException {
        try {
            // delete comments for post
            this.deleteAllCommentsByPostId(postId);

            // delete post_category entries
            this.deletePostCategoryByPostId(postId);

            // delete post
            return jt.update(DELETE_POST, postId);
        } catch (DataAccessException e) {
            throw new DBACMSPersistenceException(e.getMessage());
        }
    }

    @Override
    @Transactional
    public int deleteAllPostsByUserId(int userId) throws DBACMSPersistenceException {
        try {
            // delete comments for user
            this.deleteAllCommentsByUserId(userId);

            // delete post_category entries
            List<Post> posts = this.getAllPostsByUserId(userId);
            for (Post currentPost : posts) {
                this.deletePostCategoryByPostId(currentPost.getPostId());
            }

            // delete posts for user
            return jt.update(DELETE_ALL_POSTS_BY_USER_ID, userId);

        } catch (DataAccessException e) {
            throw new DBACMSPersistenceException(e.getMessage());
        }
    }

    private int deletePostCategoryByPostId(int postId)
            throws DBACMSPersistenceException {
        try {
            return jt.update(DELETE_POST_CATEGORY_BY_POST_ID, postId);
        } catch (DataAccessException e) {
            throw new DBACMSPersistenceException(e.getMessage());
        }
    }

    @Override
    @Transactional
    public Post updatePost(Post post) throws DBACMSPersistenceException {
        try {
            String expDateString = null;

            if (post.getExpirationDate() != null) {
                expDateString = post.getExpirationDate().toString();
            }
            jt.update(UPDATE_POST,
                    post.getHeadline(),
                    post.getContent(),
                    post.getApprovalStatus().getApprovalStatusId(),
                    post.getNumLikes(),
                    post.getPostingDate().toString(),
                    expDateString,
                    post.getImgLink(),
                    post.getUser().getUserId(),
                    post.getPostId());

            return this.getPostById(post.getPostId());
        } catch (DataAccessException e) {
            throw new DBACMSPersistenceException(e.getMessage());
        }
    }

    @Override
    @Transactional
    public Post getPostById(int postId) throws DBACMSPersistenceException {
        try {
            Post post = jt.queryForObject(SELECT_POST_BY_ID,
                    new PostMapper(),
                    postId);

            // Add ApprovalStatus object
            associateApprovalStatusWithPost(post);

            // Add User object
            associateUserWithPost(post);

            // Add Category List
            associateCategoriesWithPost(post);

            return post;
        } catch (DataAccessException e) {
            throw new DBACMSPersistenceException(e.getMessage());
        }
    }

    @Override
    @Transactional
    public List<Post> getAllPostsByUserId(int userId) throws DBACMSPersistenceException {
        try {
            List<Post> posts = jt.query(SELECT_ALL_POSTS_BY_USER_ID,
                    new PostMapper(),
                    userId);

            if (posts.size() > 0) {
                for (Post currentPost : posts) {
                    this.associateApprovalStatusWithPost(currentPost);
                    this.associateUserWithPost(currentPost);
                    this.associateCategoriesWithPost(currentPost);
                }
            }

            return posts;

        } catch (DataAccessException e) {
            throw new DBACMSPersistenceException(e.getMessage());
        }
    }

    @Override
    @Transactional
    public List<Post> getAllPostsByApprovalStatusId(int statusId)
            throws DBACMSPersistenceException {

        try {
            List<Post> posts = jt.query(SELECT_POSTS_BY_APPROVAL_STATUS_ID,
                    new PostMapper(),
                    statusId);

            if (posts.size() > 0) {
                for (Post currentPost : posts) {
                    this.associateApprovalStatusWithPost(currentPost);
                    this.associateUserWithPost(currentPost);
                    this.associateCategoriesWithPost(currentPost);
                }
            }

            return posts;

        } catch (DataAccessException e) {
            throw new DBACMSPersistenceException(e.getMessage());
        }
    }

    @Override
    @Transactional
    public List<Post> getAllPostsByStaticCategory() throws DBACMSPersistenceException {
        try {
            List<Post> posts = jt.query(SELECT_ALL_STATIC_POSTS, new PostMapper());
            if (posts.size() > 0) {
                for (Post currentPost : posts) {
                    this.associateApprovalStatusWithPost(currentPost);
                    this.associateUserWithPost(currentPost);
                    this.associateCategoriesWithPost(currentPost);
                }
            }

            return posts;

        } catch (DataAccessException e) {
            throw new DBACMSPersistenceException(e.getMessage());
        }
    }

    @Override
    @Transactional
    public List<Post> getAllPosts() throws DBACMSPersistenceException {
        try {
            List<Post> posts = jt.query(SELECT_ALL_POSTS,
                    new PostMapper());

            if (posts.size() > 0) {
                for (Post currentPost : posts) {
                    this.associateApprovalStatusWithPost(currentPost);
                    this.associateUserWithPost(currentPost);
                    this.associateCategoriesWithPost(currentPost);
                }
            }

            return posts;

        } catch (DataAccessException e) {
            throw new DBACMSPersistenceException(e.getMessage());
        }
    }

    @Override
    @Transactional
    public List<Post> getThreeMostRecentPosts() throws DBACMSPersistenceException {
        try {
            List<Post> posts = jt.query(SELECT_THREE_MOST_RECENT_POSTS,
                    new PostMapper());

            if (posts.size() > 0) {
                for (Post currentPost : posts) {
                    this.associateApprovalStatusWithPost(currentPost);
                    this.associateUserWithPost(currentPost);
                    this.associateCategoriesWithPost(currentPost);
                }
            }

            return posts;

        } catch (DataAccessException e) {
            throw new DBACMSPersistenceException(e.getMessage());
        }
    }

    @Override
    @Transactional
    public List<Post> getTwelveMostRecentPosts() throws DBACMSPersistenceException {
        try {
            List<Post> posts = jt.query(SELECT_TWELVE_MOST_RECENT_POSTS,
                    new PostMapper());

            if (posts.size() > 0) {
                for (Post currentPost : posts) {
                    this.associateApprovalStatusWithPost(currentPost);
                    this.associateUserWithPost(currentPost);
                    this.associateCategoriesWithPost(currentPost);
                }
            }

            return posts;

        } catch (DataAccessException e) {
            throw new DBACMSPersistenceException(e.getMessage());
        }
    }
    
    @Override
    @Transactional
    public List<Post> getTwelveMostRecentApprovedPosts() throws DBACMSPersistenceException {
        try {
            List<Post> posts = jt.query(SELECT_TWELVE_MOST_RECENT_APPROVED_POSTS,
                    new PostMapper());

            if (posts.size() > 0) {
                for (Post currentPost : posts) {
                    this.associateApprovalStatusWithPost(currentPost);
                    this.associateUserWithPost(currentPost);
                    this.associateCategoriesWithPost(currentPost);
                }
            }

            return posts;

        } catch (DataAccessException e) {
            throw new DBACMSPersistenceException(e.getMessage());
        }
    }

    @Override
    @Transactional
    public List<Post> getMostRecentPostsAfterFirstTwelve()
            throws DBACMSPersistenceException {
        try {
            List<Post> posts = jt.query(SELECT_RECENT_POSTS_AFTER_FIRST_TWELVE,
                    new PostMapper());

            if (posts.size() > 0) {
                for (Post currentPost : posts) {
                    this.associateApprovalStatusWithPost(currentPost);
                    this.associateUserWithPost(currentPost);
                    this.associateCategoriesWithPost(currentPost);
                }
            }

            return posts;

        } catch (DataAccessException e) {
            throw new DBACMSPersistenceException(e.getMessage());
        }
    }

    private void associateApprovalStatusWithPost(Post post)
            throws DBACMSPersistenceException {
        try {
            int approvalStatusId
                    = jt.queryForObject(SELECT_APPROVAL_ID_BY_POST_ID,
                            new ApprovalStatusIdMapper(),
                            post.getPostId());

            ApprovalStatus as = this.getApprovalStatusById(approvalStatusId);

            post.setApprovalStatus(as);
        } catch (DataAccessException e) {
            throw new DBACMSPersistenceException(e.getMessage());
        }

    }

    private void associateUserWithPost(Post post) throws DBACMSPersistenceException {
        try {
            int userId = jt.queryForObject(SELECT_USER_ID_BY_POST_ID,
                    new UserIdMapper(),
                    post.getPostId());

            User user = this.getUserById(userId);

            post.setUser(user);
        } catch (DataAccessException e) {
            throw new DBACMSPersistenceException(e.getMessage());
        }
    }

    private void associateCategoriesWithPost(Post post)
            throws DBACMSPersistenceException {
        try {
            List<Category> categories = new ArrayList<>();

            List<Integer> categoryIds
                    = jt.query(SELECT_CATEGORY_ID_BY_POST_ID,
                            new CategoryIdMapper(),
                            post.getPostId());

            if (categoryIds.size() > 0) {
                for (Integer currentId : categoryIds) {
                    categories.add(jt.queryForObject(
                            SELECT_CATEGORY_BY_ID,
                            new CategoryMapper(),
                            currentId));
                }
            }

            post.setCategories(categories);
        } catch (DataAccessException e) {
            throw new DBACMSPersistenceException(e.getMessage());
        }
    }

    // ***********************************************************************
    // ***********************  Comment Methods  *****************************
    // ***********************************************************************
    @Override
    @Transactional
    public Comment addComment(Comment comment) throws DBACMSPersistenceException {
        try {
            jt.update(INSERT_COMMENT,
                    comment.getContent(),
                    comment.getPostingDate().toString(),
                    comment.getPost().getPostId(),
                    comment.getUser().getUserId());

            comment.setCommentId(jt.queryForObject("select LAST_INSERT_ID()",
                    Integer.class));

            return comment;
        } catch (DataAccessException e) {
            throw new DBACMSPersistenceException(e.getMessage());
        }
    }

    @Override
    public int deleteComment(int commentId) throws DBACMSPersistenceException {
        try {
            return jt.update(DELETE_COMMENT, commentId);
        } catch (DataAccessException e) {
            throw new DBACMSPersistenceException(e.getMessage());
        }
    }

    @Override
    public int deleteAllCommentsByUserId(int userId) throws DBACMSPersistenceException {
        try {
            return jt.update(DELETE_ALL_COMMENTS_BY_USER_ID, userId);
        } catch (DataAccessException e) {
            throw new DBACMSPersistenceException(e.getMessage());
        }
    }

    @Override
    public int deleteAllCommentsByPostId(int postId) throws DBACMSPersistenceException {
        try {
            return jt.update(DELETE_ALL_COMMENTS_BY_POST_ID, postId);
        } catch (DataAccessException e) {
            throw new DBACMSPersistenceException(e.getMessage());
        }
    }

    @Override
    @Transactional
    public Comment updateComment(Comment comment) throws DBACMSPersistenceException {
        try {
            jt.update(UPDATE_COMMENT,
                    comment.getContent(),
                    comment.getPostingDate().toString(),
                    comment.getPost().getPostId(),
                    comment.getUser().getUserId(),
                    comment.getCommentId());

            return this.getCommentById(comment.getCommentId());
        } catch (DataAccessException e) {
            throw new DBACMSPersistenceException(e.getMessage());
        }
    }

    @Override
    @Transactional
    public Comment getCommentById(int commentId) throws DBACMSPersistenceException {
        try {
            Comment comment = jt.queryForObject(SELECT_COMMENT_BY_ID,
                    new CommentMapper(),
                    commentId);

            // associate post object with comment
            associatePostWithComment(comment);

            // associate user object with comment
            associateUserWithComment(comment);

            return comment;
        } catch (DataAccessException e) {
            throw new DBACMSPersistenceException(e.getMessage());
        }
    }

    @Override
    @Transactional
    public List<Comment> getAllCommentsByPostId(int postId) throws DBACMSPersistenceException {
        try {
            List<Comment> comments = jt.query(SELECT_COMMENTS_BY_POST_ID,
                    new CommentMapper(),
                    postId);

            if (comments.size() > 0) {
                for (Comment currentComment : comments) {
                    associatePostWithComment(currentComment);
                    associateUserWithComment(currentComment);
                }
            }

            return comments;
        } catch (DataAccessException e) {
            throw new DBACMSPersistenceException(e.getMessage());
        }
    }

    @Override
    @Transactional
    public List<Comment> getAllCommentsByUserId(int userId) throws DBACMSPersistenceException {
        try {
            List<Comment> comments = jt.query(SELECT_COMMENTS_BY_USER_ID,
                    new CommentMapper(),
                    userId);

            if (comments.size() > 0) {
                for (Comment currentComment : comments) {
                    associatePostWithComment(currentComment);
                    associateUserWithComment(currentComment);
                }
            }

            return comments;
        } catch (DataAccessException e) {
            throw new DBACMSPersistenceException(e.getMessage());
        }
    }

    @Override
    @Transactional
    public List<Comment> getAllCommentsByPostIdAndUserId(int postId, int userId)
            throws DBACMSPersistenceException {
        try {
            List<Comment> comments
                    = jt.query(SELECT_COMMENTS_BY_POST_ID_AND_USER_ID,
                            new CommentMapper(),
                            postId,
                            userId);

            if (comments.size() > 0) {
                for (Comment currentComment : comments) {
                    associatePostWithComment(currentComment);
                    associateUserWithComment(currentComment);
                }
            }

            return comments;

        } catch (DataAccessException e) {
            throw new DBACMSPersistenceException(e.getMessage());
        }
    }

    @Override
    @Transactional
    public List<Comment> getAllComments() throws DBACMSPersistenceException {
        try {
            List<Comment> comments
                    = jt.query(SELECT_ALL_COMMENTS, new CommentMapper());

            if (comments.size() > 0) {
                for (Comment currentComment : comments) {
                    associatePostWithComment(currentComment);
                    associateUserWithComment(currentComment);
                }
            }

            return comments;
        } catch (DataAccessException e) {
            throw new DBACMSPersistenceException(e.getMessage());
        }
    }

    private void associatePostWithComment(Comment comment) throws DBACMSPersistenceException {
        try {
            int postId = jt.queryForObject(SELECT_POST_ID_BY_COMMENT_ID,
                    new PostIdMapper(),
                    comment.getCommentId());

            Post post = this.getPostById(postId);

            comment.setPost(post);
        } catch (DataAccessException e) {
            throw new DBACMSPersistenceException(e.getMessage());
        }
    }

    private void associateUserWithComment(Comment comment) throws DBACMSPersistenceException {
        try {
            int userId = jt.queryForObject(SELECT_USER_ID_BY_COMMENT_ID,
                    new UserIdMapper(),
                    comment.getCommentId());

            User user = this.getUserById(userId);

            comment.setUser(user);
        } catch (DataAccessException e) {
            throw new DBACMSPersistenceException(e.getMessage());
        }
    }

    // ***********************************************************************
    // ***********************  Category Methods  ****************************
    // ***********************************************************************
    @Override
    @Transactional
    public Category addCategory(Category category) throws DBACMSPersistenceException {
        try {
            jt.update(INSERT_CATEGORY, category.getName());

            category.setCategoryId(jt.queryForObject("select LAST_INSERT_ID()",
                    Integer.class));

            return category;
        } catch (DataAccessException e) {
            throw new DBACMSPersistenceException(e.getMessage());
        }
    }

    @Override
    @Transactional
    public int deleteCategory(int categoryId) throws DBACMSPersistenceException {
        try {
            // delete post_category entries
            jt.update(DELETE_POST_CATEGORY_BY_CATEGORY_ID, categoryId);

            return jt.update(DELETE_CATEGORY, categoryId);
        } catch (DataAccessException e) {
            throw new DBACMSPersistenceException(e.getMessage());
        }
    }

    @Override
    @Transactional
    public Category updateCategory(Category category)
            throws DBACMSPersistenceException {
        try {
            jt.update(UPDATE_CATEGORY,
                    category.getName(),
                    category.getCategoryId());

            return this.getCategorById(category.getCategoryId());
        } catch (DataAccessException e) {
            throw new DBACMSPersistenceException(e.getMessage());
        }
    }

    @Override
    public Category getCategorById(int categoryId) throws DBACMSPersistenceException {
        try {
            return jt.queryForObject(SELECT_CATEGORY_BY_ID,
                    new CategoryMapper(),
                    categoryId);
        } catch (DataAccessException e) {
            throw new DBACMSPersistenceException(e.getMessage());
        }
    }

    @Override
    public List<Category> getAllCategories() throws DBACMSPersistenceException {
        try {
            return jt.query(SELECT_ALL_CATEGORIES,
                    new CategoryMapper());
        } catch (DataAccessException e) {
            throw new DBACMSPersistenceException(e.getMessage());
        }
    }

    // ***********************************************************************
    // ***********************  Approval Status Methods  *********************
    // ***********************************************************************
    @Override
    @Transactional
    public ApprovalStatus addApprovalStatus(ApprovalStatus status)
            throws DBACMSPersistenceException {
        try {
            jt.update(INSERT_APPROVAL_STATUS,
                    status.getDescription());

            status.setApprovalStatusId(
                    jt.queryForObject("select LAST_INSERT_ID()",
                            Integer.class));

            return status;
        } catch (DataAccessException e) {
            throw new DBACMSPersistenceException(e.getMessage());
        }
    }

    @Override
    public int deleteApprovalStatus(int id) throws DBACMSPersistenceException {
        try {
            return jt.update(DELETE_APPROVAL_STATUS, id);
        } catch (DataAccessException e) {
            throw new DBACMSPersistenceException(e.getMessage());
        }
    }

    @Override
    @Transactional
    public ApprovalStatus updateApprovalStatus(ApprovalStatus status)
            throws DBACMSPersistenceException {
        try {
            jt.update(UPDATE_APPROVAL_STATUS,
                    status.getDescription(),
                    status.getApprovalStatusId());

            return this.getApprovalStatusById(status.getApprovalStatusId());
        } catch (DataAccessException e) {
            throw new DBACMSPersistenceException(e.getMessage());
        }
    }

    @Override
    public ApprovalStatus getApprovalStatusById(int id) throws DBACMSPersistenceException {
        try {
            return jt.queryForObject(SELECT_APPROVAL_STATUS_BY_ID,
                    new ApprovalStatusMapper(),
                    id);
        } catch (DataAccessException e) {
            throw new DBACMSPersistenceException(e.getMessage());
        }
    }

    @Override
    public List<ApprovalStatus> getAllApprovalStatus() throws DBACMSPersistenceException {
        try {
            return jt.query(SELECT_ALL_APPROVAL_STATUS,
                    new ApprovalStatusMapper());
        } catch (DataAccessException e) {
            throw new DBACMSPersistenceException(e.getMessage());
        }
    }
    
    @Override
    public ApprovalStatus getApprovalStatusByDescription(String description) 
            throws DBACMSPersistenceException {
        
            try {
                return jt.queryForObject(SELECT_APPROVAL_STATUS_BY_DESCRIPTION, 
                        new ApprovalStatusMapper(), 
                        description);
            } catch (DataAccessException e) {
                throw new DBACMSPersistenceException(e.getMessage());
            }
    }

    // ***********************************************************************
    // ***********************  Mappers  *************************************
    // ***********************************************************************
    private static final class UserMapper implements RowMapper<User> {

        @Override
        public User mapRow(ResultSet rs, int i) throws SQLException {
            User user = new User();
            user.setUserId(rs.getInt("userId"));
            user.setName(rs.getString("name"));
            user.setUserName(rs.getString("userName"));
            user.setEmail(rs.getString("email"));
            user.setPassword(rs.getString("password"));
            return user;
        }

    }

    private static final class UserIdMapper implements RowMapper<Integer> {

        @Override
        public Integer mapRow(ResultSet rs, int i) throws SQLException {
            return rs.getInt("userId");
        }

    }

    private static final class RoleMapper implements RowMapper<Role> {

        @Override
        public Role mapRow(ResultSet rs, int i) throws SQLException {
            Role role = new Role();
            role.setRoleId(rs.getInt("roleId"));
            role.setName(rs.getString("name"));
            return role;
        }

    }

    private static final class RoleIdMapper implements RowMapper<Integer> {

        @Override
        public Integer mapRow(ResultSet rs, int i) throws SQLException {
            Integer integer;
            integer = rs.getInt("roleId");
            return integer;
        }

    }

    private static final class PostMapper implements RowMapper<Post> {

        @Override
        public Post mapRow(ResultSet rs, int i) throws SQLException {
            Post post = new Post();
            post.setPostId(rs.getInt("postId"));
            post.setHeadline(rs.getString("headline"));
            post.setContent(rs.getString("content"));
            post.setNumLikes(rs.getInt("numLikes"));
            post.setPostingDate(rs.getTimestamp("postingDate").toLocalDateTime());
            if (rs.getTimestamp("expirationDate") != null) {
                post.setExpirationDate(rs.getTimestamp("expirationDate").toLocalDateTime());
            } else {
                post.setExpirationDate(null);
            }
            post.setImgLink(rs.getString("imgLink"));
            return post;
        }

    }

    private static final class PostIdMapper implements RowMapper<Integer> {

        @Override
        public Integer mapRow(ResultSet rs, int i) throws SQLException {
            return rs.getInt("postId");
        }

    }

    private static final class CommentMapper implements RowMapper<Comment> {

        @Override
        public Comment mapRow(ResultSet rs, int i) throws SQLException {
            Comment comment = new Comment();
            comment.setCommentId(rs.getInt("commentId"));
            comment.setContent(rs.getString("content"));
            comment.setPostingDate(rs.getTimestamp("postingDate").toLocalDateTime());
            return comment;
        }

    }

    private static final class CategoryMapper implements RowMapper<Category> {

        @Override
        public Category mapRow(ResultSet rs, int i) throws SQLException {
            Category cat = new Category();
            cat.setCategoryId(rs.getInt("categoryId"));
            cat.setName(rs.getString("name"));
            return cat;
        }

    }

    private static final class CategoryIdMapper implements RowMapper<Integer> {

        @Override
        public Integer mapRow(ResultSet rs, int i) throws SQLException {
            return rs.getInt("categoryId");
        }

    }

    private static final class ApprovalStatusMapper implements RowMapper<ApprovalStatus> {

        @Override
        public ApprovalStatus mapRow(ResultSet rs, int i) throws SQLException {
            ApprovalStatus status = new ApprovalStatus();
            status.setApprovalStatusId(rs.getInt("approvalStatusId"));
            status.setDescription(rs.getString("description"));
            return status;
        }

    }

    private static final class ApprovalStatusIdMapper implements RowMapper<Integer> {

        @Override
        public Integer mapRow(ResultSet rs, int i) throws SQLException {
            return rs.getInt("approvalStatusId");
        }

    }

}