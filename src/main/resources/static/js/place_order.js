function finishPurchase () {
   $.ajax({
      type: 'DELETE',
      url: '/useractions/completeorder',
      success: function (data) {
         alert("Transaction has been processed.");
      },
      error: function(jqXHR, textStatus, errorThrown){
         alert(errorThrown);
      }
   });
}

$(document).ready(function () {
   const placeOrderButton = document.querySelector("#placeOrder");
   placeOrderButton.addEventListener("click", evt => {
      finishPurchase();
   });
});