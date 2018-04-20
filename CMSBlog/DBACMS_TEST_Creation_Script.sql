drop database if exists dbacms_test;

create database dbacms_test;

use dbacms_test;

create table if not exists `role`(
	roleId int not null auto_increment,
    name varchar(20) not null,
    primary key (roleId)
);

create table if not exists `user` (
	userId int not null auto_increment,
    name varchar(100) not null,
    userName varchar(50) not null,
    password varchar(256) not null,
    email varchar(100) not null,
    -- enabled tinyint(1) not null,
    primary key (userId)
);

create table if not exists user_role (
	userRoleId int not null auto_increment,
    userId int not null,
    roleId int not null,
    primary key (userRoleId)
);

alter table user_role
	add constraint fk_UserRole_UserId foreign key (userId)
    references `user`(userId) on delete no action;
alter table user_role
	add constraint fk_UserRole_RoleId foreign key (roleId)
    references role(roleId) on delete no action;

create table if not exists `category` (
	categoryId int not null auto_increment,
    name varchar(100) not null,
    primary key (categoryId)
);

create table approval_status (
	approvalStatusId tinyint(1) primary key auto_increment,
    description varchar(20) not null
);

create table if not exists `post` (
	postId int not null auto_increment,
    headline varchar(100) not null,
    content varchar(20000) not null,
    approvalStatusId tinyint not null,
    numLikes int,
    postingDate datetime not null,
    expirationDate datetime,
    imgLink varchar(100),
    userId int not null,
    primary key (postId)
);

alter table `post`
	add constraint fk_Post_UserId foreign key (userId)
	references `user`(userId) on delete no action;
alter table `post`
	add constraint fk_Post_ApprovalStatusId foreign key (approvalStatusId)
	references approval_status(approvalStatusId) on delete no action;

create table if not exists `comment` (
	commentId int not null auto_increment,
    content varchar(10000) not null,
    `postingDate` datetime not null,
    postId int not null,
    userId int not null,
    primary key (commentId)
);

alter table `comment`
	add constraint fk_Comment_PostId foreign key (postId)
    references post(postId) on delete no action;
alter table `comment`
	add constraint fk_Comment_UserId foreign key (userId)
	references `user`(userId) on delete no action;


create table if not exists post_category (
	postCategoryId int not null auto_increment,
    postId int not null,
    categoryId int not null,
    primary key (postCategoryId)
);

alter table post_category
	add constraint fk_PostCategory_PostId foreign key (postId)
	references post(postId) on delete no action;
alter table post_category
	add constraint fk_PostCategory_CategoryId foreign key (categoryId)
    references category(categoryId) on delete no action;
    
-- insert into user (name, userName, password, email) 
-- values('TestName1', 'TestUserName1', 'TestPassword', 'TestEmail');

insert into approval_status (description)
values ('Unapproved'), ('Approved');

-- insert into post (headline, content, approvalStatusId, numLikes, postingDate, expirationDate, imgLink, userId) 
-- values ('TestHeadline', 'TestContent', 1, 0, '1990/01/01', null, 'TestImgLink', 1),
-- ('TestHeadline2', 'TestContent2', 1, 0, '1990/01/01', null, 'TestImgLink2', 1), 
-- ('TestHeadline3', 'TestContent3', 1, 0, '1990/01/01', null, 'TestImgLink3', 1);

-- insert into `comment` (content, postingDate, postId, userId) 
-- values('TestContent', '1990/02/02', 1, 1);

-- insert into role (name) 
-- values ('ROLE_ADMIN'), ('ROLE_SUBADMIN'), ('ROLE_USER'), ('ROLE_BANNED');

-- insert into user_role (userId, roleId)
-- values (1, 1);

