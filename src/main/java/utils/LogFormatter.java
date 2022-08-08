package utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Formatter;
import java.util.logging.LogRecord;

public class LogFormatter extends Formatter {
    @Override
    public String format(LogRecord record) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date now = new Date();
        String dateStr = sdf.format(now);

        return dateStr + " [" + record.getLevel() + "] " +
               record.getSourceMethodName() + ": " +
               record.getMessage() + "\r\n";
    }
}
