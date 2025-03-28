package com;

import util.RaceConditionDetector;
import util.Bank;

public class TransferTask implements Runnable {
    private final Bank bank;
    private final int fromId;
    private final int toId;
    private final int amount;

    public TransferTask(Bank bank, int fromId, int toId, int amount) {
        this.bank = bank;
        this.fromId = fromId;
        this.toId = toId;
        this.amount = amount;
    }

    @Override
    public void run() {
        Account from = bank.getAccount(fromId);
        Account to = bank.getAccount(toId);

        // To avoid deadlock, lock the accounts in order based on their id.
        Account firstLock = (from.getId() < to.getId()) ? from : to;
        Account secondLock = (from.getId() < to.getId()) ? to : from;

        synchronized (firstLock) {
            // Simulate race condition detection
            RaceConditionDetector.detectRaceCondition(Thread.currentThread());
            synchronized (secondLock) {
                // Perform transfer if sufficient funds exist
                if (from.getBalance() >= amount) {
                    from.withdraw(amount);
                    to.deposit(amount);
                    System.out.println("Transferred " + amount + " from account " + fromId + " to account " + toId);
                } else {
                    System.out.println("Insufficient funds in account " + fromId);
                }
            }
        }
    }
}

