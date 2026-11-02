package Pharmacy_Invetory_System.transaction;

import Pharmacy_Invetory_System.inventory.Inventory;
import Pharmacy_Invetory_System.employees.Staff;
import Pharmacy_Invetory_System.product.Product;

public class Sale extends Transaction {
    private final String saleNo;
    private static int currentRecieptNo = 1;

    public Sale(Staff staff, Inventory inventory){
        super(staff, inventory);
        this.saleNo = String.format("SN%03d",currentRecieptNo++);
    }

    public String getSaleNo() {
        return saleNo;
    }

    @Override
    public String getTransactionType(){
        return "Sale";
    }

    @Override
    public boolean addTransactionDetail(int quantity, double unitPrice, Product product) {
        if (product == null || quantity <= 0 || unitPrice < 0) {
            return false;
        }

        for (TransactionDetail detail: getTransactionDetails()) {
            if (detail.getProduct() == product && detail.getUnitPrice() == unitPrice) {
                detail.setQuantity(detail.getQuantity() +  quantity);
                return true;
            }
        }

        return super.addTransactionDetail(quantity, unitPrice, product);
    }
}

