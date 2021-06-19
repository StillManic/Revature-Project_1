let url = "http://localhost:8080/Project_1/controller"
let time_limit = 1.21e+9;   // 2 weeks in milliseconds

function fillProposals() {
    console.log("filling proposal table");
    let flag = "/get_proposals";

    let xhttp = new XMLHttpRequest();

    xhttp.open("GET", url + flag, true);
    xhttp.send();

    xhttp.onreadystatechange = () => {
        if (xhttp.readyState == 4) {
            if (xhttp.status == 200) {
                console.log("fillProposals() ready/OK");
                let rt = xhttp.responseText;
                // let jsons = JSON.parse(rt);
                // let stories = JSON.parse(jsons[0]);
                // let is_senior_editor = JSON.parse(jsons[1]);
                let json = JSON.parse(rt);

                console.log(json);

                let hadOverdue = false;

                let table = document.getElementById("proposals");
                // for (let story of stories) {
                for (let story of json) {
                    let overdue = checkOverdue(story.submissionDate);
                    if (overdue) hadOverdue = true;

                    let tr = document.createElement("tr");
                    
                    // Author
                    let td = document.createElement("td");
                    if (overdue) {
                        td.setAttribute("class", "green red-background");
                    } else {
                        td.setAttribute("class", "green purple-background");
                    }
                    td.innerHTML = story.author.firstName + " " + story.author.lastName;
                    if (overdue || !hadOverdue) {
                        td.onclick = () => {
                            handleRowClick(story);
                        }
                    }
                    tr.appendChild(td);

                    // Title
                    td = document.createElement("td");
                    if (overdue) {
                        td.setAttribute("class", "green red-background");
                    } else {
                        td.setAttribute("class", "green purple-background");
                    }
                    td.innerHTML = story.title;
                    if (overdue || !hadOverdue) {
                        td.onclick = () => {
                            handleRowClick(story);
                        }
                    }
                    tr.appendChild(td);

                    // Story Name
                    td = document.createElement("td");
                    if (overdue) {
                        td.setAttribute("class", "green red-background");
                    } else {
                        td.setAttribute("class", "green purple-background");
                    }
                    td.innerHTML = story.type.name;
                    if (overdue || !hadOverdue) {
                        td.onclick = () => {
                            handleRowClick(story);
                        }
                    }
                    tr.appendChild(td);

                    // Approval Status
                    td = document.createElement("td");
                    if (overdue) {
                        td.setAttribute("class", "green red-background");
                    } else {
                        td.setAttribute("class", "green purple-background");
                    }
                    td.innerHTML = story.approvalStatus;
                    if (overdue || !hadOverdue) {
                        td.onclick = () => {
                            handleRowClick(story);
                        }
                    }
                    tr.appendChild(td);
                    
                    table.appendChild(tr);
                }
            }
        }
    }
}

function handleRowClick(story) {
    let flag = "/save_story_to_session";
    let xhttp = new XMLHttpRequest();
    xhttp.open("POST", url + flag, true);
    xhttp.send(JSON.stringify(story));

    xhttp.onreadystatechange = () => {
        if (xhttp.readyState == 4) {
            if (xhttp.status == 200) {
                if (xhttp.responseText == "saved") {
                    console.log("saved story, redirecting!");
                    window.location.href = "story_form_static.html";
                }
            }
        }
    }
}

function getDateDifference(submitted) {
    const current = new Date();
    submitted = new Date(submitted);
    return current - submitted;
}

function checkOverdue(submissionDate) {
    return getDateDifference(submissionDate) > time_limit;
}

function populateStaticForm() {
    let flag = "/get_story_from_session";
    let xhttp = new XMLHttpRequest();
    xhttp.open("GET", url + flag, true);
    xhttp.send();

    xhttp.onreadystatechange = () => {
        console.log("Clicked " + xhttp.responseText);
        let story = JSON.parse(xhttp.responseText);

        let sf_sub_date = document.getElementById("sf_submission_date");
        let sf_title = document.getElementById("sf_title");
        let sf_author_name = document.getElementById("sf_author_name");
        let sf_author_bio = document.getElementById("sf_author_bio");
        let sf_author_points = document.getElementById("sf_author_points");
        let sf_genre = document.getElementById("sf_genre");
        let sf_type = document.getElementById("sf_type");
        let sf_description = document.getElementById("sf_description");
        let sf_tagline = document.getElementById("sf_tagline");
        let sf_completion_date = document.getElementById("sf_completion_date");
        let sf_status = document.getElementById("sf_status");

        let overdue = checkOverdue(story.submissionDate);

        sf_sub_date.innerHTML = story.submissionDate;

        if (overdue) {
            sf_sub_date.innerHTML += "    ** High Priority **";
        }

        sf_title.innerHTML = story.title;
        sf_author_name.innerHTML = story.author.firstName + " " + story.author.lastName;
        sf_author_bio.innerHTML = story.author.bio;
        sf_author_points.innerHTML = story.author.points + " (" + (story.author.points - story.type.points) + " points if approved)";
        sf_genre.innerHTML = story.genre.name;
        sf_type.innerHTML = story.type.name + " (" + story.type.points + " points)";
        sf_description.innerHTML = story.description;
        sf_tagline.innerHTML = story.tagLine;
        sf_completion_date.innerHTML = story.completionDate;
        sf_status.innerHTML = story.approvalStatus;

        if (overdue) {
            sf_sub_date.setAttribute("class", "red light-grey-background");
        } else {
            sf_sub_date.setAttribute("class", "purple light-grey-background");
        }
        sf_title.setAttribute("class", "purple light-grey-background");
        sf_author_name.setAttribute("class", "purple light-grey-background");
        sf_author_bio.setAttribute("class", "purple light-grey-background");
        sf_author_points.setAttribute("class", "purple light-grey-background");
        sf_genre.setAttribute("class", "purple light-grey-background");
        sf_type.setAttribute("class", "purple light-grey-background");
        sf_description.setAttribute("class", "purple light-grey-background");
        sf_tagline.setAttribute("class", "purple light-grey-background");
        sf_completion_date.setAttribute("class", "purple light-grey-background");
        sf_status.setAttribute("class", "purple light-grey-background");



        let approveBtn = document.getElementById("approve_button");
        let denyBtn = document.getElementById("deny_button");
        let infoBtn = document.getElementById("info_button");

        approveBtn.onclick = () => {
            approve(story);
        }
        denyBtn.onclick = () => {
            // deny(story);
            modal.style.display = "block";
        }
        /* Handle Modal */
        let modal = document.getElementById("modal");
        
        infoBtn.onclick = () => {
            requestInfo(story);
            // modal.style.display = "block";
        }

        let sendBtn = document.getElementById("send_reason_button");

        sendBtn.onclick = () => {
            let reason = document.getElementById("reason").value;
            if (reason.length > 0) {
                modal.style.display = "none";
                deny(story, reason);
            }
        }

        // let span = document.getElementsByClassName("close")[0];

        // span.onclick = () => {
        //     modal.style.display = "none";
        // }
    }
}

function approve(story) {
    let flag = "/approve_story";

    let json = JSON.stringify(story);

    let xhttp = new XMLHttpRequest();
    xhttp.open("POST", url + flag, true);
    xhttp.send(json);

    xhttp.onreadystatechange = () => {
        if (xhttp.readyState == 4) {
            if (xhttp.status == 200) {
                console.log("Approved story " + json);
            }
        }
    }
}

function deny(story, reason) {
    //
    //  TODO: create pop-up or other method of providing a reason for denial.
    //
    let flag = "/deny_story";

    story.approvalStatus = "denied";
    story.reason = reason;

    let json = JSON.stringify(story);

    let xhttp = new XMLHttpRequest();
    xhttp.open("POST", url + flag, true);
    xhttp.send(json);

    xhttp.onreadystatechange = () => {
        if (xhttp.readyState == 4) {
            if (xhttp.status == 200) {
                console.log("Denied story " + json);
            }
        }
    }
}

function requestInfo(story) {
    let flag = "/request_info";

    let json = JSON.stringify(story);

    let xhttp = new XMLHttpRequest();
    xhttp.open("POST", url + flag, true);
    xhttp.send(json);

    xhttp.onreadystatechange = () => {
        if (xhttp.readyState == 4) {
            if (xhttp.status == 200) {
                console.log("Requesting more information for " + story);
            }
        }
    }
}

function sfBack() {
    window.location.href = "editor_main.html";
}

function logout() {
    let flag = "/logout";
    let xhttp = new XMLHttpRequest();
    xhttp.open("POST", url + flag, true);
    xhttp.send();

    xhttp.onreadystatechange = () => {
        if (xhttp.readyState == 4) {
            if (xhttp.status == 200) {
                console.log("Logged out!");
                window.location.href = xhttp.responseText;
            }
        }
    }
}