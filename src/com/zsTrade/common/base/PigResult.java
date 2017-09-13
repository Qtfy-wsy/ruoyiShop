package com.zsTrade.common.base;

import java.util.List;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * 自定义响应结构
 */
public class PigResult {

    // 定义jackson对象
    private static final ObjectMapper MAPPER = new ObjectMapper();
    private String id;
    // 响应业务状态
    private Integer status;

    // 响应消息
    private String msg;

    // 响应中的数据
    private Object data;

    public static PigResult build(Integer status, String msg, Object data) {
        return new PigResult(status, msg, data);
    }
    public static PigResult build(String id,Integer status, String msg, Object data) {
        return new PigResult(id,status, msg, data);
    }
    public static PigResult ok(Object data) {
        return new PigResult(data);
    }

    public static PigResult ok() {
        return new PigResult(null);
    }

    public PigResult() {

    }

    public static PigResult build(Integer status, String msg) {
        return new PigResult(status, msg, null);
    }

    public PigResult(Integer status, String msg, Object data) {
        this.status = status;
        this.msg = msg;
        this.data = data;
    }
    public PigResult(String id,Integer status, String msg, Object data) {
        this.status = status;
        this.msg = msg;
        this.data = data;
        this.id=id;
    }
    public PigResult(Object data) {
        this.status = 200;
        this.msg = "OK";
        this.data = data;
    }

//    public Boolean isOK() {
//        return this.status == 200;
//    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    /**
     * 将json结果集转化为PigResult对象
     * 
     * @param jsonData json数据
     * @param clazz PigResult中的object类型
     * @return
     */
    public static PigResult formatToPojo(String jsonData, Class<?> clazz) {
        try {
            if (clazz == null) {
                return MAPPER.readValue(jsonData, PigResult.class);
            }
            JsonNode jsonNode = MAPPER.readTree(jsonData);
            JsonNode data = jsonNode.get("data");
            Object obj = null;
            if (clazz != null) {
                if (data.isObject()) {
                    obj = MAPPER.readValue(data.traverse(), clazz);
                } else if (data.isTextual()) {
                    obj = MAPPER.readValue(data.asText(), clazz);
                }
            }
            return build(jsonNode.get("status").intValue(), jsonNode.get("msg").asText(), obj);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 没有object对象的转化
     * 
     * @param json
     * @return
     */
    public static PigResult format(String json) {
        try {
            return MAPPER.readValue(json, PigResult.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Object是集合转化
     * 
     * @param jsonData json数据
     * @param clazz 集合中的类型
     * @return
     */
    public static PigResult formatToList(String jsonData, Class<?> clazz) {
        try {
            JsonNode jsonNode = MAPPER.readTree(jsonData);
            JsonNode data = jsonNode.get("data");
            Object obj = null;
            if (data.isArray() && data.size() > 0) {
                obj = MAPPER.readValue(data.traverse(),
                        MAPPER.getTypeFactory().constructCollectionType(List.class, clazz));
            }
            return build(jsonNode.get("status").intValue(), jsonNode.get("msg").asText(), obj);
        } catch (Exception e) {
            return null;
        }
    }

	@Override
	public String toString() {
		return "PigResult [status=" + status + ", msg=" + msg + ", data="
				+ data + "]";
	}

    
}
