package main;
import javafx.application.Application;
import javafx.stage.Stage;

/**
 * This class contains the main method to launch the program
 * @author Daniil Molchanov
 */
public class Launcher extends Application{
    
    /**
     * This method drives the program
     * @param args unused
     */
    public static void main(String[] args) {
        launch(args);
    }
    
    /**
     * Creates a new game
     */
    @Override
    public void start(Stage stage) throws Exception {
        new Game(stage);     
    }
}
