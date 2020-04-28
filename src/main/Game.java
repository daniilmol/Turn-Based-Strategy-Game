package main;

import java.util.ArrayList;

import environment.Board;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.media.AudioClip;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import ui.GameSettings;
import ui.MenuPanel;

/**
 * This class contains the code that calls methods that execute main
 * game functions. Initializes all scenes and panels. 
 * @author Daniil Molchanov
 */
public class Game implements Runnable{
    
    /**
     * This is the menu
     */
    private static MenuPanel menu;
    
    /**
     * These are the game settings when starting a new game
     */
    private GameSettings settings;
    
    /**
     * This is the stage that scenes are displayed on
     */
    private static Stage stage;
    
    /**
     * This is the menu scene
     */
    private static Scene menuScene;
    
    /**
     * This is the game scene
     */
    private static Scene gameScene;
    
    /**
     * This is the game settings scene
     */
    public static Scene gameSettings;
    
    /**
     * This is the current scene
     */
    private static Scene currentScene;
    
    /**
     * One of the many audio clips that will be played at specific events.
     */
    private AudioClip currentClip = new AudioClip("file:sounds/hal1.mp3");
    
    /**
     * This is the list of players in the game
     */
    public static ArrayList<Player> players;
    
    /**
     * This determines when to start/stop the threads
     */
    private boolean running = false;
    
    /**
     * This is the thread that plays sounds
     */
    private Thread audioLoop;
    
    /**
     * This is the main game panel
     */
    public static Pane p;
    
    /**
     * This displays the number of gold a player has
     */
    private static Text goldAmount;
    
    /**
     * This is the main group that contains most nodes
     */
    public static Group g;
    
    /**
     * This is a group
     */
    public static Group h;
    
    /**
     * This button ends the turn
     */
    private Button endTurn;
    
    /**
     * This button cancels the end turn sequence
     */
    private Button cancel;
    
    /**
     * This is the end turn button in the side panel
     */
    private Button endTurnPanel;
    
    /**
     * Prevents duplicate panels from being created
     */
    private boolean clicked;
    
    /**
     * This contains the turn controls
     */
    private Rectangle endPanel;
    
    /**
     * This is the side panel
     */
    private Rectangle gamePanel;
    
    /**
     * Game constructor initializes the view, threads and system
     * @param stage contains the scenes
     */
    public Game(Stage stage) {
        clicked = false;
        audioLoop = new Thread();
        Game.stage = stage;
        
        initializeTurnButtons();
        initializePanels();
        initializeText();
        initializeScenes(); 
        
        stage.setTitle("Turn Based");
        stage.setScene(currentScene);
        stage.show();
        stage.setOnCloseRequest(new EventHandler<WindowEvent>(){
            @Override
            public void handle(WindowEvent e) {
                Platform.exit();
                System.exit(0);
            }
        });
        
        start();
    }
    
    /**
     * This initializes all the scenes
     */
    private void initializeScenes() {    
        menuScene = new Scene(menu, 1300, 800);
        currentScene = menuScene;
        gameScene = new Scene(p, 1600, 800);
        gameSettings = new Scene(settings, 1300, 800);        
    }
    
    /**
     * This initializes all the text
     */
    private void initializeText() {    
        goldAmount = new Text("Gold: " + 10);
        goldAmount.setFill(Color.WHITE);
        goldAmount.setFont(Font.loadFont("file:resources/ancient.ttf", 30));
        goldAmount.relocate(1300, 0);
        g.getChildren().addAll(goldAmount, endTurnPanel);      
    }
    
    /**
     * This returns the gold text
     * @return goldAmount
     */
    public static Text getGoldText() {
        return goldAmount;
    }
    
    /**
     * This method initializes all the panels
     */
    private void initializePanels() {
        
        gamePanel = new Rectangle(1300, 0, 300, 800);
        gamePanel.setFill(Color.SADDLEBROWN);
     
        g = new Group();
        p = new Pane();
        p.getChildren().add(g);
        g.getChildren().add(gamePanel);
        menu = new MenuPanel(1300, 800);
        settings = new GameSettings();
        
        endPanel = new Rectangle(350, 100, 300, 200);
        endPanel.setFill(Color.LIGHTGRAY);
        endPanel.setOpacity(0.5);
        
        
        endTurnPanel.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent e) {
                if(!clicked)
                    p.getChildren().addAll(endPanel, endTurn, cancel);
                clicked = true;
            }
            
        });
        
    }
    
    /**
     * This method initializes the buttons
     */
    private void initializeTurnButtons() {
        
        endTurn = new Button("End Turn");
        cancel = new Button("Cancel");
        endTurnPanel = new Button("End Turn");
        endTurn.relocate(350, 100);
        cancel.relocate(430, 100);
        endTurnPanel.relocate(1300, 100);
        
        endTurn.setOnAction(this::actionPerformed);
        cancel.setOnAction(this::actionPerformed);
        
    }
    
    /**
     * This method counts the number of units on the field for every player
     * to determine whether they are eliminated from the game
     */
    private void countUnits() {
        for(int p = 0; p < Game.players.size(); p++) {
            for(int i = 0; i < 26; i++) {
                for(int j = 0; j < 16; j++) {
                    if(Board.tiles[i][j].getEntity() != null && !Board.tiles[i][j].getEntity().getLabel().equals("N") && 
                            Board.tiles[i][j].getEntity().getTeam().getColor().equals(Game.players.get(p).getColor())) {
                                Game.players.get(p).setUnits(Game.players.get(p).getUnits() + 1);
                    }
                }
            }
        }
    }
    
    /**
     * This method removes eliminated players from the player list
     */
    private void removeDeadUnits() {
        int removeIndex = -1;

        for(int p = 0; p < Game.players.size(); p++) {

            if(Game.players.get(p).getUnits() == 0) {
                for(int i = 0; i < Game.players.size(); i++) {
                    if(Game.players.get(i).getColor().equals(Game.players.get(p).getColor()))
                        removeIndex = i;
                    }
                System.out.println("REMOVE INDEX IS NOW" + removeIndex);
                Game.players.remove(removeIndex);
                Player.playerCount--;
                System.out.println("PLAYER LENGTH: " + Game.players.size());
            }
        }
    }
    
    /**
     * This method sets the unit count back to 0 after counting the units
     */
    private void resetUnitCount() {       
        for(int p = 0; p < players.size(); p++) {
            players.get(p).setUnits(0);
        }
    }
    
    /**
     * Cycles the turn and performs all game mechanics.
     * @param e
     */
    public void actionPerformed(ActionEvent e) {

        Button x = (Button)e.getSource();
        if(x.getText().equals("End Turn")) {
            if(MouseClickHandler.moving)
                return;
            countUnits();
            removeDeadUnits();
            resetUnitCount();
            Board.tiles[0][0].unlockAll();

            for(int i = 0; i < players.size(); i++) {
                players.get(i).cycleTurn();
            }
            for(int i = 2; i < 5; i++)
                p.getChildren().remove(2);   
        }else {
            for(int i = 2; i < 5; i++)
            p.getChildren().remove(2);    
        }
        clicked = false;
    }
    
    /**
     * This methods sets up the board and adds it to the pane
     * @param k is the number of players
     * @param type is the game type
     */
    public static void setBoard(int k, String type) {
        Board board = new Board(k, type);
        p.getChildren().add(board);
        Player.storedColor = players.get(0).getColor();
        Board.tiles[0][0].lockAll();
        Board.tiles[0][0].unlockFriendlyTiles(players.get(0).getColor());
        Board.tiles[0][0].lockEnemyTiles(players.get(0).getColor());
    }
    
    /**
     * This method returns the stage
     * @return stage
     */
    public static Stage getStage() {
        return stage;
    }
    
    /**
     * This method sets the scene to the game scene
     * @return current scene
     */
    public static Scene setToGameScene() {
        return currentScene = gameScene;
    }
    
    /**
     * This method returns the current scene
     * @return current scene
     */
    public static Scene getCurrentScene() {
        return currentScene;
    }
    
    /**
     * This method initializes all the players
     * @param numPlayers is the number of players
     */
    public static void setPlayers(int numPlayers, String type) {
        players = new ArrayList<Player>(numPlayers);
        String[] colors = {"Red", "Blue", "Yellow", "Purple"};
        for(int i = 0; i < numPlayers; i++) {
            if(type.equals("Pass and Play") || i == 0)
                players.add(new Player(colors[i], i + 1, false));
            else
                players.add(new Player(colors[i], i + 1, true));
            players.get(i).setGold(10);
            players.get(i).setRate(5);
        }
    }

    /**
     * This method plays the audio
     */
    private void play_audio() {
        
        currentClip = new AudioClip(("file:sounds/hal1.mp3"));
        currentClip.play(0); 
        
    }

    /**
     * This method is called when the program is launched. It is 
     * run on a loop until the program is stopped. 
     * Plays music on a loop
     */
    @Override
    public void run() {
        while(running) {
            if(!currentClip.isPlaying())
            play_audio();
        }
        stop();
    }
    
    /**
     * This method starts the thread
     */
    public synchronized void start() {
        running = true;
        audioLoop = new Thread(this, "Display");
        audioLoop.start();
    }
    
    /**
     * This method stops the thread
     */
    public synchronized void stop() {
        running = false;
        try {
            audioLoop.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}