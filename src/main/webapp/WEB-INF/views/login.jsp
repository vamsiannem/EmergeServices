<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE HTML>
<!--[if lt IE 7]><html class="no-js lt-ie9 lt-ie8 lt-ie7"><![endif]-->
<!--[if IE 7]><html class="no-js lt-ie9 lt-ie8"><![endif]-->
<!--[if IE 8]><html class="no-js lt-ie9"><![endif]-->
<!--[if gt IE 8]><!--><html class="no-js"><!--<![endif]-->
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
	<meta name="viewport" content="width=device-width, initial-scale = 1.0, user-scalable = no">
	<title>E-Merge | Lister Technologies</title>
	<link href='http://fonts.googleapis.com/css?family=Open+Sans:400,700,600' rel='stylesheet' type='text/css'>
	<link rel="stylesheet" href="<c:url value='/css/normalize.css' />" type="text/css" media="screen">
	<link rel="stylesheet" href="<c:url value='/css/grid.css' />" type="text/css" media="screen">
	<link rel="stylesheet" href="<c:url value='/css/style.css' />" type="text/css" media="screen">
	<!-- <link rel="stylesheet" href="<c:url value='/css/style.min.css' />" type="text/css" media="screen"> -->
	<!--[if IE]><script src="http://html5shim.googlecode.com/svn/trunk/html5.js"></script><![endif]-->

	<!-- Favicon -->
	<link rel="shortcut icon" href="<c:url value='/images/ipadretinaicon.png' />" type="image/x-icon">

	<!-- Foundation CSS -->
	<link href="<c:url value='/css/foundation1.css' />" rel="stylesheet" type="text/css">

	<!-- Foundation JavaScript -->
 	<script src="<c:url value='/js/vendor/jquery.js' /> "></script>
	<script src="<c:url value='/js/foundation/foundation.js' /> "></script>
	<script src="<c:url value='/js/foundation/foundation.reveal.js' /> "></script> <!-- For Modal Alerts -->
	<script src="<c:url value='/js/foundation/foundation.tab.js' /> "></script> <!-- For Tabs -->

</head>

<body>

	<div class="menu">
		<div class="container clearfix">

			<div id="logo" class="grid_3">
				<img src="<c:url value='/images/logo.png' />">
			</div>

			<div id="nav" class="grid_9 omega">
				<ul class="navigation">
					<li data-slide="1">About</li>
					<li data-slide="2">Resources</li>
					<li data-slide="3">Contact</li>
					<li data-slide="4">Log In</li>
				</ul>
			</div>

		</div>
	</div>


	<div class="slide" id="slide1" data-slide="1" data-stellar-background-ratio="0.5">
		<div class="container clearfix">

			<div id="content" class="grid_7">
				<h1>E-Merge</h1>
				<h2>Get ready to make magic!</h2>
				<p>We're on the brink of the next big shift in E-Marketing! We are still in beta and if you'd like to test our product, welcome,  <a href="mailto:sales@listertechnologies.com">shoot us an email</a>. </p>
				<p>Don't forget to follow us!</p>
				<p><a href="https://twitter.com/listertechnologies" target="_blank"><img src="<c:url value='/images/twitter.png' />"></a> <a href="http://facebook.com/listertechnologies" target="_blank"><img "<c:url value='/images/facebook-icon.png' />"></a></p>
			</div>
			<div id="decorative" class="grid_5 omega">
				<img src="<c:url value='/images/decorative.png' /> ">
			</div>

		</div>
	</div>



	<div class="slide" id="slide2" data-slide="2" data-stellar-background-ratio="1.5">
		<div class="container clearfix">

			<div id="content" class="grid_12">
				<h1>Marketing Re-designed</h1>
				<h2>Where Intelligence and Imagination work in parallel</h2>
			</div>

		</div>
	</div>



	<div class="slide" id="slide3" data-slide="3" data-stellar-background-ratio="0.5">
		<div class="container clearfix">

			<div id="content" class="grid_12">
				<h1>Contact</h1>
				<h2>Lister Technologies</h2>
				<h3>Be the Change you want to see</h3>
			</div>



		</div>
	</div>



	<div class="slide slide4" id="slide4" data-slide="4" data-stellar-background-ratio="0.5">
		<div class="container clearfix">

			<div id="content" class="grid_12">
				<h1>Login</h1>
				<h2><a href="#" data-reveal-id="myModal" data-reveal>Login</a> now to access Beta version of <a href="http://listertechnologies.com" target="_blank">E-Merge</a>.</h2>
				<p>Don't forget to follow us!</p>
				<p><a href="https://twitter.com/listertechnologies" target="_blank"><img src = "<c:url value='/images/twitter.png' /> "></a> <a href="http://facebook.com/listertechnologies" target="_blank"><img src="<c:url value='/images/facebook-icon.png' />"></a></p>
			</div>

		</div>
	</div>

	<!-- Modal -->

	<div id="myModal" class="reveal-modal small" data-reveal>
 	    <div style="text-align:center">
			  <p style="font-size:20pt;font-weight:bold;">E-Merge Login</p>
			  	<p style="font-size:15pt;">Marketing Re-Imagined </p>
	    	  <dl class="tabs" data-tab>
				<dd class="active"><a href="#panel2-1">Login</a></dd>
				<dd><a href="#panel2-2">Register</a></dd>
				<dd><a href="#panel2-3">Reset Password</a></dd>
			  </dl>
    	</div>
        <div class="tabs-content">
	  		<div class="content active" id="panel2-1">
			 	<p>
					Username :
					<input type="text" name="username" autocomplete="off">
					Password :
	  				<input type="password" name="loginpass">
	  				<a onclick="loginAction()" class="button small radius" >Login</a>
	  			</p>
  			</div>
  			<div class="content" id="panel2-2">
    			<p>
					Enter preferred username :
					<input type="text" name="username" autocomplete="off" id="username">
					Choose a password :
	  				<input type="password" name="loginpass" id = "loginpass">
	  				Retype password :
	  				<input type="password" name="loginpass2">
	  				<a onclick="registerAction()" class="button small radius">Register</a>
    			</p>
		</div>
 			<div class="content" id="panel2-3">
   				<p>
   					Enter your username :
					<input type="text" name="username" autocomplete="off">
					<a href="#" class="button small radius">Reset</a>
   				</p>
 			</div>
		</div>
		<a class="close-reveal-modal">&#215;</a>
	</div>

	<!-- Login Modal Ends -->

	<script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jquery/1.7.2/jquery.min.js'"></script>
	<script type="text/javascript" src="<c:url value='/js/jquery.stellar.min.js' />"></script>
	<script type="text/javascript" src="<c:url value='/js/waypoints.min.js' />"></script>
	<script type="text/javascript" src="<c:url value='/js/jquery.easing.1.3.js' />"></script>
	<script type="text/javascript" src="<c:url value='/js/scripts.js' />"></script>
	<!-- <script type="text/javascript" src="<c:url value='/js/scripts.min.js' />"></script> -->

	<script>
    	 $(document).foundation({
    		tab: {
      callback : function (tab) {
        console.log(tab);
      }
    }
  });

  function loginAction(){
        var userName= $("#panel2-1:nth-child(1)").children().find("input:text").val();
		var pwd = $("#panel2-1:nth-child(1)").children().find("input:password").val();
		var dataJson= {};
		dataJson["userName"] = userName;
		dataJson["password"]=pwd;
        jsonData = JSON.stringify(dataJson);

		console.log(jsonData);
		$.ajax({
		  url: "/EmergeServices/org/login?isResponseRequired=true&storedProcedureName=em_checkLogin",
		  type: "POST",
		  headers: {"Accept":"application/json","Content-Type":"application/json"},
		  data: "["+jsonData+"]",
		  complete: function(response){
			jsonString = JSON.parse(response.responseText);
			//console.log(response);
			jsonStr = JSON.parse(jsonString['id']);
			console.log(jsonStr[0].response);
			if(jsonStr[0].response=="1"){
				window.location.href="home.jsp";
			}else if (jsonStr[0].response=="0"){
				alert("Invalid credentials");
			}

		},
		error: function( jqXHR,  textStatus,  errorThrown){
			alert("Error in API call");
		}
		})
  }
  function registerAction(){
        //var userName= $("#panel2-2:nth-child(1)").children().find("type:text").val();
		//var pwd = $("#panel2-2:nth-child(1)").children().find("type:password").val();
		var userName= document.getElementById("username").value;
		var pwd = document.getElementById("loginpass").value;
		var dataJson= {};
		dataJson["userName"] = userName;
		dataJson["password"]=pwd;
        jsonData = JSON.stringify(dataJson);
		console.log(jsonData);
		$.ajax({
		  url: "/EmergeServices/org/register?isResponseRequired=true&storedProcedureName=em_register_user" ,
		  type: "POST",
		  headers: {"Accept":"application/json","Content-Type":"application/json"},
		  data: "["+jsonData+"]",
		  complete: function(response){
			jsonString = JSON.parse(response.responseText);
			jsonStr = JSON.parse(jsonString['id']);
			console.log(jsonStr[0].response);
			if(jsonStr[0].response=="1"){
				window.location.href="home.jsp";
			}else if (jsonStr[0].response=="0"){
				alert("User already exists");
			}

		},
		error: function( jqXHR,  textStatus,  errorThrown){
			alert("Error in API call");
		}
		})
  }
    </script>
</body>
</html>
