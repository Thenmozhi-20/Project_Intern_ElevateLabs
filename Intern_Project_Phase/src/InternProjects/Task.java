package InternProjects;

import java.util.Timer;
import java.util.TimerTask;
import java.time.Duration;
import java.time.LocalDateTime;

import javafx.application.Platform;
import javafx.scene.control.Alert;


public class Task 
{
	String title;
	LocalDateTime deadLine;
	int priority ;
	
	
	public Task(String title,LocalDateTime deadLine,int priority)
	{
		this.title = title;
		this.deadLine = deadLine;
		this.priority=priority;
	}
	
	// Remember 5 minutes before deadline
	public void scheduleReminder()
	{
		Timer timer = new Timer();
		Alert_Show alert = new Alert_Show();
		
		long delay = Duration.between(LocalDateTime.now(), deadLine.minusMinutes(5)).toMillis();
		
		if(delay > 0)
		{
			timer.schedule(new TimerTask() {
				@Override
                public void run() {
                    Platform.runLater(() -> alert.showAlert("Reminder", "Task \"" + title + "\" is due soon!", Alert.AlertType.INFORMATION));
                }
			},delay);
		}
	}
	
	@Override
    public String toString() {
        return "[Title: " + title + ", Priority: " + priority + ", Deadline: " + deadLine + "]";
    }
}
