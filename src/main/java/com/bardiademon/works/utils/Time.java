package com.bardiademon.works.utils;

public final class Time
{
    private int hour;
    private short minute;
    private short second;

    private static final char PATTERN_HOUR = 'h', PATTERN_MIN = 'm', PATTERN_SEC = 's';

    private Time(final int hour , final short minute , final short second)
    {
        this.hour = hour;
        this.minute = minute;
        this.second = second;
    }

    public static Time of(final long time) throws RuntimeException
    {
        try
        {
            final String timeStr = String.valueOf(time);
            final int length = timeStr.length();
            if (length == 7)
            {
                final String secStr = timeStr.substring(length - 2);
                final String minStr = timeStr.substring(length - 4 , length - 2);
                final String hourStr = timeStr.substring(1 , length - 4);

                return of(String.format("%s:%s:%s" , hourStr , minStr , secStr));
            }
        }
        catch (Exception ignored)
        {
        }

        return of("00:00:00");
    }

    public static Time of(final String timeStr) throws RuntimeException
    {
        final String time = timeStr.trim();
        if (time.matches("[0-9]+:[0-9]{2}:[0-9]{2}"))
        {
            final String[] split = time.split(":");
            if (split.length == 3)
            {
                final int hour = Integer.parseInt(split[0]);
                final short min = Short.parseShort(split[1]);
                final short sec = Short.parseShort(split[2]);

                if (min <= 60 && sec <= 60)
                {
                    return new Time(hour , min , sec);
                }
            }
        }
        throw new RuntimeException("Invalid time format");
    }

    public int getHour()
    {
        return hour;
    }

    public short getMinute()
    {
        return minute;
    }

    public short getSecond()
    {
        return second;
    }

    @Override
    public String toString()
    {
        return toString("hh:mm:ss");
    }

    public String toString(final String pattern)
    {
        try
        {
            if (pattern.indexOf(PATTERN_HOUR) >= 0 || pattern.indexOf(PATTERN_MIN) >= 0 || pattern.indexOf(PATTERN_SEC) >= 0)
            {
                final StringBuilder result = new StringBuilder();

                final char[] chars = pattern.toCharArray();
                for (int i = 0, len = chars.length; i < len; i++)
                {
                    final char aChar = chars[i];
                    if (aChar == PATTERN_HOUR || aChar == PATTERN_MIN || aChar == PATTERN_SEC)
                    {
                        final StringBuilder pat = new StringBuilder();
                        for (int j = i; j < len; j++)
                        {
                            if (chars[j] == aChar)
                            {
                                i = j;
                                pat.append(chars[j]);
                            }
                            else break;
                        }

                        result.append(toStringOnePattern(pat.toString()));
                    }
                    else result.append(aChar);
                }

                return result.toString();
            }
        }
        catch (Exception e)
        {
            return "";
        }

        return toString();
    }

    private String toStringOnePattern(final String pattern) throws Exception
    {
        final int hms;
        if (pattern.indexOf(PATTERN_HOUR) >= 0) hms = hour;
        else if (pattern.indexOf(PATTERN_MIN) >= 0) hms = minute;
        else if (pattern.indexOf(PATTERN_SEC) >= 0) hms = second;
        else throw new Exception("Invalid one pattern");

        if (pattern.length() == 1) return String.valueOf(hms);

        final String hmsString = hms < 10 ? "0" + hms : String.valueOf(hms);

        if (pattern.length() == 2) return hmsString;

        final int len = pattern.length() - hmsString.length();

        final StringBuilder result = new StringBuilder();
        if (len > 0) for (int i = 0; i < len; i++) result.append("0");
        result.append(hmsString);

        return result.toString();
    }

    public long toLong()
    {
        return Long.parseLong(String.format("1%s%s%s" ,
                hour < 10 ? "0" + hour : String.valueOf(hour) ,
                minute < 10 ? "0" + minute : String.valueOf(minute) ,
                second < 10 ? "0" + second : String.valueOf(second)
        ));
    }

    public Time setHour(int hour)
    {
        if (hour < 0) throw new RuntimeException("Invalid hour");
        this.hour = hour;
        return this;
    }

    public Time setMinute(short minute)
    {
        if (minute < 0 || minute >= 60) throw new RuntimeException("Invalid minute");
        this.minute = minute;
        return this;
    }

    public Time setSecond(short second)
    {
        if (second < 0 || second >= 60) throw new RuntimeException("Invalid minute");
        this.second = second;
        return this;
    }

    public Time plus()
    {
        second++;
        if (second >= 60)
        {
            second = 0;
            minute++;

            if (minute >= 60)
            {
                minute = 0;
                hour++;
            }
        }

        return this;
    }

    public Time minus()
    {
        second--;
        if (second < 0)
        {
            if (minute > 0)
            {
                second = 59;
                minute--;
            }
            else
            {
                if (hour > 0)
                {
                    hour--;
                    minute = 59;
                    second = 59;
                }
                else
                {
                    hour = 0;
                    minute = 0;
                    second = 0;
                }
            }
        }
        return this;
    }

    public Time plusHour(final int hour)
    {
        if (hour < 0) throw new RuntimeException("Invalid hour");
        else if (hour == 0) return this;

        final int sec = (hour * 60) * 60;
        return plusSecond(sec);
    }

    public Time plusMinute(final int minute)
    {
        if (minute < 0) throw new RuntimeException("Invalid minute");
        else if (minute == 0) return this;

        final int sec = (minute * 60);
        return plusSecond(sec);
    }

    public Time plusSecond(final int second)
    {
        if (second < 0) throw new RuntimeException("Invalid second");
        else if (second == 0) return this;

        for (int i = 0; i < second; i++) plus();
        return this;
    }

    public Time minusHour(final int hour)
    {
        if (hour < 0) throw new RuntimeException("Invalid hour");
        else if (hour == 0) return this;

        final int sec = (hour * 60) * 60;
        return minusSecond(sec);
    }

    public Time minusMinute(final int minute)
    {
        if (minute < 0) throw new RuntimeException("Invalid minute");
        else if (minute == 0) return this;

        final int sec = (minute * 60);
        return minusSecond(sec);
    }

    public Time minusSecond(final int second)
    {
        if (second < 0) throw new RuntimeException("Invalid second");
        else if (second == 0) return this;
        for (int i = 0; i < second; i++) minus();

        return this;
    }

    public Time plus(final Time time)
    {
        if (time == null) return Time.of(this.toLong());

        int sec = this.getSecFromHour();
        sec += this.getSecFromMinute();
        sec += this.getSecond();

        return time.plusSecond(sec);
    }

    public int getSecFromHour()
    {
        if (hour == 0) return 0;
        return (hour * 60) * 60;
    }

    public int getSecFromMinute()
    {
        if (minute == 0) return 0;
        return (minute * 60);
    }
}
