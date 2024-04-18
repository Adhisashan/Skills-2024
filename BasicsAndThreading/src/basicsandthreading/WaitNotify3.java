/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package basicsandthreading;

import java.util.ArrayList;
import org.testng.annotations.Test;

/**
 *
 * @author adhim
 */
public class WaitNotify3 {

    private static final int MAX_SIZE = 5;
    private static ArrayList<Integer> al_docids = new ArrayList<>();
    static ArrayList<Integer> docs = new ArrayList<Integer>();

    public static void main(String[] args) {

        docs.add(101);
        docs.add(102);
        docs.add(103);
        docs.add(104);
        docs.add(105);

        Thread producer = new Thread(() -> {
            while (true) {
                produce();
                try {
                    Thread.sleep(1000); // Simulate some production time
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        Thread consumer = new Thread(() -> {
            while (true) {
                consume();
                try {
                    Thread.sleep(2000); // Simulate some consumption time
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        producer.start();
        consumer.start();
    }

    private static void produce() {
        synchronized (al_docids) {
            System.out.println("al_docids.size()::::" + al_docids.size());
            while (al_docids.size() >= MAX_SIZE) {
                try {
                    System.out.println("Buffer is full. Producer is waiting...");
                    al_docids.wait(); // Wait until al_docids is not full
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            for (int i = 0; i < docs.size(); i++) {
                int data = docs.get(i);
                al_docids.add(data);
                System.out.println("Produced:- " + data);
            }
            al_docids.notify(); // Notify consumer that data is available
        }
    }

    private static void consume() {
        synchronized (al_docids) {
            System.out.println("al_docids.size()--Consume--" + al_docids.size());
            while (al_docids.isEmpty()) {
                try {
                    System.out.println("Buffer is empty. Consumer is waiting...");
                    al_docids.wait(); // Wait until al_docids is not empty
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            int data = al_docids.remove(0);
            System.out.println("Consumed:- " + data);
            al_docids.notify(); // Notify producer that al_docids has space
        }
    }
    
//The ProducerConsumerExample class has a buffer to store data.
//The produce() method is synchronized on the buffer and produces data. If the buffer is full, it waits for the consumer to consume some data before producing more.
//The consume() method is synchronized on the buffer and consumes data. If the buffer is empty, it waits for the producer to produce some data.
//The producer thread produces data and notifies the consumer when data is available.
//The consumer thread consumes data and notifies the producer when the buffer has space
}
