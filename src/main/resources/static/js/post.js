function addComment(comment,addFirst){
    const commentLI=document.createElement("li");
    const nameP=document.createElement("p");
    nameP.innerHTML=comment.name;
    nameP.classList.add("text-light")
    const surnameP=document.createElement("p");
    surnameP.innerHTML=comment.surname;
    surnameP.classList.add("text-light")
    const emailP=document.createElement("p");
    emailP.innerHTML=comment.email;
    emailP.classList.add("text-light")
    const contentP=document.createElement("p");
    contentP.innerHTML=comment.content;
    contentP.classList.add("text-light")
    const timeP=document.createElement("p");
    timeP.innerHTML=(new Date(comment.createdAt)).toLocaleString("en-US", {timeZoneName: "short"});
    timeP.classList.add("text-light")
    commentLI.appendChild(nameP);
    commentLI.appendChild(surnameP);
    commentLI.appendChild(emailP);
    commentLI.appendChild(contentP);
    commentLI.appendChild(timeP);
    if(addFirst) document.getElementById("comment-list").prepend(commentLI);
    else document.getElementById("comment-list").append(commentLI);
}

function sendComment(){
    console.log("ok");
    const form=document.getElementById("send-comment");
    $.ajax({
        type: form.method,
        url: form.action,
        data: {
            name: document.getElementById("name").value,
            surname: document.getElementById("surname").value,
            email: document.getElementById("email").value,
            content: document.getElementById("content").value,
            postId: document.getElementById("post-id").value
        },
        success: function(data) {
            const comment = JSON.parse(JSON.stringify(data));
            addComment(comment,true);
        },
        error: function(data) {
            alert(data.responseText);
        }
    });
}

function loadComment(){
    $.ajax({
        type: "GET",
        url: "/api/comment/"+document.getElementById("post-id").value,
        success: function(data) {
            const comments = JSON.parse(JSON.stringify(data));
            comments.forEach((comment) => {
                addComment(comment,false);
            });
        },
        error: function(data) {
            alert(data.responseText);
        }
    });
}

const shiftWindow = function() { scrollBy(0, -50) };
function load() { if (window.location.hash) shiftWindow(); }

function initialize(){
    window.addEventListener("hashchange", shiftWindow);
}
