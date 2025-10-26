package InternProjects;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.util.Stack;

public class ResetPassword 
{
	public void forgotPassword(Stage primaryStage)
	{
		GetImage icon = new GetImage();
		LoginPage login = new LoginPage();
		
		Label resetLabel = new Label("Password Reset");
		resetLabel.setStyle("-fx-font-size : 20px;" + "-fx-text-fill : white;" + "-fx-font-weight: bold;");
		resetLabel.setPadding(new Insets(10,0,0,88)); //top right bottom left
		
		Separator line = new Separator();
		line.getStyleClass().add("line");
		
		Label descrip = new Label("Forgotten your password? Enter your e-mail  address below, and we'll send you an e-mail  allowing you to reset it.");
		descrip.setStyle("-fx-font-size :13px;" + "-fx-text-fill : #737373;");
		descrip.setWrapText(true);
		descrip.setTranslateX(10);
		descrip.setTranslateY(3);
		
		VBox vbox1 = new VBox(descrip);
		vbox1.setPrefSize(235,80);
		vbox1.setMaxSize(235,80);
		vbox1.setStyle("-fx-background-color : #2E2D2D;");
		vbox1.setTranslateX(40);
		
		TextField emailField = new TextField();
		emailField.getStyleClass().add("loginField");
		emailField.setPromptText("E-mail Address");
		emailField.setPrefSize(270,15);
		emailField.setMaxSize(270,15);
		emailField.setTranslateY(10);
		emailField.setTranslateX(20);
		
		Button resetBtn = new Button("Reset My Password");
		resetBtn.getStyleClass().add("resetBtn");
		resetBtn.setTranslateX(86);
		resetBtn.setTranslateY(30);
		
		ImageView lArrowImg = icon.getSmallIcon("/Icons/left_arrow.png");
		Button lArrBtn = new Button();
		lArrBtn.setGraphic(lArrowImg);
		lArrBtn.getStyleClass().add("navigation");
		lArrBtn.setAlignment(Pos.TOP_LEFT);
		
		lArrBtn.setOnAction(e->{
			login.loginPage(primaryStage);
		});
		
		VBox vbox = new VBox(16,resetLabel,line,vbox1,emailField,resetBtn);
		vbox.getStyleClass().add("resetPasswordScene");
		vbox.setPrefSize(320,300);
		vbox.setMaxSize(320,300);
		vbox.setTranslateX(450);
		vbox.setTranslateY(170);
		
		HBox hbox = new HBox(lArrBtn,vbox);
		hbox.setStyle("-fx-background-color : #2E2D2D;" + "-fx-border-color: transparent;");
		
		Scene scene = new Scene(hbox,1280,650);
		String cssPath = getClass().getResource("/Styles/styles.css").toExternalForm();
		scene.getStylesheets().add(cssPath);
		
		primaryStage.setScene(scene);
		primaryStage.setTitle("Reset Password");
		primaryStage.show();
		primaryStage.centerOnScreen();
	}
}
