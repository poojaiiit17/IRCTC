async function pay() {
    const response = await fetch("http://localhost:8080/api/payments", {
        method: "POST",
        headers: {"Content-Type": "application/json"},
        body: JSON.stringify({
            bookingId: Number(document.getElementById("bookingId").value),
            amount: Number(document.getElementById("amount").value),
            paymentMethod: document.getElementById("paymentMethod").value
        })
    });

    const data = await response.json();

    if (response.ok) {
        document.getElementById("message").innerText =
            "Payment successful. Transaction ID: " + data.transactionId;
    } else {
        document.getElementById("message").innerText = "Payment failed";
    }
}
