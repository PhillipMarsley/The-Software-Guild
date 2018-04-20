var url = 'http://localhost:8080/SpringMVCHeroSighting2';

var locationTable = document.getElementById('locationTable'),
        editLocationDiv = document.getElementById('editLocationDiv'),
        addLocationDiv = document.getElementById('addLocationDiv'),
        addLocationButton = document.getElementById('addLocationButton');
var containerHtml = locationTable.innerHTML;


//==============================================================================
//      Ajax Calls
//==============================================================================
function loadAllLocations() {
    $.ajax({
        type: 'GET',
        url: url + '/html/locations',
        dataType: 'JSON',
        success: function (data) {
            console.log('SUCCESS loadAllLocations()');

            var htmlTextToInject = containerHtml;

            $.each(data, function (index, location) {
                var row = '<tr>';
                row += '<td>' + location.id + '</td>';
                row += '<td>' + location.name + '</td>';
                row += '<td>' + location.description + '</td>';
                row += '<td>' + location.country + '</td>';
                row += '<td>' + location.state + '</td>';
                row += '<td>' + location.city + '</td>';
                row += '<td>' + location.street + '</td>';
                row += '<td>' + location.zip + '</td>';
                row += '<td>' + location.latitude + '</td>';
                row += '<td>' + location.longitude + '</td>';
                row += '<td class="removeTableApperance">'
                        + '<button class="btn btn-default tableButton" onclick="fillEditForm(' + location.id + ')">Update</button>'
                        + '</td>';
                row += '<td class="removeTableApperance">'
                        + '<button class="btn btn-default tableButton" onclick="deleteLocation(' + location.id + ')">Delete</button>'
                        + '</td>';
                row += '</tr>';

                htmlTextToInject += row;
            });

            locationTable.innerHTML = '';
            locationTable.innerHTML = htmlTextToInject;
        },
        error: function (xhr) {
            console.log(xhr);
            console.log('ERROR loadAllLocations(): ' + xhr.status + ' | ' + xhr.statusText);
        }
    });
}

function fillEditForm(id) {
    $.ajax({
        type: 'GET',
        url: url + '/html/locations/' + id,
        success: function (data) {
            console.log('SUCCESS fillEditForm(id)');

            document.getElementById('id').value = data.id;
            document.getElementById('name').value = data.name;
            document.getElementById('description').value = data.description;
            document.getElementById('country').value = data.country;
            document.getElementById('state').value = data.state;
            document.getElementById('city').value = data.city;
            document.getElementById('street').value = data.street;
            document.getElementById('zip').value = data.zip;
            document.getElementById('latitude').value = data.latitude;
            document.getElementById('longitude').value = data.longitude;

            addLocationButton.style.display = 'block';
            addLocationDiv.style.display = 'none';
            editLocationDiv.style.display = 'block';

            editLocationDiv.style.backgroundColor = 'rgba(200, 200, 200, .5)';

        },
        error: function (xhr) {
            console.log(xhr);
            console.log('ERROR fillEditForm(id): ' + xhr.status + ' | ' + xhr.statusText);
        }
    });
}

function deleteLocation(id) {
    $.ajax({
        type: 'DELETE',
        url: url + '/html/locations/' + id,
        success: function () {
            console.log('SUCCESS deleteLocation(id)');

            addLocationButton.style.display = 'block';
            addLocationDiv.style.display = 'none';
            editLocationDiv.style.display = 'none';

            loadAllLocations();
        },
        error: function (xhr) {
            console.log(xhr);
            console.log('ERROR updateLocation(): ' + xhr.status + ' | ' + xhr.statusText);
        }
    });
}

function updateLocation() {
    if (validateForm('') === true) {
        $.ajax({
            type: 'PUT',
            url: url + '/html/locations/' + document.getElementById('id').value,
            data: JSON.stringify({
                id: document.getElementById('id').value,
                name: document.getElementById('name').value,
                description: document.getElementById('description').value,
                country: document.getElementById('country').value,
                state: document.getElementById('state').value,
                city: document.getElementById('city').value,
                street: document.getElementById('street').value,
                zip: document.getElementById('zip').value,
                latitude: document.getElementById('latitude').value,
                longitude: document.getElementById('longitude').value
            }),
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
            dataType: 'JSON',
            success: function () {
                console.log('SUCCESS updateLocation()');

                document.getElementById('id').value = '';
                document.getElementById('name').value = '';
                document.getElementById('description').value = '';
                document.getElementById('country').value = '';
                document.getElementById('state').value = '';
                document.getElementById('city').value = '';
                document.getElementById('street').value = '';
                document.getElementById('zip').value = '';
                document.getElementById('latitude').value = '';
                document.getElementById('longitude').value = '';

                addLocationButton.style.display = 'block';
                addLocationDiv.style.display = 'none';
                editLocationDiv.style.display = 'none';

                loadAllLocations();
            },
            error: function (xhr) {
                console.log(xhr);
                console.log('ERROR updateLocation(): ' + xhr.status + ' | ' + xhr.statusText);
            }
        });
    }
}

function addLocation() {
    if (validateForm('add') === true) {
        $.ajax({
            type: 'POST',
            url: url + '/html/locations',
            data: JSON.stringify({
                name: document.getElementById('addName').value,
                description: document.getElementById('addDescription').value,
                country: document.getElementById('addCountry').value,
                state: document.getElementById('addState').value,
                city: document.getElementById('addCity').value,
                street: document.getElementById('addStreet').value,
                zip: document.getElementById('addZip').value,
                latitude: document.getElementById('addLatitude').value,
                longitude: document.getElementById('addLongitude').value
            }),
            dataType: 'JSON',
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
            success: function () {
                console.log('SUCCESS addLocation()');

                document.getElementById('addName').value = '';
                document.getElementById('addDescription').value = '';
                document.getElementById('addCountry').value = '';
                document.getElementById('addState').value = '';
                document.getElementById('addCity').value = '';
                document.getElementById('addStreet').value = '';
                document.getElementById('addZip').value = '';
                document.getElementById('addLatitude').value = '';
                document.getElementById('addLongitude').value = '';

                addLocationButton.style.display = 'block';
                addLocationDiv.style.display = 'none';
                editLocationDiv.style.display = 'none';

                loadAllLocations();
            },
            error: function (xhr) {
                console.log(xhr);
                console.log('ERROR addLocation(): ' + xhr.status + ' | ' + xhr.statusText);
            }
        });
    }
}

//==============================================================================
//      Helpers
//==============================================================================
function openAddLocationDiv() {
    addLocationButton.style.display = 'none';
    addLocationDiv.style.display = 'block';
    editLocationDiv.style.display = 'none';
}

function validateForm(appendAddString) {
    var re = /^[a-zA-Z0-9 ]+$/i,
            reZip = /^([0-9]{5})+$/,
            reLetters = /^[a-zA-Z ]+$/i,
            reLongLat = /([0-9]{3})([\.]{1})([0-9]{6})$/,
            formString = '',
            formErrorDiv,
            name,
            description,
            country,
            state,
            city,
            street,
            zip,
            latitude,
            longitude;

    if (appendAddString === 'add') {
        formErrorDiv = document.getElementById('addFormErrors');
        name = document.getElementById('addName');
        description = document.getElementById('addDescription');
        country = document.getElementById('addCountry');
        state = document.getElementById('addState');
        city = document.getElementById('addCity');
        street = document.getElementById('addStreet');
        zip = document.getElementById('addZip');
        latitude = document.getElementById('addLatitude');
        longitude = document.getElementById('addLongitude');
    } else if (appendAddString === '') {
        formErrorDiv = document.getElementById('formErrors');
        name = document.getElementById('name');
        description = document.getElementById('description');
        country = document.getElementById('country');
        state = document.getElementById('state');
        city = document.getElementById('city');
        street = document.getElementById('street');
        zip = document.getElementById('zip');
        latitude = document.getElementById('latitude');
        longitude = document.getElementById('longitude');
    } else {
        console.log('ERROR: validateForm() passed in wrong value. ' + appendAddString);
        return false;
    }

    //longest place name
    //Taumatawhakatangi­hangakoauauotamatea­turipukakapikimaunga­horonukupokaiwhen­uakitanatahu
    //DATABASE : 50
    if (name.value.length > 50 || !re.test(name.value)) {
        name.style.border = '2px solid red';
        name.style.background = '#ffe8e8';

        formString += '<div class="errorDiv">';
        formString += 'ERROR: INPUT A VALID NAME (Letters A - Z) (Numbers 0 - 9) ( Less then 91 characters )';
        formString += '</div>';
    } else {
        name.style.border = '2px solid green';
        name.style.background = '#F0FFF0';
    }

    //DATABASE : 500
    if (description.value.length > 500 || !re.test(description.value)) {
        description.style.border = '2px solid red';
        description.style.background = '#ffe8e8';

        formString += '<div class="errorDiv">';
        formString += 'ERROR: INPUT A VALID DESCRIPTION (Letters A - Z) (Numbers 0 - 9)';
        formString += '</div>';
    } else {
        description.style.border = '2px solid green';
        description.style.background = '#F0FFF0';
    }

    //longest country name
    //the Separate Customs Territory of Taiwan, Penghu, Kinmen, and Matsu (Chinese Taipei)
    //DATABASE : 50
    if (country.value.length > 50 || !reLetters.test(country.value)) {
        country.style.border = '2px solid red';
        country.style.background = '#ffe8e8';

        formString += '<div class="errorDiv">';
        formString += 'ERROR: INPUT A VALID COUNTRY (Letters A - Z) ( Less then 86 characters )';
        formString += '</div>';
    } else {
        country.style.border = '2px solid green';
        country.style.background = '#F0FFF0';
    }

    //longest US state name:
    //State of Rhode Island and Providence Plantations
    //DATABASE : 50
    if (state.value.length > 50 || !reLetters.test(state.value)) {
        state.style.border = '2px solid red';
        state.style.background = '#ffe8e8';

        formString += '<div class="errorDiv">';
        formString += 'ERROR: INPUT A VALID STATE (Letters A - Z) ( Less then 51 characters )';
        formString += '</div>';
    } else {
        state.style.border = '2px solid green';
        state.style.background = '#F0FFF0';
    }

    //longest city/village name:
    //Llanfair­pwllgwyn­gyllgo­gerychwyrn­drobwll­llanty­silio­gogogoch
    //DATABASE : 50
    if (city.value.length > 50 || !reLetters.test(city.value)) {
        city.style.border = '2px solid red';
        city.style.background = '#ffe8e8';

        formString += '<div class="errorDiv">';
        formString += 'ERROR: INPUT A VALID CITY (Letters A - Z) ( Less then 70 characters )';
        formString += '</div>';
    } else {
        city.style.border = '2px solid green';
        city.style.background = '#F0FFF0';
    }

    //longest street / place name
    //Taumatawhakatangi­hangakoauauotamatea­turipukakapikimaunga­horonukupokaiwhen­uakitanatahu
    //DATABASE : 50
    if (street.value.length > 50 || !re.test(street.value)) {
        street.style.border = '2px solid red';
        street.style.background = '#ffe8e8';

        formString += '<div class="errorDiv">';
        formString += 'ERROR: INPUT A VALID STREET (Letters A - Z) (Numbers 0 - 9) ( Less then 91 characters )';
        formString += '</div>';
    } else {
        street.style.border = '2px solid green';
        street.style.background = '#F0FFF0';
    }

    //longest zip
    //US: 99950
    if (!reZip.test(zip.value)) {
        zip.style.border = '2px solid red';
        zip.style.background = '#ffe8e8';

        formString += '<div class="errorDiv">';
        formString += 'ERROR: INPUT A VALID ZIP ( Exact Format: 12345 ) ( No Spaces )';
        formString += '</div>';
    } else {
        zip.style.border = '2px solid green';
        zip.style.background = '#F0FFF0';
    }

    //Decimal Percision             9
    //Decimal Places to the right   3
    //Decimal Places to the left    6
    if (latitude.value.length !== 10 || !reLongLat.test(latitude.value)) {
        latitude.style.border = '2px solid red';
        latitude.style.background = '#ffe8e8';

        formString += '<div class="errorDiv">';
        formString += 'ERROR: INPUT A VALID LATITUDE ( Exact Format: 123.123456 ) ( No Spaces )';
        formString += '</div>';
    } else {
        latitude.style.border = '2px solid green';
        latitude.style.background = '#F0FFF0';
    }

    //Decimal Percision             9
    //Decimal Places to the right   3
    //Decimal Places to the left    6
    if (longitude.value.length !== 10 || !reLongLat.test(longitude.value)) {
        longitude.style.border = '2px solid red';
        longitude.style.background = '#ffe8e8';

        formString += '<div class="errorDiv">';
        formString += 'ERROR: INPUT A VALID LATITUDE ( Exact Format: 123.123456 ) ( No Spaces )';
        formString += '</div>';
    } else {
        longitude.style.border = '2px solid green';
        longitude.style.background = '#F0FFF0';
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
    addLocationButton.style.display = 'block';
    addLocationDiv.style.display = 'none';
    editLocationDiv.style.display = 'none';

    loadAllLocations();
});