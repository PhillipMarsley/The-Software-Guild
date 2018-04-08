var url = 'http://localhost:8080/DBACMS';
var imgurl;
var postDiv = document.getElementById('post');
var commentArray = [];
var clickedId;
var clickedPost;

//==============================================================================
//      Event Listeners
//==============================================================================
//var titleSearch = document.getElementById('titleSearch'),
//        categorySearch = document.getElementById('categorySearch');
//
//function addEventListeners() {
//    titleSearch.addEventListener('input', function () {
//        listenerSearch();
//    });
//
//    titleSearch.addEventListener('keydown', function (event) {
//        if (event.keyCode === 13) {
//            getAllUsersBySearch(searchInputString);
//        }
//    });
//
//    categorySearch.addEventListener('input', function () {
//        listenerSearch();
//    });
//
//    categorySearch.addEventListener('keydown', function (event) {
//        if (event.keyCode === 13) {
//            getAllUsersBySearch(searchInputString);
//        }
//    });
//}

$(document).ready(function () {

    loadAllComments();
    loadAllPosts();


});

//contains posts
function loadAllPosts() {
    var blogBricksDiv = $('#bricks');
    var blogPostDiv = $('#post');

    $.ajax({
        type: 'GET',
        url: url + '/12posts',
        dataType: 'JSON',
        success: function (data) {
            console.log('SUCCESSFUL loadAllPosts()');
            console.log(data);
            var brickContent = '';
            $.each(data, function (index, post) {
//                var brickContent = '';

                if (index % 3 === 0) {
                    brickContent += '<div class="row">';
                }
                brickContent += '<div class="blogBrick col" style="margin:auto;background-image:url(' + post.imgLink + ')" id="' + post.postId + '">';
                brickContent += '<p>postId: ' + post.postId + '</p><br/>';
                brickContent += '<p>headline: ' + post.headline + '</p><br/>';
                brickContent += '<p>postingDate: ' + post.postingDate + '</p><br/>';
                brickContent += '<p>user: ' + post.user + '</p><br/>';
                brickContent += '<p>categories: ' + post.categories + '</p><br/>';
                brickContent += '</div>';
                if (index % 3 === 2) {
                    brickContent += '</div>';
                }
                if (index >= data.length - 1) {
                    brickContent += '</div>';
                }
//                blogBricksDiv.append(brickContent);

                var postContent = '<div class="blogPost col-md-12" id="post' + post.postId + '" hidden>';
                postContent += '<button class="backButton btn btn-default pull-right">Back</button>';
                postContent += '<h2>' + post.headline + '</h2>';
                postContent += '<div class="postContent">' + post.content + '</div>';
                postContent += '</div>';
                blogPostDiv.append(postContent);

            });
            blogBricksDiv.append(brickContent);

            //add onClick to each blogBrick which hides the bricks and shows
            //the individual blog post + comments
            $('.blogBrick').click(function () {
                clickedId = $(this).attr('id');
                clickedPost = $('#post' + clickedId + '');

                blogBricksDiv.hide();
                blogPostDiv.show();

//                var commentBox = '<div class="textArea">';
////                commentBox += '<form method="POST">';
//                commentBox += '<textarea class="mceTextArea"></textarea><br/>';
//                commentBox += '<button id="submit">Submit Comment</button><br/>';
////                commentBox += '</form>';
//                commentBox += '</div>';
//                clickedPost.append(commentBox);

//                $.each(commentArray, function (index, comment) {
//                    var commentContent = '<div class="blogComment">';
//                    commentContent += 'commentContent: ' + comment.content + '<br/>';
//                    commentContent += '</div>';
//
//                    if (comment.post.postId == clickedId) {
//                        clickedPost.append(commentContent);
//                    }
//                });

                clickedPost.show();

                $('.backButton').click(function () {
                    clickedPost.children('.blogComment').remove();
                    clickedPost.children('.textArea').remove();
                    blogPostDiv.hide();
                    clickedPost.hide();
                    blogBricksDiv.show();
                });
            });
            $('.blogBrick').css({
                'border-radius': '5px',
                'margin': '5px',
//                'width': '200px',
//                'height': '200px',
                'overflow': 'hidden',
                'padding-left': '0px',
                'color': 'white',
                'background-color': 'black'
            });
        },
        error: function (xhr) {
            console.log(xhr);
            console.log('ERROR loadAllPosts(): ' + xhr.status + ' | ' + xhr.statusText);
        }
    });
}

function loadAllComments() {

    $.ajax({
        type: 'GET',
        url: url + '/comments',
        dataType: 'JSON',
        success: function (data) {
            console.log('SUCCESS loadAllComments()');
            $.each(data, function (index, comment) {
                commentArray.push(comment);
            });
        },
        error: function (xhr) {
            console.log(xhr);
            console.log('ERROR loadAllComments(): ' + xhr.status + ' | ' + xhr.statusText);
        }
    });
}

