package com.changhewl.hotel.util;

public class BytesUtils {
    // 实现两个十六进制字符合并为一个8位大小字节
    public static byte uniteByte(byte src0, byte src1) {
        byte _b0 = Byte.decode("0x" + new String(new byte[] { src0 }))
                .byteValue();
        _b0 = (byte) (_b0 << 4);
        byte _b1 = Byte.decode("0x" + new String(new byte[] { src1 }))
                .byteValue();
        byte ret = (byte) (_b0 ^ _b1);
        return ret;
    }

    public static byte[] intToBytes(int n) {
        byte[] b = new byte[2];
        b[1] = (byte) (n & 0x000000ff);
        b[0] = (byte) ((n & 0x0000ff00) >> 8);
        // b[1] = (byte) ((n & 0x00ff0000) >> 16);
        // b[0] = (byte) ((n & 0xff000000) >> 32);
        return b;
    }


    /**
     * 合并多个字节数组
     *
     * @param data
     * @return
     */
    public static byte[] uniteBytes(byte[]... data) {
        int length = 0;
        for (byte[] msg : data) {
            length += msg.length;
        }
        byte[] newData = new byte[length];
        int i = 0;
        for (byte[] msg : data) {
            System.arraycopy(msg, 0, newData, i, msg.length);
            i += msg.length;
        }

        return newData;
    }

    public static byte[] chatOrders(String c) {
        byte[] m = c.getBytes();
        return chatOrder(m);
    }

    public static byte[] chatOrder(byte[] m) {
        if (m.length % 2 == 0) {
            byte[] bytes = new byte[m.length / 2];
            for (int i = 0, j = 0; i < m.length; i += 2, j++) {
                bytes[j] = uniteByte(m[i], m[i + 1]);
            }
            return bytes;
        }
        return null;
    }

    /**
     * 解析十六进制字符
     *
     * @param src
     * @return
     */
    public static byte[] decodeByte(byte src) {
        byte[] des = new byte[2];
        des[0] = (byte) (src & 0x0f);
        des[1] = (byte) ((src & 0xf0) >> 4);
        return des;
    }

    /**
     * 解析十六进制字符至16进制字符串
     *
     */
    public static String decodeByteToHexString(byte src) {
        byte[] des = new byte[2];
        des[1] = (byte) (src & 0x0f);
        des[0] = (byte) ((src & 0xf0) >> 4);
        return Integer.toHexString(des[0]) + Integer.toHexString(des[1]);
    }

    public static String decodeBytesToHexString(byte[] data) {
        String result = new String();
        for (byte dd : data) {
            result = result.concat(decodeByteToHexString(dd));
        }
        return result;
    }

    public static byte[] longToBytes(long num) {
        byte[] targets = new byte[4];
        for (int i = 0; i < 4; i++) {
            int offset = (targets.length - 1 - i) * 8;
            targets[i] = (byte) ((num >>> offset) & 0xff);
        }
        return targets;
    }

    public static short byteToShort(byte[] b) {
        short s = 0;
        short s0 = (short) (b[0] & 0xff);// 最低位
        short s1 = (short) (b[1] & 0xff);
        s1 <<= 8;
        s = (short) (s0 | s1);
        return s;
    }
}
