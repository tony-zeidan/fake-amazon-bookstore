package fakeamazon.bookstore.demo.services;

import fakeamazon.bookstore.demo.exceptions.ShoppingCartAddException;
import fakeamazon.bookstore.demo.exceptions.ShoppingCartEditException;
import fakeamazon.bookstore.demo.input.templates.BookIdTemplate;
import fakeamazon.bookstore.demo.input.templates.BookQuantityTemplate;
import fakeamazon.bookstore.demo.model.*;
import fakeamazon.bookstore.demo.repository.BookRepository;
import fakeamazon.bookstore.demo.repository.ShoppingCartItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ShoppingCartService {

    private final CustomerDetailsService detailsService;
    private final BookRepository bookRepo;

    private final ShoppingCartItemRepository shoppingCartItemRepo;

    @Autowired
    public ShoppingCartService(CustomerDetailsService detailsService, BookRepository bookRepo, ShoppingCartItemRepository shoppingCartItemRepo) {
        this.detailsService = detailsService;
        this.bookRepo = bookRepo;
        this.shoppingCartItemRepo = shoppingCartItemRepo;
    }

    public Optional<ShoppingCartItem> fromTemplate(BookQuantityTemplate template) {
        Optional<Book> book = bookRepo.findById(template.getId());
        ShoppingCartItem itemConcrete = null;
        if (book.isPresent()) {
            itemConcrete = new ShoppingCartItem();
            itemConcrete.setBook(book.get());
            itemConcrete.setQuantity(template.getQuantity());
        }
        return Optional.ofNullable(itemConcrete);
    }

    public Optional<ShoppingCartItem> addToCart(Authentication auth, BookQuantityTemplate template) {

        Customer customer = detailsService.getCustomerDetails(auth);

        Optional<ShoppingCartItem> itemMade = fromTemplate(template);
        if (itemMade.isPresent()) {
            ShoppingCartItem item = null;
            ShoppingCartItem itemToFind = itemMade.get();
            boolean found = false;

            // look for a duplicate book for adding collisions
            for (ShoppingCartItem it: customer.getCart()) {

                // if a duplicate book is found, simply add to the quantity
                if (it.getBook().equals(itemToFind.getBook())) {
                    found = true;
                    it.setQuantity(it.getQuantity()+itemToFind.getQuantity());
                    shoppingCartItemRepo.save(it);
                    break;
                }
            }

            if (!found) {
                customer.getCart().add(itemToFind);
                item = shoppingCartItemRepo.save(itemToFind);
            }
            detailsService.saveCustomer(customer);
            return Optional.ofNullable(item);
        } else {
            throw new ShoppingCartAddException(template.getId());
        }
    }

    public Optional<ShoppingCartItem> changeCartAmount(Authentication auth, BookQuantityTemplate template) {
        Customer customer = detailsService.getCustomerDetails(auth);
        ShoppingCartItem found = null;
        ShoppingCartItem rm = null;
        for (ShoppingCartItem item : customer.getCart()) {
            if (item.getBook().getId() == template.getId()) {
                found = item;
                if (item.getQuantity() < Math.abs(template.getQuantity())) {
                    if (template.getQuantity() < 0) {
                        throw new ShoppingCartEditException(item.getQuantity(), template.getQuantity());
                    } else {
                        item.setQuantity(item.getQuantity() + template.getQuantity());
                        shoppingCartItemRepo.save(item);
                    }
                    break;

                } else if (item.getQuantity() == Math.abs(template.getQuantity())) {
                    if (template.getQuantity() < 0) {
                        // delete fully
                        rm = item;
                    } else {
                        item.setQuantity(item.getQuantity() + template.getQuantity());
                        shoppingCartItemRepo.save(item);
                    }
                    break;

                } else if (item.getQuantity() > Math.abs(template.getQuantity())) {
                    item.setQuantity(item.getQuantity() + template.getQuantity());
                    shoppingCartItemRepo.save(item);
                    break;
                }
                break;
            }
        }
        // the item is to be deleted fully
        if (rm != null) {
            customer.getCart().remove(rm);
            shoppingCartItemRepo.delete(rm);
        }
        detailsService.saveCustomer(customer);
        return Optional.ofNullable(found);
    }

    public Optional<ShoppingCartItem> removeCartItem(Authentication auth, BookIdTemplate template) {
        Customer customer = detailsService.getCustomerDetails(auth);
        List<ShoppingCartItem> cart = customer.getCart();
        ShoppingCartItem found = null;
        for (ShoppingCartItem item : cart) {
            if (item.getBook().getId() == template.getId()) {
                found = item;
                break;
            }
        }
        if (found != null) {
            customer.getCart().remove(found);
            shoppingCartItemRepo.delete(found);
        }
        detailsService.saveCustomer(customer);
        return Optional.ofNullable(found);
    }
}
