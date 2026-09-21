const API="http://localhost:8080/api";
async function loadStations(){
 const stations=await fetch(API+"/stations").then(r=>r.json());
 for(const id of ["source","destination"]){
  document.getElementById(id).innerHTML='<option value="">Select station</option>'+stations.map(s=>`<option value="${s.stationName}">${s.stationName} (${s.stationCode})</option>`).join("");
 }
}
async function searchTrains(){
 const source=document.getElementById("source").value,destination=document.getElementById("destination").value,date=document.getElementById("journeyDate").value;
 if(!source||!destination||!date){alert("Please select source, destination and journey date.");return;}
 const trains=await fetch(API+"/trains/search?source="+encodeURIComponent(source)+"&destination="+encodeURIComponent(destination)+"&date="+date).then(r=>r.json());
 document.getElementById("results").innerHTML=trains.length?trains.map(t=>`<div class="train-card"><h3>${t.trainNumber} - ${t.trainName}</h3><p>${t.source} → ${t.destination}</p><p>Available: ${t.availableSeats}</p><button onclick="bookTrain(${t.trainId},'${date}')">Select Train</button></div>`).join(""):"<div class='empty'>No trains found.</div>";
}
function bookTrain(id,date){localStorage.setItem("selectedTrainId",id);localStorage.setItem("journeyDate",date);location.href="booking.html";}
loadStations();