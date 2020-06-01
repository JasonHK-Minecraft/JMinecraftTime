package dev.jasonhk.minecraft.time;

import java.time.Clock;
import java.time.ZoneId;
import java.time.temporal.ChronoField;
import java.time.temporal.ChronoUnit;
import java.time.temporal.Temporal;
import java.time.temporal.TemporalAdjuster;
import java.time.temporal.TemporalAmount;
import java.time.temporal.TemporalField;
import java.time.temporal.TemporalUnit;
import java.time.temporal.UnsupportedTemporalTypeException;
import java.util.HashMap;
import static java.time.temporal.ChronoField.HOUR_OF_DAY;
import static java.time.temporal.ChronoField.MINUTE_OF_HOUR;
import static java.time.temporal.ChronoField.NANO_OF_DAY;
import static java.time.temporal.ChronoField.NANO_OF_SECOND;
import static java.time.temporal.ChronoField.SECOND_OF_DAY;
import static java.time.temporal.ChronoField.SECOND_OF_MINUTE;

import lombok.NonNull;
import lombok.val;
import lombok.var;

import dev.jasonhk.minecraft.time.temporal.MinecraftField;
import dev.jasonhk.minecraft.time.temporal.MinecraftUnit;
import static dev.jasonhk.minecraft.time.RealTime.HOURS_PER_DAY;
import static dev.jasonhk.minecraft.time.RealTime.MINUTES_PER_DAY;
import static dev.jasonhk.minecraft.time.RealTime.MINUTES_PER_HOUR;
import static dev.jasonhk.minecraft.time.RealTime.NANOS_PER_DAY;
import static dev.jasonhk.minecraft.time.RealTime.NANOS_PER_HOUR;
import static dev.jasonhk.minecraft.time.RealTime.NANOS_PER_MINUTE;
import static dev.jasonhk.minecraft.time.RealTime.NANOS_PER_SECOND;
import static dev.jasonhk.minecraft.time.RealTime.SECONDS_PER_DAY;
import static dev.jasonhk.minecraft.time.RealTime.SECONDS_PER_HOUR;
import static dev.jasonhk.minecraft.time.RealTime.SECONDS_PER_MINUTE;
import static dev.jasonhk.minecraft.time.temporal.MinecraftField.TICK_OF_DAY;

@SuppressWarnings("unused")
public final class MinecraftTime implements Temporal, TemporalAdjuster
{
    //<editor-fold desc="Static Fields">
    //<editor-fold desc="Time Constants">
    /**
     * The minimum supported {@code MinecraftTime}, {@code 0} tick. This equals to 06:00, the start
     * of a Minecraft day.
     */
    public static final MinecraftTime MIN;

    /**
     * The time set when using the {@code /time set day} command in-game, {@code 1000} tick. This
     * equals to 07:00 of a Minecraft day.
     */
    public static final MinecraftTime DAY;

    /**
     * The time set when using the {@code /time set noon} command in-game, {@code 6000} tick. This
     * equals to 12:00 of a Minecraft day.
     */
    public static final MinecraftTime NOON;

    /**
     * The time set when using the {@code /time set sunset} command in-game, {@code 12000} tick.
     * This equals to 18:00 of a Minecraft day.
     */
    public static final MinecraftTime SUNSET;

    /**
     * The time set when using the {@code /time set night} command in-game, {@code 13000} tick. This
     * equals to 19:00 of a Minecraft day.
     */
    public static final MinecraftTime NIGHT;

    /**
     * The time set when using the {@code /time set midnight} command in-game, {@code 18000} tick.
     * This equals to 00:00 of a Minecraft day.
     */
    public static final MinecraftTime MIDNIGHT;

    /**
     * The time set when using the {@code /time set sunrise} command in-game, {@code 23000} tick.
     * This equals to 05:00 of a Minecraft day.
     */
    public static final MinecraftTime SUNRISE;

    /**
     * The maximum supported {@code MinecraftTime}, {@code 23999} tick. This equals to 05:59:56.4,
     * the end of a Minecraft day.
     */
    public static final MinecraftTime MAX;
    //</editor-fold>

    /**
     * The hour offset value of Minecraft's time system.
     */
    static final int HOUR_OFFSET = 6;

    /**
     * Seconds per tick.
     */
    static final double SECONDS_PER_TICK
            = (double) RealTime.MINUTES_PER_DAY / GameTime.MINUTES_PER_DAY /
              GameTime.TICKS_PER_SECOND;

    /**
     * Nanoseconds per tick.
     */
    static final long NANOS_PER_TICK = (long) (SECONDS_PER_TICK * NANOS_PER_SECOND);

    static final double TICKS_PER_NANO = SECONDS_PER_TICK / NANOS_PER_SECOND;

    /**
     * Ticks per second.
     */
    static final double TICKS_PER_SECOND = 1 / SECONDS_PER_TICK;

    /**
     * Ticks per minute.
     */
    static final double TICKS_PER_MINUTE = TICKS_PER_SECOND * SECONDS_PER_MINUTE;

    /**
     * Ticks per hour.
     */
    static final int TICKS_PER_HOUR = (int) (TICKS_PER_MINUTE * MINUTES_PER_HOUR);

    /**
     * Ticks per day.
     */
    static final int TICKS_PER_DAY = TICKS_PER_HOUR * HOURS_PER_DAY;

    static final long NANOS_PER_TICKS_PER_SECOND = (long) (TICKS_PER_SECOND * NANOS_PER_SECOND);

    private static final long NANOS_OF_HOUR_OFFSET = HOUR_OFFSET * NANOS_PER_HOUR;

    private static final HashMap<Integer, MinecraftTime> HOURS = new HashMap<>(HOURS_PER_DAY);
    //</editor-fold>

    static
    {
        for (int i = 0; i < HOURS_PER_DAY; i++)
        {
            val tickOfDay = TICKS_PER_HOUR * i;
            HOURS.put(tickOfDay, new MinecraftTime(tickOfDay));
        }

        MIN = HOURS.get(0);
        MAX = new MinecraftTime(23999);

        DAY = HOURS.get(1000);
        NOON = HOURS.get(6000);
        SUNSET = HOURS.get(12000);
        NIGHT = HOURS.get(13000);
        MIDNIGHT = HOURS.get(18000);
        SUNRISE = HOURS.get(23000);
    }

    /**
     * The tick-of-day, from {@code 0} to {@code 23999}.
     */
    private final short tickOfDay;

    private MinecraftTime(final int tickOfDay)
    {
        this.tickOfDay = (short) tickOfDay;
    }

    //<editor-fold desc="Static Methods">
    //<editor-fold desc="TIME CREATORS">

    /**
     * Obtains an instance of {@code MinecraftTime} from the current time of the system clock in the
     * default time-zone.
     * <p>
     * This will query the {@linkplain Clock#systemDefaultZone() system clock} in the default
     * time-zone to obtain the current time.
     * <p>
     * Using this method will prevent the ability to use an alternate clock for testing because the
     * clock is hard-coded.
     *
     * @return The current Minecraft time using the system clock and default time-zone, not {@code null}.
     */
    @NonNull
    public static MinecraftTime now()
    {
        return now(Clock.systemDefaultZone());
    }

    /**
     * Obtains an instance of {@code MinecraftTime} from the current time of the system clock in the
     * specified time-zone.
     * <p>
     * This will query the {@linkplain Clock#system(ZoneId) system clock} to obtain the current
     * time. Specifying the time-zone avoids dependence on the default time-zone.
     * <p>
     * Using this method will prevent the ability to use an alternate clock for testing because the
     * clock is hard-coded.
     *
     * @param zone The zone ID to use, not {@code null}.
     * @return The current Minecraft time using the system clock, not {@code null}.
     */
    @NonNull
    public static MinecraftTime now(@NonNull final ZoneId zone)
    {
        return now(Clock.system(zone));
    }

    /**
     * Obtains an instance of {@code MinecraftTime} from the current time of the specified clock.
     * <p>
     * This will query the specified clock to obtain the current time. Using this method allows the
     * use of an alternate clock for testing. The alternate clock may be introduced using
     * {@linkplain Clock dependency injection}.
     *
     * @param clock The clock to use, not {@code null}.
     * @return The current Minecraft time, not {@code null}.
     */
    @NonNull
    public static MinecraftTime now(@NonNull final Clock clock)
    {
        val instant     = clock.instant();
        val zoneOffset  = clock.getZone().getRules().getOffset(instant);
        val localSecond = instant.getEpochSecond() + zoneOffset.getTotalSeconds();
        val secondOfDay = Math.floorMod(localSecond, SECONDS_PER_DAY);

        return ofSecondOfDay(secondOfDay);
    }

    /**
     * Obtains an instance of {@code MinecraftTime} from an hour and minute.
     * <p>
     * This returns a {@code MinecraftTime} with the tick-of-day converted from the specified hour
     * and minute.
     *
     * @param hour   The hour-of-day to represent, from {@code 0} to {@code 23}.
     * @param minute The minute-of-day to represent, from {@code 0} to {@code 59}.
     * @return The Minecraft time, not {@code null}.
     */
    @NonNull
    public static MinecraftTime of(final int hour, final int minute)
    {
        return of(hour, minute, 0, 0);
    }

    /**
     * Obtains an instance of {@code MinecraftTime} from an hour, minute and second.
     * <p>
     * This returns a {@code MinecraftTime} with the tick-of-day converted from the specified hour,
     * minute and second.
     *
     * @param hour   The hour-of-day to represent, from {@code 0} to {@code 23}.
     * @param minute The minute-of-day to represent, from {@code 0} to {@code 59}.
     * @param second The second-of-day to represent, from {@code 0} to {@code 59}.
     * @return The Minecraft time, not {@code null}.
     */
    @NonNull
    public static MinecraftTime of(final int hour, final int minute, final int second)
    {
        return of(hour, minute, second, 0);
    }

    @NonNull
    public static MinecraftTime of(
            final int hour,
            final int minute,
            final int second,
            final int nano)
    {
        HOUR_OF_DAY.checkValidValue(hour);
        MINUTE_OF_HOUR.checkValidValue(minute);
        SECOND_OF_MINUTE.checkValidValue(second);
        NANO_OF_SECOND.checkValidValue(nano);

        return create(hour, minute, second, nano);
    }

    /**
     * Obtains an instance of {@code MinecraftTime} from a tick-of-day value.
     * <p>
     * This returns a {@code MinecraftTime} with the specified tick-of-day.
     *
     * @param tickOfDay The tick-of-day, from {@code 0} to {@code 23999}.
     * @return The Minecraft time, not {@code null}.
     */
    @NonNull
    public static MinecraftTime ofTickOfDay(final long tickOfDay)
    {
        TICK_OF_DAY.checkValidValue(tickOfDay);
        return create((int) tickOfDay);
    }

    /**
     * Obtains an instance of {@code MinecraftTime} from a second-of-day value.
     * <p>
     * This returns a {@code MinecraftTime} with the tick-of-day converted from the specified
     * second-of-day.
     *
     * @param secondOfDay The second-of-day, from {@code 0} to {@code 24 * 60 * 60 - 1}.
     * @return The Minecraft time, not {@code null}.
     */
    @NonNull
    public static MinecraftTime ofSecondOfDay(final long secondOfDay)
    {
        SECOND_OF_DAY.checkValidValue(secondOfDay);
        return ofNanoOfDay(secondOfDay * NANOS_PER_SECOND);
    }

    @NonNull
    public static MinecraftTime ofNanoOfDay(final long nanoOfDay)
    {
        NANO_OF_DAY.checkValidValue(nanoOfDay);
        return create((int) (Math.floorMod(nanoOfDay - NANOS_OF_HOUR_OFFSET, NANOS_PER_DAY) /
                             NANOS_PER_TICK));
    }

    @NonNull
    private static MinecraftTime create(final int tickOfDay)
    {
        return HOURS.containsKey(tickOfDay)
               ? HOURS.get(tickOfDay)
               : new MinecraftTime(tickOfDay);
    }

    @NonNull
    private static MinecraftTime create(
            final int hour,
            final int minute,
            final int second,
            final int nano)
    {
        var nanoOfDay = hour * NANOS_PER_HOUR;
        nanoOfDay += minute * NANOS_PER_MINUTE;
        nanoOfDay += second * NANOS_PER_SECOND;
        nanoOfDay += nano;

        return ofNanoOfDay(nanoOfDay);
    }
    //</editor-fold>
    //</editor-fold>

    //<editor-fold desc="Instance Methods">
    @Override
    public long until(final Temporal endExclusive, final TemporalUnit unit)
    {
        return 0;
    }

    //<editor-fold desc="Time Adjuster">
    @Override
    public Temporal adjustInto(final Temporal temporal)
    {
        return temporal.isSupported(TICK_OF_DAY)
               ? temporal.with(TICK_OF_DAY, toTickOfDay())
               : temporal.with(SECOND_OF_DAY, toSecondOfDay());
    }
    //</editor-fold>

    //<editor-fold desc="Time Accessors">
    @Override
    public boolean isSupported(final TemporalField field)
    {
        return false;
    }

    /**
     * Gets the value of the specified field from this time as an {@code int}.
     *
     * @param field The field to get, not {@code null}.
     * @return The value for the field.
     */
    @Override
    public int get(final TemporalField field)
    {
        if ((field instanceof ChronoField) || (field instanceof MinecraftField))
        {
            return getInt(field);
        }

        return Temporal.super.get(field);
    }

    /**
     * Gets the value of the specified field from this time as an {@code long}.
     *
     * @param field The field to get, not {@code null}.
     * @return The value for the field.
     */
    @Override
    public long getLong(final TemporalField field)
    {
        if ((field instanceof ChronoField) || (field instanceof MinecraftField))
        {
            return getInt(field);
        }

        return field.getFrom(this);
    }

    private int getInt(final TemporalField field)
    {
        if (field instanceof ChronoField)
        {
            switch ((ChronoField) field)
            {
                case SECOND_OF_MINUTE:
                    return getSecond();
                case SECOND_OF_DAY:
                    return toSecondOfDay();
                case MINUTE_OF_HOUR:
                    return getMinute();
                case MINUTE_OF_DAY:
                    return ((getHour() * MINUTES_PER_HOUR) + getMinute());
                case HOUR_OF_AMPM:
                    return (getHour() % 12);
                case HOUR_OF_DAY:
                    return getHour();
                case CLOCK_HOUR_OF_AMPM:
                {
                    val hour = getHour() % 12;
                    return (hour == 0) ? 12 : hour;
                }
                case CLOCK_HOUR_OF_DAY:
                {
                    val hour = getHour();
                    return (hour == 0) ? 24 : hour;
                }
                case AMPM_OF_DAY:
                    return (getHour() / 12);
            }
        }
        else if (field instanceof MinecraftField)
        {
            if (field == TICK_OF_DAY) { return toTickOfDay(); }
        }

        throw new UnsupportedTemporalTypeException("Unsupported field: " + field);
    }

    /**
     * Gets the hour-of-day field.
     *
     * @return The hour-of-day, from {@code 0} to {@code 23}.
     */
    public int getHour()
    {
        return (int) (toNanoOfDay() / NANOS_PER_HOUR);
    }

    /**
     * Gets the minute-of-hour field.
     *
     * @return The minute-of-hour, from {@code 0} to {@code 59}.
     */
    public int getMinute()
    {
        return (int) ((toNanoOfDay() % NANOS_PER_HOUR) / NANOS_PER_MINUTE);
    }

    /**
     * Gets the second-of-minute field.
     *
     * @return The second-of-minute, from {@code 0} to {@code 59}.
     */
    public int getSecond()
    {
        return (int) ((toNanoOfDay() % NANOS_PER_MINUTE) / NANOS_PER_SECOND);
    }

    public int getNano()
    {
        return (int) (toNanoOfDay() % NANOS_PER_SECOND);
    }

    /**
     * Extracts the time as ticks of day, from {@code 0} to {@code 23999}.
     *
     * @return The tick-of-day equivalent to this time.
     */
    public int toTickOfDay()
    {
        return tickOfDay;
    }

    /**
     * Extracts the time as seconds of day, from {@code 0} to {@code 24 * 60 * 60 - 1}.
     *
     * @return The second-of-day equivalent to this time.
     */
    public int toSecondOfDay()
    {
        return (int) (toNanoOfDay() / NANOS_PER_SECOND);
    }

    public long toNanoOfDay()
    {
        return Math.floorMod((tickOfDay * NANOS_PER_TICK) + NANOS_OF_HOUR_OFFSET, NANOS_PER_DAY);
    }
    //</editor-fold>

    //<editor-fold desc="Time Manipulators">
    @Override
    public boolean isSupported(final TemporalUnit unit)
    {
        return false;
    }

    /**
     * Returns an adjusted copy of this time.
     * <p>
     * This returns a {@code MinecraftTime}, based on this one, with the time adjusted. The
     * adjustment takes place using the specified adjuster strategy object. Read the documentation
     * of the adjuster to understand what adjustment will be made.
     *
     * @param adjuster The adjuster to use, not {@code null}.
     * @return A {@code MinecraftTime} based on {@code this} with the adjustment made, not {@code null}.
     */
    @Override
    public MinecraftTime with(@NonNull final TemporalAdjuster adjuster)
    {
        return (adjuster instanceof MinecraftTime)
               ? (MinecraftTime) adjuster
               : (MinecraftTime) adjuster.adjustInto(this);
    }

    @Override
    public MinecraftTime with(final TemporalField field, final long newValue)
    {
        if (field instanceof ChronoField)
        {
            val _field = (ChronoField) field;
            _field.checkValidValue(newValue);

            switch (_field)
            {
                case SECOND_OF_MINUTE:
                    return withSecond((int) newValue);
                case SECOND_OF_DAY:
            }
        }

        return null;
    }

    public MinecraftTime withHour(final int hour)
    {
        if (hour == getHour()) { return this; }

        HOUR_OF_DAY.checkValidValue(hour);
        return create(hour, getMinute(), getSecond(), 0);
    }

    public MinecraftTime withMinute(final int minute)
    {
        if (minute == getMinute()) { return this; }

        MINUTE_OF_HOUR.checkValidValue(minute);
        return create(getHour(), minute, getSecond(), 0);
    }

    public MinecraftTime withSecond(final int second)
    {
        if (second == getSecond()) { return this; }

        SECOND_OF_MINUTE.checkValidValue(second);
        return create(getHour(), getMinute(), second, 0);
    }

    @Override
    public MinecraftTime plus(final TemporalAmount amount)
    {
        return (MinecraftTime) amount.addTo(this);
    }

    @Override
    public MinecraftTime plus(final long amountToAdd, final TemporalUnit unit)
    {
        if (unit instanceof ChronoUnit)
        {
            switch ((ChronoUnit) unit)
            {
                case SECONDS:
                    return plusSeconds(amountToAdd);
                case MINUTES:
                    return plusMinutes(amountToAdd);
                case HOURS:
                    return plusHours(amountToAdd);
                case HALF_DAYS:
                    return plusHours(amountToAdd * 12);
            }
        }
        else if (unit instanceof MinecraftUnit)
        {
            if (unit == MinecraftUnit.TICKS)
            {
                return plusTicks(amountToAdd);
            }

            throw new UnsupportedTemporalTypeException("Unsupported unit: " + unit);
        }

        return unit.addTo(this, amountToAdd);
    }

    public MinecraftTime plusHours(final long hoursToAdd)
    {
        if (hoursToAdd == 0) { return this; }

        val oldHour = getHour();
        val newHour = (int) Math.floorMod(oldHour + hoursToAdd, HOURS_PER_DAY);

        return (newHour == oldHour)
               ? this
               : create(newHour, getMinute(), getSecond(), 0);
    }

    public MinecraftTime plusMinutes(final long minutesToAdd)
    {
        if (minutesToAdd == 0) { return this; }

        val oldMinuteOfDay = (getHour() * MINUTES_PER_HOUR) + getMinute();
        val newMinuteOfDay = Math.floorMod(oldMinuteOfDay + minutesToAdd, MINUTES_PER_DAY);

        if (newMinuteOfDay == oldMinuteOfDay) { return this; }

        val newHour   = (int) (newMinuteOfDay / MINUTES_PER_HOUR);
        val newMinute = (int) (newMinuteOfDay % MINUTES_PER_HOUR);
        return create(newHour, newMinute, getSecond(), 0);
    }

    public MinecraftTime plusTicks(final long ticksToAdd)
    {
        if (ticksToAdd == 0) { return this; }

        val newTickOfDay = Math.floorMod(tickOfDay + ticksToAdd, TICKS_PER_DAY);
        return (newTickOfDay == tickOfDay)
               ? this
               : create((int) newTickOfDay);
    }

    public MinecraftTime plusSeconds(final long secondsToAdd)
    {
        if (secondsToAdd == 0) { return this; }

        val oldSecondOfDay = toSecondOfDay();
        val newSecondOfDay = Math.floorMod(oldSecondOfDay + secondsToAdd, SECONDS_PER_DAY);

        if (newSecondOfDay == oldSecondOfDay) { return this; }

        val newHour   = (int) (newSecondOfDay / SECONDS_PER_HOUR);
        val newMinute = (int) ((newSecondOfDay % SECONDS_PER_HOUR) / SECONDS_PER_MINUTE);
        val newSecond = (int) (newSecondOfDay % SECONDS_PER_MINUTE);
        return create(newHour, newMinute, newSecond, 0);
    }

    @Override
    public MinecraftTime minus(final TemporalAmount amount)
    {
        return (MinecraftTime) amount.subtractFrom(this);
    }

    @Override
    public MinecraftTime minus(final long amountToSubtract, final TemporalUnit unit)
    {
        return (amountToSubtract == Long.MIN_VALUE)
               ? plus(Long.MAX_VALUE, unit).plus(1, unit)
               : plus(-amountToSubtract, unit);
    }
    //</editor-fold>

    @Override
    public boolean equals(final Object that)
    {
        if (that == this) { return true; }

        if (that instanceof MinecraftTime)
        {
            val other = (MinecraftTime) that;
            return (other.tickOfDay == tickOfDay);
        }

        return false;
    }

    @Override
    public int hashCode()
    {
        return tickOfDay;
    }
    //</editor-fold>
}
