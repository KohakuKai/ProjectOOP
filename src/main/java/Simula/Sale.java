package Simula;

public class Sale extends Transaction {
    private String saleNo;
    private static int currentRecieptNo = 1;

    Sale(Employee employee, Inventory inventory){
        super(employee, inventory);
        this.saleNo = String.format("SN%03d",currentRecieptNo++);
    }

    public String getSaleNo() {
        return saleNo;
    }

    @Override
    public String getTransactionType(){
        return "Sale";
    }
}

