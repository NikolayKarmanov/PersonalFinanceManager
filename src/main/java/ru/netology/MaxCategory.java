package ru.netology;

import org.json.simple.JSONObject;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MaxCategory {

    public static String getMaxCategory(List<Buy> basket) {
        Map<String, String> categories = Categories.getCategories();
        Map<String, Integer> summary = new HashMap<>();

        // заполняем список "категория:сумма"
        for(Buy buy : basket) {
            String category;
            String title = buy.getTitle();
            // ==============================================================================
            if(categories.containsKey(title)) {
                // если покупка есть в спике категорий, то определяем ее категорию
                category = categories.get(title);
            } else {
                // если покупки нет в списке категорий, то присваиваем ей категорию "другое"
                category = "другое";
            }
            // ==============================================================================
            if(summary.containsKey(category)) {
                // если категория поупки уже есть в списке "категория:сумма", то меняем значение суммы
                int currentSum = summary.get(category);
                summary.replace(category, currentSum + buy.getSum());
            } else {
                // если категории покупки нет в списке "категория:сумма", то добавляем ее и сумму
                summary.put(category, buy.getSum());
            }
        }

        // после того, как мы заполнили список "категория:сумма",
        // нам нужно только найти категорию с максимальной суммой и вернуть ее

        Map.Entry<String, Integer> result = summary.entrySet().stream().max(Comparator.comparing(Map.Entry::getValue)).orElse(null);

        JSONObject body = new JSONObject();
        body.put(result.getKey(), result.getValue());
        JSONObject head = new JSONObject();
        head.put("maxCategory", body);
        String str = head.toJSONString();

        return str;
    }
}
