/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package basicsandthreading;

import java.lang.management.ManagementFactory;
import java.lang.management.ThreadInfo;
import java.lang.management.ThreadMXBean;
import org.testng.annotations.Test;

/**
 *
 * @author adhim
 */
public class MultiThreading implements Runnable {

    /**
     * @param args the command line arguments
     */
    @Test
    public void MultiThread() {
        try {
            int i_maxthreads = 4;
            for (int i = 0; i < i_maxthreads; i++) {
                Thread thread_1 = new Thread(this, "Thread " + i);
                thread_1.start();
            }

//            Thread thread_1 = new Thread(this, "Thread 1");
//            Thread thread_2 = new Thread(this, "Thread 2");
//            Thread thread_3 = new Thread(this, "Thread 3");
//
//            // Set the priority of thread1 to maximum
////            thread_2.setPriority(Thread.MAX_PRIORITY);
////            thread_1.setPriority(Thread.MIN_PRIORITY);
//            thread_1.start();
//            thread_2.start();
//            thread_3.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void run() {
        try {
            for (int i = 0; i < 5; i++) {
                System.out.println(Thread.currentThread().getName() + " \t" + i);
//                System.out.println(Thread.currentThread().getName() + " \t" + i + "\tPriority: " + Thread.currentThread().getPriority());
                try {
                    Thread.sleep(5000); // Sleeping for 1 second
//                    ThreadStatus(Thread.currentThread().getName());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void ThreadStatus(String ThreadName) {
        try {
            ThreadMXBean threadMXBean = ManagementFactory.getThreadMXBean();
            long[] threadIds = threadMXBean.getAllThreadIds();
            for (long threadId : threadIds) {
                ThreadInfo threadInfo = threadMXBean.getThreadInfo(threadId);
                if (threadInfo != null) {
                    Thread.State state = threadInfo.getThreadState();
                    if (state == Thread.State.TERMINATED) {
                        System.out.println("Thread " + ThreadName + " is TERMINATED.");
                    }
                    if (state == Thread.State.BLOCKED) {
                        System.out.println("Thread " + ThreadName + " is BLOCKED.");
                    }
                    if (state == Thread.State.WAITING) {
                        System.out.println("Thread " + ThreadName + " is WAITING.");
                    }
                    if (threadInfo.getLockName() != null) {
                        System.out.println("Thread " + ThreadName + " is holding a lock on " + threadInfo.getLockName());
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
