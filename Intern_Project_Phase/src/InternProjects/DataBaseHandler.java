package InternProjects;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.PriorityQueue;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.scene.control.ScrollPane;


public class DataBaseHandler 
{
	public int isNewUser(String uName,String passWord)
	{
		Alert_Show getAlert = new Alert_Show();
		
		try(Connection con = DBConnection.getConnection())
		{
			String query = "select password from users where userName=?";
			PreparedStatement pst = con.prepareStatement(query);
			pst.setString(1,uName);
			
			ResultSet rst = pst.executeQuery();
			
			if(rst.next())
			{
				String userPassword = rst.getString("password");
				if(passWord.equals(userPassword))
				{
					return 0;  // 0 means old user // goes to home page
				}
				else
				{
					getAlert.showAlert("Wrong Password","Enter Correct Password",Alert.AlertType.ERROR);
					return 1;  // 1 means old user but password wrong  // goes to login page
				}
			}
			else
			{
				getAlert.showAlert("Not Sign In","You have not signed up. Please Sign up first!",Alert.AlertType.ERROR);
				return 2;  // it means new user // goes to sign up page
			}
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
		return 4;
	}
	
	public int getUserId(String uName)
	{
		try(Connection con = DBConnection.getConnection())
		{
			String query = "select userId from users where userName=?";
			PreparedStatement pst = con.prepareStatement(query);
			pst.setString(1,uName);
			
			ResultSet rst = pst.executeQuery();
			
			if(rst.next())  // this is very important even single number provide also
			{
				int id = rst.getInt("userId");
				return id;
			}
			else 
				return -1;
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
		return -1;
	}
	
	public int getTaskId(int userId, String title)
	{
		try(Connection con = DBConnection.getConnection())
		{
			String query = "select taskId from tasks where userId=? and title=?";
			PreparedStatement pst = con.prepareStatement(query);
			pst.setInt(1, userId);
			pst.setString(2,title);
			
			ResultSet rst = pst.executeQuery();
			
			if(rst.next())
			{
				int taskid = rst.getInt("taskId");
				return taskid;
			}
			else
			{
				return -1;
			}
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
		return -1;
	}
	
	public void addTask(String uName,String title,LocalDateTime javaDeadline,int priorityNo)
	{
		try(Connection con = DBConnection.getConnection())
		{
			int userId = getUserId(uName);
			String query = "insert into tasks(userId,title,deadline,status,priority) values (?,?,?,?,?)";
			
			// helps to convert java-vertion time to mysql time
			Timestamp sqlDeadline = Timestamp.valueOf(javaDeadline);
			
			PreparedStatement pst = con.prepareStatement(query);
			pst.setInt(1, userId);
			pst.setString(2,title);
			pst.setTimestamp(3, sqlDeadline);
			pst.setString(4,"Active");
			pst.setInt(5,priorityNo);
			pst.executeUpdate();
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
	}
	
	public void removeTasks(String uName,String titleText)
	{
		try(Connection con = DBConnection.getConnection())
		{
			int userId = getUserId(uName);
			int taskid = getTaskId(userId,titleText);
			
			String query = "update tasks SET Status= 'Deleted' where taskId=?";
			PreparedStatement pst = con.prepareStatement(query);
			pst.setInt(1, taskid);
			
		    pst.executeUpdate();
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
	}
	
	public void markAsCompleted(String userName,String titleText)
	{
		try(Connection con = DBConnection.getConnection())
		{
			int userId = getUserId(userName);
			int taskId = getTaskId(userId,titleText);
			
			String query = "update tasks SET status = 'Completed' where taskId=?";
			PreparedStatement pst = con.prepareStatement(query);
			pst.setInt(1,taskId);
			
			pst.executeUpdate();
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
	}
	
	public HBox loadCompletedTasks(String userName)
	{
		VBox vbox = new VBox(12);
		vbox.setPrefSize(700,400);
		vbox.setMaxSize(700,400);
		vbox.getStyleClass().add("vbox3");
		vbox.setPadding(new Insets(25,0,0,0)); // top left bottom right
		
		HomePage home = new HomePage();
		HBox paneContainer= null;
		
		try(Connection con = DBConnection.getConnection())
		{
			int userId = getUserId(userName);
			
			String query = "select title,deadline from tasks where userId=? and status='completed'";
			PreparedStatement pst = con.prepareStatement(query);
			pst.setInt(1, userId);
			
			ResultSet rst = pst.executeQuery();
			
			while(rst.next())
			{
				String title = rst.getString("title");
				String desc = rst.getString("deadline");
				
				VBox vbox1 = home.showCompletedTasks(title, desc);
				vbox1.setPrefSize(550,52);
				vbox1.setMaxSize(550,52);
				vbox1.setStyle("-fx-background-color: #383838");
				vbox1.setTranslateX(70);
				vbox1.setTranslateY(14);
				
				vbox.getChildren().add(vbox1);
			}
			
			ScrollPane pane = new ScrollPane(vbox);
			pane.setFitToWidth(false);  // it avoids that scroll bar placed at the scene size.
			// the vertical scroll is already in true.
			pane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER); //  disable horizontal scroll
			pane.setStyle("-fx-background-color: transparent;");
			pane.getStyleClass().add("scroll-bar");

			// Wrap in a centered container
			paneContainer= new HBox(pane);
			paneContainer.setAlignment(Pos.CENTER);
			
			return paneContainer;
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
		return paneContainer;
	}
	
	public void addNewUser(String name,String password,String conPassword,String email)
	{
		try(Connection con = DBConnection.getConnection())
		{
			String query = "insert into users(userName,password,confirmPassword,email) values (?,?,?,?);";
			PreparedStatement  pst = con.prepareStatement(query);
			
			pst.setString(1,name);
			pst.setString(2,password);
			pst.setString(3,conPassword);
			pst.setString(4,email);
			
			pst.executeUpdate();
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
	}
	
	public void loadPreviousTasks(String userName,PriorityQueue<Task> pq,VBox vbox3,GetImage icon,DataBaseHandler dbHandler)
	{
		HomePage home = new HomePage();
		
		for (Task task : pq) {
		    String titleText = task.title;
		    LocalDateTime deadLine = task.deadLine;
		    int priority = task.priority;
		    
		    String strDeadLine = String.valueOf(deadLine);
		    String strPriority = Integer.toString(priority);

		    // Example usage:
		    home.load(userName, titleText, strDeadLine, strPriority, vbox3, icon, dbHandler);
		}
	}
	
	public ArrayList<Task> getAllTasks(String userName) 
	{
        ArrayList<Task> list = new ArrayList<>();
        int userId = getUserId(userName);
        HomePage home = new HomePage();
        
        try(Connection con = DBConnection.getConnection())
        {
        	String query = "select title,deadline,priority from tasks where userId=? and Status = 'Active'";
        	
        	PreparedStatement pst = con.prepareStatement(query);
        	pst.setInt(1,userId);
        	
        	ResultSet rst = pst.executeQuery();
        	
        	while(rst.next())
        	{
        		String title = rst.getString("title");
        		// convert mySQL time to java time
        		LocalDateTime javaTime = rst.getObject("deadline", LocalDateTime.class);
        		int priority = rst.getInt("priority");
        		
        		Task task = new Task(title,javaTime,priority);
        		list.add(task);
        	}
        }
        catch(SQLException e)
        {
        	e.printStackTrace();
        }
        
        return list;
    }
}
