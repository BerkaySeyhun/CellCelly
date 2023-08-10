const phoneInput = document.getElementById('phone');
const phonePattern = /^5[0-9]{9}$/;
const phoneError = document.getElementById('phone-error');
const submitButton = document.getElementById('submitButton');
const passwordInput = document.getElementById('password');
const passwordError = document.getElementById("password-error");

{
    const rmCheck = document.getElementById("rememberMe");
    if (localStorage.checkbox && localStorage.checkbox !== "") {
        rmCheck.setAttribute("checked", "checked");
        // phoneInput.value = localStorage.username;
    } else {
        rmCheck.removeAttribute("checked");
        // phoneInput.value = "";
    }
    if (localStorage.username) {
        phoneInput.value = localStorage.username;
    } else {
        phoneInput.value = "";
    }
    rmCheck.addEventListener('change', function () {
        listenRememberMe();
    });

    function listenRememberMe() {
        if (rmCheck.checked && phoneInput.value !== "") {
            localStorage.username = phoneInput.value;
            localStorage.checkbox = rmCheck.checked;
        } else {
            localStorage.username = "";
            localStorage.checkbox = "";
        }
    }
}// Remember me.

function validate(inputElement, pattern, errorElement) {
    const isValid = pattern.test(inputElement.value);
    if (!isValid && inputElement.value !== '') {
        errorElement.style.display = 'block';
    } else {
        errorElement.style.display = 'none';
    }
    return isValid;
}

submitButton.addEventListener("click", async function (event) {
    const isPhoneValid = validate(phoneInput, phonePattern, phoneError);
    if (isPhoneValid && passwordInput.value !== '') {
        passwordError.style.display = 'none';
        try {
            const response = await fetch(`http://35.194.5.106:8080/api/customer/login/${phoneInput.value}/${passwordInput.value}`, {
                method: "GET"
            });
            if (response.ok) {
                // Display success message
                alert("BaÅŸarÄ±yla giriÅŸ yaptÄ±nÄ±z!");
                // Wait for 2 seconds and redirect
                setTimeout(function () {
                    window.location.href = "../UserInformation/user_info.html";
                }, 800);
            } else {
                // Display error message
                alert("GiriÅŸ bilgilerinizi kontrol ederek tekrar deneyiniz.");
            }
        } catch (error) {
            console.error("An error occurred:", error);
        }
    }else{
        passwordError.style.display = 'block';
    }
});
// http://35.194.5.106:8080/api/customer/login/5051937182/1234abcD.


phoneInput.addEventListener("change", function (event) {
    const isPhoneValid = phonePattern.test(phoneInput.value);
    if (isPhoneValid !== true) {
        phoneError.style.display = 'block';
    } else {
        phoneError.style.display = 'none';
    }
});
// passwordInput.addEventListener("change", function (event) {
//     if (passwordInput.value !== '') {
//         passwordError.style.display = 'block';
//     } else {
//         passwordError.style.display = 'none';
//     }
// });




