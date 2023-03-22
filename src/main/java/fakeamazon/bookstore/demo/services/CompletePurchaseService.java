package fakeamazon.bookstore.demo.services;

import fakeamazon.bookstore.demo.model.*;
import fakeamazon.bookstore.demo.repository.PurchaseHistoryRepository;

import java.util.*;

import fakeamazon.bookstore.demo.repository.PurchaseItemRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class CompletePurchaseService {

    private final PurchaseItemRepository purchaseItemRepository;
    private final PurchaseHistoryRepository purchaseHistoryRepository;

    private final BookOwnerInventoryService inventoryService;

    private final CustomerDetailsService userService;

    private final ShoppingCartService cartService;

    @Autowired
    public CompletePurchaseService(CustomerDetailsService userService, ShoppingCartService cartService, PurchaseHistoryRepository pHRepo, PurchaseItemRepository pIRepo, BookOwnerInventoryService inventoryService){
        this.purchaseHistoryRepository = pHRepo;
        this.purchaseItemRepository = pIRepo;
        this.inventoryService = inventoryService;
        this.userService = userService;
        this.cartService = cartService;
    }

    public Optional<PurchaseHistory> completePurchase(Authentication auth){
        //Check inventory first
        Customer currUser = userService.getCustomerDetails(auth);
        PurchaseHistory currPurchaseHistory = currUser.getHistory();
        List<ShoppingCartItem> cart = currUser.getCart();

        if(cart.isEmpty()){return Optional.empty();}

        List<PurchaseItem> newPurchaseItems = new ArrayList<>();

        //Have to make sure all items in the cart are valid before updating the quantities
        for (ShoppingCartItem item: cart){
            int itemQuantity = item.getQuantity();
            Book itemBook = item.getBook();
            int bookQuantity = itemBook.getQuantity();

            if (itemQuantity > bookQuantity){
                throw new NoSuchElementException();
            }
        }

        for (ShoppingCartItem item : cart){
            int itemQuantity = item.getQuantity();
            Book itemBook = item.getBook();
            int bookQuantity = itemBook.getQuantity();

            Optional<Book> quantityResponse = inventoryService.updateQuantity(itemBook.getId(), bookQuantity - itemQuantity);

            //Book not found for some reason, therefore quantity not updated
            if (!quantityResponse.isPresent()){
                throw new NoSuchElementException();
            } else {
                //Not saving the purchase item to the repo and purchase history right away because the entire transaction should be invalid if one of them fails
                PurchaseItem newPurchaseItem = new PurchaseItem();
                newPurchaseItem.setBook(itemBook);
                newPurchaseItem.setQuantity(itemQuantity);
                newPurchaseItem.setCreatedAt(new Date());

                newPurchaseItems.add(newPurchaseItem);
            }
        }

        for (PurchaseItem pItem: newPurchaseItems){
            purchaseItemRepository.save(pItem);
            currPurchaseHistory.addToHistory(pItem);
        }

        currUser.setHistory(currPurchaseHistory);

        purchaseHistoryRepository.save(currPurchaseHistory);

        currUser = cartService.clearCart(currUser);

        userService.saveCustomer(currUser);

        return Optional.of(currPurchaseHistory);
    }
}
