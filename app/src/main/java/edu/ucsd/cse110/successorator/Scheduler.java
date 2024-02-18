package edu.ucsd.cse110.successorator;

import android.widget.TextView;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import edu.ucsd.cse110.successorator.lib.domain.Date;

// https://docs.oracle.com/javase/8/docs/api/java/util/concurrent/ScheduledExecutorService.html
public class Scheduler {
    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
    private final TextView dateText;

    public Scheduler(TextView dateText) {
        this.dateText = dateText;
    }

    public void startTask() {
        scheduler.scheduleAtFixedRate(() -> {
            LocalDateTime currentTime = LocalDateTime.now().minusHours(2);
            if (currentTime.getHour() == 0 && currentTime.getMinute() == 0) {
                Date date = new Date(DateTimeFormatter.ofPattern("EEEE M/dd"));
                date.setDate(LocalDateTime.now());
                dateText.setText(date.getDate());
            }
        }, 0, 1, TimeUnit.MINUTES);
    }
}