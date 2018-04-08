var canvas = document.getElementById('gameCanvas');
var gc = canvas.getContext('2d');
var gameScreen = document.getElementById('gameScreen');

//==============================================================================
//      TO DO
//==============================================================================
//      Update Graphics
//          Background, player, etc.
//      Add Collision
//          Player can jump on top of blocks, pipes, and hit the blocks
//      Allow player to hit blocks
//          Add in block hit-animation
//      Add menu event when blocks are hit (window.location = ?)

const GRAVITY = 0.5;

var onGround = false;

var player;
var block1;
var block2;
var block3;
var block4;
var pipeTop1;
var pipeBase1;
var pipeTop2;
var pipeBase2;
var grass;
var ground;

window.requestAnimFrame = (function(){
      return  window.requestAnimationFrame       || 
              window.webkitRequestAnimationFrame || 
              window.mozRequestAnimationFrame    || 
              window.oRequestAnimationFrame      || 
              window.msRequestAnimationFrame     || 
              function(/* function */ callback, /* DOMElement */ element){
                window.setTimeout(callback, 1000 / 60);
              };
    })();

var gameArea = {
    canvas: document.getElementById('gameCanvas'),
    clear: function () {
        gc.clearRect(0, 0, this.canvas.width, this.canvas.height);
    },
    paint: function () {
        gc.fillStyle = this.color;
        gc.fillRect(0, 0, this.canvas.width, this.canvas.height);
    },
    start: function () {
        this.canvas.width = gameScreen.clientWidth - (gameScreen.offsetWidth - gameScreen.clientWidth);
        this.canvas.height = gameScreen.clientHeight - ((gameScreen.offsetHeight - gameScreen.clientHeight) / 3);
        this.groundLevel = canvas.height - 40;
        this.context = this.canvas.getContext('2d');
        this.keys = gameArea.keys || [];
        this.color;

        window.addEventListener('keydown', function (event) {
            if (event.keyCode === 32 && event.target === document.body) {
                //prevents page from scrolling down when spacebar is pressed
                event.preventDefault();
            }
            gameArea.keys[event.keyCode] = (event.type === "keydown");
        });
        window.addEventListener('keyup', function (event) {
            gameArea.keys[event.keyCode] = (event.type === "keydown");
        });
    }
}

function component(width, height, posX, posY) {
    this.gamearea = gameArea;
    this.width = width;
    this.height = height;
    this.velocityX = 0;
    this.velocityY = 0;
    this.posX = posX;
    this.posY = posY;
    this.color;
    this.paint = function () {
        gc = gameArea.context;
        gc.fillStyle = this.color;
        gc.fillRect(this.posX, this.posY, this.width, this.height);
    }
    this.newPos = function () {
        this.posX += this.velocityX;
        this.posY += this.velocityY;
    }
}

function buildGame() {
    //width, height, color, posX, posY  
    ground = new component(gameArea.canvas.width, 30, 0, gameArea.canvas.height - 30);
    grass = new component(gameArea.canvas.width, 10, 0, gameArea.canvas.height - 40);

    block1 = new component(50, 50, gameArea.canvas.width / 100 * 20 - 20, ((gameArea.canvas.height / 2) * 0.85) - 20);
    block2 = new component(50, 50, gameArea.canvas.width / 100 * 40 - 20, ((gameArea.canvas.height / 2) * 0.85) - 20);
    block3 = new component(50, 50, gameArea.canvas.width / 100 * 60 - 20, ((gameArea.canvas.height / 2) * 0.85) - 20);
    block4 = new component(50, 50, gameArea.canvas.width / 100 * 80 - 20, ((gameArea.canvas.height / 2) * 0.85) - 20);

    pipeTop1 = new component(80, 30, -40, gameArea.canvas.height - 150 - 30);
    pipeBase1 = new component(55, 150 - 40, -27.5, gameArea.canvas.height - 150);
    pipeTop2 = new component(80, 30, gameArea.canvas.width - 40, gameArea.canvas.height - 150 - 30);
    pipeBase2 = new component(55, 150 - 40, gameArea.canvas.width - 27.5, gameArea.canvas.height - 150);

    player = new component(35, 60, gameArea.canvas.width / 2, 120);
}

function update() {
    player.velocityX -= 0.2;

    if (player.velocityX > 6) {
        player.velocityX = 6;
    }

    if (gameArea.keys && gameArea.keys[65]) /* A */ {
        player.velocityX += 0.6;

        if (player.posX <= 0 || player.velocityX < 0) {
            player.velocityX = 0;
        }

        player.posX -= player.velocityX;
    }

    if (gameArea.keys && gameArea.keys[68]) /* D */ {
        player.velocityX += 0.6;

        if (player.posX >= gameArea.canvas.width - player.width || player.velocityX < 0) {
            player.velocityX = 0;
        }

        player.posX += player.velocityX;
    }

    player.velocityY += GRAVITY;
    player.posY += player.velocityY;

    if (player.posY > gameArea.groundLevel - player.height)
    {
        player.posY = gameArea.groundLevel - player.height;
        player.velocityY = 0;
        onGround = true;
    }

    if (gameArea.keys && gameArea.keys[32]) {
        if (onGround) {
            player.velocityY = -13;
            onGround = false;
        }
    } else {
        if (player.velocityY < -6.5) {
            player.velocityY = -6.5;
        }
    }
}

function backgroundGradient(width, height, posX, posY) {
    var gradient = gc.createLinearGradient(0, 0, 0, height);
    gradient.addColorStop(0, "#75c2fa");
    gradient.addColorStop(1, "white");

    return gradient;
}

function pipeGradient(width, height, posX, posY) {
    var x2 = posX + width;

    var gradient = gc.createLinearGradient(posX, 0, x2, 0);
    gradient.addColorStop(0, "#33cc33");
    gradient.addColorStop(0.5, "white");
    gradient.addColorStop(1, "#33cc33");

    return gradient;
}

function boxGradient(width, height, posX, posY) {
    var x2 = posX + width;
    var y2 = posY + height;

    var gradient = gc.createLinearGradient(posX, posY, x2, y2);
    gradient.addColorStop(0, "#ffaf33");
    gradient.addColorStop(1, "#fffb00");

    return gradient;
}

function groundGradient(width, height, posX, posY) {
    var y2 = posY + height;

    var gradient = gc.createLinearGradient(0, posY, 0, y2);
    gradient.addColorStop(0, "#cd8024");
    gradient.addColorStop(1, "#723e00");

    return gradient;
}

function grassGradient(width, height, posX, posY) {
    var y2 = posY + height;

    var gradient = gc.createLinearGradient(0, posY, 0, y2);
    gradient.addColorStop(0, "#1fff00");
    gradient.addColorStop(1, "#15ab00");

    return gradient;
}

function playerGradient(width, height, posX, posY) {
    var x2 = posX + width;
    var y2 = posY + height;

    var gradient = gc.createLinearGradient(posX, posY, x2, y2);
    gradient.addColorStop(0, "#ff0000");
    gradient.addColorStop(0.2, "white");
    gradient.addColorStop(0.4, "#ff0000");
    gradient.addColorStop(0.6, "white");
    gradient.addColorStop(0.8, "#ff0000");
    gradient.addColorStop(1, "white");

    return gradient;
}

function draw() {
    //background
    gameArea.clear();
    gameArea.color = backgroundGradient(gameArea.canvas.width, gameArea.canvas.height, 0, 0);
    gameArea.paint();

    ground.color = groundGradient(ground.width, ground.height, ground.posX, ground.posY);
    ground.paint();
    grass.color = grassGradient(grass.width, grass.height, grass.posX, grass.posY);
    grass.paint();

    //objects
    block1.color = boxGradient(block1.width, block1.height, block1.posX, block1.posY);
    block1.paint();
    block2.color = boxGradient(block2.width, block1.height, block2.posX, block2.posY);
    block2.paint();
    block3.color = boxGradient(block3.width, block1.height, block3.posX, block3.posY);
    block3.paint();
    block4.color = boxGradient(block4.width, block1.height, block4.posX, block4.posY);
    block4.paint();

    pipeTop1.color = pipeGradient(pipeTop1.width, pipeTop1.height, pipeTop1.posX, pipeTop1.posY);
    pipeTop1.paint();
    pipeBase1.color = pipeGradient(pipeBase1.width, pipeBase1.height, pipeBase1.posX, pipeBase1.posY);
    pipeBase1.paint();
    pipeTop2.color = pipeGradient(pipeTop2.width, pipeTop2.height, pipeTop2.posX, pipeTop2.posY);
    pipeTop2.paint();
    pipeBase2.color = pipeGradient(pipeBase2.width, pipeBase2.height, pipeBase2.posX, pipeBase2.posY);
    pipeBase2.paint();

    //player
    player.color = playerGradient(player.width, player.height, player.posX, player.posY);
    player.paint();
}

function gameLoop() {
    requestAnimFrame (gameLoop);
    update();
    draw();
}

$(window).bind("load", function () {
    gameArea.start();
    buildGame();
    gameLoop();
});