package Simula;

import java.time.LocalDate;
import java.util.ArrayList;

public class Purchase extends Transaction{

    private String purchaseOrderNo,purchaseStatus;
    private static int currentNumber =1;
    private ArrayList<LocalDate> expirationDates = new ArrayList<>();  //kaya may ganto sa PURCHASE at wala sa SALE, dahil need itake into account yung expiration dates pag bibilli ng mga gamot
    private final double BULKDISCOUNT = 0.20;


    Purchase (Employee employee, Inventory inventory) {
        super(employee, inventory);
        this.purchaseOrderNo = String.format("PN%03d",currentNumber++);
    }

    public boolean addPurchaseLine(int quantity, double unitPurchaseCost, Product product, LocalDate expirationDate) { //parang addTransactionDetail lang to

        if(expirationDate == null ||!expirationDate.isAfter(LocalDate.now())) return false; //pagka walang expiration or lagpas na yung expiration sa araw ngayon
        if(!addTransactionDetail(quantity, unitPurchaseCost, product)) return false;
        expirationDates.add(expirationDate); return true; //i aadd na sa arrayList ng Expiration date

    }

    public ArrayList<LocalDate> getExpirationDates() {return expirationDates;}
    public String getPurchaseOrderNo() { return purchaseOrderNo; }
    public String getPurchaseStatus() { return purchaseStatus; }

    public double getBULKDISCOUNT() {return  BULKDISCOUNT;}



    @Override
    public String getTransactionType() {
        return "Purchase";
    }
}
