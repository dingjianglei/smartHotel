package java.post.hotel;

import java.io.EOFException;
import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;


public class OrderInputStream extends FilterInputStream {

	protected OrderInputStream(InputStream in) {
		super(in);
		// TODO Auto-generated constructor stub
	}

	/**
	 * �ӵ�ǰ λ�ô���ȡlength���ֽ�
	 * 
	 * @param length
	 * @return
	 * @throws java.io.IOException
	 */
	public final byte[] read(int length) throws IOException {
		byte[] data = new byte[length];
		in.read(data, 0, length);
		return data;
	}

	public final int read(byte b[]) throws IOException {
		return in.read(b, 0, b.length);
	}

	public final int read(byte b[], int off, int len) throws IOException {
		return in.read(b, off, len);
	}

	public final void readFully(byte b[]) throws IOException {
		readFully(b, 0, b.length);
	}

	public final void readFully(byte b[], int off, int len) throws IOException {
		if (len < 0)
			throw new IndexOutOfBoundsException();
		int n = 0;
		while (n < len) {
			int count = in.read(b, off + n, len - n);
			if (count < 0)
				throw new EOFException();
			n += count;
		}
	}

	public final int skipBytes(int n) throws IOException {
		int total = 0;
		int cur = 0;
		while ((total < n) && ((cur = (int) in.skip(n - total)) > 0)) {
			total += cur;
		}
		return total;
	}

	public final byte readByte() throws IOException {
		int ch = in.read();
		if (ch < 0)
			throw new EOFException();
		return (byte) (ch);
	}

	public final int readUnsignedByte() throws IOException {
		int ch = in.read();
		if (ch < 0)
			throw new EOFException();
		return ch;
	}

	public final short readShort() throws IOException {
		int ch2 = in.read();
		int ch1 = in.read();
		if ((ch1 | ch2) < 0)
			throw new EOFException();
		return (short) ((ch2 << 8) + (ch1 << 0));
	}

	public final int readUnsignedShort() throws IOException {
		int ch2 = in.read();
		int ch1 = in.read();
		if ((ch1 | ch2) < 0)
			throw new EOFException();
		return (ch2 << 8) + (ch1 << 0);
	}

	public final char readChar() throws IOException {
		int ch2 = in.read();
		int ch1 = in.read();
		if ((ch1 | ch2) < 0)
			throw new EOFException();
		return (char) ((ch2 << 8) + (ch1 << 0));
	}

	public final int readInt() throws IOException {
		int ch4 = in.read();
		int ch3 = in.read();
		int ch2 = in.read();
		int ch1 = in.read();
		if ((ch1 | ch2 | ch3 | ch4) < 0)
			throw new EOFException();
		return ((ch4 << 24) + (ch3 << 16) + (ch2 << 8) + (ch1 << 0));
	}

	/**
	 * 2���ֽڵ�int������
	 * 
	 * @return
	 * @throws java.io.IOException
	 */
	public final int readInt2() throws IOException {
		byte[] data=this.read(2);
		return Utils.getLength(data);
//		int ch2 = in.read();
//		int ch1 = in.read();
//		if ((ch1 | ch2) < 0)
//			throw new EOFException();
//		return ((ch2 << 8) + (ch1 << 0));
	}

	private byte readBuffer[] = new byte[8];

	public final long readLong() throws IOException {
		readFully(readBuffer, 0, 8);
		return (((long) readBuffer[0] << 56)
				+ ((long) (readBuffer[1] & 255) << 48)
				+ ((long) (readBuffer[2] & 255) << 40)
				+ ((long) (readBuffer[3] & 255) << 32)
				+ ((long) (readBuffer[4] & 255) << 24)
				+ ((readBuffer[5] & 255) << 16) + ((readBuffer[6] & 255) << 8) + ((readBuffer[7] & 255) << 0));
	}

	public final float readFloat() throws IOException {
		return Float.intBitsToFloat(readInt());
	}

	public final double readDouble() throws IOException {
		return Double.longBitsToDouble(readLong());
	}

}
