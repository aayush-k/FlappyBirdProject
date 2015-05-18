import java.net.URISyntaxException;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;


public class HelloWorld extends Application{
	
	private Button button = null;
	private Text text = null ;
	
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		//Create a Group 
		Group root = new Group();

		// Create Text node
		text = new Text("Hello");
        text.xProperty().set(150);
        text.yProperty().set(100);
        text.setFont(Font.font ("Verdana", 36));
		root.getChildren().add(text);

        //TODO Activity 1: set font color and reflection
        
        
        
        
        //TODO Activity 2: Add a Button to scene

        
        
        
		//Create scene
		Scene scene = new Scene(root, 400, 400);
		
		//TODO activity 3: add event handler
		//addActionEventHandler();
		
		// Set scene to the stage and show
		primaryStage.setScene(scene);
		primaryStage.show();
		
	}

	public static void main(String[] args) {
		Application.launch(args);
	}

	
//    private void addActionEventHandler(){
//    }	
//    
//	private int clicks = 0;
//	private String[] hellos = {"Hello", "Bonjour", "你好"};
//	private String[] sounds = {"hello.mp3", "bonjour.mp3", "nihao.mp3"};
	
}
