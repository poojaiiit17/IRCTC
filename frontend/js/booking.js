const API = "http://localhost:8080/api";

async function bookTicket() {
    const user = JSON.parse(localStorage.getItem("user"));
    const trainId = localStorage.getItem("selectedTrainId");

    if (!user || !trainId) {
        alert("Please login and select a train first.");
        window.location.href = "login.html";
        return;
    }

    const response = await fetch(API + "/bookings", {
        method: "POST",
        headers: {"Content-Type": "application/json"},
        body: JSON.stringify({
            userId: user.userId,
            trainId: Number(trainId),
            passengerName: document.getElementById("passengerName").value,
            passengerAge: Number(document.getElementById("passengerAge").value),
            passengerGender: document.getElementById("passengerGender").value,
            journeyDate: document.getElementById("journeyDate").value,
            amount: Number(document.getElementById("amount").value)
        })
    });

    const data = await response.json();

    if (response.ok) {
        document.getElementById("message").innerText =
            "Booking successful. PNR: " + data.pnr;
    } else {
        document.getElementById("message").innerText =
            data.message || "Booking failed";
    }
}
