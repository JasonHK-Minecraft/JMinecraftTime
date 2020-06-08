package dev.jasonhk.minecraft.time.internal;

import org.apiguardian.api.API;

/**
 * A utility class containing constants of time in real life.
 *
 * @apiNote This class is for <strong>internal use only</strong>.
 * @since 0.0.1
 */
@API(status = API.Status.INTERNAL,
     since = "0.0.1",
     consumers = { "dev.jasonhk.minecraft.time.*" })
public final class RealTime
{
    /**
     * Hours per day.
     */
    public static final int HOURS_PER_DAY = 24;

    /**
     * Minutes per hour.
     */
    public static final int MINUTES_PER_HOUR = 60;

    /**
     * Seconds per minute.
     */
    public static final int SECONDS_PER_MINUTE = 60;

    /**
     * Milliseconds per second.
     */
    public static final int MILLIS_PER_SECOND = 1_000;

    /**
     * Microseconds per second.
     */
    public static final int MICROS_PER_SECOND = 1_000_000;

    /**
     * Nanoseconds per second.
     */
    public static final long NANOS_PER_SECOND = 1_000_000_000;

    /**
     * Minutes per day.
     */
    public static final int MINUTES_PER_DAY = MINUTES_PER_HOUR * HOURS_PER_DAY;

    /**
     * Seconds per hour.
     */
    public static final int SECONDS_PER_HOUR = SECONDS_PER_MINUTE * MINUTES_PER_HOUR;

    /**
     * Seconds per day.
     */
    public static final int SECONDS_PER_DAY = SECONDS_PER_HOUR * HOURS_PER_DAY;

    /**
     * Nanoseconds per microsecond.
     */
    public static final int NANOS_PER_MICRO = (int) (NANOS_PER_SECOND / MICROS_PER_SECOND);

    /**
     * Nanoseconds per millisecond.
     */
    public static final int NANOS_PER_MILLI = (int) (NANOS_PER_SECOND / MILLIS_PER_SECOND);

    /**
     * Nanoseconds per minute.
     */
    public static final long NANOS_PER_MINUTE = NANOS_PER_SECOND * SECONDS_PER_MINUTE;

    /**
     * Nanoseconds per hour.
     */
    public static final long NANOS_PER_HOUR = NANOS_PER_MINUTE * MINUTES_PER_HOUR;

    /**
     * Nanoseconds per day.
     */
    public static final long NANOS_PER_DAY = NANOS_PER_HOUR * HOURS_PER_DAY;

    private RealTime()
    {
        throw new UnsupportedOperationException(
                "This is a utility class and cannot be instantiated.");
    }
}
