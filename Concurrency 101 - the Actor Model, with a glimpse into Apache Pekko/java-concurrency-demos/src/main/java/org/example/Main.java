package org.example;

public class Main {
    static final int NUM_OF_TRANSACTIONS = 1000;
    static final int NUM_OF_ATMS = 3;
    static final long SLEEP_TIME = 3000;
    static int balance = 0;

    static void deposit() {
        balance = balance + 1;
    }

    static void withdraw() {
        balance = balance - 1;
    }

    static void makeTransactions() {
        for (int i = 0; i < NUM_OF_TRANSACTIONS; i++) {
            deposit();
            withdraw();
        }
    }


    public static void main(String[] args) throws InterruptedException {
        for (int i = 0; i < NUM_OF_ATMS; i++) {
            new Thread(Main::makeTransactions).start();
        }
        Thread.sleep(SLEEP_TIME);
        System.out.println(balance);
    }
}