$( document ).ready(function() {
    $("form#uplform").submit(async function(e) {
        e.preventDefault();
        let formData = new FormData();

        formData.append("name", $("#bookname").val());
        formData.append("quantity", $("#bookquantity").val());
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
        let json = JSON.stringify(object);

        $.ajax({
            url: "http://localhost:8080/owneractions/upload",
            type: 'POST',
            data: json,
            success: function (data) {
                alert(data)
            },
            processData: false,
            contentType: 'application/json; charset=utf-8',
        });
    });

    let lbl = $("#preview-label");
    let prev = $(".imagepreview");
    let img = $("#controlpreview");
    let btn = prev.find("button");

    btn.click(function(e) {
        e.preventDefault();
        if (prev.is(":visible")) {
            prev.hide();
            lbl.hide();
            img.attr("src", "");
        }
    })

    $("#bookimage").change(async function () {
        console.log("Called");
        let files = $(this).prop('files');

        if (files.length > 0) {
            let f = await blobToBase64(files[0])
            prev.show();
            lbl.show();
            img.attr("src", f);
        } else {
            console.log("HERE");
            prev.hide();
            lbl.hide();
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