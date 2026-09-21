const API="http://localhost:8080/api";
async function loadBookings(){
 const user=JSON.parse(localStorage.getItem("user"));
 if(!user){location.href="login.html";return;}
 const data=await fetch(API+"/bookings/user/"+user.userId).then(r=>r.json());
 const box=document.getElementById("bookings");
 if(!data.length){box.innerHTML="<div class='empty'>No bookings found.</div>";return;}
 box.innerHTML=data.map(b=>`<div class="ticket"><div><span class="label">PNR</span> <strong>${b.pnr}</strong></div>
 <p><b>Passenger:</b> ${b.passengerName} &nbsp; <b>Journey:</b> ${b.journeyDate}</p>
 <p><b>Train ID:</b> ${b.trainId} &nbsp; <b>Amount:</b> ₹${b.amount}</p>
 <span class="status ${b.bookingStatus.toLowerCase()}">${b.bookingStatus}</span>
 <button onclick="showPassengers(${b.bookingId})">Passenger Details</button>
 <div id="passengers-${b.bookingId}"></div>
 ${b.bookingStatus!=="CANCELLED"?`<button onclick="cancelBooking(${b.bookingId})">Cancel Ticket</button>`:""}</div>`).join("");
}
async function showPassengers(id){
 const data=await fetch(API+"/passengers/booking/"+id).then(r=>r.json());
 document.getElementById("passengers-"+id).innerHTML=data.map(p=>`<p><b>${p.name}</b> | Age: ${p.age} | ${p.gender} | Coach: ${p.coach} | Seat: ${p.seatNumber}</p>`).join("");
}
async function cancelBooking(id){if(confirm("Cancel this ticket?")){await fetch(API+"/bookings/"+id+"/cancel",{method:"PUT"});loadBookings();}}
loadBookings();