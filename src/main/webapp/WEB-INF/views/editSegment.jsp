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
    <script src="<c:url value='/js/jquery-serialize-object.js' />"></script>
    <script src="<c:url value='/js/jquery.uniform.js' />"></script>
    <script src="<c:url value='/js/utils.js' />"></script>
    <script src="<c:url value='/js/query_gen.js' />"></script>
    <script src="<c:url value='/js/jquery-ui-1.10.3.custom.js' />"></script>
    <script type="text/javascript" src="<c:url value='/js/jquery.bvalidator.js' />"></script>
    <script>
        function removeCondition(condition){
        	$(condition).parent().parent().parent().remove();
        }

        function removeContainer(container){
        	$(container).parent().remove();
        }

            var counter;
            var ctr;
             //initialize counter to size of conditions, if it is a new condition, set it to zero
            if("${add}"!="0"){
                counter = 1;
            }

            if("${add}"!="0"){
                ctr = 1;
            }

        $(document).ready(function() {

            var form = $("#editForm");

            //form the request Body
            var queryString = {};
            queryString['segmentId'] = "${segmentId}";
            jsonData = JSON.stringify(queryString);

            if("${add}"=="0"){
                //ajax call to populate data
                $.ajax({
                    type: "POST",
                    url: "/EmergeServices/org/segmentInfo?isResponseRequired=true",
                    headers: {"Accept":"application/json","Content-Type":"application/json"},
                    username:"admin",
                    password:"password",
                    data: "["+jsonData+"]",
                    complete: function(response){
                        jsonString = JSON.parse(response.responseText);
                        formData = JSON.parse(jsonString['id']);
                        $.each(formData,function(key,value) {
                             form.find("[name='"+key+"']").val(value);
                        });

                        var unserializedCondition = JSON.parse(formData.conditionSerialized);
                        //console.log(unserializedCondition);

                        $.each(unserializedCondition,function(conditionId,conditionGroups) {
                            $('.right_canvas').append('<div class="main_container" id="'+conditionId+'"><div class="attrib_container droppable"></div><a id="remove" onclick="removeContainer(this)"><div class="container_closebtn">X</div></a><div class="attrib_clear"></div><div class="container_btns"><select name="group['+conditionId+'][groupCondition]" style="width:70px"><option>--</option><option>AND</option><option>OR</option></select></div></div>').sortable({
                                items: ".main_container"
                            });
                            $('.main_container#'+conditionId+' option:contains("'+conditionGroups.groupCondition+'")').prop('selected',true);

                            //console.log(conditionGroups);
                            $.each(conditionGroups,function(key,conditionGroup){
                                if(conditionGroup.attributeCode==undefined && key!=='groupCondition'){
                                    $('.main_container#'+conditionId+' .attrib_container').append('<div class="sub_main_container rightcon_sets '+key+'"><div class="attrib_subgroup_container droppable" id="'+key+'"></div><a id="removeSubGroup" onclick="removeContainer(this)"><div class="container_closebtn">X</div></a><div class="attrib_clear"></div><div class="container_btns"><select class="subSelect" name="group['+conditionId+']['+key+'][indCondition]" style="width:70px"><option>--</option><option>AND</option><option>OR</option></select></div></div>');
                                    $.each(conditionGroup,function(subkey,subCondition){
                                       //console.log(key+":"+subkey);
                                       if(subkey!='indCondition'){
                                            $('.attrib_subgroup_container#'+key+'').append('<div class="rightcon_sets" id="'+subkey+'"><div  class="rightcon_attribs"><div class="attrib_name" style=""><b>'+subCondition.attributeCode+'</b><input type="hidden" name="group['+conditionId+']['+key+']['+subkey+'][attributeType]" value="'+subCondition.attributeType+'"><input type="hidden" name="group['+conditionId+']['+key+']['+subkey+'][attributeCode]" value="'+subCondition.attributeCode+'"><input type="hidden" name="group['+conditionId+']['+key+']['+subkey+'][tableName]" value="'+subCondition.tableName+'"><input type="hidden" name="group['+conditionId+']['+key+']['+subkey+'][columnName]" value="'+subCondition.columnName+'"></div><div class="attrib_select"><select style="width:115px;" name="group['+conditionId+']['+key+']['+subkey+'][attributeOperators]"><option>IS</option><option>IS NOT</option><option>IS ONE OF</option><option>IS NOT ONE OF</option><option>==</option><option>></option><option><</option><option>>=</option><option><=</option><option><></option></select></div><div class="attrib_input"><input  class="allsearch" type="text" name="group['+conditionId+']['+key+']['+subkey+'][attributeValue]" value="'+subCondition.attributeValue+'"></div><div class="attrib_del"><a class="removeCondition" onclick="removeCondition(this)"><img src="/EmergeServices/css/images/deletebtn.png" alt="Delete"/></a></div></div><div class="attrib_clear"></div><div class="attrib_btns"><select name="group['+conditionId+']['+key+']['+subkey+'][indCondition]" style="width:70px"><option>--</option><option>AND</option><option>OR</option></select></div></div>');
                                            $('.attrib_subgroup_container#'+key+' .rightcon_sets#'+subkey+' option:contains("'+subCondition.indCondition+'")').prop('selected',true);
                                            $('.attrib_subgroup_container#'+key+' .rightcon_sets#'+subkey+' option').each(function(){
                                               if($(this).text() == subCondition.attributeOperators){
                                                    $(this).prop('selected',true);
                                                }
                                            })
                                       }
                                    });
                                    $('.sub_main_container.'+key+' select.subSelect option:contains("'+conditionGroup.indCondition+'")').prop('selected',true);
                                }else{
                                    if(key!=='groupCondition'){
                                        $('.main_container#'+conditionId+' .attrib_container').append('<div class="rightcon_sets" id="'+key+'"><div  class="rightcon_attribs"><div class="attrib_name" style=""><b>'+conditionGroup.attributeCode+'</b><input type="hidden" name="group['+conditionId+']['+key+'][attributeType]" value="'+conditionGroup.attributeType+'"><input type="hidden" name="group['+conditionId+']['+key+'][attributeCode]" value="'+conditionGroup.attributeCode+'"><input type="hidden" name="group['+conditionId+']['+key+'][tableName]" value="'+conditionGroup.tableName+'"><input type="hidden" name="group['+conditionId+']['+key+'][columnName]" value="'+conditionGroup.columnName+'"></div><div class="attrib_select"><select style="width:115px;" name="group['+conditionId+']['+key+'][attributeOperators]"><option>IS</option><option>IS NOT</option><option>IS ONE OF</option><option>IS NOT ONE OF</option><option>==</option><option>></option><option><</option><option>>=</option><option><=</option><option><></option></select></div><div class="attrib_input"><input  class="allsearch" type="text" name="group['+conditionId+']['+key+'][attributeValue]" value="'+conditionGroup.attributeValue+'"></div><div class="attrib_del"><a class="removeCondition" onclick="removeCondition(this)"><img src="/EmergeServices/css/images/deletebtn.png" alt="Delete"/></a></div></div><div class="attrib_clear"></div><div class="attrib_btns"><select name="group['+conditionId+']['+key+'][indCondition]" style="width:70px"><option>--</option><option>AND</option><option>OR</option></select></div></div>');
                                        $('.rightcon_sets#'+key+' option').each(function(){
                                            if($(this).text() == conditionGroup.attributeOperators){
                                                $(this).prop('selected',true);
                                            }
                                        })
                                    }
                                    $('.rightcon_sets#'+key+' option:contains("'+conditionGroup.indCondition+'")').prop('selected',true);
                                }
                            })
                        });
                        dradDropSub();
                        DragDrop();
                        getSelectedContainer();
                        // initialize counter to size of conditions, if it is a new condition, set it to zero
                                    if("${add}"=="0"){
                                        counter = $('div.main_container').length+1;
                                    }else{
                                        counter = 1;
                                    }

                                    if("${add}"=="0"){
                                        ctr = $('div.rightcon_sets').length+1;
                                    }else{
                                        ctr = 1;
                                    }
                    }
                });
            }



            // drag drop conditions
            $('#addConditionGroupButton').click(function(){
            	counter++;
            	$('.right_canvas').append('<div class="main_container" id="'+counter+'"><div class="attrib_container droppable"></div><a id="remove" onclick="removeContainer(this)"><div class="container_closebtn">X</div></a><div class="attrib_clear"></div><div class="container_btns"><select name="group['+counter+'][groupCondition]" style="width:70px"><option>--</option><option>AND</option><option>OR</option></select></div></div>').sortable({
            		items: ".main_container"
            	});
            	DragDrop();
            	getSelectedContainer();
            });

            //add sub group to selected container
            $('#addSubGroupButton').click(function(){
            	ctr++;
            	contId = $('.clickedMainContainer').parent().attr('id');

            	$('.clickedMainContainer').append('<div class="sub_main_container rightcon_sets '+ctr+'"><div class="attrib_subgroup_container droppable" id="'+ctr+'"></div><a id="removeSubGroup" onclick="removeContainer(this)"><div class="container_closebtn">X</div></a><div class="attrib_clear"></div><div class="container_btns"><select id="subSelect" name="group['+contId+']['+ctr+'][indCondition]" style="width:70px"><option>--</option><option>AND</option><option>OR</option></select></div></div>');
                	dradDropSub();
            });

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
                    data: '[{"codeType":"Attribute","subCodeType":"attributeType"}]',
                    complete: function(response){
                        metaString = JSON.parse(response.responseText);
                        metaData = JSON.parse(metaString['id']);

                        $.each(metaData,function(key,value) {
                        //console.log(value.quickCodeDescription);
                               $("#accordion").append("<h3>"+value.quickCodeDescription+"</h3><div class='set1'><input  class='allsearch' type='text' name='search'  onkeyup='FilterCriteria(this)'><div id='attriblist'><ul attrType='"+value.quickCodeValue+"' id='attributeUL'></ul></div></div>");
                        });
                        loadAccordion();
                    }
                });

        });

        function loadAccordion(){
            $( "#accordion" ).accordion({
                heightStyle: "content"
            });

            populateAccordion();
        }

        function populateAccordion(){
                $.ajax({
                    type: "GET",
                    url: "/EmergeServices/org/attributes?isResponseRequired=true",
                    headers: {"Accept":"application/json","Content-Type":"application/json"},
                    username:"admin",
                    password:"password",
                    complete: function(response){
                        attrString = JSON.parse(response.responseText);
                        attrData = JSON.parse(attrString['id']);

                        $.each(attrData,function(key,value) {
                              var attrList = $("#accordion").find("[attrType='"+value.attributeType+"']");
                              attrList.append("<li table='"+value.tableName+"' columnName='"+value.columnName+"' id='"+value.attributeType+"'><span>"+value.attributeName+"</span></li>")
                        });

                        // check if attribute group has at least one attribute
                        $.each($("#accordion").find("ul"),function(key,value){
                            if(($(value).children().size())==0){
                                $(value).append("<i>There are no attributes added under this attribute group. Add new KPIs under this attribute group</i>");
                            }
                        });

                        makeDraggable();
                    }
                });
        }

        function makeDraggable(){
            $("#accordion li").draggable({
                appendTo: "body",
                helper: "clone"
            });
        }

        function DragDrop() {

        	$( ".droppable" ).droppable({
        		activeClass: "ui-state-default",
        		hoverClass: "highlightContainer",
        		accept: ":not(.ui-sortable-helper)",
        		drop: function( event, ui ) {
                    //$( this ).find( ".placeholder" ).remove();
                    containerId = $(this).parent().attr('id');
                    ctr++;
                    $(this).append('<div class="rightcon_sets" id="'+ctr+'"><div  class="rightcon_attribs"><div class="attrib_name" style="'+ui.draggable.attr('style')+'"><b>'+ui.draggable.text()+'</b><input type="hidden" name="group['+containerId+']['+ctr+'][attributeType]" value="'+ui.draggable.attr('id')+'"><input type="hidden" name="group['+containerId+']['+ctr+'][attributeCode]" value="'+ui.draggable.text()+'"><input type="hidden" name="group['+containerId+']['+ctr+'][tableName]" value="'+ui.draggable.attr('table')+'"><input type="hidden" name="group['+containerId+']['+ctr+'][columnName]" value="'+ui.draggable.attr('columnName')+'"></div><div class="attrib_select"><select style="width:115px;" name="group['+containerId+']['+ctr+'][attributeOperators]"><option>IS</option><option>IS NOT</option><option>IS ONE OF</option><option>IS NOT ONE OF</option><option>==</option><option>></option><option><</option><option>>=</option><option><=</option><option><></option></select></div><div class="attrib_input"><input  class="allsearch" type="text" name="group['+containerId+']['+ctr+'][attributeValue]" value=""></div><div class="attrib_del"><a class="removeCondition" onclick="removeCondition(this)"><img src="/EmergeServices/css/images/deletebtn.png" alt="Delete"/></a></div></div><div class="attrib_clear"></div><div class="attrib_btns"><select name="group['+containerId+']['+ctr+'][indCondition]" style="width:70px"><option>--</option><option>AND</option><option>OR</option></select></div></div>	');
                    getSelectedContainer();
        	    }
        	}).sortable({
        		items: ".rightcon_sets"
        	});
        }

        function dradDropSub(){
        	var subctr;
        	if("${add}"=="0"){
                subctr = $('div.rightcon_sets').length;
            }else{
                subctr = 0;
            }

        	$( ".attrib_subgroup_container" ).droppable({
        			greedy: true,
        			activeClass: "ui-state-default",
        			hoverClass: "highlightContainer",
        			accept: ":not(.ui-sortable-helper)",
        			drop: function( event, ui ) {
        				//$( this ).find( ".placeholder" ).remove();
        				containerId = $(this).parent().parent().parent().attr('id');
        				subGroupId = $(this).attr('id');
        				subctr++;
        				$(this).append('<div class="rightcon_sets" id="'+subGroupId+'"><div  class="rightcon_attribs"><div class="attrib_name" style="'+ui.draggable.attr('style')+'"><b>'+ui.draggable.text()+'</b><input type="hidden" name="group['+containerId+']['+subGroupId+']['+subctr+'][attributeType]" value="'+ui.draggable.attr('id')+'"><input type="hidden" name="group['+containerId+']['+subGroupId+']['+subctr+'][attributeCode]" value="'+ui.draggable.text()+'"><input type="hidden" name="group['+containerId+']['+subGroupId+']['+subctr+'][tableName]" value="'+ui.draggable.attr('table')+'"><input type="hidden" name="group['+containerId+']['+subGroupId+']['+subctr+'][columnName]" value="'+ui.draggable.attr('columnName')+'"></div><div class="attrib_select"><select style="width:115px;" name="group['+containerId+']['+subGroupId+']['+subctr+'][attributeOperators]"><option>IS</option><option>IS NOT</option><option>IS ONE OF</option><option>IS NOT ONE OF</option><option>==</option><option>></option><option><</option><option>>=</option><option><=</option><option><></option></select></div><div class="attrib_input"><input  class="allsearch" type="text" name="group['+containerId+']['+subGroupId+']['+subctr+'][attributeValue]" value=""></div><div class="attrib_del"><a class="removeCondition" onclick="removeCondition(this)"><img src="/EmergeServices/css/images/deletebtn.png" alt="Delete"/></a></div></div><div class="attrib_clear"></div><div class="attrib_btns"><select name="group['+containerId+']['+subGroupId+']['+subctr+'][indCondition]" style="width:70px"><option>--</option><option>AND</option><option>OR</option></select></div></div>	');
        				getSelectedContainer();
        			}
        		}).sortable({
        			items: ".rightcon_sets"
        		});
        }

        function getSelectedContainer(){
        	$('.attrib_container').click(function(){
        	    $('.attrib_container').removeClass('clickedMainContainer');
        		$(this).addClass('clickedMainContainer');
        	});
        }

        function testAction(segmentQuery){
            var segmentResultMesg = new Object();

                //ajax call to test if segment conditions are valid
                $.ajax({
                    type: "POST",
                    async : false,
                    url: "/EmergeServices/org/testSegment?isResponseRequired=true&storedProcedureName=testExecuteSegment",
                    headers: {"Accept":"application/json","Content-Type":"application/json"},
                    username:"admin",
                    password:"password",
                    data: '[{"segmentQuery":"'+segmentQuery+'"}]',
                    complete: function(response){
                       resp = JSON.parse(response.responseText);
                       responseCount = JSON.parse(resp.id);
                       $.each(responseCount,function(key,obj){
                           $.each(obj,function(subkey,responseMsg){
                                if(subkey=='count'){
                                   segmentResultMesg.error = false;
                                   segmentResultMesg.message = "No of users in this Segment: "+responseMsg;
                                }else{
                                    segmentResultMesg.error = true;
                                    segmentResultMesg.message = responseMsg;
                                }
                           });
                       });
                    }
                });

             return segmentResultMesg;
        }

        function saveAction(){
            if($('.dataForm').data('bValidator').validate())  {
                var segmentValidationResult = {};
                var requestJSON = $('#editForm').serializeFormJSON();
                var requestJSON = $('#editForm').serializeObject();
                if(requestJSON.routineName==''){
                    requestJSON.conditionSerialized = JSON.stringify(requestJSON.group);
                    //SEGMENT QUERY
                    requestJSON.segmentQuery = generateSegmentQuery(requestJSON.group);
                    delete requestJSON.group;

                    segmentValidationResult = testAction(requestJSON.segmentQuery)
                    console.log(segmentValidationResult);
                }else{
                   segmentValidationResult.error = false;
                   segmentValidationResult.message = "You are using an SQL routine as a segment. Do you wish to continue?";
                }




                if(!segmentValidationResult.error){

                    if(requestJSON.createdDate==''){
                        requestJSON.createdDate = getCurDate();
                    }

                    //requestJSON.modifiedDate = getCurDate();

                    //console.log(JSON.stringify(requestJSON));
                    var requestBody = "["+JSON.stringify(requestJSON)+"]";

                    var segmentConfirmSave = confirm(segmentValidationResult.message + "\n\n Do you wish to proceed?" );
                    if(segmentConfirmSave){
                        //ajax call to save data
                        $.ajax({
                            type: "POST",
                            url: "/EmergeServices/org/segment?isResponseRequired=true",
                            headers: {"Accept":"application/json","Content-Type":"application/json"},
                            username:"admin",
                            password:"password",
                            data: requestBody,
                            complete: function(response){
                               window.location.href = "/EmergeServices/org/viewAllSegments";
                            }
                        });
                    }
                }else{
                    alert(segmentValidationResult.message);
                }

            }
        }

        function getCurDate(){
            var curDate = new Date();
            var newDate = curDate.getFullYear()+"-"+curDate.getMonth()+"-"+curDate.getDate();
            return newDate;
         }

         function deleteAction(){
            var didConfirm = confirm("Are you sure you want to delete?");
            if(didConfirm == true){
                var requestBody = "["+JSON.stringify($('.id_col').serializeFormJSON())+"]";

                //ajax call to delete data
                $.ajax({
                    type: "DELETE",
                    url: "/EmergeServices/org/segment?isResponseRequired=true",
                    headers: {"Accept":"application/json","Content-Type":"application/json"},
                    username:"admin",
                    password:"password",
                    data: requestBody,
                    complete: function(response){
                       window.location.href = "/EmergeServices/org/viewAllSegments";
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
        <span>Customize Customer Segment</span>
        <a onclick="deleteAction()" class="styledButton actionButtons">Delete</a>
        <a onclick="window.location.href='/EmergeServices/org/viewAllSegments';" class="styledButton actionButtons">Back</a>
        <a class="styledButton actionButtons" id="addConditionGroupButton">Add Condition Group</a>
        <a class="styledButton actionButtons" id="addSubGroupButton">Add Sub Condition</a>
        <a onclick="saveAction()" class="styledButton actionButtons">Save</a>
    </div>
    <div class="innerContent">
        <div class="leftcon">
            <div id="accordion">
            </div>
        </div>
        <form id="editForm">
            <table cellspacing="0" class="form-list dataForm" style="border:1px solid #D7D5D6 !important;background-color:#FAFAFA;width:79%;padding-left:20px;float:right">
                <tr class="visibilityNone" style="margin:3px;">
                    <td class="odd"></td>
                	<td class="value"><input type="hidden" name="segmentId" class="id_col"></td>
                </tr>
                <tr>
		            <td class="odd"><label for="segmentName">Segment Name *</label></td>
		            <td class="value"><input type="text" name="segmentName" data-bvalidator="required,alphanum"></td>
	            </tr>
	            <tr>
                    <td class="odd" valign="middle"><label for="segmentDescription">Segment Description *</label></td>
                	<td><textarea name="segmentDescription" data-bvalidator="required,alphanum,maxlength[500]" style="width:60%;height:80px"></textarea></td>
                </tr>
                <tr class="visibilityNone">
           		    <td class="odd" valign="top"></td>
           			<td class="" valign="top">
           			    <input type="hidden" name="createdBy">
                    </td>
           		</tr>
	        </table>
	        <div style="float:left;margin:5px 0px 10px 22%;font-size:1.5em;color:#555555"><span>There are two simple methods to add a customer segment : </span></div>
	        <div style="float:left;margin:5px 0px 10px 22%;font-size:1.2em;color:#E17009"><span>1. Drag-drop attributes and form you custom rule </span></div>
	        <div style="float:left;margin:5px 0px 10px 22%;font-size:0.85em;color:#555555"><i><span>i) Add Condition Groups to the container below, to drag attributes onto it.</span><span style="margin-left:15px">ii) Select a condition group and add Sub groups to have groupings inside a condition group</span></i></div>
            <div class="right_canvas">

            </div>
            <div style="float:left;margin:5px 0px 10px 50%;font-size:1.5em;color:#555555"><span>OR</span></div>
            <div style="float:left;margin:5px 0px 10px 22%;font-size:1.2em;color:#E17009"><span>2. Enter the name of the SQL routine to be run: </span></div>
            <div style="float:left;margin:5px 0px 10px 22%;font-size:0.85em;color:#555555"><i><span>* Note : Make sure the routine returns distinct member Ids</span></i></div>
            <table cellspacing="0" class="form-list dataForm" style="border:1px solid #D7D5D6 !important;background-color:#FAFAFA;width:79%;padding-left:20px;float:right">
                 <tr style="margin:3px;">
                    <td class="odd"><label for="segmentName">SQL Routine Name</label></td>
		            <td class="value"><input type="text" name="routineName" style="width:200px"></td>
                </tr>
            </table>
        </form>
    </div>
</div>
<jsp:include page="footer.jsp"/>
</body>
</html>
