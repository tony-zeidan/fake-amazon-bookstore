package fakeamazon.bookstore.demo.controller;

import fakeamazon.bookstore.demo.exceptions.QuantityInvalidException;
import fakeamazon.bookstore.demo.exceptions.ShoppingCartAddException;
import fakeamazon.bookstore.demo.exceptions.ShoppingCartEditException;
import fakeamazon.bookstore.demo.input.templates.BookIdTemplate;
import fakeamazon.bookstore.demo.input.templates.BookQuantityTemplate;
import fakeamazon.bookstore.demo.model.PurchaseDetail;
import fakeamazon.bookstore.demo.model.ShoppingCartItem;
import fakeamazon.bookstore.demo.services.CompletePurchaseService;
import fakeamazon.bookstore.demo.services.ShoppingCartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@RestController
@RequestMapping("useractions")
public class UserRestController {

    private final ShoppingCartService shoppingCartService;
    private final CompletePurchaseService completePurchaseService;

    /**
     * The controller for user related actions.
     *
     * @param shoppingCartService The service that allows modification of user cart
     * @param completePurchaseService The service that allows purchasing
     */
    @Autowired
    public UserRestController(ShoppingCartService shoppingCartService, CompletePurchaseService completePurchaseService) {
        this.shoppingCartService = shoppingCartService;
        this.completePurchaseService = completePurchaseService;
    }

    /**
     *  Allows the user to add an item to their cart.
     *
     * @param auth Authentication context of the current user
     * @param item The body containing the book id and quantity to remove
     * @return add success -> OK, book not found -> NOT_FOUND
     */
    @PostMapping("addtocart")
    public ResponseEntity<ShoppingCartItem> addToCart(Authentication auth, @RequestBody BookQuantityTemplate item) {
        try {
            Optional<ShoppingCartItem> itemAdded = shoppingCartService.addToCart(auth, item);
            if (itemAdded.isPresent()) {
                return ResponseEntity.status(HttpStatus.CREATED).body(itemAdded.get());
            } else {
                return ResponseEntity.ok(null);
            }
        } catch (ShoppingCartAddException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    /**
     * Allows the user to edit the quantity of a book in their cart.
     * If the book doesn't exist in their cart, return a NOT_FOUND.
     * If the book does exist in their cart, but the quantity couldn't be changed return a NOT_ACCEPTABLE.
     * If the book does exist in their cart, and was edited return an OK.
     *
     * @param auth Authentication context of the current user
     * @param item The body containing the book id and quantity to remove
     * @return edit success -> OK, book not found -> NOT_FOUND, edit unsuccessful otherwise -> NOT_ACCEPTABLE
     */
    @PatchMapping("editcart")
    public ResponseEntity<ShoppingCartItem> editCartQuantity(Authentication auth, @RequestBody BookQuantityTemplate item) {
        try {
            // item edited could not be present, but it doesn't matter
            Optional<ShoppingCartItem> itemEdited = shoppingCartService.changeCartAmount(auth, item);
            return ResponseEntity.of(itemEdited);
        } catch (ShoppingCartEditException e) {
            // quantity was bad
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(null);
        }
    }

    /**
     * Endpoint to allow the removal of items from a user's cart.
     *
     * @param auth Spring boot authentication context
     * @param item The user input template containing ID to remove
     * @return The book item removed
     */
    @DeleteMapping("removefromcart")
    public ResponseEntity<ShoppingCartItem> removeFromCart(Authentication auth, @RequestBody BookIdTemplate item) {
        Optional<ShoppingCartItem> itemRemoved = shoppingCartService.removeCartItem(auth, item);
        return ResponseEntity.of(itemRemoved);
    }

    /**
     * Complete the purchase order request of items in the current user's shopping cart.
     *
     * @param auth Authentication details of the current user
     * @return The list of items purchased in the order (book name and quantity)
     */
    @DeleteMapping("completeorder")
    public ResponseEntity<List<PurchaseDetail>> completeOrder(Authentication auth) {
        try {
            Optional<List<PurchaseDetail>> currPurchaseDetails = completePurchaseService.completePurchase(auth);
            return ResponseEntity.of(currPurchaseDetails);
        } catch (QuantityInvalidException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).header("ErrorResponse", e.getMessage()).body(null);
        } catch (NoSuchElementException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).header("ErrorResponse", e.getMessage()).body(null);
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).header("ErrorResponse", "Unexpected error occurred: "+e.getMessage()).body(null);
        }
    }

    /**
     * Get the books in the current user's shopping cart.
     *
     * @param auth Authentication details of the current user
     * @return The list of books in the shopping cart
     */
    @GetMapping("getcartitems")
    public ResponseEntity<List<ShoppingCartItem>> getCartItems(Authentication auth) {
        List<ShoppingCartItem> items = shoppingCartService.getItemsForCustomer(auth);
        return ResponseEntity.ok().body(items);
    }
}
