function finishPurchase () {
   $.ajax({
      type: 'DELETE',
      url: '/useractions/completeorder',
      timeout: 1000,
      success: function (data, status, xhrObject) {
         alert("Transaction has been processed");
         location.reload();
      },
      error: function(jqXHR, textStatus, errorThrown){
         let errorResponse = jqXHR.getResponseHeader('ErrorResponse');
         alert(errorResponse);
      }
   });
}

$(document).ready(function () {
   const placeOrderButton = $("#placeOrder");
   placeOrderButton.click(evt => {
      finishPurchase();
   });
});