$( document ).ready(function() {
    // handle of submit of the edit form
    $("form#editBook").submit(async function(e) {
        e.preventDefault();
        let formData = new FormData();
        const bookId = $("#bookId").text()
        console.log("bookID" + bookId)
        formData.append("id", bookId);
        formData.append("name", $("#bookname").val());
        formData.append("publisher", $("#bookpublisher").val());
        formData.append("description", $("#bookdescription").val());
        formData.append("isbn", $("#bookisbn").val());


        let imageFiles = $("#bookimage").prop('files');

        if (imageFiles.length > 0) {
            let res = await blobToBase64(imageFiles[0]);
            formData.append("picture", res);
        }

        let object = {};
        formData.forEach((value, key) => object[key] = value);
        console.log(object)
        let json = JSON.stringify(object);

        // submit a patch request to update the book information
        $.ajax({
            url: `http://localhost:8080/owneractions/edit?id=${bookId}`,
            type: 'PATCH',
            data: json,
            success: function (data) {
                alert(JSON.stringify(data))
            },
            error: function() {
                alert("error when edit")
            },
            processData: false,
            contentType: 'application/json; charset=utf-8',
        });
    });

    let img = $(".imagepreviewdisplay");
    let btn = $(".imagepreviewbutton");

    if (img.attr('src') !== "") {
        btn.text("-");
        img.show();
    }

    // this is when the expand or collapse buton is clicked
    btn.click(function(e) {
        e.preventDefault();
        if (img.is(":visible")) {
            btn.text("+");
            img.hide();
        } else {
            if (img.attr('src') !== "") {
                btn.text("-");
                img.show();
            }
        }
    })

    // this is when the user selects a new file
    $("#bookimage").change(async function () {
        let files = $(this).prop('files');

        if (files.length > 0) {
            let f = await blobToBase64(files[0]);
            img.attr("src", f);
            if (!img.is(":visible")) {
                btn.text("-");
                img.show();
            }
        } else {
            img.hide();
            btn.text("+");
            img.attr("src", "");
        }
    })
});

function blobToBase64(blob) {
    return new Promise((resolve, _) => {
        const reader = new FileReader();
        reader.onloadend = () => resolve(reader.result);
        reader.readAsDataURL(blob);
    });
}