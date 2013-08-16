package com.tukhvatullin.chess4j.game;


import java.util.List;

/**
 * Date: 4/5/13
 * Author: Marat Tukhvatullin pokmeptb@gmail.com
 */
public class MoveResponse {

  private Move.Type type;
  private List<Action> actions;

  public static MoveResponse cantMove() {
    return new MoveResponse(Move.Type.CANTMOVE, null);
  }

  public MoveResponse(Move.Type type, List<Action> actions) {
    this.type = type;
    this.actions = actions;
  }

  public static MoveResponse kingIsUnderAttack() {
    return new MoveResponse(Move.Type.KINGISUNDERATTACK, null);
  }


  public static class Action {
    private String from;
    private String to;

    public Action(String from, String to) {
      this.from = from;
      this.to = to;
    }
  }
}
