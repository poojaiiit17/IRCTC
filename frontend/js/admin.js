const API="http://localhost:8080/api";
async function load(){
 const s=await fetch(API+"/admin/stats").then(r=>r.json());
 document.getElementById("stats").innerHTML=`<div class="stat"><b>${s.users}</b><span>Users</span></div><div class="stat"><b>${s.trains}</b><span>Trains</span></div><div class="stat"><b>${s.bookings}</b><span>Bookings</span></div>`;
 const trains=await fetch(API+"/trains").then(r=>r.json());
 document.getElementById("trains").innerHTML=trains.map(t=>`<div class="admin-row"><span><b>${t.trainNumber}</b> - ${t.trainName}<br>${t.source} → ${t.destination} | Seats: ${t.availableSeats}/${t.totalSeats}</span><button onclick="deleteTrain(${t.trainId})">Delete</button></div>`).join("");
 const stations=await fetch(API+"/stations").then(r=>r.json());
 document.getElementById("stations").innerHTML=stations.map(s=>`<div class="admin-row"><span><b>${s.stationCode}</b> - ${s.stationName}, ${s.city}</span><button onclick="deleteStation(${s.stationId})">Delete</button></div>`).join("");
 const bookings=await fetch(API+"/admin/bookings").then(r=>r.json());
 document.getElementById("bookings").innerHTML=bookings.map(b=>`<div class="admin-row">PNR <b>${b.pnr}</b> | Passenger: ${b.passengerName} | Status: ${b.bookingStatus}</div>`).join("");
}
async function addTrain(){const total=Number(document.getElementById("totalSeats").value);await fetch(API+"/trains",{method:"POST",headers:{"Content-Type":"application/json"},body:JSON.stringify({trainNumber:trainNumber.value,trainName:trainName.value,source:source.value,destination:destination.value,totalSeats:total,availableSeats:total})});load();}
async function deleteTrain(id){if(confirm("Delete train?")){await fetch(API+"/trains/"+id,{method:"DELETE"});load();}}
async function addStation(){await fetch(API+"/stations",{method:"POST",headers:{"Content-Type":"application/json"},body:JSON.stringify({stationCode:stationCode.value,stationName:stationName.value,city:city.value,state:state.value})});load();}
async function deleteStation(id){if(confirm("Delete station?")){await fetch(API+"/stations/"+id,{method:"DELETE"});load();}}
load();