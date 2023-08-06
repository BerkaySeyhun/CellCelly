// Validate email format
// function submitForm() {
//     const phoneInput = document.getElementById('phone');
//     const passwordInput = document.getElementById('password');
//     const passwordError = document.getElementById('password-error');
//     const passwordPattern = /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[!@#$%^&*()_+\-=[\]{};':"\\|,.<>/?]).{8,}$/;
//     const validPattern = passwordPattern.test(passwordInput.value);
//     const url = "http://35.194.5.106:8080/api/customer/login";
//     if (!validPattern) {
//         passwordError.style.display = 'block';
//         return false;
//     } else {
//         passwordError.style.display = 'none';
//
//         window.location.href = "user_info.html";
//
//         return true;
//
//         // return true; // burada sayfaya yönlendir.
//     }
// }


//$ curl -X POST http://35.194.5.106:8080/api/customer/login -H "Content-Type: application/json" -d
// '{"MSISDN":"5539330921","PASSWORD":"12345"}'
// function submitForm() {
//     console.log("çalıştı");
//     const passwordError = document.getElementById('password-error');
// const passwordPattern = /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[!@#$%^&*()_+\-=[\]{};':"\\|,.<>/?]).{8,}$/;
// const validPattern = passwordPattern.test(passwordInput.value);
// const url = "http://35.194.5.106:8080/api/customer/login";

// if (!validPattern) {
//     passwordError.style.display = 'block';
//     return false;
// } else {
//     passwordError.style.display = 'none';
//
//     window.location.href = "user_info.html";
//     // return true;
//     // return true; // burada sayfaya yönlendir.
// }

// document.getElementById('submitButton').addEventListener('click', () => {
//
//     console.log("çalıştı");
//     const phoneInput = document.getElementById('phone').value;
//     const passwordInput = document.getElementById('password').value;
//
//     const data = {
//         phone: phoneInput,
//         password: passwordInput
//     };
//     console.log(data);
//     fetch('http://35.194.5.106:8080/api/customer/login', {
//         method: 'POST',
//         headers: {
//             'Content-Type': 'application/json'
//         },
//         body: JSON.stringify(data)
//     })
//         .then(response => {
//             if (!response.ok) {
//                 throw new Error('Network response was not ok');
//             } else if (response.ok) {
//                 console.log("response is ok.");
//             }
//             console.log("response if sonrası");
//             return response.json();
//         })
//         .then(data => {
//             // Middleware'den dönen yanıtı burada kullanabilirsiniz.
//             console.log(data);
//         })
//         .catch(error => {
//             // Hata durumunda burada işlem yapabilirsiniz.
//             console.error('Fetch API Hatası:', error);
//         });
//
// });
const phoneInput = document.getElementById('phone'); // Form elemanını burada tanımlıyoruz
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


// const jsondata = {
//
// }
// const response = await fetch("/api/login", {
//     method: "POST",
//
//     cache: "no-cache",
//     credentials: "same-origin",
//     headers: {
//         "Content-Type": "application/json",
//         // 'Content-Type': 'application/x-www-form-urlencoded',
//     },
//     redirect: "follow", // manual, *follow, error
//     referrerPolicy: "no-referrer", // no-referrer, *no-referrer-when-downgrade, origin, origin-when-cross-origin, same-origin, strict-origin, strict-origin-when-cross-origin, unsafe-url
//     body: JSON.stringify(data), // body data type must match "Content-Type" header
// });

