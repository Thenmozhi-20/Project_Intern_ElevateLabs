package InternProjects;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class GetImage 
{
	public ImageView getIcon(String path)
	{
		Image image = new Image(getClass().getResourceAsStream(path));
		ImageView iconView = new ImageView(image);
		iconView.setFitHeight(35);
		iconView.setFitWidth(35);
		return iconView;
	}
	
	public ImageView getSmallIcon(String path)
	{
		Image image = new Image(getClass().getResourceAsStream(path));
		ImageView iconView = new ImageView(image);
		iconView.setFitHeight(25);
		iconView.setFitWidth(25);
		return iconView;
	}
}
