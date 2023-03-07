$( document ).ready(function() {
    $("form#uplform").submit(async function(e) {
        e.preventDefault();
        let formData = new FormData();

        formData.append("name", $("#bookname").val());
        formData.append("quantity", $("#bookquantity").val());
        formData.append("publisher", $("#bookpublisher").val());
        formData.append("description", $("#bookdescription").val());
        formData.append("isbn", $("#bookisbn").val());

        console.log(formData.get("name"));
        console.log(formData.get("publisher"));
        console.log(formData.get("description"));
        console.log(formData.get("isbn"));


        let imageFiles = $("#bookimage").prop('files');

        if (imageFiles.length > 0) {
            let res = await blobToBase64(imageFiles[0]);
            console.log(res);
            formData.append("picture", res);
        }

        console.log(formData.get("picture"));

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

    $("#bookimage").change(async function () {
        console.log("called");
        let files = $(this).prop('files');
        if (files.length > 0) {
            let f = await blobToBase64(files[0])
            $("#imagepreview").attr("src", f);
        } else {
            $("#imagepreview").attr("src", "");
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