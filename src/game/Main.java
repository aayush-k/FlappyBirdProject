package game;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Main extends Application{
	private static Animation scenery, ground, avatar, pipe1, pipe2, pipe3;
	protected static int counter = 0;
	private static Text score;
	private static UIButton start2, restart2;
	private static UIImage getReady, gameOver, instructions;
	private static Group root;
	
	private void addActionEventHandler(){
    	start2.getBtn().setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) { 
        		Animation.setMotion(true);
            	counter = 0;
            	score.setText(counter + "");
        		ground.play();
            	scenery.play();
            	start2.toggle(false);
            	getReady.toggle(true);
            	instructions.toggle(true);
            	int top1 = ((int) (Math.random()*151))-275; //-95 to -270
            	pipe1 = new Pipe("obstacle.png",top1,548);
            	int top2 = ((int) (Math.random()*151))-275; //-95 to -270
            	pipe2 = new Pipe("obstacle.png",top2,748);
            	root.getChildren().addAll(pipe1.getImg(), pipe2.getImg());
            	//wait, toggle getReady and jump
            	avatar.play();
            	pipe1.play();
            	pipe2.play();
            }
        });
    	restart2.getBtn().setOnAction(new EventHandler<ActionEvent>() { //restart button only appears at game over
             @Override
             public void handle(ActionEvent event) {
            	 restart2.toggle(false);
            	 gameOver.toggle(false);
            	 avatar.reset();
            	 start2.toggle(true);
             	}
    	});
    }
    
	protected static void counter(){
		counter++;
		score.setText(counter + "");
	}
	
    private void addMouseEventHandler(){
    	root.onMouseClickedProperty().set(new EventHandler<MouseEvent>() {
    		public void handle(MouseEvent event) {
    			if(Animation.getMotion()){
    				getReady.toggle(false);
    				score.setVisible(true);
    				instructions.toggle(false);
    				avatar.play();
    			}
    		}
        });
    }
	
    @Override
   	public void start(Stage primaryStage){
   		//Create a Group
   		root = new Group();
   		
   		//TODO 1: add background
   		scenery = new Scenery("background2.png");
   		ground = new Ground("ground2.png");
   		avatar = new Avatar("flappy.png");
   		
		//TODO 3: add UI
		start2 = new UIButton("clickrun.png",60,60);
		restart2 = new UIButton("Restart",170,150);
		instructions = new UIImage("instructions.png",130,150);
		
		getReady = new UIImage("getready.png",105,60);
		gameOver = new UIImage("gameover.png",110,60);
		
		score = new Text("");
		score.setFont(new Font("Verdana", 30.0));
		score.setLayoutX(180);
		score.setLayoutY(100);
		//score.setVisible(true);
		
		root.getChildren().addAll(scenery.getImg(),ground.getImg(),avatar.getImg(),
				start2.getBtn(), restart2.getBtn(), score,
				getReady.getImg(), gameOver.getImg(), instructions.getImg());

		restart2.toggle(false);
		instructions.toggle(false);
		getReady.toggle(false);
		gameOver.toggle(false);
		score.setVisible(false);
		//TODO 4: add event handlers
		addActionEventHandler();
		addMouseEventHandler();
		
		//Create scene and add to stage		
		Scene scene = new Scene(root, 400, 400);
		primaryStage.setScene(scene);
		primaryStage.show();
    }
    
	public static void end(){
		Animation.setMotion(false);
	   	//scrap current pipe objects and get a clean slate
		root.getChildren().clear();
		pipe1.stop();pipe2.stop();
		pipe1.destroy();pipe2.destroy();
		root.getChildren().addAll(scenery.getImg(),ground.getImg(),avatar.getImg(),
				start2.getBtn(), restart2.getBtn(), score,
				getReady.getImg(), gameOver.getImg(), instructions.getImg());
		
		//game over screen components:
		ground.pause();
		scenery.pause();
		restart2.toggle(true);
		getReady.toggle(false);
		instructions.toggle(false);
		gameOver.toggle(true);
		//reinitialize new, unused pipe objects	
	}

	public static void main(String[] args) {
		Application.launch(args);
	}

	public static boolean intersect(double x_p, double y_p){
		boolean topCollision = avatar.getImg().intersects(x_p, y_p, 52, 320);
		boolean botCollision = avatar.getImg().intersects(x_p, y_p+470, 52, 320);
		return topCollision || botCollision;
	}

	public static void passOn(String file, int y, int x){ 
	    	//secondary phase of each pipe animation
	    	pipe3 = new Pipe(file,y,x,true);
	    	pipe3.play();
	    	root.getChildren().add(pipe3.getImg());
	    }
}