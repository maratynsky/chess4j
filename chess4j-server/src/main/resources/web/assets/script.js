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
            case "ENPASSANT":
            case "CASTLING":
                move(response.actions);
                break;
            case "PROMOTION":
                promotion(response.actions);
            default:
                moveBack();
        }
    };

    function promotion(actions) {
        $("#" + actions[0].from).empty();
        var color = "white";
        var queen = $("<span class='piece " + color + "'>" + getPiece('q') + "</span>");
        $("#" + actions[0].to).empty().append(queen);
        initDraggable(queen);

    }

    function move(actions) {
        for (var k in actions) {
            var action = actions[k];
            var piece = !!action.from ? $("#" + action.from).children(".piece") : null;
            $("#" + action.to).empty().append(piece);
            if (!!piece) {
                piece.css("top", "0px");
                piece.css("left", "0px");
            }
        }
    }

    var from;

    function moveBack() {
        var piece = $("#" + from).children(".piece");
        piece.css("top", "0px");
        piece.css("left", "0px");
    }


    $.ajax('/getgame').done(function (response) {
        var color = response.color.toLowerCase();
        $("#name").html(getPiece(response.rank)+" "+response.name);
        if(!!response.enemy_name){
            $("#enemy_name").html(getPiece(response.enemy_rank)+" "+response.enemy_name);
        }
        $("#board").addClass(color).load("/assets/board_"+color+".html", function(){
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
                    } else {
                        moveBack();
                    }
                }
            });
        });

    });

    function initDraggable(items) {
        items.draggable({
            zIndex: 100,
            start: function (event, ui) {
                var id = $(this).parent().attr("id");
                from = id;
            }
        });
    }


});