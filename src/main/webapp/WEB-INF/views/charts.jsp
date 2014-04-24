<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>e-Merge User Segments</title>
    <link rel="stylesheet" href="<c:url value='/css/jqx.base.css' />" type="text/css"/>
    <link rel="stylesheet" href="<c:url value='/css/jqx.ui-overcast.css' />" type="text/css"/>
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
                    	url: "/EmergeServices/org/segment?isResponseRequired=true",
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
                                { name: 'reportId' },
                                { name: 'reportName' },
                                { name: 'reportDescription'},
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
                            pageable: true,
                            pagesize:20,
                            autoheight: true,
                            theme: 'ui-overcast',
                            columns: [
                              { text: 'Segment ID', datafield: 'segmentId', width: "10%" },
                              { text: 'Segment Name', datafield: 'segmentName', width: "20%" },
                              { text: 'Segment Description', datafield: 'segmentDescription', width: "70%" }
                            ]
                        });

                        $("#jqxgrid").bind('rowselect', function (event) {
                            var row = event.args.rowindex;
                            var datarow = $("#jqxgrid").jqxGrid('getrowdata', row);
                            window.location.href="/EmergeServices/org/editSegment/id/"+datarow.segmentId;
                        });

                        $("#jqxgrid").jqxGrid({keyboardnavigation: false});
                    }
    </script>
</head>
<body>
    <jsp:include page="header.jsp"/>
    <div class="bodyContent">
        <div class="contentHeader">
            <span>View All Charts</span>
        </div>
        <div class="innerContent">
            <center><div id="jqxgrid"></div></center>
        </div>
    </div>
    <jsp:include page="footer.jsp"/>
</body>
</html>