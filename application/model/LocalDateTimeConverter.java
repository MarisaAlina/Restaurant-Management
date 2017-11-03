package uk.ac.ucl.cs.gc01.me.application.model;
/** ==========================DATETIME - TIMESTAMP===================
 * OrderModel: DateTime Object Property (more flexible and has "now" object - gets local datetime for specific zones
 * Converter class needed to display DateTime correctly onto GUI
 * Uses inheritance to implement a new converter from Java library
 * 
 * References:
 * http://stackoverflow.com/questions/22417370/working-with-datetime-as-stringproperty
 * https://docs.oracle.com/javase/8/docs/api/java/time/LocalDate.html
 * https://docs.oracle.com/javase/8/docs/api/java/time/LocalDate.html#now--
 */
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import javafx.util.StringConverter;

public class LocalDateTimeConverter extends StringConverter<LocalDateTime> {
	
	/** The Constant dateFormat. */
	public final static DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
	
	/**
	 * @see javafx.util.StringConverter#toString(java.lang.Object)
	 */
	// Constructor
	@Override
    public String toString(LocalDateTime t) {
        if (t==null) {
            return "" ;
        } else {
            return dateFormat.format(t);
        }
    }

    /**
     * @see javafx.util.StringConverter#fromString(java.lang.String)
     */
    @Override
    public LocalDateTime fromString(String string) {
        try {
            return LocalDateTime.parse(string, dateFormat);
        } catch (DateTimeParseException exc) {
            return null ;
        }
    }
}
