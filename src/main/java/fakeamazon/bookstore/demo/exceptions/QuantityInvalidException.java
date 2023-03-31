package fakeamazon.bookstore.demo.exceptions;

public class QuantityInvalidException extends RuntimeException{
    public QuantityInvalidException(String bookName){
        super("Book: "+bookName+" - Quantity invalid");
    }
}
