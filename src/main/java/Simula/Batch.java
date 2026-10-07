package Simula;

import java.time.LocalDate;

public class Batch {
    private String batchID;
    private LocalDate expirationDate;
    private LocalDate dateReceived;
    private int quantity;

    private Product product;

    //mga constructor
    Batch(String batchID,int quantity, LocalDate expirationDate, Product product){
        this.batchID = batchID;
        this.quantity = quantity;
        this.expirationDate = expirationDate;//syntax nito ay LocalDate.of(yy,mm,dd);
        this.product = product;
        this.dateReceived = LocalDate.now(); //kukunin time ngayon
    }

    //mga getter
    public String getBatchID() {
        return batchID;
    }
    public int getQuantity() {
        return quantity;
    }
    public LocalDate getDateReceived() {
        return dateReceived;
    }
    public LocalDate getExpirationDate() {
        return expirationDate;
    }

    public boolean isExpired(){
        return(LocalDate.now().isEqual(expirationDate) || LocalDate.now().isAfter(expirationDate));
        //chinecheck nito kung yung date ngayon ay >= expiration date
    }

    //reduce o increase amount
    public void reduceQuantity(int amount){
        quantity -= amount;
    }
    public void increaseQuantity(int amount){
        quantity += amount;
    }
}
