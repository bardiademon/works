package com.bardiademon.works.utils;

import java.io.File;

public final class Path
{
    public static final String
            ROOT = System.getProperty("user.dir"),
            DATA = ROOT + File.separator + "data",
            WORKS_JSON = DATA + File.separator + "works.json";


    private Path()
    {

    }
}
