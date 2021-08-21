const COMMENT_UPDATE_ROOT_URL="/moderation/comment";

function initialize(){

    let table = document.getElementById("comment-table");
    let rows = table.getElementsByTagName("tr");
    for (i = 0; i < rows.length; i++) {
        let currentRow = table.rows[i];
        let createClickHandler = function (row) {
            return function () {
                let id = row.getElementsByTagName("td")[0].innerHTML;
                let name = row.getElementsByTagName("td")[1].innerHTML;
                let surname = row.getElementsByTagName("td")[2].innerHTML;
                let email = row.getElementsByTagName("td")[3].innerHTML;
                let content = row.getElementsByTagName("td")[4].innerHTML;
                document.getElementById("update").action = COMMENT_UPDATE_ROOT_URL + "/" + id;
                let updateName = document.getElementById("update-name");
                let updateSurname = document.getElementById("update-surname");
                let updateEmail = document.getElementById("update-email");
                let updateContent = document.getElementById("update-content");
                updateName.value = name;
                updateName.placeholder = name;
                updateName.readOnly = false;
                updateSurname.value = surname;
                updateSurname.placeholder = surname;
                updateSurname.readOnly = false;
                updateEmail.value = email;
                updateEmail.placeholder = email;
                updateEmail.readOnly = false;
                updateContent.value = content;
                updateContent.placeholder = content;
                updateContent.readOnly = false;
                updateContent.focus();
                document.getElementById("update-button").classList.remove("disabled");
                document.getElementById("update-button").disabled=false;
                document.getElementById("delete-button").classList.remove("disabled");
                document.getElementById("delete-button").disabled=false;
            };
        };
        currentRow.onclick = createClickHandler(currentRow);
    }


}

