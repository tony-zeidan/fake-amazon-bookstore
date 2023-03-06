$( document ).ready(function() {
    $("form#uplform").submit(function(e) {
        e.preventDefault();
        let formData = new FormData();

        fileReader.onload = () => {
            const srcData = fileReader.result;
            console.log('base64:', srcData)
        };
        fileReader.readAsDataURL(imageFile);

        formData.append("name", $("#bookname").val());
        formData.append("publisher", $("#bookpublisher").val());
        formData.append("description", $("#bookdescription").val());
        formData.append("isbn", $("#bookisbn").val());

        let imageFiles = $("#bookimage").prop('files');

        if (imageFiles.length > 0) {
            const fileReader = new FileReader();
            fileReader.onload = () => {
                let res = fileReader.result;
                console.log("RESULT: " + res);
                formData.append("picture", res);
            };
            fileReader.readAsDataURL(imageFiles[0]);
        }


        console.log(formData.get("bookdetails"));
        console.log(formData.get("bookimage"));

        $.ajax({
            url: "http://localhost:8080/owneractions/upload",
            type: 'POST',
            data: formData,
            success: function (data) {
                alert(data)
            },
            cache: false,
            contentType: 'application/json',
            processData: false
        });
    });
});




function blobToBase64(blob) {
    return new Promise((resolve, _) => {
        const reader = new FileReader();
        reader.onloadend = () => resolve(reader.result);
        reader.readAsDataURL(blob);
    });
}