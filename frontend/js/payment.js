const booking=JSON.parse(localStorage.getItem("pendingBooking"));const API="http://localhost:8080/api";
if(!booking){location.href="search.html";}else{document.getElementById("pnr").innerText=booking.pnr;document.getElementById("amount").innerText=booking.amount;}
async function pay(){
 const r=await fetch(API+"/payments",{method:"POST",headers:{"Content-Type":"application/json"},body:JSON.stringify({bookingId:booking.bookingId,paymentMethod:document.getElementById("paymentMethod").value})});
 const data=await r.json();
 if(r.ok){localStorage.setItem("lastPayment",JSON.stringify(data));localStorage.removeItem("pendingBooking");location.href="ticket.html?bookingId="+booking.bookingId;}else document.getElementById("message").innerText=data.message||"Payment failed";
}