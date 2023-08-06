function togglePasswordVisibility() {
    const passwordInput = document.getElementById("newPassword"),
        passwordInput2 = document.getElementById("newPasswordAgain");

    if (passwordInput.type === "password") {
        passwordInput.type = "text";
        passwordInput2.type = "text";
    } else {
        passwordInput.type = "password";
        passwordInput2.type = "password";
    }
}

function submitForm() {
    // Validate email format
    const successMessage = document.getElementById('succes-message');
    const passwordInput = document.getElementById('newPassword');
    const passwordClone = document.getElementById('newPasswordAgain');
    const passwordMatchError = document.getElementById('password-match-error');
    const passwordError = document.getElementById('password-error');
    const passwordPattern = /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[!@#$%^&*()_+\-=[\]{};':"\\|,.<>/?]).{8,}$/;
    const passwordPatternIsTrue = passwordPattern.test(passwordInput.value);

    if (passwordInput !== passwordClone) {
        passwordMatchError.style.display = 'block';
        return false;
    }else if (!passwordPatternIsTrue) {
        passwordError.style.display = 'block';
        return false;
    } else {
        passwordError.style.display = 'none';
        successMessage.style.display = 'block';
        return true;
    }
}