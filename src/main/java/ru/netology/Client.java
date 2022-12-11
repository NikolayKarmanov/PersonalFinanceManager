package ru.netology;

import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Map;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) throws IOException {
        String host = "127.0.0.1";
        int port = 8989;
        Map<String, String> map = Categories.getCategories();
        int i = 0;
        while (i < 200) {
            i++;
            try (Socket clientSocket = new Socket(host, port);
                 PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
                 BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()))) {

                // создаем объект покупка
                Buy buy = new Buy(Parameters.getRandomTitle(map), Parameters.getRandomDate(), Parameters.getRandomSum());

                // переводим объект-покупка в json-формат
                JSONObject json = new JSONObject();
                json.put("title", buy.getTitle());
                json.put("date", buy.getDate());
                json.put("sum", buy.getSum());

                // отправляем покупку в json-формате на сервер
                out.println(json);
                String getMaxCategory = in.readLine();
                //getMaxCategory = getMaxCategory.replaceAll(":\\{", ":\n\t\\{");
                System.out.println(buy.toString());
                getMaxCategory = getMaxCategory.replaceAll("\\},", "\\},\n");
                System.out.println(getMaxCategory);
                System.out.println("==============================================");
            }
        }
    }
}