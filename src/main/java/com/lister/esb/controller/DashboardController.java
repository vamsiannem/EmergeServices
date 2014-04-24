package com.lister.esb.controller;

import com.lister.esb.dto.ProgramDTO;
import com.lister.esb.enums.ActionType;
import com.lister.esb.enums.SourceSystem;
import com.lister.esb.exceptions.XmlException;
import com.lister.esb.model.DataFormat;
import com.lister.esb.model.ServiceRequest;
import com.lister.esb.processors.IRequestDelegate;
import com.lister.esb.utils.ConversionUtils;
import com.lister.esb.utils.ESBConstants;
import com.lister.esb.utils.ServiceRequestFactoryBean;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * User: rajeev
 * Date: 14/5/13
 * Time: 11:25 AM
 * To change this template use File | Settings | File Templates.
 */

@Controller
public class DashboardController {

    private static Logger logger = LoggerFactory.getLogger(DashboardController.class);

    @Autowired
    private ConversionUtils conversionUtils;

    @Autowired
    private JmsTemplate jmsTemplate;

    @Autowired
    @Qualifier("serviceRequestFactoryBean")
    private ServiceRequestFactoryBean serviceRequestFactoryBean;

    @Autowired
    @Qualifier("simpleRequestDelegator")
    private IRequestDelegate requestDelegator;

    @RequestMapping(method=RequestMethod.GET, value="/home")
    public ModelAndView dashboard() {
        return new ModelAndView("home");
    }

    @RequestMapping(method=RequestMethod.GET, value="/viewAllPrograms")
    public ModelAndView viewAllPrograms() {
        return new ModelAndView("programs");
    }

    @RequestMapping(method=RequestMethod.GET, value="/viewAllAttributes")
    public ModelAndView viewAllAttributes() {
        return new ModelAndView("attributes");
    }

    @RequestMapping(method=RequestMethod.GET, value="/viewAllTemplates")
    public ModelAndView viewAllTemplates() {
        return new ModelAndView("templates");
    }

    @RequestMapping(method=RequestMethod.GET, value="/viewAllSegments")
    public ModelAndView viewAllSegments() {
        return new ModelAndView("segments");
    }

    @RequestMapping(method=RequestMethod.GET, value="/viewAllCharts")
    public ModelAndView viewAllCharts() {
        return new ModelAndView("charts");
    }

    @RequestMapping(method=RequestMethod.GET, value="/editProgram/id/{programId}")
    public ModelAndView editProgram(@PathVariable("programId") String programId) {
        ModelAndView mav = new ModelAndView("editProgram");
        mav.addObject("programId",programId);
        mav.addObject("add",0);
        return mav;
    }

    @RequestMapping(method=RequestMethod.GET, value="/addProgram")
    public ModelAndView addProgram() {
        return new ModelAndView("editProgram","add",1);
    }

    @RequestMapping(method=RequestMethod.GET, value="/editCampaign/id/{campaignId}")
    public ModelAndView editCampaign(@PathVariable("campaignId") String campaignId) {
        ModelAndView mav = new ModelAndView("editCampaign");
        mav.addObject("campaignId",campaignId);
        mav.addObject("add",0);
        return mav;
    }

    @RequestMapping(method=RequestMethod.GET, value="/addCampaign/programId/{programId}")
    public ModelAndView addCampaign(@PathVariable("programId") String programId) {
        ModelAndView mav = new ModelAndView("editCampaign");
        mav.addObject("programId",programId);
        mav.addObject("add",1);
        return mav;
    }

    @RequestMapping(method=RequestMethod.GET, value="/addAttribute")
    public ModelAndView addAttribute() {
        ModelAndView mav = new ModelAndView("addAttribute");
        mav.addObject("add",0);
        return mav;
    }

    @RequestMapping(method=RequestMethod.GET, value="/editTemplate/id/{templateId}")
    public ModelAndView editTemplate(@PathVariable("templateId") String templateId) {
        ModelAndView mav = new ModelAndView("editTemplate");
        mav.addObject("templateId",templateId);
        mav.addObject("add",0);
        return mav;
    }

    @RequestMapping(method=RequestMethod.GET, value="/addTemplate")
    public ModelAndView addTemplate() {
        return new ModelAndView("editTemplate","add",1);
    }

    @RequestMapping(method=RequestMethod.GET, value="/editSegment/id/{segmentId}")
    public ModelAndView editSegment(@PathVariable("segmentId") String segmentId) {
        ModelAndView mav = new ModelAndView("editSegment");
        mav.addObject("segmentId",segmentId);
        mav.addObject("add",0);
        return mav;
    }

    @RequestMapping(method=RequestMethod.GET, value="/addSegment")
    public ModelAndView addSegment() {
        return new ModelAndView("editSegment","add",1);
    }

    @RequestMapping(method=RequestMethod.GET, value="/loginAction")
    public ModelAndView loginAction() {
        return new ModelAndView("login");
    }


}
