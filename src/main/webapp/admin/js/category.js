var token = localStorage.getItem("token");
var size = 10;
async function loadCategory(page, param) {
    if (param == null) {
        param = "";
    }
    var url = 'http://localhost:8080/api/category/public/search?page=' + page + '&size=' + size + '&q=' + param;
    const response = await fetch(url, {
        method: 'GET'
    });
    var result = await response.json();
    console.log(result)
    var list = result.content;
    var totalPage = result.totalPages;

    var main = '';
    for (i = 0; i < list.length; i++) {
        main += `<tr>
                    <td>${list[i].id}</td>
                    <td>${list[i].name} ${list[i].isPrimary==true?'<span class="dmchinh"> <i class="fa fa-check-circle"></i> danh mục chính</span>':''}</td>
                    <td class="sticky-col">
                        <a onclick='return confirm("Bạn có chắc chắn muốn xóa danh mục này?");' href="/admin/deletecategory?id=${list[i].id}"><i class="fa fa-trash-alt iconaction"></i></a>
                        <i onclick="loadACategory('${list[i].name}',${list[i].id})" data-bs-toggle="modal" data-bs-target="#exampleModal" class="fa fa-edit iconaction"></i>
                    </td>
                </tr>`
    }
    document.getElementById("listcategory").innerHTML = main
    var mainpage = ''
    for (i = 1; i <= totalPage; i++) {
        var cl = '';
        if(Number(i)-1 == page){
            cl = 'active-page';
        }
        mainpage += `<li onclick="loadCategory(${(Number(i) - 1)},${param})" class="page-item ${cl}"><a class="page-link" href="#listsp">${i}</a></li>`
    }
    document.getElementById("pageable").innerHTML = mainpage
}



async function loadACategory(name, id) {
    document.getElementById("idcate").value = id
    document.getElementById("catename").value = name
}

async function clearData() {
    document.getElementById("idcate").value = ""
    document.getElementById("catename").value = ""
}
