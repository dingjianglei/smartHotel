package com.changhewl.hotel.model;

public enum ClientCommandEnums
{
    PTOS_S1_0102("01_0102", "所有当前在线的设备的节点信息(心跳包)"),

    STOP_S1_0010("01_0010", "开放网络命令，允许节点入网"),
    STOP_S1_0011("01_0011", "关闭网络命令"),

    STOP_S1_0103("01_0103", "允许节点入网命令请求"),
    STOP_S1_0104("01_0104", "查询当前已入网但未批准的所有节点"),
    STOP_S1_0105("01_0105", "删除已经入网的节点命令请求"),
    STOP_S1_0106("01_0106", "查询当前的在线设备命令请求"),

    STOP_S1_1001("01_1001", "控制开关量节点的开关命令请求"),
    STOP_S4_1002("04_1002", "控制开关量节点的开关状态变化上报反馈"),
    STOP_S1_1003("01_1003", "查询开关量节点当前的状态命令请求"),

    STOP_S1_1101("01_1101", "控制开关量节点的开关命令请求"),

    STOP_S1_1201("01_1201", "节点进入红外学习模式"),
    STOP_S1_1202("01_1202", "节点退出红外学习模式"),
    STOP_S1_1203("01_1203", "设置节点红外学习的存储地址"),
    STOP_S4_1204("04_1204", "红外学习完成的上报通知"),
    STOP_S1_1210("01_1210", "发送对应地址的节点红外命令"),

    STOP_S1_4001("01_4001", "控制门锁设备开关的命令请求"),
    STOP_S4_4002("04_4002", "控制门锁节点的状态变化上报反馈"),

    STOP_S1_4101("01_4101", "控制取电开关通断的命令请求"),
    STOP_S4_4102("04_4102", "控制取电开关节点的状态变化上报反馈"),
    END("END", "结束枚举");

    private String code;
    private String description;

    private ClientCommandEnums(String code, String description)
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