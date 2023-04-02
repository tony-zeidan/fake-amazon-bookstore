$( document ).ready(function() {
    const nameListDiv = $("#user-name-list");
    let nameList = [];
    $.ajax({
        type: "get",
        url: "/owneractions/usernamelist",
        success: function (data) {
            console.log(data)
            data.forEach((username) => {
                const usernameDiv = $(document.createElement("div"))
                const usernameP = $(document.createElement("p"));
                usernameDiv.append(usernameP);
                nameListDiv.append(usernameDiv);
            })
        },
        error: function (errorThrown) {
            alert(errorThrown)
        }
    });
})