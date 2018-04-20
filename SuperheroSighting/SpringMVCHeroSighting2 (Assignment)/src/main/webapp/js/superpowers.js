var url = 'http://localhost:8080/SpringMVCHeroSighting2';

var superpowerTable = document.getElementById('superpowerTable'),
        editSuperpowerDiv = document.getElementById('editSuperpowerDiv'),
        addSuperpowerDiv = document.getElementById('addSuperpowerDiv'),
        addSuperpowerButton = document.getElementById('addSuperpowerButton');
var containerHtml = superpowerTable.innerHTML;

//==============================================================================
//      Ajax Calls
//==============================================================================
function loadAllSuperpowers() {
    $.ajax({
        type: 'GET',
        url: url + '/html/superpowers',
        dataType: 'JSON',
        success: function (data) {
            console.log('SUCCESS loadAllSuperpowers()');

            var htmlTextToInject = containerHtml;

            $.each(data, function (index, superpower) {
                var row = '<tr>';
                row += '<td>' + superpower.id + '</td>';
                row += '<td>' + superpower.type + '</td>';
                row += '<td class="removeTableApperance">'
                        + '<button class="btn btn-default tableButton" onclick="fillEditForm(' + superpower.id + ')">Update</button>'
                        + '</td>';
                row += '<td class="removeTableApperance">'
                        + '<button class="btn btn-default tableButton" onclick="deleteSuperpower(' + superpower.id + ')">Delete</button>'
                        + '</td>';
                row += '</tr>';

                htmlTextToInject += row;
            });

            superpowerTable.innerHTML = '';
            superpowerTable.innerHTML = htmlTextToInject;
        },
        error: function (xhr) {
            console.log(xhr);
            console.log('ERROR loadAllSuperpowers(): ' + xhr.status + ' | ' + xhr.statusText);
        }
    });
}

function fillEditForm(id) {
    $.ajax({
        type: 'GET',
        url: url + '/html/superpowers/' + id,
        success: function (data) {
            console.log('SUCCESS fillEditForm(id)');

            document.getElementById('id').value = data.id;
            document.getElementById('type').value = data.type;

            addSuperpowerButton.style.display = 'block';
            addSuperpowerDiv.style.display = 'none';
            editSuperpowerDiv.style.display = 'block';

            editSuperpowerDiv.style.backgroundColor = 'rgba(200, 200, 200, .5)';

        },
        error: function (xhr) {
            console.log(xhr);
            console.log('ERROR fillEditForm(id): ' + xhr.status + ' | ' + xhr.statusText);
        }
    });
}

function deleteSuperpower(id) {
    $.ajax({
        type: 'DELETE',
        url: url + '/html/superpowers/' + id,
        success: function () {
            console.log('SUCCESS deleteSuperpower(id)');

            addSuperpowerButton.style.display = 'block';
            addSuperpowerDiv.style.display = 'none';
            editSuperpowerDiv.style.display = 'none';

            loadAllSuperpowers();
        },
        error: function (xhr) {
            console.log(xhr);
            console.log('ERROR updateSuperpower(): ' + xhr.status + ' | ' + xhr.statusText);
        }
    });
}

function updateSuperpower() {
    if (validateForm('') === true) {
        $.ajax({
            type: 'PUT',
            url: url + '/html/superpowers/' + document.getElementById('id').value,
            data: JSON.stringify({
                id: document.getElementById('id').value,
                type: document.getElementById('type').value
            }),
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
            dataType: 'JSON',
            success: function () {
                console.log('SUCCESS updateSuperpower()');

                document.getElementById('id').value = '';
                document.getElementById('type').value = '';

                addSuperpowerButton.style.display = 'block';
                addSuperpowerDiv.style.display = 'none';
                editSuperpowerDiv.style.display = 'none';

                loadAllSuperpowers();
            },
            error: function (xhr) {
                console.log(xhr);
                console.log('ERROR updateSuperpower(): ' + xhr.status + ' | ' + xhr.statusText);
            }
        });
    }
}

function addSuperpower() {
    if (validateForm('add') === true) {
        $.ajax({
            type: 'POST',
            url: url + '/html/superpowers',
            data: JSON.stringify({
                type: document.getElementById('addType').value
            }),
            dataType: 'JSON',
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
            success: function () {
                console.log('SUCCESS addSuperpower()');

                document.getElementById('addType').value = '';

                addSuperpowerButton.style.display = 'block';
                addSuperpowerDiv.style.display = 'none';
                editSuperpowerDiv.style.display = 'none';

                loadAllSuperpowers();
            },
            error: function (xhr) {
                console.log(xhr);
                console.log('ERROR addSuperpower(): ' + xhr.status + ' | ' + xhr.statusText);
            }
        });
    }
}

//==============================================================================
//      Helpers
//==============================================================================
function openAddSuperpowerDiv() {
    addSuperpowerButton.style.display = 'none';
    addSuperpowerDiv.style.display = 'block';
    editSuperpowerDiv.style.display = 'none';
}

function validateForm(appendAddString) {
    var re = /^[a-zA-Z0-9 ]+$/i,
            formString = '',
            type,
            formErrorDiv;

    if (appendAddString === 'add') {
        type = document.getElementById('addType');
        formErrorDiv = document.getElementById('addFormErrors');
    } else if (appendAddString === '') {
        type = document.getElementById('type');
        formErrorDiv = document.getElementById('formErrors');
    } else {
        console.log('ERROR: validateForm() passed in wrong value. ' + appendAddString);
        return false;
    }

    if (!re.test(type.value)) {
        type.style.border = '2px solid red';
        type.style.background = '#ffe8e8';

        formString += '<div class="errorDiv">';
        formString += 'ERROR: INPUT A VALID TYPE (Letters A - Z) (Numbers 0 - 9)';
        formString += '</div>';
    } else {
        type.style.border = '2px solid green';
        type.style.background = '#F0FFF0';
    }


    formErrorDiv.innerHTML = formString;
    if (formErrorDiv.innerHTML.length > 0) {
        return false;
    }

    return true;
}


//==============================================================================
//      Run
//==============================================================================
$(document).ready(function () {
    addSuperpowerButton.style.display = 'block';
    addSuperpowerDiv.style.display = 'none';
    editSuperpowerDiv.style.display = 'none';

    loadAllSuperpowers();
});