<!doctype html>
<html lang="en">

<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link href="resources/style/output.css" rel="stylesheet">
</head>

<body class="flex items-center justify-center h-screen overflow-hidden" style="background: #edf2f7;">
    <div class="flex flex-col min-h-screen bg-grey-lighter">
        <div class="container flex flex-col items-center justify-center flex-1 max-w-md px-2 mx-auto">

            <div class="w-full px-6 py-8 text-black bg-white rounded shadow-md">
                <h1 class="mb-8 text-3xl text-center">Sign up</h1>
                <div id="errorDivContainer" class="hidden p-1 mb-3 border-2 border-red-400">
                    <div  class="flex items-center justify-center text-3xl text-center ">
                        <span id="errorBox" class="p-3 text-sm text-red-500 ">Error</span>
                    </div>
                </div>
                <input id="username" type="text" class="block w-full p-3 mb-4 border rounded border-grey-light"
                    name="username" placeholder="username" />

                <input id="email" type="text" class="block w-full p-3 mb-4 border rounded border-grey-light"
                    name="email" placeholder="Email" />

                <input id="password" type="password" class="block w-full p-3 mb-4 border rounded border-grey-light"
                    name="password" placeholder="Password" />
                <input id="confirm-password" type="password"
                    class="block w-full p-3 mb-4 border rounded border-grey-light" name="confirm_password"
                    placeholder="Confirm Password" />

                <button id="signup-btn"
                    class="w-full py-3 my-1 text-center text-white rounded bg-maintheme hover:bg-green-dark focus:outline-none">Create
                    Account
                </button>

                <div class="mt-4 text-sm text-center text-grey-dark">
                    By signing up, you agree to the
                    <a class="no-underline border-b border-grey-dark text-grey-dark" href="#">
                        Terms of Service
                    </a> and
                    <a class="no-underline border-b border-grey-dark text-grey-dark" href="#">
                        Privacy Policy
                    </a>
                </div>
            </div>

            <div class="mt-6 text-grey-dark">
                Already have an account?
                <a class="no-underline border-b border-blue text-blue" href="../login/">
                    Log in
                </a>.
            </div>
        </div>
        <script>var exports = {};</script>
        <script type="module" src="${SCRIPTS}signup.js"></script>
    </div>
</body>

</html>