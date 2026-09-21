const API = "http://localhost:8080/api";

async function login() {
    const response = await fetch(API + "/users/login", {
        method: "POST",
        headers: {"Content-Type": "application/json"},
        body: JSON.stringify({
            email: document.getElementById("email").value,
            password: document.getElementById("password").value
        })
    });

    const data = await response.json();

    if (response.ok) {
        localStorage.setItem("user", JSON.stringify(data));
        window.location.href = "search.html";
    } else {
        document.getElementById("message").innerText = data.message || "Login failed";
    }
}
