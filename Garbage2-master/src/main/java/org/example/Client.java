package org.example;

import java.io.*;
import java.net.Socket;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Client {
    private static final String SERVER_HOST = "localhost";
    private static final int SERVER_PORT = 5000;

    public static void main(String[] args) {
        String filePath = "test_file.txt"; // Вкажіть шлях до файлу

        try (Socket socket = new Socket(SERVER_HOST, SERVER_PORT)) {
            System.out.println("Підключення до сервера...");

            try (
                    DataOutputStream out = new DataOutputStream(socket.getOutputStream());
                    DataInputStream in = new DataInputStream(socket.getInputStream())
            ) {
                // Зчитування файлу
                File file = new File(filePath);
                if (!file.exists()) {
                    System.out.println("Файл не  знайдено: " + filePath);
                    return;
                }

                String fileName = file.getName();
                int fileSize = (int) file.length();
                byte[] fileData = new byte[fileSize];

                try (FileInputStream fis = new FileInputStream(file)) {
                    fis.read(fileData);
                }

                // Обчислюємо хеш файлу
                String fileHash = calculateHash(fileData);

                // Відправка метаданих на сервер
                out.writeUTF(fileName);
                out.writeInt(fileSize);
                out.writeUTF(fileHash);

                // Отримуємо відповідь від сервера
                String serverResponse = in.readUTF();
                System.out.println("Відповідь сервера: " + serverResponse);

                if (serverResponse.contains("успішно")) {
                    // Отримання файлу назад від сервера
                    int receivedSize = in.readInt();
                    byte[] receivedData = new byte[receivedSize];
                    in.readFully(receivedData);
                    String serverHash = in.readUTF();

                    // Зберігаємо файл
                    try (FileOutputStream fos = new FileOutputStream("received_" + fileName)) {
                        fos.write(receivedData);
                    }

                    // Перевіряємо цілісність
                    if (fileHash.equals(serverHash)) {
                        System.out.println("Файл отримано успішно. Цілісність підтверджено.");
                        saveTransferStatus(fileName, fileSize, "Успішно збережено та перевірено");
                    } else {
                        System.out.println("Помилка: Хеші не збігаються!");
                        saveTransferStatus(fileName, fileSize, "Помилка цілісності");
                    }
                } else {
                    saveTransferStatus(fileName, fileSize, "Передача не вдалася: " + serverResponse);
                }
            }
        } catch (IOException | NoSuchAlgorithmException e) {
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

    // Метод для збереження статусу передачі
    private static void saveTransferStatus(String fileName, int fileSize, String status) {
        String log = "Файл: " + fileName + ", Розмір: " + fileSize + " байт, Статус: " + status;
        System.out.println("Статус передачі: " + log);

        try (FileWriter writer = new FileWriter("transfer_status.txt", true)) {
            writer.write(log + System.lineSeparator());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
