<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>e-Merge Add/Edit Template</title>

    <link rel="stylesheet" href="<c:url value='/css/jqx.base.css' />" type="text/css"/>
    <link rel="stylesheet" href="<c:url value='/css/uniform.default.css' />" type="text/css"/>
    <link rel="stylesheet" href="<c:url value='/css/form.styles.default.css' />" type="text/css"/>
    <link rel="stylesheet" href="<c:url value='/css/bvalidator.theme.gray2.css' />" type="text/css"/>
    <link rel="stylesheet" href="<c:url value='/css/jquery-ui-1.10.3.custom.css' />" type="text/css"/>
    <link rel="stylesheet" href="<c:url value='/css/jqx.ui-redmond.css' />" type="text/css"/>


    <script src="<c:url value='/js/jquery-1.9.1.js' />"></script>
    <script src="<c:url value='/js/ckeditor.js' />"></script>
    <script src="<c:url value='/js/jquery-form-serialize.js' />"></script>
    <script src="<c:url value='/js/jquery.uniform.js' />"></script>
    <script src="<c:url value='/js/jquery-ui-1.10.3.custom.js' />"></script>
    <script type="text/javascript" src="<c:url value='/js/jquery.bvalidator.js' />"></script>


    <script type="text/javascript">

        var jsonString = '';
        var formData = '';

        //form the request Body
        var queryString = {};
        queryString['templateId'] = "${templateId}";
        jsonData = JSON.stringify(queryString);



        $(document).ready(function () {

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


        var form = $("#editForm");

                //check if add or update
                if("${add}"=="0"){
                    //ajax call to populate data
                    $.ajax({
                        type: "POST",
                        url: "/EmergeServices/org/templateInfo?isResponseRequired=true",
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

        $(window).load(function(){
             CKEDITOR.replace('ckeditor',{
               height:'400px'
             });
        })

        function getAttributes(attrJSON){
                    if(attrJSON.attributeType==""){
                       // document.getElementById('columnName').disabled=true;
                    }else{
                        //document.getElementById('columnName').disabled=false;
                        requestAttrJSON = "["+JSON.stringify(attrJSON)+"]";
                        $.ajax({
                                type: "POST",
                                url: "/EmergeServices/org/attributeByType?isResponseRequired=true&storedProcedureName=retrieveAttributeByType",
                                headers: {"Accept":"application/json","Content-Type":"application/json"},
                                username:"admin",
                                password:"password",
                                data: requestAttrJSON,
                                complete: function(response){
                                    attrString = JSON.parse(response.responseText);
                                    attrData = JSON.parse(attrString['id']);
                                    $("#editForm").find("[name='feature']").html("");
                                    $.each(attrData,function(key,attribs) {
                                            $("#editForm").find("[name='feature']").append("<option value="+attribs.attributeId+">"+attribs.attributeName+"</option>");
                                    });
                                }
                            });
                    }
                 }

        function personalizeTemplate(attrId){
               var alias=prompt("Enter an alias for the attribute selected.");
               if(alias){
                    CKEDITOR.instances['ckeditor'].insertText('*|'+alias+'|*');
              }

              var requestJSON = CKEDITOR.instances.ckeditor.document.getBody().getText();

              document.getElementById('alias_element').value+="~"+alias;
              document.getElementById('selected').value+="~"+attrId;
        }

         function getCurDate(){
            var curDate = new Date();
            var newDate = curDate.getFullYear()+"-"+curDate.getMonth()+"-"+curDate.getDate();
            return newDate;
         }


        function saveAction(){
            if($('.dataForm').data('bValidator').validate())  {

                var requestJSON = $('#editForm').serializeFormJSON();
                requestJSON.templateContentHtml = CKEDITOR.instances.ckeditor.getData();
                if(requestJSON.createdDate==''){
                    requestJSON.createdDate = getCurDate();
                }
                delete requestJSON['attributeType'];
                delete requestJSON['feature'];

                requestJSON.modifiedDate = getCurDate();

                var requestBody = "["+JSON.stringify(requestJSON)+"]";

                //ajax call to save data
                $.ajax({
                    type: "POST",
                    url: "/EmergeServices/org/templates?isResponseRequired=true",
                    headers: {"Accept":"application/json","Content-Type":"application/json"},
                    username:"admin",
                    password:"password",
                    data: requestBody,
                    complete: function(response){
                        templId = JSON.parse(response.responseText).id ;
                        var aliases = document.getElementById('alias_element').value.split("~");
                        var attrIds = document.getElementById('selected').value.split("~");

                        for(i=0;i<aliases.length;i++){
                            if(i==0){
                                continue;
                            }else{
                                $.ajax({
                                    type: "POST",
                                    url: "/EmergeServices/org/templatedetail?isResponseRequired=true",
                                    headers: {"Accept":"application/json","Content-Type":"application/json"},
                                    username:"admin",
                                    password:"password",
                                    data: '[{"templateId":'+templId+',"persFieldName":"'+aliases[i]+'","attributeId":'+attrIds[i]+'}]',
                                });
                            }
                        }

                        window.location.href = "/EmergeServices/org/viewAllTemplates";
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
                    url: "/EmergeServices/org/templates?isResponseRequired=true",
                    headers: {"Accept":"application/json","Content-Type":"application/json"},
                    username:"admin",
                    password:"password",
                    data: requestBody,
                    complete: function(response){
                       window.location.href="/EmergeServices/org/viewAllTemplates";
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
        <span>Create Personalized E-Mail Content</span>
        <a onclick="saveAction()" class="styledButton actionButtons">Save Template</a>
        <a onclick="deleteAction()" class="styledButton actionButtons">Delete</a>
        <a onclick="window.location.href='/EmergeServices/org/viewAllTemplates';" class="styledButton actionButtons">Back</a>
    </div>
    <div class="formContent">
            <form id="editForm" style="width:90%">
                    <table style="margin-top:2%" class="dataForm">
                        <tr>
                            <td class="odd" valign="top"><label>Template Name *</label></td>
                            <td class="" valign="top"><input type="text" style="width:300px;" name="templateName" data-bvalidator="required,alphanum"></td>
                        </tr>
                        <tr>
                            <td class="odd" valign="top"><label>Mail Subject *</label></td>
                            <td class="" valign="top"><input type="text" style="width:300px;" name="mailSubject" data-bvalidator="required"></td>
                        </tr>
                        <tr>
                            <td class="odd" valign="top"><label>Attribute Type</label></td>
                            <td class="" valign="top">
                                <select size="7" name="attributeType" id="attributeType" onclick="getAttributes($(this).serializeFormJSON())">
                                     <option></option>
                                </select>
                            </td>
                        </tr>
                        <tr>
                            <td class="odd" valign="top"><label>Attribute</label></td>
                            <td class="" valign="top">
                                <select size="8" name="feature" id="feature" onclick="personalizeTemplate(this.value)">
                                </select>
                            </td>
                        </tr>
                        <tr>
                            <td class="odd" valign="top"><label>Template Content *</label></td>
                            <td class="" valign="top"><textarea name="templateContentHtml" id="ckeditor"></textarea></td>
                        </tr>
                        <tr class="visibilityNone">
                            <td class="odd" valign="top"></td>
                            <td class="" valign="top"><input type="hidden" name="createdDate"></td>
                        </tr>
                        <tr class="visibilityNone">
                            <td class="odd" valign="top"></td>
                            <td class="" valign="top"><input type="hidden" name="mActiveFlag" value="Yes"></td>
                        </tr>
                        <tr class="visibilityNone">
                            <td class="odd" valign="top"></td>
                            <td class="" valign="top"><input type="hidden" name="createdBy"></td>
                        </tr>
                        <tr class="visibilityNone">
                            <td class="odd" valign="top"></td>
                            <td class="" valign="top"><input type="hidden" name="templateId" class="id_col"></td>
                        </tr>
                    </table>
            </form>
            <input type="hidden" id="alias_element" name="alias_element" value="" />
            <input type="hidden" id="selected" name="selected" value="" />
    </div>
</div>
<jsp:include page="footer.jsp"/>
</body>
</html>