$(document).ready(function(event){
    const filters = document.querySelectorAll(".filter");

    filterGetCall();

    filters.forEach(ele => {
        ele.addEventListener("change", (event) => {
            filterGetCall();
        });
    })

    const saveButton = document.querySelector("#saveButton");
    saveButton.addEventListener("click", e=> {
        incrementInventory(e);
    });

    const nextButton = document.querySelector("#nextButton");
    nextButton.addEventListener("click", e=> {
        if (page.currentPage+1 === page.totalPages) return;
        page.currentPage+=1;
        filterGetCall();
    });


    const prevButton = document.querySelector("#prevButton");
    prevButton.addEventListener("click", e=> {
        if (page.currentPage === 0) return;
        page.currentPage-=1;
        filterGetCall();
    });
});

const page = {
    currentPage:0,
    totalPages:0
}
function incrementInventory(e){
    e.preventDefault();

    const inputs = $(".inc-quantity");

    inputs.each(function (){
        if($(this).val() !== "0" && $(this).val() !== null){
            $.ajax({
                type: "patch",
                url: "owneractions/inventory",
                contentType: 'application/json',
                dataType: 'json',
                data: JSON.stringify({
                    id: $(this).data("bookId"),
                    quantity: $(this).val()
                }),
                success: function(data, status, xhrObject){
                    let updatedQuants = $(`#quantity-${data['id']}`);
                    updatedQuants.text(data['quantity']);
                    alert("success");
                },
                error: function(jqXHR, textStatus, errorThrown){
                    alert(errorThrown);
                }
            });
        }
    })
}

function filterGetCall() {
    const nameFilter = document.querySelector("#nameFilter");
    const isbnFilter = document.querySelector("#isbnFilter");
    const descriptionFilter = document.querySelector("#descriptionFilter");
    const publisherFilter = document.querySelector("#publisherFilter");

    let url = new URL("localhost:8080/getAllBooksPage");
    if (nameFilter.value) url.searchParams.set("name", nameFilter.value);
    if (isbnFilter.value) url.searchParams.set("isbn", isbnFilter.value);
    if (descriptionFilter.value) url.searchParams.set("description", descriptionFilter.value);
    if (publisherFilter.value) url.searchParams.set("publisher", publisherFilter.value);
    if (page.currentPage) url.searchParams.set("page", page.currentPage);
    $.ajax({
        type: "get",
        url: "/owneractions/getAllBooksPage?"+url.searchParams.toString(),
        dataType: 'json',
        success: function(data, status, xhrObject)
        {
            page.currentPage = data["currentPage"];
            page.totalPages = data["totalPages"];
            addBookTable(data['books']);
        },
        error: function(jqXHR, textStatus, errorThrown)
        {
            alert(errorThrown);
        }
    });
}



function addBookTable(data) {
    const alertP =document.querySelector("#alert");
    alertP.style.visibility = "hidden";

    document.querySelectorAll(".content-tr").forEach(tr => {
        tr.remove();
    });

    if (data.length === 0) {
        alertP.style.visible = "visible";
        return;
    }

    const table = $(document.querySelector("#booksTable"));
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
                tableRow.append(cell);
            } else if (key === "quantity"){
                cell.text(data[i][key]);
                cell.attr("id",`quantity-${data[i]["id"]}`);
                tableRow.append(cell);
                let cell2 = $(document.createElement("td"));
                let inputNum = $(document.createElement("input"));
                inputNum.addClass("inc-quantity");

                //For CSS styling
                inputNum.addClass("bl-generalinput");
                inputNum.data("bookId", data[i]["id"]);
                inputNum.attr("type", "number");
                inputNum.attr("value", "0");
                inputNum.attr("min", "-10000");
                inputNum.attr("max", "10000");
                cell2.append(inputNum);
                tableRow.append(cell2);
            } else {
                cell.text(data[i][key]);
                tableRow.append(cell);
            }
        }
        const editLinkCell = createEditLinkCell(id)
        tableRow.append(editLinkCell);
        tableRow.addClass("content-tr")
        table.append(tableRow.get(0));
    }
}

function createEditLinkCell(bookId) {
    const editLink = $(document.createElement("button"))
    editLink.addClass( "bl-button")
    editLink.click(() => {
        window.location.href = `http://localhost:8080/owner/edit?bookId=${bookId}`
    })
    editLink.text("Edit Book")
    const cell = $(document.createElement("td"));
    cell.append(editLink);
    return cell;
}
