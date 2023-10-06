
import java.time.LocalDateTime;  // Import the LocalDateTime class
import java.time.format.DateTimeFormatter;  // Import the DateTimeFormatter class

public class Date {

    private static Date dateInstance;
    private final String date;

    private Date(){
        date = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd"));
    }

    public static Date getDateInstance() {
        if (dateInstance == null){
            dateInstance = new Date();
        }
        return dateInstance;
    }

    public String getDate() {
        return date;
    }
}
