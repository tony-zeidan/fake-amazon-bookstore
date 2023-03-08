console.log("Script added");

function incrementInventory(e){
    e.preventDefault();

}

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

            const table = $(document.querySelector("#booksTable"));
            const header = $(document.createElement("tr"));
            for(let key in data[0]) {
                const cell = $(document.createElement("th"));
                cell.text(key);
                header.append(cell);
                if (key === "picture") {
                    cell.attr("id", "imgcol");
                } else if (key === "quantity"){
                    const cell2 = $(document.createElement("th"));
                    cell2.text("increment");
                    header.append(cell2);
                }
            }
            table.append(header);

            for(let i = 0; i < data.length; i++) {
                const tableRow = $(document.createElement("tr"));
                for(let key in data[i]) {
                    const cell = $(document.createElement("td"));
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
                        tableRow.append(cell);
                        let cell2 = $(document.createElement("td"));
                        let inputNum = $(document.createElement("input"));
                        inputNum.attr("type", "number");
                        inputNum.attr("class", "bl-generalinput");
                        inputNum.attr("placeholder", "0");
                        inputNum.attr("min", "-10000");
                        inputNum.attr("max", "10000");
                        cell2.append(inputNum);
                        tableRow.append(cell2);
                    } else {
                        cell.text(data[i][key]);
                        tableRow.append(cell);
                    }
                }
                table.append(tableRow.get(0));
            }

            let saveButton = $(document.createElement("button"));
            saveButton.text("Save");
            saveButton.attr("class", "bl-button");
            saveButton.click();

            $(".content-div").append(saveButton);
        },
        error: function(jqXHR, textStatus, errorThrown)
        {
            alert(errorThrown);
        }
    });
});
