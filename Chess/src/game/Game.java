package game;

import ui.UiController;

/**
 * Main Game
 */
public class Game{

    private UiController game_view;  // game view
    /**
     * Constructor: init game, set necessary properties.
     * @param game_controller
     * @param game_view
     */
    public Game(GameController game_controller, UiController game_view){
        // game controller
        this.game_view = game_view;

        this.game_view.bindGameController(game_controller); // bind game controller to game view
    }

    public void startGame(){
        this.game_view.initMainMenu(); // init window and begin to draw GUI
    }

    /**
     * Main function
     * @param args
     */
    public static void main(String [] args){
        // initialize Chessboard(model), game view, and game controller
        UiController uiController;
        GameController game_controller;
        uiController = new UiController(64); // initialize game view
        game_controller = new GameController(uiController); // initialize game controller

        // init game
        Game game = new Game(game_controller, uiController);

        // start game
        game.startGame();
    }
}