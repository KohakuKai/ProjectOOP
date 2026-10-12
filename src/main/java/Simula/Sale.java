package Simula;

public class Sale extends Transaction {
    private String receiptNo;
    private static int currentRecieptNo = 1;

    Sale(Employee employee, Inventory inventory){
        super(employee, inventory);
        this.receiptNo = String.format("PN%04d",currentRecieptNo++);
    }

    public String getReceiptNo() {
        return receiptNo;
    }

    @Override
    public String getTransactionType(){
        return "Sale";
    }
}
