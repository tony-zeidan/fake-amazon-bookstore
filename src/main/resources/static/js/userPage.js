$(document).ready(function(){
    console.log("user page ready")
    fetchBooksPages();
});

const fetchBooksPages = () => {
    console.log("fetching books")
    const url = new URL("http://localhost:8080/getAllBooksPage");
    $.ajax({
        type: "get",
        url: url,
        dataType: 'json',
        success: function(data, status, xhrObject)
        {
            displayBooks(data['books']);

        },
        error: function(jqXHR, textStatus, errorThrown) {
            alert(errorThrown);
        }
    });
}

const displayBooks = (bookData) => {
    console.log(bookData);
    const booksContainer = $(document.querySelector("#user-books-container"));
    bookData.forEach((data) => {
        const title = data.name
        const picture = data.picture
        let userBookDiv = $(document.createElement("div"))
        userBookDiv.attr('class', 'user-book-container')

        let userBookTitleDiv = $(document.createElement("div"))
        userBookDiv.attr('class', 'user-book-title-container')

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

        userBookDiv.append(userBookTitleDiv)
        userBookDiv.append(pictureDiv)
        booksContainer.append(userBookDiv);
    })
}
