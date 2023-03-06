$( document ).ready(function() {
    $("form#uplform").submit(function(e) {
        e.preventDefault();
        let formData = new FormData();
        formData.append("bookdetails", new Blob([JSON.stringify({
            name: $("#bookname").val(),
            publisher: $("#bookpublisher").val(),
            description: $("#bookdescription").val(),
            isbn: $("#bookisbn").val()
        })], {
            type: "application/json"
        }), )
        formData.append("bookimage", $("#bookimage").prop('files')[0]);

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
            contentType: 'multipart/form-data',
            processData: false
        });
    });
});