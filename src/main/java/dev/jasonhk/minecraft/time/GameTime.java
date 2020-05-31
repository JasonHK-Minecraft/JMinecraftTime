package dev.jasonhk.minecraft.time;

final class GameTime
{
    /**
     * Minutes per day.
     */
    static final int MINUTES_PER_DAY = 20;

    /**
     * Ticks per second.
     */
    static final int TICKS_PER_SECOND = 20;

    private GameTime()
    {
        throw new UnsupportedOperationException(
                "This is a utility class and cannot be instantiated.");
    }
}
