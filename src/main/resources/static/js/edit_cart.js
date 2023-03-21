function commitCartChanges() {
    $(".item-card").each(function (ind) {
        let id = $(this).id.data("book");
        console.log("BOOK ID " + id);
        let quant = $(this > "item-card-quantity").val();
        let del = $(this > "item-card-remove").checked;

        if (del || quant===0) {
            $.ajax({
                url: 'useractions/cart/remove',
                type: 'DELETE',
                data: {
                    'id': id
                },
                success: function (data) {
                    alert(JSON.stringify(data))
                },
                error: function() {
                    alert("Could not delete the item with ID="+id);
                },
                processData: false,
                contentType: 'application/json; charset=utf-8',
            });
        } else if (quant !== 0) {
            $.ajax({
                url: 'useractions/cart/edit',
                type: 'PATCH',
                data: {
                    'id': id,
                    'quantity': quant
                },
                success: function (data) {
                    alert(JSON.stringify(data))
                },
                error: function() {
                    alert("Could not edit the item with ID="+id+ " and with QUANT="+quant);
                },
                processData: false,
                contentType: 'application/json; charset=utf-8',
            });
        }
    })
}