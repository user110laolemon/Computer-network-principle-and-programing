package HW3.HW3_1;

import java.time.Instant;

public class Watch {
    private Instant startTime;
    private Instant endTime;

    public Watch() {
    }

    public Watch(Instant sT, Instant eT) {
        startTime = sT;
        endTime = eT;
    }

    public void setStartTime(Instant time) {
        startTime = time;
    }

    public void setEndTime(Instant time) {
        endTime = time;
    }

    public Instant getStartTime() {
        return startTime;
    }

    public Instant getEndTime() {
        return endTime;
    }

    public void start() {
        setStartTime(Instant.now());
    }

    public void stop() {
        setEndTime(Instant.now());
    }
}
