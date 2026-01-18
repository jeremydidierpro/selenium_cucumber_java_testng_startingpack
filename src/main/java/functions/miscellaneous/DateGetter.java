package functions.miscellaneous;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.Date;

public class DateGetter {

    private DateGetter(){
    }


    public static String getStartDay(int daysBeforeStarts){
        SimpleDateFormat formatter = new SimpleDateFormat("d");
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE,daysBeforeStarts);
        Date date = cal.getTime();
        return formatter.format(date);
    }

    public static int getDurationFromMonthsToDays(int nOfMonths){
        LocalDate startDate = LocalDate.now();
        LocalDate endDate = LocalDate.now().plusMonths(nOfMonths);
        return (int) ChronoUnit.DAYS.between(startDate, endDate);
    }

    public static LocalDate toLocaleDate(String date){
        return LocalDateTime.parse(date).toLocalDate();
    }

    public static String getDate(String yearInString, String monthInString) {
        int year = Integer.parseInt(yearInString);
        int month = Integer.parseInt(monthInString) - 1;//In calendar january = 0 take off 1 to month number
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.MONTH, month);
        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.DAY_OF_MONTH, 1);// This is necessary to get proper results
        cal.set(Calendar.DATE, cal.getActualMaximum(Calendar.DATE));
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        return format.format(cal.getTime());
    }
    
}

