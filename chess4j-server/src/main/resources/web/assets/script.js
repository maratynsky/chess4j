$(function () {
  function showMessage(text) {
    console.log(text);
  }

  var ws = new WebSocket('ws://' + document.location.host + '/chess');
  showMessage('Connecting...');
  ws.onopen = function () {
    showMessage('Connected!');
    //ws.send("getBoard");
  };
  ws.onclose = function () {
    showMessage('Lost connection');
  };
  ws.onmessage = function (msg) {
    showMessage(msg.data);
    var response = $.parseJSON(msg.data);
    switch (response.type) {
      case "MOVENMENT":
      case "ATTACK":
        move(response);
        nextTurn();
        break;
      case "ENPASSANT":
        enpassant(response);
        nextTurn();
        break;
      case "CASTLING":
        castling(response);
        nextTurn();
        break;
      case "PROMOTION":
        promotion(response);
        nextTurn();
        break;
      default:
        moveBack();
    }
  }

  function castling(response){
    _move(response.kingAction);
    _move(response.rookAction);
  }

  function enpassant(response) {
    var action = response.action;
    _move(action);
    var pawn = response.pawn;
    $("#" + pos2str(pawn)).empty();
  }

  function promotion(response) {
    var action = response.action;
    _move(action);
    var char = response.piece;
    $("#" + pos2str(action.from)).empty();
    var color = "white";
    var newPiece = $("<span class='piece " + color + "'>" + getPiece(char) + "</span>");
    $("#" + pos2str(action.to)).empty().append(newPiece);
    initDraggable(newPiece);
  }

  function pos2str(pos) {
    return pos.col + '' + pos.row;
  }

  function move(response) {
    var action = response.action;
    _move(action);
  }

  function _move(action){
    $(".piece.last-move").removeClass("last-move");
    var piece = $("#" + pos2str(action.from)).children(".piece");
    $("#" + pos2str(action.to)).empty().append(piece);
    if (!!piece) {
      piece.css("top", "0px");
      piece.css("left", "0px");
      piece.addClass("last-move");
    }
  }

  var from;

  function moveBack() {
    var piece = $("#" + from).children(".piece");
    piece.css("top", "0px");
    piece.css("left", "0px");
  }

  var turn = null;
  function nextTurn(){
    $(".username."+turn).removeClass("turn");
    turn = turn=='white'?'black':'white';
    $(".username."+turn).addClass("turn");
  }

  $("#board").addClass("white").load("/assets/board_white.html");

  function loadGame(){
    $.ajax('/getgame').done(function (response) {
      var color = response.color.toLowerCase();
      var enemyColor = color=='white'?'black':'white';
      $("#name").html(getPiece(response.rank) + " " + response.name).addClass(color);
      if (!!response.enemy_name) {
        $("#enemy_name").html(getPiece(response.enemy_rank) + " " + response.enemy_name).addClass(enemyColor);
      }
      turn = response.turn.toLowerCase();
      $(".username."+response.turn).addClass('turn');
      $("#board").removeClass("black white").addClass(color).load("/assets/board_" + color + ".html", function () {
        var map = response.map;
        for (var k in map) {
          var p = map[k];
          var color = p >= 'a' && p <= 'z' ? "black" : "white";
          var piece = getPiece(p);
          $("#" + k).html("<span class='piece " + color + "'>" + piece + "</span>");
        }

        initDraggable($("span.piece"));
        $(".board td").droppable({
          drop: function (event, ui) {
            var id = $(this).attr("id");
            if (!!from && !!id) {
              ws.send(from + id);
            }
            else {
              moveBack();
            }
          }
        });
      });

    });
  }

  loadGame();


  function initDraggable(items) {
    items.draggable({
      zIndex: 100,
      start: function (event, ui) {
        var id = $(this).parent().attr("id");
        from = id;
      }
    });
//    items.touch({
//      animate: true,
//      sticky: false,
//      dragx: true,
//      dragy: true,
//      rotate: true,
//      resort: true,
//      scale: true
//    });
  }


});