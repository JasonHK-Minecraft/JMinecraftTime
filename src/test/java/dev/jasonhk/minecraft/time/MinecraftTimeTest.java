package dev.jasonhk.minecraft.time;

import java.time.DateTimeException;
import java.util.stream.Stream;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import static org.junit.jupiter.api.DisplayNameGenerator.ReplaceUnderscores;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DisplayName("MinecraftTime")
@DisplayNameGeneration(ReplaceUnderscores.class)
@SuppressWarnings({ "unused", "JUnit5MalformedParameterized" })
public class MinecraftTimeTest
{
    @Nested
    class Static_Members extends Static_Members_Base
    {
        @Nested
        class ofNanoOfDay extends ofNanoOfDay_Base
        {
            @ParameterizedTest(name = "when the nano-of-day value is {0}")
            @MethodSource
            void should_return_an_instance_of_MinecraftTime(
                    final long nanoOfDay,
                    final long tickOfDay)
            {
                MinecraftTimeAssert.assertThat(MinecraftTime.ofNanoOfDay(nanoOfDay))
                                   .hasTickOfDay(tickOfDay);
            }

            @ParameterizedTest(name = "when the nano-of-day value is {0}")
            @MethodSource
            void should_throw_a_DateTimeException_when_the_range_was_invalid(final long nanoOfDay)
            {
                assertThatThrownBy(() -> { MinecraftTime.ofNanoOfDay(nanoOfDay); })
                        .isInstanceOf(DateTimeException.class);
            }
        }

        @Nested
        class ofTickOfDay extends ofTickOfDay_Base
        {
            @ParameterizedTest(name = "when the tick-of-day value is {0}")
            @MethodSource
            void should_return_an_instance_of_MinecraftTime(final long tickOfDay)
            {
                MinecraftTimeAssert.assertThat(MinecraftTime.ofTickOfDay(tickOfDay))
                                   .hasTickOfDay(tickOfDay);
            }

            @ParameterizedTest(name = "when the tick-of-day value is {0}")
            @MethodSource
            void should_throw_a_DateTimeException_when_the_range_was_invalid(final long tickOfDay)
            {
                assertThatThrownBy(() -> { MinecraftTime.ofTickOfDay(tickOfDay); })
                        .isInstanceOf(DateTimeException.class);
            }
        }
    }

    static class Static_Members_Base
    {
        static class ofNanoOfDay_Base
        {
            static Stream<Arguments> should_return_an_instance_of_MinecraftTime()
            {
                return Stream.of(
                        Arguments.of(0L, 18_000L),
                        Arguments.of(21_599_999_999_999L, 23_999L),
                        Arguments.of(21_600_000_000_000L, 0L),
                        Arguments.of(86_399_999_999_999L, 17_999L));
            }

            static Stream<Arguments> should_throw_a_DateTimeException_when_the_range_was_invalid()
            {
                return Stream.of(
                        Arguments.of(-1L),
                        Arguments.of(86_400_000_000_000L));
            }
        }

        static class ofTickOfDay_Base
        {
            static Stream<Arguments> should_return_an_instance_of_MinecraftTime()
            {
                return Stream.of(
                        Arguments.of(0L),
                        Arguments.of(23_999L));
            }

            static Stream<Arguments> should_throw_a_DateTimeException_when_the_range_was_invalid()
            {
                return Stream.of(
                        Arguments.of(-1L),
                        Arguments.of(24_000L));
            }
        }
    }
}
