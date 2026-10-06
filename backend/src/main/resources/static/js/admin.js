const API = "/api";
let adminAuth = sessionStorage.getItem("adminAuth");

function getAdminAuth() {
    if (adminAuth) {
        return adminAuth;
    }

    const username = prompt("Admin username:");
    const password = prompt("Admin password:");

    if (!username || !password) {
        alert("Admin login is required.");
        return null;
    }

    adminAuth = btoa(username + ":" + password);
    sessionStorage.setItem("adminAuth", adminAuth);
    return adminAuth;
}

async function api(url, options = {}) {
    const auth = getAdminAuth();

    if (!auth) {
        throw new Error("Admin authentication required");
    }

    options.headers = {
        ...(options.headers || {}),
        "Authorization": "Basic " + auth
    };

    const response = await fetch(url, options);

    if (response.status === 401 || response.status === 403) {
        sessionStorage.removeItem("adminAuth");
        adminAuth = null;
        alert("Invalid admin credentials.");
        location.reload();
        throw new Error("Admin authentication failed");
    }

    return response;
}

async function load() {
    try {
        const s = await (await api(API + "/admin/stats")).json();
        document.getElementById("stats").innerHTML =
            `<div class="stat"><b>${s.users}</b><span>Users</span></div>
             <div class="stat"><b>${s.trains}</b><span>Trains</span></div>
             <div class="stat"><b>${s.bookings}</b><span>Bookings</span></div>`;

        const trains = await (await api(API + "/trains")).json();
        document.getElementById("trains").innerHTML = trains.map(t =>
            `<div class="admin-row"><span><b>${t.trainNumber}</b> - ${t.trainName}<br>
            ${t.source} → ${t.destination} | Seats: ${t.availableSeats}/${t.totalSeats}</span>
            <button onclick="deleteTrain(${t.trainId})">Delete</button></div>`).join("");

        const stations = await (await api(API + "/stations")).json();
        document.getElementById("stations").innerHTML = stations.map(s =>
            `<div class="admin-row"><span><b>${s.stationCode}</b> - ${s.stationName}, ${s.city}</span>
            <button onclick="deleteStation(${s.stationId})">Delete</button></div>`).join("");

        const bookings = await (await api(API + "/admin/bookings")).json();
        document.getElementById("bookings").innerHTML = bookings.map(b =>
            `<div class="admin-row">PNR <b>${b.pnr}</b> | Passenger: ${b.passengerName} | Status: ${b.bookingStatus}</div>`).join("");
    } catch (e) {
        console.error(e);
    }
}

async function addTrain() {
    const total = Number(document.getElementById("totalSeats").value);

    await api(API + "/trains", {
        method: "POST",
        headers: {"Content-Type": "application/json"},
        body: JSON.stringify({
            trainNumber: document.getElementById("trainNumber").value,
            trainName: document.getElementById("trainName").value,
            source: document.getElementById("source").value,
            destination: document.getElementById("destination").value,
            totalSeats: total,
            availableSeats: total
        })
    });

    load();
}

async function deleteTrain(id) {
    if (confirm("Delete train?")) {
        await api(API + "/trains/" + id, {method: "DELETE"});
        load();
    }
}

async function addStation() {
    await api(API + "/stations", {
        method: "POST",
        headers: {"Content-Type": "application/json"},
        body: JSON.stringify({
            stationCode: document.getElementById("stationCode").value,
            stationName: document.getElementById("stationName").value,
            city: document.getElementById("city").value,
            state: document.getElementById("state").value
        })
    });

    load();
}

async function deleteStation(id) {
    if (confirm("Delete station?")) {
        await api(API + "/stations/" + id, {method: "DELETE"});
        load();
    }
}

load();