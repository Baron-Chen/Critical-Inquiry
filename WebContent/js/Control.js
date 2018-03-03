/**
 * 
 */

$(document).ready(function(){
	
	
	//load data from database
	$("#click").click(function(){
	    //$("#title").toggle();
	    $.ajax({
            type:"post",
            url:"MyServlet",
            dataType:"json",//return value
            success:function(data) {
                if(data.success) {
            			$("#databse").empty();
                		$("#databse").append("<option vlaue='-1'>==Select One==</option>");
                		for (var i = 0; i < data.result.length; i++) {
                         if(data.result[i].name != null) {
                            $("#databse").append(
                                    "<option value='" + i
                                            + "'>" + data.result[i].name
                                            + "</option>");
                            }
                        }

                     $("#image_display").empty();
                     for (var i = 0; i < data.result.length; i++) {
                         if(data.result[i].image != null) {
                               $("#image_display").append("<img  class = 'images' src='" + data.result[i].image + "'>");
                          }
                    } 
                 } else {
                    alert(data.msg);//print the error message
                }
            },
            error:function(msg) {
                cosole.log(msg);
            }
        });
	  });
	
	//add function
	$("#add").click(function() {
		var name = $("#name").val();
		$.ajax({
            type:"post",
            url:"Add_data",
            data:{"name" : name},
            dataType:"json",
            success:function(data) {
            		if(data.success) {
            			alert(name + " added sucessfully");
            		} else {
            			alert("failed");
            		}
            },
            error:function(msg) {
                cosole.log(msg);
            }
		});
	});
	
	//delete function
	$("#delete").click(function() {
		var name = $("#name").val();
		$.ajax({
            type:"post",
            url:"Del_data",
            data:{"name" : name},
            dataType:"json",
            success:function(data) {
            		if(data.success) {
            			alert(name + " deleted sucessfully");
            		} else {
            			alert("failed");
            		}
            },
            error:function(msg) {
                cosole.log(msg);
            }
		});
	});

    $("#fileUploadContent").initUpload({
        "uploadUrl":"Image",//上传文件信息地址
        //"size":350,//文件大小限制，单位kb,默认不限制
        //"maxFileNumber":3,//文件个数限制，为整数
        //"filelSavePath":"",//文件上传地址，后台设置的根目录
        "beforeUpload":beforeUploadFun,//在上传前执行的函数
        "onUpload":onUploadFun,//在上传后执行的函数
        //autoCommit:true,//文件是否自动上传
        "fileType":['bmp','dib','rle','emf','gif','jpg','jpeg','jpe','jif','pcx','dcx','pic','png','tga','tif','tiffxif','wmf','jfif']//文件类型限制，默认不限制，注意写的是文件后缀
    });

    function beforeUploadFun(opt){
        opt.otherData =[{"name":"name","value":"zxm"}];
    };

    function onUploadFun(opt){
        uploadTools.uploadError(opt);//显示上传错误
        uploadTools.uploadSuccess(opt);//显示上传成功
    };
    
    
    function testUpload(){
        var opt = uploadTools.getOpt("fileUploadContent");
        uploadEvent.uploadFileEvent(opt);
    }
	
});