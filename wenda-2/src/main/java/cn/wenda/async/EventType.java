package cn.wenda.async;

public enum EventType {
	LIKE(0),
	COMMENT(1),
	LOGIN(2),
	EMAIL(3);
	
	private int value;
	EventType(int value) {
		this.value=value;
	}
	public int getValue() {
		return value;
	}
	public void setValue(int value) {
		this.value = value;
	}
	

}
