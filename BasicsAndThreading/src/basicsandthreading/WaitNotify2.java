/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package basicsandthreading;

/**
 *
 * @author adhim
 */
import java.util.LinkedList;
import java.util.Queue;

public class WaitNotify2 {
    
    public static void main(String[] args) {
        Restaurant restaurant = new Restaurant();
        Chef chef = new Chef(restaurant);
        Waiter waiter1 = new Waiter(restaurant);
        Waiter waiter2 = new Waiter(restaurant);

        chef.start();
        waiter1.start();
        waiter2.start();
    }
}

class Restaurant {

    private final int MAX_ORDERS = 5;
    private Queue<Integer> orders = new LinkedList<>();

    public synchronized void placeOrder(int orderNumber) {
        while (orders.size() >= MAX_ORDERS) {
            System.out.println("Kitchen: Order queue is full. Waiting for orders to be served...");
            try {
                wait(2000); // Wait until some orders are served
                serveOrder();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        orders.offer(orderNumber);
        System.out.println("Kitchen: Order " + orderNumber + " is placed.");
        System.out.println("---------" + orders.size());
        notify(); // Notify waiter that an order is ready
    }

    public synchronized int serveOrder() {
        while (orders.isEmpty()) {
            System.out.println("Waiter: No orders to serve. Waiting for orders to be placed...");
            try {
                wait(); // Wait until some orders are placed

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        int servedOrder = orders.poll();
        System.out.println("Waiter: Order " + servedOrder + " is served.");
        notify(); // Notify kitchen that an order is served
        return servedOrder;
    }
}

class Waiter extends Thread {

    private Restaurant restaurant;

    public Waiter(Restaurant restaurant) {
        this.restaurant = restaurant;
    }

    public void run() {
        while (true) {
//            restaurant.serveOrder();
            try {
                Thread.sleep(2000); // Simulate time taken to serve an order
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

class Chef extends Thread {

    private Restaurant restaurant;

    public Chef(Restaurant restaurant) {
        this.restaurant = restaurant;
    }

    public void run() {
        int orderNumber = 1;
        while (orderNumber <= 10) {
            restaurant.placeOrder(orderNumber++);
            try {
                Thread.sleep(3000); // Simulate time taken to prepare an order
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

//The Restaurant class represents the kitchen of the restaurant, where orders are placed and served.
//The placeOrder() method is synchronized and is called by the chef to place an order.
//The serveOrder() method is synchronized and is called by waiters to serve an order.
//The Waiter class represents a waiter who serves orders.
//The Chef class represents the chef who prepares orders.
//When the kitchen is full (i.e., maximum orders reached), the chef waits until some orders are served (wait()).
//When there are no orders to serve, the waiter waits until some orders are placed (wait()).
//When an order is placed or served, the respective thread notifies the other thread (notify()).
//We simulate order preparation and serving times with Thread.sleep().
//This example demonstrates how wait(), notify(), and notifyAll() can be used for inter-thread communication and synchronization in a real-world scenario.
