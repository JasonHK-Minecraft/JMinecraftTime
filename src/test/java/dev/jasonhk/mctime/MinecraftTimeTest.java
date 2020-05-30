package dev.jasonhk.mctime;

import java.time.DateTimeException;

import static dev.jasonhk.mctime.MinecraftTimeAssert.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DisplayName("MinecraftTime")
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
public class MinecraftTimeTest
{
    @Nested
    class Static_Members
    {
        @Nested
        class ofTickOfDay
        {
            @ParameterizedTest
            @ValueSource(longs = { 0, 23999 })
            void should_return_an_instance_of_MinecraftTime(long tickOfDay)
            {
                assertThat(MinecraftTime.ofTickOfDay(tickOfDay))
                        .hasTickOfDay(tickOfDay);
            }

            @ParameterizedTest
            @ValueSource(longs = { -1, 24000 })
            void should_throw_a_DateTimeException_when_the_range_was_invalid(long tickOfDay)
            {
                assertThatThrownBy(
                        () -> { MinecraftTime.ofTickOfDay(tickOfDay); })
                        .isInstanceOf(DateTimeException.class);
            }
        }
    }
}
