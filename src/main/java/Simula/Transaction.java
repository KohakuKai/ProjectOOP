package Simula;

import java.time.LocalDate;
import java.util.ArrayList;

public abstract class Transaction {

    private static int currentIDNumber = 1;
    private String transactionID;
    private LocalDate transactionDate;
    private Employee employee;

    private final static double VAT = 0.12;
    private final static double SENIOR_DISCOUNT = 0.20;
    private final static double PWD_DISCOUNT = 0.20;

    private ArrayList<TransactionDetail> transactionDetails = new ArrayList<>();

    Transaction( Employee employee) {
        this.transactionID = String.format("T%04d", currentIDNumber++);
        this.transactionDate = LocalDate.now();
        this.employee = employee;
    }

    public abstract String getTransactionType();

    public boolean addTransactionDetail(int quantity, double unitPrice, Product product) {
        if(product == null || quantity <=0 || unitPrice<0)  return false;

        transactionDetails.add(new TransactionDetail(quantity,unitPrice,product)); return true;
    }

    public ArrayList<TransactionDetail> viewTransactionDetails() {
        return transactionDetails;
    }

    public double getTotalPrice() {
        double total =0;
        for(TransactionDetail td : transactionDetails) {
            total += td.getSubTotal();
        }
        return total;
    }

}
