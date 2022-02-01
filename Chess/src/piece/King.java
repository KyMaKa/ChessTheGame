package piece;

import chessboard.ChessBoard;
import chessboard.Player;
import java.util.ArrayList;

public class King extends Piece {
    /**
     * Constructor: Initialize a King Object
     * @param board the board we are currently using
     * @param player the player that holds the piece
     */
    public King(ChessBoard board, Player player){
        super("king", board, player);
        if(player == Player.WHITE){  // White player
            this.piece_image_path = "assets/white_king.png";
            if(board.getKing1() == null){
                board.setKing1(this);
            }
            else{
                System.out.println("ERROR: There are more than one king");
            }
        }
        else{ // Black player
            this.piece_image_path = "assets/black_king.png";
            if(board.getKing2() == null){
                board.setKing2(this);
            }
            else{
                System.out.println("ERROR: There are more than one king");
            }
        }
    }

    /**
     * Get all possible move coordinates for this king piece at current coordinate
     *
     *                   P: this piece
     *                   @: Possible coordinates to move
     *     @ @ @
     *     @ P @
     *     @ @ @
     *
     * @return ArrayList<Coordinate> Object that contains all possible move coordinates.
     */
    public ArrayList<Coordinate> getPossibleMoveCoordinate() {
        int current_x_coord = this.x_coordinate;       // get current x coord of pawn
        int current_y_coord = this.y_coordinate;       // get current y coord of pawn
        ChessBoard board = this.board;            // get game board
        ArrayList<Coordinate> coords = new ArrayList<Coordinate>();          // create return ArrayList

        // check left top
        addToCoordinatesIfValid(coords, current_x_coord - 1, current_y_coord + 1); // add to coords if valid.


        // check top
        addToCoordinatesIfValid(coords, current_x_coord, current_y_coord + 1); // add to coords if valid.

        // check right top
        addToCoordinatesIfValid(coords, current_x_coord + 1, current_y_coord + 1); // add to coords if valid.


        // check left
        addToCoordinatesIfValid(coords, current_x_coord - 1, current_y_coord); // add to coords if valid.


        // check right
        addToCoordinatesIfValid(coords, current_x_coord + 1, current_y_coord); // add to coords if valid.


        // check left bottom
        addToCoordinatesIfValid(coords, current_x_coord - 1, current_y_coord - 1); // add to coords if valid.

        // check bottom
        addToCoordinatesIfValid(coords, current_x_coord, current_y_coord - 1); // add to coords if valid.


        // check right bottom
        addToCoordinatesIfValid(coords, current_x_coord + 1, current_y_coord - 1); // add to coords if valid.


        return coords;
    }

    /**
     * Check whether the king is in check
     * @return true if the king is in check; otherwise return false
     */
    public boolean isInCheck(){
        ChessBoard board = this.board;   // get current game board
        ArrayList<Piece> opponent_pieces;

        // get opponent's pieces
        if(this.player == Player.WHITE){
            opponent_pieces = this.board.getBlack_pieces();
        }
        else{
            opponent_pieces = this.board.getWhite_pieces();
        }
        for( Piece p : opponent_pieces){ // get opponent piece
            if (p.x_coordinate == -1 || p.y_coordinate == -1) // invalid coord
                continue;
            ArrayList<Coordinate> coords = p.getPossibleMoveCoordinate();
            for(Coordinate coord : coords){
                if (coord.getX() == this.x_coordinate && coord.getY() == this.y_coordinate){  // opponent's next move could reach the king
                    return true; // is in check
                }
            }
        }
        return false;
    }
}