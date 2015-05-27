package game;

import javafx.scene.image.ImageView;

public class UIImage{
	private ImageView img;
	
	public UIImage(String name, int x, int y){
		String address = getClass().getResource("/" + name).toString();
		img = new ImageView(address);
		img.setLayoutX(x);
		img.setLayoutY(y);
	}
	
	public ImageView getImg(){
		return img;
	}
	
	public void toggle(boolean toggle){
		img.setVisible(toggle);
	}
}
