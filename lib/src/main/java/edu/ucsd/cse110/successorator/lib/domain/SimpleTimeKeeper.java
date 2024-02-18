package edu.ucsd.cse110.successorator.lib.domain;

import java.time.LocalDateTime;

import edu.ucsd.cse110.successorator.lib.util.MutableSubject;
import edu.ucsd.cse110.successorator.lib.util.Subject;

public class SimpleTimeKeeper implements TimeKeeper {
    private MutableSubject<LocalDateTime> dateTime;

    public Subject<LocalDateTime> getDateTime() { return dateTime; }

    public void setDateTime(LocalDateTime dateTime) { this.dateTime.setValue(dateTime); }
}
