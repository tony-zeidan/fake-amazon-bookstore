console.log("Script added");

document.addEventListener("DOMContentLoaded", function(event){
    $.ajax({
        type: "get",
        url: "getAllBooks",
        dataType: 'json',
        success: function(data, status, xhrObject)
        {
            if (data.length === 0) {
                let alert = $(document.createElement("p"));
                alert.text("There were no books found in your inventory!");
                alert.attr('class', 'notfound');
                $(".content-div").append(alert)
                return;
            } else {
                $(".content-div").remove(".notfound");
            }

            const table = document.querySelector("#booksTable");
            const header = document.createElement("tr");
            for(let key in data[0]) {
                const cell = document.createElement("th");
                if (key === "picture") {
                    cell.id = "imgcol";
                }
                cell.innerText = key;
                header.appendChild(cell);
            }
            table.appendChild(header);
            let id = 0
            for(let i = 0; i < data.length; i++) {
                const tableRow = $(document.createElement("tr"));
                for(let key in data[i]) {
                    const cell = $(document.createElement("td"));
                    if (key==="id") {
                        id = data[i][key]
                    }
                    if (key === "picture") {
                        let imgPrev = $(document.createElement("div"));
                        imgPrev.attr('class', 'imagepreview');
                        let imgPrevBtn = $(document.createElement("button"));
                        imgPrevBtn.attr("class", 'imagepreviewbutton');
                        imgPrevBtn.attr("type", 'button');
                        imgPrevBtn.text('-');
                        imgPrev.append(imgPrevBtn);
                        let imgPrevImg = $(document.createElement("img"));
                        imgPrevImg.attr('src', data[i][key]);
                        imgPrevImg.attr('class', 'imagepreviewdisplay');
                        imgPrev.append(imgPrevImg);
                        imgPrevImg.show();

                        imgPrevBtn.click(function(e) {
                                e.preventDefault();
                                if (imgPrevImg.is(":visible")) {
                                    imgPrevBtn.text("+");
                                    imgPrevImg.hide();
                                } else {
                                    console.log("Image invis")
                                    if (imgPrevImg.attr('src') !== "") {
                                        imgPrevBtn.text("-");
                                        imgPrevImg.show();
                                    }
                                }
                            }
                        )
                        cell.append(imgPrev);
                    } else {
                        cell.text(data[i][key]);
                    }

                    tableRow.append(cell);
                }
                const editLinkCell = createEditLinkCell(id)
                tableRow.append(editLinkCell);
                table.appendChild(tableRow.get(0));
            }
        },
        error: function(jqXHR, textStatus, errorThrown)
        {
            alert(errorThrown);
        }
    });
});

function createEditLinkCell(bookId) {
    const editLink = $(document.createElement("a"))
    editLink.attr("class", "bl-button")
    editLink.attr("href",`http://localhost:8080/owner/edit?bookId=${bookId}` )
    editLink.text("Edit Book")
    const cell = $(document.createElement("td"));
    cell.append(editLink);
    return cell;
}

