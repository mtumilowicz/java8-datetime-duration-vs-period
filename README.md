# java8-datetime-duration-vs-period
_Reference_: https://docs.oracle.com/javase/tutorial/datetime/iso/period.html
_Reference_: https://docs.oracle.com/javase/8/docs/api/java/time/Duration.html

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

## static methods

## instance methods