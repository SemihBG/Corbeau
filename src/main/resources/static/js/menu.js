function initialize(){
    var tagList=document.getElementsByClassName("tag-list")[0]
    var tags=tagList.getElementsByTagName("li")
    var ul = document.getElementById("tags")
    for (var i = ul.children.length; i >= 0; i--) {
        ul.appendChild(ul.children[Math.random() * i | 0]);
    }
    for (let i = 0; i < tags.length; i++) {
        tags[i].style.marginRight=(Math.floor(Math.random() * 63) + 23)+"px";
        tags[i].style.marginLeft=(Math.floor(Math.random() * 53))+"px";
        tags[i].style.marginTop=(Math.floor(Math.random() * 11) + 11)+"px";
        tags[i].style.marginBottom=(Math.floor(Math.random() * 11))+"px";
    }
}

