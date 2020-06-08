package dev.jasonhk.minecraft.time;

import java.time.DateTimeException;
import java.util.stream.Stream;

import lombok.val;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import static org.junit.jupiter.api.DisplayNameGenerator.ReplaceUnderscores;

import static org.assertj.core.api.Assertions.assertThat;
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
        class Time_Creator extends Time_Creators_Base
        {
            @Nested
            class ofNanoOfDay extends ofNanoOfDay_Base
            {
                @ParameterizedTest(name = "when the nano-of-day value is {0}")
                @MethodSource
                void should_return_an_instance_of_MinecraftTime(
                        final long nanoOfDay,
                        final int tickOfDay)
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
            class ofSecondOfDay extends ofSecondOfDay_Base
            {
                @ParameterizedTest(name = "when the second-of-day value is {0}")
                @MethodSource
                void should_return_an_instance_of_MinecraftTime(
                        final int secondOfDay,
                        final int tickOfDay)
                {
                    MinecraftTimeAssert.assertThat(MinecraftTime.ofSecondOfDay(secondOfDay))
                                       .hasTickOfDay(tickOfDay);
                }

                @ParameterizedTest(name = "when the second-of-day value is {0}")
                @MethodSource
                void should_throw_a_DateTimeException_when_the_range_was_invalid(final int secondOfDay)
                {
                    assertThatThrownBy(() -> { MinecraftTime.ofSecondOfDay(secondOfDay); })
                            .isInstanceOf(DateTimeException.class);
                }
            }

            @Nested
            class ofTickOfDay extends ofTickOfDay_Base
            {
                @ParameterizedTest(name = "when the tick-of-day value is {0}")
                @MethodSource
                void should_return_an_instance_of_MinecraftTime(final int tickOfDay)
                {
                    MinecraftTimeAssert.assertThat(MinecraftTime.ofTickOfDay(tickOfDay))
                                       .hasTickOfDay(tickOfDay);
                }

                @ParameterizedTest(name = "when the tick-of-day value is {0}")
                @MethodSource
                void should_throw_a_DateTimeException_when_the_range_was_invalid(final int tickOfDay)
                {
                    assertThatThrownBy(() -> { MinecraftTime.ofTickOfDay(tickOfDay); })
                            .isInstanceOf(DateTimeException.class);
                }
            }
        }
    }

    @Nested
    class Instance_Members extends Instance_Members_Base
    {
        @Nested
        class Time_Accessors extends Time_Accessors_Base
        {
            @Nested
            class toNanoOfDay extends toNanoOfDay_Base
            {
                @ParameterizedTest(name = "when the nano-of-day value is {0}")
                @DisplayName("should return the nano-of-day value from the given nano-of-day value")
                @MethodSource
                void should_return_the_nano_of_day_value_from_the_given_nano_of_day_value(
                        final long originalNanoOfDay,
                        final long roundedNanoOfDay)
                {
                    val time = MinecraftTime.ofNanoOfDay(originalNanoOfDay);

                    assertThat(time.toNanoOfDay())
                            .isBetween(0L, 86_399_999_999_999L)
                            .isEqualTo(roundedNanoOfDay);
                }

                @ParameterizedTest(name = "when the tick-of-day value is {0}")
                @DisplayName("should return the nano-of-day value from the given tick-of-day value")
                @MethodSource
                void should_return_the_nano_of_day_value_from_the_given_tick_of_day_value(
                        final int tickOfDay,
                        final long nanoOfDay)
                {
                    val time = MinecraftTime.ofTickOfDay(tickOfDay);

                    assertThat(time.toNanoOfDay())
                            .isBetween(0L, 86_399_999_999_999L)
                            .isEqualTo(nanoOfDay);
                }
            }

            @Nested
            class toSecondOfDay extends toSecondOfDay_Base
            {
                @ParameterizedTest(name = "when the second-of-day value is {0}")
                @DisplayName("should return the second-of-day value from the given second-of-day value")
                @MethodSource
                void should_return_the_second_of_day_value_from_the_given_second_of_day_value(
                        final int originalSecondOfDay,
                        final int roundedSecondOfDay)
                {
                    val time = MinecraftTime.ofSecondOfDay(originalSecondOfDay);

                    assertThat(time.toSecondOfDay())
                            .isBetween(0, 86_399)
                            .isEqualTo(roundedSecondOfDay);
                }

                @ParameterizedTest(name = "when the tick-of-day value is {0}")
                @DisplayName("should return the second-of-day value from the given tick-of-day value")
                @MethodSource
                void should_return_the_second_of_day_value_from_the_given_tick_of_day_value(
                        final int tickOfDay,
                        final int secondOfDay)
                {
                    val time = MinecraftTime.ofTickOfDay(tickOfDay);

                    assertThat(time.toSecondOfDay())
                            .isBetween(0, 86_399)
                            .isEqualTo(secondOfDay);
                }
            }

            @Nested
            class toTickOfDay extends toTickOfDay_Base
            {
                @ParameterizedTest(name = "when the tick-of-day value is {0}")
                @DisplayName("should return the tick-of-day value")
                @MethodSource
                void should_return_the_tick_of_day_value(final int tickOfDay)
                {
                    val time = MinecraftTime.ofTickOfDay(tickOfDay);

                    assertThat(time.toTickOfDay())
                            .isBetween(0, 23999)
                            .isEqualTo(tickOfDay);
                }
            }
        }
    }

    static class Static_Members_Base
    {
        static class Time_Creators_Base
        {
            static class ofNanoOfDay_Base
            {
                static Stream<Arguments> should_return_an_instance_of_MinecraftTime()
                {
                    return Stream.of(Arguments.of(0L, 18_000),
                                     Arguments.of(21_599_999_999_999L, 23_999),
                                     Arguments.of(21_600_000_000_000L, 0),
                                     Arguments.of(86_399_999_999_999L, 17_999));
                }

                static Stream<Arguments> should_throw_a_DateTimeException_when_the_range_was_invalid()
                {
                    return Stream.of(
                            Arguments.of(-1L),
                            Arguments.of(86_400_000_000_000L));
                }
            }

            static class ofSecondOfDay_Base
            {
                static Stream<Arguments> should_return_an_instance_of_MinecraftTime()
                {
                    return Stream.of(Arguments.of(0, 18_000),
                                     Arguments.of(21_599, 23_999),
                                     Arguments.of(21_600, 0),
                                     Arguments.of(86_399, 17_999));
                }

                static Stream<Arguments> should_throw_a_DateTimeException_when_the_range_was_invalid()
                {
                    return Stream.of(
                            Arguments.of(-1),
                            Arguments.of(86_400));
                }
            }

            static class ofTickOfDay_Base
            {
                static Stream<Arguments> should_return_an_instance_of_MinecraftTime()
                {
                    return Stream.of(Arguments.of(0),
                                     Arguments.of(23_999));
                }

                static Stream<Arguments> should_throw_a_DateTimeException_when_the_range_was_invalid()
                {
                    return Stream.of(
                            Arguments.of(-1),
                            Arguments.of(24_000));
                }
            }
        }
    }

    static class Instance_Members_Base
    {
        static class Time_Accessors_Base
        {
            static class toNanoOfDay_Base
            {
                static Stream<Arguments> should_return_the_nano_of_day_value_from_the_given_nano_of_day_value()
                {
                    return Stream.of(Arguments.of(0L, 0L),
                                     Arguments.of(86_399_999_999_999L, 86_396_400_000_000L));
                }

                static Stream<Arguments> should_return_the_nano_of_day_value_from_the_given_tick_of_day_value()
                {
                    return Stream.of(Arguments.of(0, 21_600_000_000_000L),
                                     Arguments.of(17_999, 86_396_400_000_000L),
                                     Arguments.of(18_000, 0L),
                                     Arguments.of(23_999, 21_596_400_000_000L));
                }
            }

            static class toSecondOfDay_Base
            {
                static Stream<Arguments> should_return_the_second_of_day_value_from_the_given_second_of_day_value()
                {
                    return Stream.of(Arguments.of(0, 0),
                                     Arguments.of(86_399, 86_396));
                }

                static Stream<Arguments> should_return_the_second_of_day_value_from_the_given_tick_of_day_value()
                {
                    return Stream.of(Arguments.of(0, 21_600),
                                     Arguments.of(17_999, 86_396),
                                     Arguments.of(18_000, 0),
                                     Arguments.of(23_999, 21_596));
                }
            }

            static class toTickOfDay_Base
            {
                static Stream<Arguments> should_return_the_tick_of_day_value()
                {
                    return Stream.of(Arguments.of(0),
                                     Arguments.of(23_999));
                }
            }
        }
    }
}
