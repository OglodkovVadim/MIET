package Lab7;

public class Main {
    public static void main(String[] args) {
        SharedData sharedData = new SharedData();

        // Пример использования методов в разных потоках
        Thread readerThread1 = new Thread(() -> {
            sharedData.read();
        }, "ReaderThread-1");

        Thread readerThread2 = new Thread(() -> {
            sharedData.read();
        }, "ReaderThread-2");

        Thread readerThread3 = new Thread(() -> {
            sharedData.read();
        }, "ReaderThread-3");

        Thread readerThread4 = new Thread(() -> {
            sharedData.read();
        }, "ReaderThread-4");

        Thread writerThread1 = new Thread(() -> {
            sharedData.write("Data-1");
        }, "WriterThread-1");

        Thread writerThread2 = new Thread(() -> {
            sharedData.write("Data-1");
        }, "WriterThread-2");

        readerThread1.start();
        readerThread2.start();
        writerThread1.start();
        readerThread3.start();
        readerThread4.start();
        writerThread2.start();
    }
}