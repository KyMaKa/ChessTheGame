package piece;

import chessboard.ChessBoard;
import chessboard.Player;
import java.util.ArrayList;

public class Pawn extends Piece {
    private boolean first_time_move;  // this flag check whether this is first time for Pawn to move, if so, it can advance 2 squares forward.

    /**
     * Constructor: initialize a Pawn Object
     * @param board the board that we are currently using
     * @param player the player that holds the piece
     */
    public Pawn(ChessBoard board, Player player) {
        super("pawn", board, player);
        this.first_time_move = true;   // it is pawn's first move, so it can advance 2 squares now.
        if (player == Player.WHITE) {  // White player
            this.piece_image_path = "assets/white_pawn.png";
        } else { // Black player
            this.piece_image_path = "assets/black_pawn.png";
        }
    }

    /**
     * check whether it is pawn's first time to move
     * @return true if it is first time move; otherwise return false.
     */
    public boolean isFirstTimeMove(){
        return this.first_time_move;
    }

    /**
     * Setter: set first_time_move
     * @param flag
     */
    public void setFirst_time_move(boolean flag){
        this.first_time_move = flag;
    }

    /**
     * Assume no promotion for pawn
     *
     * Get all possible move coordinates for this pawn piece at current coordinate
     *
     *       @             X @ X         @: Possible Coordinate to move to
     *     X @ X             P           P: this piece
     *       P                           X: opponent's piece
     *
     * @return ArrayList<Coordinate> Object that contains all possible move coordinates.
     */
    public ArrayList<Coordinate> getPossibleMoveCoordinate() {
        int current_x_coord = this.x_coordinate;       // get current x coord of pawn
        int current_y_coord = this.y_coordinate;       // get current y coord of pawn
        ChessBoard board = this.getChessBoard();            // get game board

        ArrayList<Coordinate> coords = new ArrayList<Coordinate>();          // create return ArrayList
        int possible_move = (this.player == Player.WHITE) ? 1 : -1;                // if White player, then move +1, otherwise move -1
        if (current_y_coord + possible_move >= board.getHeight() || current_y_coord + possible_move < 0) { // reach top/bottom
            return coords;
        }

        if (board.getPieceAtCoordinate(current_x_coord, current_y_coord + possible_move) != null) { // check piece in front, and the square is occupied.
            // do nothing
        } else {
            coords.add(new Coordinate(current_x_coord, current_y_coord + possible_move));  // could go to that unoccupied square
        }
        if (this.first_time_move &&                                                       // check first_time_move => advance 2 squares
                board.getPieceAtCoordinate(current_x_coord, current_y_coord + possible_move) == null &&       // both squares ahead are not occupied
                board.getPieceAtCoordinate(current_x_coord, current_y_coord + possible_move * 2) == null) {   // first move, therefore it can advance two more steps
            coords.add(new Coordinate(current_x_coord, current_y_coord + possible_move * 2));
        }

        if (board.getPieceAtCoordinate(current_x_coord - 1, current_y_coord + possible_move) != null &&
                board.getPieceAtCoordinate(current_x_coord - 1, current_y_coord + possible_move).player != this.player) {  // there is an opponent piece on the left side
            coords.add(new Coordinate(current_x_coord - 1, current_y_coord + possible_move));
        }

        if (board.getPieceAtCoordinate(current_x_coord + 1, current_y_coord + possible_move) != null &&
                board.getPieceAtCoordinate(current_x_coord + 1, current_y_coord + possible_move).player != this.player) {  // there is an opponent piece on the right side
            coords.add(new Coordinate(current_x_coord + 1, current_y_coord + possible_move));
        }
        return coords;
    }

    /**
     * Set coordinate for pawn object.
     * Change its first_time_move flag if necessary
     * @param x         the x coordinate to put the piece
     * @param y         the y coordinate to put the piece
     * @return
     */
    public boolean setCoordinate(int x, int y) {
        if (this.x_coordinate == -1 || this.y_coordinate == -1) { // first time init
            this.first_time_move = true;
        } else {
            this.first_time_move = false;
        }
        return super.setCoordinate(x, y);
    }

    /**
     * Set coordinate for pawn object without modifying the first_time_move_flag
     * @param x        the x coordinate to put the piece
     * @param y        the y coordinate to put the piece
     * @return
     */
    public boolean setCoordinateWithoutChangingFirstTimeMoveFlag(int x, int y){
        return super.setCoordinate(x, y);
    }
}