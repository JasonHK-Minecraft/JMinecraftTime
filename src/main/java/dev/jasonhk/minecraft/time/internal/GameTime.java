package dev.jasonhk.minecraft.time.internal;

import org.apiguardian.api.API;

/**
 * A utility class containing constants of time in Minecraft, without scaling the day-light cycle.
 *
 * @apiNote This class is for <strong>internal use only</strong>.
 * @since 0.0.1
 */
@API(status = API.Status.INTERNAL,
     since = "0.0.1",
     consumers = { "dev.jasonhk.minecraft.time.*" })
public final class GameTime
{
    /**
     * Minutes per day.
     */
    public static final int MINUTES_PER_DAY = 20;

    /**
     * Ticks per second.
     */
    public static final int TICKS_PER_SECOND = 20;

    private GameTime()
    {
        throw new UnsupportedOperationException(
                "This is a utility class and cannot be instantiated.");
    }
}
