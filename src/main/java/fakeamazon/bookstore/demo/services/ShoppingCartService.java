package fakeamazon.bookstore.demo.services;

import fakeamazon.bookstore.demo.aop.LoggedServiceOperation;
import fakeamazon.bookstore.demo.exceptions.ShoppingCartAddException;
import fakeamazon.bookstore.demo.exceptions.ShoppingCartEditException;
import fakeamazon.bookstore.demo.input.templates.BookIdTemplate;
import fakeamazon.bookstore.demo.input.templates.BookQuantityTemplate;
import fakeamazon.bookstore.demo.model.*;
import fakeamazon.bookstore.demo.repository.BookRepository;
import fakeamazon.bookstore.demo.repository.ShoppingCartItemRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service

@Transactional
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

    @LoggedServiceOperation
    public List<ShoppingCartItem> getItemsForCustomer(Authentication auth) {
        Customer customer = detailsService.getCustomerDetails(auth);
        return shoppingCartItemRepo.findAllByCustomer_Username(customer.getUsername());
    }

    public Optional<ShoppingCartItem> fromTemplate(Customer customer, BookQuantityTemplate template) {
        Optional<Book> book = bookRepo.findById(template.getId());
        ShoppingCartItem itemConcrete = null;
        if (book.isPresent()) {
            itemConcrete = new ShoppingCartItem();
            itemConcrete.setCustomer(customer);
            itemConcrete.setBook(book.get());
            itemConcrete.setQuantity(template.getQuantity());
        }
        return Optional.ofNullable(itemConcrete);
    }

    @LoggedServiceOperation
    public Optional<ShoppingCartItem> addToCart(Authentication auth, BookQuantityTemplate template) {

        Customer customer = detailsService.getCustomerDetails(auth);
        Optional<ShoppingCartItem> itemMade = fromTemplate(customer, template);

        if (itemMade.isPresent()) {
            ShoppingCartItem itemToFind = itemMade.get();

            ShoppingCartItem item = shoppingCartItemRepo.findShoppingCartItemByBook_IdAndCustomer_Username(template.getId(), customer.getUsername());
            if (item != null) {
                item.setQuantity(item.getQuantity()+itemToFind.getQuantity());
                shoppingCartItemRepo.save(item);
                detailsService.saveCustomer(customer);
                System.out.println("CASE1");
                return Optional.empty();
            } else {
                customer.getCart().add(itemToFind);
                System.out.println("CASE2");
                shoppingCartItemRepo.save(itemToFind);
                detailsService.saveCustomer(customer);
                return Optional.of(itemToFind);
            }
        } else {
            throw new ShoppingCartAddException(template.getId());
        }
    }

    @LoggedServiceOperation
    public Optional<ShoppingCartItem> changeCartAmount(Authentication auth, BookQuantityTemplate template) {
        Customer customer = detailsService.getCustomerDetails(auth);
        ShoppingCartItem item = shoppingCartItemRepo.findShoppingCartItemByBook_IdAndCustomer_Username(template.getId(), customer.getUsername());

        if (item != null) {
            if (item.getQuantity() < Math.abs(template.getQuantity())) {
                if (template.getQuantity() < 0) {
                    throw new ShoppingCartEditException(item.getQuantity(), template.getQuantity());
                } else {
                    item.setQuantity(item.getQuantity() + template.getQuantity());
                    shoppingCartItemRepo.save(item);
                    detailsService.saveCustomer(customer);
                }

            } else if (item.getQuantity() == Math.abs(template.getQuantity())) {
                if (template.getQuantity() < 0) {
                    // delete fully
                    customer.getCart().remove(item);
                    shoppingCartItemRepo.delete(item);
                    detailsService.saveCustomer(customer);
                } else {
                    item.setQuantity(item.getQuantity() + template.getQuantity());
                    shoppingCartItemRepo.save(item);
                    detailsService.saveCustomer(customer);
                }

            } else if (item.getQuantity() > Math.abs(template.getQuantity())) {
                item.setQuantity(item.getQuantity() + template.getQuantity());
                shoppingCartItemRepo.save(item);
                detailsService.saveCustomer(customer);
            }
        } else {
            return Optional.empty();
        }
        return Optional.of(item);
    }

    @LoggedServiceOperation
    public Optional<ShoppingCartItem> removeCartItem(Authentication auth, BookIdTemplate template) {
        Customer customer = detailsService.getCustomerDetails(auth);

        ShoppingCartItem item = shoppingCartItemRepo.findShoppingCartItemByBook_IdAndCustomer_Username(template.getId(), customer.getUsername());
        shoppingCartItemRepo.deleteShoppingCartItemByBook_IdAndCustomer_Username(template.getId(), customer.getUsername());

        return Optional.ofNullable(item);
    }

    @LoggedServiceOperation
    public Customer clearCart(Customer currUser) {
        List<ShoppingCartItem> currCart = currUser.getCart();
        List<ShoppingCartItem> updatedCart = new ArrayList<>(currUser.getCart());
        for (ShoppingCartItem item: updatedCart) {
            currCart.remove(item);
            shoppingCartItemRepo.delete(item);
        }
        currUser.setCart(currCart);
        return currUser;
    }
}
