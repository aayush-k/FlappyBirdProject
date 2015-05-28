package game;

import javafx.animation.Interpolator;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.util.Duration;

public class Ground extends Animation{
	TranslateTransition groundMove;
	
	public Ground(String file, String m){
		super(file, ground+25,0,m);
		groundMove = new TranslateTransition(new Duration(0.885*screenTime), img);
    	groundMove.setToX(-400);
    	groundMove.setInterpolator(new Interpolator(){
			protected double curve(double t){
				return t;
			}
		});
    	groundMove.setCycleCount(Timeline.INDEFINITE);
	}
	
	public void play(){
		groundMove.play();
	}
	
	public void pause(){
		groundMove.pause();
	}
}
