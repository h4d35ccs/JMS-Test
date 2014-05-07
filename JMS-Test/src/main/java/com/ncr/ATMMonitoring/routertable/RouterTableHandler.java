package com.ncr.ATMMonitoring.routertable;

import java.util.Iterator;

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

    public static boolean matriculaIsInRouterTable(int matricula) {

	boolean isPresent = false;

	String matriculaPresent = config.getString(Integer.toString(matricula),
		MATRICULA_NOT_PRESENT_DEFAULT_VALUE);

	if (StringUtils.isNotEmpty(matriculaPresent)) {

	    isPresent = true;
	}

	return isPresent;
    }

    public static void addMatriculaAndIpToTable(int matricula,
	    String ipNodeInCharge) {

	try {

	    config.addProperty(Integer.toString(matricula), ipNodeInCharge);

	} catch (Exception e) {

	    throw new RouterTableException("Can not add the pair matricula("
		    + matricula + "), node in charge (" + ipNodeInCharge
		    + ") due an exception", e);
	}
    }

    public static void removeMatriculaFromTable(int matricula) {

	config.clearProperty(Integer.toString(matricula));
    }

    public static String getNodeInCharge(int matricula) {
	return config.getString(Integer.toString(matricula));
    }

    public static String tableTotring() {

	StringBuffer tableToString = new StringBuffer("[");
	Iterator<String> iter = config.getKeys();

	while (iter.hasNext()) {

	    String key = iter.next();
	    tableToString.append(key);
	    tableToString.append("=");
	    tableToString.append(config.getString(key));
	    tableToString.append(", ");

	}
	tableToString.append("]");
	return tableToString.toString();
    }

    public static void main(String pepe[]) {

	try {
	    System.out.println(RouterTableHandler.matriculaIsInRouterTable(5));
	    RouterTableHandler.addMatriculaAndIpToTable(8, "123.456.78.9");
	    System.out.println("added?");
	    System.out.println(RouterTableHandler.matriculaIsInRouterTable(8));
	} catch (Exception e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}
    }
}
