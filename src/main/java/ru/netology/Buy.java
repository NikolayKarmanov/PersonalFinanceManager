package ru.netology;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Date;

@Getter
@AllArgsConstructor
public class Buy {
    private String title;
    private String date;
    private int sum;
}
