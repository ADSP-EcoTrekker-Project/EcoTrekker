/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package com.ecotrekker.gridco2cache;

import java.util.concurrent.TimeUnit;

public class App {
    public String getGreeting() {
        return "Hello World!";
    }

    public static void main(String[] args) {
        while (true){
            System.out.println(new App().getGreeting());
            try {
                TimeUnit.SECONDS.sleep(15);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }
}