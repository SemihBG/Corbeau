// noinspection DuplicatedCode

function addComment(comment, addFirst) {
    if (!comment.hasOwnProperty("id") ||
        !comment.hasOwnProperty("name") ||
        !comment.hasOwnProperty("surname") ||
        !comment.hasOwnProperty("email") ||
        !comment.hasOwnProperty("content")) return;
    if(loadedCommentArray.includes(comment.id))
        return;
    loadedCommentCount++
    if (allCommentCount <= loadedCommentCount)
        document.getElementById("load-button").style.display = "none";
    loadedCommentArray.push(comment.id)
    const commentP = document.createElement("a");
    commentP.classList.add("list-group-item");
    commentP.classList.add("list-group-item-action");
    commentP.style.border = "solid #cde3fa 2px";
    commentP.style.background = "#dde8f3";
    commentP.style.marginTop = "1%";
    const imageImg = document.createElement("img");
    imageImg.id = "image-" + comment.name;
    imageImg.src = "/api/image/random/" + comment.name+"-"+comment.surname;
    imageImg.width = 50;
    imageImg.height = 50;
    imageImg.style.border = "solid #cde3fa 2px";
    imageImg.style.display = "inline-block";
    const infoDiv = document.createElement("div");
    infoDiv.style.display = "inline";
    const fullnameP = document.createElement("p");
    fullnameP.innerHTML = comment.name + " " + comment.surname;
    fullnameP.style.display = "inline";
    fullnameP.style.fontSize = "x-large";
    fullnameP.style.marginLeft = "1%";
    const emailP = document.createElement("p");
    emailP.innerHTML = comment.email;
    emailP.style.fontSize = "large";
    emailP.style.marginLeft = "1%";
    const timeP = document.createElement("p");
    timeP.innerHTML = (new Date(comment.createdAt)).toLocaleString("en-US", {
        hour: 'numeric',
        minute: 'numeric',
        year: 'numeric',
        month: 'numeric',
        day: 'numeric'
    });
    timeP.style.float = "right";
    timeP.style.display = "inline";
    const contentP = document.createElement("p");
    contentP.innerHTML = comment.content;
    contentP.style.marginTop="2.5%"
    contentP.style.marginLeft="1%"
    contentP.style.marginRight="1%"
    contentP.style.marginBottom="2.5%"
    contentP.style.fontWeight="lighter"
    infoDiv.appendChild(fullnameP);
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
const emailRegex = RegExp(/\S+@\S+\.\S+/);
const NAME_MIN_LENGTH = 4;
const NAME_MAX_LENGTH = 32;
const SURNAME_MIN_LENGTH = 4;
const SURNAME_MAX_LENGTH = 32;
const EMAIL_MIN_LENGTH = 4;
const EMAIL_MAX_LENGTH = 64;
const CONTENT_MIN_LENGTH = 4;
const CONTENT_MAX_LENGTH = 256;

var loadCount=0;
var loadedCommentCount = 0;
var allCommentCount = 0;

const loadedCommentArray=[];

function sendComment() {

    const commentWarn = document.getElementById("comment-warn");
    commentWarn.style.display = "none";

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
    const name = document.getElementById("name").value.trim();
    const surname = document.getElementById("surname").value.trim();
    const email = document.getElementById("email").value.trim();
    const content = document.getElementById("content").value.trim();
    const postId = document.getElementById("post-id").value.trim();

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
        emailWarn.innerHTML = "Invalid email";
        emailWarn.style.display = "block";
        allValid = false;
    }

    //check content validation
    if (content.length < CONTENT_MIN_LENGTH || content.length > CONTENT_MAX_LENGTH) {
        contentWarn.innerHTML = "Content must be between " +
            CONTENT_MIN_LENGTH + " - " + CONTENT_MAX_LENGTH;
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
            allCommentCount++
            updateAllCommentCount(allCommentCount)
        },
        error: function (data) {
            const errorResonse = JSON.parse(JSON.stringify(data.responseJSON));
            commentWarn.style.display = "block";
            errorResonse.fieldErrors.forEach((fieldError) => {
                commentWarn.innerHTML += commentWarn.innerHTML + fieldError.message;
            })
        }
    });

}

function loadComments() {
    $.ajax({
        type: "GET",
        url: "/api/comment/" + document.getElementById("post-id").value + "?p=" + (loadCount + 1),
        success: function (data) {
            const comments = JSON.parse(JSON.stringify(data));
            comments.forEach((comment) => {
                addComment(comment, false);
            });
            loadCount++;
            document.getElementById("loaded-count").style.display = "inline";
            document.getElementById("loaded-count").innerHTML = loadedCommentCount + " loaded";
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

function initialize(commnetCount) {

    window.addEventListener("hashchange", shiftWindow);
    allCommentCount=commnetCount
    $('img.content-scalable').addClass('img-enlargeable').click(function () {
        var src = $(this).attr('src');
        var modal;

        function removeModal() {
            modal.remove();
            $('body').off('keyup.modal-close');
        }

        modal = $('<div>').css({
            background: 'RGBA(0,0,0,.5) url(' + src + ') no-repeat center',
            backgroundSize: 'contain',
            width: '100%',
            height: '100%',
            position: 'fixed',
            zIndex: '10000',
            top: '0',
            left: '0',
            cursor: 'zoom-out'
        }).click(function () {
            removeModal();
        }).appendTo('body');
        //handling ESC
        $('body').on('keyup.modal-close', function (e) {
            if (e.key === 'Escape') {
                removeModal();
            }
        });
    });


}


function updateAllCommentCount(commentCount){
    document.getElementById("allCommentCount").innerHTML="All comments: "+commentCount
}