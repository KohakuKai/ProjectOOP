package Simula;

public class Inventory {
    private String transactionDetailID;
    private int quantity;
    private double unitPrice, subTotal;

    private Product product;
    //private Transaction transaction;
    //wala pa to

    public double calculateSubTotal(){
        return quantity * product.getSellingPrice();
    }

}

//di pa to tapos