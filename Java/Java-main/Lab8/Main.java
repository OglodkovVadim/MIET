package Lab8;

import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class Main {

    private static final String POP3_SERVER = "pop.mail.ru";
    private static final int POP3_PORT = 995;
    private static final String USERNAME = "javalovelab@mail.ru";
    private static final String PASSWORD = "Fqm10UhDXHmWEL2cLYke";

    public static void main(String[] args) {
        try (SSLSocket socket = (SSLSocket) SSLSocketFactory.getDefault().createSocket(POP3_SERVER, POP3_PORT);
             BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream(), "UTF-8"));
             BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream(), "UTF-8"))) {

            // Читаем приветственное сообщение от сервера
            String response = reader.readLine();

            // Отправляем команду USER
            sendCommand(writer, "USER " + USERNAME);
            response = reader.readLine();

            // Отправляем команду PASS
            sendCommand(writer, "PASS " + PASSWORD);
            response = reader.readLine();

            // Отправляем команду STAT для получения статистики по почтовому ящику
            sendCommand(writer, "STAT");
            response = reader.readLine();

            // Отправляем команду LIST для получения списка писем
            sendCommand(writer, "LIST");
            response = reader.readLine();
            System.out.println(response);

            // Парсим список писем и получаем их количество
            int numEmails = Integer.parseInt(response.split(" ")[1]);

            // Отправляем команды RETR для получения содержимого каждого письма
            for (int i = 1; i <= numEmails; i++) {
                sendCommand(writer, "RETR " + i);

                // Чтение заголовков
                while (!(response = reader.readLine()).isEmpty()) {
                    // Здесь вы можете обработать заголовки письма, если это необходимо
                }

                // Чтение тела письма
                String emailBody = new String();
                while (!(response = reader.readLine()).equals(".")) {
                    emailBody += response;
                }

                // Вывод тела письма
                System.out.println("Email Body:\n" + emailBody);
            }

            // Отправляем команду QUIT для завершения соединения
            sendCommand(writer, "QUIT");
            response = reader.readLine();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void sendCommand(BufferedWriter writer, String command) throws IOException {
        writer.write(command + "\r\n");
        writer.flush();
    }
}