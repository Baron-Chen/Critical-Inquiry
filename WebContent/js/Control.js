/**
 Create by Baron
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
                         	var images = data.result[i].image.split(",");
                         	for (var j = 0; j < images.length; j++) {
                               $("#image_display").append("<img  class = 'images' id = '" + data.result[i]._id.$oid 
                                + "' src='" + images[j] + "'>");
                         	}
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


    //check user function
    $("#submit").click(function() {
        var username = $("#username").val();
        var password = $("#password").val();
        if(username == "" || password == "") {
            alert("Please fill up the form");
        } else {          
            $.ajax({
                type:"post",
                url:"Check_user",
                data:{"username" : username, "password" : password},
                dataType:"json",
                success:function(data) {
                    alert(data.result);
                },
                error:function(msg) {
                    cosole.log(msg);
                }
            });
        }
    });

    //sign up function
    $("#signup").click(function() {
        var username = $("#username").val();
        var password = $("#password").val();
        if(username == "" || password == "") {
            alert("Please fill up the form");
        } else {          
            $.ajax({
                type:"post",
                url:"Add_user",
                data:{"username" : username, "password" : password},
                dataType:"json",
                success:function(data) {
                    alert(data.result);
                },
                error:function(msg) {
                    cosole.log(msg);
                }
            });
        }
    });

    //Pic click
    $("body").on("click",".images",function(){
    	  var id = $(this).attr("id");
    	  window.location.href="display.html?id=" + id; 
    	});

    //Search
    $("#search").click(function(){
		search();
    });
    
    $("#search_box").bind('keydown',function(event){
    		if (event.keyCode == "13") {
    			search();
    		};
    });

    $("#fileUploadContent").initUpload({
        "uploadUrl":"Image",//上传文件信息地址
        //"size":350,//文件大小限制，单位kb,默认不限制
        //"maxFileNumber":3,//文件个数限制，为整数
        //"filelSavePath":"",//文件上传地址，后台设置的根目录
        "selfUploadBtId":"add_image_sel",//自定义文件上传按钮id
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
        opt.otherData =[{"name":"name","value":name}];
    };

    function onUploadFun(opt){
        uploadTools.uploadError(opt);//显示上传错误
        uploadTools.uploadSuccess(opt);//显示上传成功
    };


    
    function search(){
		var text = $("#search_box").val();
		window.location.href="search.html?search=" + text;
    }
	
});