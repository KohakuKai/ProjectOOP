package Simula;

public class Purchase extends Transaction{

    private String purchaseOrderNo,purchaseStatus;
    private static int currentNumber =1;

    Purchase (Employee employee) {
        super(employee);
        this.purchaseOrderNo = String.format("PN%04d",currentNumber++);
        this.purchaseStatus = "PENDING";
    }

    @Override
    public String getTransactionType() {
        return "Transaction";
    }
}
