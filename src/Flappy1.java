import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
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

/* Name: Aayush Kumar
 * Class: APCS-Spring 2015
 * Date: 5/14/15
 */

/*TODO: Use below code to make a more instanteous stop for the background animation when the bird hits the ground
 *	while(moving){
 		if(flappy.yProperty().get()==375){
 			backgroundMove.stop();
			moving=false;
		}
	}
 *
 * HANDY CALCULATIONS:
 * y(t) = -0.5*g*t^2 + v_0*t + y_0
 * h = -4.9t^2 + v_0*t + h_0
 * 
 * 0 = 4.9t^2 + fallHeight
 * sqrt(fallHeight/4.9)
 * 
 * v(t) = -g*t + v_0
 * 0 = -9.8*t + 15
 * 15 = 9.8*t
 */

public class Flappy1 extends Application{
	
	private ImageView bkgrd = null ;
	private static ImageView flappy = null ;
	private Button button = null;
	private MediaPlayer player = null;
	private Group root = null;
	static boolean moving = true;
	TranslateTransition backgroundMove = null;
	final static double ground = 375;
	static double startHeight = 180; //initial height of bird
	final static double jumpVelocity = 40; //v_0 at which bird jumps up from current location (meters/second)
	final static double jumpDuration = jumpVelocity / 9.8; //calculation for jump duration (see above)
	final static double jumpHeight = jumpVelocity * jumpDuration - (4.9 * jumpDuration * jumpDuration); //calculation for jump distance (see above)
	static double fallHeight = 0; //INITIAL calculation for drop distance, gets redefined in start(Stage primaryStage)
	static double fallDuration = 0; //calculation for drop duration(see above)
	
	
    private void addActionEventHandler(){
    
        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) { 
            	flight(false);
            	background();
            	button.disarm();
            	button.setVisible(false);
            }
        });
    }
    
    private void addMouseEventHandler(){
    	root.onMouseClickedProperty().set(new EventHandler<MouseEvent>() {
    		public void handle(MouseEvent event) {
    			flight(true);
    		}
        });
    }
    
    private void background(){
    	backgroundMove = new TranslateTransition(new Duration(2000), bkgrd);
    	backgroundMove.setToX(-400);
    	backgroundMove.setInterpolator(Interpolator.LINEAR);
    	backgroundMove.setCycleCount(Timeline.INDEFINITE);
    	backgroundMove.play();
    }
    
    private void flight(boolean initialVelocity){
    	Timeline timeline = new Timeline();
    	//debugPrint("PreMovement Stage");

    	if(initialVelocity){
			KeyValue rise = new KeyValue(flappy.yProperty(), flappy.yProperty().get() - jumpHeight, new Interpolator() {
    			protected double curve(double t) { // t goes 0 to 1,
    				double cTime = jumpDuration*t; //in sec the time elapsed
    				double cJump = (jumpVelocity*cTime)-(4.9*cTime*cTime); //current height of the bird
    				double c = (cJump/jumpHeight);
    				return c; //convert to decimal progress from 0 to 1
    			}
            });
        	KeyFrame birdRise = new KeyFrame(Duration.seconds(jumpDuration/5), rise);
        	timeline.getKeyFrames().add(birdRise);
            player.play();
            //debugPrint("After Jump Stage");
    	}
    	KeyValue fall = new KeyValue(flappy.yProperty(), ground, new Interpolator() {
			protected double curve(double t) {
				double cTime = fallDuration*t; //in sec the time elapsed
				double cDrop = (4.9*cTime*cTime); // distance dropped
				double c = (cDrop/fallHeight);
				return c; //convert to decimal progress
			}
        }); 
    	KeyFrame birdFall = new KeyFrame(Duration.seconds(fallDuration/3), fall);
    	timeline.getKeyFrames().add(birdFall);
        timeline.play();
        if(flappy.yProperty().get()==375){
        	backgroundMove.stop();
        }
        //debugPrint("After Fall Stage");
    }
    
    public static void debugPrint(String stage){
		System.out.println("\n"+stage);
		System.out.println("Fall Height: " + fallHeight);
		System.out.println("Fall Duration: " + fallDuration);
		System.out.println("Current Height: " + flappy.yProperty().get());
	}

	
	@Override
	public void start(Stage primaryStage) throws Exception {
		//Create a Group 
		root = new Group();
		
		//TODO 1: add background
		//String bAddress = getClass().getResource("/background2.png").toString();
		
		bkgrd = new ImageView("background.png");
		bkgrd.setFitWidth(800);
		
		
		//TODO 2: add Flappy
		flappy = new ImageView("flappy.png");
		flappy.xProperty().set(180);
		flappy.yProperty().set(ground-startHeight);
		fallHeight = ground - flappy.yProperty().get();
		fallDuration = Math.sqrt(fallHeight/4.9);
		
		//TODO 3: add Button
		button = new Button("Start");
		button.setLayoutX(175);
		button.setLayoutY(175);
		
		//TODO 4: add Sound
    	String address = getClass().getResource("/flap.mp3").toString();
        Media m = new Media(address);
        player = new MediaPlayer(m);
		
		//Add controls
		root.getChildren().add( bkgrd );
		root.getChildren().add( flappy );
		root.getChildren().add( button);
		
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
