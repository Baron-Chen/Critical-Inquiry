$(document).ready(function(){
    var herf = window.location.href;
    var id = herf.substr(herf.indexOf('=')+1,herf.length);
    var name, brand, capacity, manufacture, country, upc, remarks
    $.ajax({
        type:"post",
                 url:"Load_info",
                 data:{"id" : id},
                 dataType:"json",
                 success:function(data) {
                    if(data.success) {

                    }
                 },
                 error:function(msg) {
                     console.log(msg);
                 }
    });

	$("#fileUploadContent").initUpload({
        "uploadUrl":"Image",//上传文件信息地址
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
        var manufacture = $("#manufacture").val();
        var country = $("#country").val();
        var upc = $("#upc").val();
        var remarks = $("#remarks").val();
        opt.otherData =[{"name":"name","value":name},
        {"name":"brand","value":brand},
        {"name":"capacity","value":capacity},
        {"name":"manufacture","value":manufacture},
        {"name":"country","value":country},
        {"name":"upc","value":upc},
        {"name":"remarks","value":remarks}];
    };

    function onUploadFun(opt){
    	alert("Data Added Successfully");
        window.location.href="index.html"; 
    };
});