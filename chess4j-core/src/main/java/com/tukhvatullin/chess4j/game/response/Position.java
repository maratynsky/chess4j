package com.tukhvatullin.chess4j.game.response;

public class Position {
    private final char col;
    private final int row;

    public Position(char col, int row) {
      this.col = col;
      this.row = row;
    }

    public char getCol() {
      return col;
    }

    public int getRow() {
      return row;
    }

    @Override
    public String toString() {
      return col + "" + row;
    }
}