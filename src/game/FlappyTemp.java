package game;
import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.SequentialTransition;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import javafx.util.Duration;

/*TODO: there's a bug where if hit the ground, the game doesn't end (happens when you jump above ceiling, spam jump, etc)
 * make getReadyImg show up for a certain time interval after which the game starts/bird falls
 * 	this will be with some type of delay in the start button action event handle method
 * 
 * if (c == 1 && y_0<5){
					groundMove.pause();
					backgroundMove.pause();
		        	moving = false;
		            getReadyImg.setVisible(false);
		        	gameOverImg.setVisible(true);
		        	restart.arm();
	            	restart.setVisible(true);
				}
 * 
 * Name: Aayush Kumar
 * Period: APCS-Spring 2015
 * Date: 5/18/15
 */

public class FlappyTemp extends Application{
	private static ImageView bkgrd, groundImg, startImg, gameOverImg, getReadyImg, flappy;
	private Button start, restart;
	private MediaPlayer player;
	private Media m;
	private Group root;
	Exception GameOver = new Exception();
	static boolean moving;
	TranslateTransition groundMove,backgroundMove;
	final static double difficulty = 4, ground = 330, startHeight = 220, jumpVelocity = 40; 
	
	private void addActionEventHandler(){
    	start.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) { 
            	moving = true;
            	groundMove.play();
            	backgroundMove.play();
            	start.disarm();
            	start.setVisible(false);
            	getReadyImg.setVisible(true);
            	Timeline fall = flight(false);
            	fall.play();
            }
        });
    	restart.setOnAction(new EventHandler<ActionEvent>() { //restart button only appears at game over
             @Override
             public void handle(ActionEvent event) { 
              	restart.disarm();
            	restart.setVisible(false);
            	gameOverImg.setVisible(false);
              	flappy.yProperty().set(startHeight);
              	start.arm();
              	start.setVisible(true);
             }
    	});
    }
    
    private void addMouseEventHandler(){
    	root.onMouseClickedProperty().set(new EventHandler<MouseEvent>() {
    		public void handle(MouseEvent event) {
    			if(moving){
    				getReadyImg.setVisible(false);
    				Timeline rise = flight(true);
    				Timeline fall = flight(false);
    				SequentialTransition seq = new SequentialTransition(rise,fall);
    				seq.play();
    			} 			
    		}
        });
    }
    
    private Timeline flight(boolean initialVelocity) {  //uses yproperty and works correctly
		Timeline flight = new Timeline();
    	final double y_c = flappy.yProperty().get(),
			y_0 = ground - y_c,
			v_0 = (initialVelocity)? jumpVelocity:0,
			time = (initialVelocity)? v_0/9.8 : Math.sqrt(y_0/4.9) + jumpVelocity/9.8,
			delta = (-1 * v_0 * time + (4.9 * time * time)),
			target = (initialVelocity)? y_c + delta: ground;
		KeyValue move = new KeyValue(flappy.yProperty(), target, new Interpolator() {
			protected double curve(double t) { // t goes 0 to 1,
				double cTime = time*t; //in sec the time elapsed
				double cDelta = (-1*v_0*cTime)+(4.9*cTime*cTime); //current height of the bird
				double c = cDelta/delta;
				if(y_0<5){
					//throw new MyException("test");
					groundMove.pause();
					backgroundMove.pause();
		        	moving = false;
		            getReadyImg.setVisible(false);
		        	gameOverImg.setVisible(true);
		        	restart.arm();
	            	restart.setVisible(true);
				}
				return c; //convert to decimal progress from 0 to 1
			}
		});
		double scaler = (initialVelocity)? 1.5:2.0;
		Duration scaledTime = Duration.seconds(time/(difficulty*scaler));
		KeyFrame birdFlight = new KeyFrame(scaledTime, move);
		flight.getKeyFrames().add(birdFlight);
		String movement = (initialVelocity)? "Jump":"Fall";
		System.out.println(movement + " from " + y_c + " to "+ target + "\n\tin " + scaledTime);
		if(initialVelocity){
        	player = new MediaPlayer(m);
        	player.play();
        }
		return flight;
    }
    
    @Override
	public void start(Stage primaryStage) throws Exception {
		//Create a Group 
		root = new Group();
		
		//TODO 1: add background
		bkgrd = new ImageView("background2.png");
		groundImg = new ImageView("ground2.png");
		groundImg.yProperty().set(355);
		groundMove = new TranslateTransition(new Duration(2000), groundImg);
    	groundMove.setToX(-400);
    	groundMove.setInterpolator(Interpolator.LINEAR);
    	groundMove.setCycleCount(Timeline.INDEFINITE);
    	backgroundMove = new TranslateTransition(new Duration(10000), bkgrd);
    	backgroundMove.setToX(-400);
    	backgroundMove.setInterpolator(Interpolator.LINEAR);
    	backgroundMove.setCycleCount(Timeline.INDEFINITE);
		
		//TODO 2: add Flappy
    	flappy = new ImageView("flappy.png");
		flappy.xProperty().set(180);
		flappy.yProperty().set(ground-startHeight);
		
		//TODO 3: add Buttons
		startImg = new ImageView("clickrun.png");
		start = new Button();
		start.setLayoutX(60);
		start.setLayoutY(60);
		start.setGraphic(startImg);
		start.setStyle("-fx-background-color: transparent;");
		restart = new Button("Restart");
		restart.setPrefWidth(100);
		restart.setLayoutX(150);
		restart.setLayoutY(150);
		restart.setVisible(false);
		restart.disarm();

		//TODO 4: add Sound
		String address = getClass().getResource("/flap.mp3").toString();
        m = new Media(address);
		
        //TODO 5: add graphics
        getReadyImg = new ImageView("getready.png");
        getReadyImg.xProperty().set(105);
        getReadyImg.yProperty().set(60);
        getReadyImg.setVisible(false);
        gameOverImg = new ImageView("gameover.png");
        gameOverImg.xProperty().set(110);
        gameOverImg.yProperty().set(60);
        gameOverImg.setVisible(false);
        
		//Add controls
		root.getChildren().addAll(bkgrd,groundImg,flappy,start,restart,gameOverImg,getReadyImg);
		
		//TODO 4: add action handler to the button
		addActionEventHandler();
		
		//TODO 5: add mouse handler to the scene
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
