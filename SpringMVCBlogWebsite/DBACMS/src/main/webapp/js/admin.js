var url = 'http://localhost:8080/DBACMS';

//==============================================================================
//      Event Listners
//==============================================================================
var searchInput = document.getElementById('searchInput'),
        showAsGridSpan = document.getElementById('showAsGridSpan'),
        showAsListSpan = document.getElementById('showAsListSpan'),
        statusRollSelect = document.getElementById('statusRollSelect'),
        orderBySelect = document.getElementById('orderBySelect'),
        statusRollOptionIndex = statusRollSelect.selectedIndex,
        orderByOptionIndex = orderBySelect.selectedIndex,
        contentData = [];

function addEventListners() {
    showAsGridSpan.addEventListener('click', function () {
        console.log('showAsGridSpan Clicked!');
        gridDisplaySelected = true;
        listDisplaySelected = false;
        allUsers.innerHTML = displayGridAllUsers;
        allPosts.innerHTML = displayGridAllPosts;
        allComments.innerHTML = displayGridAllComments;
    });

    showAsListSpan.addEventListener('click', function () {
        console.log('showAsListSpan Clicked!');
        gridDisplaySelected = false;
        listDisplaySelected = true;
        allUsers.innerHTML = displayListAllUsers;
        allPosts.innerHTML = displayListAllPosts;
        allComments.innerHTML = displayListAllComments;
    });

    searchInput.addEventListener('focus', function () {
        if (searchInput.value !== '') {
            document.getElementById('dropdownContent').style.display = 'block';
        }
    });

    searchInput.addEventListener('blur', function () {
        document.getElementById('dropdownContent').style.display = 'none';
    });

    searchInput.addEventListener('input', function () {
        listenerSearch();
    });

    searchInput.addEventListener('keydown', function (event) {
        if (event.keyCode === 13) {
            getAllUsersBySearch(searchInputString);
        }
    });

    statusRollSelect.addEventListener('change', function () {
        statusRollOptionIndex = statusRollSelect.selectedIndex;
        console.log('statusRollSelect ' + statusRollSelect.selectedIndex);
    });

    orderBySelect.addEventListener('change', function () {
        orderByOptionIndex = orderBySelect.selectedIndex;
        console.log('orderBySelect ' + statusRollSelect.selectedIndex);
    });
}

//==============================================================================
//      Event Listner Helpers
//==============================================================================
var userNames = '',
        searchInputString = '';

function listenerSearch() {
    if (searchInput.value !== '') {
        document.getElementById('dropdownContent').style.display = 'block';
    } else {
        document.getElementById('dropdownContent').style.display = 'none';
    }

    searchInputString = searchInput.value;
    userNames = '<ul class="list-unstyled">';
    for (var i = 0; i < userList.length; i++) {
        var item = userList[i];

        if (item.toLowerCase().indexOf(searchInputString.toLowerCase()) !== -1) {
            userNames += '<li>' + item + '</li>';
        }
    }
    userNames += '</ul>';
    document.getElementById('dropdownContent').innerHTML = userNames;
}

//==============================================================================
//      Ajax Calls
//==============================================================================
var unapprovedPostsDiv = document.getElementById('unapprovedPostsDiv'),
        userTotalSpan = document.getElementById('userTotalSpan'),
        userList = [];

function getAllUsers() {
    $.ajax({
        type: 'GET',
        url: url + '/users',
        dataType: 'JSON',
        success: function (data) {
            console.log('SUCCESS getAllUsers()');
            console.log('data');

            $.each(data, function (index, user) {
                userList.push(user.name);
            });

            buildListDisplayAllUsers(data);
            allUsers.innerHTML = displayListAllUsers;

            buildGridDisplayAllUsers(data);
            allUsers.innerHTML = displayGridAllUsers;
        },
        error: function (xhr) {
            console.log(xhr);
            console.log('ERROR getAllUsers(): ' + xhr.status + ' | ' + xhr.statusText);
        }
    });
}

function getAllUsersBySearch(searchString) {
    var dataArray = [];

    $.ajax({
        type: 'GET',
        url: url + '/users',
        dataType: 'JSON',
        success: function (data) {
            console.log('SUCCESS getAllUsers()');

            $.each(data, function (index, user) {
                if (user.name.toLowerCase().indexOf(searchString.toLowerCase()) !== -1) {
                    dataArray.push(data[index]);
                }
            });

            buildListDisplayAllUsers(dataArray);
            allUsers.innerHTML = displayListAllUsers;

            buildGridDisplayAllUsers(dataArray);
            allUsers.innerHTML = displayGridAllUsers;
        },
        error: function (xhr) {
            console.log(xhr);
            console.log('ERROR getAllUsers(): ' + xhr.status + ' | ' + xhr.statusText);
        }
    });
}

function getUserCount() {
    $.ajax({
        type: 'GET',
        url: url + '/users',
        dataType: 'JSON',
        success: function (data) {
            console.log('SUCCESS getUserCount()');
            userTotalSpan.innerHTML = data.length;
        },
        error: function (xhr) {
            console.log(xhr);
            console.log('ERROR getUserCount(): ' + xhr.status + ' | ' + xhr.statusText);
        }
    });
}

function getSingleUser(index) {
    index += 1;
    var singleUserHtml = '';
    //    allUsersDiv.style.display = 'none';

    $.ajax({
        type: 'GET',
        url: url + '/user/' + index,
        dataType: 'JSON',
        success: function (data) {
            console.log('SUCCESS getSingleUser()');
            console.log(data);

            singleUserHtml += '<div class="col" onclick="getAllPostsByUser(' + index + ')">';
            singleUserHtml += 'INDEX --- ' + data.index + '</br>';
            singleUserHtml += 'userId: ' + data.userId + '</br>';
            singleUserHtml += 'name: ' + data.name + '</br>';
            singleUserHtml += 'userName: ' + data.userName + '</br>';
            singleUserHtml += 'password: ' + data.password + '</br>';
            singleUserHtml += 'email: ' + data.email + '</br>'
            for (var i = 0; i < data.roles.length; i++) {
                singleUserHtml += 'roleId: ' + data.roles[i].roleId + '</br>';
                singleUserHtml += 'roleName: ' + data.roles[i].name + '</br>';
            }
            singleUserHtml += '</div>';

            //int       userId
            //String    name
            //String    userName
            //String    email
            //String    password
            //ArrayList roles
            //  int       roleId
            //  String    name

            document.getElementById('selectedUser').innerHTML = singleUserHtml;

            if (gridDisplaySelected === true) {
                buildListDisplayAllPosts();
                buildListDisplayAllComments();

                buildGridDisplayAllPosts();
                buildGridDisplayAllComments();

                allPosts.innerHTML = displayListAllPosts;
                allComments.innerHTML = displayListAllComments;

                allPosts.innerHTML = displayGridAllPosts;
                allComments.innerHTML = displayGridAllComments;
            }

            if (listDisplaySelected === true) {
                buildGridDisplayAllPosts();
                buildGridDisplayAllComments();

                buildListDisplayAllPosts();
                buildListDisplayAllComments();

                allPosts.innerHTML = displayGridAllPosts;
                allComments.innerHTML = displayGridAllComments;

                allPosts.innerHTML = displayListAllPosts;
                allComments.innerHTML = displayListAllComments;
            }

        },
        error: function (xhr) {
            console.log(xhr);
            console.log('ERROR getUserCount(): ' + xhr.status + ' | ' + xhr.statusText);
        }
    });
}

function getAllUsersByLevelUser() {
//    $.ajax({
//        type: 'GET',
//        url: url + '/users',
//        dataType: 'JSON',
//        success: function (data) {
//            console.log('SUCCESS getAllUsersByLevelUser()');
//        },
//        error: function (xhr) {
//            console.log(xhr);
//            console.log('ERROR getAllUsersByLevelUser(): ' + xhr.status + ' | ' + xhr.statusText);
//        }
//    });
}

function getAllUsersByLevelUserBanned() {
//    $.ajax({
//        type: 'GET',
//        url: url + '/users',
//        dataType: 'JSON',
//        success: function (data) {
//            console.log('SUCCESS getAllUsersByLevelUserBanned()');
//        },
//        error: function (xhr) {
//            console.log(xhr);
//            console.log('ERROR getAllUsersByLevelUserBanned(): ' + xhr.status + ' | ' + xhr.statusText);
//        }
//    });
}

function getAllUsersByLevelSubAdmin() {
//        $.ajax({
//        type: 'GET',
//        url: url + '/users',
//        dataType: 'JSON',
//        success: function (data) {
//            console.log('SUCCESS getAllUsersByLevelSubAdmin()');
//        },
//        error: function (xhr) {
//            console.log(xhr);
//            console.log('ERROR getAllUsersByLevelSubAdmin(): ' + xhr.status + ' | ' + xhr.statusText);
//        }
//    });
}

function getAllUsersByLevelAdmin() {
//        $.ajax({
//        type: 'GET',
//        url: url + '/users',
//        dataType: 'JSON',
//        success: function (data) {
//            console.log('SUCCESS getAllUsersByLevelAdmin()');
//        },
//        error: function (xhr) {
//            console.log(xhr);
//            console.log('ERROR getAllUsersByLevelAdmin(): ' + xhr.status + ' | ' + xhr.statusText);
//        }
//    });
}

function getAllUsersByJoinDateNewest() {
//        $.ajax({
//        type: 'GET',
//        url: url + '/users',
//        dataType: 'JSON',
//        success: function (data) {
//            console.log('SUCCESS getAllUsersByJoinDateNewest()');
//        },
//        error: function (xhr) {
//            console.log(xhr);
//            console.log('ERROR getAllUsersByJoinDateNewest(): ' + xhr.status + ' | ' + xhr.statusText);
//        }
//    });
}

function getAllUsersByJoinDateOldest() {
//        $.ajax({
//        type: 'GET',
//        url: url + '/users',
//        dataType: 'JSON',
//        success: function (data) {
//            console.log('SUCCESS getAllUsersByJoinDateOldest()');
//        },
//        error: function (xhr) {
//            console.log(xhr);
//            console.log('ERROR getAllUsersByJoinDateOldest(): ' + xhr.status + ' | ' + xhr.statusText);
//        }
//    });
}

function getUnapprovedPosts() {
    $.ajax({
        type: 'GET',
        url: url + '/getUnapprovedPosts',
        dataType: 'JSON',
        success: function (data) {
            console.log('SUCCESS getUnapprovedPosts()');

            var displayGridUnapprovedPosts = '';

            $.each(data, function (index, post) {
                if (index % 3 === 0) {
                    displayGridUnapprovedPosts += '<div class="row">';
                }

                contentData.push(post.content);

                displayGridUnapprovedPosts += '<div id="' + index + '" class="col unapprovedPostsGrid">';
                displayGridUnapprovedPosts += '<span id="' + post.postId + '" class="approvePost glyphicon glyphicon-ok" onclick="approvePost(this.id)"></span>'
                displayGridUnapprovedPosts += 'MODULUS 3 INDEX --- ' + (index % 3) + '</br>';
                displayGridUnapprovedPosts += 'INDEX --- ' + index + '</br>';
                displayGridUnapprovedPosts += 'postId: ' + post.postId + '</br>';
                displayGridUnapprovedPosts += 'headLine: <span id="' + index + '" onclick="showUnapprovedPostContent(this.id)"> ' + post.headline + '</span></br>';
//                displayGridUnapprovedPosts += 'content: ' + post.content + '</br>';
                displayGridUnapprovedPosts += 'approvalStatus: ' + post.approvalStatues + '</br>';
                displayGridUnapprovedPosts += 'numLikes: ' + post.numLikes + '</br>';
                displayGridUnapprovedPosts += 'postingDate: ' + post.postingDate + '</br>';
                displayGridUnapprovedPosts += 'expirationDate: ' + post.expirationDate + '</br>';
                displayGridUnapprovedPosts += 'imgLink: ' + post.imgLink + '</br>';
                displayGridUnapprovedPosts += 'user: ' + post.user + '</br>';
                displayGridUnapprovedPosts += 'categories: ' + post.categories + '</br>';
                displayGridUnapprovedPosts += '</div>';

                if (index % 3 === 2) {
                    displayGridUnapprovedPosts += '</div>';
                }

                if (index % 3 === 0 && index >= data.length - 1) {
                    displayGridUnapprovedPosts += '</div>';
                }
            });

            unapprovedPostsDiv.innerHTML = displayGridUnapprovedPosts;
        },
        error: function (xhr) {
            console.log(xhr);
            console.log('ERROR getUnapprovedPosts(): ' + xhr.status + ' | ' + xhr.statusText);
        }
    });
}

function getAllPostsByUser(index) {
    index += 1;

//    $.ajax({
//        type: 'GET',
//        url: url + '/posts/user/' + index,
//        dataType: 'JSON',
//        success: function (data) {
//            console.log('SUCCESS getUserCount()');
//            console.log(data);
//
//              FILL IN WITH METHOD CALL
//            
//            document.getElementById('selectedUserPosts').innerHtml = allPostsSingleUser;
//        },
//        error: function (xhr) {
//            console.log(xhr);
//            console.log('ERROR getUserCount(): ' + xhr.status + ' | ' + xhr.statusText);
//        }
//    });
}

function getAllCommentsByUser(index) {
    index += 1;

//    $.ajax({
//        type: 'GET',
//        url: url + '/posts/user/' + index,
//        dataType: 'JSON',
//        success: function (data) {
//            console.log('SUCCESS getUserCount()');
//            console.log(data);
//
//              FILL IN WITH METHOD CALL
//            
//            document.getElementById('selectedUserPosts').innerHtml = allPostsSingleUser;
//        },
//        error: function (xhr) {
//            console.log(xhr);
//            console.log('ERROR getUserCount(): ' + xhr.status + ' | ' + xhr.statusText);
//        }
//    });
}

//==============================================================================
//      Ajax Helpers
//==============================================================================

var users = document.getElementById('users'),
        posts = document.getElementById('posts'),
        comments = document.getElementById('comments'),
        allUsers = document.getElementById('allUsers'),
        allPosts = document.getElementById('allPosts'),
        allComments = document.getElementById('allComments'),
        gridDisplaySelected = true,
        listDisplaySelected = false,
        displayGridAllUsers,
        displayListAllUsers,
        displayGridAllPosts,
        displayListAllPosts,
        displayGridAllComments,
        displayListAllComments;

function approvePost(id) {

    $.ajax({
        type: 'POST',
        url: url + '/approvePost/' + id,
        dataType: 'JSON',
        success: function (data) {
            console.log('Success ApprovePost');
            console.log(data);
            
            getUnapprovedPosts();
                
        },
        error: function (xhr) {
            console.log(xhr);
            console.log('ERROR approvePost(): ' + xhr.status + ' | ' + xhr.statusText);
        }
    });
}

function showUnapprovedPostContent(id) {
    console.log('upPost clicked ' + id);

    var win = window.open();
    var htmlContent = contentData[id];
    $(win.document.body).html(htmlContent);
    win.focus();
}

function buildGridDisplayAllUsers(data) {
    displayGridAllUsers = '';

    $.each(data, function (index, user) {
        if (index % 3 === 0) {
            displayGridAllUsers += '<div class="row">';
        }

        displayGridAllUsers += '<div class="col allUsersGrid" onclick="getSingleUser(' + index + ')">';
        displayGridAllUsers += 'MODULUS 3 INDEX --- ' + (index % 3) + '</br>';
        displayGridAllUsers += 'INDEX --- ' + index + '</br>';
        displayGridAllUsers += 'userId: ' + user.userId + '</br>';
        displayGridAllUsers += 'name: ' + user.name + '</br>';
        displayGridAllUsers += 'userName: ' + user.userName + '</br>';
        displayGridAllUsers += 'password: ' + user.password + '</br>';
        displayGridAllUsers += 'email: ' + user.email + '</br>';
        for (var i = 0; i < user.roles.length; i++) {
            displayGridAllUsers += 'roleId: ' + user.roles[i].roleId + '</br>';
            displayGridAllUsers += 'roleName: ' + user.roles[i].name + '</br>';
        }
        displayGridAllUsers += '</div>';

        if (index % 3 === 2) {
            displayGridAllUsers += '</div>';
        }

        if (index >= data.length - 1) {
            displayGridAllUsers += '</div>';
        }

        //int       userId
        //String    name
        //String    userName
        //String    email
        //String    password
        //ArrayList roles
        //  int       roleId
        //  String    name
    });
}

function buildListDisplayAllUsers(data) {
    displayListAllUsers = '';

    $.each(data, function (index, user) {
        displayListAllUsers += '<div class="row allUsersList" onclick="getSingleUser(' + index + ')">';
        displayListAllUsers += 'INDEX --- ' + index + '</br>';
        displayListAllUsers += 'userId: ' + user.userId + '</br>';
        displayListAllUsers += 'name: ' + user.name + '</br>';
        displayListAllUsers += 'userName: ' + user.userName + '</br>';
        displayListAllUsers += 'password: ' + user.password + '</br>';
        displayListAllUsers += 'email: ' + user.email + '</br>'
        for (var i = 0; i < user.roles.length; i++) {
            displayListAllUsers += 'roleId: ' + user.roles[i].roleId + '</br>';
            displayListAllUsers += 'roleName: ' + user.roles[i].name + '</br>';
        }
        displayListAllUsers += '</div>';

        //int       userId
        //String    name
        //String    userName
        //String    email
        //String    password
        //ArrayList roles
        //  int       roleId
        //  String    name
    });
}

function buildGridDisplayAllPosts(data) {
    displayGridAllPosts = '';
    var index = 'grid post';

//    $.each(data, function (index, post) {
//        if (index % 3 === 0) {
//            displayGridAllPosts += '<div class="row">';
//        }

//        displayGridAllPosts += '<div class="col allPostsGrid" >';
//        displayGridAllPosts += 'MODULUS 3 INDEX --- ' + (index % 3) + '</br>';
//        displayGridAllPosts += 'INDEX --- ' + index + '</br>';
//        displayGridAllPosts += 'postId: ' + post.postId + '</br>';
//        displayGridAllPosts += 'headLine: ' + post.headline + '</br>';
//        displayGridAllPosts += 'content: ' + post.content + '</br>';
//        displayGridAllPosts += 'approvalStatus: ' + post.approvalStatues + '</br>';
//        displayGridAllPosts += 'numLikes: ' + post.numLikes + '</br>';
//        displayGridAllPosts += 'postingDate: ' + post.postingDate + '</br>';
//        displayGridAllPosts += 'expirationDate: ' + post.expirationDate + '</br>';
//        displayGridAllPosts += 'imgLink: ' + post.imgLink + '</br>';
//        displayGridAllPosts += 'user: ' + post.user + '</br>';
//        displayGridAllPosts += 'categories: ' + post.categories + '</br>';
//        displayGridAllPosts += '</div>';

    displayGridAllPosts += '<div class="col allPostsGrid">';
    displayGridAllPosts += 'INDEX --- ' + index + '</br>'
    displayGridAllPosts += 'INDEX --- ' + index + '</br>'
    displayGridAllPosts += 'INDEX --- ' + index + '</br>'
    displayGridAllPosts += 'INDEX --- ' + index + '</br>'
    displayGridAllPosts += 'INDEX --- ' + index + '</br>'
    displayGridAllPosts += '</div>';

    displayGridAllPosts += '<div class="col allPostsGrid">';
    displayGridAllPosts += 'INDEX --- ' + index + '</br>'
    displayGridAllPosts += 'INDEX --- ' + index + '</br>'
    displayGridAllPosts += 'INDEX --- ' + index + '</br>'
    displayGridAllPosts += 'INDEX --- ' + index + '</br>'
    displayGridAllPosts += 'INDEX --- ' + index + '</br>'
    displayGridAllPosts += '</div>';

    displayGridAllPosts += '<div class="col allPostsGrid">';
    displayGridAllPosts += 'INDEX --- ' + index + '</br>'
    displayGridAllPosts += 'INDEX --- ' + index + '</br>'
    displayGridAllPosts += 'INDEX --- ' + index + '</br>'
    displayGridAllPosts += 'INDEX --- ' + index + '</br>'
    displayGridAllPosts += 'INDEX --- ' + index + '</br>'
    displayGridAllPosts += '</div>';

//        if (index % 3 === 2) {
//            displayGridAllPosts += '</div>';
//        }
//
//        if (index % 3 === 0 && index >= data.length - 1) {
//            displayGridAllPosts += '</div>';
//        }

    //int           postId
    //String        headline
    //String        content
    //Object        approvalStatus
    //int           numLikes
    //LocalDateTime postingDate
    //LocalDateTime expirationDate
    //String        imgLink
    //Object        user
    //ArrayList     categories
//    });
}

function buildListDisplayAllPosts(data) {
    displayListAllPosts = '';
    var index = 'list post';

    $.each(data, function (index, post) {
        displayListAllPosts += '<div class="row allPostsList" >';
        displayListAllPosts += 'INDEX --- ' + index + '</br>';
        displayListAllPosts += 'postId: ' + post.postId + '</br>';
        displayListAllPosts += 'headLine: ' + post.headline + '</br>';
        displayListAllPosts += 'content: ' + post.content + '</br>';
        displayListAllPosts += 'approvalStatus: ' + post.approvalStatues + '</br>';
        displayListAllPosts += 'numLikes: ' + post.numLikes + '</br>';
        displayListAllPosts += 'postingDate: ' + post.postingDate + '</br>';
        displayListAllPosts += 'expirationDate: ' + post.expirationDate + '</br>';
        displayListAllPosts += 'imgLink: ' + post.imgLink + '</br>';
        displayListAllPosts += 'user: ' + post.user + '</br>';
        displayListAllPosts += 'categories: ' + post.categories + '</br>';
        displayListAllPosts += '</div>';
    });

//    displayListAllPosts += '<div class="row allPostsList">';
//    displayListAllPosts += 'INDEX --- ' + index + '</br>'
//    displayListAllPosts += 'INDEX --- ' + index + '</br>'
//    displayListAllPosts += 'INDEX --- ' + index + '</br>'
//    displayListAllPosts += 'INDEX --- ' + index + '</br>'
//    displayListAllPosts += 'INDEX --- ' + index + '</br>'
//    displayListAllPosts += '</div>';
//
//    displayListAllPosts += '<div class="row allPostsList">';
//    displayListAllPosts += 'INDEX --- ' + index + '</br>'
//    displayListAllPosts += 'INDEX --- ' + index + '</br>'
//    displayListAllPosts += 'INDEX --- ' + index + '</br>'
//    displayListAllPosts += 'INDEX --- ' + index + '</br>'
//    displayListAllPosts += 'INDEX --- ' + index + '</br>'
//    displayListAllPosts += '</div>';
//
//    displayListAllPosts += '<div class="row allPostsList">';
//    displayListAllPosts += 'INDEX --- ' + index + '</br>'
//    displayListAllPosts += 'INDEX --- ' + index + '</br>'
//    displayListAllPosts += 'INDEX --- ' + index + '</br>'
//    displayListAllPosts += 'INDEX --- ' + index + '</br>'
//    displayListAllPosts += 'INDEX --- ' + index + '</br>'
//    displayListAllPosts += '</div>';

    //int           postId
    //String        headline
    //String        content
    //Object        approvalStatus
    //int           numLikes
    //LocalDateTime postingDate
    //LocalDateTime expirationDate
    //String        imgLink
    //Object        user
    //ArrayList     categories
//    });
}

function buildGridDisplayAllComments(data) {
    displayGridAllComments = '';
    var index = ' grid comment';

//    $.each(data, function (index, comment) {
//        if (index % 3 === 0) {
//            displayGridAllComments += '<div class="row">';
//        }
//
//        displayGridAllComments += '<div class="col allCommentsGrid">';
//        displayGridAllComments += 'MODULUS 3 INDEX --- ' + (index % 3) + '</br>';
//        displayGridAllComments += 'INDEX --- ' + index + '</br>';
//        displayGridAllComments += 'commentId: ' + comment.commentId + '<br/>';
//        displayGridAllComments += 'content: ' + comment.content + '<br/>';
//        displayGridAllComments += 'postingDate: ' + comment.postingDate + '<br/>';
//        displayGridAllComments += 'post: ' + comment.post + '<br/>';
//        displayGridAllComments += 'user: ' + comment.user + '<br/>';
//        displayGridAllComments += '</div>';

    displayGridAllComments += '<div class="col allCommentsGrid">';
    displayGridAllComments += '<span class="deleteComment glyphicon glyphicon-remove"></span>';
    displayGridAllComments += 'INDEX --- ' + index + '</br>'
    displayGridAllComments += 'INDEX --- ' + index + '</br>'
    displayGridAllComments += 'INDEX --- ' + index + '</br>'
    displayGridAllComments += 'INDEX --- ' + index + '</br>'
    displayGridAllComments += 'INDEX --- ' + index + '</br>'
    displayGridAllComments += '</div>';

    displayGridAllComments += '<div class="col allCommentsGrid">';
    displayGridAllComments += '<span class="deleteComment glyphicon glyphicon-remove"></span>';
    displayGridAllComments += 'INDEX --- ' + index + '</br>'
    displayGridAllComments += 'INDEX --- ' + index + '</br>'
    displayGridAllComments += 'INDEX --- ' + index + '</br>'
    displayGridAllComments += 'INDEX --- ' + index + '</br>'
    displayGridAllComments += 'INDEX --- ' + index + '</br>'
    displayGridAllComments += '</div>';

    displayGridAllComments += '<div class="col allCommentsGrid">';
    displayGridAllComments += '<span class="deleteComment glyphicon glyphicon-remove"></span>';
    displayGridAllComments += 'INDEX --- ' + index + '</br>'
    displayGridAllComments += 'INDEX --- ' + index + '</br>'
    displayGridAllComments += 'INDEX --- ' + index + '</br>'
    displayGridAllComments += 'INDEX --- ' + index + '</br>'
    displayGridAllComments += 'INDEX --- ' + index + '</br>'
    displayGridAllComments += '</div>';

//        if (index % 3 === 2) {
//            displayGridAllComments += '</div>';
//        }
//
//        if (index % 3 === 0 && index >= data.length - 1) {
//            displayGridAllComments += '</div>';
//        }

    //int           commentId
    //String        content
    //LocalDateTime postingDate
    //Object        post
    //Object        user
//    });
}

function buildListDisplayAllComments(data) {
    displayListAllComments = '';
    var index = 'list comment';

//    $.each(data, function (index, comment) {
//        displayListAllComments += '<div class="row allCommentsList">';
//        displayListAllComments += 'INDEX --- ' + index + '</br>';
//        displayListAllComments += 'commentId: ' + comment.commentId + '<br/>';
//        displayListAllComments += 'content: ' + comment.content + '<br/>';
//        displayListAllComments += 'postingDate: ' + comment.postingDate + '<br/>';
//        displayListAllComments += 'post: ' + comment.post + '<br/>';
//        displayListAllComments += 'user: ' + comment.user + '<br/>';
//        displayListAllComments += '<div>';

    displayListAllComments += '<div class="row allCommentsList">';
    displayListAllComments += '<span class="deleteComment glyphicon glyphicon-remove"></span>';
    displayListAllComments += 'INDEX --- ' + index + '</br>'
    displayListAllComments += 'INDEX --- ' + index + '</br>'
    displayListAllComments += 'INDEX --- ' + index + '</br>'
    displayListAllComments += 'INDEX --- ' + index + '</br>'
    displayListAllComments += 'INDEX --- ' + index + '</br>'
    displayListAllComments += '</div>';

    displayListAllComments += '<div class="row allCommentsList">';
    displayListAllComments += '<span class="deleteComment glyphicon glyphicon-remove"></span>';
    displayListAllComments += 'INDEX --- ' + index + '</br>'
    displayListAllComments += 'INDEX --- ' + index + '</br>'
    displayListAllComments += 'INDEX --- ' + index + '</br>'
    displayListAllComments += 'INDEX --- ' + index + '</br>'
    displayListAllComments += 'INDEX --- ' + index + '</br>'
    displayListAllComments += '</div>';

    displayListAllComments += '<div class="row allCommentsList">';
    displayListAllComments += '<span class="deleteComment glyphicon glyphicon-remove"></span>';
    displayListAllComments += 'INDEX --- ' + index + '</br>'
    displayListAllComments += 'INDEX --- ' + index + '</br>'
    displayListAllComments += 'INDEX --- ' + index + '</br>'
    displayListAllComments += 'INDEX --- ' + index + '</br>'
    displayListAllComments += 'INDEX --- ' + index + '</br>'
    displayListAllComments += '</div>';

    //int           commentId
    //String        content
    //LocalDateTime postingDate
    //Object        post
    //Object        user
//    });
}

//==============================================================================
//      Run
//==============================================================================
window.onload = function () {
    if (!window.location.hash) {
        window.location = window.location + '#loaded';
        window.location.reload();
    }
}

$(document).ready(function () {
    getUnapprovedPosts();
    getUserCount();
    getAllUsers();

    //has to load after the 'populate' functions
    addEventListners();
});