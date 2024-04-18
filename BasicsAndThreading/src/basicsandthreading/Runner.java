/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package basicsandthreading;

import org.testng.TestNG;
/**
 *
 * @author adhim
 */
public class Runner {
    
    static TestNG testNg;

    public static void main(String[] args) {
        testNg = new TestNG();
        testNg.setTestClasses(new Class[]{LockCondition.class});
        testNg.run();
    }
}
