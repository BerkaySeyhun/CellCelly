const resetPassButton = document.getElementById('resetPasswordButton');
const securityKeyError = document.getElementById('securityKey-error')
const phonePattern = /^5[0-9]{9}$/;
const emailPattern = /^([\w-\.]+@([\w-]+\.)+[\w-]{2,4})?$/;
const securityKeyPattern = /^[0-9]{6}$/;

const phoneInput = document.getElementById('phone');
const phoneError = document.getElementById('phone-error');

const emailError = document.getElementById('email-error');
const emailInput = document.getElementById('email');

const securityKeyInput = document.getElementById('securityKey');

phoneInput.addEventListener("change", function (event) {
    const isPhoneValid = phonePattern.test(phoneInput.value);
    if (isPhoneValid !== true) {
        phoneError.style.display = 'block';
    } else {
        phoneError.style.display = 'none';
    }
});
emailInput.addEventListener("change", function (event) {
    const isEmailValid = emailPattern.test(emailInput.value);
    if (isEmailValid !== true) {
        emailError.style.display = 'block';
    } else {
        emailError.style.display = 'none';
    }
});
securityKeyInput.addEventListener("change", function (event) {
    const isSecurityKeyValid = securityKeyPattern.test(securityKeyInput.value);
    if (isSecurityKeyValid !== true) {
        securityKeyError.style.display = 'block';
    } else {
        securityKeyError.style.display = 'none';
    }
});
resetPassButton.addEventListener("click", function (event){
    const isPhoneValid = phonePattern.test(phoneInput.value);
    const isEmailValid = emailPattern.test(emailInput.value);
    const isSecurityKeyValid = securityKeyPattern.test(securityKeyInput.value);
    if( (isPhoneValid === true && isSecurityKeyValid === true) && isEmailValid === true){
        alert("Åifreniz baÅŸarÄ±yla deÄŸiÅŸtirildi.")
        window.location.href = "../Login/login.html"
    }else{
        return false;
    }
});
