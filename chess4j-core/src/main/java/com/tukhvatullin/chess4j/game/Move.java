package com.tukhvatullin.chess4j.game;

/**
 * Date: 4/1/13
 * Author: Marat Tukhvatullin pokmeptb@gmail.com
 */
public class Move {

  private char pieceCode;
  private char colFrom;
  private int rowFrom;
  private char colTo;
  private int rowTo;

  public Move(char pieceCode, char colFrom, int rowFrom,
              char colTo, int rowTo) {
    this.pieceCode = pieceCode;
    this.colFrom = colFrom;
    this.rowFrom = rowFrom;
    this.colTo = colTo;
    this.rowTo = rowTo;
  }

  public char getColFrom() {
    return colFrom;
  }

  public int getRowFrom() {
    return rowFrom;
  }

  public char getColTo() {
    return colTo;
  }

  public int getRowTo() {
    return rowTo;
  }

  public char getPieceCode() {
    return pieceCode;
  }

  public String getFrom() {
    return String.valueOf(colFrom) + rowFrom;

  }

  public String getTo() {
    return String.valueOf(colTo) + rowTo;
  }

  public static enum Type {
    MOVENMENT,
    ATTACK,
    CASTLING,
    PROMOTION,
    ENPASSANT,
    CANTMOVE,
    KINGISUNDERATTACK
  }

  @Override
  public String toString() {
    return new StringBuilder().append(pieceCode)
        .append(colFrom).append(rowFrom)
        .append(colTo).append(rowTo).toString();

  }
}
