$(function(){
	var submit_1 = false;
	var submit_2 = false;
	//focus input
	$('input').eq(0).focus(function(){
		if($(this).val().length==0){
			$(this).parent().next("div").text("Please input your Username");
		}
	})
	$('input').eq(1).focus(function(){
		if($(this).val().length==0){
		    $(this).parent().next("div").text("Please input your password");
		}
	})

	//input judgement
	//username：
	$('input').eq(0).blur(function(){
		if($(this).val().length==0){
			$(this).parent().next("div").text("");
			$(this).parent().next("div").css("color",'#ccc');
			submit_1 = false;
		}else{
			$(this).parent().next("div").text("");
			submit_1 = true;
		}		
	})
	//password
	$('input').eq(1).blur(function(){
		if($(this).val().length==0){
			$(this).parent().next("div").text("");
			$(this).parent().next("div").css("color",'#ccc');
			submit_2 = false;
		}else{
			$(this).parent().next("div").text("");
			submit_2 = true;
		}		
	})

//	Submit
	$("#submit_btn").click(function(e){	
		submit(e);

	})
	

     function sentVal(name,password){
         $.ajax({
                type:"post",
                url:"Check_user",
                data:{"username" : name, "password" : password},
                dataType:"json",
                success:function(data) {
                	if(data.success) {
                    	alert("Welcome, " + name);
                    	window.location.href = "index.html";
                	} else if(data.type == 0){
						$('input').eq(0).parent().next("div").text(data.result);
						$('input').eq(0).parent().next("div").css("color",'red');
                	} else {
						$('input').eq(1).parent().next("div").text(data.result);
						$('input').eq(1).parent().next("div").css("color",'red');
                	}
                },
                error:function(msg) {
                    console.log(msg);
                }
            });
     }

	function submit(e) {
		var username;
		var password;
		for(var j=0 ;j<2;j++){
			if($('input').eq(j).val().length==0){				
				$('input').eq(j).focus();	
				$('input').eq(j).parent().next(".tips").text("This place shouldn't be empty");
				$('input').eq(j).parent().next(".tips").css("color",'red');	
				e.preventDefault();
				return;
			}			
		}
		if(submit_1&&submit_2) {
			username = $('input').eq(0).val();
			password = $('input').eq(1).val();
			sentVal(username, password);
		} else {
			e.preventDefault();
			return;
		}
	}

})
