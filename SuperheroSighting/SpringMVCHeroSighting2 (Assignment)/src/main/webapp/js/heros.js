var url = 'http://localhost:8080/SpringMVCHeroSighting2';

var heroTable = document.getElementById('heroTable'),
        editHeroDiv = document.getElementById('editHeroDiv'),
        addHeroDiv = document.getElementById('addHeroDiv'),
        addHeroButton = document.getElementById('addHeroButton');
var containerHtml = heroTable.innerHTML;

//==============================================================================
//      Ajax Calls
//==============================================================================
function loadAllHeros() {
    $.ajax({
        type: 'GET',
        url: url + '/html/heros',
        dataType: 'JSON',
        success: function (data) {
            console.log('SUCCESS loadAllHeros()');
            console.log(data);

            var htmlTextToInject = containerHtml;

            $.each(data, function (index, hero) {
                var row = '<tr>';
                row += '<td>' + hero.id + '</td>';
                row += '<td>' + hero.name + '</td>';
                row += '<td>' + hero.description + '</td>';
                row += '<td id="' + hero.id + '" onclick="showSuperpowerDetails(this.id)"> [Show Superpower Details] </td>';
                row += '<td id="' + hero.id + '" onclick="showOrganizationDetails(this.id)"> [Show Organization Details] </td>';
                row += '<td class="removeTableApperance">'
                        + '<button class="btn btn-default tableButton" onclick="fillEditForm(' + hero.id + ')">Update</button>'
                        + '</td>';
                row += '<td class="removeTableApperance">'
                        + '<button class="btn btn-default tableButton" onclick="deleteHero(' + hero.id + ')">Delete</button>'
                        + '</td>';
                row += '</tr>';

                htmlTextToInject += row;
            });

            heroTable.innerHTML = '';
            heroTable.innerHTML = htmlTextToInject;
        },
        error: function (xhr) {
            console.log(xhr);
            console.log('ERROR loadAllHeros(): ' + xhr.status + ' | ' + xhr.statusText);
        }
    });
}

function fillEditForm(id) {
    $.ajax({
        type: 'GET',
        url: url + '/html/heros/' + id,
        success: function (data) {
            console.log('SUCCESS fillEditForm(id)');

            document.getElementById('id').value = data.id;
            document.getElementById('name').value = data.name;
            document.getElementById('description').value = data.description;

            addHeroButton.style.display = 'block';
            addHeroDiv.style.display = 'none';
            editHeroDiv.style.display = 'block';

            editHeroDiv.style.backgroundColor = 'rgba(200, 200, 200, .5)';

        },
        error: function (xhr) {
            console.log(xhr);
            console.log('ERROR fillEditForm(id): ' + xhr.status + ' | ' + xhr.statusText);
        }
    });
}

function deleteHero(id) {
    $.ajax({
        type: 'DELETE',
        url: url + '/html/heros/' + id,
        success: function () {
            console.log('SUCCESS deleteHero(id)');

            addHeroButton.style.display = 'block';
            addHeroDiv.style.display = 'none';
            editHeroDiv.style.display = 'none';

            loadAllHeros();
        },
        error: function (xhr) {
            console.log(xhr);
            console.log('ERROR updateHero(): ' + xhr.status + ' | ' + xhr.statusText);
        }
    });
}

function updateHero() {
    if (validateForm('') === true) {
        $.ajax({
            type: 'PUT',
            url: url + '/html/heros/' + document.getElementById('id').value,
            data: JSON.stringify({
                id: document.getElementById('id').value,
                name: document.getElementById('name').value,
                description: document.getElementById('description').value,
                superpowers: superpowers,
                organizations: organizations,
            }),
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
            dataType: 'JSON',
            success: function () {
                console.log('SUCCESS updateHero()');

                document.getElementById('id').value = '';
                document.getElementById('name').value = '';
                document.getElementById('description').value = '';
                document.getElementById('superpowers').value = '';
                document.getElementById('organizations').value = '';

                addHeroButton.style.display = 'block';
                addHeroDiv.style.display = 'none';
                editHeroDiv.style.display = 'none';

                loadAllHeros();
            },
            error: function (xhr) {
                console.log(xhr);
                console.log('ERROR updateHero(): ' + xhr.status + ' | ' + xhr.statusText);
            }
        });
    }
}

function addHero() {
    if (validateForm('add') === true) {

        $.ajax({
            type: 'POST',
            url: url + '/html/heros',
            data: JSON.stringify({
                name: document.getElementById('addName').value,
                description: document.getElementById('addDescription').value,
                superpowers: addSuperpowers,
                organizations: addOrganizations
            }),
            dataType: 'JSON',
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
            success: function (data) {
                console.log(data);

                console.log('SUCCESS addHero()');

                document.getElementById('addId').value = '';
                document.getElementById('addName').value = '';
                document.getElementById('addDescription').value = '';
                document.getElementById('addSuperpowers').value = '';
                document.getElementById('addOrganizations').value = '';

                addHeroButton.style.display = 'block';
                addHeroDiv.style.display = 'none';
                editHeroDiv.style.display = 'none';

                loadAllHeros();
            },
            error: function (xhr) {
                console.log(xhr);
                console.log('ERROR addHero(): ' + xhr.status + ' | ' + xhr.statusText);
            }
        });
    }
}

//==============================================================================
//      Helpers
//==============================================================================
var superpowers = [],
        addSuperpowers = [],
        organizations = [],
        addOrganizations = [];

function openAddHeroDiv() {
    addHeroButton.style.display = 'none';
    addHeroDiv.style.display = 'block';
    editHeroDiv.style.display = 'none';
}

function validateForm(appendAddString) {
    var re = /^[a-zA-Z0-9 ]+$/i,
            formString = '',
            name,
            description,
            superpowers,
            organizations,
            formErrorDiv;

    if (appendAddString === 'add') {
        name = document.getElementById('addName');
        description = document.getElementById('addDescription');
        superpowers = document.getElementById('addSuperpowers');
        organizations = document.getElementById('addOrganizations');
        formErrorDiv = document.getElementById('addFormErrors');
    } else if (appendAddString === '') {
        name = document.getElementById('name');
        description = document.getElementById('description');
        superpowers = document.getElementById('superpowers');
        organizations = document.getElementById('organizations');
        formErrorDiv = document.getElementById('formErrors');
    } else {
        console.log('ERROR: validateForm() passed in wrong value. ' + appendAddString);
        return false;
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

    if (!re.test(description.value)) {
        description.style.border = '2px solid red';
        description.style.background = '#ffe8e8';

        formString += '<div class="errorDiv">';
        formString += 'ERROR: INPUT A VALID DESCRIPTION (Letters A - Z) (Numbers 0 - 9)';
        formString += '</div>';
    } else {
        description.style.border = '2px solid green';
        description.style.background = '#F0FFF0';
    }

    if (superpowers.value === '') {
        superpowers.style.border = '2px solid red';
        superpowers.style.background = '#ffe8e8';

        formString += '<div class="errorDiv">';
        formString += 'ERROR: SELECT AT LEAST 1 SUPERPOWER';
        formString += '</div>';
    } else {
        superpowers.style.border = '2px solid green';
        superpowers.style.background = '#F0FFF0';
    }

    if (organizations.value === '') {
        organizations.style.border = '2px solid red';
        organizations.style.background = '#ffe8e8';

        formString += '<div class="errorDiv">';
        formString += 'ERROR: SELECT AT LEAST 1 ORGANIZATION';
        formString += '</div>';
    } else {
        organizations.style.border = '2px solid green';
        organizations.style.background = '#F0FFF0';
    }

    formErrorDiv.innerHTML = formString;
    if (formErrorDiv.innerHTML.length > 0) {
        return false;
    }

    return true;
}


function loadSuperpowersList() {
    $.ajax({
        type: 'GET',
        url: url + '/html/superpowers',
        dataType: 'JSON',
        success: function (data) {
            console.log('SUCCESS loadSuperpowersList()');

            var superpowersHtml = '';
            $.each(data, function (index, superpower) {
                superpowersHtml += '<option class="superpowersOption" id="' + superpower.id + '">' + superpower.type + '</option>';
            });
            document.getElementById('superpowers').innerHTML = superpowersHtml;

            var superpowersList = document.getElementsByClassName('superpowersOption');
            for (var i = 0; i < superpowersList.length; i++) {
                superpowersList[i].addEventListener('click', function () {
                    superpowers = [];
                    var selected = document.querySelectorAll('#superpowers option:checked');
                    for (var i = 0; i < selected.length; i++) {
                        getSuperpowerById(selected[i].id).then(function (data) {
                            superpowers.push(data);
                        });
                    }
                    console.log(superpowers);
                });
            }

            //==========================================================================

            var addSuperpowersHtml = '';
            $.each(data, function (index, superpower) {
                addSuperpowersHtml += '<option class="addSuperpowersOption" id="' + superpower.id + '">' + superpower.type + '</option>';
            });
            document.getElementById('addSuperpowers').innerHTML = addSuperpowersHtml;

            var addSuperpowersList = document.getElementsByClassName('addSuperpowersOption');
            for (var i = 0; i < addSuperpowersList.length; i++) {
                addSuperpowersList[i].addEventListener('click', function () {
                    addSuperpowers = [];
                    var selected = document.querySelectorAll('#addSuperpowers option:checked');
                    for (var i = 0; i < selected.length; i++) {
                        getSuperpowerById(selected[i].id).then(function (data) {
                            addSuperpowers.push(data);
                        });
                    }
                    console.log(addSuperpowers);
                });
            }
        },
        error: function (xhr) {
            console.log(xhr);
            console.log('ERROR loadSuperpowersList(): ' + xhr.status + ' | ' + xhr.statusText);
        }
    });
}

function loadOrganizationsList() {
    $.ajax({
        type: 'GET',
        url: url + '/html/organizations',
        dataType: 'JSON',
        success: function (data) {
            console.log('SUCCESS loadOrganizationsList()');

            var organizationsHtml = '';
            $.each(data, function (index, organization) {
                organizationsHtml += '<option class="organizationsOption" id="' + organization.id + '">' + organization.name + '</option>';
            });
            document.getElementById('organizations').innerHTML = organizationsHtml;

            var organizationsList = document.getElementsByClassName('organizationsOption');
            for (var i = 0; i < organizationsList.length; i++) {
                organizationsList[i].addEventListener('click', function () {
                    organizations = [];
                    var selected = document.querySelectorAll('#organizations option:checked');
                    for (var i = 0; i < selected.length; i++) {
                        getOrganizationById(selected[i].id).then(function (data) {
                            organizations.push(data);
                        });
                    }
                    console.log(organizations);
                });
            }
            //==========================================================================

            var addOrganizationsHtml = '';
            $.each(data, function (index, organization) {
                addOrganizationsHtml += '<option class="addOrganizationsOption" id="' + organization.id + '">' + organization.name + '</option>';
            });
            document.getElementById('addOrganizations').innerHTML = addOrganizationsHtml;

            var addOrganizationsList = document.getElementsByClassName('addOrganizationsOption');
            for (var i = 0; i < addOrganizationsList.length; i++) {
                addOrganizationsList[i].addEventListener('click', function () {
                    addOrganizations = [];
                    var selected = document.querySelectorAll('#addOrganizations option:checked');
                    for (var i = 0; i < selected.length; i++) {
                        getOrganizationById(selected[i].id).then(function (data) {
                            addOrganizations.push(data);
                        });
                    }
                    console.log(addOrganizations);
                });
            }
        },
        error: function (xhr) {
            console.log(xhr);
            console.log('ERROR loadOrganizationsList(): ' + xhr.status + ' | ' + xhr.statusText);
        }
    });
}


function getSuperpowerById(id) {
    return $.ajax({
        type: 'GET',
        url: url + '/html/superpowers/' + id,
        success: function (data) {
            console.log('SUCCESS getSuperpowerById(id)');
            return data;
        },
        error: function (xhr) {
            console.log('ERROR getSuperpowerById(id): ' + xhr.status);
            console.log(xhr);
        }
    });
}

function getOrganizationById(id) {
    return $.ajax({
        type: 'GET',
        url: url + '/html/organizations/' + id,
        success: function (data) {
            console.log('SUCCESS getOrganizationById(id)');
            return data;
        },
        error: function (xhr) {
            console.log('ERROR getOrganizationById(id): ' + xhr.status);
            console.log(xhr);
        }
    });
}

function showSuperpowerDetails(id) {
    $.ajax({
        type: 'GET',
        url: url + '/html/superpowers/hero/' + id,
        success: function (data) {
            console.log('SUCCESS fillEditForm(id)');
            console.log(data);

            var htmlString = '';

            htmlString += '<table>';
            htmlString += '<tr>';
            htmlString += '<th colspan="3">Superpower Details (Hero id ' + id + ')</th>';
            htmlString += '</tr>';
            htmlString += '<tr>';
            htmlString += '<th>Id</th>';
            htmlString += '<th>Type</th>';
            htmlString += '</tr>';

            $.each(data, function (index, superpower) {
                htmlString += '<tr>';
                htmlString += '<td>' + superpower.id + '</td>';
                htmlString += '<td>' + superpower.type + '</td>';
                htmlString += '</tr>';
            });

            htmlString += '</table>';
            
            document.getElementById('superpowersDetailsDiv').innerHTML = htmlString;
        },
        error: function (xhr) {
            console.log(xhr);
            console.log('ERROR fillEditForm(id): ' + xhr.status + ' | ' + xhr.statusText);
        }
    });
}

function showOrganizationDetails(id) {
    $.ajax({
        type: 'GET',
        url: url + '/html/organizations/hero/' + id,
        success: function (data) {
            console.log('SUCCESS fillEditForm(id)');
            console.log(data);

            var htmlString = '';

            htmlString += '<table>';
            htmlString += '<tr>';
            htmlString += '<th colspan="7">Organization Details (Hero id ' + id + ')</th>';
            htmlString += '</tr>';
            htmlString += '<tr>';
            htmlString += '<th>Id</th>';
            htmlString += '<th>LocationId</th>';
            htmlString += '<th>Location</th>';
            htmlString += '<th>Name</th>';
            htmlString += '<th>Description</th>';
            htmlString += '<th>Phone</th>';
            htmlString += '<th>Email</th>';
            htmlString += '</tr>';

            $.each(data, function (index, organization) {
                htmlString += '<tr>';
                htmlString += '<td>' + organization.id + '</td>';
                htmlString += '<td>' + organization.locationId + '</td>';
                htmlString += '<td id="' + organization.locationId + '" onclick="showLocationDetails(this.id)"> [Show Location Details] </td>';
                htmlString += '<td>' + organization.name + '</td>';
                htmlString += '<td>' + organization.description + '</td>';
                htmlString += '<td>' + organization.phone + '</td>';
                htmlString += '<td>' + organization.email + '</td>';
                htmlString += '</tr>';
            });

            htmlString += '</table>';

            document.getElementById('organizationsDetailsDiv').innerHTML = htmlString;
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

            htmlString += '<table>';
            htmlString += '<tr>';
            htmlString += '<th colspan="10">Location Details (Organization id ' + id + ')</th>';
            htmlString += '</tr>';
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

            document.getElementById('locationDetailsDiv').innerHTML = htmlString;
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
    addHeroButton.style.display = 'block';
    addHeroDiv.style.display = 'none';
    editHeroDiv.style.display = 'none';

    loadAllHeros();
    loadSuperpowersList();
    loadOrganizationsList();
});