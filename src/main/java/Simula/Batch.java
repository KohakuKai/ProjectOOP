package Simula;

import java.time.LocalDate;

public class Batch {

    private static int currentIDNumber = 1;

    private String batchID;
    private LocalDate expirationDate;
    private LocalDate dateReceived;
    private int quantity;

    private static int warningDays = 30;
    private Product product;

    //mga constructor
    Batch(int quantity,Product product, LocalDate expirationDate){
        this.batchID = String.format("B%04d",currentIDNumber++);
        this.quantity = quantity;
        this.product = product;
        this.expirationDate = expirationDate;//syntax nito ay LocalDate.of(yy,mm,dd);

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

    //mga setter
    public void setDateReceived(LocalDate dateReceived) {
        this.dateReceived = dateReceived;
    }
    public void setExpirationDate(LocalDate expirationDate) {
        this.expirationDate = expirationDate;
    }

    //taga check expiration date
    public boolean isExpired(){
        return(LocalDate.now().isEqual(expirationDate) || LocalDate.now().isAfter(expirationDate));
        //chinecheck nito kung yung date ngayon ay >= expiration date
    }

    public boolean isNearExpiration() {
        return(!isExpired() && !expirationDate.isAfter(LocalDate.now().plusDays(warningDays)));
    }

    //reduce or increase amount
    public boolean reduceQuantity(int amount) {
        if (amount > quantity || amount <= 0) return false;

        quantity -= amount; return true;
    }

    public boolean increaseQuantity(int amount){
        if(amount<=0) return false;

        quantity += amount; return true;
    }
}
