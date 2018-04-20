var url = 'http://localhost:8080/SpringMVCHeroSighting2';

var sightingTable = document.getElementById('sightingTable'),
        editSightingDiv = document.getElementById('editSightingDiv'),
        addSightingDiv = document.getElementById('addSightingDiv'),
        addSightingButton = document.getElementById('addSightingButton');
var containerHtml = sightingTable.innerHTML;

//==============================================================================
//      Ajax Calls
//==============================================================================
function loadAllSightings() {
    $.ajax({
        type: 'GET',
        url: url + '/html/sightings',
        dataType: 'JSON',
        success: function (data) {
            console.log('SUCCESS loadAllSightings()');
            console.log(data);

            var htmlTextToInject = containerHtml;

            $.each(data, function (index, sighting) {
                var row = '<tr>';
                row += '<td>' + sighting.id + '</td>';
                row += '<td>' + sighting.locationId + '</td>';
                row += '<td id="' + sighting.locationId + '" onclick="showLocationDetails(this.id)"> [Show Location Details] </td>';
                row += '<td>' + sighting.date + '</td>';
                row += '<td>' + sighting.time + '</td>';
                row += '<td id="' + sighting.id + '" onclick="showHeroDetails(this.id)"> [Show Hero Details] </td>';
                row += '<td class="removeTableApperance">'
                        + '<button class="btn btn-default tableButton" onclick="fillEditForm(' + sighting.id + ')">Update</button>'
                        + '</td>';
                row += '<td class="removeTableApperance">'
                        + '<button class="btn btn-default tableButton" onclick="deleteSighting(' + sighting.id + ')">Delete</button>'
                        + '</td>';
                row += '</tr>';

                htmlTextToInject += row;
            });

            sightingTable.innerHTML = '';
            sightingTable.innerHTML = htmlTextToInject;
        },
        error: function (xhr) {
            console.log(xhr);
            console.log('ERROR loadAllSightings(): ' + xhr.status + ' | ' + xhr.statusText);
        }
    });
}

function fillEditForm(id) {
    $.ajax({
        type: 'GET',
        url: url + '/html/sightings/' + id,
        dataType: 'JSON',
        success: function (data) {
            console.log('SUCCESS fillEditForm(id)');

            document.getElementById('id').value = data.id;
            document.getElementById('locationId').value = data.locationId;
            document.getElementById('location').value = data.location;
            document.getElementById('date').value = data.date;
            document.getElementById('time').value = data.time;
            document.getElementById('heros').value = data.heros;

            addSightingButton.style.display = 'block';
            addSightingDiv.style.display = 'none';
            editSightingDiv.style.display = 'block';

            editSightingDiv.style.backgroundColor = 'rgba(200, 200, 200, .5)';

        },
        error: function (xhr) {
            console.log(xhr);
            console.log('ERROR fillEditForm(id): ' + xhr.status + ' | ' + xhr.statusText);
        }
    });
}

function deleteSighting(id) {
    $.ajax({
        type: 'DELETE',
        url: url + '/html/sightings/' + id,
        success: function () {
            console.log('SUCCESS deleteSighting(id)');

            addSightingButton.style.display = 'block';
            addSightingDiv.style.display = 'none';
            editSightingDiv.style.display = 'none';

            loadAllSightings();
        },
        error: function (xhr) {
            console.log(xhr);
            console.log('ERROR updateSighting(): ' + xhr.status + ' | ' + xhr.statusText);
        }
    });
}


function updateSighting() {
    if (validateForm('') === true) {
        var locationGet = getLocationById(document.getElementById('locationId').value);
        $.when(locationGet).then(function (result1) {

            $.ajax({
                type: 'PUT',
                url: url + '/html/sightings/' + document.getElementById('id').value,
                data: JSON.stringify({
                    id: document.getElementById('id').value,
                    locationId: document.getElementById('locationId').value,
                    location: result1,
                    date: document.getElementById('date').value,
                    time: document.getElementById('time').value,
                    heros: heros
                }),
                headers: {
                    'Accept': 'application/json',
                    'Content-Type': 'application/json'
                },
                dataType: 'JSON',
                success: function (data) {
                    console.log('SUCCESS updateSighting()');
                    console.log(data);

                    document.getElementById('id').value = '';
                    document.getElementById('locationId').value = '';
                    document.getElementById('location').value = '';
                    document.getElementById('date').value = '';
                    document.getElementById('time').value = '';
                    document.getElementById('heros').value = '';

                    addSightingButton.style.display = 'block';
                    addSightingDiv.style.display = 'none';
                    editSightingDiv.style.display = 'none';

                    loadAllSightings();
                },
                error: function (xhr) {
                    console.log('ERROR updateSighting(): ' + xhr.status);
                    console.log(xhr);
                }
            });
        });
    }
}

function addSighting() {
    if (validateForm('add') === true) {
        $.ajax({
            type: 'POST',
            url: url + '/html/sightings',
            data: JSON.stringify({
                locationId: document.getElementById('addLocationId').value,
                location: getLocationById(document.getElementById('addLocationId').value),
                date: document.getElementById('addDate').value,
                time: convertToSqlTime(document.getElementById('addTime').value),
                heros: addHeros
            }),
            dataType: 'JSON',
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
            success: function (data) {
                console.log('SUCCESS addSighting()');
                console.log(data);

                document.getElementById('addId').value = '';
                document.getElementById('addLocationId').value = '';
                document.getElementById('addLocation').value = '';
                document.getElementById('addDate').value = '';
                document.getElementById('addTime').value = '';
                document.getElementById('addHeros').value = '';

                addSightingButton.style.display = 'block';
                addSightingDiv.style.display = 'none';
                editSightingDiv.style.display = 'none';

                loadAllSightings();
            },
            error: function (xhr) {
                console.log(xhr);
                console.log('ERROR addSighting(): ' + xhr.status + ' | ' + xhr.statusText);
            }
        });
    }
}

//==============================================================================
//      Helpers
//==============================================================================
var locations = [],
        addLocations = [],
        heros = [],
        addHeros = [];

function openAddSightingDiv() {
    addSightingButton.style.display = 'none';
    addSightingDiv.style.display = 'block';
    editSightingDiv.style.display = 'none';
}

function getLocationById(id) {
    return $.ajax({
        type: 'GET',
        url: url + '/html/locations/' + id,
        success: function (data) {
            console.log('SUCCESS fillEditForm(id)');
        },
        error: function (xhr) {
            console.log(xhr);
            console.log('ERROR fillEditForm(id): ' + xhr.status + ' | ' + xhr.statusText);
        }
    });
}

function getLocationsById(id) {
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

function getHeroById(id) {
    return $.ajax({
        type: 'GET',
        url: url + '/html/heros/' + id,
        success: function (data) {
            console.log('SUCCESS getHeroById(id)');
        },
        error: function (xhr) {
            console.log('ERROR getHeroById(id): ' + xhr.status);
            console.log(xhr);
        }
    });
}

function validateForm(appendAddString) {
    var re = /^[a-zA-Z0-9 ]+$/i,
            formString = '',
            locationId,
            location,
            date,
            time,
            heros,
            formErrorDiv;

    if (appendAddString === 'add') {
        locationId = document.getElementById('addLocationId');
        location = document.getElementById('addLocation');
        date = document.getElementById('addDate');
        time = document.getElementById('addTime');
        heros = document.getElementById('addHeros');
        formErrorDiv = document.getElementById('addFormErrors');
    } else if (appendAddString === '') {
        locationId = document.getElementById('locationId');
        location = document.getElementById('location');
        date = document.getElementById('date');
        time = document.getElementById('time');
        heros = document.getElementById('heros');
        formErrorDiv = document.getElementById('formErrors');
    } else {
        console.log('ERROR: validateForm() passed in wrong value. ' + appendAddString);
        return false;
    }

//    if (!re.test(locationId.value)) {
//        locationId.style.border = '2px solid red';
//        locationId.style.background = '#ffe8e8';
//
//        formString += '<div class="errorDiv">';
//        formString += 'ERROR: INPUT A VALID NAME (Letters A - Z) (Numbers 0 - 9)';
//        formString += '</div>';
//    } else {
//        locationId.style.border = '2px solid green';
//        locationId.style.background = '#F0FFF0';
//    }

    if (location.value === location[0].value) {
        location.style.border = '2px solid red';
        location.style.background = '#ffe8e8';

        formString += '<div class="errorDiv">';
        formString += 'ERROR: CHOOSE A LOCATION';
        formString += '</div>';
    } else {
        location.style.border = '2px solid green';
        location.style.background = '#F0FFF0';
    }

    if (date.value === '') {
        date.style.border = '2px solid red';
        date.style.background = '#ffe8e8';

        formString += '<div class="errorDiv">';
        formString += 'ERROR: CHOOSE A DATE';
        formString += '</div>';
    } else {
        date.style.border = '2px solid green';
        date.style.background = '#F0FFF0';
    }

    if (time.value === '') {
        time.style.border = '2px solid red';
        time.style.background = '#ffe8e8';

        formString += '<div class="errorDiv">';
        formString += 'ERROR: CHOOSE A TIME';
        formString += '</div>';
    } else {
        time.style.border = '2px solid green';
        time.style.background = '#F0FFF0';
    }

    if (heros.value === '') {
        heros.style.border = '2px solid red';
        heros.style.background = '#ffe8e8';

        formString += '<div class="errorDiv">';
        formString += 'ERROR: CHOOSE AT LEAST 1 HERO';
        formString += '</div>';
    } else {
        heros.style.border = '2px solid green';
        heros.style.background = '#F0FFF0';
    }


    formErrorDiv.innerHTML = formString;
    if (formErrorDiv.innerHTML.length > 0) {
        return false;
    }

    return true;
}

function loadLocationsList() {
    $.ajax({
        type: 'GET',
        url: url + '/html/locations',
        dataType: 'JSON',
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        },
        success: function (data) {
            console.log('SUCCESS loadLocationList()');

            var addLocation = document.getElementById('addLocation'),
                    location = document.getElementById('location'),
                    addLocationHtml = '<option selected>Choose...</option>',
                    locationHtml = '<option selected>Choose...</option>',
                    addLocationlist,
                    locationList;

            $.each(data, function (index, location) {
                addLocationHtml += '<option class="addLocationDropdown" id="' + location.id + '">' + location.name + '</option>';
            });

            addLocation.innerHTML = addLocationHtml;
            addLocationlist = document.getElementsByClassName('addLocationDropdown');

            for (var i = 0; i < addLocationlist.length; i++) {
                addLocationlist[i].addEventListener('click', function (event) {
                    console.log('click');
                    document.getElementById('addLocationId').value = event.target.id;
                });
            }

            //==========================================================================

            $.each(data, function (index, location) {
                locationHtml += '<option class="locationDropdown" id="' + location.id + '">' + location.name + '</option>';
            });

            location.innerHTML = locationHtml;
            locationList = document.getElementsByClassName('locationDropdown');

            for (var i = 0; i < locationList.length; i++) {
                locationList[i].addEventListener('click', function (event) {
                    document.getElementById('locationId').value = event.target.id;
                });
            }
        },
        error: function (xhr) {
            console.log(xhr);
            console.log('ERROR loadLocationList(): ' + xhr.status);
        }
    });
}

function loadHerosList() {
    $.ajax({
        type: 'GET',
        url: url + '/html/heros',
        success: function (data) {
            console.log('SUCCESS loadHerosList()');

            var herosHtml = '',
                    addHerosHtml = '',
                    herosElm = document.getElementById('heros'),
                    addHerosElm = document.getElementById('addHeros');

            $.each(data, function (index, hero) {
                herosHtml += '<option class="herosOption" id="' + hero.id + '">' + hero.name + '</option>';
            });

            herosElm.innerHTML = herosHtml;
            var herosList = document.getElementsByClassName('herosOption');

            for (var i = 0; i < herosList.length; i++) {

                herosList[i].addEventListener('click', function () {
                    heros = [];
                    var selected = document.querySelectorAll('#heros option:checked');
                    for (var i = 0; i < selected.length; i++) {
                        getHeroById(selected[i].id).then(function (data) {
                            heros.push(data);
                        });
                    }

                    console.log(heros);
                });
            }
            //==========================================================================


            $.each(data, function (index, hero) {
                addHerosHtml += '<option class="herosOption" id="' + hero.id + '">' + hero.name + '</option>';
            });

            addHerosElm.innerHTML = addHerosHtml;
            var addHerosList = document.getElementsByClassName('herosOption');

            for (var i = 0; i < addHerosList.length; i++) {

                addHerosList[i].addEventListener('click', function () {
                    addHeros = [];
                    var selected = document.querySelectorAll('#addHeros option:checked');

                    for (var i = 0; i < selected.length; i++) {
                        getHeroById(selected[i].id).then(function (data) {
                            addHeros.push(data);
                        });
                    }
//                    console.log(addHeros);
                });
            }
        },
        error: function (xhr) {
            console.log('ERROR loadHerosList(): ' + xhr.status);
            console.log(xhr);
        }
    });
}

function showHeroDetails(id) {
    $.ajax({
        type: 'GET',
        url: url + '/html/hero/sighting/' + id,
        success: function (data) {
            console.log('SUCCESS showHeroDetails(id)');
            console.log(data);

            var htmlString = '';

            htmlString += '<table>';
            htmlString += '<tr>';
            htmlString += '<th colspan="5">Hero Details (Sighting id ' + id + ')</th>';
            htmlString += '</tr>';
            htmlString += '<tr>';
            htmlString += '<th>Id</th>';
            htmlString += '<th>Name</th>';
            htmlString += '<th>Description</th>';
            htmlString += '<th>Superpowers</th>';
            htmlString += '<th>Organizations</th>';
            htmlString += '</tr>';

            $.each(data, function (index, hero) {
                htmlString += '<tr>';
                htmlString += '<th>' + hero.id + '</th>';
                htmlString += '<th>' + hero.name + '</th>';
                htmlString += '<th>' + hero.description + '</th>';
                htmlString += '<th id=' + hero.id + ' onclick="showSuperpowerDetails(this.id)">[Superpowers Details]</th>';
                htmlString += '<th id=' + hero.id + ' onclick="showOrganizationDetails(this.id)">{Organizations Details]</th>';
                htmlString += '</tr>';
            });

            htmlString += '</table>';

            document.getElementById('herosDetailsDiv').innerHTML = htmlString;
        },
        error: function (xhr) {
            console.log('ERROR showHeroDetails(id)');
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
            console.log('SUCCESS showLocationDetails(id)');

            var htmlString = '';

            htmlString += '<table>';
            htmlString += '<tr>';
            htmlString += '<th colspan="10">Location Details (Location id ' + id + ')</th>';
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
            console.log('ERROR showLocationDetails(id): ' + xhr.status + ' | ' + xhr.statusText);
        }
    });
}

//==============================================================================
//      Run
//==============================================================================
Date.prototype.toDateInputValue = (function () {
    var local = new Date(this);
    local.setMinutes(this.getMinutes() - this.getTimezoneOffset());
    return local.toJSON().slice(0, 10);
});

Date.prototype.toTimeInputValue = (function () {
    var local = new Date(this);
    local.setMinutes(this.getMinutes());
    return local.toTimeString();
});

//input:    2018-04-06
//sql:      2008-11-11 
var sqlDate = '';

//input:    01:31
//sql:      13:23:44
var sqlTime = '';

function convertToSqlTime(time) {
    return time += ':00';
}

$(document).ready(function () {
    addSightingButton.style.display = 'block';
    addSightingDiv.style.display = 'none';
    editSightingDiv.style.display = 'none';

    document.getElementById('addDate').value = new Date().toDateInputValue();
    document.getElementById('date').value = new Date().toDateInputValue();

    var currentTime = new Date().toTimeInputValue();
    currentTime = currentTime.slice(0, 5);

    document.getElementById('addTime').value = currentTime;
    document.getElementById('time').value = currentTime;

    loadAllSightings();
    loadLocationsList();
    loadHerosList();
});