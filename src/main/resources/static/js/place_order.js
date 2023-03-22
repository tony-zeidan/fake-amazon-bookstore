function finishPurchase () {
   $.ajax({
      type: 'DELETE',
      url: '/useractions/completeorder',
      success: function (data) {
         alert("Transaction has been processed.");
      },
      error: function(jqXHR, textStatus, errorThrown){
         alert("Your transaction has been denied.");
      }
   });
}

$(document).ready(function () {
   const placeOrderButton = $("#placeOrder");
   placeOrderButton.click(evt => {
      finishPurchase();
   });
});