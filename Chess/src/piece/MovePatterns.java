package piece;

import chessboard.ChessBoard;
import java.util.ArrayList;

public class MovePatterns {

  ChessBoard board;
  Piece p;
  String pattern;
  public MovePatterns(ChessBoard board, Piece p, String pattern) {
    this.board = board;
    this.p = p;
    this.pattern = pattern;
  }

  public ArrayList<Coordinate> getPossibleMoves() {
    if (pattern.equals("Bishop"))
      return getMovesBishop();
    if (pattern.equals("Knight"))
      return getMoverKnight();
    if (pattern.equals("Queen"))
      return getMovesQueen();
    if (pattern.equals("Rook"))
      return getMovesRook();
    return null;
  }

  public ArrayList<Coordinate> getMovesBishop() {
    int current_x_coord = this.p.x_coordinate;       // get current x coord of pawn
    int current_y_coord = this.p.y_coordinate;       // get current y coord of pawn
    ChessBoard board = this.board;            // get game board

    // create return ArrayList
    return new ArrayList<>(
        getMovesSideways(current_x_coord, current_y_coord, board));
  }

  /**
   *  Get all possible move coordinates for this knight piece at current coordinate
   *
   *
   *               @      @
   *        @                   @       P: this piece
   *                 P                 @: Possible coordinates to move
   *        @                   @
   *              @       @

   * @return ArrayList<Coordinate> Object that contains all possible move coordinates.
   */
  public ArrayList<Coordinate> getMoverKnight() {
    ChessBoard board = this.p.getChessBoard();            // get game board
    ArrayList<Coordinate> coords = new ArrayList<>();          // create return ArrayList
    int x, y;
        /*
         several cases
                     2      3
               1                   4

               5                   8
                    6       7

         */
    // case1
    x = this.p.x_coordinate - 2;
    y = this.p.y_coordinate + 1;
    if(x >= 0 && y < board.getHeight()){
      p.addToCoordinatesIfValid(coords, x, y); // add to coords if the piece can move to that coordinate
    }
    // case2
    x = this.p.x_coordinate - 1;
    y = this.p.y_coordinate + 2;
    if(x >= 0 && y < board.getHeight()){
      p.addToCoordinatesIfValid(coords, x, y); // add to coords if the piece can move to that coordinate
    }
    // case3
    x = this.p.x_coordinate + 1;
    y = this.p.y_coordinate + 2;
    if(x < board.getWidth() && y < board.getHeight()){
      p.addToCoordinatesIfValid(coords, x, y); // add to coords if the piece can move to that coordinate
    }
    // case4
    x = this.p.x_coordinate + 2;
    y = this.p.y_coordinate + 1;
    if(x < board.getWidth() && y < board.getHeight()){
      p.addToCoordinatesIfValid(coords, x, y); // add to coords if the piece can move to that coordinate
    }
    // case5
    x = this.p.x_coordinate - 2;
    y = this.p.y_coordinate - 1;
    if(x >= 0 && y >= 0 ){
      p.addToCoordinatesIfValid(coords, x, y); // add to coords if the piece can move to that coordinate
    }
    // case6
    x = this.p.x_coordinate - 1;
    y = this.p.y_coordinate - 2;
    if(x >= 0 && y >= 0){
      p.addToCoordinatesIfValid(coords, x, y); // add to coords if the piece can move to that coordinate
    }
    // case7
    x = this.p.x_coordinate + 1;
    y = this.p.y_coordinate - 2;
    if(x < board.getWidth() && y >= 0){
      p.addToCoordinatesIfValid(coords, x, y); // add to coords if the piece can move to that coordinate
    }
    // case1
    x = this.p.x_coordinate + 2;
    y = this.p.y_coordinate - 1;
    if(x < board.getWidth() && y >= 0){
      p.addToCoordinatesIfValid(coords, x, y); // add to coords if the piece can move to that coordinate
    }


    return coords;
  }

  /**
   * Get all possible move coordinates for this queen piece at current coordinate
   *
   *        @   @   @
   *         @  @  @         P: this piece
   *          @ @ @          @: Possible coordinates to move
   *        @ @ P @ @
   *          @ @ @
   *         @  @  @
   *        @   @   @
   *
   * @return ArrayList<Coordinate> Object that contains all possible move coordinates.
   */
  public ArrayList<Coordinate> getMovesQueen() {
    int current_x_coord = this.p.x_coordinate;       // get current x coord of pawn
    int current_y_coord = this.p.y_coordinate;       // get current y coord of pawn
    ChessBoard board = this.p.getChessBoard();            // get game board
    ArrayList<Coordinate> coords = new ArrayList<>();          // create return ArrayList

    coords.addAll(getMovesSideways(current_x_coord, current_y_coord, board));
    coords.addAll(getMovesStraight(current_x_coord, current_y_coord, board));
    return  coords;
  }

  public ArrayList<Coordinate> getMovesRook() {
    int current_x_coord = this.p.x_coordinate;       // get current x coord of pawn
    int current_y_coord = this.p.y_coordinate;       // get current y coord of pawn
    ChessBoard board = this.p.getChessBoard();            // get game board
    return getMovesStraight(current_x_coord, current_y_coord, board);
  }

  /**
   *Get all possible move coordinates for this rook piece at current coordinate
   *
   *            @
   *            @          P: this piece
   *            @          @: Possible coordinates to move
   *      @ @ @ P @ @ @
   *            @
   *            @
   *            @
   *
   * @return ArrayList<Coordinate> Object that contains all possible move coordinates.
   */
  private ArrayList<Coordinate> getMovesStraight(int current_x_coord, int current_y_coord, ChessBoard board) {
    ArrayList<Coordinate> coords = new ArrayList<>();
    int i;
    // check left
    for(i = current_x_coord - 1; i >= 0; i--){
      if(p.addToCoordinatesIfValid(coords, i, current_y_coord)) // add to coords if valid; if this return true, then it meets other pieces.
        break;
    }
    // check right
    for(i = current_x_coord + 1; i < board.getWidth(); i++){
      if(p.addToCoordinatesIfValid(coords, i, current_y_coord)) // add to coords if valid; if this return true, then it meets other pieces.
        break;
    }
    // check above
    for(i = current_y_coord + 1 ; i < board.getHeight(); i++){
      if(p.addToCoordinatesIfValid(coords, current_x_coord, i)) // add to coords if valid; if this return true, then it meets other pieces.
        break;
    }
    // check below
    for(i = current_y_coord - 1; i >= 0; i--){
      if(p.addToCoordinatesIfValid(coords, current_x_coord, i)) // add to coords if valid; if this return true, then it meets other pieces.
        break;
    }
    return coords;
  }

  /**
   * Get all possible move coordinates for this bishop piece at current coordinate
   *
   *      @         @
   *       @       @          P: this piece
   *        @     @           @: Possible coordinates to move
   *         @   @
   *          @ @
   *           P
   *          @ @
   *         @   @
   *        @     @
   *       @       @
   *
   *
   * @return ArrayList<Coordinate> Object that contains all possible move coordinates.
   */
  private ArrayList<Coordinate> getMovesSideways(int current_x_coord, int current_y_coord, ChessBoard board) {
    ArrayList<Coordinate> coords = new ArrayList<>();
    int i, j;
    // go direction of left top
    for(i = current_x_coord - 1, j = current_y_coord + 1; i >= 0 && j < board.getHeight(); i--, j++){
      if(p.addToCoordinatesIfValid(coords, i, j)) // add to coords if valid; if this return true, then it meets other pieces.
        break;
    }
    // go direction of right top
    for(i = current_x_coord + 1, j = current_y_coord + 1; i < board.getWidth() && j < board.getHeight(); i++, j++){
      if(p.addToCoordinatesIfValid(coords, i, j)) // add to coords if valid; if this return true, then it meets other pieces.
        break;
    }
    // go direction of left bottom
    for(i = current_x_coord - 1, j = current_y_coord - 1; i >= 0 && j >= 0; i--, j--){
      if(p.addToCoordinatesIfValid(coords, i, j)) // add to coords if valid; if this return true, then it meets other pieces.
        break;
    }
    // go direction of right bottom
    for(i = current_x_coord + 1, j = current_y_coord - 1; i < board.getWidth() && j >= 0; i++, j--){
      if(p.addToCoordinatesIfValid(coords, i, j)) // add to coords if valid; if this return true, then it meets other pieces.
        break;
    }
    return coords;
  }
}
