// handle submit button click for editbookpage
// http://localhost:8080/owner/edit?bookId=1

$( document ).ready(function() {
    console.log("document loaded")
    $("form#editBook").submit(async function(e) {
        e.preventDefault();
        let formData = new FormData();
        const bookId = $("#bookId").text()
        console.log("bookID" + bookId)
        formData.append("id", bookId);
        formData.append("name", $("#bookname").val());
        formData.append("quantity", 0);
        formData.append("publisher", $("#bookpublisher").val());
        formData.append("description", $("#bookdescription").text());
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

        $.ajax({
            url: `http://localhost:8080/owneractions/edit?id=${bookId}`,
            type: 'POST',
            data: json,
            success: function (data) {
                alert(data)
            },
            processData: false,
            contentType: 'application/json; charset=utf-8',
        });
    });

    $("#bookimage").change(async function () {
        let files = $(this).prop('files');
        if (files.length > 0) {
            let f = await blobToBase64(files[0])
            $("#imagepreview").attr("src", f);
        } else {
            $("#imagepreview").attr("src", "");
        }
    })
});

export function blobToBase64(blob) {
    return new Promise((resolve, _) => {
        const reader = new FileReader();
        reader.onloadend = () => resolve(reader.result);
        reader.readAsDataURL(blob);
    });
}