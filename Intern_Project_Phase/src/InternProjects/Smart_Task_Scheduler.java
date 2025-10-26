package InternProjects;

import javafx.application.Application;
import javafx.stage.Stage;
import java.sql.*;

public class Smart_Task_Scheduler extends Application
{
	public void start(Stage primaryStage)
	{
		LoginPage login = new LoginPage();
		login.loginPage(primaryStage);
	}
	
	public static void main(String args[]) throws SQLException
	{
		DBConnection db = new DBConnection();
	    DBConnection.getConnection();
		Application.launch(args);
	}
}
