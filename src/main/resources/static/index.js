var mainContent = document.getElementById("tdContent");
var currentSkillType = {
    "type": "soft",
    "value": 1
};

function init() {
    changeSkillType("hard");

}

function changeSkillType(typeButtonId) {

    currentSkillType.type = typeButtonId.toString();
    if (typeButtonId == "soft") {
        currentSkillType.value = 1;
    } else {
        currentSkillType.value = 0;

    }

    var req = new XMLHttpRequest();

    req.open('GET', '/all');
    req.onload = function () {

        if (req.readyState == 4) {
            if (req.status == 200) {
                parsedJSON = JSON.parse(req.responseText);
                console.log(parsedJSON);
                contentUpdate(typeButtonId, parsedJSON.subs[currentSkillType.value])
            }
        }

    };
    req.send();
}

function contentUpdate(typeButtonId, nodeList) {
    var resultHtml = "";
    for (i = 0; i <= nodeList.subs.length - 1; i++) {
        resultHtml += "<a  style='width: 64%' id=\"" + nodeList.subs[i].id + "\" onclick=\"showSubs(this.id)\">" +
            nodeList.subs[i].content + "</a>" +
            "<a style='width: 2%;" +
            " padding: 0px;" +
            " padding-top: 10px'>" + nodeList.subs[i].subs.length.toString() + "</a>" +
            "<a onclick='showUpdateWindow(" + nodeList.subs[i].id + ")' style='width: 12.6%'>Modify</a>" +
            "<a onclick='showCreateWindow("+ nodeList.subs[i].id + ")' style='width: 14.6%'>addNode</a>";

        if (nodeList.subs[i].subs.length > 0) {
            resultHtml += subWriter(nodeList.subs[i].subs, nodeList.subs[i].id);
        }
    }


    if (typeButtonId == "soft") {
        document.getElementById("hard").style.backgroundColor = 'snow';
    } else {
        document.getElementById("soft").style.backgroundColor = 'snow';
    }
    document.getElementById(typeButtonId).style.backgroundColor = '#f0f2f4';

    clearContent(mainContent, "div");
    mainContent.insertAdjacentHTML("beforeend", resultHtml);

    mainContent.style.display = 'block';
}

function subWriter(subs, divID) {
    var resultHtml = "<div id=\"div" + divID + "\" style=\"display:none\">";

    subs.forEach(function (subValue) {
        resultHtml += "<a id=\"" + subValue.id + "\" style='width: 48%' onclick=\"showSubs(this.id)\">" +
            subValue.content + "</a><a style='width: 2%;" +
            " padding: 0px;" +
            " padding-top: 10px'>" + subValue.subs.length.toString() + "</a><a " +
            "onclick='showUpdateWindow(" +subValue.id + ")' style='width: 12.6%'" +
            ">Modify</a><a style='width: 14.6%'" +
            " onclick='showCreateWindow(" +subValue.id + ")'>addNode</a>" +
            "<a onclick='deleteNode(" + subValue.id + ")' style='width: 10%'>delete</a>";
        if (subValue.subs.length > 0) {
            resultHtml += subWriter(subValue.subs, subValue.id);
        }
    });

    resultHtml+= "</div>";
    return resultHtml;
}

function clearContent(element, childTag) {
    if (element.getElementsByTagName(childTag).length > 0) {
        element.style.display = 'none';
        for (childCount = element.childNodes.length - 1; childCount >= 0; childCount--) {
            element.removeChild(element.childNodes[childCount])
        }
    }
}

function showSubs(parentID) {
    var subs = document.getElementById("div" + parentID);

    if (subs.style.display == 'block') {
        subs.style.display = 'none';
    } else {
        subs.style.display = 'block';
    }
}

function quickSearch(searchFieldID) {
    var searchReq = document.getElementById(searchFieldID);
    var searchRes = document.getElementById("search-result");

    var linkedElem = "";

    clearContent(searchRes, "a");

    var req = new XMLHttpRequest();

    req.open('GET', '/all');
    req.onload = function () {

        if (req.readyState == 4) {
            if (req.status == 200) {
                parsedJSON = JSON.parse(req.responseText);
                if (searchReq.value != "") {
                    linkedElem += search(parsedJSON.subs[currentSkillType.value].subs, searchReq.value);

                    searchRes.style.display = 'block';
                    searchRes.insertAdjacentHTML("afterbegin", linkedElem);
                } else {
                    clearContent(searchRes, "a");
                }
            }
        }

    };
    req.send();
}

function search(subs, searchValue) {
    var result = "";

    subs.forEach(function (sub) {

        if (~sub.content.toLocaleLowerCase().indexOf(searchValue.toString().toLocaleLowerCase())) {
            result += "<a href='#" + sub.id + "' onclick='showSubsByLink(" + sub.id + ")'>" + sub.content + "</a>";
        }
        if (sub.subs.length > 0) {
            result += search(sub.subs, searchValue);
        }
    });

    return result;
}

function showSubsByLink(searchingElemID) {
    document.getElementById("search-result").style.display = 'none';
    document.getElementById("quick-search").value = '';
    var elem = document.getElementById(searchingElemID);

    var currentColor = elem.style.backgroundColor;
    elem.style.backgroundColor = 'grey';

    setTimeout(function (currentColor, elem) {
        elem.style.backgroundColor = currentColor;
    }, 500, currentColor, elem);

    while (elem.parentElement != "tdContent") {
        elem.style.display = 'block';
        elem = elem.parentElement;
    }
}

function addNode() {
    var req = new XMLHttpRequest();
    var content = document.getElementById("content").value;
    var parentID = document.getElementById("parentID").value;

    req.open('POST', '/add/' + parentID);
    req.setRequestHeader("Content-type",
        "application/x-www-form-urlencoded");
    req.onload = function () {
        changeSkillType(currentSkillType.type)
    };
    req.send(content);

    document.getElementById("updateWindow").style.display = 'none';
}

function showUpdateWindow(parentID) {
    document.getElementById("commitButton").setAttribute("onclick", "updateNode()");
    showWindow(parentID);
}

function showCreateWindow(parentID) {
    document.getElementById("commitButton").setAttribute("onclick", "addNode()");
    showWindow(parentID);
}

function showWindow(parentID) {
    var updateWindow = document.getElementById("updateWindow");

    if (updateWindow.style.display == 'none') {
        updateWindow.style.display = 'block';
    } else {
        updateWindow.style.display = 'none';
    }

    document.getElementById("parentID").value = parentID;
}

function updateNode() {
    var req = new XMLHttpRequest();
    var content = document.getElementById("content").value;
    var parentID = document.getElementById("parentID").value;

    req.open('POST', '/update/' + parentID);
    req.setRequestHeader("Content-type",
        "application/x-www-form-urlencoded");
    req.onload = function () {
        changeSkillType(currentSkillType.type);
        showSubsByLink(parentID);
    };
    req.send(content);

    document.getElementById("updateWindow").style.display = 'none';
}

function deleteNode(currentNodeID) {
    var req = new XMLHttpRequest();

    req.open('POST', '/delete/' +
        document.getElementById(currentNodeID).parentElement.id.toString().replace("div", "")
        + "/" + currentNodeID);
    req.setRequestHeader("Content-type",
        "application/x-www-form-urlencoded");
    req.onload = function () {
        changeSkillType(currentSkillType.type)
    };
    req.send();
}
