function addComment(comment,addFirst){
    const commentP=document.createElement("a");
    commentP.classList.add("list-group-item");
    commentP.classList.add("list-group-item-action");
    commentP.classList.add("bg-dark");
    const imageImg=document.createElement("img");
    imageImg.id="image-"+comment.name;
    imageImg.src="/api/image/random/"+comment.name;
    imageImg.width=50;
    imageImg.height=50;
    imageImg.style.border="3px solid #17a2b8";
    imageImg.style.display="inline-block";
    const infoDiv=document.createElement("div");
    infoDiv.style.display="inline";
    const nameP=document.createElement("p");
    nameP.innerHTML=comment.name;
    nameP.classList.add("text-light");
    nameP.style.display="inline";
    nameP.style.fontSize="19px";
    nameP.style.marginLeft="10px";
    const surnameP=document.createElement("p");
    surnameP.innerHTML=comment.surname;
    surnameP.classList.add("text-light");
    surnameP.style.display="inline";
    surnameP.style.fontSize="19px";
    surnameP.style.marginLeft="10px";
    const timeP=document.createElement("p");
    timeP.innerHTML=(new Date(comment.createdAt)).toLocaleString("en-US", {timeZoneName: "short"});
    timeP.classList.add("text-light");
    timeP.style.float="right";
    timeP.style.display="inline";
    const emailP=document.createElement("p");
    emailP.innerHTML=comment.email;
    emailP.classList.add("text-light");
    emailP.style.fontSize="15px";
    emailP.style.marginLeft="10px";
    const contentP=document.createElement("p");
    contentP.innerHTML=comment.content;
    contentP.classList.add("text-light");
    infoDiv.appendChild(nameP);
    infoDiv.appendChild(surnameP);
    infoDiv.appendChild(timeP);
    infoDiv.appendChild(emailP);
    commentP.appendChild(imageImg);
    commentP.appendChild(infoDiv);
    commentP.appendChild(contentP);

    if(addFirst) document.getElementById("comment-div").prepend(commentP);
    else document.getElementById("comment-div").append(commentP);

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
            document.getElementById("comment-load").style.display="none";
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
