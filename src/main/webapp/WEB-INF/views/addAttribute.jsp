<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>e-Merge Add/Edit Attribute</title>

    <link rel="stylesheet" href="<c:url value='/css/jqx.base.css' />" type="text/css"/>
    <link rel="stylesheet" href="<c:url value='/css/uniform.default.css' />" type="text/css"/>
    <link rel="stylesheet" href="<c:url value='/css/form.styles.default.css' />" type="text/css"/>
    <link rel="stylesheet" href="<c:url value='/css/bvalidator.theme.gray2.css' />" type="text/css"/>
    <link rel="stylesheet" href="<c:url value='/css/jquery-ui-1.10.3.custom.css' />" type="text/css"/>
    <link rel="stylesheet" href="<c:url value='/css/jqx.ui-redmond.css' />" type="text/css"/>


    <script src="<c:url value='/js/jquery-1.9.1.js' />"></script>
    <script src="<c:url value='/js/jquery-form-serialize.js' />"></script>
    <script src="<c:url value='/js/jquery.uniform.js' />"></script>
    <script src="<c:url value='/js/jquery-ui-1.10.3.custom.js' />"></script>
    <script type="text/javascript" src="<c:url value='/js/jquery.bvalidator.js' />"></script>


    <script type="text/javascript">

         var jsonString = '';
         var formData = '';

        $(document).ready(function () {

            var form = $("#editForm");

            //style forms
            $("select, input, textarea").uniform();

            //validate form
            var options = {
                    classNamePrefix: 'bvalidator_gray2_',
                    position: {x:'right', y:'center'},
                    offset:     {x:15, y:0},
                    template: '<div class="{errMsgClass}"><div class="bvalidator_gray2_arrow"></div><div class="bvalidator_gray2_cont1">{message}</div></div>'
            };
            $('.dataForm').bValidator(options);

            // get attribute metadata
            $.ajax({
                    type: "POST",
                    url: "/EmergeServices/org/metadata?isResponseRequired=true&storedProcedureName=getMetaData",
                    headers: {"Accept":"application/json","Content-Type":"application/json"},
                    username:"admin",
                    password:"password",
                    data: '[{"codeType":"Attribute","subCodeType":null}]',
                    complete: function(response){
                        metaString = JSON.parse(response.responseText);
                        metaData = JSON.parse(metaString['id']);

                        $.each(metaData,function(key,value) {
                           form.find("[name='"+value.quickCodeSubType+"']").append("<option value="+value.quickCodeValue+">"+value.quickCodeDescription+"</option>");
                        });

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
                    url: "/EmergeServices/org/attributes?isResponseRequired=true",
                    headers: {"Accept":"application/json","Content-Type":"application/json"},
                    username:"admin",
                    password:"password",
                    data: requestBody,
                    complete: function(response){
                       window.location.href = "/EmergeServices/org/viewAllAttributes";
                    }
                });
            }
         }

         function getColumns(tableJSON){
            if(tableJSON.tableName==""){
                document.getElementById('columnName').disabled=true;
            }else{
                document.getElementById('columnName').disabled=false;
                requestTableJSON = "["+JSON.stringify(tableJSON)+"]";
                $.ajax({
                        type: "POST",
                        url: "/EmergeServices/org/columns?isResponseRequired=true&storedProcedureName=retrieveColumns",
                        headers: {"Accept":"application/json","Content-Type":"application/json"},
                        username:"admin",
                        password:"password",
                        data: requestTableJSON,
                        complete: function(response){
                            columnString = JSON.parse(response.responseText);
                            columnData = JSON.parse(columnString['id']);
                            $("#editForm").find("[name='columnName']").html("");
                            $.each(columnData,function(key,columns) {
                                $.each(columns,function(key,value) {
                                    $("#editForm").find("[name='"+key+"']").append("<option value="+value+">"+value+"</option>");
                                });
                            });
                        }
                    });
            }
         }

    </script>
</head>
<body>
<jsp:include page="header.jsp"/>
<div class="bodyContent">
    <div class="contentHeader">
        <span>Select a new KPI to be used for targeting</span>
        <a onclick="saveAction()" class="styledButton actionButtons">Save KPI</a>
        <a onclick="window.location.href='/EmergeServices/org/viewAllAttributes';" class="styledButton actionButtons">Back</a>
    </div>
    <div class="formContent">
                <form id="editForm">
                    <table style="margin-top:2%" class="dataForm">
                        <tr>
                            <td class="odd" valign="top"><label>Attribute Name *</label></td>
                            <td class="even" valign="top"><input type="text" name="attributeName" data-bvalidator="required,alphanum"></td>
                        </tr>
                        <tr>
                            <td class="odd" valign="top"><label>Attribute Type *</label></td>
                            <td class="even" valign="top">
                                <select size="7" name="attributeType" data-bvalidator="required,max[1]">
                                     <option></option>
                                </select>
                            </td>
                        </tr>
                        <tr>
                            <td class="odd" valign="top"><label>Table Name *</label></td>
                            <td class="even" valign="top">
                                <select onclick="getColumns($(this).serializeFormJSON())" size="5" id="tableName" name="tableName" data-bvalidator="required,max[1]">
                                     <option></option>
                                </select>
                            </td>
                        </tr>
                        <tr>
                            <td class="odd" valign="top"><label>Column Name *</label></td>
                            <td class="even" valign="top">
                                <select size="15" name="columnName" id="columnName" disabled data-bvalidator="required,max[1]">
                                     <option></option>
                                </select>
                            </td>
                        </tr>
                        <tr class="visibilityNone">
                            <td class="odd" valign="top"></td>
                            <td class="even" valign="top"><input type="hidden" name="createdDate"></td>
                        </tr>
                        <tr class="visibilityNone">
                            <td class="odd" valign="top"></td>
                            <td class="even" valign="top"><input type="hidden" name="activeFlag" value="Yes"></td>
                        </tr>
                        <tr class="visibilityNone">
                            <td class="odd" valign="top"></td>
                            <td class="even" valign="top"><input type="hidden" name="createdBy"></td>
                        </tr>
                    </table>
                </form>
    </div>
</div>
<jsp:include page="footer.jsp"/>
</body>
</html>