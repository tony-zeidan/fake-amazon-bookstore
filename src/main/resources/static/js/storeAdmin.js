
document.addEventListener("DOMContentLoaded", function(event){
    $.ajax({
        type: "get",
        url: "getAllBooks",
        dataType: 'json',
        success: function(data, status, xhrObject)
        {
            if (data.length === 0) return;

            const table = document.querySelector("#booksTable");
            const header = document.createElement("tr");
            for(let key in data[0]) {
                const cell = document.createElement("th");
                cell.innerText = key;
                header.appendChild(cell);
            }
            table.appendChild(header);
            let id = 0
            for(let i = 0; i < data.length; i++) {
                const tableRow = document.createElement("tr");
                for(let key in data[i]) {
                    const cell = document.createElement("td");
                    if (key==="id") {
                        id = data[i][key]
                    }
                    if (key === "picture") {
                        let imgData = document.createElement("img")
                        imgData.src = data[i][key];
                        cell.appendChild(imgData);
                    } else {
                        cell.innerText = data[i][key];
                    }

                    tableRow.appendChild(cell);
                }
                const editLink = document.createElement("a")
                editLink.title = "Edit Book"
                editLink.href = `http://localhost:8080/owner/edit?bookId=${id}`
                const linkText = document.createTextNode("Edit Book")
                editLink.appendChild(linkText)
                const cell = document.createElement("td");
                cell.appendChild(editLink);
                tableRow.appendChild(cell);
                table.appendChild(tableRow);
            }
        },
        error: function(jqXHR, textStatus, errorThrown)
        {
            alert(errorThrown);
        }
    });
});
