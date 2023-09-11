<!doctype html>
<html lang="en">

<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- <meta http-equiv="Content-Security-Policy" content="script-src " /> -->
    <!-- <meta http-equiv="Content-Security-Policy-Report-Only"
        content="script-src https://accounts.google.com/gsi/client; frame-src https://accounts.google.com/gsi/; connect-src https://accounts.google.com/gsi/;">

    <meta> -->
    <link href="resources/style/output.css" rel="stylesheet">
    <script src="https://accounts.google.com/gsi/client" async defer></script>
</head>

<body class="" style="background: #edf2f7;">
    <div class="flex flex-col items-center justify-center bg-grey-lighter">
        <div class="container flex flex-col items-center justify-center flex-1 max-w-md px-2 mx-auto">

            <div class="w-full px-6 py-8 text-black bg-white rounded shadow-md">
                <div class="flex justify-center w-full ">
                    <img class="w-10 h-10" src="resources/image_resources/logo.png" alt="">
                </div>
                <h1 class="mb-4 text-3xl font-semibold text-center">Sign up</h1>
                <div id="errorDivContainer" class="hidden p-1 mb-3 border-2 border-red-400">
                    <div class="flex items-center justify-center text-3xl text-center ">
                        <span id="errorBox" class="p-3 text-sm text-red-500 ">Error</span>
                    </div>
                </div>
                <input id="username" type="text" class="block w-full p-3 mb-4 border rounded border-grey-light"
                    name="username" placeholder="username" />

                <input id="email" type="text" class="block w-full p-3 mb-4 border rounded border-grey-light"
                    name="email" placeholder="Email" />

                <!-- <input id="password" type="password" class="block w-full p-3 mb-4 border rounded border-grey-light"
                    name="password" placeholder="Password" /> -->
                <div class="relative w-full">
                    <div class="absolute inset-y-0 right-0 flex items-center px-2">
                        <input class="hidden js-password-toggle-1" id="js-password-toggle-1" type="checkbox" />
                        <label
                            class="px-2 py-1 font-mono text-sm text-gray-600 bg-gray-300 rounded cursor-pointer hover:bg-gray-400 js-password-label-1"
                            for="js-password-toggle-1">show</label>
                    </div>
                    <input class="block w-full p-3 mb-4 border rounded border-grey-light" name="password" id="password"
                        type="password" placeholder="Password" autocomplete="off" />
                </div>
                <!-- <input id="confirm-password" type="password"
                    class="block w-full p-3 mb-4 border rounded border-grey-light" name="confirm_password"
                    placeholder="Confirm Password" /> -->
                <div class="relative w-full">
                    <div class="absolute inset-y-0 right-0 flex items-center px-2">
                        <input class="hidden js-password-toggle-2" id="js-password-toggle-2" type="checkbox" />
                        <label
                            class="px-2 py-1 font-mono text-sm text-gray-600 bg-gray-300 rounded cursor-pointer hover:bg-gray-400 js-password-label-2"
                            for="js-password-toggle-2">show</label>
                    </div>
                    <input class="block w-full p-3 mb-4 border rounded border-grey-light" name="password"
                        id="confirm-password" type="password" placeholder="Confirm Password" autocomplete="off" />
                </div>
                <button id="signup-btn"
                    class="w-full py-3 my-1 text-center text-white rounded bg-maintheme hover:bg-green-dark focus:outline-none">Create
                    Account
                </button>
                <div class="flex justify-center w-full pt-3 pb-2 my-1 ">

                    <div  id="g_id_onload"
                        data-client_id="337084451495-b1tda8u3401dmtqcpcfsrlgprnrs0op8.apps.googleusercontent.com"
                        data-context="signup" data-ux_mode="redirect" data-login_uri="http://localhost:8080/fix_mia_app_war_exploded/signupgooglehome"
                        data-callback="signup_google" data-nonce="" data-itp_support="true">
                    </div>

                    <div class="g_id_signin" data-type="standard" data-shape="rectangular" data-theme="outline"
                        data-text="signup_with" data-size="large" data-locale="en-US" data-logo_alignment="left">
                    </div>
                </div>

                <div class="mt-4 text-sm text-center text-grey-dark">
                    By signing up, you agree to the
                    Terms of Service
                    and
                    Privacy Policy

                </div>
            </div>

            <div class="mt-3 text-grey-dark">
                Already have an account?
                <a class="no-underline border-b border-blue text-blue" href="../login/">
                    Log in
                </a>.
            </div>
        </div>


        <script>var exports = {};</script>
        <script type="module" src="${SCRIPTS}signup.js"></script>
        <script>

            const passwordToggle1 = document.getElementById('js-password-toggle-1')

            passwordToggle1.addEventListener('change', function () {

                const password = document.getElementById('password'),
                    passwordLabel = document.querySelector('.js-password-label-1')

                if (password.type === 'password') {
                    password.type = 'text'
                    passwordLabel.innerHTML = 'hide'
                } else {
                    password.type = 'password'
                    passwordLabel.innerHTML = 'show'
                }

                password.focus()
            })
            const passwordToggle2 = document.getElementById('js-password-toggle-2')

            passwordToggle2.addEventListener('change', function () {

                const password = document.getElementById('confirm-password'),
                    passwordLabel = document.querySelector('.js-password-label-2')

                if (password.type === 'password') {
                    password.type = 'text'
                    passwordLabel.innerHTML = 'hide'
                } else {
                    password.type = 'password'
                    passwordLabel.innerHTML = 'show'
                }

                password.focus()
            })
            const uname = document.getElementById("username")
            const email = document.getElementById("email")
            const password = document.getElementById("password");
            const confirmPassword = document.getElementById("confirm-password");


            const test = (uname1, email1, password1, confirmPassword1) => {
                uname.value = uname1;
                email.value = email1;
                password.value = password1;
                confirmPassword.value = confirmPassword1;
            }
            test("rag", "rag@gmail.com", "rag!!123RAG", "rag!!123RAG");
        </script>
        <script>
         
            const signup_google  = ()=>{
              alert("HEY")
            }
        </script>
    </div>
</body>

</html>