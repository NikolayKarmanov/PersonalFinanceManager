package ru.netology;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) throws ParseException {
        List<Buy> basket = new ArrayList<>();
        File file = new File("data.bin");
        // проверяем существует ли файл data.bin
        if (file.exists()) {
            // если существует, то создаем корзину на основе данных из файла
            basket = loadFromBinFile(file);
        }

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
                    saveToBin(basket);
                    String str = MaxCategory.getMaxCategory(basket, date);
                    out.println(str);
                }
            }
        } catch (IOException e) {
            System.out.println("Не могу стартовать сервер");
            e.printStackTrace();
        }
    }

    // метод для загрузки корзины из бинарного файла
    static List<Buy> loadFromBinFile(File file) {
        List<Buy> basket = null;
        try (FileInputStream fis = new FileInputStream(file);
             ObjectInputStream ois = new ObjectInputStream(fis)) {
            basket = (List<Buy>) ois.readObject();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        return basket;
    }

    public static void saveToBin(List<Buy> basket) {
        try (FileOutputStream fos = new FileOutputStream("data.bin");
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            oos.writeObject(basket);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }
}


