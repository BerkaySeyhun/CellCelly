function circularBar(remainingData, totalData, elementID, dataType) {
    const dataRatio = remainingData / totalData;
    const circle = document.querySelector(`#${elementID} circle`);
    const circleLength = circle.getTotalLength();
    const offset = circleLength * (dataRatio);

    const textX = 90;
    const textY = 90; // Yuvarlak çubuğun merkezine yerleşti
    // Calculate remaining internet text
    const text = document.createElementNS("http://www.w3.org/2000/svg", "text");
    text.setAttribute("x", textX);
    text.setAttribute("y", textY);
    text.setAttribute("text-anchor", "middle");
    text.setAttribute("dominant-baseline", "middle");
    text.setAttribute("font-family", "Arial");
    text.setAttribute("font-size", "18px");
    text.setAttribute("fill", "#666f85");
    text.textContent = remainingData + ` ${dataType}`; // Kullanılacak metin
    circle.parentNode.appendChild(text); // SVG içine ekleme
}

const mb = document.getElementById("dataChart").getAttribute("data-type");
const sms = document.getElementById("smsChart").getAttribute("data-type");
const min = document.getElementById("voiceChart").getAttribute("data-type");


const phoneInput = localStorage.getItem("phoneInputValue");
console.log(phoneInput);
document.addEventListener("DOMContentLoaded", async function () {
    try {
        const response = await fetch(`http://35.194.5.106:8080/api/balance/getuserbalance/${phoneInput}`, {
            method: "GET"
        });

        if (!response.ok) {
            throw new Error("API request failed");
        }

        const data = await response.json();
        console.log(data);
        {
            document.getElementById("userName").textContent = data[0].username;
            document.getElementById("phoneNumber").textContent = data[0].msisdn;
            document.getElementById("packageName").textContent = data[0].packageName;
            document.getElementById("dueTime").textContent = data[0].edate;
            document.getElementById("voice").textContent = data[0].minute;
            document.getElementById("totalVoice").textContent = data[0].minute;
            document.getElementById("sms").textContent = data[0].sms;
            document.getElementById("totalSms").textContent = data[0].packageSms;
            document.getElementById("internet").textContent = data[0].data;
            document.getElementById("totalInternet").textContent = data[0].packageData;
        } // fetched datas
        // Update package details

        circularBar(data[0].data, data[0].packageData, "dataChart", mb);
        circularBar(data[0].sms, data[0].packageSms, "smsChart", sms);
        circularBar(data[0].data, data[0].packageData, "voiceChart", min);
        {

        }
    } catch (error) {
        console.error("An error occurred:", error);
    }
});

{
    const changePasswordButton = document.getElementById('changePasswordButton');
    changePasswordButton.addEventListener("click", function () {
        window.location.href = "../ChangePassword/change_password.html";
    });
    const exitButton = document.getElementById('exitButton');
    exitButton.addEventListener("click", function () {
        window.location.href = "../Login/login.html";
    });
}
