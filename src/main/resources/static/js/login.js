
const NAME_MIN_LENGTH=5;
const NAME_MAX_LENGTH=25;
const NAME_REGEX=new RegExp(/^[0-9a-z]+$/i);
const PASSWORD_MIN_LENGTH=7;
const PASSWORD_MAX_LENGTH=27;

function checkFormValidation() {
    const nameWarn= document.getElementById("name-warn");
    const passwordWarn = document.getElementById("password-warn");
    nameWarn.style.display = "none";
    passwordWarn.style.display = "none";
    let post = true;
    const nameInput = document.getElementById("name");
    const passwordInput = document.getElementById("password");
    if (nameInput.value.length < NAME_MIN_LENGTH || nameInput.value.length> NAME_MAX_LENGTH) {
        nameWarn.innerHTML="Name must be between "+NAME_MIN_LENGTH+"-"+NAME_MAX_LENGTH;
        console.log(nameWarn.innerHTML);
        nameWarn.style.display = "block";
        post=false;
    }else if(!NAME_REGEX.test(nameInput.value)){
        nameWarn.innerHTML="Name must consist of letters and numbers";
        console.log(nameWarn.innerHTML);
        nameWarn.style.display = "block";
        post=false;
    }
    if (passwordInput.value.length < PASSWORD_MIN_LENGTH || passwordInput.value.length>PASSWORD_MAX_LENGTH) {
        passwordWarn.innerHTML="Password must be between "+PASSWORD_MIN_LENGTH+"-"+PASSWORD_MAX_LENGTH;
        console.log(passwordWarn.innerHTML);
        passwordWarn.style.display = "block";
        post=false;
    }
    console.log("checkFormValidation, post: "+post);
    if(post){
        document.getElementById("login").submit();
    }
}

