package com.mygdx.game.utils;

import java.time.Duration;
import java.time.Instant;

public class Debug {
    public static boolean debug_flag = true;

    public static Instant startTime;
    public static Instant endTime;

    public static void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    public static void measureTime(String message){
        if(startTime != null && endTime != null){
            long timeElapsed = Duration.between(startTime, endTime).toSeconds();
            if (timeElapsed == 0L) {
                timeElapsed = Duration.between(startTime, endTime).toMillis();
                System.out.println(message + " " + timeElapsed + " ms");
            } else
                System.out.println(message + " " + timeElapsed + " s");
        }
    }
}
