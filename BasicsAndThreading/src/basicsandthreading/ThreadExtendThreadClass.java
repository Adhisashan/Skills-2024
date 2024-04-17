/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package basicsandthreading;

import org.testng.annotations.Test;

/**
 *
 * @author adhim
 */
public class ThreadExtendThreadClass {

    /**
     * @param args the command line arguments
     */
    @Test
    public void singleThread() {
        try {
            
            ThreadExtendExample thread = new ThreadExtendExample();
            thread.start();

            for (int i = 0; i < 5; i++) {
                System.out.println("Main Thread---------: " + i);
                try {
                    Thread.sleep(1000); // Sleeping for 1 second
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
