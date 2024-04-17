/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package basicsandthreading;

import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

/**
 *
 * @author adhim
 */
public class BasicsAndThreading implements Runnable {

    /**
     * @param args the command line arguments
     */
    
    @Test
    public void singleThread() {
        try {
            
            Thread thread = new Thread(this);
            thread.start();
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void run() {
        try {
            for (int i = 0; i < 5; i++) {
                System.out.println("Child Thread: " + i);
                try {
                    Thread.sleep(5000); // Sleeping for 1 second
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
