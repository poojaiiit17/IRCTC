const API = "http://localhost:8080/api";

async function register() {
    const response = await fetch(API + "/users/register", {
        method: "POST",
        headers: {"Content-Type": "application/json"},
        body: JSON.stringify({
            name: document.getElementById("name").value,
            email: document.getElementById("email").value,
            password: document.getElementById("password").value,
            phone: document.getElementById("phone").value
        })
    });

    if (response.ok) {
        alert("Registration successful");
        window.location.href = "login.html";
    } else {
        document.getElementById("message").innerText = await response.text();
    }
}
