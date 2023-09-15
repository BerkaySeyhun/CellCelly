function validate(inputElement, pattern, errorElement) {
    const isValid = pattern.test(inputElement.value);
    if (!isValid && inputElement.value !== '') {
        errorElement.style.display = 'block';
    } else {
        errorElement.style.display = 'none';
    }
    return isValid;
}

const phoneInputUnique = document.getElementById('phone');
const phonePattern = /^5[0-9]{9}$/;
const phoneError = document.getElementById('phone-error');
const submitButton = document.getElementById('submitButton');
const passwordInput = document.getElementById('password');
const passwordError = document.getElementById("password-error");

const rmCheck = document.getElementById("rememberMe");
if (localStorage.checkbox && localStorage.checkbox !== "") {
    rmCheck.setAttribute("checked", "checked");
} else {
    rmCheck.removeAttribute("checked");
}
if (localStorage.username) {
    phoneInputUnique.value = localStorage.username;
} else {
    phoneInputUnique.value = "";
}
rmCheck.addEventListener('change', function () {
    listenRememberMe();
});

function listenRememberMe() {
    if (rmCheck.checked && phoneInputUnique.value !== "") {
        localStorage.username = phoneInputUnique.value;
        localStorage.checkbox = rmCheck.checked;
    } else {
        localStorage.username = "";
        localStorage.checkbox = "";
    }
}

phoneInputUnique.addEventListener("change", function (event) {
    const isPhoneValid = phonePattern.test(phoneInputUnique.value);
    if (isPhoneValid !== true) {
        phoneError.style.display = 'block';
    } else {
        localStorage.setItem("phoneInputValue", phoneInputUnique.value);
        phoneError.style.display = 'none';
    }
});

submitButton.addEventListener("click", async function (event) {

    const isPhoneValid = validate(phoneInputUnique, phonePattern, phoneError);
    if (isPhoneValid && passwordInput.value !== '') {
        passwordError.style.display = 'none';
        window.location.href = "../UserInformation/user_info.html";
        try {
            const response = await fetch(`http://35.194.5.106:8080/api/customer/login/${phoneInputUnique.value}/${passwordInput.value}`, {
                method: "GET"
            });
            if (response.ok) {
                // Wait for 2 seconds and redirect
                setTimeout(function () {
                    window.location.href = "../UserInformation/user_info.html";
                }, 0);
            } else {
                // Display error message
                alert("Please check your entrances and try again. ");
            }
        } catch (error) {
            alert("An error occured ,please check your entrances and try again. ");
            console.error("An error occurred:", error);
        }
    } else {
        passwordError.style.display = 'block';
    }
});









