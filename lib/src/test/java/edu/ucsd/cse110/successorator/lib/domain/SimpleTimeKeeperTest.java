package edu.ucsd.cse110.successorator.lib.domain;

import static org.junit.Assert.*;

import org.junit.Test;

import java.time.LocalDateTime;

import edu.ucsd.cse110.successorator.lib.util.MutableSubject;
import edu.ucsd.cse110.successorator.lib.util.SimpleSubject;

public class SimpleTimeKeeperTest {
    @Test
    public void testGetters() {
        TimeKeeper timeKeeper = new SimpleTimeKeeper();
        MutableSubject<LocalDateTime> expected = new SimpleSubject<>();
        expected.setValue(LocalDateTime.of(2024, 2, 13, 12, 21));
        timeKeeper.setDateTime(LocalDateTime.of(2024, 2, 13, 12, 21));
        assertEquals(expected.getValue(), timeKeeper.getDateTime().getValue());
    }
}
