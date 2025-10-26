package InternProjects;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Separator;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.PriorityQueue;


public class HomePage 
{
	public void homePage(String userName,Stage primaryStage)
	{
		Alert_Show alert = new Alert_Show();
		GetImage icon = new GetImage();
		DataBaseHandler dbHandler = new DataBaseHandler();
		LoginPage login = new LoginPage();
		
		ImageView lArrowImg = icon.getSmallIcon("/Icons/left_arrow.png");
		Button lArrBtn = new Button();
		lArrBtn.setGraphic(lArrowImg);
		lArrBtn.getStyleClass().add("navigation");
		lArrBtn.setAlignment(Pos.TOP_LEFT);
		
		lArrBtn.setOnAction(e->{
			login.loginPage(primaryStage);
		});
		
		ImageView userView = icon.getIcon("/Icons/user.png");
		Button userBtn = new Button();
		userBtn.setGraphic(userView);
		userBtn.getStyleClass().add("userDetails");
		userBtn.setAlignment(Pos.TOP_RIGHT);
		userBtn.setTranslateX(1200);
		
		Button profileView= new Button("View Profile");
		Button taskDetails = new Button("Task Details");
		Button signOut = new Button("Sign Out");
		
		profileView.getStyleClass().add("profile");
		taskDetails.getStyleClass().add("profile");
		signOut.getStyleClass().add("profile");
		
		VBox profile = new VBox(profileView,taskDetails,signOut);
		
		userBtn.setOnMouseEntered(e->{
			//dbHandler.showUserDetails(userName);
		});
		
		Label todoLabel = new Label("My Todos");
		todoLabel.setAlignment(Pos.TOP_CENTER);
		todoLabel.setPadding(new Insets(8,0,0,0));
		todoLabel.setTranslateX(600);
		todoLabel.getStyleClass().add("todoLabel");
		
		Label motive = new Label("keep it up!");
		motive.getStyleClass().add("motive");
		
		//ProgressBar bar = new ProgressBar(0);
		
		Label title = new Label("Title");
		title.getStyleClass().add("tdl");
		Label descrip = new Label("Description");
		descrip.getStyleClass().add("tdl");
		Label priority = new Label("Priority(5 to 1)");
		priority.getStyleClass().add("tdl");
		
		TextField tleField = new TextField();
		tleField.setPrefSize(270,10);
		tleField.setPromptText("What's the title of your To Do?");
		tleField.getStyleClass().add("tdf");
		
		TextField desField = new TextField();
		desField.setPrefSize(270,10);
		desField.setPromptText("format: \"yyyy-MM-dd HH:mm:ss\"");
		desField.getStyleClass().add("tdf");
		
		TextField priorityField = new TextField();
		priorityField.setPrefSize(10,3);
		priorityField.setPromptText("5-1");
		priorityField.getStyleClass().add("tdf");
		
		VBox vbox1 = new VBox(6,title,tleField);
		vbox1.setPadding(new Insets(20,0,0,12)); // top right bottom left
    	VBox vbox2 = new VBox(6,descrip,desField);
		vbox2.setPadding(new Insets(20,0,0,0));
		VBox vbox33 = new VBox(6,priority,priorityField);
		vbox33.setPadding(new Insets(20,0,0,0));
		
		Button addButton = new Button("Add");
		addButton.setTranslateY(24);
		addButton.getStyleClass().add("addBtn");
		
		HBox hbox1 = new HBox(30,vbox1,vbox2,vbox33);
		
		HBox hbox2 = new HBox(30);
		hbox2.getChildren().addAll(hbox1,addButton);
		hbox2.setAlignment(Pos.CENTER);
		
		Separator line1 = new Separator();
		line1.getStyleClass().add("line");
		
		Button todoBtn = new Button("To Do");
		todoBtn.getStyleClass().add("todoTaskBtn");
		
		Button finishBtn = new Button("Completed");
		finishBtn.getStyleClass().add("completeBtn");
		
		HBox hbox3 = new HBox(0,todoBtn,finishBtn);
		hbox3.setPadding(new Insets(0,0,0,40));
	
		VBox vbox3 = new VBox(20,hbox2,line1,hbox3);
		vbox3.setPrefSize(900,490);
		vbox3.setMaxSize(900,490);
		vbox3.getStyleClass().add("vbox3");
		
		ArrayList<Task> taskList = dbHandler.getAllTasks(userName);
		
		PriorityQueue<Task> pq = new PriorityQueue<>(
			    (a, b) -> {
			        if(b.priority != a.priority)
			            return b.priority - a.priority;           // higher priority first
			        else
			            return a.deadLine.compareTo(b.deadLine); // earlier deadline first
			    }
			);
	    pq.addAll(taskList);
	    
	    //  Schedule reminders for all previously stored tasks
	    for(Task task : pq) {
	        task.scheduleReminder();
	    }
	    
	    dbHandler.loadPreviousTasks(userName,pq,vbox3,icon,dbHandler); //it helps to get all the todo's and show them all.
		
	    
	    ScrollPane pane = new ScrollPane(vbox3);
		pane.setFitToWidth(false);  // it avoids that scroll bar placed at the scene size.
		// the vertical scroll is already in true.
		pane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER); //  disable horizontal scroll
		pane.setStyle("-fx-background-color: transparent;");
		pane.getStyleClass().add("scroll-bar");

		// Wrap in a centered container
		HBox paneContainer = new HBox(pane);
		paneContainer.setAlignment(Pos.CENTER); 
		
		VBox vbox = new VBox(5);
		vbox.getChildren().addAll(lArrBtn,userBtn,todoLabel,paneContainer);
	    //	vbox.setAlignment(Pos.TOP_CENTER);
		vbox.getStyleClass().add("scenebox");
		
		addButton.setOnAction(e->{
			String titleText = tleField.getText();
			String descText = desField.getText();
			String priorityValue = priorityField.getText();
			
			if(titleText.isEmpty())
				alert.showAlert("Missing","Enter the title of your To Do",Alert.AlertType.WARNING);
			
			else if(descText.isEmpty())
				alert.showAlert("Missing","Enter the description of your To Do",Alert.AlertType.WARNING);

			else if(priorityValue.isEmpty())
				alert.showAlert("Missing","Enter the Priority from 1 to 5.",Alert.AlertType.WARNING);
			
			else
			{	
				int priorityNo = Integer.parseInt(priorityValue);
				
				DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
			    LocalDateTime deadline = LocalDateTime.parse(descText, formatter);
			    
				if(priorityNo==0)
					alert.showAlert("Wrong Priority Value","Enter Priority range 5 to 1.",Alert.AlertType.WARNING);
				else
				{
					Task newTask = new Task(titleText, deadline, priorityNo);
					 
					dbHandler.addTask(userName,titleText,deadline,priorityNo);
					
					newTask.scheduleReminder();
					
					load(userName,titleText,descText,priorityValue,vbox3,icon,dbHandler);
					
					pq.add(newTask);
				}
			}
		});
		
		todoBtn.setOnAction(e->{
			homePage(userName,primaryStage);
		});
		
		
		finishBtn.setOnAction(e->{  // it will done, when the completed button clicks, it starts to shown all the completed tasks.	
			Label completedLabel = new Label("Your Completed Tasks");
			completedLabel.setStyle("-fx-font-size: 25px;" + "-fx-text-fill: #ffffff;"+"-fx-font-weight:bold;");
			completedLabel.setTranslateY(35);
			completedLabel.setTranslateX(400);
			
			HBox paneContainer1 = dbHandler.loadCompletedTasks(userName);
			//paneContainer1.setPadding(new Insets(0,0,40,0)) ; //// top right bottom left
			
			lArrBtn.setOnAction(e1->{
				homePage(userName,primaryStage);
			});
			
			
			VBox vbox5 = new VBox(50);
			vbox5.getChildren().addAll(lArrBtn,completedLabel,paneContainer1);
	    	//	vbox5.setAlignment(Pos.TOP_CENTER);
			vbox5.getStyleClass().add("scenebox");
			
			Scene finishedTaskScene = new Scene(vbox5,1280,650);
			String cssPath = getClass().getResource("/Styles/styles.css").toExternalForm();
			finishedTaskScene.getStylesheets().add(cssPath);
			
			primaryStage.setScene(finishedTaskScene);
			primaryStage.setTitle("Show Completed Tasks");
			primaryStage.show();
			primaryStage.centerOnScreen();
		});
		
		Scene scene = new Scene(vbox,1280,650);
		String cssPath = getClass().getResource("/Styles/styles.css").toExternalForm();
		scene.getStylesheets().add(cssPath);
		
		primaryStage.setScene(scene);
		primaryStage.setTitle("Task Scheduler");
		primaryStage.show();
		primaryStage.centerOnScreen();
	}
	
	public void load(String userName,String titleText,String deadline,String priorityNo,VBox vbox3,GetImage icon,DataBaseHandler dbHandler)
	{ // this load method is used to show all the tasks what are added in todo list.
		Label taskLabel = new Label(titleText);
		Label desLabel = new Label(deadline);
		
		taskLabel.setPadding(new Insets(6, 0, 0, 20));
		desLabel.setPadding(new Insets(6, 0, 0, 20));
		
		taskLabel.getStyleClass().add("task");
		desLabel.getStyleClass().add("desc");
		
		VBox vbox5 = new VBox(2, taskLabel, desLabel);
		// it helps to place the buttons(trash & correct tic) placed at right side of that 
		//box and the sentence can increase their contents by downwards also. 
		HBox.setHgrow(vbox5, Priority.ALWAYS); 

		ImageView whtTrash = icon.getIcon("/Icons/whiteTrash.png");
		Button whtBtn = new Button();
		whtBtn.setGraphic(whtTrash);

		ImageView mark = icon.getIcon("/Icons/mark.png");
		Button markBtn = new Button();
		markBtn.setGraphic(mark);
		
		ImageView redTrash = icon.getIcon("/Icons/redTrash.png");
		Button redBtn = new Button();
		redBtn.setGraphic(redTrash);
		redBtn.setVisible(false);
		
		whtBtn.setStyle("-fx-background-color: transparent; -fx-border-color: transparent;"); // these two helps to vanish the button predefined box.
		markBtn.setStyle("-fx-background-color: transparent; -fx-border-color: transparent;");
		redBtn.setStyle("-fx-background-color: transparent; -fx-border-color: transparent;");
		
		StackPane trashPane = new StackPane();
		trashPane.getChildren().addAll(whtBtn,redBtn);
		
		whtBtn.setOnMouseEntered(e1 -> {
			whtBtn.setVisible(false);
			redBtn.setVisible(true);
		});
		redBtn.setOnMouseExited(e2 ->{
			redBtn.setVisible(false);
			whtBtn.setVisible(true);
		});
		
		HBox hbox4 = new HBox(1,trashPane,markBtn);
		hbox4.setAlignment(Pos.CENTER_RIGHT);

		HBox hbox5 = new HBox(10, vbox5, hbox4);
		hbox5.setPrefSize(680,85);
		hbox5.setMaxSize(680,85);
		hbox5.setStyle("-fx-background-color: #383838");
		hbox5.setTranslateX(40);

		vbox3.getChildren().add(hbox5);
		
		redBtn.setOnAction(e3->{
			dbHandler.removeTasks(userName,titleText);
			vbox3.getChildren().remove(hbox5);
		});
		
		markBtn.setOnAction(e->{
			dbHandler.markAsCompleted(userName,titleText);
			vbox3.getChildren().remove(hbox5);
		});
	}
	
	public VBox showCompletedTasks(String title , String desc)
	{
		Label tleLabel = new Label(title);
		Label desLabel = new Label(desc);
		
		tleLabel.setPadding(new Insets(3,0,0,15));
		desLabel.setPadding(new Insets(3,0,0,15));
		tleLabel.setStyle("-fx-text-fill : #FA7098;"+"-fx-font-size: 16px;"+"-fx-font-weight:bold;");
		desLabel.setStyle("-fx-text-fill : #D9D9D9;"+"-fx-font-size: 13px;" + "-fx-font-weight:normal");
		
		VBox vbox = new VBox(3,tleLabel,desLabel);
		HBox.setHgrow(vbox, Priority.ALWAYS);  //box and the sentence can increase their contents by downwards also. 
		return vbox;
	}
}
