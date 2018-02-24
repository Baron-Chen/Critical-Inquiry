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
                            $("#databse").append(
                                    "<option value='" + i
                                            + "'>" + data.result[i].name
                                            + "</option>");
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
	
	//drag pictures
	 $(".dropzonex").dropzone({
         type:"post",
         url: "Image",
         maxFiles: 10,
         maxFilesize: 2,
         acceptedFiles: "image/*",
         addRemoveLinks: true,
         init: function() {
             this.on("success", function(file) {
                 alert("File " + file.name + " uploaded");
             });
             this.on("removedfile", function(file) {
                 alert("File " + file.name + " removed");
             });
         }
     });
	
});