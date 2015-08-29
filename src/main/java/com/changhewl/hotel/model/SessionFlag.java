package com.changhewl.hotel.model;

import lombok.Getter;

/**
 * Session标识
 * Created by Administrator on 2015/5/24.
 */
public enum SessionFlag {
    REMOTE("REMOTE","远程主机session"),
    USER("USER","用户session");//02
    @Getter
    private String flag;
    @Getter
    private String description;
    SessionFlag(String flag,String description){
        this.flag=flag;
        this.description=description;
    }

}
