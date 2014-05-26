package com.ncr.ATMMonitoring.routertable;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Properties;
import java.util.Set;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.lang.StringUtils;

import com.ncr.ATMMonitoring.routertable.exception.RouterTableException;

/**
 * @author Otto Abreu
 * 
 */
public class RouterTableHandler {

    private static PropertiesConfiguration config;

    private static final String MATRICULA_NOT_PRESENT_DEFAULT_VALUE = "";

    private static final String ROUTER_TABLE_FILE_NAME = "routerTable.properties";

    static {
	try {

	    config = new PropertiesConfiguration(ROUTER_TABLE_FILE_NAME);
	    config.setAutoSave(true);

	} catch (ConfigurationException e) {

	    throw new RouterTableException(
		    RouterTableException.LOAD_FILE_EXCEPTION, e);
	}

    }

    /**
     * Return true if the given matricula is present in the table
     * 
     * @param matricula
     *            int
     * @return boolean
     */
    public static boolean matriculaIsInRouterTable(long matricula) {

	boolean isPresent = false;

	String matriculaPresent = config.getString(Long.toString(matricula),
		MATRICULA_NOT_PRESENT_DEFAULT_VALUE);

	if (StringUtils.isNotEmpty(matriculaPresent)) {

	    isPresent = true;
	}

	return isPresent;
    }

    /**
     * Adds a matricula and the processing node to the table
     * 
     * If the given matricula exists, updates the value
     * 
     * @param matricula
     *            int
     * @param ipNodeInCharge
     *            String
     */
    public static void addMatriculaAndIpToTable(long matricula,
	    String ipNodeInCharge) {

	try {

	    if(config.containsKey(Long.toString(matricula))){
		
		config.clearProperty(Long.toString(matricula));
	    }
	    config.addProperty(Long.toString(matricula), ipNodeInCharge);

	} catch (Exception e) {

	    throw new RouterTableException("Can not add the pair matricula("
		    + matricula + "), node in charge (" + ipNodeInCharge
		    + ") due an exception", e);
	}
    }

    /**
     * Removes an specific entry from the table
     * 
     * @param matricula
     *            int
     */
    public static void removeMatriculaFromTable(long matricula) {

	config.clearProperty(Long.toString(matricula));
    }

    /**
     * returns the node in charge of processing the matricula if any
     * 
     * @param matricula
     * @return String
     */
    public static String getNodeInCharge(long matricula) {
	return config.getString(Long.toString(matricula));
    }

    /**
     * <pre>
     * Returns the current table in string format.
     * Each value is coma separated
     * format: [matricula1=node In charge1, matricula2=node In charge2 ]
     * </pre>
     * 
     * @return String
     */
    public static String tableTotring() {

	final String itemSeparator = ", ";
	StringBuffer tableToString = new StringBuffer("[");
	Iterator<String> iter = config.getKeys();

	while (iter.hasNext()) {

	    String key = iter.next();
	    tableToString.append(key);
	    tableToString.append("=");
	    tableToString.append(config.getString(key));
	    tableToString.append(itemSeparator);

	}
	removeLastComaIfPresent(itemSeparator, tableToString);
	tableToString.append("]");
	return tableToString.toString();
    }
    
    private static void removeLastComaIfPresent(String itemSeparator,StringBuffer tableToString){
	
	int lastcoma = tableToString.lastIndexOf(itemSeparator);
	
	if(lastcoma > -1){
    		
	    tableToString.delete(lastcoma,
		    lastcoma + 1);
	}
     }

    /**
     * Clears the table and then adds all the given values
     * @param newTable Properties
     */
    public static void clearAndUpdateAllTable(Properties newTable) {
	RouterTableHandler.clearTable();
	RouterTableHandler.addAllProperties(newTable);
    }
    
    /**
     *  adds all the given values
     * @param newTable Properties
     */
    public static void updateOAllTable(Properties newTable) {
	
	RouterTableHandler.addAllProperties(newTable);
    }
    
    
    /**
     * Eliminates all the records in the table
     */
    public static void clearTable() {
	config.clear();
    }
    
    private static void addAllProperties(Properties newTable) {

	for (Object key : newTable.keySet()) {

	    String value = newTable.getProperty(key.toString());
	    config.addProperty(key.toString(), value.toString());
	}
    }
    
    /**
     * Return true if ALL the given values are present in the table
     * @param valuesToCheck
     * @return
     */
    public static boolean allValuesPresentInTable(Properties valuesToCheck){
	
	Set<Object> keysToCheck =  valuesToCheck.keySet();
	 boolean allValuesPresent = true;
	
	 for (Object key :keysToCheck){
	   
	     if(!valuesAreEquals(valuesToCheck,key.toString())) {
		 allValuesPresent = false;
		 break;
	     }
	    
	}
	 return allValuesPresent;
    }
    
    private static boolean valuesAreEquals(Properties valuesToCheck,String key){
	
	boolean containsValues = true;
	
	 String valueToCheck = (String)valuesToCheck.getProperty(key);

	if(!config.containsKey(key.toString())){
		
		containsValues = false;
		
	    }else{
		
		String valueInTable = config.getString(key);
		
		if(!valueToCheck.equals(valueInTable)){
		    containsValues = false;  
		}
	    }
	return containsValues;
    }
    /**
     * Return true if the properties given matches exactly with the actual Table
     * @param props
     * @return
     */
    public static boolean isEqualTo(Properties props){
	
	int tableSize = tableSize();
	boolean equal = false;
	
	Set<Object> toCheckKeys = props.keySet();
	
	int toCheckSize = toCheckKeys.size();
	
	if(toCheckSize == tableSize){
	    
	    equal = allValuesPresentInTable(props);
	}
	
	return equal;
    }
    
    /**
     * Returns the size of the table
     * @return int
     */
    public static int tableSize(){
	Iterator<String> keys = config.getKeys();
	int tableSize = 0;
	
	while(keys.hasNext()){
	    tableSize++;
	    keys.next();
	}
	
	return tableSize;
    }
    /**
     * <pre>
     * Returns the common elements (Matricula) between the current table and the given properties
     * Returns empty Set if there is no common mantricula between the two tables
     * @param newValues Properties
     * </pre>
     * @return Set<String>
     */
    public static Set<String> commonPresentMatricula(Properties newValues){
	
	Set<String> commonMatricula = new HashSet<String>();
	Set<Object> newValuesKeys = newValues.keySet();
	
	for(Object key : newValuesKeys){
	    if(config.containsKey(key.toString())){
		commonMatricula.add(key.toString());
	    }
	}
	return commonMatricula;
    }

    public static void main(String pepe[]) {

	try {
	    System.out.println(RouterTableHandler.matriculaIsInRouterTable(5));
	    RouterTableHandler.addMatriculaAndIpToTable(8, "123.456.78.9");
	    System.out.println("added?");
	    System.out.println(RouterTableHandler.matriculaIsInRouterTable(8));
	    System.out.println(RouterTableHandler.tableTotring());
	    
	    Properties props = new Properties();
	    props.put("8", "123.456.78.9");
	    System.out.println(RouterTableHandler.allValuesPresentInTable(props));
	   System.out.println("---");
	    System.out.println(RouterTableHandler.isEqualTo(props));
	    RouterTableHandler.addMatriculaAndIpToTable(8,"123.456.9.1");
	    System.out.println(RouterTableHandler.tableTotring());
	} catch (Exception e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}
    }
}
