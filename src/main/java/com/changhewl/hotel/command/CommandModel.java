package com.changhewl.hotel.command;

import com.changhewl.hotel.util.BytesUtils;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * 命令数据对象
 * Created by dingjianglei on 2015/5/15 0015.
 */
@Slf4j
@Data
public class CommandModel {
    protected String     flag;      //数据起始标志
    protected String     length;    //数据长度
    protected String     tp;        //产品的序号
    protected String     mac;       //mac地址
    protected String     direction; //数据包发送方向
    protected String     fcs;       //校验标志位
    protected String     ip;        //主机IP地址
    protected List<String> dataArray;//业务类型
    private byte[] dataBytes;        //保存字节码信息

    public CommandModel(){

    }

    public CommandModel(byte[] databytes) {
        setFlag(BytesUtils.decodeByteToHexString(databytes[0]).toUpperCase());
        byte[] lengs = { databytes[1], databytes[2] };
        setLength(BytesUtils.decodeBytesToHexString(lengs).toUpperCase());
        byte[] tps = { databytes[3], databytes[4] };
        setTp(BytesUtils.decodeBytesToHexString(tps).toUpperCase());
        byte[] macs = { databytes[5], databytes[6], databytes[7], databytes[8], databytes[9], databytes[10] };

        setMac(BytesUtils.decodeBytesToHexString(macs).toUpperCase());
        setDirection(BytesUtils.decodeByteToHexString(databytes[11]).toUpperCase());
        List dataArray = new ArrayList();
        for (int i = 12; i < databytes.length - 1; ++i) {
            dataArray.add(BytesUtils.decodeByteToHexString(databytes[i]).toUpperCase());
        }
        setDataArray(dataArray);
        setFcs(BytesUtils.decodeByteToHexString(databytes[(databytes.length - 1)]).toUpperCase());
        log.info("FCS:{}", this.fcs);
        setDataBytes(databytes);
    }

    public String getKey()
    {
        if ((StringUtils.isNotEmpty(this.direction)) && (this.dataArray != null)) {
            return this.direction + "_" + (String)this.dataArray.get(0) + (String)this.dataArray.get(1);
        }
        return null;
    }

    public byte getChecksum()
    {
        StringBuffer byteStr = new StringBuffer(this.flag).append(this.length).append(this.tp).append(this.mac).append(this.direction);

        if (this.dataArray != null) {
            for (String str : this.dataArray) {
                byteStr.append(str);
            }
        }

        byte[] data = BytesUtils.chatOrders(byteStr.toString());
        int value = data[1];
        for (int i = 2; i < data.length; ++i) {
            value ^= data[i];
        }
        byte fcs = (byte)value;
        return fcs;
    }

    public int getLength()
    {
        int value1 = this.dataBytes[1];
        int value2 = this.dataBytes[2];
        if (value1 < 0) {
            value1 = 256 + value1;
        }

        if (value2 < 0) {
            value2 = 256 + value2;
        }
        return value1 * 256 + value2;
    }

    public String compentOrder(int length)
    {
        byte[] bytes = BytesUtils.intToBytes(length);
        String byteLength = BytesUtils.decodeBytesToHexString(bytes);
        this.length = byteLength;
        StringBuffer byteStr = new StringBuffer(this.flag).append(byteLength).append(this.tp).append(this.mac).append(this.direction);

        if (this.dataArray != null) {
            for (String str : this.dataArray) {
                byteStr.append(str);
            }
        }

        byteStr.append(BytesUtils.decodeByteToHexString(getChecksum()));
        return byteStr.toString();
    }

    public String toString() {
        return "CommandModel{flag='" + this.flag + '\'' + ", length='" + this.length + '\'' + ", tp='" + this.tp + '\'' + ", mac='" + this.mac + '\'' + ", direction='" + this.direction + '\'' + ", fcs='" + this.fcs + '\'' + ", dataArray=" + this.dataArray + '}';
    }
}
