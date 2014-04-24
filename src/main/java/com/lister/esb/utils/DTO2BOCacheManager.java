package com.lister.esb.utils;

import com.lister.esb.dto.*;
import com.lister.esb.model.*;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class DTO2BOCacheManager {

    private Map<Class<? extends BaseDTO>, Class<? extends BaseBO>>
            dto2BOMapping ;

    public DTO2BOCacheManager() {
        dto2BOMapping = new HashMap<Class<? extends BaseDTO>, Class<? extends BaseBO>>(4);
        loadBOMap();
    }

    public Class<? extends BaseBO> getBOClass(Class dtoClass){
        return dto2BOMapping.get(dtoClass);
    }

    public Class<? extends BaseDTO> getDTOClass(Class boClass){
        for(Map.Entry<Class<? extends BaseDTO>, Class<? extends BaseBO>> entry: dto2BOMapping.entrySet() ){
            if(entry.getValue().equals(boClass)) {
                return entry.getKey();
            }
        }
        return null;
    }

    private void loadBOMap() {
        dto2BOMapping.put(CustomerDTO.class, CustomerBO.class);
        dto2BOMapping.put(CampaignDTO.class, CampaignBO.class);
        dto2BOMapping.put(ProgramDTO.class, ProgramBO.class);
        dto2BOMapping.put(AttributeDTO.class, AttributeBO.class);
        dto2BOMapping.put(TemplateDTO.class, TemplateBO.class);
        dto2BOMapping.put(CampaignsegmentDTO.class, CampaignsegmentBO.class);
        dto2BOMapping.put(SegmentDTO.class, SegmentBO.class);
		dto2BOMapping.put(PurchaseDTO.class, PurchaseBO.class);
        dto2BOMapping.put(TemplatedetailDTO.class, TemplatedetailBO.class);
        dto2BOMapping.put(SkuDTO.class, SkuBO.class);
        dto2BOMapping.put(CategoryDTO.class, CategoryBO.class);
        dto2BOMapping.put(CartDTO.class, CartBO.class);
        dto2BOMapping.put(BrowseDTO.class, BrowseBO.class);
        dto2BOMapping.put(EventDTO.class, EventBO.class);
    }
}
