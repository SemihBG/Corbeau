let name = "";
let ext = "";
let isNameInputActivated = false;
let isContentSelected = false;
let isContentLabelActivated = false;
let isSaveButtonActivated = false;

const NAME_MIN_LENGTH = 5;
const NAME_MAX_LENGTH = 64;
const NAME_REGEX = new RegExp(/^[0-9a-z-]+$/i);

function onContentChanged() {
    const fullPath = document.getElementById("content").value;
    const extIndex = fullPath.lastIndexOf(".");
    ext = fullPath.substring(extIndex + 1);
    isContentSelected = true;
    updateContentLabel();
    updateNameInput();
    updateSaveButton();
}

function onNameTyped(nameInput) {
    name = nameInput.value;
    updateContentLabel();
    updateNameInput();
    updateSaveButton();
}

function updateContentLabel() {
    const updateLabel = document.getElementById("content-label");
    let activated = ext !== "";
    if (name !== "" && activated)
        document.getElementById("content-label").innerHTML = name + '.' + ext;
    else if (name !== "")
        document.getElementById("content-label").innerHTML = name;
    else if (activated)
        document.getElementById("content-label").innerHTML = '.' + ext;
    else
        updateLabel.innerHTML = "No Image Selected";

    if (activated && !isContentLabelActivated) {
        isContentLabelActivated = true;
        updateLabel.classList.add("text-info");
        updateLabel.classList.remove("text-secondary");
    } else if (!activated && isContentLabelActivated) {
        isContentLabelActivated = false;
        updateLabel.classList.add("text-secondary");
        updateLabel.classList.remove("text-info");
    }

}

function updateNameInput() {
    const isNameValid = checkName(name);
    if (isNameValid && !isNameInputActivated) {
        isNameInputActivated = true;
        const nameInput = document.getElementById("name");
        nameInput.classList.add("text-info")
        nameInput.classList.remove("text-secondary")
    } else if (!isNameValid && isNameInputActivated) {
        isNameInputActivated = false;
        const nameInput = document.getElementById("name");
        nameInput.classList.add("text-secondary")
        nameInput.classList.remove("text-info")
    }
}


function updateSaveButton() {
    if (!isSaveButtonActivated && isNameInputActivated && isContentLabelActivated) {
        isSaveButtonActivated = true;
        const saveButton = document.getElementById("save-button");
        saveButton.classList.remove("disabled");
        saveButton.disabled = false;
    } else if (isSaveButtonActivated && !(isNameInputActivated && isContentLabelActivated)) {
        isSaveButtonActivated = false;
        const saveButton = document.getElementById("save-button");
        saveButton.classList.add("disabled");
        saveButton.disabled = true;
    }
}

function checkName(name) {
    return name.length >= NAME_MIN_LENGTH && name.length <= NAME_MAX_LENGTH && NAME_REGEX.test(name);
}
