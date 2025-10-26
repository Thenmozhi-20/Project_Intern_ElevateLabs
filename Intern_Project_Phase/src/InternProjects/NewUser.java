package InternProjects;

import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.geometry.Insets;
import javafx.geometry.Pos;

public class NewUser 
{
	public void signUp(Stage primaryStage)
	{
		GetImage icon = new GetImage();
		LoginPage login= new LoginPage();
		Alert_Show alert = new Alert_Show();
		DataBaseHandler dbHandler = new DataBaseHandler();
		HomePage home = new HomePage();
		
		Label todoLabel = new Label("To-Do App");
		todoLabel.setStyle("-fx-text-fill : white;"+ "-fx-font-size : 40px;"+"-fx-font-weight: bold;");
		todoLabel.setTranslateX(490);
		todoLabel.setTranslateY(40);
		
		Label signUp = new Label("Sign Up");
		signUp.setStyle("-fx-text-fill : white;"+ "-fx-font-size : 23px;"+"-fx-font-weight: bold;");
		signUp.setTranslateX(135);
		signUp.setTranslateY(15);
		
		TextField uNameField = new TextField();
		uNameField.setPromptText("User Name");
		uNameField.getStyleClass().add("signUpFiedld");
		uNameField.setPrefSize(280,13);
		uNameField.setMaxSize(280,13);
		uNameField.setTranslateX(60);
		uNameField.setTranslateY(15);
		
		TextField passwordField =new TextField();
		passwordField.setPromptText("Password");
		passwordField.getStyleClass().add("signUpFiedld");
		passwordField.setPrefSize(280,13);
		passwordField.setMaxSize(280,13);
		passwordField.setTranslateX(60);
		passwordField.setTranslateY(15);
		
		TextField confirmField = new TextField();
		confirmField.setPromptText("Confirm Password");
		confirmField.getStyleClass().add("signUpFiedld");
		confirmField.setPrefSize(280,13);
		confirmField.setMaxSize(280,13);
		confirmField.setTranslateX(60);
		confirmField.setTranslateY(15);
		
		TextField emailField = new TextField();
		emailField.setPromptText("Email Address");
		emailField.getStyleClass().add("signUpFiedld");
		emailField.setPrefSize(280,13);
		emailField.setMaxSize(280,13);
		emailField.setTranslateX(60);
		emailField.setTranslateY(15);
		
		Button signUpBtn = new Button("Create account");
		signUpBtn.getStyleClass().add("signUpBtn");
		signUpBtn.setTranslateX(60);
		signUpBtn.setTranslateY(15);
		
		Label signIn = new Label("Have an account?");
		signIn.setStyle("-fx-text-fill: #8A8888;" + "-fx-font-size: 12px;");
		signIn.setTranslateY(5);
		
		Button signInBtn = new Button("Sign In");
		signInBtn.getStyleClass().add("signInBtn");
		
		HBox hbox1 = new HBox(3,signIn,signInBtn);
		hbox1.setTranslateX(120);
		
		LoginPage signInPage = new LoginPage();
		signInBtn.setOnAction(e1->{
			signInPage.loginPage(primaryStage);
		});
		
		signUpBtn.setOnAction(e->{
			String name = uNameField.getText();
			String password = passwordField.getText();
			String conPassword = confirmField.getText();
			String email = emailField.getText();
			
			if(name.isEmpty()) alert.showAlert("Missing","User Name is required",Alert.AlertType.WARNING);
			else if(password.isEmpty()) alert.showAlert("Missing","Password is requird",Alert.AlertType.WARNING);
			else if(conPassword.isEmpty()) alert.showAlert("Missing", "Confirm Password is required", Alert.AlertType.WARNING);
			else if(!password.equals(conPassword)) alert.showAlert("Not Same","Password and Confirm Passoword should same.",Alert.AlertType.WARNING);
			else if(email.isEmpty()) alert.showAlert("Missing","Email is required",Alert.AlertType.WARNING);
			
			dbHandler.addNewUser(name,password,conPassword,email);
			alert.showAlert("Sign Up","You have successfully Signed in!",Alert.AlertType.CONFIRMATION);
			home.homePage(name, primaryStage);
		});
		
		VBox vbox1 = new VBox(18);
		vbox1.getChildren().addAll(signUp,uNameField,passwordField,confirmField,emailField,signUpBtn,hbox1);
		vbox1.getStyleClass().add("vbox3");
		vbox1.setPrefSize(405,325);
		vbox1.setMaxSize(405,325);
		vbox1.setTranslateX(390);
		vbox1.setTranslateY(120);
		
		HBox hbox = new HBox(vbox1);
		
		ImageView lArrowImg = icon.getSmallIcon("/Icons/left_arrow.png");
		Button lArrBtn = new Button();
		lArrBtn.setGraphic(lArrowImg);
		lArrBtn.getStyleClass().add("navigation");
		lArrBtn.setAlignment(Pos.TOP_LEFT);
		
		lArrBtn.setOnAction(e->{
			login.loginPage(primaryStage);
		});
		
		VBox vbox = new VBox(15,lArrBtn,todoLabel,hbox);
		vbox.setStyle("-fx-background-color : #000000;");
		
		Scene scene = new Scene(vbox,1280,650);
		String cssPath = getClass().getResource("/Styles/styles.css").toExternalForm();
		scene.getStylesheets().add(cssPath);
		
		primaryStage.setScene(scene);
		primaryStage.setTitle("Sign Up");
		primaryStage.show();
		primaryStage.centerOnScreen();
	}
}
