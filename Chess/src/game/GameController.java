package game;
import chessboard.ChessBoard;
import chessboard.Player;
import java.io.FileNotFoundException;
import piece.Coordinate;
import piece.King;
import piece.Piece;
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import ui.UiController;

/**
 * Game Controller
 */
public class GameController {
    protected ChessBoard board;  // game board
    protected UiController uiController; // game view
    protected Piece chosen_piece;  // the piece that is chosen by player
    protected boolean game_start; // game already starts?
    protected String player1_name; // player1 name
    protected String player2_name; // player2 name
    protected String message;    // game message

    /**
     * Constructor: initialize game controller
     * @param uiController
     */
    public GameController(UiController uiController){
        //this.board = board;      // bind chessboard to current game controller
        this.uiController = uiController; // bind game view to current game controller
        this.chosen_piece = null; // no piece is chosen by player yet
        this.game_start = false;  // game is not started yet, need to click start button.
        this.message = "Press Start button to start the game"; // game message
    }

    /**
     * Check whether player's king is in check
     * @param player check this player's king
     * @return true if king is in check.
     */
    public boolean playersKingIsInCheck(Player player){
        Piece king;
        if (player == Player.WHITE){
            king = this.board.getKing1();
        }
        else{
            king = this.board.getKing2();
        }
        return ((King)king).isInCheck();
    }

    /**
     * check whether is checkmate or stalemate
     * @return null if neither checkmate nor stalemate; return "checkmate" if checkmate; return "stalemate" if stalemate
     */
    public String isCheckmateOrStalemate(){
        /*
         * Check checkmate and stalemate
         */
        if (this.board.playerCannotMove(this.board.getTurns() % 2 == 0 ? Player.WHITE : Player.BLACK)){ // so right now that player cannot move any game
            King king = (this.board.getTurns() % 2 == 0) ? (King)this.board.getKing1() : (King)this.board.getKing2();  // get current player's king
            if(king == null) // chessboard not initialized yet.
                return null;
            if(king.isInCheck()){ // checkmate
                return "checkmate";
            }
            else{ // stalemate
                return "stalemate";
            }
        }
        return null;
    }

    /**
     * Game is over
     * @param status
     */
    public void gameIsOver(String status){
        if(!this.game_start) // game already over
            return;
        Player current_player = this.getPlayerForThisTurn(); // get current player
        this.game_start = false; // game not started now.
        if (status.equals("checkmate")){ // checkmate
            this.message = "Checkmate! "  + (current_player == Player.WHITE ? this.player2_name : player1_name) + " Win!!"; // reset message

        }
        else{ // stalemate
            this.message = "Stalemate!"; // reset message
        }

        // redraw menu
        this.uiController.mainMenu.drawMenu(this.message);
    }

    /**
     * Return possible move coordinates for piece; eliminate suicide move
     *
     * @param p the piece we want to move.
     * @return coordinate lists
     */
    ArrayList<Coordinate> showPossibleMovesForPiece(Piece p){
        ArrayList<Coordinate> return_coords = new ArrayList<Coordinate>();
        ArrayList<Coordinate> coords = p.getPossibleMoveCoordinate(); // get all possible move coordinates for this choesn piece
        Color color;
        int x, y;
        for (Coordinate coord : coords) {
            if (this.chosen_piece.isSuicideMove(coord.getX(), coord.getY())){ // it is a suicide move, therefore player cannot make this move.
                continue;
            }

            // add this coord to return_coords
            return_coords.add(coord);
        }
        return return_coords;
    }

    /**
     * Player's piece captures opponent piece
     *
     * If it can be done, redraw the chessboard; otherwise do nothing
     * @param panel
     * @param opponent_piece             opponent piece
     */
    public void movePieceToOpponentPieceLocationIfValid(JPanel panel, Piece opponent_piece){
        // check whether opponent's piece is under capture scope
        ArrayList<Coordinate> coords = this.chosen_piece.getPossibleMoveCoordinate();
        if(coords != null){
            for(Coordinate coord : coords){
                if (this.chosen_piece.isSuicideMove(coord.getX(), coord.getY())){ // it is a suicide move, therefore player cannot make this move.
                    continue;
                }
                if (coord.getX() == opponent_piece.getX_coordinate() && coord.getY() == opponent_piece.getY_coordinate()){ // opponent's piece is captured
                    // System.out.println("You captured a piece");


                    //  remove that opponent's piece
                    this.board.removePiece(opponent_piece);

                    // move player's piece to that coordinate
                    opponent_piece.removeSelf(); // remove opponent's piece

                    // move the chosen piece to coordinate of opponent's piece
                    this.chosen_piece.setCoordinate(coord.getX(), coord.getY());

                    // update turns and redraw the canvas
                    this.chosen_piece = null;
                    this.board.incrementTurns();
                    this.message = (this.playersKingIsInCheck(this.getPlayerForThisTurn()) ? "Check! " : "") +     // show king in check
                                   (this.getPlayerForThisTurn() == Player.WHITE ? this.player1_name : this.player2_name) + "'s turn"; // show which player's turn
                    panel.repaint();
                    return;
                }
            }
        }
        else{
            // nothing happend here
        }
    }

    /**
     * Move player's piece to unoccupied tile if valid, which means the move is not a suicide move
     * @param panel
     * @param x     the x coord to move to
     * @param y     the y coord to move to
     */
    public void movePlayerPieceToEmptyTileIfValid(JPanel panel, int x, int y){
        ArrayList<Coordinate> coords = this.chosen_piece.getPossibleMoveCoordinate();
        for(Coordinate coord : coords){
            if (this.chosen_piece.isSuicideMove(coord.getX(), coord.getY())){ // it is a suicide move, therefore player cannot make this move.
                continue;
            }
            if (coord.getX() == x && coord.getY() == y){ // player can move the piece there
                // System.out.println("You moved a piece");

                // move player's piece to that coordinate
                this.chosen_piece.setCoordinate(x, y);

                // update turns and redraw the canvas
                this.chosen_piece = null;
                this.board.incrementTurns();
                this.message = (this.playersKingIsInCheck(this.getPlayerForThisTurn()) ? "Check! " : "") +     // show king in check
                        (this.getPlayerForThisTurn() == Player.WHITE ? this.player1_name : this.player2_name) + "'s turn"; // show which player's turn
                panel.repaint();
                return;
            }
        }
    }

    /**
     *
     * @return Player for this turn.
     */
    public Player getPlayerForThisTurn(){
        return this.board.getPlayerForThisTurn();
    }

    /**
     * Check user mouse click, and update gui.
     * @param g2d
     * @param clicked_x_coord
     * @param clicked_y_coord
     */
    public void checkUserClick(Graphics2D g2d, double clicked_x_coord, double clicked_y_coord){
        if(this.game_start == false){ // game is not started yet. so we don't need to check user mouse click.
            return;
        }
        int x, y;
        Piece p;
        /*
         * check mouse click.
         */
        if(clicked_x_coord >= 0 && clicked_y_coord >= 0) {     // valid click scope
            x = (int) (clicked_x_coord / 64);                  // convert to left-bottom game board coordinate system
            y = this.board.getHeight() - 1 - (int) (clicked_y_coord / 64);
            p = this.board.getPieceAtCoordinate(x, y);

            /*
             *  Now we clicked a spot/piece
             *
             */
            if (p != null) { // player clicked a piece; show its possible moves
                if(p.getPlayer() == this.board.getPlayerForThisTurn()) { // player clicked his/her own piece
                    this.chosen_piece = p;       // save as chosen_piece
                    this.uiController.chessboard_ui.drawPossibleMovesForPiece(g2d, this.showPossibleMovesForPiece(p)); // draw possible moves
                }
                else{ // player clicked opponent's piece
                    if(this.chosen_piece == null) // do nothing
                        return;
                    this.movePieceToOpponentPieceLocationIfValid(this.uiController, p);
                }
            }
            else if (this.chosen_piece != null) { // that means  p == null, and player clicked a tile that is not occupied
                this.movePlayerPieceToEmptyTileIfValid(this.uiController, x, y);
            }
        }
    }

    public void startCustomGame(int x) throws FileNotFoundException {
        ChessBoard new_board = new ChessBoard(x, x); // create new board;

        // rebind the chessboard to UiController, GameController
        this.board = new_board;
        this.uiController.setBoard(new_board);
        this.uiController.initChessUI();
        this.uiController.chessboard_ui.board = new_board;

        this.uiController.chessboard_ui.clicked_x_coord = -1;  // reset click x coord
        this.uiController.chessboard_ui.clicked_y_coord = -1;  // reset click y coord


        this.board.generateCustomBoard();
        this.uiController.redraw();

        this.game_start = true; // start game
    }

    /**
     *
     * Start a new game
     */
    public void startNewGame(int x){
        ChessBoard new_board = new ChessBoard(x, x); // create new board;

        // rebind the chessboard to UiController, GameController
        this.board = new_board;
        this.uiController.setBoard(new_board);
        this.uiController.initChessUI();
        this.uiController.chessboard_ui.board = new_board;

        this.uiController.chessboard_ui.clicked_x_coord = -1;  // reset click x coord
        this.uiController.chessboard_ui.clicked_y_coord = -1;  // reset click y coord


        this.board.generateStandardBoard();
        this.uiController.redraw();

        this.game_start = true; // start game
    }
}
