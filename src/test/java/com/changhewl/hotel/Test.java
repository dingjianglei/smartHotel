package com.changhewl.hotel;

import com.changhewl.hotel.util.BytesUtils;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Test {
	
	public static void testData(){
		Integer bb=22;
		byte[]cc= bb.toString().getBytes();
		System.out.println(cc);
	}
	public static void main(String[] args) {
		try {
			/**
			MQUtils.init();
			ContrlModel model=new ContrlModel();
			model.setBusiType(BusiType.CLOSE_DEVICE);
			model.setServerName("closeDeviceService");
			model = XstreamUtil.fromXML(model.toXML(), ContrlModel.xstream);
			log.info(model.toString());
			MQUtils.sendMessageToQueue("DEVICE_QUEUE",model);
			**/
            byte [] by=BytesUtils.intToBytes(116);
            String ss=BytesUtils.decodeBytesToHexString(by);
            System.out.println(ss);
        } catch (Exception e) {
			e.printStackTrace();
		}
	}
	public static byte[] short2byte(int n) {
        byte b[] = new byte[2];
        b[0] = (byte) (n >> 8);
        b[1] = (byte) n;
        System.out.println(b);
        return b;
    }
}
