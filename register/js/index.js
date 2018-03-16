$(function(){
	var stuList = getStuList();//set student collection
	 
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
		}else if($(this).val().length>0 && $(this).val().length<4){
			$(this).parent().next("div").text("Length should between 4-20 letters");
			$(this).parent().next("div").css("color",'red');
		}else if($(this).val().length>=4&& !isNaN($(this).val())){
			$(this).parent().next("div").text("The username cannot be numbers only");
			$(this).parent().next("div").css("color",'red');
		}else{
			for(var m=0;m<stuList.length;m++){
				if($(this).val()==stuList[m].name){
					$(this).parent().next("div").text("The username is already registered");
					$(this).parent().next("div").css("color",'red');
					return;
				}				
			}
			$(this).parent().next("div").text("");
		}		
	})
	//password
	$('input').eq(1).blur(function(){
		if($(this).val().length==0){
			$(this).parent().next("div").text("");
			$(this).parent().next("div").css("color",'#ccc');
		}else if($(this).val().length>0 && $(this).val().length<6){
			$(this).parent().next("div").text("The length should between 6-20 characters");
			$(this).parent().next("div").css("color",'red');
		}else{
			$(this).parent().next("div").text("");
		}		
	})
//	confirmation
	$('input').eq(2).blur(function(){
		if($(this).val().length==0){
			$(this).parent().next("div").text("");
			$(this).parent().next("div").css("color",'#ccc');
		}else if($(this).val()!=$('input').eq(1).val()){
			$(this).parent().next("div").text("Password don't match");
			$(this).parent().next("div").css("color",'red');
		}else{
			$(this).parent().next("div").text("");
		}		
	})
	

	code();
	$("#code").click(code);	

//	Submit
	$("#submit_btn").click(function(e){		
		for(var j=0 ;j<5;j++){
			if($('input').eq(j).val().length==0){				
				$('input').eq(j).focus();				
				if(j==4){
					$('input').eq(j).parent().next().next("div").text("This place shouldn't be empty");
					$('input').eq(j).parent().next().next("div").css("color",'red');
					e.preventDefault();
					return;
				}
				$('input').eq(j).parent().next(".tips").text("This place shouldn't be empty");
				$('input').eq(j).parent().next(".tips").css("color",'red');	
				e.preventDefault();
				return;
			}			
		}

	})
	
//  student model
	function Student(name,password,tel,id){
         this.name = name;
         this.password = password;
         this.tel = tel;
         this.id = id;
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

})
