<%--
  Created by IntelliJ IDEA.
  User: ACER
  Date: 10/2/2023
  Time: 4:01 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<h1>Service Provider View</h1>

<input type="text" id="cat" placeholder="category name">
<button id="add">add</button>
<br/>
<br/>
<br/>
<button id="view">View Services</button>
<%

%>
<table id="table">
    <thead>
    <th>email</th>
    <th>f_name</th>
    <th>l_name</th>
    <th>description</th>
    <th>price</th>
    <th>id</th>
    </thead>
    <tbody id="tbody">

    </tbody>
</table>

<script>
    const url = "./service-provider/category-register"
    const formData = new FormData();
    formData.append("category_name", document.getElementById("cat").value);
    const add = async () => {
        const response = await fetch(url, {body: formData, method: "POST"})
        const text = await response.text();
        console.log(text);
    }
    document.getElementById("add").addEventListener('click', async () => {
        await add()
    })
    const listServices = async () => {
        const url = "./service-provider/view-all-service-providers"

        const response = await fetch(url, {method: "GET"})
        const json = await response.json();
        console.log("just json ")
        console.log(json)
        const jsonObject = json[0];
        const table = document.getElementById("table");
        const tBody = document.getElementById("tbody");
        const columnCount = table.getElementsByTagName("th").length;
        console.log("object size")
        console.log(Object.keys(jsonObject).length)
        const keyLength = Object.keys(jsonObject).length;
        console.log("length is ", keyLength)

        {
            Object.keys(json).map((generatedId) => {
                console.log("gen id ", generatedId)
            })
        }

        return;
        for (let i = 0; i < keyLength; i++) {
            console.log("hey ", i)
            console.log("json ", jsonObject[i], " ", i)
            const id = jsonObject[i]['generated_id']
            const fname = jsonObject[i]["fname"];
            const lname = jsonObject[i]["lname"];
            const email = jsonObject[i]["email"];
            const price = jsonObject[i]["price"];
            console.log(fname, " first name ", i);
            console.log(jsonObject[i][0])
            // const tRow = document.createElement("tr");
            // console.log("price is ", price)
            // const td = [];
            // for (let j = 0; j < columnCount; j++) {
            //     td.push(document.createElement("td"));
            // }
        }

    }
    document.getElementById("view").addEventListener('click', async () => {
        await listServices();
    })
</script>
</body>
</html>
