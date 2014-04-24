package com.lister.esb.repositories;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;
import java.util.List;
import java.util.Map;

@Repository("simpleJdbcRepository")
public class SimpleJdbcRepository {

    @Autowired
    private NamedParameterJdbcTemplate namedParamJdbcTemplate;

    @Autowired
    DriverManagerDataSource dataSource;

    private Logger logger = Logger.getLogger(SimpleJdbcRepository.class);


    public int[] executeBatch(String executeQuery, List<Map<String, Object>> parameterMap) {
        //simpleJdbcCall.withProcedureName(procedureName).execute(parameterMap);

        Map<String,Object>[] inParameterArrayMap = parameterMap.toArray(new Map[parameterMap.size()]);
        return namedParamJdbcTemplate.batchUpdate(executeQuery, inParameterArrayMap);
    }

    @Transactional
    public Map<String, Object> execute(String executeQuery, Map<String, Object> parameterMap) {
        SimpleJdbcCall simpleJdbcCall = new SimpleJdbcCall(dataSource);
        logger.info(executeQuery);
        logger.info(parameterMap);
        SqlParameterSource sqlParameterSource = new MapSqlParameterSource(parameterMap);
        //parameterSource.
        return simpleJdbcCall.withProcedureName(executeQuery).execute(sqlParameterSource);
    }



}
