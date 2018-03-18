$(document).ready(function(){ 
    var herf = window.location.href;  
    var text_final = "";
    if(herf.indexOf('=') != -1) {
        var text = herf.substr(herf.indexOf('=')+1,herf.length);
        text_final = text.replace("%20", " ");
    };

    $.ajax({
            type:"post",
            url:"MyServlet",
            dataType:"json",//return value   
            data:{"name" : text_final},
            success:function(data) {
                if(data.success) {
                     $("#grid").empty();
                     for (var i = 0; i < data.result.length; i++) {
                         if(data.result[i].image != null) {
                         	var images = data.result[i].image.split(",");
                         	if (images[0]!=null || images[0]!="") {
                               $("#grid").append("<li><a href = 'display.html?id=" + data.result[i]._id.$oid + "'><img id = '" + data.result[i]._id.$oid 
                                + "' src='" + images[0] + "'></a></li>");
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
            },
            error:function(msg) {
                console.log(msg);
            }
    });


    
});
