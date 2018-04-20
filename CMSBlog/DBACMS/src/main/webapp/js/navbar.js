//==============================================================================
//      Navigation Bar Loader
//==============================================================================
var url = 'http://localhost:8080/DBACMS/';

var navbar = document.getElementById('navbar');
navbar.innerHTML = '';

var xhr = new XMLHttpRequest();
xhr.open('GET', url + 'navbar.html', true);
xhr.responseType = '';
xhr.send();

xhr.addEventListener('load', loadNavBar);


//==============================================================================
//      Functions Bar
//==============================================================================
function loadNavBar() {
    navbar.innerHTML = this.responseText;
    var navUl = navbar.firstChild;

    grabStaticPages(navUl).then(function () {

        var windowLoc = window
                .location
                .pathname
                .replace('/DBACMS/', '')
                .replace('.html', '')
                .replace(' ', '')
                .toLowerCase();
        var windowLocLength = windowLoc.length;

        var windowUrl = document.URL;
        var key = windowUrl
                .substring(windowUrl.lastIndexOf('?') + 1)
                .toLowerCase();

        var nodeList = navbar.firstElementChild.children;
        var currentNode;

        for (var i = 0; i < nodeList.length; i++) {
            if (nodeList[i].nodeType === 1) {

                currentNode = nodeList[i]
                        .innerText
                        .split(' ')
                        .join('')
                        .toLowerCase()

                if (windowLoc === '') {
                    windowLoc = 'home';
                }

                if (windowLoc.length <= windowLocLength && !key.includes('http')) {
                    windowLoc += key;
                }

                if (currentNode === windowLoc || currentNode.includes(key)) {
                    document.getElementById(nodeList[i].id).className = 'active';

                    var headline = document.getElementById(nodeList[i].id).innerText.trim().toLowerCase();

                    getStaticPageCotent(headline).then(function() {
                        document.getElementById('content').innerHTML = postContentHtml;
                    });
                } else {
                    document.getElementById(nodeList[i].id).className = '';
                }


            }
        }
    });
}

function grabStaticPages(listData) {
    return $.ajax({
        type: 'GET',
        url: url + '/posts/static',
        dataType: 'JSON',
        success: function (data) {
            console.log('SUCCESS grabStaticPages()');
            console.log(data);

            var navHtmlText = '';

            $.each(data, function (index, post) {
                navHtmlText += '<li role="presentation" id="900' + index + '" class="">';
                navHtmlText += '<a href="http://localhost:8080/DBACMS/staticPage.html?' + post.headline + '">';
                navHtmlText += post.headline;
                navHtmlText += '</a></li>';
            });

            listData.innerHTML += navHtmlText;

        },
        error: function (xhr) {
            console.log('ERROR grabStaticPages() | ' + xhr.status);
            console.log(xhr);
        }
    });
}

var postContentHtml = '';
function getStaticPageCotent(headlineA) {
    return $.ajax({
        type: 'GET',
        url: url + '/posts/static',
        dataType: 'JSON',
        success: function (data) {
            console.log('SUCCESS getStaticPageCotent()');
            console.log(data);

            $.each(data, function (index, post) {                
                if (post.headline.toLowerCase() === headlineA.toLowerCase()) {
                    postContentHtml = post.content;
                }
            });
        },
        error: function (xhr) {
            console.log('ERROR getStaticPageCotent() | ' + xhr.status);
            console.log(xhr);
        }
    });
}