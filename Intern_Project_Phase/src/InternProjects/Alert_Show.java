package InternProjects;

import javafx.scene.control.Alert;

public class Alert_Show 
{
	public void showAlert(String title,String msg,Alert.AlertType alertName)
	{
		Alert alert = new Alert(alertName);
		alert.setTitle(title);
		alert.setHeaderText(msg);
		alert.setContentText(null);
		alert.show();
	}
}
