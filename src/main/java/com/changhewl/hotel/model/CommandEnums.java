package com.changhewl.hotel.model;

public enum CommandEnums
{
    PTOS_S3_0101("03_0101", "主机的注册消息上报"),
    PTOS_S3_0102("03_0102", "所有当前在线的设备的节点信息(心跳包)"),
    PTOS_S2_0010("02_0010", "开放网络命令应答"),
    PTOS_S2_0011("02_0011", "开放网络命令应答"),
    PTOS_S3_0110("03_0110", "通用节点的注册上报通知"),

    PTOS_S2_0103("02_0103", "允许节点入网命令应答"),
    PTOS_S2_0104("02_0104", "查询当前已入局域网但未批准的所有节点命令应答"),
    PTOS_S2_0105("02_0105", "删除已经入网的节点命令应答"),

    PTOS_S2_1001("02_1001", "控制开关量节点的开关命令应答"),
    PTOS_S3_1002("03_1002", "开关量节点的开关状态变化上报"),
    PTOS_S4_1003("04_1003", "查询开关量节点当前的状态命令应答");

    private String code;
    private String description;

    private CommandEnums(String code, String description)
    {
        this.code = code;
        this.description = description;
    }

    public String getCode()
    {
        return this.code;
    }

    public String getDescription()
    {
        return this.description;
    }
}