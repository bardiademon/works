package com.bardiademon.works;

import com.bardiademon.works.controller.HomeController;

import java.sql.Time;
import java.sql.Timestamp;
import java.time.LocalDateTime;

public class Main
{
    public static void main(String[] args)
    {
//        final String[] times = {
//                "12:54:00" ,
//                "14:17:00" ,
//
//                "14:31:00" ,
//                "18:04:00" ,
//
//                "01:00:00" ,
//                "02:07:00" ,
//
//                "12:15:00" ,
//                "15:40:00" ,
//
//                "17:49:00" ,
//                "18:19:00" ,
//
//                "20:45:00" ,
//                "21:27:00" ,
//        };
        final String[] times = {
                "12:00:00" ,
                "14:27:00" ,
                "15:20:00" ,
                "17:13:00" ,
        };
        System.out.println("Time.valueOf(\"05:00:00\").getTime() = " + Time.valueOf("05:00:00").getTime());
        System.out.println("new Time(30600000) = " + new Time(30600000));

        System.out.println("Time.valueOf(\"00:00:00\") = " + Time.valueOf("00:00:00").getTime());
        System.out.println(Timestamp.valueOf(LocalDateTime.now()).getTime());

        long[] timesLong = new long[times.length / 2];

        int counter = 0;
        for (int i = 0; i < times.length - 1; i += 2)
        {
            final Time time1 = Time.valueOf(times[i]);
            final Time time2 = Time.valueOf(times[i + 1]);

            timesLong[counter++] = new Time((time1.getTime() - time2.getTime())).getTime();
        }

        Time time = new Time(timesLong[0]);
        for (int i = 1; i < timesLong.length; i++)
        {
            final Time time1 = Time.valueOf(times[i]);

            time = new Time(time.getTime() + time1.getTime());
        }

        System.out.println(time.getTime());


        System.out.println("bardiademon");
        new HomeController();
    }
}