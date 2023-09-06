import {getById} from "./util/getById.js";
import {validate} from "./util/Validate.js";
import { MakeRequest} from "./util/MakeRequests.js";


const username = getById("username") as HTMLInputElement;
const email = getById("email") as HTMLInputElement;
const password = getById("password") as HTMLInputElement;
const confirmPassword = getById("confirm-password") as HTMLInputElement;
const errorBox = getById("errorBox") as HTMLSpanElement;
const errorDiv = getById("errorDivContainer") as HTMLDivElement;
const signup =async () => {



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
    const form = new FormData();
    form.append("name","rag");
    console.log(await MakeRequest("POST","./signuptest",form,"text"));
    console.log("ok ok ok ok ");
}
window.addEventListener('input', () => {
    errorDiv.classList.add("hidden");
    errorBox.innerHTML = "";

})
const btn = getById("signup-btn");
btn?.addEventListener("click",async () => {

   await signup();
})
export {}
