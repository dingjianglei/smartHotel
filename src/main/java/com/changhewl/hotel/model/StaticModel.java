package com.changhewl.hotel.model;

import lombok.Data;
import org.apache.commons.lang.StringUtils;

/**
 * Created by Administrator on 2015/5/20 0020.
 */
@Data
public class StaticModel {
    private String userid;
    private String mac;
    private String address;
    private long sessionId;
    private String sessionFlag;
    private boolean isReverse = false;

    public String getKey() {
        if (this.isReverse) {
            if (StringUtils.equals(this.sessionFlag, SessionFlag.REMOTE.getFlag())) {
                return SessionFlag.USER.getFlag() + "_MAC_(" + this.mac + ")";
            }
            return SessionFlag.REMOTE.getFlag() + "_MAC_(" + this.mac + ")";
        }
        return this.sessionFlag + "_MAC_(" + this.mac + ")";
    }
}
