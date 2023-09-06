const uname = document.getElementById("username")
const email = document.getElementById("email")
const password = document.getElementById("password");
const confirmPassword = document.getElementById("confirm-password");


const test = (uname1,email1,password1,confirmPassword1)=>{
    uname.value = uname1;
    email.value = email1;
    password.value = password1;
    confirmPassword.value = confirmPassword1;
}
test("rag","rag@gmail.com","rag!!123RAG","rag!!123RAG");