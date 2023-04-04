const page = {
    currentPage:0,
    totalPages:0
}
const fetchBooksPages = () => {
    const nameFilter = document.querySelector("#userNameFilter");
    const isbnFilter = document.querySelector("#userIsbnFilter");
    const descriptionFilter = document.querySelector("#userDescriptionFilter");
    const publisherFilter = document.querySelector("#userPublisherFilter");

    let url = new URL("localhost:8080/getbooks");
    if (nameFilter.value) url.searchParams.set("name", nameFilter.value);
    if (isbnFilter.value) url.searchParams.set("isbn", isbnFilter.value);
    if (descriptionFilter.value) url.searchParams.set("description", descriptionFilter.value);
    if (publisherFilter.value) url.searchParams.set("publisher", publisherFilter.value);
    if (page.currentPage) url.searchParams.set("page", page.currentPage);
    url.searchParams.set('size', "4");
    $.ajax({
        type: "get",
        url: "/bookstore/getbooks?"+url.searchParams.toString(),
        dataType: 'json',
        success: function(data, status, xhrObject)
        {
            page.currentPage = data["currentPage"];
            page.totalPages = data["totalPages"];
            displayBooks(data['books']);
        },
        error: function(jqXHR, textStatus, errorThrown)
        {
            alert(errorThrown);
        }
    });
}

const displayBooks = (bookData) => {
    $("#user-books-container").empty()
    const booksContainer = $(document.querySelector("#user-books-container"));
    bookData.forEach((data) => {
        const title = data.name
        const picture = data.picture
        let userBookDiv = $(document.createElement("div"))
        userBookDiv.attr('class', 'user-book-container')

        let userBookTitleDiv = $(document.createElement("div"))

        let titleText = $(document.createElement("p"))
        titleText.text(title);
        titleText.attr('class', 'title-text')
        userBookTitleDiv.append(titleText);

        let pictureDiv = $(document.createElement("div"))
        pictureDiv.attr('class', 'user-picture-container')
        let img = $(document.createElement("img"));
        img.attr('src', picture);
        img.attr('class', 'user-book-image')
        pictureDiv.append(img)

        let cartActionDiv = $(document.createElement("div"))
        cartActionDiv.attr('class', 'cart-selector')

        let quantityInputDiv = $(document.createElement("div"))
        quantityInputDiv.attr('class', 'quantity-input-container')

        let quantityLabel = $(document.createElement("label"))
        quantityLabel.text("quantity: ")

        let quantityInput = $(document.createElement("input"))
        quantityInput.attr("type", 'number')
        quantityInput.attr('value', "1")
        quantityInput.attr("min", "1")
        quantityInput.attr("max", "10")
        quantityInput.attr('class', 'cart-selector-select')

        quantityInputDiv.append(quantityLabel, quantityInput)

        let addCartButton = $(document.createElement("button"))
        addCartButton.attr('class', 'add-to-cart-button cart-selector-btn')
        addCartButton.text("add to cart")
        addCartButton.click(() => {
            handleAddToCart(data.id, quantityInput.val())
        })


        cartActionDiv.append(quantityInputDiv);
        cartActionDiv.append(addCartButton);
        userBookDiv.append(userBookTitleDiv)
        userBookDiv.append(pictureDiv)
        userBookDiv.append(cartActionDiv)
        booksContainer.append(userBookDiv);
    })
}

$(document).ready(function() {
    fetchBooksPages();
    console.log(page.currentPage)
    $("#user-search-button").click(() => {
        page.currentPage = 0;
        fetchBooksPages();
    });

    const nextButton = $("#nextButton");
    nextButton.click( () => {
        if (page.currentPage+1 === page.totalPages) return;
        page.currentPage+=1;
        fetchBooksPages();
    });


    const prevButton = $("#prevButton");
    prevButton.click( () => {
        if (page.currentPage === 0) return;
        page.currentPage-=1;
        fetchBooksPages();
    });
});

/**
 * Handles the adding of an item to the cart and the server response.
 *
 * @param book_id   The ID of the specific book entity to add
 * @param quantity  The initial quantity of this book in the cart
 */
function handleAddToCart (book_id, quantity) {
    $.ajax({
        url: '/useractions/addtocart',
        type: 'POST',
        data: JSON.stringify({
            'id': book_id,
            'quantity': quantity
        }),
        success: function () {
            alert("The book was added successfully!");
            updateCartCountDisplay();
        },
        error: function() {
            alert("There was an error in processing your action (id="+book_id+", count="+quantity+")");
        },
        processData: false,
        contentType: 'application/json; charset=utf-8',
    });
}

function updateCartCountDisplay() {
    $.ajax({
        url: '/useractions/getcartitems',
        type: 'GET',
        success: (res) => {
            console.log(res);
            const cartCount = res.length;
            const cartLink = $('a[href="/user/viewcart"]');
            cartLink.text(`View Cart (${cartCount})`);
        },
        error: () => {
            console.log("error updating cart count display")
        }
    })
}