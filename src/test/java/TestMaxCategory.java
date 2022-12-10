import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import ru.netology.Buy;
import ru.netology.MaxCategory;

import java.util.ArrayList;
import java.util.List;

public class TestMaxCategory {

    Buy buy1 = new Buy("булка", "2022.02.08", 50);
    Buy buy2 = new Buy("колбаса", "2022.02.08", 300);
    Buy buy3 = new Buy("сухарики", "2022.02.08", 20);
    Buy buy4 = new Buy("курица", "2022.02.08", 500);
    Buy buy5 = new Buy("тапки", "2022.02.08", 1000);
    Buy buy6 = new Buy("шапка", "2022.02.08", 1000);
    Buy buy7 = new Buy("мыло", "2022.02.08", 50);

    List<Buy> basket = new ArrayList<>();

    {
        basket.add(buy1);
        basket.add(buy2);
        basket.add(buy3);
        basket.add(buy4);
        basket.add(buy5);
        basket.add(buy6);
        basket.add(buy7);
    }

    @Test
    public void testMaxCategory() throws ParseException {
        String str = MaxCategory.getMaxCategory(basket);

        JSONParser parser = new JSONParser();
        JSONObject json = (JSONObject) parser.parse(str);

        // значением ключа "maxCategory" является вложенный json, поэтому извлекаем из него необходимые нам данные
        JSONObject result_json = (JSONObject) json.get("maxCategory");
        String result_title = (String) result_json.get("category");
        int result_sum = (int) (long) result_json.get("sum");

        String expected_title = "одежда";
        int expected_sum = 2000;

        Assertions.assertEquals(expected_title, result_title);
        Assertions.assertEquals(expected_sum, result_sum);
    }

    @Test
    public void testOtherCategory() throws ParseException {
        Buy other = new Buy("Лодка", "2022.02.08", 50000);
        basket.add(other);
        String str = MaxCategory.getMaxCategory(basket);

        JSONParser parser = new JSONParser();
        JSONObject json = (JSONObject) parser.parse(str);

        // значением ключа "maxCategory" является вложенный json, поэтому извлекаем из него необходимые нам данные
        JSONObject result_json = (JSONObject) json.get("maxCategory");
        String result_title = (String) result_json.get("category");
        int result_sum = (int) (long) result_json.get("sum");

        String expected_title = "другое";
        int expected_sum = 50000;

        Assertions.assertEquals(expected_title, result_title);
        Assertions.assertEquals(expected_sum, result_sum);
    }
}
