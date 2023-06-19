package ru.job4j.thread;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;

public class Wget implements Runnable {
    private final String url;
    private final int speed;

    public Wget(String url, int speed) {
        this.url = url;
        this.speed = speed;
    }

    @Override
    public void run() {
        String[] splt = url.split("/");
        String outPath = "tmp_".concat(splt[splt.length - 1]);
        try (BufferedInputStream in = new BufferedInputStream(new URL(url).openStream());
             FileOutputStream fileOutputStream = new FileOutputStream(outPath)) {
            byte[] dataBuffer = new byte[1024];
            int bytesRead;
            int index = 0;
            String[] process = {"-", "\\", "|", "/"};
            long start = System.currentTimeMillis();
            while ((bytesRead = in.read(dataBuffer, 0, 1024)) != -1) {
                System.out.print("\r load: " + process[index++ % 4]);
                fileOutputStream.write(dataBuffer, 0, bytesRead);
                long finish = System.currentTimeMillis();
                long time = finish - start;
                if (time < speed) {
                    Thread.sleep(speed - time);
                }
                start = System.currentTimeMillis();
            }
            System.out.print("\rLoading completed.");
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        if (args.length != 2) {
            throw new IllegalArgumentException("Insufficient number of input parameters Usage java PATH TIME.");
        }
        String url = args[0];
        int speed = Integer.parseInt(args[1]);
        Thread wget = new Thread(new Wget(url, speed));
        wget.start();
        wget.join();
    }
}