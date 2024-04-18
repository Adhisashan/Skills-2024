/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package basicsandthreading;

import java.util.concurrent.ThreadLocalRandom;
import org.testng.annotations.Test;

/**
 *
 * @author adhim
 */
public class WaitNotify1 {

    @Test
    public void main() {
        Data data = new Data();
        Thread sender = new Thread(new Sender(data), "Sender");
        Thread receiver = new Thread(new Receiver(data), "Receiver");

        sender.start();
        receiver.start();
    }

    public class Sender implements Runnable {

        private Data data;

        public Sender(Data data) {
            this.data = data;
        }

        // standard constructors
        public void run() {
            System.out.println("Sender thread name: " + Thread.currentThread().getName());
            String doc_ids[] = {
                "First doc_id",
                "Second doc_id",
                "End",
                "Third doc_id",
                "Fourth doc_id"
                
            };

            for (String ld_doc_id : doc_ids) {
                data.send(ld_doc_id);
//The Sender uses the send() method to send data to the Receiver:
//If transfer is false, we’ll wait by calling wait() on this thread.
//But when it is true, we toggle the status, set our message, and call notifyAll() to wake up other threads to specify that a significant event has occurred and they can check if they can continue execution.

                try {
                    Thread.sleep(ThreadLocalRandom.current().nextInt(1000, 5000));
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    System.err.println("Thread Interrupted");
                }
            }
        }
    }

    public class Receiver implements Runnable {

        private Data load;

        public Receiver(Data load) {
            this.load = load;
        }

        // standard constructors
        public void run() {
            System.out.println("Receiver thread name: " + Thread.currentThread().getName());

//Similarly, the Receiver will use the receive() method:
//If the transfer was set to false by Sender, only then will it proceed, otherwise we’ll call wait() on this thread.
//When the condition is met, we toggle the status, notify all waiting threads to wake up, and return the data packet that was received.
            for (String receivedMessage = load.receive();
                    !"End".equals(receivedMessage);
                    receivedMessage = load.receive()) {

                System.out.println(receivedMessage);

                //Thread.sleep() to mimic heavy server-side processing
                try {
                    Thread.sleep(ThreadLocalRandom.current().nextInt(1000, 5000));
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    System.err.println("Thread Interrupted");
                }
            }
        }
    }

    public class Data {

        private String ld_doc_id;

        // True if receiver should wait
        // False if sender should wait
        private boolean transfer = true;

        //We have a boolean variable transfer, which the Sender and Receiver will use for synchronization:
        //If this variable is true, the Receiver should wait for Sender to send the message.
        //If it’s false, Sender should wait for Receiver to receive the message.
        public synchronized void send(String ld_doc_id) {
            while (!transfer) {
                try {
                    wait();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    System.err.println("Thread Interrupted");
                }
            }
            transfer = false;

            this.ld_doc_id = ld_doc_id;
            notifyAll();
        }

        public synchronized String receive() {
            while (transfer) {
                try {
                    wait();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    System.err.println("Thread Interrupted");
                }
            }
            transfer = true;

            String returnPacket = ld_doc_id;
            notifyAll();
            return returnPacket;
        }

    }
}
