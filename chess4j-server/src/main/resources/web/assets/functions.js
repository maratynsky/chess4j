function getPiece(p) {
    var pp = {'k': '&#9818;',
        'q': '&#9819;',
        'r': '&#9820;',
        'b': '&#9821;',
        'n': '&#9822;',
        'p': '&#9823;'};
    return pp[p.toLowerCase()];
}