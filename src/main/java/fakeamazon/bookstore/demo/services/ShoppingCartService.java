package fakeamazon.bookstore.demo.services;

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
                return Optional.empty();
            } else {
                customer.getCart().add(itemToFind);
                shoppingCartItemRepo.save(itemToFind);
                detailsService.saveCustomer(customer);
                return Optional.of(itemToFind);
            }
        } else {
            throw new ShoppingCartAddException(template.getId());
        }
    }

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


    public Optional<ShoppingCartItem> removeCartItem(Authentication auth, BookIdTemplate template) {
        Customer customer = detailsService.getCustomerDetails(auth);

        ShoppingCartItem item = shoppingCartItemRepo.findShoppingCartItemByBook_IdAndCustomer_Username(template.getId(), customer.getUsername());
        shoppingCartItemRepo.deleteShoppingCartItemByBook_IdAndCustomer_Username(template.getId(), customer.getUsername());

        return Optional.ofNullable(item);
    }
}
