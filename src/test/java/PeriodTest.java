import org.junit.Test;

import java.time.*;
import java.util.Arrays;

import static java.time.temporal.ChronoUnit.*;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Created by mtumilowicz on 2019-01-17.
 */
public class PeriodTest {

    @Test
    public void parse_weeks() {
        Period period = Period.parse("P4W");

        assertThat(period.toString(), is("P28D"));
    }

    @Test
    public void getUnits() {
        assertThat(Period.ZERO.getUnits(), is(Arrays.asList(YEARS, MONTHS, DAYS)));
    }

    @Test
    public void daylight_saving() {
        LocalDateTime dateTime = LocalDateTime.of(2010, 3, 30, 10, 10, 10);

        assertThat(dateTime.plus(Period.ofDays(1)),
                is(LocalDateTime.of(2010, 3, 31, 10, 10, 10)));
    }

    @Test
    public void daylight_saving_zonedDateTime() {
        ZonedDateTime dateTime = LocalDateTime.of(2019, 3, 30, 10, 10, 10)
                .atZone(ZoneId.of("Europe/Paris"));

        assertThat(dateTime.plus(Period.ofDays(1)),
                is(LocalDateTime.of(2019, 3, 31, 10, 10, 10)
                        .atZone(ZoneId.of("Europe/Paris"))));
    }
    
    @Test
    public void getChronology() {
        assertThat(Period.ofDays(1).getChronology().getCalendarType(), is("iso8601"));
    }
}
