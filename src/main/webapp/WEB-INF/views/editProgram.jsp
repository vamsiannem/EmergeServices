<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>e-Merge Add/Edit Program</title>

    <link rel="stylesheet" href="<c:url value='/css/jqx.base.css' />" type="text/css"/>
    <link rel="stylesheet" href="<c:url value='/css/uniform.default.css' />" type="text/css"/>
    <link rel="stylesheet" href="<c:url value='/css/form.styles.default.css' />" type="text/css"/>
    <link rel="stylesheet" href="<c:url value='/css/bvalidator.theme.gray2.css' />" type="text/css"/>
    <link rel="stylesheet" href="<c:url value='/css/jquery-ui-1.10.3.custom.css' />" type="text/css"/>
    <link rel="stylesheet" href="<c:url value='/css/jqx.ui-overcast.css' />" type="text/css"/>


    <script src="<c:url value='/js/jquery-1.9.1.js' />"></script>
    <script src="<c:url value='/js/jquery-form-serialize.js' />"></script>
    <script src="<c:url value='/js/jquery.uniform.js' />"></script>
    <script src="<c:url value='/js/jquery-ui-1.10.3.custom.js' />"></script>
    <script type="text/javascript" src="<c:url value='/js/jquery.bvalidator.js' />"></script>

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
            $( "#tabs" ).tabs();

            var form = $("#editForm");

            //form the request Body
            var queryString = {};
            queryString['programId'] = "${programId}";
            jsonData = JSON.stringify(queryString);

            //style forms
            $("select, input, textarea").uniform();

            //check if edit or add mode
            if("${add}"=="0"){
                //ajax call to populate data
                $.ajax({
                    type: "POST",
                    url: "/EmergeServices/org/programsInfo?isResponseRequired=true",
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

                        $(document).find(".progHeader").html(formData.programName);
                    }
                });
            }

            //validate form
            var options = {
                    classNamePrefix: 'bvalidator_gray2_',
                    position: {x:'right', y:'center'},
                    offset:     {x:15, y:0},
                    template: '<div class="{errMsgClass}"><div class="bvalidator_gray2_arrow"></div><div class="bvalidator_gray2_cont1">{message}</div></div>'
            };
            $('.dataForm').bValidator(options);


            // render campaign grid
                   var campaignData = '';
                    $.ajax({
                        type: "POST",
                    	url: "/EmergeServices/org/campaignPrograms?isResponseRequired=true&storedProcedureName=campaignProgramReadAll",
                    	data: "["+jsonData+"]",
                        headers: {"Accept":"application/json","Content-Type":"application/json"},
                        username:"admin",
                        password:"password",
                        complete: function(response){
                    	    campaignJsonString = JSON.parse(response.responseText);
                    	    campaignJsonStr = JSON.parse(campaignJsonString['id']);
                            campaignData = campaignJsonStr;
                            renderGrid(campaignData);
                    	    /*$.each(campaignJsonStr, function(i) {
                                console.log(campaignJsonStr[i]);
                            });*/
                    	}
                     });

         });

         function getCurDate(){
            var curDate = new Date();
            var newDate = curDate.getFullYear()+"-"+curDate.getMonth()+"-"+curDate.getDate();
            return newDate;
         }

         function saveAction(){
            if($('.dataForm').data('bValidator').validate())  {

                var requestJSON = $('#editForm').serializeFormJSON();

                if(requestJSON.createdDate==''){
                    requestJSON.createdDate = getCurDate();
                }

                requestJSON.modifiedDate = getCurDate();

                var requestBody = "["+JSON.stringify(requestJSON)+"]";

                //ajax call to save data
                $.ajax({
                    type: "POST",
                    url: "/EmergeServices/org/programs?isResponseRequired=true",
                    headers: {"Accept":"application/json","Content-Type":"application/json"},
                    username:"admin",
                    password:"password",
                    data: requestBody,
                    complete: function(response){
                       window.location.href = "/EmergeServices/org/viewAllPrograms";
                    }
                });
            }
         }

        function deleteAction(){
            var didConfirm = confirm("Are you sure you want to delete?");
            if(didConfirm == true){
                var requestBody = "["+JSON.stringify($('.id_col').serializeFormJSON())+"]";

                //ajax call to delete data
                $.ajax({
                    type: "DELETE",
                    url: "/EmergeServices/org/programs?isResponseRequired=true",
                    headers: {"Accept":"application/json","Content-Type":"application/json"},
                    username:"admin",
                    password:"password",
                    data: requestBody,
                    complete: function(response){
                       window.location.href = "/EmergeServices/org/viewAllPrograms";
                    }
                });
            }
         }

         function addCampaigns(){
            window.location.href = "/EmergeServices/org/addCampaign/programId/${programId}";
         }


                    // render the grid
                    function renderGrid(data){
                        var source =
                        {
                            datatype: "json",
                            datafields: [
                                { name: 'campaignId' },
                                { name: 'campaignName' },
                                { name: 'mCampaignStatus'},
                                { name: 'mCategory'},
                                { name: 'mLaunchStyle' },
                                { name: 'stopMode' },
                                { name: 'mLinkTracking' }
                            ],
                            localdata: data
                        };

                        var dataAdapter = new $.jqx.dataAdapter(source, {
                            loadComplete: function (data) { },
                            loadError: function (xhr, status, error) { }
                        });
                        $("#jqxgrid").jqxGrid(
                        {
                            width: "95%",
                            source: dataAdapter,
                            pageable: true,
                            autoheight: true,
                            theme: 'ui-overcast',
                            columns: [
                              { text: 'Campaign ID', datafield: 'campaignId', width: "10%" },
                              { text: 'Camapaign Name', datafield: 'campaignName', width: "30%" },
                              { text: 'Status', datafield: 'mCampaignStatus', width: "10%" },
                              { text: 'Category', datafield: 'mCategory', width: "10%" },
                              { text: 'Launch Style', datafield: 'mLaunchStyle', width: "15%" },
                              { text: 'Stop Mode', datafield: 'stopMode', width: "10%" },
                              { text: 'Link Tracking', datafield: 'mLinkTracking', width: "15%" }
                            ]
                        });

                        $("#jqxgrid").bind('rowselect', function (event) {
                            var row = event.args.rowindex;
                            var datarow = $("#jqxgrid").jqxGrid('getrowdata', row);
                            //console.log(datarow.campaignId);
                            window.location.href = "/EmergeServices/org/editCampaign/id/"+datarow.campaignId;
                        });

                        $("#jqxgrid").jqxGrid({keyboardnavigation: false});
                    }
    </script>
</head>
<body>
    <jsp:include page="header.jsp"/>
    <div class="bodyContent">
        <div class="contentHeader">
            <span>Edit Program Details</span>
            <a onclick="saveAction()" class="styledButton actionButtons">Save</a>
            <c:if test="${add==0}" >
                <a onclick="deleteAction()" class="styledButton actionButtons">Delete</a>
                <a onclick="addCampaigns()" class="styledButton actionButtons">Add Campaigns</a>
            </c:if>
            <a onclick="window.location.href='/EmergeServices/org/viewAllPrograms';" class="styledButton actionButtons">Back</a>
        </div>
        <div class="formContent">
            <h2 class="progHeader">
                <c:if test="${add!=0}" >
                     Add New Program
                </c:if>
            </h2>
            <div id="tabs" style="width:90%;font-size:13px">
                <ul>
                    <li><a href="#tabs-1">Program Information</a></li>
                    <li><a href="#tabs-2">Related Campaigns</a></li>
                </ul>
                <div id="tabs-1">
                    <form id="editForm">
                        <table style="margin-top:2%" class="dataForm">
                            <tr>
                                <td class="odd" valign="top"><label>Program Name *</label></td>
                                <td class="even" valign="top"><input type="text" name="programName" data-bvalidator="required,alphanum"></td>
                            </tr>
                            <tr>
                                <td class="odd" valign="top"><label>Program Description *</label></td>
                                <td class="even" valign="top"><textarea name="programDescription" data-bvalidator="required,maxlength[500]"></textarea></td>
                            </tr>
                            <tr class="visibilityNone">
                                <td class="odd" valign="top"></td>
                                <td class="even" valign="top"><input type="hidden" name="createdDate"></td>
                            </tr>
                            <tr class="visibilityNone">
                                <td class="odd" valign="top"></td>
                                <td class="even" valign="top"><input type="hidden" name="createdBy"></td>
                            </tr>
                            <tr class="visibilityNone">
                                <td class="odd" valign="top"></td>
                                <td class="even" valign="top"><input type="hidden" name="programId" class="id_col"></td>
                            </tr>
                        </table>
                    </form>
                </div>
                <div id="tabs-2">
                    <div id="jqxgrid" style="margin-top:2%"></div>
                </div>
            </div>
        </div>
    </div>
    <jsp:include page="footer.jsp"/>
</body>
</html>