package com.tukhvatullin.chess4j.game.response;

import com.tukhvatullin.chess4j.game.Move;

/**
 * @author pokmeptb
 */
public class KingIsUnderAttackResponse extends MoveResponse {
  public KingIsUnderAttackResponse() {
    super(Move.Type.KINGISUNDERATTACK);
  }
}
