<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en" class="no-js">
	<head>
		<title>e-Merge Dashboard</title>
		<link rel="stylesheet" type="text/css" href="<c:url value='/css/default.css' />" />
		<link rel="stylesheet" type="text/css" href="<c:url value='/css/component.css' />" />
		<link rel="stylesheet" href="<c:url value='/css/form.styles.default.css' />" type="text/css"/>
		<script src="<c:url value='/js/modernizr.custom.js' />"></script>
	</head>
	<body>
		<div class="container" style="background:#eeffff">
			<div class="header" style="position:relative">
				<span>
				    <a href="/EmergeServices/org/home">
				        <img class="logo" src="<c:url value='/images/emerge-logo.png' />" style="width:15%;height:11%;">
				    </a>
				</span>
				<span style="position:absolute;top:50%;margin-left:2%;font-size:1.5em">The Gateway to Integrated e-Business</span>
				<span>
				    <a href="http://www.listertechnologies.com">
				        <img class="logo" src="<c:url value='/images/lister.png' />" style="position:absolute;width:13.5%;height:48%;left:68%;top:40%;">
                    </a>
			    </span>
			</div>
			<div class="main">
				<nav id="cbp-hrmenu" class="cbp-hrmenu">
					<ul>
						<li>
							<a href="#">1. Attributes For Segmentation</a>
							<div class="cbp-hrsub">
								<div class="cbp-hrsub-inner">
									<div>
										<h4>Select relevant KPIs</h4>
										<div class="contentDiv">
											Select the KPIs that you think are most relevant to be included while targeting customers.........(Sample Description).<p><button onclick="window.location.href='/EmergeServices/org/viewAllAttributes'" class="styledButton">View All Attributes</button></p>
										</div>
									</div>

								</div><!-- /cbp-hrsub-inner -->
							</div><!-- /cbp-hrsub -->
						</li>
						<li>
							<a href="#">2. Create a personalized template</a>
							<div class="cbp-hrsub">
								<div class="cbp-hrsub-inner">
									<div>
										<h4>Design your own E-Mail Templates</h4>
										<div class="contentDiv">Templates Sample Description
<p><button onclick="window.location.href='/EmergeServices/org/viewAllTemplates'" class="styledButton">View All Email Templates</button></p>
										</div>
									</div>
								</div><!-- /cbp-hrsub-inner -->
							</div><!-- /cbp-hrsub -->
						</li>
						<li>
							<a href="#">3. Manage Customer Segments</a>
							<div class="cbp-hrsub">
								<div class="cbp-hrsub-inner">
									<div>
										<h4>Create New Customer Groups</h4>
										<div class="contentDiv">Group your customer base into meaningful segments for efficient targeting.
<p><button onclick="window.location.href='/EmergeServices/org/viewAllSegments'" class="styledButton">View Customer Segments</button></p>
										</div>
									</div>
								</div><!-- /cbp-hrsub-inner -->
							</div><!-- /cbp-hrsub -->
						</li>
						<li>
							<a href="#">4. Manage Programs and Campaigns</a>
							<div class="cbp-hrsub">
								<div class="cbp-hrsub-inner">
									<div>
										<h4>Create Marketing Programs and Campaigns</h4>
										<div class="contentDiv">
											<div>1. Create a Marketing event</div>
											<div>2. Create a Campaign</div>
											<div>3. Schedule the campaign</div>
											<div>4. Attach a Template</div>
											<div>5. Select the customer groups</div>
											<div>6. Blast the Emails!!!</div>
<p><button onclick="window.location.href='/EmergeServices/org/viewAllPrograms'" class="styledButton">View All Programs</button></p>
										</div>
									</div>
								</div><!-- /cbp-hrsub-inner -->
							</div><!-- /cbp-hrsub -->
						</li>
						<li>
							<a href="#">5. Reports</a>
							<div class="cbp-hrsub">
								<div class="cbp-hrsub-inner">
									<div>
										<h4>View Charts and Reports</h4>
							            <div class="contentDiv">
							            View your data in the form of meaningful charts and reports
<p><button onclick="window.location.href='/EmergeServices/org/viewAllSegments'" class="styledButton">View All Charts</button></p>
										</div>
									</div>
								</div><!-- /cbp-hrsub-inner -->
							</div><!-- /cbp-hrsub -->
						</li>
					</ul>
				</nav>
			</div>
		</div>

		<script src="<c:url value='/js/cbpHorizontalMenu.min.js' />"></script>
		<script>
			$(function() {
				cbpHorizontalMenu.init();
			});
		</script>
	</body>
</html>