package ui;

import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import main.Game;

public class MenuPanel extends Pane{

    public static Group g = new Group();
    private Button play;
    private Button exit;
    
    public MenuPanel(int windowWidth, int windowHeight) {
        getChildren().add(g);
        play = new Button("Play");
        play.relocate(windowWidth / 2, windowHeight / 2);
        exit = new Button("Exit");
        exit.relocate(windowWidth / 2, windowHeight / 2 + 30);
        g.getChildren().addAll(play, exit);
        play.setOnAction(this::processButtonRequest);
        exit.setOnAction(this::processButtonRequest);
        
    }
    
    public void processButtonRequest(ActionEvent e) {
        Button x = (Button)e.getSource();
        if(x.getText().equals("Exit")) {
            System.exit(0);
        }
        else {
            Game.getStage().setScene(Game.gameSettings);
        }
    }
    
}
