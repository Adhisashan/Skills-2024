/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package basicsandthreading;

/**
 *
 * @author adhim
 */
public class MultiThreadExtendThreadClass extends Thread {

    String threadname = "";

    public MultiThreadExtendThreadClass(String name) {
        this.threadname = name;
    }

    public void run() {
        for (int i = 0; i < 3; i++) {
            System.out.println("threadname::" + threadname + "\t" + +i);
            try {
                Thread.sleep(5000); // Sleeping for 5 second
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
