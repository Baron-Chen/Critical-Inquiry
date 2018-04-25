$(document).ready(function(){ 
    var herf = window.location.href;  
    var text_final = "";
    var username;
    if(herf.indexOf('=') != -1) {
        var text = herf.substr(herf.indexOf('=')+1,herf.length);
        text_final = text.replace("%20", " ");
    };


    $("#logout").on("click", function(){
        $.ajax({
            type:"post",
            url:"Index_load",
            success:function(data) {
                alert("Log Out");
                window.location.reload();
            }
        });
    });

    $.ajax({
            type:"get",
            url:"Index_load",
            dataType:"json",//return value   
            data:{"search" : text_final},
            success:function(data) {
                username = data.user;
                console.log(username);
                if(data.success) {
                     $("#grid").empty();
                     for (var i = 0; i < data.result.length; i++) {
                         if(data.result[i].image != null) {
                         	var images = data.result[i].image.split(",");
                         	if (images[0]!=null && images[0]!="") {
                               $("#grid").append("<li><a href = 'display.html?id=" + data.result[i]._id.$oid + "'><img id = '" + data.result[i]._id.$oid 
                                + "' src='" + images[0] + "'></a></li>");
                         	} else {
                                $("#grid").append("<li><a href = 'display.html?id=" + data.result[i]._id.$oid + "'><img id = '" + data.result[i]._id.$oid 
                                + "' src='myImages/no_picture.jpg'></a></li>");
                            }
                          }
                          new AnimOnScroll( document.getElementById( 'grid' ), {
                                minDuration : 0.4,
                                maxDuration : 0.7,
                                viewportFactor : 0.2
                            })
                    } 
                 } else {
                    alert("No Results");//print the error message
                }
                if(username != "null" && username != "") {
                    $("#login").hide();
                    $("#register").hide();
                    $("#logout").show();
                    $("#add").show();
                    $("#username").text("Welcome, " + username)
                } else {
                    $("#login").show();
                    $("#register").show();
                    $("#logout").hide();
                    $("#add").hide();
                }
            },
            error:function(msg) {
                console.log(msg);
            }
    });
    
});
