const TAG_NAME_MIN_LENGTH = 5;
const TAG_NAME_MAX_LENGTH = 25;
const TAG_NAME_REGEX = new RegExp(/^[0-9a-z_]+$/i);
const TAG_UPDATE_ROOT_URL = "/moderation/tag";

let selectedTagName;

function isTagNameValid(name) {
    return name.length >= TAG_NAME_MIN_LENGTH &&
        name.length <= TAG_NAME_MAX_LENGTH &&
        TAG_NAME_REGEX.test(name);
}

function initialize(){

    const saveName = document.getElementById('save-name');
    const saveButton = document.getElementById('save-button');
    saveName.addEventListener("input", function () {
        const isValid = isTagNameValid(this.value);
        if (isValid && saveButton.disabled) {
            saveButton.classList.remove('disabled');
            saveName.classList.remove('text-secondary');
            saveName.classList.add('text-info');
            saveButton.disabled = false;
        } else if (!isValid && !saveButton.disabled) {
            saveButton.classList.add('disabled');
            saveName.classList.add('text-secondary');
            saveName.classList.remove('text-info');
            saveButton.disabled = true;
        }
    });

    const updateName = document.getElementById('update-name');
    const updateButton = document.getElementById('update-button');
    updateName.addEventListener("input",function (){
        const isValid = isTagNameValid(this.value);
        if(this.value!==selectedTagName && isValid && updateButton.disabled){
            updateButton.classList.remove('disabled');
            updateName.classList.remove('text-secondary');
            updateName.classList.add('text-info');
            updateButton.disabled = false;
        }else if (this.value===selectedTagName || (!isValid && !updateButton.disabled)) {
            updateButton.classList.add('disabled');
            updateName.classList.add('text-secondary');
            updateName.classList.remove('text-info');
            updateButton.disabled = true;
        }
    });

    let table = document.getElementById("tag-table");
    let rows = table.getElementsByTagName("tr");
    for (i = 0; i < rows.length; i++) {
        let currentRow = table.rows[i];
        let createClickHandler = function (row) {
            return function () {
                let id = row.getElementsByTagName("td")[0].innerHTML;
                let name = row.getElementsByTagName("td")[1].innerHTML;
                selectedTagName = name;
                document.getElementById("update").action = TAG_UPDATE_ROOT_URL + "/" + id;
                document.getElementById("update-current-name").innerHTML = name;
                let updateName = document.getElementById("update-name");
                updateName.value = name;
                updateName.placeholder = name;
                updateName.readOnly = false;
                updateName.focus();
            };
        };
        currentRow.onclick = createClickHandler(currentRow);
    }

}

initialize();

