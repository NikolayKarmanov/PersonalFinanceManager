package ru.netology;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class Parameters {
    public static String getRandomDate () {
        Random random = new Random();
        int year = random.nextInt(3) + 2020;
        int month = random.nextInt(12) + 1;
        String month_string = Integer.toString(month);
        if (month < 10) {
            month_string = "0" + month_string;
        }
        int day = random.nextInt(30) + 1;
        String day_string = Integer.toString(day);
        if (day < 10) {
            day_string = "0" + day_string;
        }
        String date_string = year + "." + month_string + "." + day_string;
        return date_string;
    }
    public static String getRandomTitle (Map<String, String> categories) {
        Random random = new Random();
        List<String> title = new ArrayList<>();
        for (Map.Entry<String, String> entry : categories.entrySet()) {
            title.add(entry.getKey());
        }
        String randomTitle = title.get(random.nextInt(title.size()));
        return randomTitle;
    }
    public static int getRandomSum() {
        Random random = new Random();
        return (random.nextInt(50) + 1) * 10;
    }
}
