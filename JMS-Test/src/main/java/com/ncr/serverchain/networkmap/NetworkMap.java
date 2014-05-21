package com.ncr.serverchain.networkmap;

import com.google.gson.Gson;

/**
 * Represents the whole network map
 * @author Otto Abreu
 * 
 */
public class NetworkMap {

    private NetworkNode root;

    public NetworkMap(NetworkNode root) {
	super();
	this.root = root;
    }

    
    @Override
    public String toString() {
	StringBuffer buffer = new StringBuffer("");

	if (networkIsNotEmpty()) {
	    buffer.append(" ROOT: ");
	    buffer.append(root.toString());
	    buffer.append("");
	} else {
	    buffer.append(" Empty network ");
	}
	return buffer.toString();
    }

    /**
     * <pre>
     * JSON representation of the network.
     * 
     * example:
     * 	 {"nodeUrlAndPort":"153.57.97.118:61617","lastcomunicationInMilisecAt":0,"
		children":[
			{
        			"nodeUrlAndPort":"153.57.97.118:61619",
        			"routerTable":"[8\u003d153.57.97.118:61622]",
        			"lastcomunicationInMilisecAt":1400068408847
        		},
			{
				"nodeUrlAndPort":"153.57.97.118:61618",
				"routerTable":"[3\u003d153.57.97.118:61623]",
				"lastcomunicationInMilisecAt":1400068408848
			}
		]
	   }
	  </pre>
     * @return String
     */
    public String toJSON() {
	String json = "{}";

	if (networkIsNotEmpty()) {
	    Gson gson = new Gson();
	    json = gson.toJson(this.root).toString();
	}
	return json;
    }

    private boolean networkIsNotEmpty() {
	if (this.root != null) {
	    return true;
	} else {
	    return false;
	}
    }

}
