package Pharmacy_Invetory_System;

import java.time.LocalDate;
import java.util.ArrayList;

public abstract class Transaction {

    private static int currentIDNumber = 1;
    private final String transactionID;
    private final LocalDate transactionDate;
    private final Staff staff;
    private final Inventory inventory;

    private final static double VAT = 0.12;
    private final static double SENIOR_DISCOUNT = 0.20;
    private final static double PWD_DISCOUNT = 0.20;

    private final ArrayList<TransactionDetail> transactionDetails = new ArrayList<>();

    Transaction(Staff staff, Inventory inventory) {
        this.transactionID = String.format("T%03d", currentIDNumber++);
        this.transactionDate = LocalDate.now();
        this.staff = staff;
        this.inventory = inventory;
    }

    //getter
    public Staff getStaff() {
        return staff;
    }
    public String getTransactionID() { return transactionID;}
    public LocalDate getTransactionDate() {return transactionDate;}

    public abstract String getTransactionType();

    public boolean addTransactionDetail(int quantity, double unitPrice, Product product) {
        if(product == null || quantity <=0 || unitPrice<0)  return false; //makes sure na hindi null yung mga input
        transactionDetails.add(new TransactionDetail(quantity,unitPrice,product)); return true; //eto na yung pag add sa arrayList na <>transactionDetails
    }

    public ArrayList<TransactionDetail> getTransactionDetails() {
        return transactionDetails;
    }

    public double getSubTotalPrice() {
        double total =0;
        for(TransactionDetail td : transactionDetails) {
            total += td.getSubTotal();
        }

        return total;
    }

    public double getVATAmount() {
      return getSubTotalPrice() * VAT;
    }
    public double getTotalWithVAT () {
        return getSubTotalPrice() + getVATAmount();
    }

    public double getPwdDiscount() { return getSubTotalPrice() * PWD_DISCOUNT;}
    public double getTotalWithPwdDiscount() {return getTotalWithVAT() - getPwdDiscount();}

    public double getSeniorDiscount() {return getSubTotalPrice() * SENIOR_DISCOUNT;}
    public double getTotalWithSeniorDiscount() {return getTotalWithVAT() - getSeniorDiscount();}
}
