package com.bardiademon.works;

import com.bardiademon.works.controller.HomeController;
import com.bardiademon.works.data.enums.ThemeType;
import com.bardiademon.works.utils.Time;
import com.bardiademon.works.view.HomeView;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.Locale;

public final class Main
{
    public static void main(final String[] args) throws IOException
    {
        System.out.println("bardiademon");

        final JSONArray absEngineCore = new JSONObject(
                new String(Files.readAllBytes(
                        new File("D:\\Programming\\Works\\app\\data\\my_works.json").toPath()
                ) , StandardCharsets.UTF_8)
        ).getJSONArray("abs_engine_core");

        final String resultItem = "Name: :NAME:\n" +
                "Time: :TIME:\n" +
                "Result: :RESULT:\n---------------\n";

        final StringBuilder result = new StringBuilder();

        Time totalTime = null;
        float totalMoney = 0;

        for (int i = 0; i < absEngineCore.length(); i++)
        {
            final JSONObject item = absEngineCore.getJSONObject(i);

            final String name = item.getString("name");
            final long worked = item.getLong("worked");
            final int hourlyAmount = item.getInt("hourly_amount");

            final Time time = Time.of(worked);

            totalTime = time.plus(totalTime);

            final float money = HomeController.calculateMoney(time , hourlyAmount);

            totalMoney += money;

            result.append(
                    resultItem
                            .replace(":NAME:" , name)
                            .replace(":TIME:" , time.toString())
                            .replace(":RESULT:" , HomeController.moneyToString(money).replace("IRR" , ""))
            );
        }

        result.append("\n-------\nTOTAL\n-------\n").append(
                resultItem
                        .replace(":NAME:" , "TOTAL")
                        .replace(":TIME:" , totalTime.toString())
                        .replace(":RESULT:" , HomeController.moneyToString(totalMoney).replace("IRR" , ""))
        );

        Files.write(new File("output.txt").toPath() , result.toString().getBytes(StandardCharsets.UTF_8));


        HomeView.themeType = ThemeType.DARK;
        if (args != null && args.length == 1)
        {
            try
            {
                final String themeTypeStr = args[0].trim().toUpperCase(Locale.ROOT);
                HomeView.themeType = ThemeType.valueOf(themeTypeStr);
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }

        System.out.println("Theme Type = " + HomeView.themeType);

        new HomeController();
    }
}