package ui;

import javafx.event.ActionEvent;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Screen;
import main.Game;

public class GameSettings extends Pane{
    private Button start;
    private ComboBox<Integer> numPlayers = new ComboBox<Integer>();
    private ComboBox<String> gameType = new ComboBox<String>();
    private Group g = new Group();
    private static final Text dropDownInfo = new Text("Number of Players: ");
    private static final Text gameTypeInfo = new Text("Game Type: ");
    
    public GameSettings() {
        getChildren().add(g);
        start = new Button("Start");
        numPlayers.getItems().addAll(2, 3, 4);
        gameType.getItems().addAll("Singleplayer", "Pass and Play");
        numPlayers.setValue(2);
        gameType.setValue("Singleplayer");
        g.getChildren().addAll(gameTypeInfo, gameType, dropDownInfo, numPlayers, start);
        g.relocate(570, 350);
        numPlayers.relocate(120, -15);
        gameTypeInfo.relocate(0, -50);
        gameType.relocate(120, -50);
        start.relocate(50, 20);
        start.setOnAction(this::processButtonRequest);
    }
    
    public void processButtonRequest(ActionEvent e) {
            int k = numPlayers.getValue();
            String type = gameType.getValue();
            Game.setBoard(k, type);
            Text text = new Text("Gold: " + Game.players.get(0).getGold());
            text.setFont(Font.font("Times New Roman", FontWeight.BOLD, 30));
            text.setFill(Color.WHITE);
            text.setTranslateX(1300);
            Game.g.getChildren().add(text);
            Game.getStage().setScene(Game.setToGameScene());
    }
    
}
