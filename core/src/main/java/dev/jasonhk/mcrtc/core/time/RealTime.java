package dev.jasonhk.mcrtc.core.time;

import lombok.experimental.UtilityClass;

@UtilityClass
class RealTime
{
    /**
     * Hours per day.
     */
    final int HOURS_PER_DAY = 24;

    /**
     * Minutes per hour.
     */
    final int MINUTES_PER_HOUR = 60;

    /**
     * Seconds per minute.
     */
    final int SECONDS_PER_MINUTE = 60;

    /**
     * Nanoseconds per second.
     */
    final long NANOS_PER_SECOND = 1_000_000_000;

    /**
     * Minutes per day.
     */
    final int MINUTES_PER_DAY = MINUTES_PER_HOUR * HOURS_PER_DAY;

    /**
     * Seconds per hour.
     */
    final int SECONDS_PER_HOUR = SECONDS_PER_MINUTE * MINUTES_PER_HOUR;

    /**
     * Seconds per day.
     */
    final int SECONDS_PER_DAY = SECONDS_PER_HOUR * HOURS_PER_DAY;
}
