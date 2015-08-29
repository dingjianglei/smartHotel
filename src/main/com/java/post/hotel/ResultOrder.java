package java.post.hotel;

import java.io.Serializable;

public class ResultOrder implements Serializable {
	/**
	 * @return the ieee
	 */
	public String getIeee() {
		return ieee;
	}

	/**
	 * @param ieee
	 *            the ieee to set
	 */
	public void setIeee(String ieee) {
		this.ieee = ieee;
	}

	/**
	 * @return the port
	 */
	public int getPort() {
		return port;
	}

	/**
	 * @param port
	 *            the port to set
	 */
	public void setPort(int port) {
		this.port = port;
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private int result;
	private int d0=-1;
	private int d1=-1;
	private boolean power;

	public boolean isPower() {
		return power;
	}

	public void setPower(boolean power) {
		this.power = power;
	}

	public int getResult() {
		return result;
	}

	public void setResult(int result) {
		this.result = result;
	}

	private String ieee;
	private int port;

	public int getD0() {
		return d0;
	}

	public void setD0(int d0) {
		this.d0 = d0;
	}

	public int getD1() {
		return d1;
	}

	public void setD1(int d1) {
		this.d1 = d1;
	}
}