package dev.jasonhk.minecraft.time;

final class RealTime
{
    /**
     * Hours per day.
     */
    static final int HOURS_PER_DAY = 24;

    /**
     * Minutes per hour.
     */
    static final int MINUTES_PER_HOUR = 60;

    /**
     * Seconds per minute.
     */
    static final int SECONDS_PER_MINUTE = 60;

    /**
     * Milliseconds per second.
     */
    static final int MILLIS_PER_SECOND = 1_000;

    /**
     * Microseconds per second.
     */
    static final int MICROS_PER_SECOND = 1_000_000;

    /**
     * Nanoseconds per second.
     */
    static final long NANOS_PER_SECOND = 1_000_000_000;

    /**
     * Minutes per day.
     */
    static final int MINUTES_PER_DAY = MINUTES_PER_HOUR * HOURS_PER_DAY;

    /**
     * Seconds per hour.
     */
    static final int SECONDS_PER_HOUR = SECONDS_PER_MINUTE * MINUTES_PER_HOUR;

    /**
     * Seconds per day.
     */
    static final int SECONDS_PER_DAY = SECONDS_PER_HOUR * HOURS_PER_DAY;

    static final int NANOS_PER_MICRO = (int) (NANOS_PER_SECOND / MICROS_PER_SECOND);

    static final int NANOS_PER_MILLI = (int) (NANOS_PER_SECOND / MILLIS_PER_SECOND);

    static final long NANOS_PER_MINUTE = NANOS_PER_SECOND * SECONDS_PER_MINUTE;

    static final long NANOS_PER_HOUR = NANOS_PER_MINUTE * MINUTES_PER_HOUR;

    static final long NANOS_PER_DAY = NANOS_PER_HOUR * HOURS_PER_DAY;

    private RealTime()
    {
        throw new UnsupportedOperationException(
                "This is a utility class and cannot be instantiated.");
    }
}
