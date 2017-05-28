import java.util.Calendar;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;
import javax.swing.JOptionPane;


public class JTimerTask {

    Timer timer;
    String title;
    int hour;
    int minute;
    int day;
    int month;
    public JTimerTask(int hour, int minute,String title) {
        this.title = title;
        this.hour = hour;
        this.minute = minute;

        timer = new Timer();
        Calendar calendar = Calendar.getInstance();
        long now = calendar.getTimeInMillis();
////
        calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH), hour, minute, 0);
        long then = calendar.getTimeInMillis();
        if (then - now > 0) {
            timer.schedule(new myReminder(), then - now);
        }
        else{
//            JOptionPane.showMessageDialog(null, "Am I kidding?", "Who dares play with me?", JOptionPane.ERROR_MESSAGE);
        }
    }

    class myReminder extends TimerTask {

        public void run() {
            System.out.format("Timer Task Finished..!%n");
            JOptionPane.showMessageDialog(null, title, "Hey Come on !", JOptionPane.WARNING_MESSAGE);
            timer.cancel(); // Terminate the timer thread
        }
    }

}