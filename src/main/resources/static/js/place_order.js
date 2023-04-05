function finishPurchase () {
   $.ajax({
      type: 'DELETE',
      url: '/useractions/completeorder',
      timeout: 1000,
      success: function (data) {
         let runningDetails = "Purchase complete:\n";
         for (const item of data){
            runningDetails += '\n' + item['bookName'] + ": " + item['purchaseQuantity'];
         }
         alert(runningDetails);
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