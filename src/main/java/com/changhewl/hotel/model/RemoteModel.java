package com.changhewl.hotel.model;

import com.changhewl.hotel.command.CommandModel;
import lombok.Data;
import lombok.ToString;

import java.util.Date;

/**
 * 远程主机实体信息
 * Created by Administrator on 2015/5/24.
 */
@ToString
@Data
public class RemoteModel {
    private String mac;     //主机MAC信息
    private String tp;      //编号
    private String ip;      //主机IP
    private Date createTime;//创建日期

    public RemoteModel(){}
    public RemoteModel(CommandModel model){
        setMac(model.getMac());
        setTp(model.getTp());
        setIp(model.getIp());
        setCreateTime(new Date());
    }
    public String getKey(){
        return "REMOTE_MAC_("+mac+")";
    }

}
