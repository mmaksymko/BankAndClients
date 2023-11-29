package ua.lpnu.lab7;

import java.math.BigDecimal;

public class Bank {
    private BigDecimal totalCash;

    public Bank(BigDecimal totalCash) {
        this.totalCash = totalCash;
    }

    public void withdrawCash(BigDecimal amount) throws IllegalArgumentException {
        synchronized (this) {
            if (totalCash.compareTo(amount) >= 0) {
                totalCash = totalCash.subtract(amount);
            } else throw new IllegalArgumentException("Bank does not have such money!");
        }
    }
    public void depositCash(BigDecimal amount) {
        synchronized (this) {
            totalCash=totalCash.add(amount);
        }
    }

    public BigDecimal getBalance() {
        synchronized (this) {
            return totalCash;
        }
    }

    public void setBalance(BigDecimal totalCash) {
        this.totalCash = totalCash;
    }
}
