package game;

import javafx.scene.image.ImageView;


public class Animation {
	protected ImageView img;
	protected static boolean moving;
	final static int difficulty = 4, ground = 335; 
	protected int Y, X;
	protected String oFile;
	
	
	public Animation(String file, int y, int x) {
		oFile = file;
		String address = getClass().getResource("/" + oFile).toString();
		img = new ImageView(address);
		Y = y;
		X = x;
		img.yProperty().set(Y);
		img.xProperty().set(X);
	}
	
	public ImageView getImg(){
		return img;
	}
	
	public void reset(){
		
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
}
