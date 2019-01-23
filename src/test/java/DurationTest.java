import org.junit.Test;

import java.time.Duration;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;
import java.time.temporal.UnsupportedTemporalTypeException;
import java.util.Arrays;

import static java.time.temporal.ChronoUnit.NANOS;
import static java.time.temporal.ChronoUnit.SECONDS;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Created by mtumilowicz on 2019-01-17.
 */
public class DurationTest {

    @Test(expected = DateTimeParseException.class)
    public void parse_wrongFormat() {
        Duration.parse("X");
    }

    @Test
    public void parse() {
        Duration duration = Duration.parse("P2DT-3H4M");

        assertThat(duration.getSeconds(), is(4 * 60 - 3 * 3600 + 2 * 24 * 3600L));
        assertThat(duration.toString(), is("PT45H4M"));
    }

    @Test
    public void parse_negate() {
        Duration duration = Duration.parse("-PT4M");

        assertThat(duration.getSeconds(), is(-4 * 60L));
        assertThat(duration.toString(), is("PT-4M"));
    }

    @Test
    public void equals_same_value_different_format() {
        Duration d1 = Duration.parse("PT60M");
        Duration d2 = Duration.parse("PT1H");

        assertThat(d1, is(d2));
    }

    @Test
    public void getUnits() {
        assertThat(Duration.ZERO.getUnits(), is(Arrays.asList(SECONDS, NANOS)));
    }

    @Test(expected = UnsupportedTemporalTypeException.class)
    public void get() {
        Duration.ZERO.get(ChronoUnit.MONTHS);
    }

    @Test
    public void withSeconds() {
        Duration duration = Duration.ofSeconds(10, 15);

        assertThat(duration.withSeconds(15), is(Duration.ofSeconds(15, 15)));
    }

    @Test
    public void withSeconds_withNanos() {
        Duration duration = Duration.ofSeconds(10, 15);

        assertThat(duration.withSeconds(15).withNanos(20),
                is(Duration.ofSeconds(15, 20)));
    }
}
