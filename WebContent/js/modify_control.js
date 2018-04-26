$(document).ready(function(){
    var href = window.location.href;
    var id;
    var imgs = "";
    if(href.indexOf('=') > 0) {
        id = href.substr(href.indexOf('=')+1, href.length);
    } else{
          alert("No Result");
          window.location.href = "index.html";
    }
    var name, brand, capacity, manufacture, country, upc, remarks, image;
    $.ajax({
        type:"post",
        url:"Load_info",
        data:{"id" : id},
        dataType:"json",
        success:function(data) {
            if(data.success) {
                 $("#name").val(data.result[0].name);
                 $("#brand").val(data.result[0].brand);
                 $("#capacity").val(data.result[0].capacity);
                 $("#manufacturer").val(data.result[0].manufacturer);
                 $("#country").val(data.result[0].country);
                 $("#upc").val(data.result[0].upc);
                 $("#remarks").val(data.result[0].remarks);
                 image = data.result[0].image;
                 var images = image.split(",");
                 if (images[0]!=null && images[0]!="") {
                    control = images.length;
                     count = 0;
                     for (var j = 0; j < control; j++) {
                        if (images[j]!=null && images[j]!="") {
                        var showTypeStr="<img src='"+images[j]+"'/>"
                        var img_name = images[j].substr(images[j].indexOf('/')+1, images[j].length);
                        var modelStr="";
                        modelStr+="<div class='fileItem'>";
                        modelStr+="<div class='imgShow'>";
                        modelStr+=showTypeStr;
                        modelStr+=" </div>";
                        modelStr+="<div class='status'>";
                        modelStr+="<span class='icon_font icon-trash' id='"+images[j]+"'></span>";
                        modelStr+="</div>";
                        modelStr+=" <div class='fileName'>";
                        modelStr+=img_name;
                        modelStr+="</div>";
                        modelStr+="</div>";
                        $(".box").append(modelStr);
                     }
                 }
                        $(".icon_font").on("click", function(){
                            imgs += $(this).attr("id");
                            imgs += ","
                            $(this).parents(".fileItem").hide();
                        })
                }
            }
            else {
            	alert("No Result");
                window.location.href = "index.html";
            }
         },
         error:function(msg) {
         	alert("No Result");
            window.location.href = "index.html";
         }
    });

	$("#fileUploadContent").initUpload({
        "uploadUrl":"Update_data",//上传文件信息地址
        //"size":350,//文件大小限制，单位kb,默认不限制
        //"maxFileNumber":3,//文件个数限制，为整数
        //"filelSavePath":"",//文件上传地址，后台设置的根目录
        "selfUploadBtId":"submit_button",//自定义文件上传按钮id
        "scheduleStandard":false,//模拟进度的模式
        "isHiddenUploadBt":true,//是否隐藏上传按钮
        "isHiddenCleanBt":true,//是否隐藏清除按钮
        "beforeUpload":beforeUploadFun,//在上传前执行的函数
        "onUpload":onUploadFun,//在上传后执行的函数
        //autoCommit:true,//文件是否自动上传
        "fileType":['bmp','dib','rle','emf','gif','jpg','jpeg','jpe','jif','pcx','dcx','pic','png','tga','tif','tiff','xif','wmf','jfif']//文件类型限制，默认不限制，注意写的是文件后缀
    });

    function beforeUploadFun(opt){
        var name = $("#name").val();
        if(name == "") {
            alert("Please input the name")
            return;
        }
        var brand = $("#brand").val();
        var capacity = $("#capacity").val();
        var manufacture = $("#manufacturer").val();
        var country = $("#country").val();
        var upc = $("#upc").val();
        var remarks = $("#remarks").val();
        opt.otherData =[{"name":"name","value":name},
        {"name":"id","value":id},
        {"name":"brand","value":brand},
        {"name":"capacity","value":capacity},
        {"name":"manufacture","value":manufacture},
        {"name":"country","value":country},
        {"name":"upc","value":upc},
        {"name":"remarks","value":remarks},
        {"name":"image","value":image},
        {"name":"delete_img","value":imgs}];
    };

    function onUploadFun(opt){
    	alert("Data Modified Successfully");
        window.location.href="index.html"; 
    };

    $("#delete_button").on("click", function(){
        if(confirm("Want to delete?")) {
            $.ajax({
                type:"post",
                url:"Del_data",
                data:{"id" : id, "images" : image},
                dataType:"json",
                success:function(data) {
                    if(data.success) {
                        alert("Deleted Successfully");
                        window.location.href="index.html";
                    }
                }
            });
        }
    });
});