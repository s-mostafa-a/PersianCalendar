/**
 * Created by ahmadi on 5/9/16.
 */
import javax.swing.*;
import java.awt.*;
import java.util.Date;
public class Calender {
    public static void main(String[] args) throws AWTException {
        Frame n = new Frame(5, 2, 1395);
        Date date = new Date();
        Mobaddel tabdil = new Mobaddel();
        String year = String.format("%tY",date);
        String month = String.format("%tm",date);
        String day = String.format("%td",date);
        tabdil.GregorianToPersian(Integer.parseInt(year), Integer.parseInt(month), Integer.parseInt(day));
        n.TodayIs(tabdil.getDay(), tabdil.getMonth(), tabdil.getYear());
        n.setIconImage(new ImageIcon("image.jpg").getImage());
    }
}
