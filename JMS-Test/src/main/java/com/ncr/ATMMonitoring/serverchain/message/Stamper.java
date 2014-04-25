package com.ncr.ATMMonitoring.serverchain.message;

import java.io.Serializable;

/**
 * @author Otto Abreu
 *
 */
public interface Stamper {

    void setMessageStamp(Serializable message);
}
