package ua.lpnu.lab7;

import java.math.BigDecimal;
import java.util.Random;
import java.util.concurrent.Callable;

public class BankThread extends Thread {
    private boolean paused, stopped;
    private final Client client;
    private final Random random;

    public BankThread(Client client) {
        paused = false;
        this.client = client;
        random = new Random();
    }

    public Client getClient() {
        return client;
    }

    @Override
    public void run() {
        while (!currentThread().isInterrupted()) {
            synchronized (this) {
                if(stopped){
                    currentThread().interrupt();
                }
                if (paused) {
                    try {
                        this.wait();
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
                try {
                    if (random.nextInt(0, 2) == 0) {
                        client.depositCash(new BigDecimal(random.nextInt(0, 10)));
//                               System.out.println(client.getName() + " deposited money. Current balance is " + client.getBalance());
                    } else {
                        client.withdrawCash(new BigDecimal(random.nextInt(0, 10)));
//                                System.out.println(client.getName() + " withdrew money. Current balance is " + client.getBalance());
                    }
                } catch (IllegalArgumentException e) {
//                      System.out.println(e.getMessage());
                }
            }
        }
    }

    public boolean isPaused() {
        return paused;
    }

    public void togglePauseness() {
        paused = !paused;
        if (!paused) {
            synchronized (this) {
                this.notifyAll();
            }
        }
    }

    public void stopThread(){
        stopped = true;
    }
}
