# java8-datetime-duration-vs-period
_Reference_: https://docs.oracle.com/javase/tutorial/datetime/iso/period.html  
_Reference_: https://docs.oracle.com/javase/8/docs/api/java/time/Duration.html  
_Reference_: https://docs.oracle.com/javase/8/docs/api/java/time/Period.html

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
* **daylight savings time: ignored**

## static methods
All of methods below return `Duration`:
* `between(Temporal startInclusive, Temporal endExclusive)`
* `from(TemporalAmount amount)`
* `of(long amount, TemporalUnit unit)`
* `ofDays/Hours/Millis/Minutes/Nanos/Seconds`
* `parse(CharSequence text)`

## instance methods
* `Duration abs()` - copy of this duration with a positive length.
* `Temporal addTo/subtractFrom(Temporal temporal)`
* `boolean isNegative()`
* `boolean isZero()`
* `Duration minus/plus(Duration duration)`
* `Duration minus/plus(long amount, TemporalUnit unit)`
* `Duration plus/minusDays/Hours/Millis/Minutes/Nanos/Seconds(long amount)`
* `Duration multipliedBy/dividedBy(long value)`
* `Duration negated()`
* `long toDays/Hours/Millis/Minutes/Nanos`
* `Duration withNanos(int nanoOfSecond)`
* `Duration withSeconds(long seconds)`

# period
* a date-based amount of time in the ISO-8601 calendar system
* models a quantity or amount of time in terms of years, months and days
* period is modeled as a directed amount of time, meaning that individual parts of the period may be negative
* **daylight savings time: maintain the local time**

## static methods
All of methods below return `Period`:
* `between(Temporal startInclusive, Temporal endExclusive)`
* `from(TemporalAmount amount)`
* `of(long amount, TemporalUnit unit)`
* `ofDays/Hours/Millis/Minutes/Nanos/Seconds`
* `parse(CharSequence text)`

## instance methods
* `Temporal addTo/subtractFrom(Temporal temporal)`
* `long	get(TemporalUnit unit)` - Gets the value of the requested unit.
* `int getDays/Months/Years()`
* `boolean isNegative()`
* `boolean isZero()`
* `Period minus/plus(TemporalAmount amount)`
* `Period minus/plusDays/Months/Years(long amount)`
* `Period multipliedBy/dividedBy(long value)`
* `Period negated()`
* `Period normalized()` - Returns a copy of this period with the years and months normalized.
* `long	toTotalMonths()`
* `Period withDays/Months/Years(int amount)`