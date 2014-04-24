package com.lister.esb.dto;

import java.util.Date;
import javax.persistence.GeneratedValue;

/**
 * Created with IntelliJ IDEA.
 * User: rajeev
 * Date: 12/6/13
 * Time: 2:52 PM
 * To change this template use File | Settings | File Templates.
 */
public class TestsegmentDTO extends BaseDTO{

    private String segmentQuery;

    public String getSegmentQuery() {
        return segmentQuery;
    }

    public void setSegmentQuery(String segmentQuery) {
        this.segmentQuery = segmentQuery;
    }
}
