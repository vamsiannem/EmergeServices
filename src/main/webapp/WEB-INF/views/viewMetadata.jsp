<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>e-Merge Programs</title>
    <link rel="stylesheet" href="<c:url value='/css/jqx.base.css' />" type="text/css"/>
    <link rel="stylesheet" href="<c:url value='/css/form.styles.default.css' />" type="text/css"/>
    <link rel="stylesheet" href="<c:url value='/css/jqx.ui-redmond.css' />" type="text/css"/>
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
                    	url: "/EmergeServices/org/metadata?isResponseRequired=true&storedProcedureName=get_all_metadata_details",
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
                                { name: 'quickCodeType' },
                                { name: 'quickCodeSubType' },
                                { name: 'quickCodeValue'},
                                { name: 'quickCodeDescription'}
                            ],
                            localdata: data
                        };

                        var dataAdapter = new $.jqx.dataAdapter(source, {
                            loadComplete: function (data) { },
                            loadError: function (xhr, status, error) { }
                        });
                        $("#jqxgrid").jqxGrid(
                        {
                            width: "70%",
                            source: dataAdapter,
                            pageable: true,
                            autoheight: true,
                            theme: 'ui-redmond',
                            columns: [
                              { text: 'Code Type', datafield: 'quickCodeType', width: "20%" },
                              { text: 'Sub Type', datafield: 'quickCodeSubType', width: "30%" },
                              { text: 'Value', datafield: 'quickCodeValue', width: "20%" },
                              { text: 'Description', datafield: 'quickCodeDescription', width: "30%" }

                            ]
                        });

                        $("#jqxgrid").bind('rowselect', function (event) {
                            var row = event.args.rowindex;
                            var datarow = $("#jqxgrid").jqxGrid('getrowdata', row);
                            console.log(datarow.quickCodeType);
                        });

                        $("#jqxgrid").jqxGrid({keyboardnavigation: false});
                    }
    </script>
</head>
<body>
    <!--<a onclick="window.location.href='/EmergeServices/org/addProgram'" class="styledButton">Add New Program</a>-->
    <div id="jqxgrid" style="margin-top:2%;">
    </div>
</body>
</html>