function initialize(){
    var tagList=document.getElementsByClassName("tag-list")[0]
    var tags=tagList.getElementsByTagName("li")
    for (let i = 0; i < tags.length; i++) {
        tags[i].style.marginRight=(Math.floor(Math.random() * 60) + 20)+"px";
        tags[i].style.marginLeft=(Math.floor(Math.random() * 50))+"px";
        tags[i].style.marginTop=(Math.floor(Math.random() * 10) + 10)+"px";
        tags[i].style.marginBottom=(Math.floor(Math.random() * 10))+"px";
    }
}

