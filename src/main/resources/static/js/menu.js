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
        let tagDiv = tagList[locatedCount];
        tagDiv.style.top = rH*heightPixel+heightAdditional+((heightPixel-tagDiv.offsetHeight)/2) +"px";
        tagDiv.style.left = rW*widthPixel+widthAdditional+((widthPixel-tagDiv.offsetWidth)/2) +"px";
        randomCircle(tagDiv.getElementsByTagName("canvas")[0])
        locatedCount++;
    }

}

function getRandomNumber(min, max) {
    return Math.floor(Math.random() * (max - min) + min);
}


function randomCircle(canvas) {
    //grain is basically level of detail. higher = finer.
    var w = canvas.width;
    var h = canvas.height;
    var context = canvas.getContext('2d');
    context.beginPath();
    context.arc(w/2, h/2, w/2, 0, 2 * Math.PI, false);
    context.fillStyle = getDarkColor()
    context.fill();
}

function getDarkColor() {
    var h = rand(1, 360);
    var s = rand(0, 100);
    var l = rand(0, 100);
    return 'hsl(' + h + ',' + s + '%,' + l + '%)';
}
function rand(min, max) {
    return min + Math.random() * (max - min);
}

