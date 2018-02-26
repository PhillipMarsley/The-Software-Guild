//number of colums in the item Container
var COLUMS = 3;
var itemContainer = $('#divItems');

var buttonAddDollar = $('#addDollarButton');
var buttonAddQuarter = $('#addQuarterButton');
var buttonAddDime = $('#addDimeButton');
var buttonAddNickle = $('#addNickleButton');
var buttonMakePurchase = $('#makePurchaseButton');
var buttonReturnChange = $('#returnChangeButton');

var inputTotalChange = $('#inputTotalChange');
var inputMessage = $('#inputMessage');
var inputItemNumber = $('#inputItem');
var inputChangeRecieved = $('#inputChangeRecieved');

/* Note: All cash is handled as a whole number for accuracy. Decimals are injected to the string after. */
var totalCash = 0;

var messageSuccess = "Thank you!!!";
var messageNoQuanityLeft = "SOLD OUT!!!";

function loadItems() {
    $.ajax({
        type: 'GET',
        url: 'http://localhost:8080/items',
        dataType: 'json',
        success: function (response) {
            var counter = 0;
            itemContainer.empty();

            for (var i = 0; i < response.length; i += COLUMS) {
                var rowString = '';
                var t1 = '<div class="row">';
                for (var k = 0; k < COLUMS; k++) {
                    var t2 = '<div class="col itemBox" onclick="getItem(this)">';
                    var t3 = '<div class="row itemBoxRow number">' + response[counter].id + '</div>';
                    var t4 = '<div class="row itemBoxRow">' + response[counter].name + '</div>';
                    var t5 = '<div class="row itemBoxRow">' + response[counter].price + '</div>';
                    var t6 = '<div class="row itemBoxRow quantity"> Quantity Left: ' + response[counter].quantity + '</div>';
                    var t7 = '</div>';
                    rowString = rowString.concat(t2, t3, t4, t5, t6, t7);
                    counter++;
                }
                var t8 = '</div>';
                var ttt = t1.concat(rowString, t8);
                itemContainer.append(ttt);
            }
        },
        error: function () {

        }
    });
}

function getVendItem(amount, id) {
    console.log(amount);

    $.ajax({
        type: 'GET',
        url: 'http://localhost:8080/money/' + amount + '/item/' + id,
        datatype: 'JSON',
        success: function (response) {
            inputMessage.val(messageSuccess);

            var changeString = '';
            if (response.quarters > 0) {
                changeString = changeString.concat('Quater(s): ' + response.quarters + ' ');
            }
            if (response.dimes > 0) {
                changeString = changeString.concat('Dime(s): ' + response.dimes + ' ');
            }
            if (response.nickles > 0) {
                changeString = changeString.concat('Nickle(s): ' + response.nickles + ' ');
            }
            if (response.pennies > 0) {
                changeString = changeString.concat('Pennies: ' + response.pennies);
            }

            inputChangeRecieved.val(changeString);
        },
        error: function (response) {
            inputMessage.val(response.responseJSON.message);
        }
    });
}

function clearForms() {
    inputTotalChange.val('');
    inputMessage.val('');
    inputItemNumber.val('');
    inputChangeRecieved.val('');
}

function stringIntoDouble(string, percision) {
    var length = string.length;
    var decimalPlace = length - percision;
    var zeroCount = 0;
    var zeros = '';

    if (decimalPlace < 0) {
        zeroCount = decimalPlace * -1;
        zeros = '0';
        decimalPlace = 0;
    }

    var firstHalf = string.substring(0, decimalPlace);
    var secondHalf = string.substring(decimalPlace, length);

    for (var i = 0; i < zeroCount - 1; i++) {
        zeros = zeros.concat('0');
    }

    return firstHalf.concat('.', zeros, secondHalf);
}

function addCash(event, amount) {
    totalCash += amount;

    var output = stringIntoDouble(totalCash.toString(), 2);
    inputTotalChange.val(output);

    event.preventDefault;
    return false;
}

function convertTotalToFloat(string) {
    return parseFloat(totalCash).toFixed(2);
}

function getItem(element) {
    var id = element.getElementsByClassName('number')[0].innerHTML;
    inputItemNumber.val(id);
}

//loads before images
$(document).ready(function () {
    loadItems();
    clearForms();

    buttonAddDollar.click(function (event) {
        return addCash(event, 100);
    });

    buttonAddQuarter.click(function (event) {
        return addCash(event, 25);
    });

    buttonAddDime.click(function (event) {
        return addCash(event, 10);
    });

    buttonAddNickle.click(function (event) {
        return addCash(event, 5);
    });

    buttonMakePurchase.click(function (event) {
        var total = parseFloat(stringIntoDouble(totalCash.toString(), 2));
        var item = inputItemNumber.val();

        getVendItem(total, item);

        event.preventDefault;
        return false;
    });

    buttonReturnChange.click(function (event) {
        var total = parseInt(totalCash);

        var quarters = Math.floor(total / 25);
        total -= quarters * 25;
        var dimes = Math.floor(total / 10);
        total -= dimes * 10;
        var nickles = Math.floor(total / 5);
        total -= nickles * 5;
        var pennies = total;

        var changeString = '';
        if (quarters > 0) {
            changeString = changeString.concat('Quater(s): ' + quarters + ' ');
        }
        if (dimes > 0) {
            changeString = changeString.concat('Dime(s): ' + dimes + ' ');
        }
        if (nickles > 0) {
            changeString = changeString.concat('Nickle(s): ' + nickles + ' ');
        }
        if (pennies > 0) {
            changeString = changeString.concat('Pennies: ' + pennies);
        }

        inputChangeRecieved.val(changeString);

        event.preventDefault;
        return false;
    });
});