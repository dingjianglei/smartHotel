package java.post.hotel;

public class OrderConfig {
	public interface ORDER {
		/**
		 * Э��ͷ
		 */
		public final static byte HEADER = (byte) 0xfc;
		/**
		 * ��������
		 */
		public final static byte DIR1 = (byte) 0x01;
		/**
		 * ����Ӧ��
		 */
		public final static byte DIR2 = (byte) 0x02;
		/**
		 * �����ϱ�
		 */
		public final static byte DIR3 = (byte) 0x03;
		/**
		 * �ϱ��ظ�
		 */
		public final static byte DIR4 = (byte) 0x04;

		/**
		 * ϵͳ����
		 */
		public final static byte ORDERD01 = (byte) 0x00;
		/**
		 * ���в�Ʒ����
		 */
		public final static byte ORDERD02 = (byte) 0x01;
		/**
		 * ͨ�ÿ������ڵ㣨����ƹ�)
		 */
		public final static byte ORDERD03 = (byte) 0x10;
		/**
		 * �����ڵ�
		 */
		public final static byte ORDERD04 = (byte) 0x11;
		/**
		 * ����ת����
		 */
		public final static byte ORDERD05 = (byte) 0x12;
		public final static byte ORDERD06 = (byte) 0x06;

	}

	public interface DEVICEPARAMS {
		public final static int SWITCHOFF = 0;
		public final static int SWITCHON = 1;
		public final static int VALUE0 = 0;
	}

	public interface IRCODE {
		public final static int TV_OPEN = 1;
		public final static int TV_VOICE = 2;
		public final static int TV_VOICE_UP = 3;
		public final static int TV_VOICE_DOWN = 4;
		public final static int TV_UP = 5;
		public final static int TV_DOWN = 6;
		public final static int TV_LEFT = 7;
		public final static int TV_RIGHT = 8;
		public final static int TV_ITEM_UP = 9;
		public final static int TV_ITEM_DOWN = 10;
		public final static int TV_AVTV = 11;
		public final static int TV_SURE = 12;
		public final static int TV_SELF1 = 13;
		public final static int TV_SELF2 = 14;

		public final static int AIR_POWER_ON = 20;
		public final static int AIR_POWER_OFF = 0;
		public final static int AIR_MODE_COLD = 21;
		public final static int AIR_MODE_HUM = 29;
		public final static int AIR_MODE_WARM = 30;
		public final static int AIR_MODE_HOT = 31;
		/**
		 * ����19
		 */
		public final static int AIR_UP = 22;
		/**
		 * �½�19
		 */
		public final static int AIR_DOWN = 23;
		public final static int AIR_SLIENT = 24;
		public final static int AIR_LEFTRIGHT = 25;
		public final static int AIR_UPDOWN = 26;
		public final static int AIR_SELF1 = 27;
		public final static int AIR_SELF2 = 28;
		public final static int AIR_UP20 = 29;
		public final static int AIR_DOWN20 = 30;
		public final static int AIR_UP21 = 31;
		public final static int AIR_DOWN21 = 32;
		public final static int AIR_UP22 = 33;
		public final static int AIR_DOWN22 = 34;
		public final static int AIR_UP23 = 35;
		public final static int AIR_DOWN23 = 36;
		public final static int AIR_UP24 = 37;
		public final static int AIR_DOWN24 = 38;
		public final static int AIR_UP25 = 39;
		public final static int AIR_DOWN25 = 40;
		public final static int AIR_UP26 = 41;
		public final static int AIR_DOWN26 = 42;
		public final static int AIR_UP27 = 43;
		public final static int AIR_DOWN27 = 44;
		public final static int AIR_UP28 = 45;
		public final static int AIR_DOWN28 = 46;
		public final static int AIR_UP29 = 47;
		public final static int AIR_DOWN29 = 48;
		public final static int AIR_UP30 = 49;
		public final static int AIR_DOWN30 = 50;
	}
}
