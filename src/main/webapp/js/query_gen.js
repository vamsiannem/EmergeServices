/*
* get assoc array size
*/
function getSize(obj) {
    var size = 0, key;
    for (key in obj) {
        if (obj.hasOwnProperty(key)) size++;
    }
    return size;
};


/*
* get first key in associative array
*/
function getFirstKey( data ) {
    for (elem in data )
    return elem;
}


/*
*  get Attribute Query Map
*/
function getAttributeQueryMap(){
    var attrQueryMap = new Array();

    attrQueryMap['IS'] = 'LIKE';
    attrQueryMap['IS NOT'] = 'NOT LIKE';
    attrQueryMap['IS ONE OF'] = 'IN';
    attrQueryMap['IS NOT ONE OF'] = 'NOT IN';
    attrQueryMap['>'] = '>';
    attrQueryMap['<'] = '<';
    attrQueryMap['>='] = '>=';
    attrQueryMap['<='] = '<=';
    attrQueryMap['=='] = '=';
    attrQueryMap['<>'] = '!=';

    return attrQueryMap;
}


/*
* Generate a random String
*/
function generateRandomString() {
    var length = 3;
    var chars = 'abcdefghijklmnopqrstuvwxyz';
    var result = '';
    for (var i = length; i > 0; --i) result += chars[Math.round(Math.random() * (chars.length - 1))];
    return result;
}


/*
* Generates a query given the segment array
*/
function generateSegmentQuery(conditionGroups){
    return generateQuery(conditionGroups);
}


/*
* checks if the condition group has any subgroups and diverts the call
*/
function generateQuery(conditionGroups){
    var queryList = new Array();
    console.log(conditionGroups);
    /*start $.each loop*/
    $.each(conditionGroups,function(key,conditionGroup) {

        var multi = false;
        $.each(conditionGroup,function(key,individualConditions) {
            //check if multi-dimensional
            if(individualConditions.attributeCode==undefined && key!=='groupCondition'){
                multi = true;
                return false;
            }else{
                return true;
            }
        });

        if(multi){
            queryList.push(generateMultiDimensionalSegmentQuery(conditionGroup));
        }else{
            var temp = new Array();
            temp['query'] = generateUniDimensionalSegmentQuery(conditionGroup);
            temp['condition'] = conditionGroup.groupCondition;
            delete conditionGroup.groupCondition;
            queryList.push(temp);
        }
    });
    /*end $.each loop*/


    return generateFinalQuery(queryList);
}


/*
* generate query for uni-dimensional segment
*/
function generateUniDimensionalSegmentQuery(conditionGroup){
     var listOfTables = new Array();
     var conditionsMap  = getAttributeQueryMap();

    /*start $.each loop*/
     $.each(conditionGroup,function(key,individualConditions) {
        if(individualConditions.attributeCode!=undefined && key!='groupCondition'){
            if(listOfTables.hasOwnProperty(individualConditions.tableName)){
                return true;
            }else{
                listOfTables[individualConditions.tableName] = generateRandomString();
            }
        }
     });
     /*end $.each loop*/

    return formSqlQueryString(listOfTables,conditionsMap,conditionGroup);

}


/*
* generate query for multi-dimensional segment
*/
function generateMultiDimensionalSegmentQuery(conditionGroup){
    var listOfTables = new Array();
    var conditionsMap  = getAttributeQueryMap();
    var subConditions = new Array();
    var otherUniConditions = new Array();
    var multiqueryList = new Array();
    var multiAugmentQuery = new Array();
    var groupCondition ="";

    /*Separating subconditions*/
    $.each(conditionGroup,function(key,individualCondition) {
        if(individualCondition.attributeCode==undefined && key!='groupCondition'){
            subConditions.push(individualCondition);
        }else if(individualCondition.attributeCode!=undefined && key!='groupCondition'){
            otherUniConditions.push(individualCondition);
        }else{
           groupCondition = individualCondition;
        }
    });

    if(getSize(otherUniConditions)>0){
        var joinUniCondition = otherUniConditions[getSize(otherUniConditions)-1]['indCondition'];
        otherUniConditions[getSize(otherUniConditions)-1]['indCondition'] = '--';
        otherUniConditions.push(joinUniCondition);


        /*Executing oridinary conditions*/
        var single = new Array();
        single['query'] = generateUniDimensionalSegmentQuery(otherUniConditions);
        single['condition'] = otherUniConditions.pop();
        if(single['condition']!='--'){
            multiqueryList.push(single);
        }
    }

    /*Executing SUB conditions*/
    for(var subCondition in subConditions){
        var temp = new Array();
        temp['query'] = generateUniDimensionalSegmentQuery(subConditions[subCondition]);
        temp['condition'] = subConditions[subCondition].indCondition;
        multiqueryList.push(temp);
    }
    if(getSize(otherUniConditions)>0){
        if(single['condition']=='--'){
            multiqueryList.push(single);
        }
    }

    //console.log(multiqueryList);
    multiAugmentQuery['query'] = generateAugmentedQuery(multiqueryList);
    multiAugmentQuery['condition'] = groupCondition;

    return multiAugmentQuery;
}


/*
* Generate the augmented query given a list of individual queries and join conditions
*/
function generateAugmentedQuery(queryList){
    augmentedQueryString = "";

    // queryList[query]
    for(var query in queryList){
        if(queryList[query]['condition'] == 'OR'){
            if(augmentedQueryString == ""){
                augmentedQueryString += queryList[query]['query'] + " UNION ";
            }else{
                augmentedQueryString += "(" + queryList[query]['query'] + ") UNION ";
            }
        }else if(queryList[query]['condition'] == 'AND'){
            if(augmentedQueryString == ""){
                augmentedQueryString = "select member_id from ( " + augmentedQueryString +  queryList[query]['query'] + ")"+generateRandomString()+" WHERE `member_id` IN ";
            }else{
               augmentedQueryString = "select member_id from ( " + augmentedQueryString + "(" + queryList[query]['query'] + ")" + ")"+generateRandomString()+" WHERE `member_id` IN ";
            }
        }else{
            augmentedQueryString += "(" + queryList[query]['query'] +")";
        }
    }

//    console.log(augmentedQueryString);
    return augmentedQueryString;
}


/*
* forms the SQL query string for each condition group or subgroup,
* as the case may be
*/
function formSqlQueryString(listOfTables,conditionMap,conditionGroup){
    var ctr = 0;
    var tableNameString = '';
    var conditionString = '';
    var joinCondition = ' AND';
    var tempJoin = conditionGroup.groupCondition;
    var groupCombineOperator = '';
    var tableList = listOfTables;
    var firstAlias =  tableList[getFirstKey(tableList)];

    /*generate table names and join Condition*/
    for (tableName in listOfTables){
        tableAlias = listOfTables[tableName];
        tableNameString += tableName + ' AS `' + tableAlias + '`';

        if(getSize(listOfTables)>1){
            tableNameString +=', ';
        	if(ctr==0){
        	    tempJoin = " `" + tableAlias + "`.member_id";
        		joinCondition += " `" + tableAlias + "`.member_id = ";
        	}else if(ctr%2==0){
        		joinCondition += " AND `" + tableAlias + "`.member_id = ";
        	}else{
        		joinCondition += " `" + tableAlias + "`.member_id";
        	}
        	ctr++;
        }else{
            tableNameString += '  ';
        }
    }
    /*end $.each loop*/

    if(getSize(listOfTables)>2){
        joinCondition += tempJoin;
    }
    if(joinCondition == ' AND'){
     	joinCondition = '';
    }

    /*prepare data for query*/
    $.each(conditionGroup,function(key,individualCondition){
        if(individualCondition.attributeCode!=undefined && key!='groupCondition'){
            switch(conditionMap[individualCondition.attributeOperators])
            {
                case 'LIKE':
                individualCondition.attributeValue = "'%" + individualCondition.attributeValue + "%'";
                break;

                case 'NOT LIKE':
            	individualCondition.attributeValue = "'%" + individualCondition.attributeValue + "%'";
            	break;

                case 'IN':
                var tempVar = individualCondition.attributeValue.split(",");
                var tempStr = new Array();
                $.each(tempVar,function(key,value){
                    tempStr.push("'"+value+"'");
                });
                individualCondition.attributeValue = "(" + tempStr.join(',') + ")";
                break;

                case 'NOT IN':
                var tempVar = individualCondition.attributeValue.split(",");
                var tempStr = new Array();
                $.each(tempVar,function(key,value){
                    tempStr.push("'"+value+"'");
                });
                individualCondition.attributeValue = "(" + tempStr.join(',') + ")";
                break;
            }
        }
    });
    /*end $.each loop*/


    /*generate condition string*/
    $.each(conditionGroup,function(key,condition){
        if(condition.attributeCode!=undefined && key!='groupCondition'){
            conditionString += "`" + listOfTables[condition.tableName] + "`.`" + condition.columnName + "` " + conditionMap[condition.attributeOperators] + " " + condition.attributeValue + " ";
            if((condition.indCondition) != '--'){
                conditionString += condition.indCondition + " ";
            }
        }
    });
    /*end $.each loop*/

    /* FORM THE QUERY */
    //substr($tableNameString,0,(strlen($tableNameString)-2))
    var sqlQuery = "SELECT DISTINCT `" + firstAlias + "`.`member_id` FROM " +tableNameString.substr(0,tableNameString.length-2) + " WHERE " + conditionString + " " + joinCondition;

    return sqlQuery;
}


/*
* Generate the final query for the SEGMENT given the augmented queries
*/
function generateFinalQuery(augmentedQueryList){

    //console.log(augmentedQueryList);

    var ctr = 0;
    startAndCondition = "SELECT member_id from ( ";
    andWhereCondition = "WHERE member_id IN ";
    augmentedQueryString = "";

    /* start for loop*/
    for(var augmentedQuery in augmentedQueryList){
        if(ctr==0){
            augmentedQueryString += "select member_id from (" + augmentedQueryList[augmentedQuery]['query'] + ") " + generateRandomString() + " ";
        }else{
            augmentedQueryString += "(select member_id from (" + augmentedQueryList[augmentedQuery]['query'] + ") " + generateRandomString() +") ";
        }

		if(augmentedQueryList[augmentedQuery]['condition'] == 'OR'){
			augmentedQueryString += " UNION ";
		}else if(augmentedQueryList[augmentedQuery]['condition'] == 'AND'){
			tempString = startAndCondition + augmentedQueryString + ") " + generateRandomString() + " " + andWhereCondition;
			augmentedQueryString=tempString;
		}

		ctr++;
    }
    /* end for loop*/

    return augmentedQueryString;

}


