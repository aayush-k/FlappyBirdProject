package game;

import javafx.animation.Interpolator;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.util.Duration;

	public class Pipe extends Animation {
		TranslateTransition obstacle;
		private int Y, X;
		private String oFile;
		
		public Pipe(final String file, int y, int x){
			super(file);
			oFile = file;
			Y = y;
			X = x;
			img.yProperty().set(Y);
			img.xProperty().set(X);
			obstacle = new TranslateTransition(new Duration(5000), img);
			obstacle.setToX(-400);
			obstacle.setCycleCount(Timeline.INDEFINITE);
			obstacle.setInterpolator(new Interpolator(){
				protected double curve(double t){
					if(t==1){
						Main.passOn(oFile,Y,X-400);
						Y = ((int) (Math.random()*151))-275;
						img.yProperty().set(Y);
					}
					return t;
				}
			});
		}
		
		public Pipe(final String file, int y, int x, boolean extention){
			super(file);
			oFile = file;
			Y = y;
			X = x;
			img.yProperty().set(Y);
			img.xProperty().set(X);
			obstacle = new TranslateTransition(new Duration(5000), img);
			obstacle.setToX(-400);
			obstacle.setCycleCount(1);
			obstacle.setInterpolator(Interpolator.LINEAR);
		}
		
		public void play(){
			obstacle.play();
		}
		
		public int[] getRange(){
			int[] range = {Y+320,Y+395};
			return range;
		}
		
		public void pause(){
			obstacle.pause();
		}
		
		public void destroy(){
			obstacle.stop();
			obstacle = null;
			img=null;
		}
	}
