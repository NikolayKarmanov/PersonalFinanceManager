package ru.netology;

import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) throws IOException {
        String host = "127.0.0.1";
        int port = 8989;
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.print("Введите название покупки или \"end\" для выхода: ");
            String title = scanner.nextLine();
            if (title.equals("end")) {
                break;
            }
            System.out.print("Введите дату: ");
            String date = scanner.nextLine();
            System.out.print("Введите сумму: ");
            String sumStr = scanner.nextLine();
            int sum = Integer.parseInt(sumStr);

            try (Socket clientSocket = new Socket(host, port);
                 PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
                 BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()))) {

                // создаем объект покупка
                Buy buy = new Buy(title, date, sum);

                // переводим объект-покупка в json-формат
                JSONObject json = new JSONObject();
                json.put("title", buy.getTitle());
                json.put("date", buy.getDate());
                json.put("sum", buy.getSum());

                // отправляем покупку в json-формате на сервер
                out.println(json);
                String getMaxCategory = in.readLine();
                getMaxCategory = getMaxCategory.replaceAll(":\\{", ":\n\t\\{");
                getMaxCategory = getMaxCategory.replaceAll("\\},", "\\},\n");
                System.out.println(getMaxCategory);
            }
        }
    }
}