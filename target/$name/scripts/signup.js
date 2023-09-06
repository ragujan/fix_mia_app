import { getById } from "./util/getById.js";
import { validate } from "./util/Validate.js";
const username = getById("username");
const email = getById("email");
const password = getById("password");
const confirmPassword = getById("confirm-password");
const errorBox = getById("errorBox");
const errorDiv = getById("errorDivContainer");
const signup = () => {
    // alert("check check 1222")
    if (!validate(username, "username")) {
        errorDiv.classList.remove("hidden");
        errorBox.innerHTML = "Invalid username only numbers and texts are allowed";
        return;
    }
    if (!validate(email, "email")) {
        errorDiv.classList.remove("hidden");
        errorBox.innerHTML = "Invalid email";
        return;
    }
    if (!validate(password, "password")) {
        errorDiv.classList.remove("hidden");
        errorBox.innerHTML = "Password length must at least 8";
        return;
    }
    if (password.value != confirmPassword.value) {
        errorDiv.classList.remove("hidden");
        errorBox.innerHTML = "Passwords not matching";
        return;
    }
};
window.addEventListener('input', () => {
    errorDiv.classList.add("hidden");
    errorBox.innerHTML = "";
});
const btn = getById("signup-btn");
btn?.addEventListener("click", () => {
    signup();
});
