package game;

import javafx.animation.Interpolator;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.util.Duration;

public class Scenery extends Animation {
	TranslateTransition sceneryMove;
	
	public Scenery(String file){
		super(file);
		sceneryMove = new TranslateTransition(new Duration(10000), img);
		sceneryMove.setToX(-400);
		sceneryMove.setInterpolator(Interpolator.LINEAR);
		sceneryMove.setCycleCount(Timeline.INDEFINITE);
	}
	
	public void play(){
		sceneryMove.play();
	}
	
	public void pause(){
		sceneryMove.pause();
	}
}
