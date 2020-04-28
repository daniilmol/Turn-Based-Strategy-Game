package ui;

import entities.Unit;
import environment.Board;
import javafx.event.ActionEvent;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import main.Game;
import main.Player;
import units.Archer;
import units.Cavalry;
import units.Cleric;
import units.Mage;
import units.Necromancer;
import units.Peasant;
import units.Rogue;
import units.SiegeEngine;
import units.Spearman;
import units.Swordsman;
import units.Warrior;

public class BuildingPanel extends Pane{
    
    public static Group g = new Group();
    private static GridPane unitCreation = new GridPane();
    private static ImageView close;
    private static boolean first = true;
    private static Button createUnit;

    private static double x;
    private static double y;
    private int playerIndex;
    private Unit unit;
    public BuildingPanel(int windowWidth, int windowHeight) {
        unitCreation.setHgap(75);
        unitCreation.setVgap(50);
        
        getChildren().add(g);
        initializeWindow(windowWidth, windowHeight);
        initializeScrollPane();
        g.relocate(325, 100);
        Image closeButton = new Image("file:assets/close-button.png");
        initializeClose(closeButton);
        g.getChildren().add(close);
        
        /**
        for(int i = 0; i < 10; i++) {
            ImageView v = new ImageView(Unit.unitTextures[i]);
            v.setFitWidth(100);
            v.setFitHeight(100);
            Button x = new Button();
            x.setGraphic(v);
            unitCreation.add(x, i % 3, i / 3);
            x.setTranslateX(45);
        }
        */
        for(int i = 0; i < Unit.unitTextures.length; i++) {
            ImageView v = new ImageView(Unit.unitTextures[i]);
            v.setFitWidth(100);
            v.setFitHeight(100);
            Button x = new Button();
            x.setGraphic(v);
            x.setOnAction(this::actionPerformed);
            unitCreation.add(x, i % 3, i / 3);
            x.setTranslateX(45);
        }
        
        createUnit = new Button("Create Unit");
        createUnit.relocate(300, 550);
        createUnit.setOnAction(this::actionPerformed);
        g.getChildren().add(createUnit);

    }
    
    public void setSourceCoordinates(double d, double e) {
        x = d;
        y = e;
    }
 
    
    
    public void actionPerformed(ActionEvent e) {
        Button x = (Button)e.getSource();

        if(!x.getText().equals("Create Unit")) {
            
            int index = 0; 
            for(Node panelUnit : unitCreation.getChildren()) {
                if(panelUnit instanceof Button) {
                    unitCreation.getChildren().get(index).setDisable(false);
                }
                index++;
            }
            x.setDisable(true);

            for(int i = 0; i < Game.players.size(); i++) {
                if(Game.players.get(i).isTurn()) {
                    System.out.println("Saved player found");
                    playerIndex = i;
                    if(Game.players.get(playerIndex).getColor().equals(Color.RED))
                        System.out.println("Stored color is red");
                    else
                        System.out.println("Stored color is blue");
                }
            }
            for(int i = 0; i < Unit.unitTextures.length; i++) {
                Node node = x.getGraphic();
                ImageView nodeImageView = (ImageView)node;
                Image nodeImage = nodeImageView.getImage();
                if(nodeImage == Unit.unitTextures[i]) {
                    unit = Unit.UNITS[i];
                    if(Peasant.class.isInstance(unit))
                        unit = new Peasant("P", nodeImage, "Peasant", 0, 0, null);
                    if(Swordsman.class.isInstance(unit))
                        unit = new Swordsman("S", nodeImage, "Swordsman", 0, 0, null);
                    if(Archer.class.isInstance(unit))
                        unit = new Archer("A", nodeImage, "Archer", 0, 0, null);
                    if(Cleric.class.isInstance(unit))
                        unit = new Cleric("C", nodeImage, "Cleric", 0, 0, null);
                    if(Necromancer.class.isInstance(unit))
                        unit = new Necromancer("N", nodeImage, "Necromancer", 0, 0, null);
                    if(Mage.class.isInstance(unit))
                        unit = new Mage("M", nodeImage, "Mage", 0, 0, null);
                    if(Warrior.class.isInstance(unit))
                        unit = new Warrior("W", nodeImage, "Warrior", 0, 0, null);
                    if(Spearman.class.isInstance(unit))
                        unit = new Spearman("Sp", nodeImage, "Spearman", 0, 0, null);
                    if(Cavalry.class.isInstance(unit))
                        unit = new Cavalry("Ca", nodeImage, "Cavalry", 0, 0, null);
                    if(SiegeEngine.class.isInstance(unit))
                        unit = new SiegeEngine("Si", nodeImage, "Siege", 0, 0, null);
                    if(Rogue.class.isInstance(unit))
                        unit = new Rogue("R", nodeImage, "Assassin", 0, 0, null);
                }
            }
            unit.setPlayer(Game.players.get(playerIndex));
            unit.firstTime();

            if(unit.getCost() <= Game.players.get(playerIndex).getGold()) {
                createUnit.setDisable(false);
            }else {
                createUnit.setDisable(true);
            }
        }else {
            int index = 0; 
            for(Node panelUnit : unitCreation.getChildren()) {
                if(panelUnit instanceof Button) {
                    unitCreation.getChildren().get(index).setDisable(false);
                }
                index++;
            }
            x.setDisable(false);
            //Player.resetLocks(Game.players[playerIndex].getColor(), false, true);
            Game.players.get(playerIndex).setGold(Game.players.get(playerIndex).getGold() - unit.getCost());
            Game.getGoldText().setText("Gold: " + Game.players.get(playerIndex).getGold());
            Board.tiles[(int)BuildingPanel.x][(int)y].setPiece(unit, false);

            closePanelClicked(null);
            closePanelReleased(null);
        }
    }
    
    private void initializeWindow(int w, int h) {
        
        Rectangle window = new Rectangle();
        window.setWidth(w);
        window.setHeight(h);
        window.setFill(Color.LIGHTGRAY);
        window.setOpacity(0.5);
        
        g.getChildren().add(window);
        
    }
    
    private void initializeScrollPane() {   
        
        ScrollPane unitScroll = new ScrollPane();
        
        unitScroll.setContent(unitCreation);
        unitScroll.setPrefSize(600, 450);
        unitScroll.setTranslateY(50);
        unitScroll.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        unitScroll.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        
        g.getChildren().add(unitScroll);
        
    }
    
    private void initializeClose(Image closeButton) {
        close = new ImageView(closeButton);
        close.setOnMousePressed(this::closePanelClicked);
        close.setOnMouseReleased(this::closePanelReleased);
        close.setTranslateX(550);
        close.setFitWidth(50);
        close.setFitHeight(50);
    }
    
    public void closePanelClicked(MouseEvent e) {
        
        close.setFitWidth(45);
        close.setFitHeight(45);

    }
    
    public void closePanelReleased(MouseEvent e) {
        Player.resetLocks(Player.storedColor, false, true);

        close.setFitWidth(50);
        close.setFitHeight(50);

        int index = 0;
        for(Node x: Game.p.getChildren()) {
            if(x instanceof BuildingPanel) {
                Game.p.getChildren().remove(index);
                break;
            }
            index++;
        }
        index = 0; 
        for(Node panelUnit : unitCreation.getChildren()) {
            if(panelUnit instanceof Button) {
                unitCreation.getChildren().get(index).setDisable(false);
            }
            index++;
        }
        createUnit.setDisable(true);

    }
     
}
