package ui;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.stream.JsonReader;
import game.GameController;
import java.io.FileNotFoundException;
import java.io.FileReader;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 *
 * This class is used to draw menu
 *
 */
public class MainMenu extends JPanel {
  private UiController game_view;
  private String message;      // game message
  private JButton start;
  private JButton exit;
  private JButton settings;
  private int size = 8;
  public static boolean custom = false;

  private GameController contr;
  /**
   * MenuView constructor
   * This constructor is used to put several components on panel
   * eg: start button, restart button, forfeit button, undo button, etc.
   * @param width
   * @param height
   */
  public MainMenu(int width, int height, final UiController game_view, GameController contr){
    this.game_view = game_view;
    this.setPreferredSize(new Dimension(width, height));
    this.setLayout(null); // use absolute layout
    this.contr = contr;

    /*
     *
     * Setup several buttons for menu
     *
     */

    // add start button
    start = new JButton("Start Game");
    start.setBounds(200, 10, 100, 50);
    this.add(start);
    start.addActionListener(new ActionListener() {
      /**
       * Clicked start button
       * @param e
       */
      @Override
      public void actionPerformed(ActionEvent e) {
        if (custom) {
          try {
            contr.startCustomGame(size);
          } catch (FileNotFoundException ex) {
            ex.printStackTrace();
          }
        } else
          contr.startNewGame(size); // run click start button event
      }
    });

    // add settings button
    settings = new JButton("Settings");
    settings.setBounds(200, 60, 100, 50);
    this.add(settings);
    settings.addActionListener(e -> {
      custom = true;
      Gson gson = new Gson();
      JsonReader reader = null;
      try {
        reader = new JsonReader(new FileReader("src/chessboard/chessboard.json"));
      } catch (FileNotFoundException ex) {
        ex.printStackTrace();
      }
      assert reader != null;
      JsonObject json = gson.fromJson(reader, JsonObject.class);
      size = json.get("Chessboard").getAsInt();
    });

    // add exit button
    exit = new JButton("Exit");
    exit.setBounds(200, 110, 100, 50);
    this.add(exit);
    exit.addActionListener(e -> {
      JComponent comp = (JComponent) e.getSource();
      Window win = SwingUtilities.getWindowAncestor(comp);
      win.dispose();
    });


    this.setBackground(new Color(100, 175, 89)); // draw menu background
    this.message = "Press Start button to start the game";
  }

  /**
   * Draw menu
   * @param g
   */
  @Override
  public void paint(Graphics g) {
    super.paint(g);

    // draw game message
    g.setColor(new Color(241, 255, 163));
    g.drawString(this.message, 250, 300);
  }

  /**
   * repaint the menu canvas.
   *
   * @param message        new message
   */
  public void drawMenu(String message){
    this.message = message;
    this.repaint(); // redraw the components
  }
}
