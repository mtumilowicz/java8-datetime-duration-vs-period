# java8-datetime-duration-vs-period
_Reference_: https://docs.oracle.com/javase/tutorial/datetime/iso/period.html  
_Reference_: https://docs.oracle.com/javase/8/docs/api/java/time/Duration.html  
_Reference_: https://docs.oracle.com/javase/8/docs/api/java/time/Period.html  
_Reference_: https://docs.oracle.com/javase/8/docs/api/java/time/temporal/ChronoUnit.html

# duration
* a time-based amount of time
* models a quantity or amount of time in terms of seconds and nanoseconds
* duration uses nanosecond resolution with a maximum value of the seconds that can be held in a long
    ```
    public static Duration ofMillis(long millis) {
        long secs = millis / 1000;
        int mos = (int) (millis % 1000);
        if (mos < 0) {
            mos += 1000;
            secs--;
        }
        return create(secs, mos * 1000_000);
    }
    ```
    as you may see from one of the many static factory 
    methods - the duration is calculated to (second, millis)
* seconds could be negative, positive or zero while nanoseconds could be only positive or zero
* **daylight savings time: NOT ignored for** `ZonedDateTime`

## static methods
All of methods below return `Duration`:
* `between(Temporal startInclusive, Temporal endExclusive)`
* `from(TemporalAmount amount)`
* `of(long amount, TemporalUnit unit)`
* `ofDays/Hours/Millis/Minutes/Nanos/Seconds`
* `parse(CharSequence text)` - pattern: `PnDTnHnMn.nS` (with optional sign at the beginning)
    * `P2DT-3H4M` parsed to _2 days, -3 hours and 4 minutes_
    * `-P2D` parsed to _-2 days_
    * normalized to hours: `P2DT-3H4M` -> `PT45H4M`, `PT60M` -> `PT1H`
    * minus in the front is removed and propagated inside: `-PT4M` is transformed to `PT-4M`
    * `DateTimeParseException`

## instance methods
* `Duration abs()` - copy of this duration with a positive length.
* `Temporal addTo/subtractFrom(Temporal temporal)`
* `int compareTo(Duration otherDuration)`
* `boolean equals(Object otherDuration)` - compares seconds and nanosecond, so format does not matter
* `boolean isNegative()`
* `boolean isZero()`
* `List<TemporalUnit> getUnits()` - {SECONDS, NANOS}
* `long	get(TemporalUnit unit)`
    * only second and nanos, otherwise `UnsupportedTemporalTypeException`
* `long getNano/Seconds()`
* `Duration minus/plus(Duration duration)`
* `Duration minus/plus(long amount, TemporalUnit unit)`
* `Duration plus/minusDays/Hours/Millis/Minutes/Nanos/Seconds(long amount)`
* `Duration multipliedBy/dividedBy(long value)`
* `Duration negated()`
* `long toDays/Hours/Millis/Minutes/Nanos()`
* `String toString()`
    * format: `PTnHnMnS` and fractional seconds are placed after a decimal point in the seconds section
    * hours, minutes and seconds all have the same sign
* `Duration withSeconds(long seconds)` - copy of this duration with the specified amount of seconds
* `Duration withNanos(int nanoOfSecond)`

# period
* a date-based amount of time in the ISO-8601 calendar system
* models a quantity or amount of time in terms of years, months and days
* period is modeled as a directed amount of time, meaning that individual parts of the period may be negative
* **daylight savings time: ignored**

## static methods
All of methods below return `Period`:
* `between(Temporal startInclusive, Temporal endExclusive)`
* `from(TemporalAmount amount)`
* `of(long amount, TemporalUnit unit)`
* `ofDays/Hours/Millis/Minutes/Nanos/Seconds(long amount)`
* `parse(CharSequence text)`
    * format: `PnYnMnWnD` (with optional sign at the beginning)
    * weeks are normalized to days: `P4W` -> `P28D`
    * `DateTimeParseException`

## instance methods
* `Temporal addTo/subtractFrom(Temporal temporal)`
* `boolean equals(Object obj)`
* `IsoChronology getChronology()` - ISO-8601 (The ISO-8601 calendar system is the 
    modern civil calendar system used today in most of the world)
* `long	get(TemporalUnit unit)`
* `int getDays/Months/Years()`
* `List<TemporalUnit> getUnits()` - {YEARS, MONTHS, DAYS}
* `boolean isNegative()`
* `boolean isZero()`
* `Period minus/plus(TemporalAmount amount)`
* `Period minus/plusDays/Months/Years(long amount)`
* `Period multipliedBy/dividedBy(long value)`
* `Period negated()`
* `Period normalized()` - Returns a copy of this period with the years and months normalized.
* `String toString()`
* `long	toTotalMonths()`
* `Period withDays/Months/Years(int amount)`

# tests
We provide tests only for not-obvious methods and not obvious cases.

## duration
* parse
    ```
    Duration duration = Duration.parse("P2DT-3H4M");
    assertThat(duration.toString(), is("PT45H4M"));
    
    Duration duration = Duration.parse("-PT4M");
    assertThat(duration.toString(), is("PT-4M"));
    
    Duration d1 = Duration.parse("PT60M");
    Duration d2 = Duration.parse("PT1H");
    assertThat(d1, is(d2));
    ```
* getUnits
    ```
    assertThat(Duration.ZERO.getUnits(), is(Arrays.asList(SECONDS, NANOS)));
    ```
* get not supported unit
    ```
    @Test(expected = UnsupportedTemporalTypeException.class)
    public void get() {
        Duration.ZERO.get(ChronoUnit.MONTHS);
    }
    ```
* **daylight saving time**
    ```
    LocalDateTime dateTime = LocalDateTime.of(2010, 3, 30, 10, 10, 10);
    assertThat(dateTime.plus(Duration.ofDays(1)), is(LocalDateTime.of(2010, 3, 31, 10, 10, 10)));
    
    
    ZonedDateTime dateTime = LocalDateTime.of(2019, 3, 30, 10, 10, 10).atZone(ZoneId.of("Europe/Paris"));
    assertThat(dateTime.plus(Duration.ofDays(1)), 
        is(LocalDateTime.of(2019, 3, 31, 11, 10, 10).atZone(ZoneId.of("Europe/Paris"))));
    ```
* withSeconds, withNanos
    ```
    Duration duration = Duration.ofSeconds(10, 15);
    
    assertThat(duration.withSeconds(15).withNanos(20),
            is(Duration.ofSeconds(15, 20)));
    ```
## period
We do not provide tests with same behaviour as `Duration`.
* getUnits
    ```
    assertThat(Period.ZERO.getUnits(), is(Arrays.asList(YEARS, MONTHS, DAYS)));
    ```
* **daylight saving time**
    ```
    LocalDateTime dateTime = LocalDateTime.of(2010, 3, 30, 10, 10, 10);
    assertThat(dateTime.plus(Period.ofDays(1)),
            is(LocalDateTime.of(2010, 3, 31, 10, 10, 10)));
            
            
    ZonedDateTime dateTime = LocalDateTime.of(2019, 3, 30, 10, 10, 10).atZone(ZoneId.of("Europe/Paris"));
    assertThat(dateTime.plus(Period.ofDays(1)),
            is(LocalDateTime.of(2019, 3, 31, 10, 10, 10).atZone(ZoneId.of("Europe/Paris"))));
    ```
* getChronology
    ```
    assertThat(Period.ofDays(1).getChronology().getCalendarType(), is("iso8601"));
    ```
