/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/JavaScript.js to edit this template
 */

console.log('hello');
var socket;
var socketOpenFlag = document.getElementById("hiddenForm:socketOpenFlag");
var roomIdInput = document.getElementById("form2:roomInput");
document.addEventListener('DOMContentLoaded', function () {

    console.log("hello");
    socketOpenFlag = document.getElementById("hiddenForm:socketOpenFlag");
    roomIdInput = document.getElementById("form2:roomInput");
    console.log(roomIdInput, socketOpenFlag, "here");
    initSocket();
});
function handleFileSelect(event) {
    const file = event.target.files[0];
    var iCont = document.querySelectorAll('.attachment-container');
    for (var i = 0; i < iCont.length; i++) {
        iCont[i].remove();
    }

    if (file) {
        if (file.type.includes("image")) {
            const reader = new FileReader();
            reader.onload = function (e) {
                const imgContainer = document.createElement("div");
                imgContainer.className = "attachment-container";
                const img = document.createElement("img");
                img.src = e.target.result;
                img.className = "attachment";
                imgContainer.appendChild(img);
                const crossButton = document.createElement("button");
                crossButton.className = "btn btn-danger btn-sm cross-button";
                crossButton.innerHTML = "×";
                crossButton.addEventListener("click", function () {
                    imgContainer.remove();
                    event.target.value = ""; // Reset the file input's value
                });
                imgContainer.appendChild(crossButton);
                document.querySelector(".chat").appendChild(imgContainer);
            };
            reader.readAsDataURL(file);
        }
    }
}

function uploadFile() {
    var fileInput = document.getElementById("fileInput");
    var selectedFile = fileInput.files[0];
    var reader = new FileReader();
    reader.onload = function (event) {
        var content = event.target.result.split(",")[1];
        var message = {
            type: "file",
            filename: selectedFile.name,
            content: content
        };
        socket.send(JSON.stringify(message));
    };
    reader.readAsDataURL(selectedFile);
}

function initSocket() {
    var roomId = roomIdInput.value;
    console.log(roomId, 'here');
    if (socket) {
        try {
            socket.close();
        } catch (e) {
        }
    }
    socket = new WebSocket('ws://' + window.location.host + '/OzPropertyHub/chat/' + roomId);
    socket.onmessage = function (event) {
        console.log(event.data, 'message');
        const message = JSON.parse(event.data);

        var chats = document.getElementById("chat");
        // Create the new message element structure
        var newMessage = document.createElement('div');
        newMessage.className = 'message incoming';
        var avatar = document.createElement('div');
        avatar.className = 'avatar';
        avatar.textContent = 'JD'; // You can set the initials here
        var messageContent = document.createElement('div');
        messageContent.className = 'message-content';
        var messageText = document.createElement('p');
        messageText.textContent = message.message; // Assuming 'content' is a property of the parsed message



// Construct the structure
        messageContent.appendChild(messageText);

        if (message.attachment) {
            var attachment = document.createElement('img');
            attachment.src = "/OzPropertyHub/uploaded-images/" + message.attachment; // Assuming 'attachment' is a property of the parsed message
            attachment.className = 'attachment';
            messageContent.appendChild(attachment);

        }
        newMessage.appendChild(avatar);
        newMessage.appendChild(messageContent);
        chats.appendChild(newMessage);
    };

    socket.onclose = function () {
        console.log('disconnect');
        socketOpenFlag.value = 'false'; // Set the value to false when the connection is closed
    };
    socket.onerror = function (a, b) {
        console.log(a, b);
    }

    socket.onopen = function (a, b) {
        socketOpenFlag.value = 'true'; // Set the value to true when the connection is open

        console.log('connect');
    }
}
;

function sendMessage() {
    var messageVal = document.getElementById('message-input').value;
    var fileInput = document.getElementById("file-attachment");
    var selectedFile = fileInput.files[0];
    var reader = new FileReader();
    reader.onload = function (event) {
        var content = event.target.result.split(",")[1];
        var message = {
            type: "file",
            filename: selectedFile.name,
            content: content,
            message: messageVal || ""
        };
        socket.send(JSON.stringify(message));
        var iCont = document.querySelectorAll('.attachment-container');
        for (var i = 0; i < iCont.length; i++) {
            iCont[i].remove();
        }
    };
    if (selectedFile)
        reader.readAsDataURL(selectedFile);
    else {
        var message = {
            type: "message",
            message: messageVal
        };
        socket.send(JSON.stringify(message));
    }


    document.getElementById('message-input').value = '';
}
