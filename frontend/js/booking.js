const API="http://localhost:8080/api";
let count=0;
function addPassenger(){
 count++;
 document.getElementById("passengers").insertAdjacentHTML("beforeend",`<div class="panel passenger"><h4>Passenger ${count}</h4><input class="pname" placeholder="Full Name"><input class="page" type="number" placeholder="Age"><select class="pgender"><option>Male</option><option>Female</option><option>Other</option></select></div>`);
}
async function createBooking(){
 const user=JSON.parse(localStorage.getItem("user")),trainId=localStorage.getItem("selectedTrainId");
 if(!user||!trainId){location.href="login.html";return;}
 const passengers=[...document.querySelectorAll(".passenger")].map((p,i)=>({name:p.querySelector(".pname").value,age:Number(p.querySelector(".page").value),gender:p.querySelector(".pgender").value}));
 if(!passengers.length||passengers.some(p=>!p.name||!p.age)){alert("Enter passenger details.");return;}
 const body={userId:user.userId,trainId:Number(trainId),journeyDate:localStorage.getItem("journeyDate"),classType:document.getElementById("classType").value,passengers};
 const r=await fetch(API+"/bookings",{method:"POST",headers:{"Content-Type":"application/json"},body:JSON.stringify(body)});
 const data=await r.json();
 if(r.ok){localStorage.setItem("pendingBooking",JSON.stringify(data));location.href="payment.html";}else document.getElementById("message").innerText=data.message||"Booking failed";
}
addPassenger();