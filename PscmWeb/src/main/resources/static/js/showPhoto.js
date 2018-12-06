/*   和照片的显示隐藏有关的部分    */

//显示照片
function displayImg(sUrl) {
    var img = document.getElementById("image");
    var x = event.clientX + document.body.scrollLeft + 20;
    var y = event.clientY + document.body.scrollTop - 5;
    img.style.left = x + "px";
    img.style.top = y + "px";
    img.style.display = "block";
    $.ajax({
        type:'post',
        data:{"fileInNames":sUrl}, //参数
        dataType:'json',
        url: "contractAtt/getContractAttList",
        success: function(data) {
            if(data.data[0]){
                dataUrl = data.data[0].location;//console.log(dataUrl)
                $("#img").attr("src", dataUrl);
            }
        },
        error:function(data){
            alert('查看失败！');
        }
    });
}
//隐藏照片
function vanishImg(){
    var img = document.getElementById("image");
    img.style.display = "none";
}


/*   和拍照有关的部分    */

// Put event listeners into place
window.addEventListener("DOMContentLoaded", function() {
    // Grab elements, create settings, etc.
    var canvas = document.getElementById('canvas');
    var context = canvas.getContext('2d');
    var video = document.getElementById('video');
    var mediaConfig = {
        video : true
    };
    var errBack = function(e) {
        console.log('An error has occurred!', e)
    };
    // Put video listeners into place
    if (navigator.mediaDevices && navigator.mediaDevices.getUserMedia) {
        navigator.mediaDevices.getUserMedia(mediaConfig).then(function(stream) {
            // video.src = window.URL.createObjectURL(stream);
            video.srcObject = stream;
            video.play();
        });
    }
    /* Legacy code below! */
    else if (navigator.getUserMedia) { // Standard
        navigator.getUserMedia(mediaConfig, function(stream) {
            video.src = stream;
            video.play();
        }, errBack);
    } else if (navigator.webkitGetUserMedia) { // WebKit-prefixed
        navigator.webkitGetUserMedia(mediaConfig, function(stream) {
            video.src = window.webkitURL.createObjectURL(stream);
            video.play();
        }, errBack);
    } else if (navigator.mozGetUserMedia) { // Mozilla-prefixed
        navigator.mozGetUserMedia(mediaConfig, function(stream) {
            video.src = window.URL.createObjectURL(stream);
            video.play();
        }, errBack);
    }
    // Trigger photo take
    document.getElementById('save').addEventListener('click', function() {
        context.drawImage(video, 0, 0, 640, 480);
        saveFile(filename)
    });
}, false);

// save canvas image to file
var type = 'png';
// generating file name
var filename = (new Date()).getTime() + '.' + type;

var _fixType = function(type) {
    type = type.toLowerCase().replace(/jpg/i, 'jpeg');
    var r = type.match(/png|jpeg|bmp|gif/)[0];
    return 'image/' + r;
};

var saveFile = function(filename) {
    // 从canvas中直接提取图片元数据
    var imgData = document.getElementById('canvas').toDataURL(type);
    //console.log("imgData==========="+imgData)
    //将mime-type改为image/octet-stream，强制让浏览器直接download
    //imgData = imgData.replace(_fixType(type), 'image/octet-stream');
    //图片download到本地
    /*var save_link = document.createElementNS('http://www.w3.org/1999/xhtml','a');
    save_link.href = imgData;
    save_link.download = filename;
    var event = document.createEvent('MouseEvents');
    event.initMouseEvent('click', true, false, window, 0, 0, 0, 0, 0, false, false, false, false, 0, null);
    save_link.dispatchEvent(event);*/

    if (imgData.lastIndexOf('data:base64') != -1) {
        imgData = imgData.replace('data:base64', 'data:image/jpeg;base64');
    } else if (imgData.lastIndexOf('data:,') != -1) {
        imgData = imgData.replace('data:,', 'data:image/jpeg;base64,');
    }
    if(isCanvasSupported()){
        ajaxUploadBase64File(imgData);
    }else{
        alert("您的浏览器不支持");
    }
    $("#photo_dialog").dialog("close");//关闭dialog
};
//ajax异步上传
function ajaxUploadBase64File(base64Data){
    var string = $("#string_photo").val();
    $.ajax({
        url:basePath + 'labor/uploadPhoto',
        type:"post",
        data:{"inId":$("#in_id_photo").val(),"string":string,"base64Data":base64Data},
        success:function(data){
            if (data != "") {
                layer.msg('拍照成功!',{icon:1,time:2000});
                //location.reload();
            }else{
                layer.msg('拍照成功!',{icon:1,time:2000});
            }
        },
        error:function(){
            layer.msg(e+'拍照成功!',{icon:1,time:2000});
        }
    });
};
//是否支持canvas
function isCanvasSupported(){
    var elem = document.createElement('canvas');
    return !!(elem.getContext && elem.getContext('2d'));
};


/*
var saveFile = function(filename) {
    // get canvas data
    var imgData = document.getElementById('canvas').toDataURL(type);
    //console.log(imgData)
    imgData = imgData.replace(_fixType(type), 'image/octet-stream');

    var save_link = document.createElementNS('http://www.w3.org/1999/xhtml',
        'a');
    save_link.href = imgData;
    save_link.download = filename;

    var event = document.createEvent('MouseEvents');
    event.initMouseEvent('click', true, false, window, 0, 0, 0, 0, 0, false,
        false, false, false, 0, null);
    save_link.dispatchEvent(event);

    $("#photo_dialog").dialog("close");//关闭dialog
};
*/