$( document ).ready(function() {
    const nameListDiv = $("#user-name-list");
    let nameList = [];
    $.ajax({
        type: "get",
        url: "/owneractions/usernamelist",
        success: function (res) {
            console.log(res)
            res.forEach((data) => {
                console.log(data.username)
                const usernameDiv = $(document.createElement("div"))
                usernameDiv.click(() => {
                    window.location.href = `http://localhost:8080/owner/history?username=${data.username}`
                })
                usernameDiv.attr("class", "user-name-list-item")
                const usernameP = $(document.createElement("p"));
                usernameP.text(data.username)
                nameList.push(data.username)
                usernameDiv.append(usernameP);
                nameListDiv.append(usernameDiv);
            })
        },
        error: function (errorThrown) {
            alert(errorThrown)
        }
    });
})