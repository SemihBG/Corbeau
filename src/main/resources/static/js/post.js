// noinspection DuplicatedCode

function addComment(comment, addFirst) {
    const commentP = document.createElement("a");
    commentP.classList.add("list-group-item");
    commentP.classList.add("list-group-item-action");
    commentP.classList.add("bg-dark");
    const imageImg = document.createElement("img");
    imageImg.id = "image-" + comment.name;
    imageImg.src = "/api/image/random/" + comment.name;
    imageImg.width = 50;
    imageImg.height = 50;
    imageImg.style.border = "3px solid #17a2b8";
    imageImg.style.display = "inline-block";
    const infoDiv = document.createElement("div");
    infoDiv.style.display = "inline";
    const nameP = document.createElement("p");
    nameP.innerHTML = comment.name;
    nameP.classList.add("text-light");
    nameP.style.display = "inline";
    nameP.style.fontSize = "19px";
    nameP.style.marginLeft = "10px";
    const surnameP = document.createElement("p");
    surnameP.innerHTML = comment.surname;
    surnameP.classList.add("text-light");
    surnameP.style.display = "inline";
    surnameP.style.fontSize = "19px";
    surnameP.style.marginLeft = "10px";
    const timeP = document.createElement("p");
    timeP.innerHTML = (new Date(comment.createdAt)).toLocaleString("en-US", {timeZoneName: "short"});
    timeP.classList.add("text-light");
    timeP.style.float = "right";
    timeP.style.display = "inline";
    const emailP = document.createElement("p");
    emailP.innerHTML = comment.email;
    emailP.classList.add("text-light");
    emailP.style.fontSize = "15px";
    emailP.style.marginLeft = "10px";
    const contentP = document.createElement("p");
    contentP.innerHTML = comment.content;
    contentP.classList.add("text-light");
    infoDiv.appendChild(nameP);
    infoDiv.appendChild(surnameP);
    infoDiv.appendChild(timeP);
    infoDiv.appendChild(emailP);
    commentP.appendChild(imageImg);
    commentP.appendChild(infoDiv);
    commentP.appendChild(contentP);

    if (addFirst) document.getElementById("comment-div").prepend(commentP);
    else document.getElementById("comment-div").append(commentP);

}

const nameRegex = RegExp(/^[A-Za-z]+$/);
const surnameRegex = RegExp(/^[A-Za-z]+$/);
const emailRegex = RegExp(/^[A-Za-z]+$/);
const contentRegex = RegExp(/^[A-Za-z]+$/);
const NAME_MIN_LENGTH = 5;
const NAME_MAX_LENGTH = 25;
const SURNAME_MIN_LENGTH = 5;
const SURNAME_MAX_LENGTH = 25;
const EMAIL_MIN_LENGTH = 5;
const EMAIL_MAX_LENGTH = 25;
const CONTENT_MIN_LENGTH = 5;
const CONTENT_MAX_LENGTH = 25;


function sendComment() {

    //display none all warn
    const nameWarn = document.getElementById("name-warn");
    nameWarn.style.display = "none";
    const surnameWarn = document.getElementById("surname-warn");
    surnameWarn.style.display = "none";
    const emailWarn = document.getElementById("email-warn");
    emailWarn.style.display = "none";
    const contentWarn = document.getElementById("content-warn");
    contentWarn.style.display = "none";

    const form = document.getElementById("send-comment");

    //get values
    const name = document.getElementById("name").value;
    const surname = document.getElementById("surname").value;
    const email = document.getElementById("email").value;
    const content = document.getElementById("content").value;
    const postId = document.getElementById("post-id").value;

    //-values validation
    let allValid = true;

    //check name validation
    if (name.length < NAME_MIN_LENGTH || name.length > NAME_MAX_LENGTH) {
        nameWarn.innerHTML = "Name must be between " +
            NAME_MIN_LENGTH + " - " + NAME_MAX_LENGTH;
        nameWarn.style.display = "block";
        allValid = false;
    } else if (!nameRegex.test(name)) {
        name.innerHTML = "Name contains illegal character";
        nameWarn.style.display = "block";
        allValid = false;
    }

    //check surname validation
    if (surname.length < SURNAME_MIN_LENGTH || surname.length > SURNAME_MAX_LENGTH) {
        surnameWarn.innerHTML = "Surame must be between " +
            SURNAME_MIN_LENGTH + " - " + SURNAME_MAX_LENGTH;
        surnameWarn.style.display = "block";
        allValid = false;
    } else if (!surnameRegex.test(surname)) {
        surnameWarn.innerHTML = "Name contains illegal character";
        surnameWarn.style.display = "block";
        allValid = false;
    }

    //check email validation
    if (email.length < EMAIL_MIN_LENGTH || email.length > EMAIL_MAX_LENGTH) {
        emailWarn.innerHTML = "Email must be between " +
            EMAIL_MIN_LENGTH + " - " + EMAIL_MAX_LENGTH;
        emailWarn.style.display = "block";
        allValid = false;
    } else if (!emailRegex.test(email)) {
        emailWarn.innerHTML = "Email contains illegal character";
        emailWarn.style.display = "block";
        allValid = false;
    }

    //check content validation
    if (content.length < CONTENT_MIN_LENGTH || content.length > CONTENT_MAX_LENGTH) {
        contentWarn.innerHTML = "Content must be between " +
            NAME_MIN_LENGTH + " - " + NAME_MAX_LENGTH;
        contentWarn.style.display = "block";
        allValid = false;
    } else if (!contentRegex.test(content)) {
        contentWarn.innerHTML = "Content contains illegal character";
        contentWarn.style.display = "block";
        allValid = false;
    }

    if (!allValid) return;

    //send post to server
    $.ajax({
        type: form.method,
        url: form.action,
        data: {
            name: name,
            surname: surname,
            email: email,
            content: content,
            postId: postId
        },
        success: function (data) {
            const comment = JSON.parse(JSON.stringify(data));
            addComment(comment, true);
        },
        error: function (data) {
            alert(data.responseText);
        }
    });

}

function loadComment() {
    $.ajax({
        type: "GET",
        url: "/api/comment/" + document.getElementById("post-id").value,
        success: function (data) {
            const comments = JSON.parse(JSON.stringify(data));
            comments.forEach((comment) => {
                addComment(comment, false);
            });
            document.getElementById("comment-load").style.display = "none";
        },
        error: function (data) {
            alert(data.responseText);
        }
    });
}

//fragment navbar -50y scroll
const shiftWindow = function () {
    scrollBy(0, -50)
};

function load() {
    if (window.location.hash) shiftWindow();
}

function initialize() {
    window.addEventListener("hashchange", shiftWindow);
}


