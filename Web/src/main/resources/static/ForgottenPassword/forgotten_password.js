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
resetPassButton.addEventListener("click", async function (event) {
    const isPhoneValid = phonePattern.test(phoneInput.value);
    const isEmailValid = emailPattern.test(emailInput.value);
    const isSecurityKeyValid = securityKeyPattern.test(securityKeyInput.value);
    if ((isPhoneValid === true && isSecurityKeyValid === true) && isEmailValid === true) {
        try {
            const response = await fetch(`http://35.194.5.106:8080/api/customer/forgotpassword/${phoneInput.value}/${emailInput.value}/${securityKeyInput.value}`, {
                method: "GET"
            });
            if (response.ok) {
                setTimeout(function () {
                    alert("Your password has been sent to the given e-mail, check your mailbox.")
                    window.location.href = "../Login/login.html"
                }, 0);
            } else {
                // Display error message
                alert("Please check your entrances and try again. ");
            }
        } catch (error) {
            console.error("An error occurred:", error);
        }

    } else {
        return false;
    }
});
