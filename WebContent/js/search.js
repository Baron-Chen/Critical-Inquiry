$(document).ready(function(){
	var herf = window.location.href;
	var text = herf.substr(herf.indexOf('=')+1,herf.length);
	var text_final = text.replace("%20", " ");
    alert(text_final);
	$.ajax({
			type:"post",
            url:"Search_info",
            data:{"name" : text_final},
            dataType:"json",
            success:function(data) {
                if(data.success) {
                		for (var i = 0; i < data.result.length; i++) {
                			$("#image_display").append("<img  class = 'images' id = '" + data.result[i]._id.$oid 
                                + "' src='" + data.result[i].image + "'>");
                		}
                } else {
                    	alert("No results");
                }
            },
            error:function(msg) {
                cosole.log(msg);
            }
	});
});