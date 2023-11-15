package christmas.utils;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

public class DateUtil {
    public static LocalDate parseDate(int date) {
        return LocalDate.of(2023, 12, date);
    }

    //주말 여부를 판단하는 메서드
    public static boolean isWeekend(int visitDate) {
        LocalDate date = parseDate(visitDate);
        DayOfWeek dayOfWeek = date.getDayOfWeek();
        return dayOfWeek == DayOfWeek.SATURDAY || dayOfWeek == DayOfWeek.SUNDAY;
    }

    //평일 여부를 판단하는 메서드
    public static boolean isWeekday(int visitDate) {
        LocalDate date = parseDate(visitDate);
        DayOfWeek dayOfWeek = date.getDayOfWeek();
        return dayOfWeek != DayOfWeek.SATURDAY && dayOfWeek != DayOfWeek.SUNDAY;
    }

    //특별 할인 여부를 판단하는 메서드
    public static boolean isSpecialDiscount(int visitDate) {
        return visitDate == 3 || visitDate == 10 || visitDate == 17 || visitDate == 24 || visitDate == 25
                || visitDate == 31;
    }

}
