/**
 * Handles the adding of an item to the cart and the server response.
 *
 * @param book_id   The ID of the specific book entity to add
 * @param quantity  The initial quantity of this book in the cart
 */
function handleAddToCart (book_id, quantity) {
    $.ajax({
        url: 'useractions/cart/add',
        type: 'POST',
        data: {
            'id': id,
            'quantity': quantity
        },
        success: function (data) {
            alert("The book (id="+data['book']['id']+") was successfully added to the cart (count="+data['quantity']+")!");
        },
        error: function() {
            alert("There was an error in processing your action (id="+book_id+", count="+quantity+")");
        },
        processData: false,
        contentType: 'application/json; charset=utf-8',
    });
}

/*
To the users of this script.
Ensure that there is a container:

    .cart-selector                  (div or some container)
        > .cart-selector-btn        (button)
        > .cart-selector-select     (input [type='number'])
 */
$(document).ready(function () {
    $(".cart-selector").each(function (idx) {
        let book_id = $(this).data("book_id");
        let btn = $(this > ".cart-selector-btn");
        let sel = $(this > ".cart-selector-select");

        btn.click(() => handleAddToCart(book_id, sel.val()));
    });
});