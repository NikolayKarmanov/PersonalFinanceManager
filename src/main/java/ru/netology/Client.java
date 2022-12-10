package ru.netology;

import com.google.gson.Gson;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Client {
    public static void main(String[] args) throws IOException, ParseException {
        String host = "127.0.0.1";
        int port = 8989;

        try (Socket clientSocket = new Socket(host, port);
             PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
             BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()))) {

            // создаем объект покупка
            Buy buy = new Buy("булка", "2022.12.08", 2000);

            // переводим объект-покупка в json-формат
            JSONObject json = new JSONObject();
            json.put("title", buy.getTitle());
            json.put("date", buy.getDate());
            json.put("sum", buy.getSum());

            // отправляем покупку в json-формате на сервер
            out.println(json);

            String getMaxCategory = in.readLine();
            System.out.println(getMaxCategory);

        }
    }
}