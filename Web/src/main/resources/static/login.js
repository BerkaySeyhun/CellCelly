async function submitForm() {
    // Validate email format
    const passwordInput = document.getElementById('password');
    const passwordError = document.getElementById('password-error');
    const passwordPattern =/^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[!@#$%^&*()_+\-=[\]{};':"\\|,.<>/?]).{8,}$/;

    if (!passwordPattern.test(passwordInput.value)) {
        passwordError.style.display = 'block';
        return false; // Prevent form submission
    } else {
        passwordError.style.display = 'none';

        // const jsondata = {
        //
        // }
        // const response = await fetch("/api/login", {
        //     method: "POST",
        //
        //     cache: "no-cache",
        //     credentials: "same-origin",
        //     headers: {
        //         "Content-Type": "application/json",
        //         // 'Content-Type': 'application/x-www-form-urlencoded',
        //     },
        //     redirect: "follow", // manual, *follow, error
        //     referrerPolicy: "no-referrer", // no-referrer, *no-referrer-when-downgrade, origin, origin-when-cross-origin, same-origin, strict-origin, strict-origin-when-cross-origin, unsafe-url
        //     body: JSON.stringify(data), // body data type must match "Content-Type" header
        // });
        return true; // Allow form submission
    }
}