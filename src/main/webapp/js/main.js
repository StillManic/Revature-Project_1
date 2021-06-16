let url = "http://localhost:8080/Project_1"
function login() {
    let flag = "/author_login";
    let loginObj = {
        username: document.getElementById("Username").value,
        password: document.getElementById("Password").value
    };

    let json = JSON.stringify(loginObj);

    // let xhttp = 
}