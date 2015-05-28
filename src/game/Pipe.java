package game;

import javafx.animation.Interpolator;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.util.Duration;

	public class Pipe extends Animation {
		TranslateTransition obstacle;
		boolean once = true;
		
		public Pipe(final String file, int y, int x, String m){
			super(file,y,x,m);
			if(message.endsWith("C")){
				obstacle = new TranslateTransition(new Duration(screenTime/2.5), img);
				obstacle.setToX(-200);
				obstacle.setCycleCount(Timeline.INDEFINITE);
				obstacle.setInterpolator(new Interpolator(){
					protected double curve(double t){
						if(t==1){
							Main.passOn(oFile,Y,X-200);
							Y = ((int) (Math.random()*151))-275;
							img.yProperty().set(Y);
						}
						return t;
					}
				});
			} else {
				obstacle = new TranslateTransition(new Duration(screenTime), img);
				obstacle.setToX(-452);
				obstacle.setCycleCount(1);
				obstacle.setInterpolator(new Interpolator(){
					protected double curve(double t){
						try{
							double x_p = img.xProperty().get()-(452*t);
							double y_p = img.yProperty().get();
							if(Main.intersect(x_p,y_p)){
								obstacle.stop();
								Main.end();
							}
							if(x_p<178 && once){
								Main.counter(message);
								once = false;
							}
						} catch (NullPointerException a){
						}
						return t;
					}
				});
			}
		}
		
		public void play(){
			obstacle.play();
		}
		
		public void pause(){
			obstacle.pause();
		}
		
		public void stop(){
			obstacle.stop();
		}
		
		public void destroy(){
			img.setVisible(false);
			obstacle.stop();
			obstacle = null;
			super.destroy();
		}
	}
