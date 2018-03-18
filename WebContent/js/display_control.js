$(document).ready(function(){


	var herf = window.location.href;
	var id = herf.substr(herf.indexOf('=')+1,herf.length);
    var showproduct = {
          "boxid":"showbox",
          "sumid":"showsum",
          "boxw":400,//width,please = height
          "boxh":400,//height, please = width
          "sumw":60,//table width
          "sumh":60,//table height
          "sumi":7,//table interval
          "sums":5,//table display number
          "sumsel":"sel",
          "sumborder":1,//table border
          "lastid":"showlast",
          "nextid":"shownext"
    };//参数定义
	//alert(id);
    var control;
    var count;
	 $.ajax({
                 type:"post",
                 url:"Load_info",
                 data:{"id" : id},
                 dataType:"json",
                 success:function(data) {
                     if(data.success) {
                         $("#name").text(data.result[0].name)
                         $("#brand").text(data.result[0].brand)
                         $("#capacity").text(data.result[0].capacity)
                         $("#manufacturer").text(data.result[0].manufacturer)
                         $("#country").text(data.result[0].country)
                         $("#upc").text(data.result[0].upc)
                         $("#remarks").text(data.result[0].remarks)
                      	var images = data.result[0].image.split(",");
                        control = images.length;
                        count = 0;
                      	for (var j = 0; j < control; j++) {
                            var real_w = 400;
                            var real_h = 400; 
                            // Make in memory copy of image to avoid css issues 
                            $("<img/>").attr("src", images[j]).on("load", function() {
                                real_w = this.width;   // Note: $(this).width() will not work for in memory images. 
                                real_h = this.height;
                                $("#showbox").append("<img src='" + this.src + "' width='" + real_w + "' height='" +
                                    real_h + "' />");
                                count++;
                                if (count == control) {
                                    $.ljsGlasses.pcGlasses(showproduct);
                                }
                            });
                      	}
                     } else {
                    	 	alert(data.msg);
                     }
                 },
                 error:function(msg) {
                     console.log(msg);
                 }
             });    
});