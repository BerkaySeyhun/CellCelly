const fnameInput = document.getElementById("fname");
const lnameInput = document.getElementById("lname");
const phoneInput = document.getElementById("phone");
const passwordInput = document.getElementById("password");
const emailInput = document.getElementById("email");
const securitKeyInput = document.getElementById("securityKey");
const packageInput = document.getElementById("package");

const fnameError = document.getElementById("fname-error");
const lnameError = document.getElementById("lname-error");
const phoneError = document.getElementById("phone-error");
const passwordError = document.getElementById("password-error");
const emailError = document.getElementById("email-error");
const securityKeyError = document.getElementById("securityKey-error");
const packageError = document.getElementById("package-error");

const fnamePattern = /^[a-zA-ZğüşıöçĞÜŞİÖÇ]+$/;
const lnamePattern = fnamePattern;
const phonePattern = /^5[0-9]{9}$/;
const passwordPattern = /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[!@#$%^&*()_+\-=[\]{};':"\\|,.<>/?]).{8,}$/;
const emailPattern = /^([\w-]+@([\w-]+\.)+[\w-]{2,4})?$/;
const securityKeyPattern = /^[0-9]{6}$/;

const registerButton = document.getElementById("registerButton");

function validate(inputElement, pattern, errorElement) {
    const isValid = pattern.test(inputElement.value);
    if (!isValid && inputElement.value !== '') {
        errorElement.style.display = 'block';
    } else {
        errorElement.style.display = 'none';
    }
    return isValid;
}

lnameInput.addEventListener("change", function () {
    validate(lnameInput, lnamePattern, lnameError);
});

fnameInput.addEventListener("change", function () {
    validate(fnameInput, fnamePattern, fnameError);
});

phoneInput.addEventListener("change", function () {
    validate(phoneInput, phonePattern, phoneError);
});

passwordInput.addEventListener("change", function () {
    validate(passwordInput, passwordPattern, passwordError);
});

emailInput.addEventListener("change", function () {
    validate(emailInput, emailPattern, emailError);
});
let checkValid;
securitKeyInput.addEventListener("change", function () {
    validate(securitKeyInput, securityKeyPattern, securityKeyError);
});
// let checkValid;
packageInput.addEventListener("change", function () {

});

registerButton.addEventListener("click", function () {
    let checkLnameValid = validate(lnameInput, lnamePattern, lnameError);
    let checkFnameValid = validate(fnameInput, fnamePattern, fnameError);
    let checkPhoneValid = validate(phoneInput, phonePattern, phoneError);
    let checkPasswordValid = validate(passwordInput, passwordPattern, passwordError);
    let checkEmailValid = validate(emailInput, emailPattern, emailError);
    let checkSecurityKeyValid = validate(securitKeyInput, securityKeyPattern, securityKeyError);

    if (checkLnameValid && checkFnameValid && checkPhoneValid && checkPasswordValid && checkEmailValid && checkSecurityKeyValid ) {
        const myVar = {
            "PACKAGE_ID": packageInput.value,
            "MSISDN": phoneInput.value,
            "NAME": fnameInput.value,
            "SURNAME": lnameInput.value,
            "EMAIL": emailInput.value,
            "PASSWORD": passwordInput.value,
            "SECURITY_KEY": securitKeyInput.value
        };
        console.log("fetch çalıştı");
        fetch("http://35.194.5.106:8080/api/customer/register", {
            method: "POST",
            headers: {
                "Content-type": "application/json"
            },
            body: JSON.stringify(myVar)
        })
            .then((data) => {
                console.log(data.statusText); // Gönderilen myVar değerlerinin cevabını konsola yazdırır
                alert("Başarıyla kayıt oldunuz.");
                registerButton.disabled = true;
            })
            .catch((error) => {
                console.error("Bir hata oluştu:", error);
            });
    }

});

const exitButton = document.getElementById('exitButton');
exitButton.addEventListener("click", function (event) {
    window.location.href = "../Login/login.html"
});


