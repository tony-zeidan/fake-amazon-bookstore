$( document ).ready(function() {
    $("form#uplform").submit(async function(e) {
        e.preventDefault();

        let imageFiles = $("#bookimage").prop('files');

        let res = null;
        if (imageFiles.length > 0) {
            res = await blobToBase64(imageFiles[0]);
        }

        let object = {};
        formData.forEach((value, key) => object[key] = value);
        let json = JSON.stringify(object);

        $.ajax({
            url: "http://localhost:8080/owneractions/upload",
            type: 'POST',
            data: {
                name: $("#bookname").val(),
                quantity: $("#bookquantity").val(),
                picture: res,
                publisher: $("#bookpublisher").val(),
                description: $("#bookdescription").val(),
                isbn: $("#bookisbn").val()
            },
            success: function (data) {
                alert(data)
            },
            processData: false,
            contentType: 'application/json; charset=utf-8',
        });
    });

    let img = $(".imagepreviewdisplay");
    let btn = $(".imagepreviewbutton");

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
        const reader = new window.FileReader();
        reader.onloadend = () => resolve(reader.result);
        reader.readAsDataURL(blob);
    });
}