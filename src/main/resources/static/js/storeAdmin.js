
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

            for(let i = 0; i < data.length; i++) {
                const tableRow = document.createElement("tr");
                for(let key in data[i]) {
                    const cell = document.createElement("td");
                    if (key === "picture") {
                        let imgData = document.createElement("img")
                        imgData.src = data[i][key];
                        cell.appendChild(imgData);
                    } else {
                        cell.innerText = data[i][key];
                    }

                    tableRow.appendChild(cell);
                }
                table.appendChild(tableRow);
            }
        },
        error: function(jqXHR, textStatus, errorThrown)
        {
            alert(errorThrown);
        }
    });
    console.log("here")
});
