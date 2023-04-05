$( document ).ready(function() {
    const nameListDiv = $("#user-name-list");
    let nameList = [];

    // fetch a list of username who has purchase history
    $.ajax({
        type: "get",
        url: "/owneractions/usernamelist",
        success: function (res) {
            res.forEach((data) => {
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

    // set up the responsive filter
    const searchBox = $("#username-search-input")
    searchBox.on("input", () => {
        const filter = searchBox.val().toLowerCase();
        nameList.forEach((username) => {
            const usernameDiv = $(`#user-name-list .user-name-list-item:contains('${username}')`);
            if (username.toLowerCase().startsWith(filter)) {
                usernameDiv.show();
            } else {
                usernameDiv.hide();
            }
        });
    })
})