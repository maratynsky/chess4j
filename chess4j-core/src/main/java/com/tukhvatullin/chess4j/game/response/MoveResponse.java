package com.tukhvatullin.chess4j.game.response;


import com.tukhvatullin.chess4j.game.Move;

/**
 * Date: 4/5/13
 * Author: Marat Tukhvatullin pokmeptb@gmail.com
 */
public class MoveResponse {

  protected Move.Type type;
  protected boolean check = false;

  public MoveResponse(Move.Type type) {
    this.type = type;
  }

  public Move.Type getMoveType() {
    return type;
  }

  @Override
  public String toString() {
    return type.toString() + (check?" (CHECK)":"");
  }

  public MoveResponse check(boolean check){
    this.check = check;
    return this;
  }

  public boolean isCheck() {
    return check;
  }
}
