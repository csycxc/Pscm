
//修改密码
function  doUpdatePwd(url, userCode,  oldPwd , newPwd) {
	$.ajax({
		type: "POST",
		url: url,
		data:{
			userCode:userCode,
			oldPwd: oldPwd,
			newPwd:newPwd
		},
		success:function(msg){
			if(msg==true){
				layer.msg('修改成功!',{icon:1,time:1000},function(){
					var index = parent.layer.getFrameIndex(window.name);
					parent.layer.close(index);
				});
			}else{
				layer.msg('修改失败!',{icon:1,time:1000});
			}
		}
	});
}


//验证原密码是否正确
function checkpwd(checkUrl,userCode){
	
	var oldPwd = $("#oldPwd").val();
	$("#oldPwd").focus(function(){
		$("#oldPwd").val(oldPwd).css({"color":"black","text-align":"left"});
		$("#oldPwd").css({"border":"1px #97cbff solid","background-color":"#fff"});
	});
	$("#oldPwd").blur(function(){
		oldPwd = $("#oldPwd").val();
		if(oldPwd=="" || oldPwd==null){
			$("#oldPwd").val("原密码不能为空").css({"color":"red","text-align":"right"});
			$("#oldPwd").css({"border":"1px red solid","background-color":"#FFE6E6"});
			return false;
		}
		$.ajax({
			type:"POST",
			url:checkUrl,
			data:{
				userCode: userCode,
				oldPwd: oldPwd
			},
			//dataType:"json",
			success:function(msg){
				if(msg==false){
					//效果有问题：显示“有误”的时候，之前输入的密码不显示了
					//以及，输入小于3个字符时，“有误”和“小于3字符”的提示同时出现
					if(oldPwd.length>=3){
						$("#oldPwd").val("原密码输入有误").css({"color":"red","text-align":"right"});
						$("#oldPwd").css({"border":"1px,red,solid","background-color":"#FFE6E6"});
					}
				}
			},
			error:function() {
            	console.log("error");
            }
		});
   });
}


















