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
	private static Animation scenery, ground, avatar, pipeC, pipe1, pipe2;
	private static UIImage getReady, gameOver, instructions;
	private static UIButton start, restart, pause, resume;
	private static Text score, highScore, levelID;
	private static Group root;
	private static Boolean alternate = true;
	private static int counter = 0;
	
	private void addActionEventHandler(){
    	start.getBtn().setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) { 
        		Animation.setMotion(true);
        		Animation.startLevel();
            	counter = 0;
            	score.setVisible(true);
            	score.setText(counter + "");
        		ground.play();
            	scenery.play();
            	start.toggle(false);
            	pause.toggle(true);
            	getReady.toggle(true);
            	instructions.toggle(true);
            	int top1 = ((int) (Math.random()*151))-275; //-95 to -270
            	pipeC = new Pipe("obstacle.png",top1,600,"pipeC");
            	root.getChildren().addAll(pipeC.getImg());
            	//wait, toggle getReady and jump
            	avatar.play();
            	pipeC.play();
            }
        });
    	restart.getBtn().setOnAction(new EventHandler<ActionEvent>() { //restart button only appears at game over
             @Override
             public void handle(ActionEvent event) {
            	 restart.toggle(false);
            	 gameOver.toggle(false);
            	 avatar.reset();
            	 start.toggle(true);
            	 score.setVisible(false);
             	}
    	});
    	pause.getBtn().setOnAction(new EventHandler<ActionEvent>() {
             @Override
             public void handle(ActionEvent event) {
            	 try{
            		 Animation.setMotion(false);
            		 ground.pause();
                	 scenery.pause();
                	 avatar.pause();
                	 pipeC.pause();
                	 pipe1.destroy();
                	 pipe2.destroy();
            	 } catch (Exception a){
            		 
            	 }
            	 pause.toggle(false);
            	 resume.toggle(true);
             	}
    	});
    	resume.getBtn().setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
           	 try{
           		 Animation.setMotion(true);
           		 ground.play();
	          	 scenery.play();
	          	 avatar.play();
	          	 pipeC.play();
           	 } catch (Exception a) {
           		 
           	 }
           	 resume.toggle(false);
           	 pause.toggle(true);
            }
   	});
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
   		scenery = new Scenery("background2.png","scenery");
   		ground = new Ground("ground2.png","ground");
   		avatar = new Avatar("flappy.png","bird");
   		
		//TODO 3: add UI
		start = new UIButton("clickrun.png",60,60);
		restart = new UIButton("Restart",170,150);
		pause = new UIButton("Pause", 350,10);
		resume = new UIButton("Resume", 350,10);
		instructions = new UIImage("instructions.png",130,150);
		
		getReady = new UIImage("getready.png",105,60);
		gameOver = new UIImage("gameover.png",110,60);
		
		score = new Text("0");
		score.setFont(new Font("Calibri", 30.0));
		score.setLayoutX(183);
		score.setLayoutY(50);
		
		highScore = new Text("High Score: 0");
		highScore.setFont(new Font("Calibri", 12.0));
		highScore.setLayoutX(5);
		highScore.setLayoutY(15);
		
		levelID = new Text();
		levelID.setFont(new Font("Calibri", 12.0));
		levelID.setLayoutX(180);
		levelID.setLayoutY(15);
		
		root.getChildren().addAll(scenery.getImg(),ground.getImg(),avatar.getImg(),
				start.getBtn(), restart.getBtn(), pause.getBtn(), resume.getBtn(),
				score, highScore, levelID,
				getReady.getImg(), gameOver.getImg(), instructions.getImg());

		restart.toggle(false);
		instructions.toggle(false);
		getReady.toggle(false);
		gameOver.toggle(false);
		score.setVisible(false);
		pause.toggle(false);
		resume.toggle(false);
		
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
		pipeC.destroy();
		try{
			pipe1.destroy();
			pipe2.destroy();
		} catch(Exception a){}
		root.getChildren().addAll(scenery.getImg(),ground.getImg(),avatar.getImg(),
				start.getBtn(), restart.getBtn(), pause.getBtn(), resume.getBtn(),
				score, highScore, levelID,
				getReady.getImg(), gameOver.getImg(), instructions.getImg());
		//game over screen components:
		ground.pause();
		scenery.pause();
		pause.toggle(false);
		resume.toggle(false);
		restart.toggle(true);
		getReady.toggle(false);
		instructions.toggle(false);
		gameOver.toggle(true);
	}

	public static void main(String[] args) {
		Application.launch(args);
	}

	public static void counter(String m){
		counter++;
		score.setText(counter + "");
		levelID.setText(Animation.level(counter));
		if(counter>Integer.parseInt(highScore.getText().substring(12))){
			highScore.setText("High Score: " + counter);
		}
	}

	public static boolean intersect(double x_p, double y_p){
		boolean topCollision = avatar.getImg().intersects(x_p, y_p, 52, 320);
		boolean botCollision = avatar.getImg().intersects(x_p, y_p+470, 52, 320);
		return topCollision || botCollision;
	}

	public static void passOn(String file, int y, int x){
		//secondary phase of each pipe animation
		if(alternate){
			pipe1 = new Pipe(file,y,x,"pipe1");
			pipe1.play();
			root.getChildren().add(1,pipe1.getImg());
			alternate = false;
		} else {
			pipe2 = new Pipe(file,y,x,"pipe2");
			pipe2.play();
			root.getChildren().add(1,pipe2.getImg());
			alternate = true;
		}
	}
}