package com.bardiademon.works;

import com.bardiademon.works.controller.HomeController;
import com.bardiademon.works.view.HomeView;

import java.io.IOException;

public final class Main
{
    public static void main(final String[] args)
    {
        System.out.println("bardiademon");
        System.out.println("Theme Type = " + HomeView.themeType);
        new HomeController();
    }
}