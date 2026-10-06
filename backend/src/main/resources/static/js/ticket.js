const API = "/api";
const id = new URLSearchParams(location.search).get("bookingId");

async function load() {
    const user = JSON.parse(localStorage.getItem("user"));

    if (!user) {
        location.href = "/login";
        return;
    }

    const bs = await fetch(API + "/bookings/user/" + user.userId).then(r => r.json());
    const b = bs.find(x => String(x.bookingId) === String(id));
    const ps = await fetch(API + "/passengers/booking/" + id).then(r => r.json());

    if (!b) {
        document.getElementById("ticket").innerHTML = "<p>Ticket not found.</p>";
        return;
    }

    document.getElementById("ticket").innerHTML =
        `<h2>Ticket Confirmed</h2>
         <h3>PNR: ${b.pnr}</h3>
         <p><b>Journey:</b> ${b.journeyDate}</p>
         <p><b>Train ID:</b> ${b.trainId} | <b>Class:</b> ${b.classType}</p>
         <p><b>Total Fare:</b> ₹${b.amount}</p>
         <h3>Passengers</h3>
         ${ps.map(p => `<p>${p.name}, Age ${p.age}, ${p.gender} — Coach ${p.coach}, Seat ${p.seatNumber}</p>`).join("")}`;
}

load();