package fakeamazon.bookstore.demo.model;

/**
 * Placeholder class for controller to return each book's name and
 * purchase quantity when the user completes a purchase.
 */
public class PurchaseDetail {
    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public int getPurchaseQuantity() {
        return purchaseQuantity;
    }

    public void setPurchaseQuantity(int purchaseQuantity) {
        this.purchaseQuantity = purchaseQuantity;
    }

    private String bookName;

    private int purchaseQuantity;

    public PurchaseDetail(String bookName, int purchaseQuantity){
        this.bookName = bookName;
        this.purchaseQuantity = purchaseQuantity;
    }
}
