package uk.ac.ucl.cs.gc01.me.application.model;

import java.time.LocalDateTime;
import javax.xml.bind.annotation.adapters.XmlAdapter;
/**
 * ==========================DATETIME - TIMESTAMP===================
 * OrderModel: DateTime Object Property (more flexible and has "now" object - gets local datetime for specific zones
 * JAXB needs Adapter to import/ export DateTime to XML files
 * 
 * Reference: 
 * http://code.makery.ch/library/javafx-8-tutorial/part5/
 * http://stackoverflow.com/questions/22417370/working-with-datetime-as-stringproperty
 * https://docs.oracle.com/javase/8/docs/api/java/time/LocalDate.html
 * https://docs.oracle.com/javase/8/docs/api/java/time/LocalDate.html#now--
 */
public class LocalDateTimeAdapter extends XmlAdapter<String, LocalDateTime> {
	@Override
    public LocalDateTime unmarshal(String v) throws Exception {
        return LocalDateTime.parse(v);
    }

    @Override
    public String marshal(LocalDateTime v) throws Exception {
        return v.toString();
    }
}
