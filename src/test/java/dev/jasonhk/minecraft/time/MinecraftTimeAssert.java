package dev.jasonhk.minecraft.time;

import org.assertj.core.api.AbstractAssert;

@SuppressWarnings("UnusedReturnValue")
public class MinecraftTimeAssert extends AbstractAssert<MinecraftTimeAssert, MinecraftTime>
{
    public MinecraftTimeAssert(MinecraftTime actual)
    {
        super(actual, MinecraftTimeAssert.class);
    }

    public static MinecraftTimeAssert assertThat(MinecraftTime actual)
    {
        return new MinecraftTimeAssert(actual);
    }

    public MinecraftTimeAssert isMinecraftTime()
    {
        return isNotNull().isInstanceOf(MinecraftTime.class);
    }

    public MinecraftTimeAssert hasTickOfDay(long tickOfDay)
    {
        isMinecraftTime();

        if (actual.toTickOfDay() != tickOfDay)
        {
            failWithMessage("Expected time to have a tick-of-day value of %d but was %d",
                            tickOfDay,
                            actual.toTickOfDay());
        }

        return this;
    }
}
