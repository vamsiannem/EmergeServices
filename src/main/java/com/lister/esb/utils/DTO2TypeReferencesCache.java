package com.lister.esb.utils;

import com.lister.esb.dto.*;
import com.lister.esb.model.CartBO;
import org.codehaus.jackson.type.TypeReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class DTO2TypeReferencesCache {

    private Map<Class, TypeReference> _typeReferencesCache;


    @Autowired
    public DTO2TypeReferencesCache(@Value("${esb_typeReferences_cacheSize}") Integer initialCacheSize) {
        _typeReferencesCache = new ConcurrentHashMap<Class, TypeReference>(initialCacheSize);
        createCacheMap();
    }

    private void createCacheMap() {
        _typeReferencesCache.put( CustomerDTO.class, new TypeReference<java.util.List<CustomerDTO>>(){} );
        _typeReferencesCache.put( CampaignDTO.class, new TypeReference<java.util.List<CampaignDTO>>(){} );
        _typeReferencesCache.put( ProgramDTO.class, new TypeReference<java.util.List<ProgramDTO>>(){} );
        _typeReferencesCache.put( AttributeDTO.class, new TypeReference<java.util.List<AttributeDTO>>(){} );
        _typeReferencesCache.put( TemplateDTO.class, new TypeReference<java.util.List<TemplateDTO>>(){} );
        _typeReferencesCache.put( CampaignsegmentDTO.class, new TypeReference<java.util.List<CampaignsegmentDTO>>(){} );
        _typeReferencesCache.put( SegmentDTO.class, new TypeReference<java.util.List<SegmentDTO>>(){} );
		_typeReferencesCache.put( PurchaseDTO.class, new TypeReference<java.util.List<PurchaseDTO>>(){} );
        _typeReferencesCache.put( TemplatedetailDTO.class, new TypeReference<java.util.List<TemplatedetailDTO>>(){} );
        _typeReferencesCache.put( SkuDTO.class, new TypeReference<java.util.List<SkuDTO>>(){} );
        _typeReferencesCache.put( CategoryDTO.class, new TypeReference<java.util.List<CategoryDTO>>(){} );
        _typeReferencesCache.put( CartDTO.class, new TypeReference<java.util.List<CartDTO>>(){} );
        _typeReferencesCache.put( BrowseDTO.class, new TypeReference<java.util.List<BrowseDTO>>(){} );
        _typeReferencesCache.put( EventDTO.class, new TypeReference<java.util.List<EventDTO>>(){} );
    }

    public TypeReference getTypeRef(Class _class){
        return _typeReferencesCache.get(_class);
    }
}
