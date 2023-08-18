import "https://cdn.jsdelivr.net/npm/axios/dist/axios.min.js";
const app = document.querySelector(".app");
const sidebar = document.querySelector(".lists");
const listName = document.querySelector(".list-name");
const taskList = document.querySelector(".task-list");
const input = document.querySelector("#input");
const close_btn = document.querySelector(".closebtn");
const open_btn = document.querySelector(".openbtn");
const add_task_btn = document.querySelector(".add-task-btn");
const add_list_ref = document.querySelector("#add-list");
const delete_list_btn = document.querySelector(".fa-trash");



let current_id;
let result;


delete_list_btn.addEventListener("click", () => {
    if(confirm("Вы уверены?")){
        deleteList();
        location.reload();
    }
})


add_list_ref.addEventListener("click", () => {
    result = prompt("Введите имя листа","Новый лист");
    if(result !== null) {
        addList(result);
        location.reload();
    }
})


add_task_btn.addEventListener("click", () => {
    addTask();
})

app.addEventListener("click", () => {
    closeNav();
})

close_btn.addEventListener("click",() => {
    closeNav();
})

open_btn.addEventListener("click",() => {
    openNav();
})


listName.addEventListener("change",() => {
    updateList();
    setTimeout(setSideBarList,500);
})





const api = "http://localhost:8082/api/lists";

const lists = await fetchData();
  

init();

async function setSideBarList() {
    const lists = await fetchData();
    sidebar.innerHTML = "";
    lists.forEach(list => {
        const div_list = document.createElement("div")
        div_list.classList.add("list");
        const list_name = document.createElement("a");
        list_name.classList.add("a-list");
        list_name.innerHTML = list.name;
        list_name.addEventListener("click", () => {
            setList(lists[lists.indexOf(list)].id);
        });
        div_list.appendChild(list_name);
        sidebar.appendChild(div_list);
    });
}



function addTask() {
    const taskValue = input.value;
    if(taskValue.trim() !== "") {
        taskList.appendChild(generateTask(taskValue,false));
        input.value = "";
    }
    updateList();
}

async function fetchData() {
    return await axios.get(api)
                .then((response) => response.data.sort((a,b) => a.id - b.id));
}


function updateList() {
    const listToUpdate = getList();
    axios.put(api+"/update",listToUpdate);
}

function deleteList() {
    axios.delete(api+"/delete/" + current_id);
    setTimeout(setSideBarList,500);
}




function getList() {
    const id = current_id;
    const name = listName.value;
    const tasks = [];
    taskList.childNodes.forEach(task => {
        const value = task.firstChild.innerHTML;
        const completed = task.firstChild.className;
        tasks.push({
            "value": value,
            "completed": completed
        })
    });
    return {
        "id": id,
        "name": name,
        "tasks": tasks
    }
}

async function setList(id) {
    const list = await axios.get(api+"/"+id).then(x => x.data);
    taskList.innerHTML = "";
    listName.value = list.name;
    current_id = list.id;
    list.tasks.forEach(task => {
        taskList.appendChild(generateTask(task.value,task.completed));
    })   
}


function addList(name) {
    const list = {
        "name": name,
        "tasks": []
    }
    axios.post(api + "/add",list);
    setTimeout(setSideBarList,500);
}


function generateTask(value,completed) {
    const task = document.createElement("div");
    const delete_btn = document.createElement("button");
    const li = document.createElement("li");
    li.innerHTML = value;
    li.classList.add(completed);
    li.addEventListener("click",() => {
        if(li.className === "false") {
            li.className = "true";
            setTimeout(updateList,200);
        }else {
            li.className = "false";
            setTimeout(updateList,200);
        }
    })
    delete_btn.innerText = "x";
    delete_btn.addEventListener("click", () => {
        taskList.removeChild(task);
        updateList();
    })
    task.classList.add("task");
    delete_btn.classList.add("task-delete-btn");
    task.appendChild(li);
    task.appendChild(delete_btn);
    return task;

}

function init() {
    current_id = lists[0].id;
    setList(current_id);
    setSideBarList(lists);
}

function openNav() {
    document.getElementById("mySidebar").style.width = "300px";
    document.getElementById("main").style.marginLeft = "300px";
  }
  
  function closeNav() {
    document.getElementById("mySidebar").style.width = "0";
    document.getElementById("main").style.marginLeft = "0";
  }
  
