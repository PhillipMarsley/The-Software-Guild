function isEven(num) {
    if (num % 2 == 0) {
        return true;
    }
    return false;
}

var evenNumContainer = [];

function pushEvens(startNumber, endNumber, step) {
    for (var i = startNumber; i < endNumber; i += step) {
        if (isEven(i)) {
            evenNumContainer.push(i);
        }
    }
}

function createPrintResults(startNumber, endNumber, step) {
    var resultsString = 'Here are the even numbers between '
        + startNumber
        + ' and '
        + endNumber
        + ' by '
        + step
        + 's';

    for (var i = 0; i < evenNumContainer.length; i++) {
        resultsString += '<br />'
        resultsString += evenNumContainer[i];
    }

    return resultsString;
}

function clear() {
    evenNumContainer = [];
    document.getElementById('resultText').innerHTML = " ";
}

function printResults() {
    clear();

    startNumber = document.getElementById('startNumber').value;
    endNumber = document.getElementById('endNumber').value;
    step = document.getElementById('step').value;

    if (isNaN(startNumber) || isNaN(endNumber) || isNaN(step)) {
        alert('Entry is not numeric');
        return;
    }

    if (endNumber <= startNumber) {
        alert('The ending number is less than or equal to the starting number');
        return;
    }

    if (step < 0) {
        alert('Step is not a positive number');
        return;
    }

    pushEvens(Number(startNumber), Number(endNumber), Number(step));

    document.getElementById('resultText').innerHTML = createPrintResults(startNumber, endNumber, step);
}