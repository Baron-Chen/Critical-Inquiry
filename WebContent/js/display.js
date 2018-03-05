$(document).ready(function(){


	var herf = window.location.href;
	var id = herf.substr(herf.indexOf('=')+1,herf.length);
	//alert(id);
	 $.ajax({
                 type:"post",
                 url:"Load_info",
                 data:{"id" : id},
                 dataType:"json",
                 success:function(data) {
                     if(data.success) {
                         $("#detail_display").append("<p class='details'> Name:" + data.result[0].name + "</p>");
                         $("#image_display").append("<img class='images' src='" + data.result[0].image + "'>");
                     } else {
                    	 	alert(data.msg);
                     }
                 },
                 error:function(msg) {
                     cosole.log(msg);
                 }
             });

});