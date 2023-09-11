import { getById } from "./util/getById.js";
import { validate } from "./util/Validate.js";
import { MakeRequest } from "./util/MakeRequests.js";
const username = getById("username");
const email = getById("email");
const password = getById("password");
const confirmPassword = getById("confirm-password");
const errorBox = getById("errorBox");
const errorDiv = getById("errorDivContainer");
const signup = async () => {
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
    let form = new FormData();
    form.append("username", username.value);
    form.append("email", email.value);
    form.append("password", password.value);
    form.append("confirmpassword", confirmPassword.value);
    const contentType = "applicaion/json";
    const url = "./signupuser";
    const formDataObj = {
        username: '',
        email: '',
        password: '',
        confirmpassword: ''
    };
    form.forEach((value, key) => {
        if (key in formDataObj) {
            formDataObj[key] = value;
        }
    });
    let response1 = await MakeRequest("POST", url, JSON.stringify(formDataObj), "text", contentType);
    console.log(response1);
    console.log("stringy object ", JSON.stringify(formDataObj));
    let response2 = await fetch(url, {
        method: "POST", body: JSON.stringify(formDataObj), headers: {
            'Content-Type': contentType
        }
    });
    const text = await response2.text();
    console.log(text);
};
window.addEventListener('input', () => {
    errorDiv.classList.add("hidden");
    errorBox.innerHTML = "";
});
const btn = getById("signup-btn");
btn?.addEventListener("click", async () => {
    await signup();
});
