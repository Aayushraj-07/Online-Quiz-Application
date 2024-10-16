package application.Student;

import javafx.animation.AnimationTimer;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.util.Duration;


public class CountDown {
	
    private static final int SECONDS_PER_DAY    = 86_400;
    private static final int SECONDS_PER_HOUR   = 3600;
    private static final int SECONDS_PER_MINUTE = 60;
   
    private Label hours;
    private Label minutes;
    private Label seconds;
    private Duration duration;
    private long lastTimerCall;
    private AnimationTimer timer;
    private int second;

    Label one1,two2;
    HBox pane; 
    
    //constructor 
    public CountDown(int second) {
    	this.second = second;
    }
    

    public HBox setCountdown() {
   	
      
    
        hours = new Label("0");
        hours.setMaxHeight(50);
        hours.setStyle("-fx-font-size: 25px; -fx-text-fill: #606060;");
    		  
        minutes = new Label("0");
        minutes.setMaxHeight(50);
        minutes.setStyle("-fx-font-size: 25px; -fx-text-fill: #606060;");
        
        seconds= new Label("0");
        seconds.setMaxHeight(50);
        seconds.setStyle("-fx-font-size: 25px; -fx-text-fill: #606060;");
        
        one1 = new Label(":");
        one1.setMaxHeight(50);
        one1.setStyle("-fx-font-size: 25px; -fx-text-fill: #606060;");
        
        two2 = new Label(":");
        two2.setMaxHeight(50);
        two2.setStyle("-fx-font-size: 25px; -fx-text-fill: #606060;");
        
        duration = Duration.seconds(second);

        lastTimerCall = System.nanoTime();
        timer = new AnimationTimer() {
            @Override public void handle(final long now) {
                if (now > lastTimerCall + 1_000_000_000l) {
                    duration = duration.subtract(Duration.seconds(1));

                    int remainingSeconds = (int) duration.toSeconds();
                  
                    int h = (remainingSeconds % SECONDS_PER_DAY) / SECONDS_PER_HOUR;
                    int m = ((remainingSeconds % SECONDS_PER_DAY) % SECONDS_PER_HOUR) / SECONDS_PER_MINUTE;
                    int s = (((remainingSeconds % SECONDS_PER_DAY) % SECONDS_PER_HOUR) % SECONDS_PER_MINUTE);

                    if (h == 0 && m == 0 && s == 0) { 
                    	timer.stop();
                    	QuizViewController.getInstance().submit();
                    	//System.exit(0);
                    }

                    hours.setText(String.format("%02d", h));
                    minutes.setText(String.format("%02d", m));
                    seconds.setText(String.format("%02d", s));
                    lastTimerCall = now;
                }
            }
        };
        

        pane = new HBox(hours,one1,minutes,two2,seconds);
        pane.setPadding(new Insets(10));
    
        timer.start();
         
        return pane;
       
    }

   
}
    
