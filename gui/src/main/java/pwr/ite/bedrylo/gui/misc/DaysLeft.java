package pwr.ite.bedrylo.gui.misc;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import lombok.Getter;

public class DaysLeft {
    private static final Date endOfEvaluation = new GregorianCalendar(2024, Calendar.MAY, 25).getTime();

    private static final Date today = new Date();
    
    @Getter
    private static final long daysLeft = (endOfEvaluation.getTime() - today.getTime())/86400000;
    
}
