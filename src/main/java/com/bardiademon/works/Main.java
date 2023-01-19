package com.bardiademon.works;

import com.bardiademon.works.controller.HomeController;
import com.bardiademon.works.data.enums.ThemeType;
import com.bardiademon.works.view.HomeView;

import java.util.Locale;

public final class Main
{
    public static void main(final String[] args)
    {
        System.out.println("bardiademon");

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