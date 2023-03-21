function commitCartChanges() {

    $(".item-card").each(function () {
        let id = parseInt($(this).data("book"));
        let quant = parseInt($(this).find(".item-card-quantity").val());
        let del = $(this).find(".item-card-remove").is(":checked");

        if (del) {
            $.ajax({
                url: '/useractions/removefromcart',
                type: 'DELETE',
                data: JSON.stringify({
                    'id': id,
                }),
                success: function (data) {
                    alert("Successfully removed book!");
                    location.reload();
                },
                error: function () {
                    alert("Could not delete the item with ID=" + id)
                },
                processData: false,
                contentType: 'application/json; charset=utf-8',
            });
        } else if (quant !== 0) {
            $.ajax({
                url: '/useractions/editcart',
                type: 'PATCH',
                data: JSON.stringify({
                    'id': id,
                    'quantity': quant,
                }),
                success: function (data) {
                    alert("Successfully edited the quantity of the book!");
                    location.reload();
                },
                error: function () {
                    alert("Could not edit the item with ID=" + id + " and with QUANT=" + quant);
                },
                processData: false,
                contentType: 'application/json; charset=utf-8',
            });
        }
    })
}

$(document).ready(function () {
    $(".cart-submit").click(()=>commitCartChanges());
})