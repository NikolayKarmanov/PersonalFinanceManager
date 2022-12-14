package ru.netology;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Buy implements Serializable {
    private String title;
    private LocalDate date;
    private int sum;

    public Buy(String title, String date, int sum) {
        this.title = title;
        this.date = LocalDate.parse(date, DateTimeFormatter.ofPattern("yyyy.MM.dd"));
        this.sum = sum;
    }

    public String getTitle() {
        return title;
    }

    public String getDate() {
        return date.format(DateTimeFormatter.ofPattern("yyyy.MM.dd"));
    }

    public LocalDate getLocalDate() {
        return date;
    }

    public int getSum() {
        return sum;
    }
}
