<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>e-Merge Attributes</title>
    <link rel="stylesheet" href="<c:url value='/css/jqx.base.css' />" type="text/css"/>
    <link rel="stylesheet" href="<c:url value='/css/jqx.ui-overcast.css' />" type="text/css"/>
    <link rel="stylesheet" href="<c:url value='/css/form.styles.default.css' />" type="text/css"/>
    <script src="<c:url value='/js/jquery-1.9.1.js' />"></script>
</head>
<body>
    <jsp:include page="header.jsp"/>
    <div id="flashContent" style="text-align:center;">
    			<object classid="clsid:d27cdb6e-ae6d-11cf-96b8-444553540000" width="1034" height="630" id="eMergeDemoV2" align="middle">
    				<param name="movie" value="<c:url value='/swf/eMergeDemoV2.swf' />" />
    				<param name="quality" value="high" />
    				<param name="bgcolor" value="#ffffff" />
    				<param name="play" value="true" />
    				<param name="loop" value="true" />
    				<param name="wmode" value="transparent" />
    				<param name="scale" value="showall" />
    				<param name="menu" value="true" />
    				<param name="devicefont" value="false" />
    				<param name="salign" value="" />
    				<param name="allowScriptAccess" value="sameDomain" />
    				<!--[if !IE]>-->
    				<object type="application/x-shockwave-flash" data="<c:url value='/swf/eMergeDemoV2.swf' />" width="1034" height="630">
    					<param name="movie" value="<c:url value='/swf/eMergeDemoV2.swf' />" />
    					<param name="quality" value="high" />
    					<param name="bgcolor" value="#ffffff" />
    					<param name="play" value="true" />
    					<param name="loop" value="true" />
    					<param name="wmode" value="transparent" />
    					<param name="scale" value="showall" />
    					<param name="menu" value="true" />
    					<param name="devicefont" value="false" />
    					<param name="salign" value="" />
    					<param name="allowScriptAccess" value="sameDomain" />
    				<!--<![endif]-->
    					<a href="http://www.adobe.com/go/getflash">
    						<img src="http://www.adobe.com/images/shared/download_buttons/get_flash_player.gif" alt="Get Adobe Flash player" />
    					</a>
    				<!--[if !IE]>-->
    				</object>
    				<!--<![endif]-->
    			</object>
    </div>
    <jsp:include page="footer.jsp"/>
</body>
</html>