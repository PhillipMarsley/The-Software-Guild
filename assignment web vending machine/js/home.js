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

/* 
Note: All cash is handled as a whole number for accuracy. 
Decimals are injected to the string after. 
Total cash is converted to a float before being sent to the server.
*/
var totalCash = 0;

var messageSuccess = "Thank you!!!";

function loadItems() {
    $.ajax({
        type: 'GET',
        url: 'http://localhost:8080/items',
        dataType: 'JSON',
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
            console.log('Sever could not be loaded');
        }
    });
}

function convertTotalToFloat(string) {
    return parseFloat(totalCash).toFixed(2);
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

function addCash(amount) {
    totalCash += amount;

    var output = stringIntoDouble(totalCash.toString(), 2);
    inputTotalChange.val(output);

    inputMessage.val('');
    inputChangeRecieved.val('');
}

function getItem(element) {
    var id = element.getElementsByClassName('number')[0].innerHTML;

    inputItemNumber.val(id);
    inputMessage.val('');
    inputChangeRecieved.val('');
}

function getVendItem(amount, id) {
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

            totalCash = 0;
            inputTotalChange.val('');
            inputChangeRecieved.val(changeString);

            //reloads items to update quanity
            loadItems();
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

//loads before images
$(document).ready(function () {
    loadItems();
    clearForms();

    buttonAddDollar.click(function () {
        return addCash(100);
    });

    buttonAddQuarter.click(function () {
        return addCash(25);
    });

    buttonAddDime.click(function () {
        return addCash(10);
    });

    buttonAddNickle.click(function () {
        return addCash(5);
    });

    buttonMakePurchase.click(function () {
        var total = parseFloat(stringIntoDouble(totalCash.toString(), 2));
        var item = inputItemNumber.val();

        getVendItem(total, item);
    });

    buttonReturnChange.click(function () {
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

        totalCash = 0;
        inputTotalChange.val('');
        inputMessage.val('');
        inputItemNumber.val('');
    });
});