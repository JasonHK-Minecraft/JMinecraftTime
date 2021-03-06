package dev.jasonhk.minecraft.time.temporal;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.time.temporal.Temporal;
import java.time.temporal.TemporalUnit;

public enum MinecraftUnit implements TemporalUnit
{
    TICKS("Ticks", UnitType.TIME_BASED, Duration.ofSeconds(3, 600_000_000));

    private final String   name;
    private final UnitType type;
    private final Duration duration;
    private final boolean  durationEstimated;

    MinecraftUnit(final String name, final UnitType type, final Duration duration)
    {
        this(name, type, duration, false);
    }

    MinecraftUnit(
            final String name,
            final UnitType type,
            final Duration duration,
            final boolean durationEstimated)
    {
        this.name = name;
        this.type = type;
        this.duration = duration;
        this.durationEstimated = durationEstimated;
    }

    @SuppressWarnings("unchecked")
    @Override
    public <R extends Temporal> R addTo(final R temporal, final long amount)
    {
        return isTimeBased()
               ? (R) temporal.plus(duration.multipliedBy(amount).toNanos(), ChronoUnit.NANOS)
               : (R) temporal.plus(duration.multipliedBy(amount).toDays(), ChronoUnit.DAYS);
    }

    @Override
    public long between(Temporal temporal1Inclusive, Temporal temporal2Exclusive)
    {
        return temporal1Inclusive.until(temporal2Exclusive, this);
    }

    @Override
    public boolean isDateBased()
    {
        return (type == UnitType.DATE_BASED);
    }

    @Override
    public boolean isTimeBased()
    {
        return (type == UnitType.TIME_BASED);
    }

    @Override
    public boolean isSupportedBy(final Temporal temporal)
    {
        return temporal.isSupported(isTimeBased() ? ChronoUnit.NANOS : ChronoUnit.DAYS);
    }

    @Override
    public boolean isDurationEstimated()
    {
        return durationEstimated;
    }

    @Override
    public Duration getDuration()
    {
        return duration;
    }

    @Override
    public String toString()
    {
        return name;
    }

    private enum UnitType
    {
        TIME_BASED,
        DATE_BASED
    }
}
