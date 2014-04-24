/**
 *
 */
package com.lister.esb.service;

import com.lister.esb.model.*;
import com.lister.esb.repositories.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * UDMService implementation which interacts with custom repositories.
 *
 * @author sudharsan_s
 *
 */
@Service("udmService")
public class UDMServiceImpl implements UDMService {

    private static Logger logger = LoggerFactory.getLogger(UDMServiceImpl.class);

    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");

    @Autowired(required = false)
    private CustomerRepository customerRepository;

    @Autowired(required = false)
    private CampaignRepository campaignRepository;

    @Autowired(required = false)
    private ProgramRepository programRepository;

    @Autowired(required = false)
    private AttributeRepository attributeRepository;

    @Autowired(required = false)
    private CartRepository cartRepository;

    @Autowired(required = false)
    private BrowseRepository browseRepository;

    @Autowired(required = false)
    private TemplateRepository templateRepository;

    @Autowired(required = false)
    private CampaignsegmentRepository campaignsegmentRepository;

    @Autowired(required = false)
    private SegmentRepository segmentRepository;
	
	@Autowired(required = false)
    private PurchaseRepository purchaseRepository;

    @Autowired
    private SimpleJdbcRepository simpleJdbcRepository;

    @Autowired(required = false)
    private TemplatedetailRepository templatedetailRepository;

    @Autowired(required = false)
    private SkuRepository skuRepository;

    @Autowired(required = false)
    private CategoryRepository categoryRepository;

    @Autowired(required = false)
    private EventRepository eventRepository;

    @Autowired
    private Properties queries;

    /**
     * Perform save operation.
     *
     * @param baseBOs list of entities
     * @param module module name
     * @return baseBOs list of persisted entities
     */
    @Override
    public Collection<? extends BaseBO> save(Collection<? extends BaseBO> baseBOs, Class module) {
        logger.debug("Perform save operation on entities");
        logger.debug("Start: save:" + sdf.format(new Date()) );
        if(module != null && module.equals(CustomerBO.class)){
            baseBOs = createCustomer((Collection<CustomerBO>) baseBOs);
        }else if(module != null && module.equals(CampaignBO.class)){
            baseBOs = createCampaign((Collection<CampaignBO>)baseBOs);
        }else if(module != null && module.equals(ProgramBO.class)){
            baseBOs = createProgram((Collection<ProgramBO>)baseBOs);
        }else if(module != null && module.equals(AttributeBO.class)){
            baseBOs = createAttribute((Collection<AttributeBO>)baseBOs);
        }else if(module != null && module.equals(TemplateBO.class)){
            baseBOs = createTemplate((Collection<TemplateBO>)baseBOs);
        }else if(module != null && module.equals(CampaignsegmentBO.class)){
            baseBOs = createCampaignsegment((Collection<CampaignsegmentBO>)baseBOs);
        }else if(module != null && module.equals(SegmentBO.class)){
            baseBOs = createSegment((Collection<SegmentBO>)baseBOs);
        }else if(module != null && module.equals(PurchaseBO.class)){
            baseBOs = createPurchase((Collection<PurchaseBO>)baseBOs);
        }else if(module != null && module.equals(TemplatedetailBO.class)){
            baseBOs = createTemplatedetail((Collection<TemplatedetailBO>)baseBOs);
        }else if(module != null && module.equals(SkuBO.class)){
            baseBOs = createSku((Collection<SkuBO>)baseBOs);
        }else if(module != null && module.equals(CategoryBO.class)){
            baseBOs = createCategory((Collection<CategoryBO>)baseBOs);
        }else if(module != null && module.equals(CartBO.class)){
            baseBOs = createCart((Collection<CartBO>)baseBOs);
        }else if(module != null && module.equals(BrowseBO.class)){
            baseBOs = createBrowse((Collection<BrowseBO>)baseBOs);
        }else if(module != null && module.equals(EventBO.class)){
            baseBOs = createEvent((Collection<EventBO>)baseBOs);
        }

        logger.debug("End: save:" + sdf.format(new Date()) );
        return baseBOs;

    }

    /**
     * Perform update operation.
     * @param baseBOs list of entities
     * @param module module name
     * @return
     */
    @Override
    public Collection<? extends BaseBO> update(Collection<? extends BaseBO> baseBOs, Class module) {
        logger.debug("Perform update operation on entities");
        if(module != null && module.equals(CustomerBO.class)){
            baseBOs = updateCustomers((Collection<CustomerBO>) baseBOs);
        }else if(module != null && module.equals(CampaignBO.class)){
            baseBOs = updateCampaigns((Collection<CampaignBO>) baseBOs);
        }else if(module != null && module.equals(ProgramBO.class)){
            baseBOs = updatePrograms((Collection<ProgramBO>) baseBOs);
        }else if(module != null && module.equals(AttributeBO.class)){
            baseBOs = updateAttributes((Collection<AttributeBO>) baseBOs);
        }else if(module != null && module.equals(TemplateBO.class)){
            baseBOs = updateTemplates((Collection<TemplateBO>) baseBOs);
        }else if(module != null && module.equals(CampaignsegmentBO.class)){
            baseBOs = updateCampaignsegments((Collection<CampaignsegmentBO>) baseBOs);
        }else if(module != null && module.equals(SegmentBO.class)){
            baseBOs = updateSegment((Collection<SegmentBO>) baseBOs);
        }else if(module != null && module.equals(PurchaseBO.class)){
            baseBOs = updatePurchases((Collection<PurchaseBO>) baseBOs);
        }else if(module != null && module.equals(TemplatedetailBO.class)){
            baseBOs = updateTemplatedetail((Collection<TemplatedetailBO>) baseBOs);
        }else if(module != null && module.equals(SkuBO.class)){
            baseBOs = updateSku((Collection<SkuBO>) baseBOs);
        }else if(module != null && module.equals(CategoryBO.class)){
            baseBOs = updateCategory((Collection<CategoryBO>) baseBOs);
        }else if(module != null && module.equals(EventBO.class)){
            baseBOs = updateEvent((Collection<EventBO>) baseBOs);
        }

        return baseBOs;
    }

     /**
     * Save customer entities.
     * @param customerBOs list of customer entities
     * @return customerBOs list of persisted customer entities
     */
    private Collection<CustomerBO> createCustomer(Collection<CustomerBO> customerBOs){
        logger.debug("Save customer entities");
        logger.debug("Start: createCustomer:" + sdf.format(new Date()) );
        //Save customer entities
        customerBOs = customerRepository.save(customerBOs);
        logger.debug("Start: createCustomer:" + sdf.format(new Date()) );
        return customerBOs;
    }


     /**
     * Save event entities.
     * @param eventBOs list of event entities
     * @return eventBOs list of persisted event entities
     */
    private Collection<EventBO> createEvent(Collection<EventBO> eventBOs){
        logger.debug("Save event entities");
        logger.debug("Start: createEvent:" + sdf.format(new Date()) );
        //Save customer entities
        eventBOs = eventRepository.save(eventBOs);
        logger.debug("Start: createEvent:" + sdf.format(new Date()) );
        return eventBOs;
    }


    /**
     * Save campaign entities.
     * @param campaignBOs list of campaign entities
     * @return campaignBOs list of persisted campaign entities
     */
    private Collection<CampaignBO> createCampaign(Collection<CampaignBO> campaignBOs){
        logger.debug("Save campaign entities");
        //Save campaign entities
        campaignBOs = campaignRepository.save(campaignBOs);

        return campaignBOs;
    }


     private Collection<ProgramBO> createProgram(Collection<ProgramBO> programBOs){
        logger.debug("Save program entities");
        //Save campaign entities
        programBOs = programRepository.save(programBOs);

        return programBOs;
    }

    private Collection<AttributeBO> createAttribute(Collection<AttributeBO> attributeBOs){
        logger.debug("Save attribute entities");
        //Save campaign entities
        attributeBOs = attributeRepository.save(attributeBOs);

        return attributeBOs;
    }

    private Collection<CartBO> createCart(Collection<CartBO> cartBOs){
        logger.debug("Save cart entities");
        //Save campaign entities
        cartBOs = cartRepository.save(cartBOs);

        return cartBOs;
    }

    private Collection<BrowseBO> createBrowse(Collection<BrowseBO> browseBOs){
        logger.debug("Save Browse entities");
        //Save campaign entities
        browseBOs = browseRepository.save(browseBOs);
        return browseBOs;
    }

    private Collection<TemplateBO> createTemplate(Collection<TemplateBO> templateBOs){
        logger.debug("Save template entities");
        //Save campaign entities
        templateBOs = templateRepository.save(templateBOs);

        return templateBOs;
    }

    private Collection<TemplatedetailBO> createTemplatedetail(Collection<TemplatedetailBO> templatedetailBOs){
        logger.debug("Save template detail entities");
        //Save template detail entities
        templatedetailBOs = templatedetailRepository.save(templatedetailBOs);

        return templatedetailBOs;
    }

    private Collection<SkuBO> createSku(Collection<SkuBO> skuBOs){
        logger.debug("Save sku detail entities");
        //Save template detail entities
        skuBOs = skuRepository.save(skuBOs);

        return skuBOs;
    }

    private Collection<CategoryBO> createCategory(Collection<CategoryBO> categoryBOs){
        logger.debug("Save category detail entities");
        //Save template detail entities
        categoryBOs = categoryRepository.save(categoryBOs);

        return categoryBOs;
    }


    private Collection<CampaignsegmentBO> createCampaignsegment(Collection<CampaignsegmentBO> campaignsegmentBOs){
        logger.debug("Save campaign segment mapping entities");
        //Save campaign entities
        campaignsegmentBOs = campaignsegmentRepository.save(campaignsegmentBOs);

        return campaignsegmentBOs;
    }

     private Collection<SegmentBO> createSegment(Collection<SegmentBO> segmentBOs){
        logger.debug("Save segment entities");
        //Save campaign entities
        segmentBOs = segmentRepository.save(segmentBOs);

        return segmentBOs;
    }
	
	
	  /**
     * Save purchase entities.
     * @param purchaseBOs list of purchase entities
     * @return purchaseBOs list of persisted purchase entities
     */
    private Collection<PurchaseBO> createPurchase(Collection<PurchaseBO> purchaseBOs){
        logger.debug("Save purchase entities");
        logger.debug("Start: createPurchase:" + sdf.format(new Date()) );
        //Save purchase entities
        purchaseBOs = purchaseRepository.save(purchaseBOs);
        logger.debug("Start: createPurchase:" + sdf.format(new Date()) );
        return purchaseBOs;
    }
	
	
    /**
     * Update campaign entities.
     * @param campaignBOs list of campaign entities
     * @return campaignBOs list of updated campaign entities
     */
    private Collection<? extends BaseBO> updateCampaigns(Collection<CampaignBO> campaignBOs) {
        logger.debug("Update campaign entities");
        //Update campaign entities
        campaignBOs = campaignRepository.save(campaignBOs);

        return campaignBOs;
    }

    /**
     * Update customer entities.
     * @param customerBOs list of campaign entities
     * @return customerBOs list of updated campaign entities
     */
    private Collection<? extends BaseBO> updateCustomers(Collection<CustomerBO> customerBOs) {
        logger.debug("Update customer entities");
        //Update customer entities
        customerBOs = customerRepository.save(customerBOs);

        return customerBOs;
    }


    /**
     * Update event entities.
     * @param eventBOs list of event entities
     * @return eventBOs list of updated event entities
     */
    private Collection<? extends BaseBO> updateEvent(Collection<EventBO> eventBOs) {
        logger.debug("Update event entities");
        //Update customer entities
        eventBOs = eventRepository.save(eventBOs);

        return eventBOs;
    }


    private Collection<? extends BaseBO> updatePrograms(Collection<ProgramBO> programBOs) {
        logger.debug("Update Program entities");
        //Update campaign entities
        programBOs = programRepository.save(programBOs);

        return programBOs;
    }

     private Collection<? extends BaseBO> updateAttributes(Collection<AttributeBO> attributeBOs) {
        logger.debug("Update Attribute entities");
        //Update campaign entities
        attributeBOs = attributeRepository.save(attributeBOs);

        return attributeBOs;
    }


    private Collection<? extends BaseBO> updateTemplates(Collection<TemplateBO> templateBOs) {
        logger.debug("Update Templates entities");
        //Update template entities
        templateBOs = templateRepository.save(templateBOs);

        return templateBOs;
    }

    private Collection<? extends BaseBO> updateTemplatedetail(Collection<TemplatedetailBO> templatedetailBOs) {
        logger.debug("Update Templates Details entities");
        //Update template entities
        templatedetailBOs = templatedetailRepository.save(templatedetailBOs);

        return templatedetailBOs;
    }

    private Collection<? extends BaseBO> updateSku(Collection<SkuBO> skuBOs) {
        logger.debug("Update SKU Details entities");
        //Update template entities
        skuBOs = skuRepository.save(skuBOs);

        return skuBOs;
    }


    private Collection<? extends BaseBO> updateCategory(Collection<CategoryBO> categoryBOs) {
        logger.debug("Update category Details entities");
        //Update template entities
        categoryBOs = categoryRepository.save(categoryBOs);

        return categoryBOs;
    }


    private Collection<? extends BaseBO> updateCampaignsegments(Collection<CampaignsegmentBO> campaignsegmentBOs) {
        logger.debug("Update Campaign segment mapping entities");
        //Update template entities
        campaignsegmentBOs = campaignsegmentRepository.save(campaignsegmentBOs);

        return campaignsegmentBOs;
    }

    private Collection<? extends BaseBO> updateSegment(Collection<SegmentBO> segmentBOs) {
        logger.debug("Update segment mapping entities");
        //Update template entities
        segmentBOs = segmentRepository.save(segmentBOs);

        return segmentBOs;
    }
	
	/**
     * Update purchase entities.
     * @param purchaseBOs list of campaign entities
     * @return purchaseBOs list of updated campaign entities
     */
    private Collection<? extends BaseBO> updatePurchases(Collection<PurchaseBO> purchaseBOs) {
        logger.debug("Update purchase entities");
        //Update purchase entities
        purchaseBOs = purchaseRepository.save(purchaseBOs);

        return purchaseBOs;
    }
	
	
    /**
     * Perform find operation.
     * @param id entity Id
     * @param module module name
     * @return
     */
    @Override
    public BaseBO find(Long id, Class module) {
        logger.debug("Retrieve entity based on unique Id");
        BaseBO baseBO = null;
        if(module != null && module.equals(CustomerBO.class)){
            logger.debug("Retrieve customer based on memberId");
            baseBO = customerRepository.findOne(id);
            //CustomerBO customerBO = (CustomerBO)baseBO;
        }else if(module != null && module.equals(CampaignBO.class)){
            logger.debug("Retrieve customer based on campaignId");
            baseBO = campaignRepository.findOne(id);
        }else if(module != null && module.equals(ProgramBO.class)){
            logger.debug("Retrieve program based on programId");
            baseBO = programRepository.findOne(id);
        }else if(module != null && module.equals(AttributeBO.class)){
            logger.debug("Retrieve attribute based on attributeId");
            baseBO = attributeRepository.findOne(id);
        }else if(module != null && module.equals(TemplateBO.class)){
            logger.debug("Retrieve attribute based on templateId");
            baseBO = templateRepository.findOne(id);
        }else if(module != null && module.equals(CampaignsegmentBO.class)){
            logger.debug("Retrieve mappings based on campaignId");
            baseBO = campaignsegmentRepository.findOne(id);
        }else if(module != null && module.equals(SegmentBO.class)){
            logger.debug("Retrieve mappings based on segmentId");
            baseBO = segmentRepository.findOne(id);
        }else if(module != null && module.equals(PurchaseBO.class)){
            logger.debug("Retrieve purchase based on memberId");
            baseBO = purchaseRepository.findOne(id);
        }else if(module != null && module.equals(TemplatedetailBO.class)){
            logger.debug("Retrieve template detail based on sequence No");
            baseBO = templatedetailRepository.findOne(id);
        }else if(module != null && module.equals(SkuBO.class)){
            logger.debug("Retrieve SKU detail based on memberId");
            baseBO = skuRepository.findOne(id);
        }else if(module != null && module.equals(CategoryBO.class)){
            logger.debug("Retrieve Category detail based on memberId");
            baseBO = categoryRepository.findOne(id);
        }

        return baseBO;
    }


     @Override
    public List<BaseBO> findAll(Class module) {
        logger.debug("Retrieve entity based on unique Id");
        ArrayList<BaseBO> baseBO = new ArrayList<BaseBO>();

        if(module != null && module.equals(ProgramBO.class)){
            logger.debug("Retrieve all programs");
            baseBO = (ArrayList)programRepository.findAll();
        }else if(module != null && module.equals(AttributeBO.class)){
            logger.debug("Retrieve all attributes");
            baseBO = (ArrayList)attributeRepository.findAll();
        }else if(module != null && module.equals(CampaignBO.class)){
            logger.debug("Retrieve all campaigns");
            baseBO = (ArrayList)campaignRepository.findAll();
        }else if(module != null && module.equals(TemplateBO.class)){
            logger.debug("Retrieve all Templates");
            baseBO = (ArrayList)templateRepository.findAll();
        }else if(module != null && module.equals(TemplatedetailBO.class)){
            logger.debug("Retrieve all Template Details");
            baseBO = (ArrayList)templatedetailRepository.findAll();
        }else if(module != null && module.equals(SkuBO.class)){
            logger.debug("Retrieve all SKu Details");
            baseBO = (ArrayList)skuRepository.findAll();
        }else if(module != null && module.equals(CategoryBO.class)){
            logger.debug("Retrieve all Category Details");
            baseBO = (ArrayList)categoryRepository.findAll();
        }else if(module != null && module.equals(SegmentBO.class)){
            logger.debug("Retrieve all Segment Details");
            baseBO = (ArrayList)segmentRepository.findAll();
        }


        return baseBO;
    }

    /**
     * Perform delete operation.
     * @param id entity Id
     * @param module module name
     * @return
     */
    @Override
    public Long delete(Long id, Class module) {
        logger.info("Deleting entity based on unique Id");
        BaseBO baseBO = null;
        if(module != null && module.equals(CustomerBO.class)){
            baseBO = customerRepository.findOne(id);
            if(baseBO!=null){
                logger.debug("Deleting customer based on memberId");
                customerRepository.delete(id);
                logger.info(" Deleted Customer Details");
            }
        }else if(module != null && module.equals(CampaignBO.class)){
            baseBO = campaignRepository.findOne(id);
            if(baseBO!=null){
                logger.debug("Deleting Campaign based on campaignId");
                campaignRepository.delete(id);
                logger.info(" Deleted Campaign Details");
            }
        }else if(module != null && module.equals(ProgramBO.class)){
            baseBO = programRepository.findOne(id);
            if(baseBO!=null){
                logger.debug("Deleting Program based on programId");
                programRepository.delete(id);
                logger.info(" Deleted Program Details");
            }
        }else if(module != null && module.equals(AttributeBO.class)){
            baseBO = attributeRepository.findOne(id);
            if(baseBO!=null){
                logger.debug("Deleting Attribute based on attributeId");
                attributeRepository.delete(id);
                logger.info(" Deleted Attribute Details");
            }
        }else if(module != null && module.equals(TemplateBO.class)){
            baseBO = templateRepository.findOne(id);
            if(baseBO!=null){
                logger.debug("Deleting Template based on attributeId");
                templateRepository.delete(id);
                logger.info(" Deleted Template Details");
            }
        }else if(module != null && module.equals(TemplatedetailBO.class)){
            baseBO = templatedetailRepository.findOne(id);
            if(baseBO!=null){
                logger.debug("Deleting Template Detail based on attributeId");
                templatedetailRepository.delete(id);
                logger.info(" Deleted Template Details");
            }
        }else if(module != null && module.equals(CampaignsegmentBO.class)){
            baseBO = campaignsegmentRepository.findOne(id);
            if(baseBO!=null){
                logger.debug("Deleting Campaign segment mapping based on attributeId");
                campaignsegmentRepository.delete(id);
                logger.info(" Deleted Campaign segment mapping Details");
            }
        }else if(module != null && module.equals(SegmentBO.class)){
            baseBO = segmentRepository.findOne(id);
            if(baseBO!=null){
                logger.debug("Deleting Segment based on attributeId");
                segmentRepository.delete(id);
                logger.info(" Deleted Segment Details");
            }
        }else if(module != null && module.equals(PurchaseBO.class)){
            baseBO = purchaseRepository.findOne(id);
            if(baseBO!=null){
                logger.debug("Deleting Purchase based on attributeId");
                purchaseRepository.delete(id);
                logger.info(" Deleted Purchase Details");
            }
        }else if(module != null && module.equals(SkuBO.class)){
            baseBO = skuRepository.findOne(id);
            if(baseBO!=null){
                logger.debug("Deleting Sku1 based on memberid");
                skuRepository.delete(id);
                logger.info(" Deleted SKU Details");
            }
        }else if(module != null && module.equals(CategoryBO.class)){
            baseBO = categoryRepository.findOne(id);
            if(baseBO!=null){
                logger.debug("Deleting category based on memberid");
                categoryRepository.delete(id);
                logger.info(" Deleted category Details");
            }
        }
        return null;
    }

    /**
     *
     *
     *
     * @param procedureName
     * @param inParameterMap
     * @return
     */
    @Override
    public int[] executeProcedure(String procedureName, List<Map<String, Object>> inParameterMap) {
        logger.info(queries.getProperty(procedureName)+" version1");
        return simpleJdbcRepository.executeBatch(queries.getProperty(procedureName), inParameterMap);
    }


    @Override
    public Map<String, Object> executeProcedure(String procedureName, Map<String, Object> inParamMap) {
             logger.info(queries.getProperty(procedureName)+" version2");
        return simpleJdbcRepository.execute(queries.getProperty(procedureName), inParamMap);
    }

     @Override
     public Map<String, Object> executeProcedure(String procedureName){
         logger.info(queries.getProperty(procedureName)+" version3");
         return simpleJdbcRepository.execute(queries.getProperty(procedureName), null);

     }
    /**
     * Perform save operation.
     * @param jsonObject String
     * @param module String
     * @return
     */
    @Override
    public Long save(String jsonObject, String module) {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * Perform update operation.
     * @param jsonObject String
     * @param module String
     * @return
     */
    @Override
    public Long update(String jsonObject, String module) {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * Perform find operation.
     * @param id String
     * @param module String
     * @return
     */
    @Override
    public String find(Long id, String module) {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * Perform delete operation.
     * @param id String
     * @param module String
     * @return
     */
    @Override
    public Long delete(Long id, String module) {
        // TODO Auto-generated method stub
        return null;
    }

}
