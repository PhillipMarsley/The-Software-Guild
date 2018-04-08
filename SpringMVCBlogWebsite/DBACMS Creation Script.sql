drop database if exists dbacms;

create database DBACMS;

use dbacms;

create table if not exists `role`(
	roleId int not null auto_increment,
    `name` varchar(20) not null,
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
	approvalStatusId tinyint primary key auto_increment,
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
    imgLink varchar(256),
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
    
insert into user (name, userName, password, email)
values('TestNameUser', 'user', '$2a$10$fSoLLwBthDUjLjD58b1AU.qCgGYtq/WX/5hjJHkYIv5EArTnsoRUm', 'UserEmail'),
('TestNameAdmin', 'admin', '$2a$10$fSoLLwBthDUjLjD58b1AU.qCgGYtq/WX/5hjJHkYIv5EArTnsoRUm', 'AdminEmail'),
('TestNameSubAdmin', 'subadmin', '$2a$10$fSoLLwBthDUjLjD58b1AU.qCgGYtq/WX/5hjJHkYIv5EArTnsoRUm', 'SubAdminEmail'),
('TestName1', 'TestUserName1', 'TestPassword1', 'TestEmail1'),
('TestName2', 'TestUserName2', 'TestPassword2', 'TestEmail2'),
('TestName3', 'TestUserName3', 'TestPassword3', 'TestEmail3'),
('TestName4', 'TestUserName4', 'TestPassword4', 'TestEmail4'),
('TestName5', 'TestUserName5', 'TestPassword5', 'TestEmail5'),
('TestName6', 'TestUserName6', 'TestPassword6', 'TestEmail6'),
('TestName7', 'TestUserName7', 'TestPassword7', 'TestEmail7'),
('TestName8', 'TestUserName8', 'TestPassword8', 'TestEmail8'),
('TestName9', 'TestUserName9', 'TestPassword9', 'TestEmail9'),
('TestName10', 'TestUserName10', 'TestPassword10', 'TestEmail10');

insert into approval_status (description)
values ('Unapproved'), ('Approved');

insert into post (headline, content, approvalStatusId, numLikes, postingDate, expirationDate, imgLink, userId) 
values ('TestHeadline', '<table style="height: 690px; width: 99.7221%; border-collapse: collapse; border-style: hidden; background-color: rgb(252, 255, 247); margin-left: auto; margin-right: auto;" data-mce-style="height: 690px; width: 99.7221%; border-collapse: collapse; border-style: hidden; background-color: #fcfff7; margin-left: auto; margin-right: auto;" class="mce-item-table" data-mce-selected="1" border="0"><tbody><tr><td style="width: 7.62638%;"><br></td><td style="width: 83.4372%;"><p style="text-align: center;" data-mce-style="text-align: center;"><img src="http://www3.pictures.zimbio.com/mp/CFF8HiJr5rrx.jpg" alt="" width="706" height="470"><br data-mce-bogus="1"></p><p style="text-align: center;" data-mce-style="text-align: center;">TestText Test Text Test Text Test Text Test Text Test Text Test Text TestText Test Text Test Text Test Text Test Text Test Text Test Text TestText Test Text Test Text Test Text Test Text Test Text Test Text TestText Test Text Test Text Test Text Test Text Test Text Test Text TestText #<span style="color: rgb(0, 0, 255);" data-mce-style="color: #0000ff;">testHashtag01 </span>Test Text Test Text Test Text Test Text Test #<span style="color: rgb(0, 0, 255);" data-mce-style="color: #0000ff;">testHashtag02 </span>TextTest Text Test Text Test Text Test Text Test Text Test Text Test TextTest Text Test Text Test Text Test Text Test Text Test Text Test TextTest Text Test Text Test Text Test Text Test </p><p style="text-align: center;" data-mce-style="text-align: center;">Text Test Text Test TextTest <span style="color: rgb(153, 51, 0);" data-mce-style="color: #993300;">#<span style="color: rgb(0, 0, 255);" data-mce-style="color: #0000ff;">testHashtag03 </span></span>TextTest Text Test Text Test Text Test Text Test Text Test Text Test TextTest Text Test Text Test Text Test Text Test Text Test Text Test TextTest Text Test Text Test Text Test Text Test Text Test Text Test TextTest Text Test Text Test Text Test Text Test Text Test Text Test TextTest Text Test Text Test Text Test Text Test Text Test Text Test TextTest Text Test Text Test Text Test Text Test Text Test Text Test TextTest Text Test Text Test Text Test Text Test Text Test Text Test TextTest Text Test Text Test Text Test Text</p><p style="text-align: center;" data-mce-style="text-align: center;">#<span style="color: rgb(0, 0, 255);" data-mce-style="color: #0000ff;">testHashtag04</span></p><p style="text-align: center;" data-mce-style="text-align: center;"><span style="color: rgb(0, 0, 255);" data-mce-style="color: #0000ff;"><img src="https://www.gamemania.be/-/media/Sites/GameMania/Dedicated%20Pages/2017/USED%20Games%20UPDATE%202017/logo_game_on.png" alt="" width="452" height="193"></span></p><p style="text-align: center;" data-mce-style="text-align: center;">TestText Test Text Test Text Test Text Test Text Test Text Test Text TestText Test Text Test Text Test Text Test Text Test Text Test Text TestText Test Text Test Text Test Text Test Text Test #<span style="color: rgb(0, 0, 255);" data-mce-style="color: #0000ff;">testHashtag05 </span>TextTest Text Test Text Test Text Test Text Test Text Test Text Test TextTest Text Test Text Test Text Test Text Test Text Test Text Test TextTest Text Test Text Test Text Test Text Test Text Test Text Test TextTest Text Test Text Test Text Test Text Test Text Test Text Test TextTest Text Test Text Test Text Test Text Test Text Test Text </p><p style="text-align: center;" data-mce-style="text-align: center;">Test TextTest Text Test <span style="color: rgb(153, 51, 0);" data-mce-style="color: #993300;">#<span style="color: rgb(0, 0, 255);" data-mce-style="color: #0000ff;">testHashtag06</span></span>Text Test Text Test Text Test Text Test Text Test Text Test Text TestText Test Text Test Text Test Text Test Text Test Text Test Text TestText Test Text Test Text Test Text Test Text Test Text Test Text TestText Test Text Test #<span style="color: rgb(0, 0, 255);" data-mce-style="color: #0000ff;">testHashtag07 </span>TextTest Text Test Text Test Text Test Text Test Text Test Text Test TextTest Text Test Text Test Text Test Text Test Text Test Text Test TextTest Text Test Text Test Text Test Text Test Text Test Text Test TextTest Text Test Text Test Text Test Text Test Text Test Text Test TextTest Text</p><p style="text-align: center;" data-mce-style="text-align: center;">#<span style="color: rgb(0, 0, 255);" data-mce-style="color: #0000ff;">testHashTag08</span></p><p style="text-align: center;" data-mce-style="text-align: center;"><span style="color: rgb(0, 0, 255);" data-mce-style="color: #0000ff;"><br data-mce-bogus="1"></span></p><p style="text-align: center;" data-mce-style="text-align: center;"><span style="color: rgb(0, 0, 255);" data-mce-style="color: #0000ff;"></span></p></td><td style="width: 8.93627%;"><br></td></tr></tbody></table><div id="mceTextAreaTags"><br data-mce-bogus="1"></div>'
, 1, 0, '1990/01/01', null, 'http://assets1.ignimgs.com/thumbs/userUploaded/2017/5/22/overwatchoffenseheroes1280-1495486376056.jpg', 1),
('TestHeadline2', 'TestContent2', 1, 0, '1990/01/01', null, 'https://assets.hardwarezone.com/img/2017/08/wc3-ptr.jpg', 1), 
('TestHeadline3', 'TestContent3', 1, 0, '1990/01/01', null, 'https://vignette.wikia.nocookie.net/plok/images/b/b3/Cover.png/revision/latest?cb=20130205062010', 1),
('TestHeadline4', 'TestContent4', 1, 0, '1990/01/01', null, 'https://i.ytimg.com/vi/hGStLP55zBs/maxresdefault.jpg', 1),
('TestHeadline5', 'TestContent5', 1, 0, '1990/01/01', null, 'https://i.ytimg.com/vi/TGBra4Mv8x8/maxresdefault.jpg', 1),
('TestHeadline6', 'TestContent6', 1, 0, '1990/01/01', null, 'https://vignette.wikia.nocookie.net/trials/images/e/ea/EvolutionScreenshot12.png/revision/latest?cb=20130626185646', 1);

insert into category (name)
value ('static');

insert into post_category (postId, categoryId)
values (2, 1), (3, 1), (4, 1);

insert into `comment` (content, postingDate, postId, userId) 
values
('TestContentPost1a', '1990/02/02', 1, 1),
('TestContentPost1b', '1990/02/02', 1, 1),
('TestContentPost2a', '1990/02/02', 2, 1),
('TestContentPost2b', '1990/02/02', 2, 1),
('TestContentPost3a', '1990/02/02', 3, 1);

insert into role (name) 
values ('ROLE_USER'), ('ROLE_ADMIN'), ('ROLE_SUBADMIN');

insert into user_role (userId, roleId)
values (1, 1), (2, 1), (2, 2), (2, 3), (3, 1), (3, 3),
(4, 1),
(5, 1),
(6, 1),
(7, 1),
(8, 1),
(9, 1),
(10, 1);
