/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package basicsandthreading;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import org.testng.annotations.Test;

/**
 *
 * @author adhim
 */
public class LockCondition {

    Lock lock = new ReentrantLock();
    Condition condition = lock.newCondition();
    boolean isReady = false;

    @Test
    public void main() {
        try {
            for (int i = 0; i < 10; i++) {
                System.out.println("i+++" + i);
                if (i == 5) {
                    waitForCondition();
                }
            }

        } catch (Exception e) {
        }
    }

    public void waitForCondition() throws InterruptedException {
        lock.lock();
        try {
            while (!isReady) {
                condition.awaitNanos(10000);
                // Causes the Current thread to Wait until the condition is signaled or interrupted
                System.out.println("Condition is met. Proceeding...");
                notifyCondition();
            }
        } finally {
            lock.unlock();
        }
    }

    public void notifyCondition() {
        lock.lock();
        try {
            isReady = true;
            condition.signal(); // Wakes up one waiting thread (Signals waiting threads if the condition is met)
        } finally {
            lock.unlock();
        }
    }
}
