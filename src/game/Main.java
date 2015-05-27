package game;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class Main extends Application{
	private static Animation scenery, ground, avatar,pipe1,pipe2,pipe3,pipe4;;
	private static int counter = 0;
	private static UIButton start2, restart2;
	private static UIImage getReady, gameOver, instructions;
	
	private static Group root;

	private void addActionEventHandler(){
    	start2.getBtn().setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) { 
        		Animation.setMotion(true);
            	ground.play();
            	scenery.play();
            	start2.toggle(false);
            	getReady.toggle(true);
            	instructions.toggle(true);
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
    
    private void addMouseEventHandler(){
    	root.onMouseClickedProperty().set(new EventHandler<MouseEvent>() {
    		public void handle(MouseEvent event) {
    			if(Animation.getMotion()){
    				getReady.toggle(false);
    				instructions.toggle(false);
    				avatar.play();
    			}
    		}
        });
    }
	
    public static void passOn(String file, int y, int x){ //secondary phase of each pipe animation
    	counter++;
    	if(counter%2==1){
    		pipe3 = new Pipe(file,y,x,true);
        	pipe3.play();
        	root.getChildren().add(pipe3.getImg());
    	} else {
    		pipe4 = new Pipe(file,y,x,true);
        	pipe4.play();
        	root.getChildren().add(pipe4.getImg());
    	}
    }
    
    public static void gameEnd(){
    	Animation.setMotion(false);
       	//scrap current pipe objects and get a clean slate
    	root.getChildren().clear();
    	pipe1.destroy();pipe2.destroy();
    	root.getChildren().addAll(scenery.getImg(),ground.getImg(),avatar.getImg(),
    			start2.getBtn(), restart2.getBtn(),
    			getReady.getImg(), gameOver.getImg(), instructions.getImg());
    	//reinitialize new, unused pipe objects
    	int top1 = ((int) (Math.random()*151))-275; //-95 to -270
    	pipe1 = new Pipe("obstacle.png",top1,548);
    	int top2 = ((int) (Math.random()*151))-275; //-95 to -270
    	pipe2 = new Pipe("obstacle.png",top2,748);
    	root.getChildren().addAll(pipe1.getImg(), pipe2.getImg());
    	//game over screen components:
   		ground.pause();
    	scenery.pause();
    	restart2.toggle(true);
    	getReady.toggle(false);
    	instructions.toggle(false);
    	gameOver.toggle(true);
    }
    
    @Override
   	public void start(Stage primaryStage){
   		//Create a Group
   		root = new Group();
   		
   		//TODO 1: add background
   		scenery = new Scenery("background2.png");
   		ground = new Ground("ground2.png");
   		avatar = new Avatar("flappy.png");
   		pipe1 = new Pipe("obstacle.png", ((int) (Math.random()*151))-275, 548);
   		pipe2 = new Pipe("obstacle.png",((int) (Math.random()*151))-275,748);

		//TODO 3: add UI
		start2 = new UIButton("clickrun.png",60,60);
		restart2 = new UIButton("Restart",170,150);
		instructions = new UIImage("instructions.png",130,150);
		
		
		getReady = new UIImage("getready.png",105,60);
		
		
		gameOver = new UIImage("gameover.png",110,60);
		
		
		root.getChildren().addAll(scenery.getImg(),ground.getImg(),avatar.getImg(),
				pipe1.getImg(), pipe2.getImg(),
				start2.getBtn(), restart2.getBtn(),
				getReady.getImg(), gameOver.getImg(), instructions.getImg());

		restart2.toggle(false);
		instructions.toggle(false);
		getReady.toggle(false);
		gameOver.toggle(false);
		
		//TODO 4: add event handlers
		addActionEventHandler();
		addMouseEventHandler();
		
		//Create scene and add to stage		
		Scene scene = new Scene(root, 400, 400);
		primaryStage.setScene(scene);
		primaryStage.show();
    }
    
	public static void main(String[] args) {
		Application.launch(args);
	}
}