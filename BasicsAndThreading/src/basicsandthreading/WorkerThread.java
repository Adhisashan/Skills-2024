/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package basicsandthreading;

/**
 *
 * @author adhim
 */
public class WorkerThread implements Runnable {

    private String Instance;

    public WorkerThread(String s) {
        this.Instance = s;
    }

    @Override
    public void run() {
        System.out.println(Thread.currentThread().getName() + " Start. Instance = " + Instance);
        processCommand();
        System.out.println(Thread.currentThread().getName() + " End.");
    }

    private void processCommand() {
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
