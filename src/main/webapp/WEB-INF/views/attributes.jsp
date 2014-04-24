<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>e-Merge Attributes</title>
    <link rel="stylesheet" href="<c:url value='/css/jqx.base.css' />" type="text/css"/>
    <link rel="stylesheet" href="<c:url value='/css/jqx.ui-overcast.css' />" type="text/css"/>
    <link rel="stylesheet" href="<c:url value='/css/form.styles.default.css' />" type="text/css"/>
    <script src="<c:url value='/js/jquery-1.9.1.js' />"></script>

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
         var jsonStr = '';

        $(document).ready(function () {
            var data = '';
                    $.ajax({
                        type: "GET",
                    	url: "/EmergeServices/org/attributes?isResponseRequired=true",
                        headers: {"Accept":"application/json","Content-Type":"application/json"},
                        username:"admin",
                        password:"password",
                        complete: function(response){
                    	    jsonString = JSON.parse(response.responseText);
                    	    jsonStr = JSON.parse(jsonString['id']);
                            data = jsonStr;
                            renderGrid(data);
                    	    /*$.each(jsonStr, function(i) {
                                console.log(jsonStr[i]);
                            });*/
                    	}
                     });
         });

                    // render the grid
                    function renderGrid(data){
                        var source =
                        {
                            datatype: "json",
                            datafields: [
                                { name: 'attributeId' },
                                { name: 'attributeName' },
                                { name: 'attributeType'},
                                { name: 'columnName'},
                                { name: 'tableName'},
                                { name: 'activeFlag'},
                                { name: 'createdDate'},
                                { name: 'modifiedDate' },
                                { name: 'createdBy' },
                                { name: 'modifiedBy' },
                                { name: 'timeStamp'}
                            ],
                            localdata: data
                        };

                        var dataAdapter = new $.jqx.dataAdapter(source, {
                            loadComplete: function (data) { },
                            loadError: function (xhr, status, error) { }
                        });
                        $("#jqxgrid").jqxGrid(
                        {
                            width: "90%",
                            source: dataAdapter,
                            pagesize:20,
                            pageable: true,
                            autoheight: true,
                            theme: 'ui-overcast',
                            columns: [
                              { text: 'Attribute ID', datafield: 'attributeId', width: "10%" },
                              { text: 'Attribute Name', datafield: 'attributeName', width: "20%" },
                              { text: 'Attribute Type', datafield: 'attributeType', width: "10%" },
                              { text: 'Column Name', datafield: 'columnName', width: "20%" },
                              { text: 'Table', datafield: 'tableName', width: "20%" },
                              { text: 'Is Active', datafield: 'activeFlag', width: "10%" } ,
                              { text: 'Action', datafield: 'Delete', width: "10%", columntype: 'button', cellsrenderer: function () {
                                                   return "Delete";
                              },buttonclick: function (row) {
                                    var datarow = $("#jqxgrid").jqxGrid('getrowdata', row);
                                    deleteAction(datarow.attributeId);
                              }
                              },
                            ]
                        });

                    }

                    function deleteAction(attrId){
                        var didConfirm = confirm("Are you sure you want to delete?");
                                    if(didConfirm == true){
                                        var requestBody = '[{"attributeId":'+attrId+'}]';

                                        //ajax call to save data
                                        $.ajax({
                                            type: "DELETE",
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
    </script>
</head>
<body>
    <jsp:include page="header.jsp"/>
    <div class="bodyContent">
        <div class="contentHeader">
            <span>KPIs used for Segmentation</span>
            <a onclick="window.location.href='/EmergeServices/org/addAttribute'" class="styledButton actionButtons">Add New Attribute</a>
        </div>
        <div class="innerContent">
            <center><div id="jqxgrid"></div></center>
        </div>
    </div>
    <jsp:include page="footer.jsp"/>
</body>
</html>