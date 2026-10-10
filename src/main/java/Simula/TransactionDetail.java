package Simula;

public class TransactionDetail {


    private static int currentIDNumber = 1;
    private String transactionDetailId;
    private  int quantity;
    private double unitPrice,subTotal;
    private Product product;

    public TransactionDetail( int quantity, double unitPrice, Product product) {
        this.transactionDetailId = String.format("TD&%04d",currentIDNumber++);
        this.quantity = quantity;
        this.unitPrice = unitPrice;
        this.product = product;

        this.subTotal = quantity*unitPrice;
    }

    public String getTransactionDetailId() { return transactionDetailId; }
    public int getQuantity() { return quantity; }
    public  double getUnitPrice() { return unitPrice; }
    public Product getProduct() { return product;}

    public double getSubTotal() { return quantity*unitPrice; }


    public void setQuantity(int newQuantity) { quantity = newQuantity; }
    public void setUnitPrice(int newUnitPrice) { unitPrice = newUnitPrice; }
    public void setProduct(Product newProduct) { product = newProduct; }

}
