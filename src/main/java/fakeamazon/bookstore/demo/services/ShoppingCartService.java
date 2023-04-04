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

    /**
     * Retrieve the shopping cart items currently in the customer's shopping cart.
     *
     * @param auth Spring boot authentication context
     * @return The list of items in the cart
     */
    @LoggedServiceOperation
    public List<ShoppingCartItem> getItemsForCustomer(Authentication auth) {
        Customer customer = detailsService.getCustomerDetails(auth);
        return shoppingCartItemRepo.findAllByCustomer_Username(customer.getUsername());
    }

    /**
     * A function that transforms that makes a shopping cart item for the given customer.
     *
     * @param customer The customer to add the shopping cart item to
     * @param template The user input template containing ID and quantity
     * @return If present, the shopping cart item
     */
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

    /**
     * Add an item to the current user's cart.
     *
     * @param auth Spring boot authentication context
     * @param template User input template containing book quantity and ID
     * @return If present, the new item that was created, and if not this means the book was already present
     */
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

    /**
     * Change the amount of books of a specific shopping cart item in the user's cart.
     *
     * @param auth Spring boot authentication context
     * @param template User input template containing ID and quantity
     * @return Not relevant, tells whether the cart item was added correctly or not
     */
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

    /**
     * Remove an item from the user's cart.
     *
     * @param auth Spring boot authentication context
     * @param template The user input template containing ID
     * @return If present, the item removed, otherwise nothing
     */
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
