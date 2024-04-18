/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package basicsandthreading;

import org.testng.annotations.Test;

/**
 *
 * @author adhim
 */
public class MultiThreadingExtendExample {
    
    @Test
    public void MultiThread() {
        try {
            MultiThreadExtendThreadClass thread_1 = new MultiThreadExtendThreadClass("MultiThread 1");
            MultiThreadExtendThreadClass thread_2 = new MultiThreadExtendThreadClass("MultiThread 2");
            thread_1.start();
            thread_2.start();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
