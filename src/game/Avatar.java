package game;

import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.SequentialTransition;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

public class Avatar extends Animation{
	SequentialTransition seq;
	static int startHeight = 180;
	private MediaPlayer player;
	private Media flap;
	
	public Avatar(String file, String m) {
		super(file,ground-startHeight,180, m);
		String address = getClass().getResource("/nihao.mp3").toString();
        flap = new Media(address);
	}
	
	public void reset(){
		img.yProperty().set(Y);
	}
	
	public void play(){
		Timeline rise = flight(true);
		Timeline fall = flight(false);
		seq = new SequentialTransition(rise,fall);
		seq.play();
		seq.setOnFinished(new EventHandler<ActionEvent>(){
			@Override
			public void handle(ActionEvent event){
				if(seq.getStatus().toString().equals("STOPPED") && moving){
					Main.end();	
				}
			}
		});
	}
	
	public void pause(){
		seq.pause();
	}
	
	private Timeline flight(boolean initialVelocity){  //uses yproperty and works correctly
		Timeline flight = new Timeline();
    	final double y_c = img.yProperty().get(),
			y_0 = ground - y_c,
			v_0 = (initialVelocity)? jumpVelocity:0,
			time = (initialVelocity)? v_0/9.8 : Math.sqrt(y_0/4.9) + jumpVelocity/9.8,
			delta = (-1 * v_0 * time + (4.9 * time * time)),
			target = (initialVelocity)? y_c + delta: ground;
		KeyValue move = new KeyValue(img.yProperty(), target, new Interpolator() {
			protected double curve(double t) { // t goes 0 to 1,
				double cTime = time*t; //in sec the time elapsed
				double cDelta = (-1*v_0*cTime)+(4.9*cTime*cTime); //current height of the bird
				double c = cDelta/delta;
				return c; //convert to decimal progress from 0 to 1
			}
		});
		double scaler = (initialVelocity)? 1.5:2.0;
		Duration scaledTime = Duration.seconds(time/(sensitivity*scaler));
		KeyFrame birdFlight = new KeyFrame(scaledTime, move);
		flight.getKeyFrames().add(birdFlight);
		if(initialVelocity){
        	player = new MediaPlayer(flap);
        	player.setRate(1.5);
        	player.play();
        }
		return flight;
    }
}
