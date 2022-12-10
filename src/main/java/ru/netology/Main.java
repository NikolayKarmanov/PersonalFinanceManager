package ru.netology;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) throws ParseException {
        List<Buy> basket = new ArrayList<>();

        try (ServerSocket serverSocket = new ServerSocket(8989);) { // стартуем сервер один(!) раз
            while (true) { // в цикле(!) принимаем подключения
                try (
                        Socket socket = serverSocket.accept();
                        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                        PrintWriter out = new PrintWriter(socket.getOutputStream());
                ) {
                    // принимаем от клиента строку и сохраняем ее в переменную типа String
                    String answer = in.readLine();
                    // преобразуем строку формата json в объект покупки
                    JSONParser parser = new JSONParser();
                    JSONObject json = (JSONObject) parser.parse(answer);
                    String title = (String) json.get("title");
                    String date = (String) json.get("date");
                    int sum = (int) (long) json.get("sum");
                    Buy buy = new Buy(title, date, sum);
                    //добавляем объект покупки в корзину
                    basket.add(buy);
                    String str = MaxCategory.getMaxCategory(basket);
                    out.println(str);
                }
            }
        } catch (IOException e) {
            System.out.println("Не могу стартовать сервер");
            e.printStackTrace();
        }
    }
}
