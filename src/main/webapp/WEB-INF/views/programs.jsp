<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>e-Merge Programs</title>
    <link rel="stylesheet" href="<c:url value='/css/jqx.base.css' />" type="text/css"/>
    <link rel="stylesheet" href="<c:url value='/css/form.styles.default.css' />" type="text/css"/>
    <link rel="stylesheet" href="<c:url value='/css/jqx.ui-overcast.css' />" type="text/css"/>
    <script src="<c:url value='/js/jquery-1.9.1.js' />"></script>

		<link rel="stylesheet" type="text/css" href="<c:url value='/css/default.css' />" />
		<link rel="stylesheet" type="text/css" href="<c:url value='/css/component.css' />" />
		<link rel="stylesheet" href="<c:url value='/css/form.styles.default.css' />" type="text/css"/>
		<script src="<c:url value='/js/modernizr.custom.js' />"></script>

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
                    	url: "/EmergeServices/org/programs?isResponseRequired=true",
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
                                { name: 'programId' },
                                { name: 'programName' },
                                { name: 'programDescription'},
                                { name: 'createdDate'},
                                { name: 'modifiedDate' },
                                { name: 'createdBy' },
                                { name: 'modifiedBy' }
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
                            pageable: true,
                            pagesize:20,
                            autoheight: true,
                            theme: 'ui-overcast',
                            columns: [
                              { text: 'Program ID', datafield: 'programId', width: "10%" },
                              { text: 'Program Name', datafield: 'programName', width: "20%" },
                              { text: 'Program Description', datafield: 'programDescription', width: "70%" }
                            ]
                        });

                        $("#jqxgrid").bind('rowselect', function (event) {
                            var row = event.args.rowindex;
                            var datarow = $("#jqxgrid").jqxGrid('getrowdata', row);
                            window.location.href = "/EmergeServices/org/editProgram/id/"+datarow.programId;
                        });

                        $("#jqxgrid").jqxGrid({keyboardnavigation: false});
                    }
    </script>
</head>
<body>
    <jsp:include page="header.jsp"/>
    <div class="bodyContent">
        <div class="contentHeader">
            <span>View All Marketing Initiatives</span>
            <a onclick="window.location.href='/EmergeServices/org/addProgram'" class="styledButton actionButtons">Add New Program</a>
        </div>
        <div class="innerContent">
            <center><div id="jqxgrid"></div></center>
        </div>
    </div>
    <jsp:include page="footer.jsp"/>
</body>
</html>