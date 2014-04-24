package com.lister.esb.service;

import com.lister.esb.model.BaseBO;

import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * UDMService is the data layer which interacts with custom repositories.
 * 
 * @author sudharsan_s
 *
 */
public interface UDMService {

	/**
	 * Perform save operation. 
	 * @param jsonObject jsonObject to be persisted.
	 * @param module module name
	 * @return
	 */
	public Long save(String jsonObject, String module);
	
	/**
	 * Perform update operation. 
	 * @param jsonObject
	 * @param module module name
	 * @return
	 */
	public Long update(String jsonObject, String module);
	
	/**
	 * Perform find operation. 
	 * @param id entity Id
	 * @param module module name
	 * @return
	 */
	public String find(Long id, String module);
	
	/**
	 * Perform delete operation. 
	 * @param id entity Id
	 * @param module module name
	 * @return
	 */
	public Long delete(Long id, String module);
	
	/**
	 * Perform save operation.
	 *
     * @param baseBOs list of entities
     * @param module module name
     * @return
	 */
	public Collection<? extends BaseBO> save(Collection<? extends BaseBO> baseBOs, Class module);
	
	/**
	 * Perform update operation.
	 *
     * @param baseBOs list of entities
     * @param module module name
     * @return
	 */
	public  Collection<? extends BaseBO>  update(Collection<? extends BaseBO> baseBOs, Class module);
	
	/**
	 * Perform find operation.
	 * @param id entity Id
	 * @param module module name
	 * @return
	 */
	public BaseBO find(Long id, Class module);

    /**
	 * Perform findAll operation.
	 * @param id entity Id
	 * @param module module name
	 * @return
	 */
	public List<BaseBO> findAll(Class module);
	
	/**
	 * Perform delete operation.
	 * @param id entity Id
	 * @param module module name
	 * @return
	 */
	public Long delete(Long id, Class module);

    /**
     *
     *
     *
     * @param procedureName
     * @param inParameterMap
     * @return
     * @throws IOException
     */
    public int[] executeProcedure(String procedureName, List<Map<String, Object>> inParameterMap);

    /**
     *
     *
     *
     * @param procedureName
     * @param inParamMap
     * @return
     * @throws IOException
     */
    public Map<String, Object> executeProcedure(String procedureName, Map<String, Object> inParamMap);

    /**
     *
     *
     *
     * @param procedureName
     * @param inParamMap
     * @return
     * @throws IOException
     */
    public Map<String, Object> executeProcedure(String procedureName);


}
