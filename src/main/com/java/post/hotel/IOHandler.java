package java.post.hotel;

import java.io.IOException;
import java.util.List;

import java.post.hotel.OrderConfig.ORDER;

public class IOHandler {

	public final static int HEADERLENGTH = 12;

	private int length = 0;
	/**
	 * �����ֽ�
	 */
	byte[] cachebyte = new byte[0];
	int cachelength = 0;
	private boolean crc;

	private IOHandlerListener listener;

	public interface IOHandlerListener {

		/**
		 * �������������豸
		 * 
		 * @param onlineDevice
		 */
		public void getOnlineDeviceList(List<DeviceVo> onlineDevice);

		/**
		 * �豸��Դ״̬
		 * 
		 * @param device
		 */
		public void getDevicePowerStatus(DeviceVo device);

		/**
		 * ����ִ�� ���
		 * 
		 * @param result
		 */
		public void getResultOrder(ResultOrder result);

		public void getOutNetDeviceList(List<DeviceVo> devices);

		public void getIrResult(ResultOrder result);

		public void getListResultOrder(List<ResultOrder> result);

		public void onToast(String string);

		public void registerSuccess();

		public void rebackOnLineDevice();

		public void rebackData(byte[] cachebyte);

		public void registerFail();

	};

	public IOHandler(IOHandlerListener listener) {
		this.listener = listener;
	}

	public void handler(byte[] data) {
		if (data[0] == ORDER.HEADER) {
			reset();
		}

		cachebyte = UtilsConfig.uniteBytes(cachebyte, data);

		if (cachebyte[0] != ORDER.HEADER) {
			System.out.println("����֮ǰ���");
			reset();
			return;
		}

		if (cachebyte.length > 2 && this.length == 0) {
			// ���㳤��
			getLength();
		}

		if (cachebyte.length >= (length + HEADERLENGTH - 8)) {
			// ȫ����
			crc();

			if (crc) {
				// crcУ��ɹ�
				try {
					OrderPack pack = new OrderPack(cachebyte);
					pack.unPack();
					if (pack.getD0() == 0x01) {
						if (pack.getD1() == 0x01) {
							if (pack.getResultByte()) {
								// �з�����ע��ɹ�
								listener.registerSuccess();
							} else {
								listener.registerFail();
							}
							;
						} else if (pack.getD1() == 0x02) {
							// �����豸��Ϣ
							listener.rebackOnLineDevice();
						}else if(pack.getD1()==0x06){
							// �����豸��Ϣ
							listener.rebackOnLineDevice();
						}
					} else {
						// ���ԭ·����
						listener.rebackData(cachebyte);
					}

					// if (pack.getD0() == 0x01) {
					// if (pack.getD1() == 0x02) {
					// // ���е�ǰ���ߵ��豸�Ľڵ���Ϣ�������
					// List<DeviceVo> devices = pack.getCurrentDevice();
					// listener.getOnlineDeviceList(devices);
					// } else if (pack.getD1() == 0x04) {
					// // ���е�ǰ���ߵ��豸�Ľڵ���Ϣ�������
					// List<DeviceVo> devices = pack.getCurrentDevice();
					// listener.getOutNetDeviceList(devices);
					// } else if (pack.getD1() == 0x05) {
					// // ɾ��ڵ������Ӧ��
					// List<ResultOrder> result = pack.getListResult();
					// listener.getListResultOrder(result);
					// }
					// } else if (pack.getD0() == 0x10) {
					// if (pack.getD1() == 0x02) {
					// // �豸����״̬�ϱ�ֵ
					// DeviceVo device = pack.getPowerStatus();
					// listener.getDevicePowerStatus(device);
					// } else if (pack.getD1() == 0x01) {
					// // ���ƿ������ڵ�Ŀ�����������Ӧ��
					// ResultOrder result = pack.getResult();
					// listener.getResultOrder(result);
					// } else if (pack.getD1() == 0x03) {
					// // ��ѯ�����ֵ
					// DeviceVo device = pack.getPowerStatus();
					// listener.getDevicePowerStatus(device);
					// }
					// } else if (pack.getD0() == 0x40) {
					// // �����
					// if (pack.getD1() == 0x02) {
					// // �豸����״̬�ϱ�ֵ
					// DeviceVo device = pack.getPowerStatus();
					// listener.getDevicePowerStatus(device);
					// } else if (pack.getD1() == 0x01) {
					// // ���ƿ������ڵ�Ŀ�����������Ӧ��
					// ResultOrder result = pack.getResult();
					// listener.getResultOrder(result);
					// }
					// } else if (pack.getD0() == 0x41) {
					// if (pack.getD1() == 0x02) {
					// // �豸����״̬�ϱ�ֵ
					// DeviceVo device = pack.getPowerStatus();
					// listener.getDevicePowerStatus(device);
					// } else if (pack.getD1() == 0x01) {
					// // ���ƿ������ڵ�Ŀ�����������Ӧ��
					// ResultOrder result = pack.getResult();
					// listener.getResultOrder(result);
					// }
					// } else if (pack.getD0() == 0x12) {
					// // if (pack.getD1() == 0x01) {
					// // DeviceIrActivity.learnmode = true;
					// // // �ڵ�������ѧϰģʽӦ��
					// // Toast.makeText(context, "�Ѿ��������ģʽ",
					// // Toast.LENGTH_SHORT).show();
					// //
					// // Intent intent = new Intent(
					// // DeviceIrSetActivity.ACTIONIRLEARNSET);
					// // context.sendBroadcast(intent);
					// // } else if (pack.getD1() == 0x02) {
					// // DeviceIrActivity.learnmode = false;
					// // // �ڵ��˳�����ѧϰģʽӦ��
					// // Toast.makeText(context, "�˳�����ģʽ",
					// // Toast.LENGTH_SHORT).show();
					// // Intent intent = new Intent(
					// // DeviceIrSetActivity.ACTIONIRLEARNEXIT);
					// // context.sendBroadcast(intent);
					// // } else if (pack.getD1() == 0x03) {
					// // // ���ýڵ����ѧϰ�Ĵ洢��ַӦ��
					// // ResultOrder result = pack.getResult();
					// // listener.getIrResult(result);
					// // } else if (pack.getD1() == 0x04) {
					// // // ����ѧϰ��ɵ��ϱ�֪ͨ
					// // ResultOrder result = pack.getResult();
					// // listener.getIrResult(result);
					// // } else if (pack.getD1() == 0x10) {
					// // // ���Ͷ�Ӧ��ַ�Ľڵ��������Ӧ��
					// // ResultOrder result = pack.getResult();
					// // listener.getIrResult(result);
					// // }
					// }else if (pack.getD0() == 0x11) {
					// if (pack.getD1() == 0x01) {
					// ResultOrder result = pack.getResult();
					// listener.getResultOrder(result);
					// }
					// }else if(pack.getD0()==0x00){
					// if(pack.getD1()==0x10){
					// boolean result=pack.getResultByte();
					// if(result){
					// listener.onToast("�豸��������");
					// }else{
					// listener.onToast("��ʧ��");
					// }
					// }else if(pack.getD1()==0x12){
					// boolean result=pack.getResultByte();
					// if(result){
					// listener.onToast("�����ɹ�");
					// }else{
					// listener.onToast("�ָ���������ʧ��");
					// }
					// }else if(pack.getD1()==0x13){
					// boolean result=pack.getResultByte();
					// if(result){
					// listener.onToast("�����ɹ�");
					// }else{
					// listener.onToast("�ָ���������ʧ��");
					// }
					// }
					// }

					pack.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} else {
				System.out.println("crc ʧ��");
			}

			reset();
			return;
		}
	}

	private void crc() {
		int value = cachebyte[1];
		for (int i = 2; i < (this.length + 3); i++) {
			value = value ^ cachebyte[i];
		}
		byte xx = (byte) value;
		if (xx == cachebyte[this.length + 3]) {
			this.crc = true;
		} else {
			this.crc = false;
		}
	}

	private void getLength() {
		int value1 = cachebyte[1];
		int value2 = cachebyte[2];
		if (value1 < 0) {
			value1 = 256 + value1;
		}

		if (value2 < 0) {
			value2 = 256 + value2;
		}

		this.length = value1 * 256 + value2;
	}

	private void reset() {
		cachebyte = new byte[0];
		cachelength = 0;
		length = 0;

		crc = true;
	}

}
