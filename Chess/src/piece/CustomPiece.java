package piece;

import chessboard.ChessBoard;
import chessboard.Player;
import java.util.ArrayList;


public class CustomPiece extends Piece {

  private String pattern;
  /**
   * Initialize a piece object
   *
   * @param move_pattern how piece moves
   * @param board      the game board where we put this piece
   * @param player     the player id
   */
  public CustomPiece(String move_pattern, ChessBoard board, Player player) {
    super("custom", board, player);
    if (player == Player.WHITE) {  // White player
      this.piece_image_path = "assets/white_custom.png";
    } else { // Black player
      this.piece_image_path = "assets/black_custom.png";
    }

    this.pattern = move_pattern;
    movePatterns = new MovePatterns(this.board, this, this.pattern);
  }

  public CustomPiece(String piece_name, String move_pattern, ChessBoard board, Player player) {
    super(piece_name, board, player);

    if (player == Player.WHITE) {  // White player
      this.piece_image_path = "assets/white_" + piece_name + ".png";
    } else { // Black player
      this.piece_image_path = "assets/black_" + piece_name + ".png";
    }

    this.pattern = move_pattern;
    movePatterns = new MovePatterns(this.board, this, this.pattern);
  }
  private final MovePatterns movePatterns;

  @Override
  public ArrayList<Coordinate> getPossibleMoveCoordinate() {
    return movePatterns.getPossibleMoves();
  }
}
