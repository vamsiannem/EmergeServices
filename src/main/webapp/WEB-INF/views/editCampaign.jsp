<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>e-Merge Add/Edit Campaign</title>

    <link rel="stylesheet" href="<c:url value='/css/jqx.base.css' />" type="text/css"/>
    <link rel="stylesheet" href="<c:url value='/css/uniform.default.css' />" type="text/css"/>
    <link rel="stylesheet" href="<c:url value='/css/form.styles.default.css' />" type="text/css"/>
    <link rel="stylesheet" href="<c:url value='/css/bvalidator.theme.gray2.css' />" type="text/css"/>
    <link rel="stylesheet" href="<c:url value='/css/jquery-ui-1.10.3.custom.css' />" type="text/css"/>
    <link rel="stylesheet" href="<c:url value='/css/jqx.ui-redmond.css' />" type="text/css"/>
    <link rel="stylesheet" href="<c:url value='/css/jquery-ui-timepicker-addon.css' />" type="text/css"/>


    <script src="<c:url value='/js/jquery-1.9.1.js' />"></script>
    <script src="<c:url value='/js/jquery-form-serialize.js' />"></script>
    <script src="<c:url value='/js/jquery.uniform.js' />"></script>
    <script src="<c:url value='/js/utils.js' />"></script>
    <script src="<c:url value='/js/jquery-ui-1.10.3.custom.js' />"></script>
    <script type="text/javascript" src="<c:url value='/js/jquery.bvalidator.js' />"></script>
    <script src="<c:url value='/js/jquery-ui-timepicker-addon.js' />"></script>
    <script src="<c:url value='/js/jquery-ui-sliderAccess.js' />"></script>

    <script src="<c:url value='/js/jqxcore.js' />"></script>
    <script src="<c:url value='/js/jqxdata.js' />"></script>
    <script src="<c:url value='/js/jqxbuttons.js' />"></script>
    <script src="<c:url value='/js/jqxdropdownlist.js' />"></script>
    <script src="<c:url value='/js/jqxgrid.js' />"></script>
    <script src="<c:url value='/js/jqxgrid.pager.js' />"></script>
    <script src="<c:url value='/js/jqxgrid.selection.js' />"></script>
    <script src="<c:url value='/js/jqxgrid.sort.js' />"></script>
    <script src="<c:url value='/js/jqxlistbox.js' />"></script>
    <script src="<c:url value='/js/jqxmenu.js' />"></script>
    <script src="<c:url value='/js/jqxscrollbar.js' />"></script>
    <script src="<c:url value='/js/jqxgrid.columnsresize.js' />"></script>
    <script src="<c:url value='/js/jqxgrid.filter.js' />"></script>
    <script src="<c:url value='/js/jqxgrid.grouping.js' />"></script>

    <script type="text/javascript">

         var jsonString = '';
         var formData = '';

        $(document).ready(function () {
            var form = $("#editForm");

            //jquery tabs
            $( "#tabs" ).tabs();

            //date picker for occurence date
            $( ".datepicker" ).datetimepicker({
                dateFormat: 'yy-mm-dd',
                altField: '#launchTime',
                numberOfMonths: 3,
                minDate: 0,
                showButtonPanel: true
            });

            //form the request Body
            var queryString = {};
            queryString['campaignId'] = "${campaignId}";
            jsonData = JSON.stringify(queryString);

            //style forms
            $("select, input, textarea").uniform();

            //check if edit or add mode
            if("${add}"=="0"){
                //ajax call to populate data
                $.ajax({
                    type: "POST",
                    url: "/EmergeServices/org/campaignsInfo?isResponseRequired=true",
                    headers: {"Accept":"application/json","Content-Type":"application/json"},
                    username:"admin",
                    password:"password",
                    data: "["+jsonData+"]",
                    complete: function(response){
                        jsonString = JSON.parse(response.responseText);
                        formData = JSON.parse(jsonString['id']);
                        $.each(formData,function(key,value) {
                             form.find("[name='"+key+"']").val(value);
                             //console.log(key+":"+value);
                        });

                    }
                });
            }


            // get campaign metadata
            $.ajax({
                    type: "POST",
                    url: "/EmergeServices/org/metadata?isResponseRequired=true&storedProcedureName=getMetaData",
                    headers: {"Accept":"application/json","Content-Type":"application/json"},
                    username:"admin",
                    password:"password",
                    data: '[{"codeType":"Campaign","subCodeType":null}]',
                    complete: function(response){
                        metaString = JSON.parse(response.responseText);
                        metaData = JSON.parse(metaString['id']);

                        $.each(metaData,function(key,value) {
                                if(formData[value.quickCodeSubType]==value.quickCodeValue || (formData[value.quickCodeSubType.toLowerCase()]==value.quickCodeValue)){
                                    form.find("[name='"+value.quickCodeSubType+"']").append("<option selected value="+value.quickCodeValue+">"+value.quickCodeDescription+"</option>");
                                }else{
                                     form.find("[name='"+value.quickCodeSubType+"']").append("<option value="+value.quickCodeValue+">"+value.quickCodeDescription+"</option>");
                                }
                        });

                    }
                });

            //validate form
            var options = {
                    classNamePrefix: 'bvalidator_gray2_',
                    position: {x:'right', y:'center'},
                    offset:     {x:10, y:-25},
                    template: '<div class="{errMsgClass}"><div class="bvalidator_gray2_arrow"></div><div class="bvalidator_gray2_cont1">{message}</div></div>'
            };
            $('.dataForm').bValidator(options);

            // get template information
            $.ajax({
                    type: "GET",
                    url: "/EmergeServices/org/templates?isResponseRequired=true",
                    headers: {"Accept":"application/json","Content-Type":"application/json"},
                    username:"admin",
                    password:"password",
                    complete: function(response){
                        templateString = JSON.parse(response.responseText);
                        templateData = JSON.parse(templateString['id']);

                        $.each(templateData,function(key,value) {
                            if(formData['templateId']==value.templateId){
                                form.find("[name='templateId']").append("<option selected value="+value.templateId+">"+value.templateName+"</option>");
                            }else{
                                form.find("[name='templateId']").append("<option value="+value.templateId+">"+value.templateName+"</option>");
                            }
                        });

                    }
                });

            // get all segments
            $.ajax({
                    type: "GET",
                    url: "/EmergeServices/org/segment?isResponseRequired=true",
                    headers: {"Accept":"application/json","Content-Type":"application/json"},
                    username:"admin",
                    password:"password",
                    complete: function(response){
                        segmentString = JSON.parse(response.responseText);
                        segmentData = JSON.parse(segmentString['id']);

                        $.each(segmentData,function(key,value) {
                            form.find("[id='segments_incl']").append("<option name='INCL' value="+value.segmentId+">"+value.segmentName+"</option>");
                            form.find("[id='segments_excl']").append("<option name='EXC' value="+value.segmentId+">"+value.segmentName+"</option>");
                        });
                         //check if edit or add mode
                         if("${add}"=="0"){
                                // get all selected segments for the campaign
                                $.ajax({
                                        type: "POST",
                                        url: "/EmergeServices/org/campaignsegmentsInfo?isResponseRequired=true&storedProcedureName=campaignSegmentMap",
                                        headers: {"Accept":"application/json","Content-Type":"application/json"},
                                        username:"admin",
                                        password:"password",
                                        data:"["+jsonData+"]",
                                        complete: function(response){
                                            campaignSegmentString = JSON.parse(response.responseText);
                                            campaignSegmentData = JSON.parse(campaignSegmentString['id']);
                                            $.each(campaignSegmentData,function(key,value) {
                                                 form.find("[name='"+value.mSegmentNature+"'][value='"+value.segmentId+"']").attr("selected",true);
                                            });

                                        }
                                    });
                          }
                    }
                });


});

        $(window).load(function(){
            //Launch Style Valid
                    if(document.getElementById('launchStyle').value=="OCON"){
                        document.getElementById('occurenceDate').style.background='#ffffff';
                        document.getElementById('launchTime').style.background='#ffffff';
                        document.getElementById('occurenceDate').disabled=false;
                        document.getElementById('launchTime').disabled=false;
                    }else if(document.getElementById('launchStyle').value=="REC"){
                        document.getElementById('occurenceDate').style.background='#ffffff';
                        document.getElementById('launchTime').style.background='#ffffff';
                        document.getElementById('occurenceDate').disabled=false;
                        document.getElementById('launchTime').disabled=false;
                        document.getElementById('mLaunchFreq').disabled=false;
                    }
        })


         function getCurDate(){
            var curDate = new Date();
            var newDate = curDate.getFullYear()+"-"+curDate.getMonth()+"-"+curDate.getDate();
            return newDate;
         }

         function saveAction(){
            if($('.dataForm').data('bValidator').validate())  {

               var requestJSON = $('#editForm').serializeFormJSON();
               var campId;
               var inclusion = requestJSON['inclusion'];
               //console.log(inclusion);
               var exclusion = requestJSON['exclusion'];
               delete requestJSON['inclusion'];
               delete requestJSON['exclusion'];

               if(requestJSON.createdDate==''){
                requestJSON.createdDate = getCurDate();
               }

               requestJSON.modifiedDate = getCurDate();
               requestJSON.mCampaignStatus = 'CREATED';

               //console.log(requestJSON);

                var requestBody = "["+JSON.stringify(requestJSON)+"]";

                //ajax call to save data
                $.ajax({
                    type: "POST",
                    url: "/EmergeServices/org/campaigns?isResponseRequired=true",
                    headers: {"Accept":"application/json","Content-Type":"application/json"},
                    username:"admin",
                    password:"password",
                    data: requestBody,
                    complete: function(response){
                           campId = JSON.parse(response.responseText).id ;
                            if("${add}"!="0"){
                                $('.id_col').val(campId);
                            }
                            //console.log($('.id_col').val());
                            //ajax call to delete existing campaign segment map
                            $.ajax({
                                type: "DELETE",
                                url: "/EmergeServices/org/campaignsegments?isResponseRequired=true&storedProcedureName=deleteCampaignSegmentMap",
                                headers: {"Accept":"application/json","Content-Type":"application/json"},
                                username:"admin",
                                password:"password",
                                data: "["+JSON.stringify($('.id_col').serializeFormJSON())+"]",
                                complete: function(response){
                                   if(inclusion!=undefined){
                                       if(inclusion instanceof Array){
                                           for(i=0;i<inclusion.length;i++){
                                                campaignSegmentMapJSON = $('.id_col').serializeFormJSON();
                                                campaignSegmentMapJSON.segmentNature = "INCL";
                                                campaignSegmentMapJSON.segmentId = inclusion[i];
                                                inclusionJSON = "["+JSON.stringify(campaignSegmentMapJSON)+"]";
                                                $.ajax({
                                                    type: "POST",
                                                    url: "/EmergeServices/org/campaignsegments?isResponseRequired=true",
                                                    headers: {"Accept":"application/json","Content-Type":"application/json"},
                                                    username:"admin",
                                                    password:"password",
                                                    data: inclusionJSON
                                                });

                                           }
                                       }else{
                                                campaignSegmentMapJSON = $('.id_col').serializeFormJSON();
                                                campaignSegmentMapJSON.segmentNature = "INCL";
                                                campaignSegmentMapJSON.segmentId = inclusion;
                                                inclusionJSON = "["+JSON.stringify(campaignSegmentMapJSON)+"]";
                                                $.ajax({
                                                    type: "POST",
                                                    url: "/EmergeServices/org/campaignsegments?isResponseRequired=true",
                                                    headers: {"Accept":"application/json","Content-Type":"application/json"},
                                                    username:"admin",
                                                    password:"password",
                                                    data: inclusionJSON
                                                });
                                       }
                                   }
                                   if(exclusion!=undefined){
                                      if(exclusion instanceof Array){
                                            for(i=0;i<exclusion.length;i++){
                                                campaignSegmentMapJSON = $('.id_col').serializeFormJSON();
                                                campaignSegmentMapJSON.segmentNature = "EXC";
                                                campaignSegmentMapJSON.segmentId = exclusion[i];
                                                exclusionJSON = "["+JSON.stringify(campaignSegmentMapJSON)+"]";
                                                $.ajax({
                                                    type: "POST",
                                                    url: "/EmergeServices/org/campaignsegments?isResponseRequired=true",
                                                    headers: {"Accept":"application/json","Content-Type":"application/json"},
                                                    username:"admin",
                                                    password:"password",
                                                    data: exclusionJSON
                                                });
                                           }
                                       }else{
                                                campaignSegmentMapJSON = $('.id_col').serializeFormJSON();
                                                campaignSegmentMapJSON.segmentNature = "EXC";
                                                campaignSegmentMapJSON.segmentId = exclusion;
                                                exclusionJSON = "["+JSON.stringify(campaignSegmentMapJSON)+"]";
                                                $.ajax({
                                                    type: "POST",
                                                    url: "/EmergeServices/org/campaignsegments?isResponseRequired=true",
                                                    headers: {"Accept":"application/json","Content-Type":"application/json"},
                                                    username:"admin",
                                                    password:"password",
                                                    data: exclusionJSON
                                                });

                                       }

                                   }

                                    window.location.href="/EmergeServices/org/editProgram/id/"+$(".sec_id_col").val();
                                }
                            });
                    }
                });

            }
         }

        function deleteAction(){
            var didConfirm = confirm("Are you sure you want to delete?");
            if(didConfirm == true){
                var requestBody = "["+JSON.stringify($('.id_col').serializeFormJSON())+"]";

                //ajax call to save data
                $.ajax({
                    type: "DELETE",
                    url: "/EmergeServices/org/campaigns?isResponseRequired=true",
                    headers: {"Accept":"application/json","Content-Type":"application/json"},
                    username:"admin",
                    password:"password",
                    data: requestBody,
                    complete: function(response){
                       window.location.href="/EmergeServices/org/editProgram/id/"+$(".sec_id_col").val();
                    }
                });
            }
         }

        function validateLaunchStyle(val){
                if(val=='OCON'){
                    document.getElementById('occurenceDate').style.background='#ffffff';
                    document.getElementById('occurenceDate').disabled=false;
                    document.getElementById('launchTime').style.background='#ffffff';
                    document.getElementById('launchTime').disabled=false;
                }
                else{
                    document.getElementById('occurenceDate').disabled=true;
                    document.getElementById('occurenceDate').style.background='#e0e0e0';
                    document.getElementById('launchTime').disabled=true;
                    document.getElementById('launchTime').style.background='#e0e0e0';
                }

                if(val=='REC'){
                    document.getElementById('mLaunchFreq').disabled=false;
                    document.getElementById('occurenceDate').disabled=false;
                    document.getElementById('occurenceDate').style.background='#ffffff';
                    document.getElementById('launchTime').disabled=false;
                    document.getElementById('launchTime').style.background='#ffffff';
                }else{
                    document.getElementById('mLaunchFreq').disabled=true;
                }
            }

        function goBack(){
            window.location.href="/EmergeServices/org/editProgram/id/"+$(".sec_id_col").val();
        }

    </script>
</head>
<body>
<jsp:include page="header.jsp"/>
<div class="bodyContent">
    <div class="contentHeader">
        <span>Customize a Campaign</span>
        <a onclick="saveAction()" class="styledButton actionButtons">Save</a>
        <c:if test="${add==0}" >
            <a onclick="deleteAction()" class="styledButton actionButtons">Delete</a>
        </c:if>
        <a onclick="window.location.href='/EmergeServices/org/viewAllPrograms';" class="styledButton actionButtons">Back</a>
    </div>
    <div class="formContent">
        <div class="dataForm">
           <form id="editForm">
                <div id="tabs" style="width:90%;height:50%;margin-top:5%;font-size:13px">
           			<ul>
           				<li>
           					<a href="#tabs-1">1. General Information</a>
           				</li>
           				<li>
           					<a href="#tabs-2">2. Campaign Scheduling</a>
           				</li>
           				<li>
           					<a href="#tabs-3">3. Select A Template</a>
           				</li>
           				<li>
           					<a href="#tabs-4">4. Target Segments</a>
           				</li>
           			</ul>
           			<div id="tabs-1">
           				<table style="margin-top:2%">
           					<tr>
           						<td class="odd" valign="top"><label>Campaign Name *</label></td>
           						<td class="even" valign="top">
           						<input type="text" name="campaignName" data-bvalidator="required,alphanum">
           						</td>
           					</tr>
           					<tr>
           						<td class="odd" valign="top"><label>Campaign Category *</label></td>
           						<td class="even" valign="top">
                                    <select size="7" name="mCategory" data-bvalidator="required,max[1]">
                                        <option></option>
                                    </select>
           						</td>
           					</tr>
           					<tr>
           						<td class="odd" valign="top"><label>Link Tracking *</label></td>
           						<td class="even" valign="top">
           						<select size="7" name="mLinkTracking" data-bvalidator="required,max[1]">
           							<option></option>
           						</select></td>
           					</tr>
           				</table>
           			</div>
           			<div id="tabs-2">
           				<table style="margin-top:2%">
           					<tr>
           						<td class="odd" valign="top"><label>Launch Style *</label></td>
           						<td class="even" valign="top">
           						<select id="launchStyle" onclick="validateLaunchStyle(this.value)" size="7" name="mLaunchStyle" data-bvalidator="required,max[1]">
           							<option></option>
           						</select></td>
           					</tr>
           					<tr>
           						<td class="odd" valign="top"><label>Occurence Date</label></td>
           						<td class="even" valign="top">
           						<input style="background:#e0e0e0" disabled type="text" name="occurenceDate" class="datepicker" id="occurenceDate">
           						<input style="background:#e0e0e0" disabled type="text" name="launchTime" class="launchTime" id="launchTime" >
           						</td>
           					</tr>
           					<tr>
           						<td class="odd" valign="top"><label>Launch Frequency</label></td>
           						<td class="even" valign="top">
           						<select size="7"  id="mLaunchFreq" disabled name="mLaunchFreq" data-bvalidator="max[1]">
           							<option></option>
           						</select></td>
           					</tr>
           					<tr>
           						<td class="odd" valign="top"><label>Stop Mode</label></td>
           						<td class="even" valign="top">
           						    <input type="text" name="stopMode">
           						</td>
           					</tr>
           				</table>
           			</div>
           			<div id="tabs-3">
           				<table style="margin-top:2%">
           					<tr>
           						<td class="odd" valign="top"><label>Campaign Template *</label></td>
           						<td class="even" valign="top">
           						<select size="7" name="templateId" data-bvalidator="required,max[1]">
           							<option></option>
           						</select></td>
           					</tr>
           				</table>
           			</div>
           			<div id="tabs-4">
           				<table style="margin-top:2%">
           					<tr>
           						<td class="odd" valign="top"><label>Segments for Inclusion *</label></td>
           						<td class="even" valign="top">
           						<select size="10" id="segments_incl" name="inclusion" multiple="true" data-bvalidator="required">
           							<option></option>
           						</select></td>
           					</tr>
           					<tr>
           						<td class="odd" valign="top"><label>Suppression List</label></td>
           						<td class="even" valign="top">
           						<select size="10" id="segments_excl" name="exclusion" multiple="true">
           							<option></option>
           						</select></td>
           					</tr>
           					<tr class="visibilityNone">
           						<td class="odd" valign="top"></td>
           						<td class="even" valign="top">
           						<input type="hidden" name="createdBy">
           						</td>
           					</tr>
           					<tr class="visibilityNone">
           						<td class="odd" valign="top"></td>
           						<td class="even" valign="top">
           						<input type="hidden" name="createdDate">
           						</td>
           					</tr>
           					<tr class="visibilityNone">
           						<td class="odd" valign="top"></td>
           						<td class="even" valign="top">
           						<input type="hidden" name="programId" class="sec_id_col" value="${programId}">
           						</td>
           					</tr>
           					<tr class="visibilityNone">
           						<td class="odd" valign="top"></td>
           						<td class="even" valign="top">
           						<input type="hidden" name="campaignId" class="id_col">
           						</td>
           					</tr>
           				</table>
           			</div>
           		</div>
           </form>
        </div>
    </div>
</div>
<jsp:include page="footer.jsp"/>
</body>
</html>