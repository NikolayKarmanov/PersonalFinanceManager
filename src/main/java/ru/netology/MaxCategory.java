package ru.netology;

import org.json.simple.JSONObject;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MaxCategory {

    public static String getMaxCategory(List<Buy> basket, String date) {
        LocalDate dateBuy = LocalDate.parse(date, DateTimeFormatter.ofPattern("yyyy.MM.dd"));
        JSONObject json = new JSONObject();

        // ==============================================================================
        Map<String, Integer> summary = calculation(basket);
        Map.Entry<String, Integer> result = summary.entrySet().stream().max(Comparator.comparing(Map.Entry::getValue)).orElse(null);
        JSONObject maxCategory = new JSONObject();
        maxCategory.put("category", result.getKey());
        maxCategory.put("sum", result.getValue());
        json.put("maxCategory", maxCategory);
        // ==============================================================================
        List<Buy> thisYear = basket.stream().filter(p -> p.getLocalDate().getYear() == dateBuy.getYear()).toList();
        Map<String, Integer> maxYearMap = calculation(thisYear);
        Map.Entry<String, Integer> resultThisYear = maxYearMap.entrySet().stream().max(Comparator.comparing(Map.Entry::getValue)).orElse(null);
        JSONObject maxYearCategory = new JSONObject();
        maxYearCategory.put("category", resultThisYear.getKey());
        maxYearCategory.put("sum", resultThisYear.getValue());
        json.put("maxYearCategory", maxYearCategory);
        // ==============================================================================
        List<Buy> thisMonth = basket.stream().filter(p -> p.getLocalDate().getMonth() == dateBuy.getMonth() && p.getLocalDate().getYear() == dateBuy.getYear()).toList();
        Map<String, Integer> maxMonthMap = calculation(thisMonth);
        Map.Entry<String, Integer> resultThisMonth = maxMonthMap.entrySet().stream().max(Comparator.comparing(Map.Entry::getValue)).orElse(null);
        JSONObject maxMonthCategory = new JSONObject();
        maxMonthCategory.put("category", resultThisMonth.getKey());
        maxMonthCategory.put("sum", resultThisMonth.getValue());
        json.put("maxMonthCategory", maxMonthCategory);
        // ==============================================================================
        List<Buy> thisDay = basket.stream().filter(p -> p.getLocalDate().getMonth() == dateBuy.getMonth() && p.getLocalDate().getYear() == dateBuy.getYear() && p.getLocalDate().getDayOfMonth() == dateBuy.getDayOfMonth()).toList();
        Map<String, Integer> maxDayMap = calculation(thisDay);
        Map.Entry<String, Integer> resultThisDay = maxDayMap.entrySet().stream().max(Comparator.comparing(Map.Entry::getValue)).orElse(null);
        JSONObject maxDayCategory = new JSONObject();
        maxDayCategory.put("category", resultThisDay.getKey());
        maxDayCategory.put("sum", resultThisDay.getValue());
        json.put("maxDayCategory", maxDayCategory);
        // ==============================================================================

        String str = json.toJSONString();
        return str;
    }

    private static Map<String, Integer> calculation(List<Buy> basket) {
        Map<String, String> categories = Categories.getCategories();
        Map<String, Integer> summary = new HashMap<>();

        // заполняем список "категория:сумма"
        for (Buy buy : basket) {
            String category;
            String title = buy.getTitle();
            // ==============================================================================
            if (categories.containsKey(title)) {
                // если покупка есть в спике категорий, то определяем ее категорию
                category = categories.get(title);
            } else {
                // если покупки нет в списке категорий, то присваиваем ей категорию "другое"
                category = "другое";
            }
            // ==============================================================================
            if (summary.containsKey(category)) {
                // если категория поупки уже есть в списке "категория:сумма", то меняем значение суммы
                int currentSum = summary.get(category);
                summary.replace(category, currentSum + buy.getSum());
            } else {
                // если категории покупки нет в списке "категория:сумма", то добавляем ее и сумму
                summary.put(category, buy.getSum());
            }
        }

        return summary;
    }
}
