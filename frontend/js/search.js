const API = "http://localhost:8080/api";

async function searchTrains() {
    const source = document.getElementById("source").value.trim();
    const destination = document.getElementById("destination").value.trim();

    const response = await fetch(
        API + "/trains/search?source=" + encodeURIComponent(source) +
        "&destination=" + encodeURIComponent(destination)
    );

    const trains = await response.json();
    const results = document.getElementById("results");

    if (trains.length === 0) {
        results.innerHTML = "<p>No trains found.</p>";
        return;
    }

    results.innerHTML = trains.map(train => `
        <div class="train-card">
            <h3>${train.trainNumber} - ${train.trainName}</h3>
            <p>${train.source} → ${train.destination}</p>
            <p>Available Seats: ${train.availableSeats}</p>
            <button onclick="bookTrain(${train.trainId})">Book</button>
        </div>
    `).join("");
}

function bookTrain(trainId) {
    localStorage.setItem("selectedTrainId", trainId);
    window.location.href = "booking.html";
}
