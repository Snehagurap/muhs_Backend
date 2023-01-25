package in.cdac.university.globalService.util;

import com.github.sisyphsu.dateparser.DateParserUtils;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class DateUtility {

    public static String dateAsStringToPrintableFormat(String date) {
        if (date == null || date.isBlank())
            return "";
        Calendar cal = DateParserUtils.parseCalendar(date);
        SimpleDateFormat sdf = new SimpleDateFormat(Constants.dateFormat);
        return sdf.format(cal.getTime());
    }
}
