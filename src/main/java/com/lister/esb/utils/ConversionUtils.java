package com.lister.esb.utils;

import com.lister.esb.dto.BaseDTO;
import org.apache.log4j.Logger;
import com.lister.esb.model.BaseBO;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.oxm.XmlMappingException;
import org.springframework.oxm.xstream.XStreamMarshaller;
import org.springframework.stereotype.Component;
import org.springframework.xml.transform.StringResult;

import javax.xml.transform.stream.StreamSource;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.util.*;

@Component
public class ConversionUtils {
    @Autowired
    private DTO2BOCacheManager DTO2BOCacheManager;
    private Logger logger = Logger.getLogger(ConversionUtils.class);

    @Autowired
    @Qualifier("jsonMapper")
    private ObjectMapper jsonMapper;

    @Autowired
    @Qualifier("xstreamMarshaller")
    private XStreamMarshaller xmlMarshaller;

    @Autowired
    private DTO2TypeReferencesCache dto2TypeReferencesCache;

    public BaseBO convertToBO(BaseDTO baseDTO){
        Class _dtoClass = baseDTO.getClass();
        Class<? extends BaseBO> _boClass = DTO2BOCacheManager.getBOClass(_dtoClass);
        BaseBO boObject =null;
        try {
            boObject = _boClass.newInstance();
            Field[] dtoDeclaredFields = baseDTO.getClass().getDeclaredFields();
            //Field[] boDeclaredFields = _boClass.getDeclaredFields();
            for(Field dtoField: dtoDeclaredFields){
                dtoField.setAccessible(true);
                try {
                    Field boField = _boClass.getDeclaredField(dtoField.getName());
                    boField.setAccessible(true);
                    boField.set(boObject, dtoField.get(baseDTO));
                } catch (NoSuchFieldException e) {
                    e.printStackTrace();
                }
            }
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        return boObject;
    }

    public BaseDTO convertToDTO(BaseBO baseBO){
        Class _boClass = baseBO.getClass();
        Class<? extends BaseDTO> _dtoClass =  DTO2BOCacheManager.getDTOClass(_boClass);
        BaseDTO dtoObject =null;
        try {
            dtoObject = _dtoClass.newInstance();
            Field[] boDeclaredFields = baseBO.getClass().getDeclaredFields();
            for(Field boField: boDeclaredFields){
                boField.setAccessible(true);
                try {
                    Field dtoField = _dtoClass.getDeclaredField(boField.getName());
                    dtoField.setAccessible(true);
                    dtoField.set(dtoObject, boField.get(baseBO));
                } catch (NoSuchFieldException e) {
                    //
                }
            }
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        return dtoObject;
    }

    public String convertToJSON(Object obj){
        logger.debug(obj);
        OutputStream outputStream = new ByteArrayOutputStream(500);
        try {
            jsonMapper.writeValue(outputStream, obj);
            logger.debug(outputStream.toString());
            return outputStream.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String convertToJSONFromBO(List<BaseBO> baseBOs){
        List<BaseDTO> baseDTOs = convertToDTOFromBO(baseBOs);
        return convertToJSON(baseDTOs);
    }

    public List<BaseBO> convertToBOFromJSON(String dtoJSON, Class<? extends BaseDTO> dtoClass) throws IOException, JsonMappingException {
        List<BaseDTO> baseDTOs = convertToDTOFromJSON(dtoJSON, dtoClass);
        return convertToBOFromDTO(baseDTOs);
    }

    public List<BaseDTO> convertToDTOFromJSON(String dtoJSON, Class<? extends BaseDTO> dtoClass) throws IOException, JsonMappingException {
        return jsonMapper.readValue(dtoJSON, dto2TypeReferencesCache.getTypeRef(dtoClass));
    }

    public List<BaseBO> convertToBOFromDTO(List<BaseDTO> baseDTOs)  {
        List<BaseBO> baseBOs = new ArrayList<BaseBO>(baseDTOs.size());
        for(BaseDTO baseDTO : baseDTOs){
            baseBOs.add(convertToBO(baseDTO));
        }
        return baseBOs;
    }

    public List<BaseDTO> convertToDTOFromBO(List<BaseBO> baseBOs)  {
        List<BaseDTO> baseDTOs = new ArrayList<BaseDTO>(baseBOs.size());
        for( BaseBO baseBO : baseBOs){
            baseDTOs.add(convertToDTO(baseBO));
        }
        return baseDTOs;
    }

    /**
     * Convert XML to DTO.
     *
     * @param dtoXML input XML
     * @return
     * @throws java.io.IOException
     * @throws org.springframework.oxm.XmlMappingException
     */
    public List<BaseDTO> convertToDTOFromXML(String dtoXML) throws IOException, XmlMappingException {
        List<BaseDTO> baseDTOs = (List<BaseDTO>) xmlMarshaller.unmarshal(new StreamSource(new ByteArrayInputStream(dtoXML.getBytes())));
        return baseDTOs;
    }

    /**
     * Convert DTO to XML.
     *
     * @param obj
     * @return
     * @throws java.io.IOException
     */
    public String convertToXML(Object obj) throws IOException {
        logger.info(obj +" asdasd");
        StringResult result = new StringResult();
        xmlMarshaller.marshal( obj, result );
        return result.toString();
    }

    public String convertToXMLFromBO(List<BaseBO> baseBOs) throws IOException {
        List<BaseDTO> baseDTOs =convertToDTOFromBO(baseBOs);

        StringResult result = new StringResult();
        xmlMarshaller.marshal( baseDTOs, result );
        return result.toString();
    }


    public String convertObjectToJson(String jsonString) throws IOException, JSONException {
        logger.info(jsonString);
       // logger.info(new JSONObject().put("id",jsonString).toString());
          return new JSONObject().put("id",jsonString).toString();
    }

    /**
     *
     * @param jsonString
     * @return
     * @throws IOException
     */
    public List<Map<String, Object>> convertToMapFromJson(String jsonString) throws IOException {
        return jsonMapper.readValue(jsonString, new TypeReference<List<Map<String, Object>>>() {});
    }

    /**
        * Convert xml into list of maps having dto field name and value
        * @param dtoXML
        * @return list of maps having dto field name and value
        * @throws IOException
        * @throws IllegalAccessException
        */
       public List<Map<String, Object>> convertToMapFromXML(String dtoXML) throws IOException, IllegalAccessException {
           //Convert to dto from xml
           List<BaseDTO> baseDTOs = convertToDTOFromXML(dtoXML);
           //Convert to list of maps from dtos
           List<Map<String, Object>> dtoFieldMaps = convertToMapFromDTO(baseDTOs);
           return dtoFieldMaps;
       }

       /**
        * Convert list of dtos to list of maps having dto field name and value
        * @param baseDTOs list of dtos
        * @return list of maps having dto field name and value
        * @throws IOException
        * @throws IllegalAccessException
        */
       public List<Map<String, Object>> convertToMapFromDTO(Collection<BaseDTO> baseDTOs) throws IOException,IllegalAccessException {
           List<Map<String, Object>> dtoFieldMaps = new ArrayList<Map<String, Object>>();
           //Convert dto to map with key as field name and value as field  value
           for (BaseDTO baseDTO: baseDTOs){
               Map<String, Object> dtoFieldMap = convertToMapFromDTO(baseDTO);
               dtoFieldMaps.add(dtoFieldMap);
           }
           return dtoFieldMaps;
       }

       /**
        * Convert dto to map with key as field name and value as field value
        * @param baseDTO dto
        * @return map containing dto field name and value
        * @throws IllegalAccessException
        */
       public Map<String, Object> convertToMapFromDTO(BaseDTO baseDTO) throws IllegalAccessException {
           Map<String, Object> dtoFieldMap = new HashMap<String, Object>();
           Class dtoClass = baseDTO.getClass();
           Field[] dtoFields = dtoClass.getDeclaredFields();
           //Construct map with key as field name and value as field value
           for (Field field: dtoFields){
               field.setAccessible(true);
               dtoFieldMap.put(field.getName(),field.get(baseDTO));
           }
           return dtoFieldMap;
       }


     /*
     Converting from procedure result to xml
     */

     public String convertToXmlString(Object obj, String dto) throws IOException {
       String responseString = new String();
       String returnString = new String();
         returnString = "<string>&lt;list&gt;";
                  logger.info(obj.toString().length());
         if(obj.toString().length()>37){
               responseString = obj.toString().substring(
                           obj.toString().indexOf("#result-set-1=[{")+16,
                           obj.toString().indexOf("}]",obj.toString().indexOf("#result-set-1=[{")+16)
                       );

             logger.info(responseString);
       String[] rows = responseString.split("\\}, \\{");


       for(int r=0;r<rows.length;r++) {
           if(rows.length>1){
                returnString = returnString + "&lt;"+dto+"&gt;";
           }
           String[] elementsArray = rows[r].split(",");
           for (int i = 0; i < elementsArray.length; i++) {
                   String[] temp = elementsArray[i].split("=");
                   if (temp.length == 2) {
                       returnString = returnString + "&lt;" + temp[0].trim() + "&gt;" + temp[1].trim() + "&lt;/" + temp[0].trim() + "&gt;";
                   } else {
                       returnString = returnString + "&lt;" + temp[0].trim() + ">&lt;/" + temp[0].trim() + "&gt;";

                   }
           }
           if(rows.length>1){
                returnString = returnString + "&lt;/"+dto+"&gt;";
           }
       }
         }else{
              returnString = returnString +  "";
         }
                  logger.info(returnString);
       return returnString+"&lt;/list&gt;</string>";
   }


    public String convertToJsonString(Object obj){
       String responseString = new String();
       String returnString = new String();
       returnString = "{ \"id\":\"[";
       logger.info(obj.toString().length());
       logger.info(obj.toString());
       logger.info(obj.toString().indexOf("#result-set-1="));
       logger.info(obj.toString().indexOf(", #update-count"));
        if(obj.toString().length()>37){
       responseString = obj.toString().substring(
                          obj.toString().indexOf("#result-set-1=")+16,
                          obj.toString().indexOf("}]",obj.toString().indexOf("#result-set-1=[{")+16)
                      );

       logger.info("Response String:"+responseString);
       String[] rows = responseString.split("\\}, \\{");

       for(int r=0;r<rows.length;r++){
           logger.info("R:"+r);
           returnString += "{";
           String[] elementsArray = rows[r].split(",");
             for (int i = 0; i < elementsArray.length; i++) {
               String[] temp = elementsArray[i].trim().split("=");
                 if(temp.length==2){
                    returnString +="\\\""+this.convertToCamelCase(temp[0])+"\\\":"+"\\\""+temp[1]+"\\\",";
                 }else{
                     returnString +="\\\""+this.convertToCamelCase(temp[0])+"\\\":"+"\\\""+null+"\\\",";
                 }
             }
           returnString = returnString.substring(0, returnString.length()-1);
           returnString += "},";
       }
       returnString = returnString.substring(0, returnString.length()-1);
       returnString+="]\"}";
        }else{
            returnString = returnString +  "";
        }
       logger.info(returnString);

       return returnString;
   }


   public String convertToCamelCase(String value){
       StringBuilder sb = new StringBuilder();

       final char delimChar = '_';
       boolean lower = false;
       for (int charInd = 0; charInd < value.length(); ++charInd) {
           final char valueChar = value.charAt(charInd);
           if (valueChar == delimChar) {
               lower = false;
           } else if (lower) {
               sb.append(Character.toLowerCase(valueChar));
           } else {
               sb.append(Character.toUpperCase(valueChar));
               lower = true;
           }
       }

       return sb.toString().substring(0, 1).toLowerCase() + sb.toString().substring(1);
   }


}
