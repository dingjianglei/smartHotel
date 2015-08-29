package java.post.hotel;

import java.io.Serializable;

public class DeviceVo implements Serializable {

	/**
	 * @return the ieee
	 */
	public String getIeee() {
		return ieee;
	}

	/**
	 * @param ieee the ieee to set
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
	 * @param port the port to set
	 */
	public void setPort(int port) {
		this.port = port;
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String name;
	private int id;
	private String ieee;
	private int port;
	private int type;
	private boolean power;

	public boolean isPower() {
		return power;
	}

	public void setPower(boolean power) {
		this.power = power;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

}