/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package basicsandthreading;

/**
 *
 * @author adhim
 */
public class ThreadExtendExample extends Thread {

    public void run() {
        for (int i = 0; i < 5; i++) {
            System.out.println("Child Thread: " + i);
            try {
                Thread.sleep(1000); // Sleeping for 1 second
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
