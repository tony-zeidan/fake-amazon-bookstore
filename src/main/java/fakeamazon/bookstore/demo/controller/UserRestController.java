package fakeamazon.bookstore.demo.controller;

import fakeamazon.bookstore.demo.exceptions.ShoppingCartAddException;
import fakeamazon.bookstore.demo.exceptions.ShoppingCartEditException;
import fakeamazon.bookstore.demo.input.templates.BookIdTemplate;
import fakeamazon.bookstore.demo.input.templates.BookQuantityTemplate;
import fakeamazon.bookstore.demo.model.ShoppingCartItem;
import fakeamazon.bookstore.demo.services.ShoppingCartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("useractions")
public class UserRestController {

    private final ShoppingCartService shoppingCartService;

    @Autowired
    public UserRestController(ShoppingCartService shoppingCartService) {
        this.shoppingCartService = shoppingCartService;
    }

    /**
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
            System.out.println(itemEdited.orElseGet(null));
            return ResponseEntity.of(itemEdited);
        } catch (ShoppingCartEditException e) {
            // quantity was bad
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(null);
        }
    }

    @DeleteMapping("removefromcart")
    public ResponseEntity<ShoppingCartItem> removeFromCart(Authentication auth, @RequestBody BookIdTemplate item) {
        Optional<ShoppingCartItem> itemAdded = shoppingCartService.removeCartItem(auth, item);
        return ResponseEntity.of(itemAdded);
    }
}
