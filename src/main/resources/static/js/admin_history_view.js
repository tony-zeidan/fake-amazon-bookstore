$( document ).ready(function() {
    const nameListDiv = $("#user-name-list");
    let nameList = [];
    $.ajax({
        type: "get",
        url: "/owneractions/usernamelist",
        success: function (data) {
            console.log(data)
        },
        error: function (errorThrown) {
            alert(errorThrown)
        }
    });
})