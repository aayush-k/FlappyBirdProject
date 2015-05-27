package game;

import javafx.scene.control.Button;
import javafx.scene.image.ImageView;

public class UIButton{
	private ImageView img;
	private Button btn;
	
	public UIButton(String name, int x, int y){
		if(name.contains(".png")){
			String address = getClass().getResource("/" + name).toString();
			img = new ImageView(address);
			btn = new Button();
			btn.setGraphic(img);
			btn.setStyle("-fx-background-color: transparent;");
		} else {
			btn = new Button(name);
		}
		//System.out.println("Width of " + name + " is " + b.getAlignment());
		btn.setLayoutX(x);
		btn.setLayoutY(y);
	}
	
	public Button getBtn(){
		return btn;
	}
	
	public void toggle(boolean toggle){
		if(toggle){
			btn.arm();
		} else {
			btn.disarm();
		}
		btn.setVisible(toggle);
	}
	
	
}
