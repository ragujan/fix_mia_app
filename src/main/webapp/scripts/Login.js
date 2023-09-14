import { getById } from "./util/getById.js";
import { validate } from "./util/Validate.js";
import { MakeRequest } from "./util/MakeRequests.js";
const email = getById("email");
const password = getById("password");
const errorBox = getById("errorBox");
const errorDiv = getById("errorDivContainer");
const signup = async () => {
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
    let form = new FormData();
    form.append("email", email.value);
    form.append("password", password.value);
    const contentType = "applicaion/json";
    const url = "./loginuser";
    const formDataObj = {
        email: '',
        password: '',
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
