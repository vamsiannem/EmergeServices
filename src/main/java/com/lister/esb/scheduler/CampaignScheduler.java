package com.lister.esb.scheduler;

/**
 * Created by IntelliJ IDEA.
 * User: rajeev_m
 * Date: 2/4/13
 * Time: 6:52 PM
 * To change this template use File | Settings | File Templates.
 */
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.lister.esb.service.UDMService;
import java.util.List;
import java.util.Map;


@Service
public class CampaignScheduler {

    private static Logger logger = LoggerFactory.getLogger(CampaignScheduler.class);

    @Autowired
    private UDMService udmService;

    //@Scheduled(fixedRate = 5000)
    public void process() {
        Map<String , Object> returnObject =null;

        returnObject = udmService.executeProcedure("programReadAll");
        for(String key : returnObject.keySet()) {
            Object value = returnObject.get(key);
            logger.info(key+"="+value);
        }
    }

}
