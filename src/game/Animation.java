package game;

import javafx.scene.image.ImageView;


public class Animation {
	protected ImageView img;
	protected ImageView img1;
	protected static boolean moving;
	final static double difficulty = 4, ground = 330; 
	
	
	public Animation(String file) {
		String address = getClass().getResource("/" + file).toString();
		img = new ImageView(address);
	}
	
	public Animation(String file, String file1){
		String address = getClass().getResource("/" + file).toString();
		img = new ImageView(address);
		String address1 = getClass().getResource("/" + file1).toString();
		img1 = new ImageView(address1);
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

	public void destroy() {
		// TODO Auto-generated method stub
		
	}
}
