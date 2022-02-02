package chessboard;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.stream.JsonReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Objects;
import piece.*;

import java.util.ArrayList;

/**
 * This is the chessboard class(Model)
 */
public class ChessBoard {
    protected int width;           // the width of game board
    protected int height;          // the height of game board
    private Piece tiles[][];    // this 2d array is used to save pieces
    protected Piece king1;         // king for player1 White
    protected Piece king2;         // king for player2 Black
    private ArrayList<Piece> white_pieces; // array list used to save all white pieces
    private ArrayList<Piece> black_pieces; // array list used to save all black pieces
    protected int turns;  // game turn

    /**
     *
     * Construct game board given width and height
     *
     *      Coordinate system
     *
     *      +
     *      |
     *      |
     *      |
     *      |
     *      (0, 0) -------->  +
     *
     *
     * @param width:  set the width of chessboard
     * @param height: set the height of chessboard
     */
    public ChessBoard(int width, int height){
        // set width and height of chessboard
        this.width = width;
        this.height = height;
        System.out.println(width);

        // initialize the 2d array to store pieces
        this.tiles = new Piece[height][];
        for(int i = 0; i < height; i++) {
            this.tiles[i] = new Piece[width];
        }

        // initialize white_pieces and black_pieces
        this.white_pieces = new ArrayList<Piece>();
        this.black_pieces = new ArrayList<Piece>();

        // initialize other variables
        this.king1 = null;        // no king is set yet
        this.king2 = null;
        this.turns = 0; // if it is even number, then it's White turn, otherwise Black turn
    }

    /**
     * add piece to white_pieces or black_pieces array list
     * @param p: The piece we want to save.
     */
    public void addPieceToList(Piece p) {
        if(p.getPlayer() == Player.WHITE){
            this.white_pieces.add(p);
        }
        else{
            this.black_pieces.add(p);
        }
    }

    /**
     * Getter: return this.white_pieces
     * @return white_piece array list that contains all white pieces
     */
    public ArrayList<Piece> getWhite_pieces(){
        return this.white_pieces;
    }

    /**
     * Getter: return this.black_pieces
     * @return black_piece array list that contains all black pieces
     */
    public ArrayList<Piece> getBlack_pieces(){
        return this.black_pieces;
    }


    /**
     * Getter: get king1 from WHITE player
     * @return king piece from WHITE player
     */
    public Piece getKing1(){
        return this.king1;
    }

    /**
     * Setter: set king1
     * @param p
     */
    public void setKing1(Piece p){
        this.king1 = p;
    }

    /**
     * Getter: get king2 from BLACK player
     * @return king piece from BLACK player
     */
    public Piece getKing2(){
        return this.king2;
    }

    /**
     * Setter: set king2
     * @param p
     */
    public void setKing2(Piece p){
        this.king2 = p;
    }

    /**
     *
     * Return the piece at given coordinate
     * if there is no piece at that coordinate, or that coordinate is invalid, return null
     * @param x  the x coordinate
     * @param y  the y coordinate
     * @return   the piece at that (x, y) coordinate. If coordinate not valid, return null.
     */
    public Piece getPieceAtCoordinate(int x, int y){
        if(x >= this.width || x < 0 || y >= this.height || y < 0) // outside the boundary
            return null;
        return this.tiles[y][x];
    }

    /**
     * Store the piece at (x, y) coordinate
     * @param p  the piece we want to set
     * @param x  the x coordinate
     * @param y  the y coordinate
     */
    public void setPieceAtCoordinate(Piece p, int x, int y){
        this.tiles[y][x] = p;
    }

    /**
     * Getter: get the width of chessboard
     * @return the width of chessboard
     */
    public int getWidth(){
        return this.width;
    }

    /**
     * Getter: get the height of chessboard
     * @return the height of chessboard
     */
    public int getHeight(){
        return this.height;
    }


    /**
     * Remove a piece from the chessboard
     * @param p the piece we want to remove from chessboard
     */
    public void removePiece(Piece p){
        int x = p.getX_coordinate();
        int y = p.getY_coordinate();
        this.tiles[y][x] = null;
    }

    /**
     * Setter: set turns
     * @param turns
     */
    public void setTurns(int turns){
        this.turns = turns;
    }

    /**
     * Increment turns by 1
     */
    public void incrementTurns(){
        this.turns++;
    }

    /**
     * Getter: get turns
     * @return turns of the game
     */
    public int getTurns(){
        return this.turns;
    }

    /**
     * Get the player for this turn
     * @return the player for this turn
     */
    public Player getPlayerForThisTurn(){
        if (this.turns % 2 == 0){
            return Player.WHITE; // White
        }
        return Player.BLACK; // Black
    }

    /**
     *
     * Check whether player can move a piece
     *
     * If a player can not move any piece, then return true
     *
     * @param player check whether player can move a piece or not.
     * @return return true if player cannot move any piece; otherwise return false
     */
    public boolean playerCannotMove(Player player){
        ArrayList<Piece> player_pieces = ((player == Player.WHITE) ? this.getWhite_pieces() : this.getBlack_pieces());
        for(Piece p : player_pieces){ // get player's piece
            if(p.getX_coordinate() == -1 || p.getY_coordinate() == -1)
                continue;;
            ArrayList<Coordinate> coords = p.getPossibleMoveCoordinate(); // possible move coordinates.
            for(Coordinate coord : coords) {
                if (p.isSuicideMove(coord.getX(), coord.getY()) == false){ // so piece can move there.
                    return false; // there is a legal move
                }
            }
        }
        return true;
    }


    /**
     * Check whether player's king is in stalemate.
     *
     * when not checked, check whether is there any legal move.
     *
     * if there is no legal move, return true; otherwise return false.
     * @param player
     * @return true if there is stalemate; otherwise return false.
     */
    public boolean isStalemate(Player player){
        King king = player == Player.WHITE ? (King)this.king1 : (King)this.king2;
        if(king.isInCheck() == false){ // king is not in check.
            // check whether is there any legal move
            // if there is no legal move, then return true
            // if there is a legal move, then return false
            if(playerCannotMove(player))
                return true;
            return false;
        }
        else{
            return false;
        }
    }

    public void generateCustomBoard() throws FileNotFoundException {
        GsonBuilder builder = new GsonBuilder();
        Gson gson = new Gson();
        JsonReader reader = new JsonReader(new FileReader("src/chessboard/chessboard.json"));
        JsonObject json = gson.fromJson(reader, JsonObject.class);
        JsonArray jArray = json.getAsJsonArray("Pieces");

        jArray.forEach(jsonElement -> {
            Piece p = null;
            String name = jsonElement.getAsJsonObject().get("name").getAsString();
            String move = null;
            try {
                move = jsonElement.getAsJsonObject().get("move").getAsString();
            } catch (NullPointerException ignored) {
            }
            String color = jsonElement.getAsJsonObject().get("color").getAsString();
            int x = jsonElement.getAsJsonObject().get("x").getAsInt();
            int y = jsonElement.getAsJsonObject().get("y").getAsInt();
            Player player = Player.WHITE;
            if (Objects.equals(color, "BLACK")) {
                player = Player.BLACK;
            }
            if (name.equals("Pawn")) {
                p = new Pawn(this, player);
            }
            if (name.equals("King")) {
                p = new King(this, player);
            }

            if (move != null) {
                if (name.equals("Custom")) {
                    p = new CustomPiece(move, this, player);
                } else {
                    p = new CustomPiece(name, move, this, player);
                }
            }
            assert p != null;
            p.setCoordinate(x, y);
        });
    }


    /**
     * Generate standard 8 x 8 game board
     */
    public void generateStandardBoard(){
        Piece p;  // a piece object

        // ===============
        //  player1 white
        // ===============
        p = new CustomPiece("Rook", "Rook",this, Player.WHITE); // a1 white rook
        p.setCoordinate(0, 0);

        p = new CustomPiece("Knight","Knight", this, Player.WHITE); // b1 white knight
        p.setCoordinate(1, 0);

        p = new CustomPiece("Bishop", "Bishop",this, Player.WHITE); // c1 white bishop
        p.setCoordinate(2, 0);

        p = new CustomPiece("Queen", "Queen", this, Player.WHITE); // d1 white queen
        p.setCoordinate(3, 0);

        p = new King(this, Player.WHITE);   // e1 white king
        p.setCoordinate(4, 0);

        p = new CustomPiece("Bishop", "Bishop", this, Player.WHITE); // f1 white bishop
        p.setCoordinate(5, 0);

        p = new CustomPiece("Knight", "Knight", this, Player.WHITE); // g1 white knight
        p.setCoordinate(6, 0);

        p = new CustomPiece("Rook", "Rook", this, Player.WHITE);   // h2 white rook
        p.setCoordinate(7, 0);

        p = new Pawn(this, Player.WHITE);   // a2 white pawn
        p.setCoordinate(0, 1);

        p = new Pawn(this, Player.WHITE);   // b2 white pawn
        p.setCoordinate(1, 1);

        p = new Pawn(this, Player.WHITE);   // c2 white pawn
        p.setCoordinate(2, 1);

        p = new Pawn(this, Player.WHITE);   // d2 white pawn
        p.setCoordinate(3, 1);

        p = new Pawn(this, Player.WHITE);   // e2 white pawn
        p.setCoordinate(4, 1);

        p = new Pawn(this, Player.WHITE);   // f2 white pawn
        p.setCoordinate(5, 1);

        p = new Pawn(this, Player.WHITE);   // g2 white pawn
        p.setCoordinate(6, 1);

        p = new Pawn(this, Player.WHITE);   // h2 white pawn
        p.setCoordinate(7, 1);


        // ===============
        //  player2 black
        // ===============
        p = new CustomPiece("Rook", "Rook", this, Player.BLACK); // a8 black rook
        p.setCoordinate(0, 7);

        p = new CustomPiece("Knight", "Knight", this, Player.BLACK); // b8 black knight
        p.setCoordinate(1, 7);

        p = new CustomPiece("Bishop", "Bishop",this, Player.BLACK); // c8 black bishop
        p.setCoordinate(2, 7);

        p = new CustomPiece("Queen", "Queen", this, Player.BLACK); // d8 black queen
        p.setCoordinate(3, 7);

        p = new King(this, Player.BLACK);   // e8 black king
        p.setCoordinate(4, 7);

        p = new CustomPiece("Bishop", "Bishop", this, Player.BLACK); // f8 black bishop
        p.setCoordinate(5, 7);

        p = new CustomPiece("Knight", "Knight",this, Player.BLACK); // g8 black knight
        p.setCoordinate(6, 7);

        p = new CustomPiece("Rook", "Rook", this, Player.BLACK);   // h8 black rook
        p.setCoordinate(7, 7);

        p = new Pawn(this, Player.BLACK);   // a7 black pawn
        p.setCoordinate(0, 6);

        p = new Pawn(this, Player.BLACK);   // b7 black pawn
        p.setCoordinate(1, 6);

        p = new Pawn(this, Player.BLACK);   // c7 black pawn
        p.setCoordinate(2, 6);

        p = new Pawn(this, Player.BLACK);   // d7 black pawn
        p.setCoordinate(3, 6);

        p = new Pawn(this, Player.BLACK);   // e7 black pawn
        p.setCoordinate(4, 6);

        p = new Pawn(this, Player.BLACK);   // f7 black pawn
        p.setCoordinate(5, 6);

        p = new Pawn(this, Player.BLACK);   // g7 black pawn
        p.setCoordinate(6, 6);

        p = new Pawn(this, Player.BLACK);   // h7 black pawn
        p.setCoordinate(7, 6);

    }
}
