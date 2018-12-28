package cn.wenda.async;

import java.util.HashMap;
import java.util.Map;

public class EventModel {
	
	public EventModel() {
		super();
	}

	private EventType type;
	//事件的触发者
	private int actorId;
	private int entityType;
	//事件所对应的实体id
	private int entityId;
	//事件应该发给谁
	private int entityOwnerId;
	
	private Map<String,String> exts=new HashMap<>();
	public EventModel(EventType type) {
		this.type=type;
	}
	
	@Override
	public String toString() {
		return "EventModel [type=" + type + ", actorId=" + actorId + ", entityType=" + entityType + ", entityId="
				+ entityId + ", entityOwnerId=" + entityOwnerId + ", exts=" + exts + "]";
	}

	public EventModel setExts(String key,String value) {
		exts.put(key, value);
		return this;
	}
	public String getExts(String key) {
		return exts.get(key);
	}

	public EventType getType() {
		return type;
	}

	public EventModel setType(EventType type) {
		this.type = type;
		return this;
	}

	public int getActorId() {
		return actorId;
	}

	public EventModel setActorId(int actorId) {
		this.actorId = actorId;
		return this;
	}

	public Map<String, String> getExts() {
		return exts;
	}

	public void setExts(Map<String, String> exts) {
		this.exts = exts;
	}

	public int getEntityType() {
		return entityType;
	}

	public EventModel setEntityType(int entityType) {
		this.entityType = entityType;
		return this;
	}

	public int getEntityId() {
		return entityId;
	}

	public EventModel setEntityId(int entityId) {
		this.entityId = entityId;
		return this;
	}

	public int getEntityOwnerId() {
		return entityOwnerId;
	}

	public EventModel setEntityOwnerId(int entityOwnerId) {
		this.entityOwnerId = entityOwnerId;
		return this;
	}


}
