package ui;

import game.GameController;
import chessboard.*;

import javax.swing.*;
import java.awt.*;

/**
 *
 * Draw GUI for game game
 * Game View consists of Chessboard View and Menu View
 */
public class UiController extends JPanel{
    protected ChessBoard board; // chessboard that we are using
    protected GameController game_controller; // game controller
    public ChessboardUi chessboard_ui; // chessboard game frame(window)
    // chessboard menu view
    public MainMenu mainMenu;
    protected int tile_size; // size of tile

    // width and height of the whole game view in pixel
    protected int view_width;
    protected int view_height;

    // width and height of the chessboard in pixel
    protected int board_width;
    protected int board_height;


    // width and height of menu on the right side in pixel
    protected int menu_width;
    protected int menu_height;

    /**
     * Constructor: init game view
     * @param tile_size this size of tile
     */
    public UiController(int tile_size){
        // super(new BorderLayout(board.width * tile_size + 500, board.height * tile_size));
        this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS)); // set layout for game view

        this.tile_size = tile_size;

        this.menu_width = 512;   // set menu width
        this.menu_height = 512; // set menu height

        this.chessboard_ui = null; // set to null first
        this.setPreferredSize(new Dimension(this.view_width, this.view_height)); // set game view size
    }

    public void setBoard(ChessBoard board) {
        this.board = board;
        this.board_width = this.board.getWidth() * tile_size; // set board width
        this.board_height = this.board.getHeight() * tile_size; // set board height
    }

    /**
     * Bing game controller to this game view
     * @param game_controller the game controller that we are going to use.
     */
    public void bindGameController(GameController game_controller){
        this.game_controller = game_controller;
    }


    /**
     * redraw the whole game view
     */
    public void redraw(){
        this.chessboard_ui.repaint();
        //this.menu_view.repaint();
    }

    /**
     * Initialize game window
     */
    private JFrame menu_frame;
    private JFrame game_frame;
    public void initMainMenu(){
        //this.menu_view = new MenuView(this.menu_width, this.menu_height, this);
        //this.add(this.menu_view/*, BorderLayout.LINE_END*/); // add menu view to game view
        this.mainMenu = new MainMenu(this.menu_width, this.menu_height, this, game_controller);
        if (game_frame != null)
            game_frame.dispose();
        this.removeAll();
        this.add(mainMenu);

        menu_frame = new JFrame("Main Menu");  // init JFrame object
        menu_frame.getContentPane().setPreferredSize(new Dimension(this.menu_width, this.menu_height));  // set height and width
        menu_frame.setResizable(false);    // disable resizable
        menu_frame.pack();
        menu_frame.setVisible(true);       // set as visible
        menu_frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE); // set close operation
        menu_frame.add(this);        // draw canvas
    }

    public void initChessUI() {
        this.chessboard_ui = new ChessboardUi(this.board, this.game_controller, this.tile_size, this.board_width, this.board_height); // initialize chessboard view
        menu_frame.dispose();
        this.removeAll();
        this.add(chessboard_ui, BorderLayout.CENTER); // add chessboard view to game view

        game_frame = new JFrame("Chess");  // init JFrame object
        game_frame.getContentPane().setPreferredSize(new Dimension(this.board_width, this.board_height));  // set height and width
        game_frame.setResizable(false);    // disable resizable
        game_frame.pack();
        game_frame.setVisible(true);       // set as visible
        game_frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE); // set close operation
        game_frame.add(this);        // draw canvas
    }
}
