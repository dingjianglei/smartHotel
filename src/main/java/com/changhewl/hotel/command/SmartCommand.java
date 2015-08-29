package com.changhewl.hotel.command;

import com.changhewl.hotel.util.BytesUtils;
import com.google.common.base.MoreObjects;
import com.google.common.collect.Lists;
import com.google.common.io.BaseEncoding;
import com.google.common.primitives.Bytes;
import com.google.common.primitives.Shorts;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SmartCommand {
    protected byte      flag;      //数据起始标志
    protected short     leng;      //数据长度
    protected short      tp;       //产品的序号
    protected String    mac;       //mac地址
    protected byte      direction; //数据包发送方向
    protected byte      fcs;       //校验标志位
    protected byte      busytype;  //业务类型
    protected List<Byte> dataByteArray = new ArrayList<Byte>();

    public static SmartCommand responseTo(SmartCommand request) {
        SmartCommand response = new SmartCommand(request.getFlag(), request.getTargetByteArray(), (byte) (request.getDirection() + 1));
        List<Byte> data = Lists.newArrayList(request.getDataByteArray());
        if (data.size() >= 2) {
            response.setDataByteArray(data.subList(0, 2));
        }
        return response;
    }
    //根据设备发送的字节信息组装实体对象
    public static SmartCommand responseTo(byte [] databytes){
        SmartCommand response=new SmartCommand();
        response.setBusytype(databytes[0]);
        byte [] lengs={databytes[1],databytes[2]};
        response.setLeng(BytesUtils.byteToShort(lengs));
        byte [] tps={databytes[3],databytes[4]};
        response.setTp(BytesUtils.byteToShort(tps));
        return response;
    }
    public SmartCommand() {
        // DO NOTHING
    }

    public SmartCommand(byte flag, byte[] target, byte direction, byte... data) {
        setFlag(flag);
        setTarget(target);
        setDirection(direction);
        setDataByteArray(data);
    }

    public SmartCommand(byte flag, String target, byte direction, byte... data) {
        setFlag(flag);
        setTarget(target);
        setDirection(direction);
        setDataByteArray(data);
    }

    public byte getFlag() {
        return flag;
    }

    public void setFlag(byte flag) {
        this.flag = flag;
    }

    public short getLength() {
        return (short) (2 + 6 + 1 + dataByteArray.size());
    }

    public byte[] getLengthByteArray() {
        short length = getLength();
        return new byte[] { (byte) (length >> 8), (byte) length };
    }

    public short getLeng() {
        return leng;
    }

    public byte[] getLengByteArray() {
        return new byte[] { (byte) (leng >> 8), (byte) leng };
    }

    public void setLeng(short leng) {
        this.leng = leng;
    }

    public void setLeng(byte b1, byte b2) {
        this.leng = Shorts.fromBytes(b1, b2);
    }

    public String getMac() {
        return mac;
    }

    public byte[] getMacByteArray() {
        return BaseEncoding.base16().decode(mac);
    }

    public void setMac(String mac) {
        this.mac = mac;
    }

    public void setMac(byte[] mac) {
        this.mac = BaseEncoding.base16().encode(mac);
    }

    public byte getDirection() {
        return direction;
    }

    public void setDirection(byte direction) {
        this.direction = direction;
    }

    public List<Byte> getDataByteArray() {
        return dataByteArray;
    }

    public void setDataByteArray(List<Byte> dataByteArray) {
        this.dataByteArray = dataByteArray;
    }

    public void setDataByteArray(byte ... data) {
        dataByteArray.clear();
        this.addData(data);
    }

    public void addData(byte data) {
        dataByteArray.add(data);
    }

    public void addData(byte ... data) {
        dataByteArray.addAll(Bytes.asList(data));
    }

    public void addData(short data) {
        dataByteArray.add((byte) (data >> 8));
        dataByteArray.add((byte) data);
    }

    public void setTarget(byte[] target) {
        if (target.length > 2) {
            setLeng(target[0], target[1]);
            setMac(Arrays.copyOfRange(target, 2, target.length));
        }
    }

    public void setTarget(String target) {
        setTarget(BaseEncoding.base16().decode(target));
    }

    public String getTarget() {
        return BaseEncoding.base16().encode(getTargetByteArray());
    }

    public byte[] getTargetByteArray() {
        List<Byte> target = Lists.newArrayList();
        target.addAll(Bytes.asList(getLengByteArray()));
        target.addAll(Bytes.asList(getMacByteArray()));
        return Bytes.toArray(target);
    }

    public byte getChecksum() {
        byte[] length = getLengthByteArray();
        byte fcs =  (byte) (length[0] ^ length[1]);
        byte[] type = getLengByteArray();
        fcs ^= type[0];
        fcs ^= type[1];
        for (byte m : getMacByteArray())
            fcs ^= m;
        fcs ^= direction;
        for (Byte m : dataByteArray)
            fcs ^= m;
        return fcs;
    }

    public short getTp() {
        return tp;
    }

    public void setTp(short tp) {
        this.tp = tp;
    }

    public byte getFcs() {
        this.fcs=getChecksum();
        return fcs;
    }


    public byte getBusytype() {
        return busytype;
    }

    public void setBusytype(byte busytype) {
        this.busytype = busytype;
    }

    @Override
    public String toString() {
        byte[] length = getLengthByteArray();
        byte[] type = getLengByteArray();
        StringBuilder macStringBuilder = new StringBuilder();
        boolean isFirst = true;
        for (byte m : getMacByteArray()) {
            if (isFirst) {
                isFirst = false;
                macStringBuilder.append(String.format("%02X", m));
            } else {
                macStringBuilder.append(String.format(",%02X", m));
            }
        }

        StringBuilder dataStringBuilder = new StringBuilder();
        if (dataByteArray.size() == 0) {
            dataStringBuilder.append("...");
        } else {
            isFirst = true;
            for (byte d : dataByteArray) {
                if (isFirst) {
                    isFirst = false;
                    dataStringBuilder.append(String.format("%02X", d));
                } else {
                    dataStringBuilder.append(String.format(",%02X",d));
                }
            }
        }

        String fcs = String.format("%02X", getChecksum());
        return MoreObjects.toStringHelper(this)
                .add("FLG", String.format("[%02X]", flag))
                .add("LEN", String.format("[%02X,%02X]", length[0], length[1]))
                .add("TYP", String.format("[%02X,%02X]", type[0], type[1]))
                .add("MAC", String.format("[%s]", macStringBuilder))
                .add("DIR", String.format("[%02X]", direction))
                .add("DAT", String.format("[%s]", dataStringBuilder))
                .add("FCS", String.format("[%s]", fcs))
                .toString();
    }

}
