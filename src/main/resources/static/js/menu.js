function shuffle(count){

    var widthPx = window.innerWidth/2;
    var heightPx = window.innerHeight/2;
    var widthAdditional=window.innerWidth/4;
    var heightAdditional=window.innerHeight/4;

    var matrix = [];
    const width=3
    const height=count/2
    const widthPixel=widthPx/width;
    const heightPixel=heightPx/height;
    for(var i=0; i<height; i++) {
        matrix[i] = [];
        for(var j=0; j<width; j++) {
            matrix[i][j] = 0;
        }
    }

    var tagList = document.getElementById("tag-div").getElementsByTagName("div")
    var tagCount = tagList.length
    var locatedCount=0
    while(locatedCount<tagCount){
        var rH=getRandomNumber(0,height);
        var rW=getRandomNumber(0,width);
        if(matrix[rH][rW]!=0) continue;
        else matrix[rH][rW]=1
        tagList[locatedCount].style.top = rH*heightPixel+heightAdditional+getRandomNumber(0,heightPixel/2) +"px";
        tagList[locatedCount].style.left = rW*widthPixel+widthAdditional+getRandomNumber(0,widthPixel/2) +"px";
        locatedCount++;
    }
}

function getRandomNumber(min, max) {
    return Math.floor(Math.random() * (max - min) + min);
}