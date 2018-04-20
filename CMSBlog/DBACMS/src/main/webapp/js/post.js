var url = 'http://localhost:8080/DBACMS';

var userName = document.getElementById('userName'),
        datePicker = document.getElementById('datePicker'),
        tags = document.getElementById('tags'),
        mceTextArea = document.getElementById('mceTextArea'),
        submitPost = document.getElementById('submitPost'),
        dateValue = '',
        tagInput = '';

var aString = '<table style="height: 690px; width: 99.7221%; border-collapse: collapse; border-style: hidden; background-color: rgb(252, 255, 247); margin-left: auto; margin-right: auto;" data-mce-style="height: 690px; width: 99.7221%; border-collapse: collapse; border-style: hidden; background-color: #fcfff7; margin-left: auto; margin-right: auto;" class="mce-item-table" data-mce-selected="1" border="0"><tbody><tr><td style="width: 7.62638%;"><br></td><td style="width: 83.4372%;"><p style="text-align: center;" data-mce-style="text-align: center;"><img src="http://www3.pictures.zimbio.com/mp/CFF8HiJr5rrx.jpg" alt="" width="706" height="470"><br data-mce-bogus="1"></p><p style="text-align: center;" data-mce-style="text-align: center;">TestText Test Text Test Text Test Text Test Text Test Text Test Text TestText Test Text Test Text Test Text Test Text Test Text Test Text TestText Test Text Test Text Test Text Test Text Test Text Test Text TestText Test Text Test Text Test Text Test Text Test Text Test Text TestText #<span style="color: rgb(0, 0, 255);" data-mce-style="color: #0000ff;">testHashtag01 </span>Test Text Test Text Test Text Test Text Test #<span style="color: rgb(0, 0, 255);" data-mce-style="color: #0000ff;">testHashtag02 </span>TextTest Text Test Text Test Text Test Text Test Text Test Text Test TextTest Text Test Text Test Text Test Text Test Text Test Text Test TextTest Text Test Text Test Text Test Text Test </p><p style="text-align: center;" data-mce-style="text-align: center;">Text Test Text Test TextTest <span style="color: rgb(153, 51, 0);" data-mce-style="color: #993300;">#<span style="color: rgb(0, 0, 255);" data-mce-style="color: #0000ff;">testHashtag03 </span></span>TextTest Text Test Text Test Text Test Text Test Text Test Text Test TextTest Text Test Text Test Text Test Text Test Text Test Text Test TextTest Text Test Text Test Text Test Text Test Text Test Text Test TextTest Text Test Text Test Text Test Text Test Text Test Text Test TextTest Text Test Text Test Text Test Text Test Text Test Text Test TextTest Text Test Text Test Text Test Text Test Text Test Text Test TextTest Text Test Text Test Text Test Text Test Text Test Text Test TextTest Text Test Text Test Text Test Text</p><p style="text-align: center;" data-mce-style="text-align: center;">#<span style="color: rgb(0, 0, 255);" data-mce-style="color: #0000ff;">testHashtag04</span></p><p style="text-align: center;" data-mce-style="text-align: center;"><span style="color: rgb(0, 0, 255);" data-mce-style="color: #0000ff;"><img src="https://www.gamemania.be/-/media/Sites/GameMania/Dedicated%20Pages/2017/USED%20Games%20UPDATE%202017/logo_game_on.png" alt="" width="452" height="193"></span></p><p style="text-align: center;" data-mce-style="text-align: center;">TestText Test Text Test Text Test Text Test Text Test Text Test Text TestText Test Text Test Text Test Text Test Text Test Text Test Text TestText Test Text Test Text Test Text Test Text Test #<span style="color: rgb(0, 0, 255);" data-mce-style="color: #0000ff;">testHashtag05 </span>TextTest Text Test Text Test Text Test Text Test Text Test Text Test TextTest Text Test Text Test Text Test Text Test Text Test Text Test TextTest Text Test Text Test Text Test Text Test Text Test Text Test TextTest Text Test Text Test Text Test Text Test Text Test Text Test TextTest Text Test Text Test Text Test Text Test Text Test Text </p><p style="text-align: center;" data-mce-style="text-align: center;">Test TextTest Text Test <span style="color: rgb(153, 51, 0);" data-mce-style="color: #993300;">#<span style="color: rgb(0, 0, 255);" data-mce-style="color: #0000ff;">testHashtag06</span></span>Text Test Text Test Text Test Text Test Text Test Text Test Text TestText Test Text Test Text Test Text Test Text Test Text Test Text TestText Test Text Test Text Test Text Test Text Test Text Test Text TestText Test Text Test #<span style="color: rgb(0, 0, 255);" data-mce-style="color: #0000ff;">testHashtag07 </span>TextTest Text Test Text Test Text Test Text Test Text Test Text Test TextTest Text Test Text Test Text Test Text Test Text Test Text Test TextTest Text Test Text Test Text Test Text Test Text Test Text Test TextTest Text Test Text Test Text Test Text Test Text Test Text Test TextTest Text</p><p style="text-align: center;" data-mce-style="text-align: center;">#<span style="color: rgb(0, 0, 255);" data-mce-style="color: #0000ff;">testHashTag08</span></p><p style="text-align: center;" data-mce-style="text-align: center;"><span style="color: rgb(0, 0, 255);" data-mce-style="color: #0000ff;"><br data-mce-bogus="1"></span></p><p style="text-align: center;" data-mce-style="text-align: center;"><span style="color: rgb(0, 0, 255);" data-mce-style="color: #0000ff;"></span></p></td><td style="width: 8.93627%;"><br></td></tr></tbody></table><div id="mceTextAreaTags"><br data-mce-bogus="1"></div>';


//==============================================================================
//      Ajax Calls
//==============================================================================
//function getUsername() {
//    $.ajax({
//        type: 'GET',
//        url: url + '/username',
//        dataType: 'JSON',
//        success: function (data) {
//            console.log('SUCCESS getUsername()');
//            console.log(data);
//        },
//        error: function (xhr) {
//            console.log(xhr);
//            console.log('ERROR getUsername(): ' + xhr.status + ' | ' + xhr.statusText);
//        }
//    });
//}

//function createPost() {
//    $.ajax({
//        type: 'POST',
//        url: url + '/makeAThing',
//        dataType: 'JSON',
//        success: function (data) {
//            console.log('SUCCESS createPost()');
//            console.log(data);
//            
//            window.location.replace(url + "/");
//        },
//        error: function (xhr) {
//            console.log(xhr);
//            console.log('ERROR createPost(): ' + xhr.status + ' | ' + xhr.statusText);
//        }
//    });
//}

function createPost() {
    $.post(url + '/makeAThing', function(data) {
        console.log('success createPost()');
        console.log(data);
    })
}

//==============================================================================
//      Event Listners
//==============================================================================
function addEventListeners() {
    datePicker.addEventListener('change', function () {
        dateValue = datePicker.value;
    });

    tags.addEventListener('input', function () {
        tagInput = tags.value;
        console.log(tagInput);
    });
}

//==============================================================================
//      Helpers
//==============================================================================
Date.prototype.toDateInputValue = (function () {
    var local = new Date(this);
    local.setMinutes(this.getMinutes() - this.getTimezoneOffset());
    return local.toJSON().slice(0, 10);
});


//==============================================================================
//      Run
//==============================================================================
$(document).ready(function () {
    datePicker.value = new Date().toDateInputValue();
    dateValue = datePicker.value;
    console.log(dateValue);

    mceTextArea.value = aString;

    addEventListeners();
});