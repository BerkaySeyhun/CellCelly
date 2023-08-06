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
//         window.location.href = "main_page.html";
//
//         return true;
//
//         // return true; // burada sayfaya yönlendir.
//     }
// }


//$ curl -X POST http://35.194.5.106:8080/api/customer/login -H "Content-Type: application/json" -d
// '{"MSISDN":"5539330921","PASSWORD":"12345"}'
function submitForm() {

    const passwordError = document.getElementById('password-error');
    const passwordPattern = /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[!@#$%^&*()_+\-=[\]{};':"\\|,.<>/?]).{8,}$/;
    const validPattern = passwordPattern.test(passwordInput.value);
    const url = "http://35.194.5.106:8080/api/customer/login";

    if (!validPattern) {
        passwordError.style.display = 'block';
        return false;
    } else {
        passwordError.style.display = 'none';

        window.location.href = "main_page.html";
        // return true;
        // return true; // burada sayfaya yönlendir.
    }

    document.getElementById('submitBtn').addEventListener('click', () => {
        const phoneInput = document.getElementById('phone').value;
        const passwordInput = document.getElementById('password').value;

        const data = {
            phone: phoneInput,
            password: passwordInput
        };
        fetch('http://35.194.5.106:8080/api/customer/login', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(data)
        })
            .then(response => {
                if (!response.ok) {
                    throw new Error('Network response was not ok');
                }
                return response.json();
            })
            .then(data => {
                // Middleware'den dönen yanıtı burada kullanabilirsiniz.
                console.log(data);
            })
            .catch(error => {
                // Hata durumunda burada işlem yapabilirsiniz.
                console.error('Fetch API Hatası:', error);
            });


    });
}

// document.getElementById('myForm').addEventListener('submit',function (event){
//     const username = document.getElementById('username').value;
//     const password = document.getElementById('password').value;
//
// })




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

