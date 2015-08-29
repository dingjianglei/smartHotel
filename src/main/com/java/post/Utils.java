package java.post;

public class Utils {
    /**
     * 全局preference配置
     */
    public final static String CONFIGPREFERENCE = "config";
    public final static String CONFIG_MODE = "mode";
    public final static String CONFIG_CONFIGDATA = "configdata";
    public final static String CONFIG_SENSOR = "sensor";
    public final static String CONFIG_WEATHER = "weather";
    public final static String CONFIG_TIME = "gettime";
    public final static String CONFIG_OPRATION = "opration";
    public final static String CONFIG_PERMISSION = "permission";
    public final static String CONFIG_SYSTEMTIME = "systemtime";
    public final static String CONFIG_LOCATION = "location";

    public final static int NETWORK_PHONE = 1;
    public final static int NO_NETWORK = 0;
    public final static int NETWORK_WIFI = 2;
    /** 启动调试 **/
    public static boolean isDebug = true;

    public static String formatOct(int value) {
        if (value < 10) {
            return "0" + value;
        }

        return Integer.toString(value);
    }

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

    public static byte[] chatOrders(String c) {
        byte[] m = c.getBytes();
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

    /**
     * 解析十六进制字符串
     *
     * @param data
     * @return
     */
    public static String getbyteMsg(byte[] data) {
        String msg = new String();
        for (byte by : data) {
            byte[] db = decodeByte(by);
            msg.concat(getMsg(db[0], db[1]));
        }
        return msg;
    }

    public static String getMsg(byte one, byte two) {
        byte[] data = { one, two };
        String msg = new String(data, 0, data.length);
        return msg;
    }

    // 将两个16以内的数字合并为高四位低四位
    public static byte uniteNumber(byte src0, byte src1) {
        byte _b0 = src0;
        _b0 = (byte) (_b0 << 4);
        byte _b1 = src1;
        byte ret = (byte) (_b0 ^ _b1);
        return ret;
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

}
