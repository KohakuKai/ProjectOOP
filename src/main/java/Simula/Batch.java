package Simula;

import java.time.LocalDate;

public class Batch {
    private String batchID;
    private LocalDate expirationDate;
    private LocalDate dateReceived;
    private int quantity;

    // product attribute dito

    //mga constructor
    Batch(String batchID, LocalDate expirationDate, int quantity){
        this.batchID = batchID;
        this.expirationDate = expirationDate;   //syntax nito ay LocalDate.of(yy,mm,dd);
        this.quantity = quantity;
        dateReceived = LocalDate.now(); //kukunin time ngayon
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
