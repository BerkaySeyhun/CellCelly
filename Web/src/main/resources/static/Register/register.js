function submitForm() {
    // Validate email format
    const emailInput = document.getElementById('email');
    const emailError = document.getElementById('email-error');
    const emailPattern = /^([\w-\.]+@([\w-]+\.)+[\w-]{2,4})?$/;
    const passwordInput = document.getElementById('password');
    const passwordError = document.getElementById('password-error');
    const passwordPattern =/^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[!@#$%^&*()_+\-=[\]{};':"\\|,.<>/?]).{8,}$/;
    const emailPatternIsTrue = emailPattern.test(emailInput.value);
    const passwordPatternIsTrue = passwordPattern.test(passwordInput.value);

    if (! emailPatternIsTrue || ! passwordPatternIsTrue) {
        if(! emailPatternIsTrue){
            emailError.style.display = 'block';
        }else{
            emailError.style.display = 'none';
        }
        if(! passwordPatternIsTrue){
            passwordError.style.display = 'block';
        }else{
            passwordError.style.display = 'none';
        }
        return false; // Prevent form submission
    }else{
        return true;
    }
}