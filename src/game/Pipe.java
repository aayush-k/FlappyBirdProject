package game;

import javafx.animation.Interpolator;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.util.Duration;

	public class Pipe extends Animation {
		TranslateTransition obstacle;
		boolean b = true;
		
		public Pipe(final String file, int y, int x){
			super(file,y,x);
			obstacle = new TranslateTransition(new Duration(5000), img);
			obstacle.setToX(-400);
			obstacle.setCycleCount(Timeline.INDEFINITE);
			obstacle.setInterpolator(new Interpolator(){
				protected double curve(double t){
					try{
						double x_p = img.xProperty().get()-(400*t);
						double y_p = img.yProperty().get();
						if(Main.intersect(x_p,y_p)){
							obstacle.stop();
							Main.end();
						}
						if((int)x_p==178 && b){
							Main.counter();
							b = false;
						}
					} catch (NullPointerException a){
					}
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
			super(file,y,x);
			oFile = file;
			Y = y;
			X = x;
			img.yProperty().set(Y);
			img.xProperty().set(X);
			obstacle = new TranslateTransition(new Duration(5000), img);
			obstacle.setToX(-400);
			obstacle.setCycleCount(1);
			obstacle.setInterpolator(new Interpolator(){
				protected double curve(double t){
					try{
						double x_p = img.xProperty().get()-(400*t);
						double y_p = img.yProperty().get();
						if(Main.intersect(x_p,y_p)){
							obstacle.stop();
							Main.end();
						}
						if((int)x_p==178 && b){
							Main.counter();
							b = false;
						}
					} catch (NullPointerException a){
					}
					return t;
				}
			});
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
		
		public void stop(){
			obstacle.stop();
		}
		
		public void destroy(){
			obstacle.stop();
			obstacle = null;
			super.destroy();
		}
	}
