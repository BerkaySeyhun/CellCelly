// function submitForm() {
// Validate email format
// const emailInput = document.getElementById('email');
// const emailError = document.getElementById('email-error');
// const emailPattern = /^([\w-\.]+@([\w-]+\.)+[\w-]{2,4})?$/;
// const passwordInput = document.getElementById('password');
// const passwordError = document.getElementById('password-error');
// const passwordPattern =/^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[!@#$%^&*()_+\-=[\]{};':"\\|,.<>/?]).{8,}$/;
// const emailPatternIsTrue = emailPattern.test(emailInput.value);
// const passwordPatternIsTrue = passwordPattern.test(passwordInput.value);
// const fnameInput = document.getElementById('fname'),
//     lnameInput = document.getElementById('lname'),
//     phoneInput = document.getElementById('phone'),
//     securityKeyInput = document.getElementById('securityKey');


const emailInput = document.getElementById('email');
const emailError = document.getElementById('email-error');
const emailPattern = /^([\w-\.]+@([\w-]+\.)+[\w-]{2,4})?$/;
const passwordInput = document.getElementById('password');
const passwordError = document.getElementById('password-error');
const passwordPattern = /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[!@#$%^&*()_+\-=[\]{};':"\\|,.<>/?]).{8,}$/;

function submitForm() {
    const emailPatternIsTrue = emailPattern.test(emailInput.value);
    const passwordPatternIsTrue = passwordPattern.test(passwordInput.value);
    if (!emailPatternIsTrue || !passwordPatternIsTrue) {
        if (!emailPatternIsTrue) {
            emailError.style.display = 'block';
        } else {
            emailError.style.display = 'none';
        }
        if (!passwordPatternIsTrue) {
            passwordError.style.display = 'block';
        } else {
            passwordError.style.display = 'none';
        }
        return false; // Prevent form submission
    } else {
        return true;
    }
}

// const myVar = {
//     "PACKAGE_ID": "10",
//     "MSISDN": "5362222222",
//     "NAME": "ERKAM",
//     "SURNAME": "DOGRUL",
//     "EMAIL": "ASDASDAS@gmail.com",
//     "PASSWORD": "mysecretpassword",
//     "SECURITY_KEY": "YK"
// };
// console.log("fetch çalıştı");
// fetch("http://35.194.5.106:8080/api/customer/register", {
//     method: "POST",
//     headers: {
//         "Content-type": "application/json"
//     },
//     body: JSON.stringify(myVar)
// })
//     .then((response) => response.json())
//     .then((json) => console.log(json)).then(() => console.log("bittttti"))



