package game;

import javafx.animation.Interpolator;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

public class Ground extends Animation{
	TranslateTransition groundMove;
	
	public Ground(String file){
		super(file, ground+25,0);
		groundMove = new TranslateTransition(new Duration(5000), img);
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
