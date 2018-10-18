<%@page contentType="text/html;charset=UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<base href="${pageContext.request.scheme }://${pageContext.request.serverName }:${pageContext.request.serverPort}${pageContext.request.contextPath }/">
<meta charset="UTF-8">
<link href="jquery/bootstrap_3.3.0/css/bootstrap.min.css" type="text/css" rel="stylesheet" />
<script type="text/javascript" src="jquery/jquery-1.11.1-min.js"></script>
<script type="text/javascript" src="jquery/bootstrap_3.3.0/js/bootstrap.min.js"></script>
<script type="text/javascript" src="js/login.js"></script>
<script type="text/javascript">
</script>
</head>
<body>
	<div style="position: absolute; top: 0px; left: 0px; width: 60%;">
		<img src="image/IMG_7114.JPG" style="width: 100%; height: 90%; position: relative; top: 50px;">
	</div>
	<div id="top" style="height: 50px; background-color: #3C3C3C; width: 100%;">
		<div style="position: absolute; top: 5px; left: 0px; font-size: 30px; font-weight: 400; color: white; font-family: 'times new roman'">CRM &nbsp;<span style="font-size: 12px;">&copy;2017&nbsp;xxxx</span></div>
	</div>
	
	<div id="login" style="position: absolute; top: 120px; right: 100px;width:450px;height:400px;border:1px solid #D5D5D5;">
		<div style="position: absolute; top: 0px; right: 60px;">
			<div class="page-header">
				<h1>登录</h1>
			</div>
			<form class="form-horizontal" role="form">
				<div class="form-group form-group-lg">
					<div style="width: 350px;">
						<input id="account" class="form-control" type="text" placeholder="用户名" value="xxx">
					</div>
					<div style="width: 350px; position: relative;top: 20px;">
						<input id="passwd" class="form-control" type="password" placeholder="密码" value="123">
					</div>
					<div class="checkbox"  style="position: relative;top: 30px; left: 10px;">
						<label>
							<input type="checkbox" id="remember"> 十天内免登录
							<a href="javascript:void(0)"><span id="fg" style="position: relative;left: 150px;">忘记密码？</span></a>
						</label>
					</div>
					<div style="position: relative;top: 35px;"><span id="errMsg" style="color: red;font-size: 16px"></span></div>
					<button id="lgBtn" type="button" class="btn btn-primary btn-lg btn-block"  style="width: 350px; position: relative;top: 45px;">登&nbsp;&nbsp;录</button>
				</div>
			</form>
		</div>
	</div>
	<div id="forgot" style="position: absolute; top: 120px; right: 100px;width:450px;height:400px;border:1px solid #D5D5D5;display: none">
		<div style="position: absolute; top: 0px; right: 60px;">
			<div class="page-header">
				<h2>忘记密码</h2>
			</div>
			<form class="form-horizontal" role="form">
				<div class="form-group form-group-lg">
					<div style="width: 350px;">
						<input id="logAct" class="form-control" type="text" placeholder="登录用户名">
						<span id="tip1" style="color: red;font-size: 14px"></span>
					</div>
					<div style="width: 350px; position: relative;top: 20px;">
						<input id="logEmail" class="form-control" type="text" placeholder="邮箱">
						<input id="btn" type="button" value="获取验证码" class="btn btn-info" style="margin-top: 5px"/>
						<span id="tip2" style="color: red;font-size: 14px;"></span>
					</div>
					<!-- <a onclick="sendMail()" href="javascript:void(0)" style="left: 260px;position: relative;top: -40px;">获取验证码</a> -->
					<div style="width: 350px; position: relative;top: 35px;">
						<input id="code" class="form-control" type="text" placeholder="验证码">
					</div>
					<div style="position: relative;top: 45px; left: 10px;">
						<label>
							<span id="tip3" style="color: red;font-size: 14px"></span>
							<a href="javascript:void(0)"><span id="lg" style="position: absolute;left: 260px;size: 16px;top:0px">登&nbsp;&nbsp;录</span></a>
						</label>
						
					</div>
					
					<button id="fgBtn" onclick="forgot()" type="button" class="btn btn-primary btn-lg btn-block"  style="width: 350px; position: relative;top: 45px;">下一步</button>
				</div>
			</form>
		</div>
	</div>
</body>
</html>