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
}
