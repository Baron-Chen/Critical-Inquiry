$(function(){
	var stuList = getStuList();//set student collection
	var submit_1 = false;
	var submit_2 = false;
	var submit_3 = false;
	var submit_4 = false;
	//focus input
	$('input').eq(0).focus(function(){
		if($(this).val().length==0){
			$(this).parent().next("div").text("Support combination of letter, maths, dash and underscore");
		}
	})
	$('input').eq(1).focus(function(){
		if($(this).val().length==0){
		    $(this).parent().next("div").text("Two or more combinations of letters, numbers and symbols are recommended, with 6-20 characters");
		}
	})
	$('input').eq(2).focus(function(){
		if($(this).val().length==0){
			$(this).parent().next("div").text("Please input password again");
		}
	})
	$('input').eq(3).focus(function(){
		if($(this).val().length==0){
			$(this).parent().next("div").text("Use your email to get your account back");
		}
	})

	//input judgement
	//username：
	$('input').eq(0).blur(function(){
		if($(this).val().length==0){
			$(this).parent().next("div").text("");
			$(this).parent().next("div").css("color",'#ccc');
			submit_1 = false;
		}else if($(this).val().length>0 && $(this).val().length<4){
			$(this).parent().next("div").text("Length should between 4-20 letters");
			$(this).parent().next("div").css("color",'red');
			submit_1 = false;
		}else if($(this).val().length>=4&& !isNaN($(this).val())){
			$(this).parent().next("div").text("The username cannot be numbers only");
			$(this).parent().next("div").css("color",'red');
			submit_1 = false;
		}else{
			for(var m=0;m<stuList.length;m++){
				if($(this).val()==stuList[m].name){
					$(this).parent().next("div").text("The username is already registered");
					$(this).parent().next("div").css("color",'red');
					submit_1 = false;
					return;
				}				
			}
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
		}else if($(this).val().length>0 && $(this).val().length<6){
			$(this).parent().next("div").text("The length should between 6-20 characters");
			$(this).parent().next("div").css("color",'red');
			submit_2 = false;
		}else{
			$(this).parent().next("div").text("");
			submit_2 = true;
		}		
	})
//	confirmation
	$('input').eq(2).blur(function(){
		if($(this).val().length==0){
			$(this).parent().next("div").text("");
			$(this).parent().next("div").css("color",'#ccc');
			submit_3 = false;
		}else if($(this).val()!=$('input').eq(1).val()){
			$(this).parent().next("div").text("Password don't match");
			$(this).parent().next("div").css("color",'red');
			submit_3 = false;
		}else{
			$(this).parent().next("div").text("");
			submit_3 = true;
		}		
	})

	//	email
	$('input').eq(3).blur(function(){
		if($(this).val().length==0){
			$(this).parent().next("div").text("");
			$(this).parent().next("div").css("color",'#ccc');
			submit_4 = false;
		}else if($(this).val().indexOf("@")==-1 || $(this).val().indexOf("@")==0){
			$(this).parent().next("div").text("Please input the correct email");
			$(this).parent().next("div").css("color",'red');
			submit_4 = false;
		}else{
			$(this).parent().next("div").text("");
			submit_4 = true;
		}		
	})
	

	// code();
	// $("#code").click(code);	

//	Submit
	$("#submit_btn").click(function(e){	
		submit(e);

	})
	
//  student model
	function Student(name,password,tel,id){
         this.name = name;
         this.password = password;
         this.tel = tel;
         this.id = id;
     }

     function sentVal(name,password,email){
         $.ajax({
                type:"post",
                url:"Add_user",
                data:{"username" : name, "password" : password, "email" : email},
                dataType:"json",
                success:function(data) {
                	if(data.success) {
                    	alert(data.result);
                    	window.location.href = "index.html";
                	} else {
						$('input').eq(0).parent().next("div").text(data.result);
						$('input').eq(0).parent().next("div").css("color",'red');
                	}
                },
                error:function(msg) {
                    cosole.log(msg);
                }
            });
     }

//	get all the former collection
	function getStuList(){
	    var list = localStorage.getItem('stuList');
	    if(list != null){
	        return JSON.parse(list);
	    }else{
	        return new Array();
	    }
	}

	function submit(e) {
		var username;
		var password;
		var email;
		for(var j=0 ;j<4;j++){
			if($('input').eq(j).val().length==0){				
				$('input').eq(j).focus();
				$('input').eq(j).parent().next(".tips").text("This place shouldn't be empty");
				$('input').eq(j).parent().next(".tips").css("color",'red');	
				e.preventDefault();
				return;
			}			
		}
		if(submit_1&&submit_2&&submit_3&&submit_4) {
			username = $('input').eq(0).val();
			password = $('input').eq(2).val();
			email = $('input').eq(3).val();
			sentVal(username, password, email);
		} else {
			e.preventDefault();
			return;
		}
	}

})
