const exitButton = document.getElementById('exitButton');
exitButton.addEventListener("click", function (event){
    window.location.href = "../Login/login.html"
});

const changePasswordButton = document.getElementById('changePasswordButton');
changePasswordButton.addEventListener("click",function (event){
   window.location.href = "../ChangePassword/change_password.html"
});