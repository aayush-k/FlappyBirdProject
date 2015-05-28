package game;

import javafx.scene.image.ImageView;


public class Animation {
	protected ImageView img;
	protected static boolean moving;
	protected static int sensitivity = 3, ground = 335, screenTime = 5000, jumpVelocity = 30;
	protected int Y, X;
	protected String oFile;
	protected String message;
	
	
	public Animation(String file, int y, int x, String m) {
		message = m;
		oFile = file;
		String address = getClass().getResource("/" + oFile).toString();
		img = new ImageView(address);
		Y = y;
		X = x;
		img.yProperty().set(Y);
		img.xProperty().set(X);
	}
	
	public String message(){
		return message;
	}
	
	public ImageView getImg(){
		return img;
	}
	
	public void reset(){
		
	}
	
	public static void startLevel(){
		sensitivity = 4;
		jumpVelocity = 35;
		screenTime = 5000;
	}
	
	public static void setMotion(boolean m){
		moving = m;
	}
	
	public static boolean getMotion(){
		return moving;
	}
	
	public void play(){
		
	}
	
	public void pause(){
		
	}
	
	public void stop(){
		
	}
	
	public void destroy() {
		img = null;
	}
	
	public static String level(int level){
		if(level%10==0){
			sensitivity++;
			return "Level Up: Sensitivity Increased!";
		} else if(level%5==0){
			jumpVelocity+=5;
			return "Level Up: Gravity Decreased!";
		}
		return "";
	}
}
