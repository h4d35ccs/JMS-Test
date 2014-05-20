package com.ncr.ATMMonitoring.serverchain.message.specific;


/**
 * @author Otto Abreu
 *
 */
public class UpdateRouterTableMessage implements SpecificMessage {

    private UpdateType updateType = UpdateType.UPDATE;
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    
    public UpdateType getUpdateType() {
        return updateType;
    }

    public void setUpdateType(UpdateType updateType) {
        this.updateType = updateType;
    }
    
    /**
     * Indicate if this update is for adding (default) or for removing
     * @author Otto Abreu
     *
     */
    public enum UpdateType{
	UPDATE, REMOVE_ONLY, FORCE_UPDATE_FROM_ROOT;
    }

}
