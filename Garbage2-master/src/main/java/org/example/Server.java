package org.example;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Server {
    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(5000)) {
            System.out.println("Сервер слухає порт 5000");

            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Клієнт підключився");

                try (
                        DataInputStream in = new DataInputStream(clientSocket.getInputStream());
                        DataOutputStream out = new DataOutputStream(clientSocket.getOutputStream())
                ) {
                    // Отримуємо метадані
                    String fileName = in.readUTF();
                    int fileSize = in.readInt();
                    String clientHash = in.readUTF();

                    // Якщо файл перевищує 1 кБ
                    if (fileSize > 1024) {
                        out.writeUTF("Файл не відповідає умовам (завеликий)");
                        System.out.println("Файл " + fileName + " відхилено: розмір > 1 кБ");
                        continue;
                    }

                    // Отримуємо файл
                    byte[] fileData = new byte[fileSize];
                    in.readFully(fileData);

                    // Зберігаємо файл
                    File file = new File("received_" + fileName);
                    try (FileOutputStream fos = new FileOutputStream(file)) {
                        fos.write(fileData);
                    }

                    // Обчислюємо хеш на сервері
                    String serverHash = calculateHash(fileData);

                    // Перевіряємо хеші
                    if (!clientHash.equals(serverHash)) {
                        out.writeUTF("Цілісність файлу порушена");
                        System.out.println("Файл " + fileName + ": помилка цілісності");
                        continue;
                    }

                    // Відправляємо файл і хеш назад клієнту
                    out.writeUTF("Файл успішно збережено та перевірено");
                    out.writeInt(fileSize);
                    out.write(fileData);
                    out.writeUTF(serverHash);

                    System.out.println("Файл " + fileName + " збережено та відправлено назад клієнту");
                } catch (Exception e) {
                    System.err.println("Помилка обробки клієнта: " + e.getMessage());
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Метод для обчислення хешу (SHA-256)
    private static String calculateHash(byte[] fileContent) throws NoSuchAlgorithmException {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] hashBytes = digest.digest(fileContent);
        StringBuilder hashString = new StringBuilder();
        for (byte b : hashBytes) {
            hashString.append(String.format("%02x", b));
        }
        return hashString.toString();
    }
}
