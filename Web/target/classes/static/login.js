function submitForm() {
    // Validate email format
    const passwordInput = document.getElementById('password');
    const passwordError = document.getElementById('password-error');
    const passwordPattern =/^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[!@#$%^&*()_+\-=[\]{};':"\\|,.<>/?]).{8,}$/;

    if (!passwordPattern.test(passwordInput.value)) {
        passwordError.style.display = 'block';
        return false; // Prevent form submission
    } else {
        passwordError.style.display = 'none';
        return true; // Allow form submission
    }
}