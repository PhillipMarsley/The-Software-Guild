//==============================================================================
//      Navigation Bar Loader
//==============================================================================
var url = 'http://localhost:8080/SpringMVCHeroSighting2/';

var navbar = document.getElementById('navbar');
navbar.innerHTML = '';

var xhr = new XMLHttpRequest();
xhr.addEventListener('load', loadNavBar);
xhr.open('GET', url + 'html/navbar.html', true);
xhr.responseType = '';
xhr.send();

function loadNavBar() {
    navbar.innerHTML = this.responseText;

    for (var i = 1; i <= 6; i++) {
        document.getElementById(i.toString()).className = '';
    }

    var windowLoc = window.location.pathname;
    windowLoc = windowLoc.replace('/SpringMVCHeroSighting2/', '');
    windowLoc = windowLoc.replace('.html', '');

    if (windowLoc === 'index') {
        document.getElementById('1').className = 'active';
    } else if (windowLoc === 'html/locations') {
        document.getElementById('2').className = 'active';
    } else if (windowLoc === 'html/organizations') {
        document.getElementById('3').className = 'active';
    } else if (windowLoc === 'html/superpowers') {
        document.getElementById('4').className = 'active';
    } else if (windowLoc === 'html/heros') {
        document.getElementById('5').className = 'active';
    } else if (windowLoc === 'html/sightings') {
        document.getElementById('6').className = 'active';
    }
}
