package org.example;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    private static final int PORT = 5000;
    private static final int MAX_FILE_SIZE = 201700; // 1 KB

    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("Server listening on port " + PORT);

            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Client connected");

                handleClient(clientSocket);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void handleClient(Socket clientSocket) {
        try (
                DataInputStream in = new DataInputStream(clientSocket.getInputStream());
                DataOutputStream out = new DataOutputStream(clientSocket.getOutputStream())
        ) {
            // Отримання метаданих файлу
            String fileName = in.readUTF();
            int fileSize = in.readInt();

            // Перевірка розміру файлу
            if (fileSize <= MAX_FILE_SIZE) {
                // Отримання файлу
                byte[] fileData = new byte[fileSize];
                in.readFully(fileData);

                // Збереження файлу на сервері
                File file = new File(fileName);
                try (FileOutputStream fos = new FileOutputStream(file)) {
                    fos.write(fileData);
                }
                System.out.println("File saved on server: " + fileName);

                // Відправка підтвердження успішного збереження та файлу назад клієнту
                out.writeUTF("Файл успішно збережено.");
                out.writeInt(fileSize);
                out.write(fileData);
            } else {
                // Відправка повідомлення про помилку, якщо файл завеликий
                out.writeUTF("Файл не задовільняє умовам (розмір більше 1 КБ).");
                System.out.println("File " + fileName + " не задовільняє умовам і не був збережений.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                clientSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
