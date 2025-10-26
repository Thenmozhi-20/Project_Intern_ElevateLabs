package InternProjects;

import javafx.stage.Stage;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;

public class LoginPage 
{
	public void loginPage(Stage primaryStage)
	{
		HomePage todoHome = new HomePage();
		ResetPassword reset = new ResetPassword();
		NewUser newUser = new NewUser();
		Alert_Show getAlert = new Alert_Show();
		DataBaseHandler dbHandler = new DataBaseHandler();
		GetImage icon = new GetImage();
		
		Label loginLabel = new Label("To-Do App");
		loginLabel.getStyleClass().add("loginLabel");
		loginLabel.setPadding(new Insets(11,0,0,0)); //top right bottom left
		
		TextField userName = new TextField();
		userName.setPromptText("User Name");
		userName.getStyleClass().add("loginField");
		userName.setPrefSize(300, 15);
		userName.setMaxSize(300, 15);
		
		TextField password = new TextField();
		password.setPromptText("Password");
		password.getStyleClass().add("loginField");
		password.setPrefSize(300,15);
		password.setMaxSize(300, 15);
		
		VBox vbox2 = new VBox(20,userName,password);
		vbox2.setPrefSize(350,250);
		vbox2.setMaxSize(350,250);
		vbox2.setTranslateX(30);
		
		Button loginBtn = new Button("Sign In");
		loginBtn.getStyleClass().add("todoBtn");
		loginBtn.setTranslateY(-1);
		loginBtn.setTranslateX(8);
		
		Button forgotPassword = new Button("Forgot Password?");
		forgotPassword.getStyleClass().add("forgotPassword");
		
		Button signUp = new Button("Sign Up");
		signUp.getStyleClass().add("forgotPassword");
		
		HBox hbox1 = new HBox(90,forgotPassword,signUp);
		hbox1.setTranslateX(43);
		hbox1.setTranslateY(-8);
		
		VBox vbox1 = new VBox(20);
		vbox1.getChildren().addAll(loginLabel,vbox2,loginBtn,hbox1);
		vbox1.setAlignment(Pos.CENTER);
		vbox1.setPrefSize(390,270);
		vbox1.setMaxSize(390, 270);
		vbox1.getStyleClass().add("loginBox");
		vbox1.setTranslateY(170);
		vbox1.setTranslateX(700);
		
	    ImageView lArrowImg = icon.getSmallIcon("/Icons/left_arrow.png");
		Button lArrBtn = new Button();
		lArrBtn.setGraphic(lArrowImg);
		lArrBtn.getStyleClass().add("navigation");
		lArrBtn.setAlignment(Pos.TOP_LEFT);
		
		VBox root = new VBox();
		
		Image bgImage = new Image(getClass().getResource("/Icons/todoList.png").toExternalForm());
		
		BackgroundImage backgroundImage = new BackgroundImage(
				bgImage,
				BackgroundRepeat.NO_REPEAT,
				BackgroundRepeat.NO_REPEAT,
				BackgroundPosition.CENTER,
				new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO,true,true,true,false)
				);
		
		root.setBackground(new Background(backgroundImage));
		root.getChildren().add(vbox1);
		
		Scene scene = new Scene(root,1280,650);
		String cssPath = getClass().getResource("/Styles/styles.css").toExternalForm();
		scene.getStylesheets().add(cssPath);
		
		primaryStage.setScene(scene);
		primaryStage.setTitle("Login Page");
		primaryStage.show();
		primaryStage.centerOnScreen();
		
		loginBtn.setOnAction(e1 ->{
			String uName = userName.getText();
			String passWrd = password.getText();
			
			if(uName.isEmpty())
			{
				getAlert.showAlert("User Name Missing","Please enter a User Name",Alert.AlertType.WARNING);
			}
			else if(passWrd.isEmpty())
			{
				getAlert.showAlert("Passowrd Missing","Please enter your password.",Alert.AlertType.WARNING);
			}
			
			if(dbHandler.isNewUser(uName,passWrd)==1) 
			{
				loginPage(primaryStage);   // 1 means old user but password wrong  // goes to login page
			}
			else if(dbHandler.isNewUser(uName, passWrd)==2)
			{
				newUser.signUp(primaryStage);  // 2 means new user // goes to sign up page
			}
			else if(dbHandler.isNewUser(uName, passWrd)==0)
			{
				todoHome.homePage(uName,primaryStage);  // 0 means old user // goes to home page
			}
		});
		
		forgotPassword.setOnAction(e->{
			reset.forgotPassword(primaryStage);
		});
		
		signUp.setOnAction(e->{
			newUser.signUp(primaryStage);
		});
	}
}
