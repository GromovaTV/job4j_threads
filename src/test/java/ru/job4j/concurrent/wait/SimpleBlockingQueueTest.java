package ru.job4j.concurrent.wait;

import org.junit.Test;

import java.util.*;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

public class SimpleBlockingQueueTest {
    @Test
    public void when6Offer2Poll() throws InterruptedException {
        var queue = new SimpleBlockingQueue<Integer>(4);
        Thread producer = new Thread(
                () -> {
                    System.out.println(Thread.currentThread().getName() + " started");
                    queue.offer(1);
                    queue.offer(2);
                    queue.offer(3);
                    queue.offer(4);
                    queue.offer(5);
                    queue.offer(6);
                },
                "Producer"
        );
        Thread consumer = new Thread(
                () -> {
                    System.out.println(Thread.currentThread().getName() + " started");
                    try {
                        queue.poll();
                        queue.poll();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                },
                "Consumer"
        );
        producer.start();
        consumer.start();
        producer.join();
        consumer.join();
        Queue<Integer> res = queue.getQueue();
        Queue<Integer> exp = new LinkedList<>(List.of(3, 4, 5, 6));
        System.out.println(queue.getQueue());
        assertEquals(exp, res);
    }

}