package com;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

// Import your multithreading library classes
import Tharindu_Chanaka.ThreadMonitorApp_Tharindu;
import util.Bank;

public class ConcurrentBankingSystem {

    public static void main(String[] args) {
        // Start thread monitoring in a separate thread from your library.
        new Thread(() -> ThreadMonitorApp_Tharindu.monitorThreads()).start();

        // Create a bank with several accounts.
        Bank bank = new Bank();
        bank.addAccount(new Account(1, 1000));
        bank.addAccount(new Account(2, 2000));
        bank.addAccount(new Account(3, 1500));

        // Create a fixed thread pool to handle concurrent transfers.
        ExecutorService executor = Executors.newFixedThreadPool(10);

        // Submit multiple transfer tasks. These tasks may run concurrently
        // and, if not properly managed, can create contention (or even deadlock scenarios).
        for (int i = 0; i < 20; i++) {
            executor.submit(new TransferTask(bank, 1, 2, 100));
            executor.submit(new TransferTask(bank, 2, 3, 150));
            executor.submit(new TransferTask(bank, 3, 1, 200));
        }

        // Shutdown the executor service gracefully.
        executor.shutdown();
    }
}
