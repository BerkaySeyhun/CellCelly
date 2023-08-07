const phoneInput = document.getElementById('phone'); // Form elemanını burada tanımlıyoruz
const passwordInput = document.getElementById('password');
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
    lsRememberMe();
});

function lsRememberMe() {
    if (rmCheck.checked && phoneInput.value !== "") {
        localStorage.username = phoneInput.value;
        localStorage.checkbox = rmCheck.checked;
    } else {
        localStorage.username = "";
        localStorage.checkbox = "";
    }
}
const phonePattern = /^5[0-9]{9}$/;

function submitForm() {
    console.log("submitform çalıştı.");
    const isPhoneValid = phonePattern.test(phoneInput.value);
    if(isPhoneValid && (passwordInput.value !== "")){
        window.location.href = "../UserInformation/user_info.html";
        return true;
    }
}


