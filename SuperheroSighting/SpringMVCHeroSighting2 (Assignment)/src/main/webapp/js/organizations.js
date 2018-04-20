var url = 'http://localhost:8080/SpringMVCHeroSighting2';

var organizationTable = document.getElementById('organizationTable'),
        editOrganizationDiv = document.getElementById('editOrganizationDiv'),
        addOrganizationDiv = document.getElementById('addOrganizationDiv'),
        addOrganizationButton = document.getElementById('addOrganizationButton');
var containerHtml = organizationTable.innerHTML;

//==============================================================================
//      Ajax Calls
//==============================================================================
function loadAllOrganizations() {
    $.ajax({
        type: 'GET',
        url: url + '/html/organizations',
        dataType: 'JSON',
        success: function (data) {
            console.log('SUCCESS loadAllOrganizations()');

            var htmlTextToInject = containerHtml;

            $.each(data, function (index, organization) {
                var row = '<tr>';
                row += '<td>' + organization.id + '</td>';
                row += '<td>' + organization.locationId + '</td>';
                row += '<td id="' + organization.locationId + '" onclick="showLocationDetails(this.id)"> [Show Location Details] </td>';
                row += '<td>' + organization.name + '</td>';
                row += '<td>' + organization.description + '</td>';
                row += '<td>' + organization.phone + '</td>';
                row += '<td>' + organization.email + '</td>';
                row += '<td class="removeTableApperance">'
                        + '<button class="btn btn-default tableButton" onclick="fillEditForm(' + organization.id + ')">Update</button>'
                        + '</td>';
                row += '<td class="removeTableApperance">'
                        + '<button class="btn btn-default tableButton" onclick="deleteOrganization(' + organization.id + ')">Delete</button>'
                        + '</td>';
                row += '</tr>';

                htmlTextToInject += row;
            });

            organizationTable.innerHTML = '';
            organizationTable.innerHTML = htmlTextToInject;
        },
        error: function (xhr) {
            console.log(xhr);
            console.log('ERROR loadAllOrganizations(): ' + xhr.status + ' | ' + xhr.statusText);
        }
    });
}

function fillEditForm(id) {
    $.ajax({
        type: 'GET',
        url: url + '/html/organizations/' + id,
        success: function (data) {
            console.log('SUCCESS fillEditForm(id)');

            document.getElementById('id').value = data.id;
            document.getElementById('locationId').value = data.locationId;
//            document.getElementById('location').value = data.location;
            document.getElementById('name').value = data.name;
            document.getElementById('description').value = data.description;
            document.getElementById('phone').value = data.phone;
            document.getElementById('email').value = data.email;

            addOrganizationButton.style.display = 'block';
            addOrganizationDiv.style.display = 'none';
            editOrganizationDiv.style.display = 'block';

            editOrganizationDiv.style.backgroundColor = 'rgba(200, 200, 200, .5)';

        },
        error: function (xhr) {
            console.log(xhr);
            console.log('ERROR fillEditForm(id): ' + xhr.status + ' | ' + xhr.statusText);
        }
    });
}

function deleteOrganization(id) {
    $.ajax({
        type: 'DELETE',
        url: url + '/html/organizations/' + id,
        success: function () {
            console.log('SUCCESS deleteOrganization(id)');

            addOrganizationButton.style.display = 'block';
            addOrganizationDiv.style.display = 'none';
            editOrganizationDiv.style.display = 'none';

            loadAllOrganizations();
        },
        error: function (xhr) {
            console.log(xhr);
            console.log('ERROR updateOrganization(): ' + xhr.status + ' | ' + xhr.statusText);
        }
    });
}

function updateOrganization() {
    if (validateForm('') === true) {
        $.ajax({
            type: 'PUT',
            url: url + '/html/organizations/' + document.getElementById('id').value,
            data: JSON.stringify({
                id: document.getElementById('id').value,
                locationId: document.getElementById('locationId').value,
                location: getLocationById(document.getElementById('locationId').value),
                name: document.getElementById('name').value,
                description: document.getElementById('description').value,
                phone: document.getElementById('phone').value,
                email: document.getElementById('email').value
            }),
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
            dataType: 'JSON',
            success: function () {
                console.log('SUCCESS updateOrganization()');

                document.getElementById('id').value = '';
                document.getElementById('locationId').value = '';
                document.getElementById('name').value = '';
                document.getElementById('description').value = '';
                document.getElementById('phone').value = '';
                document.getElementById('email').value = '';

                addOrganizationButton.style.display = 'block';
                addOrganizationDiv.style.display = 'none';
                editOrganizationDiv.style.display = 'none';

                loadAllOrganizations();
            },
            error: function (xhr) {
                console.log(xhr);
                console.log('ERROR updateOrganization(): ' + xhr.status + ' | ' + xhr.statusText);
            }
        });
    }
}

function addOrganization() {
    if (validateForm('add') === true) {
        $.ajax({
            type: 'POST',
            url: url + '/html/organizations',
            data: JSON.stringify({
                locationId: document.getElementById('addLocationId').value,
                name: document.getElementById('addName').value,
                description: document.getElementById('addDescription').value,
                phone: document.getElementById('addPhone').value,
                email: document.getElementById('addEmail').value
            }),
            dataType: 'JSON',
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
            success: function () {
                console.log('SUCCESS addOrganization()');

                document.getElementById('addLocationId').value = '';
                document.getElementById('addName').value = '';
                document.getElementById('addDescription').value = '';
                document.getElementById('addPhone').value = '';
                document.getElementById('addEmail').value = '';

                addOrganizationButton.style.display = 'block';
                addOrganizationDiv.style.display = 'none';
                editOrganizationDiv.style.display = 'none';

                loadAllOrganizations();
            },
            error: function (xhr) {
                console.log(xhr);
                console.log('ERROR addOrganization(): ' + xhr.status + ' | ' + xhr.statusText);
            }
        });
    }
}

//==============================================================================
//      Helpers
//==============================================================================
function openAddOrganizationDiv() {
    addOrganizationButton.style.display = 'none';
    addOrganizationDiv.style.display = 'block';
    editOrganizationDiv.style.display = 'none';
}

function validateForm(appendAddString) {
    var re = /^[a-zA-Z0-9 ]+$/i,
            rePhone = /^[(]{0,1}[0-9]{3}[)]{0,1}[-\s\.]{0,1}[0-9]{3}[-\s\.]{0,1}[0-9]{4}$/,
            reEmail = /^[a-zA-Z0-9.!#$%&'*+\/=?^_`{|}~-]+@[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?(?:\.[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?)*$/,
            formString = '',
            location,
            name,
            description,
            phone,
            email,
            formErrorDiv;

    if (appendAddString === 'add') {
        location = document.getElementById('locationDropdown');
        name = document.getElementById('addName');
        description = document.getElementById('addDescription');
        phone = document.getElementById('addPhone');
        email = document.getElementById('addEmail');
        formErrorDiv = document.getElementById('addFormErrors');
    } else if (appendAddString === '') {
        location = document.getElementById('locationDropdown2');
        name = document.getElementById('name');
        description = document.getElementById('description');
        phone = document.getElementById('phone');
        email = document.getElementById('email');
        formErrorDiv = document.getElementById('formErrors');
    } else {
        console.log('ERROR: validateForm() passed in wrong value. ' + appendAddString);
        return false;
    }

    if (location.value === location[0].value) {
        location.style.border = '2px solid red';
        location.style.background = '#ffe8e8';

        formString += '<div class="errorDiv">';
        formString += 'ERROR: SELECT AT LEAST 1 LOCATION';
        formString += '</div>';
    } else {
        location.style.border = '2px solid green';
        location.style.background = '#F0FFF0';
    }

    if (!re.test(name.value)) {
        name.style.border = '2px solid red';
        name.style.background = '#ffe8e8';

        formString += '<div class="errorDiv">';
        formString += 'ERROR: INPUT A VALID NAME (Letters A - Z) (Numbers 0 - 9)';
        formString += '</div>';
    } else {
        name.style.border = '2px solid green';
        name.style.background = '#F0FFF0';
    }

    if (description.value !== '' && !re.test(description.value)) {
        description.style.border = '2px solid red';
        description.style.background = '#ffe8e8';

        formString += '<div class="errorDiv">';
        formString += 'ERROR: INPUT A VALID DESCRIPTION (Letters A - Z) (Numbers 0 - 9)';
        formString += '</div>';
    } else {
        description.style.border = '2px solid green';
        description.style.background = '#F0FFF0';
    }

    if (!rePhone.test(phone.value) && phone.value.length <= 14) {
        phone.style.border = '2px solid red';
        phone.style.background = '#ffe8e8';

        formString += '<div class="errorDiv">';
        formString += 'ERROR: INPUT A VALID PHONE NUMBER ( (123) 123-1234 )';
        formString += '</div>';
    } else {
        phone.style.border = '2px solid green';
        phone.style.background = '#F0FFF0';
    }

    if (email.value !== '' && !reEmail.test(email.value)) {
        email.style.border = '2px solid red';
        email.style.background = '#ffe8e8';

        formString += '<div class="errorDiv">';
        formString += 'ERROR: INPUT A VALID EMAIL ( name@name.com )';
        formString += '</div>';
    } else {
        email.style.border = '2px solid green';
        email.style.background = '#F0FFF0';
    }


    formErrorDiv.innerHTML = formString;
    if (formErrorDiv.innerHTML.length > 0) {
        return false;
    }

    return true;
}


function loadLocationList() {
    $.ajax({
        type: 'GET',
        url: url + '/html/locations',
        dataType: 'JSON',
        success: function (data) {
            console.log('SUCCESS loadLocationList()');

            var htmlTextToInject = '<option selected>Choose...</option>';
            $.each(data, function (index, location) {
                htmlTextToInject += '<option class="dropdown" id="' + location.id + '">' + location.name + '</option>';
            });

            document.getElementById('locationDropdown').innerHTML = htmlTextToInject;

            var list = document.getElementsByClassName('dropdown');

            for (var i = 0; i < list.length; i++) {
                list[i].addEventListener('click', function (event) {
                    document.getElementById('addLocationId').value = event.target.id;
                });
            }

            //==========================================================================

            var htmlTextToInject2 = '<option selected>Choose...</option>';
            $.each(data, function (index, location) {
                htmlTextToInject2 += '<option class="dropdown2" id="' + location.id + '">' + location.name + '</option>';
            });
            document.getElementById('locationDropdown2').innerHTML = htmlTextToInject2;
            var list2 = document.getElementsByClassName('dropdown2');

            for (var i = 0; i < list2.length; i++) {
                list2[i].addEventListener('click', function (event) {
                    document.getElementById('locationId').value = event.target.id;
                });
            }
        },
        error: function (xhr) {
            console.log(xhr);
            console.log('ERROR loadLocationList(): ' + xhr.status + ' | ' + xhr.statusText);
        }
    });
}

function getLocationById(id) {
    $.ajax({
        type: 'GET',
        url: url + '/html/locations/' + id,
        success: function (data) {
            console.log('SUCCESS fillEditForm(id)');
            return data;
        },
        error: function (xhr) {
            console.log(xhr);
            console.log('ERROR fillEditForm(id): ' + xhr.status + ' | ' + xhr.statusText);
        }
    });
}

function showLocationDetails(id) {
    $.ajax({
        type: 'GET',
        url: url + '/html/locations/' + id,
        success: function (data) {
            console.log('SUCCESS fillEditForm(id)');

            var htmlString = '';

            htmlString += '<!DOCTYPE html>';
            htmlString += '<html>';
            htmlString += '<head>';
            htmlString += '<meta charset="UTF-8">';
            htmlString += '<style>';
            htmlString += 'table, th, tr, td {border: 1px solid black; border-collapse: collapse;}';
            htmlString += 'th, td {padding: 1em}';
            htmlString += '</style>';
            htmlString += '</head>';
            htmlString += '<body>';
            htmlString += '<table>';
            htmlString += '<tr>';
            htmlString += '<th>Id</th>';
            htmlString += '<th>Name</th>';
            htmlString += '<th>Description</th>';
            htmlString += '<th>Country</th>';
            htmlString += '<th>State</th>';
            htmlString += '<th>City</th>';
            htmlString += '<th>Street</th>';
            htmlString += '<th>Zip</th>';
            htmlString += '<th>Latitude</th>';
            htmlString += '<th>Longitude</th>';
            htmlString += '</tr>';
            htmlString += '<tr>';
            htmlString += '<td>' + data.id + '</td>';
            htmlString += '<td>' + data.name + '</td>';
            htmlString += '<td>' + data.description + '</td>';
            htmlString += '<td>' + data.country + '</td>';
            htmlString += '<td>' + data.state + '</td>';
            htmlString += '<td>' + data.city + '</td>';
            htmlString += '<td>' + data.street + '</td>';
            htmlString += '<td>' + data.zip + '</td>';
            htmlString += '<td>' + data.latitude + '</td>';
            htmlString += '<td>' + data.longitude + '</td>';
            htmlString += '</tr>';
            htmlString += '</table>';
            htmlString += '</body>';
            htmlString += '</html>';

            var win = window.open();
            var htmlContent = htmlString;
            $(win.document.body).html(htmlContent);
            win.focus();
        },
        error: function (xhr) {
            console.log(xhr);
            console.log('ERROR fillEditForm(id): ' + xhr.status + ' | ' + xhr.statusText);
        }
    });
}

//==============================================================================
//      Run
//==============================================================================
$(document).ready(function () {
    addOrganizationButton.style.display = 'block';
    addOrganizationDiv.style.display = 'none';
    editOrganizationDiv.style.display = 'none';

    loadAllOrganizations();
    loadLocationList();
});