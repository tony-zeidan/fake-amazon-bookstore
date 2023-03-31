package fakeamazon.bookstore.demo.exceptions;

public class NoSuchBookException extends RuntimeException{
    public NoSuchBookException(String bookName){
        super("Book: "+bookName+" - Not found");
    }
}
