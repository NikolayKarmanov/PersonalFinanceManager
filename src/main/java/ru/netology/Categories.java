package ru.netology;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Categories {
    public static Map<String, String> getCategories() {
        return categories;
    }

    static Map<String, String> categories; // набор "товар:категория"
    static {
        try {
            categories = loadCategories();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    static Map<String, String> loadCategories() throws IOException {
        Map<String, String> temp = new HashMap<>();
        try (BufferedReader br = new BufferedReader(new FileReader("categories.tsv"))) {

            while (true) {
                String str = br.readLine();
                if (str == null) {
                    break;
                }
                String[] pairs = str.split("\t");
                temp.put(pairs[0], pairs[1]);
            }
        }
        return temp;
    }
}
