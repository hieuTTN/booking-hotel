const listFile = [];
var token = localStorage.getItem("token");
async function loadHotel() {
    $('#example').DataTable().destroy();
    var url = 'http://localhost:8080/api/hotel/public/findAll';
    const response = await fetch(url, {
        method: 'GET'
    });
    var list = await response.json();
    var main = '';
    for (i = 0; i < list.length; i++) {
        main += `<tr>
                    <td>${list[i].id}</td>
                    <td><img src="${list[i].image}" style="width:80px"></td>
                    <td>${list[i].name}</td>
                    <td>${list[i].category.name}</td>
                    <td>${list[i].address}</td>
                    <td>${formatmoney(list[i].avgPrice)}</td>
                    <td>${list[i].numRating}</td>
                    <td>${list[i].star} <i class="fa fa-star startable"></i></td>
                    <td class="sticky-col">
                        <i onclick="deleteHotel(${list[i].id})" class="fa fa-trash-alt iconaction"></i>
                        <a href="add-hotel?id=${list[i].id}"><i class="fa fa-edit iconaction"></i></a>
                    </td>
                </tr>`
    }
    document.getElementById("listHotel").innerHTML = main
    $('#example').DataTable();
}

async function loadAllCategoryHotel() {
    var url = 'http://localhost:8080/api/category/public/findAll-list';
    const response = await fetch(url, {
    });
    var list = await response.json();
    var main = ``;
    for (i = 0; i < list.length; i++) {
        main += `<option value="${list[i].id}">${list[i].name}</option>`;
    }
    document.getElementById("listdpar").innerHTML = main
}

async function loadAllUtilities() {
    var url = 'http://localhost:8080/api/utilities/public/findAll';
    const response = await fetch(url, {
    });
    var list = await response.json();
    var main = ``;
    for (i = 0; i < list.length; i++) {
        main += `<option value="${list[i].id}">${list[i].name}</option>`;
    }
    document.getElementById("listUt").innerHTML = main
    const ser = $("#listUt");
    ser.select2({
        placeholder: "Chọn tiện ích",
    });
}



async function loadAHotel() {
    var uls = new URL(document.URL)
    var id = uls.searchParams.get("id");
    if (id != null) {
        var url = 'http://localhost:8080/api/hotel/admin/findById?id=' + id;
        const response = await fetch(url, {
            method: 'GET'
        });
        var result = await response.json();
        console.log(result)
        document.getElementById("name").value = result.name
        document.getElementById("price").value = result.avgPrice
        document.getElementById("address").value = result.address
        document.getElementById("anhdathem").style.display = 'block'
        linkbanner = result.image
        document.getElementById("imgpreproduct").src = result.image
        tinyMCE.get('editor').setContent(result.description)
        var main = ''
        for (i = 0; i < result.hotelImages.length; i++) {
            main += `<div id="imgdathem${result.hotelImages[i].id}" class="col-md-3 col-sm-4 col-4">
                        <img style="width: 90%;" src="${result.hotelImages[i].image}" class="image-upload">
                        <button onclick="deleteHotelImage(${result.hotelImages[i].id})" class="btn btn-danger form-control">Xóa ảnh</button>
                    </div>`
        }
        document.getElementById("listanhdathem").innerHTML = main
        await loadAllCategoryHotel();
        document.getElementById("listdpar").value =result.category.id

        await loadAllUtilities();
        var listUl = []
        for (i = 0; i < result.hotelUtilities.length; i++) {
            listUl.push(result.hotelUtilities[i].utilities.id);
        }
        $("#listUt").val(listUl).change();;
    }
}

var linkbanner = '';
async function saveHotel() {
    document.getElementById("loading").style.display = 'block'
    var uls = new URL(document.URL)
    var id = uls.searchParams.get("id");

    var url = 'http://localhost:8080/api/hotel/admin/create';
    var name = document.getElementById("name").value
    var price = document.getElementById("price").value
    var address = document.getElementById("address").value
    var category = document.getElementById("listdpar").value
    var description = tinyMCE.get('editor').getContent()
    var listUt = $("#listUt").val();
    await uploadFile(document.getElementById("imgbanner"));
    var listLinkImg = await uploadMultipleFileNotResp();
    var hotel = {
        "hotel":{
            "id": id,
            "name": name,
            "image": linkbanner,
            "avgPrice": price,
            "address": address,
            "description": description,
            "category": {
                "id":category
            },
        },
        "listImage":listLinkImg,
        "listUtilityId":listUt
    }
    const response = await fetch(url, {
        method: 'POST',
        headers: new Headers({
            'Content-Type': 'application/json'
        }),
        body: JSON.stringify(hotel)
    });
    var result = await response.json();
    console.log(result)
    if (response.status < 300) {
        swal({
                title: "Thông báo",
                text: "thêm/sửa khách sạn thành công",
                type: "success"
            },
            function() {
                document.getElementById("loading").style.display = 'none'
                window.location.href = '/admin/hotel';
            });
    } else {
        swal({
                title: "Thông báo",
                text: "thêm/sửa thất bại",
                type: "error"
            },
            function() {
                document.getElementById("loading").style.display = 'none'
                // window.location.reload();
            });
    }
}

async function deleteHotel(id) {
    var con = confirm("Bạn chắc chắn muốn xóa khách sạn này?");
    if (con == false) {
        return;
    }
    var url = 'http://localhost:8080/api/hotel/admin/delete?id=' + id;
    const response = await fetch(url, {
        method: 'DELETE'
    });
    if (response.status < 300) {
        toastr.success("xóa thành công!");
        loadHotel();
    }
    if (response.status == exceptionCode) {
        var result = await response.json()
        toastr.warning(result.defaultMessage);
    }
}


async function deleteHotelImage(id) {
    var con = confirm("Bạn muốn xóa ảnh này?");
    if (con == false) {
        return;
    }
    var url = 'http://localhost:8080/api/hotel-image/admin/delete?id=' + id;
    const response = await fetch(url, {
        method: 'DELETE'
    });
    if (response.status < 300) {
        toastr.success("xóa ảnh thành công!");
        document.getElementById("imgdathem" + id).style.display = 'none';
    }
    if (response.status == exceptionCode) {
        var result = await response.json()
        toastr.warning(result.defaultMessage);
    }
}


async function uploadMultipleFileNotResp() {
    const formData = new FormData()
    for (i = 0; i < listFile.length; i++) {
        formData.append("file", listFile[i])
    }
    var urlUpload = 'http://localhost:8080/api/public/upload-multiple-file';
    const res = await fetch(urlUpload, {
        method: 'POST',
        body: formData
    });
    if (res.status < 300) {
        return await res.json();
    } else {
        return [];
    }
}

async function uploadFile(filePath) {
    const formData = new FormData()
    formData.append("file", filePath.files[0])
    var urlUpload = 'http://localhost:8080/api/public/upload-file';
    const res = await fetch(urlUpload, {
        method: 'POST',
        body: formData
    });
    if (res.status < 300) {
        linkbanner = await res.text();
    }
}

function priviewImg(e) {
    var dv = e.parentNode.parentNode;
    var img = dv.getElementsByClassName("divimgpre")[0].getElementsByClassName("imgpreview")[0]
    const [file] = e.files
    if (file) {
        img.src = URL.createObjectURL(file)
    }
}


function loadInit() {
    $('input#choosefile').change(function() {
        var files = $(this)[0].files;
    });
    document.querySelector('#choosefile').addEventListener("change", previewImages);

    function previewImages() {
        var files = $(this)[0].files;
        for (i = 0; i < files.length; i++) {
            listFile.push(files[i]);
        }

        var preview = document.querySelector('#preview');

        for (i = 0; i < files.length; i++) {
            readAndPreview(files[i]);
        }

        function readAndPreview(file) {

            if (!/\.(jpe?g|png|gif)$/i.test(file.name)) {
                return alert(file.name + " is not an image");
            }

            var reader = new FileReader(file);

            reader.addEventListener("load", function() {
                document.getElementById("chon-anhs").className = 'col-sm-3';
                document.getElementById("chon-anhs").style.height = '100px';
                document.getElementById("chon-anhs").style.marginTop = '5px';
                document.getElementById("choose-image").style.height = '120px';
                document.getElementById("numimage").innerHTML = '';
                document.getElementById("camera").style.fontSize = '20px';
                document.getElementById("camera").style.marginTop = '40px';
                document.getElementById("camera").className = 'fas fa-plus';
                document.getElementById("choose-image").style.width = '90%';

                var div = document.createElement('div');
                div.className = 'col-md-3 col-sm-6 col-6';
                div.style.height = '120px';
                div.style.paddingTop = '5px';
                div.marginTop = '100px';
                preview.appendChild(div);

                var img = document.createElement('img');
                img.src = this.result;
                img.style.height = '85px';
                img.style.width = '90%';
                img.className = 'image-upload';
                img.style.marginTop = '5px';
                div.appendChild(img);

                var button = document.createElement('button');
                button.style.height = '30px';
                button.style.width = '90%';
                button.innerHTML = 'xóa'
                button.className = 'btn btn-warning';
                div.appendChild(button);

                button.addEventListener("click", function() {
                    div.remove();
                    console.log(listFile.length)
                    for (i = 0; i < listFile.length; i++) {
                        if (listFile[i] === file) {
                            listFile.splice(i, 1);
                        }
                    }
                    console.log(listFile.length)
                });
            });

            reader.readAsDataURL(file);

        }

    }

}