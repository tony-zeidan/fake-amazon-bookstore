package fakeamazon.bookstore.demo.model;

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

    /**
     * Placeholder class for UserRestController to return each book's name and
     * purchase quantity when the user completes a purchase. Prevents having to
     * relay unnecessary data to the application.
     *
     * @param bookName The name of the book purchased
     * @param purchaseQuantity The number of that book purchased
     */
    public PurchaseDetail(String bookName, int purchaseQuantity){
        this.bookName = bookName;
        this.purchaseQuantity = purchaseQuantity;
    }
}
