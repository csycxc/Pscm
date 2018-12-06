import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TestDate {
    public static void main(String[] args){
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM");
        SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");
        Date start = null;
        try {
            start = sdf1.parse("2018-06");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String startDate = sdf2.format(start);
        System.out.println(start);
        System.out.println(startDate);
    }

}
