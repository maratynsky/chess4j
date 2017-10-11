chess4j
=======

Chess engine java library

Usage:
-------
```java
Game game = new Game();
System.out.println(game);
Move move = new Move('e',2,'e',4);
MoveResponse response = game.move(move);
System.out.println(response);
System.out.println(game);
```
Author: Marat Tukhvatullin <pokmeptb@gmail.com>
