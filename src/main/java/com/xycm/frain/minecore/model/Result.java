package com.xycm.frain.minecore.model;

/**
 * service 方法的统一返回值。
 * <p>
 * service 只产出结果（成功与否、业务数据、消息 key），
 * 把结果翻译成消息发给玩家是 controller 的职责。
 * <p>
 * messageKey 对应 messages/&lt;语言&gt;.yml 中的键名（如 "FlyEnabled"）；
 * placeholders 以「占位符, 值」成对传入（如 "{player}", "Steve"）。
 */
public final class Result {

    private final boolean success;
    private final Object data;
    private final String messageKey;
    private final String[] placeholders;

    private Result(boolean success, Object data, String messageKey, String[] placeholders) {
        this.success = success;
        this.data = data;
        this.messageKey = messageKey;
        this.placeholders = placeholders;
    }

    /** 成功，无数据、无需发消息。 */
    public static Result ok() {
        return new Result(true, null, null, new String[0]);
    }

    /** 成功，携带业务数据；messageKey 为要发给执行者的消息键，可为 null。 */
    public static Result ok(Object data, String messageKey, String... placeholders) {
        return new Result(true, data, messageKey, placeholders);
    }

    /** 失败，messageKey 为说明失败原因的消息键。 */
    public static Result fail(String messageKey, String... placeholders) {
        return new Result(false, null, messageKey, placeholders);
    }

    public boolean isSuccess() {
        return success;
    }

    /** 获取业务数据并转为指定类型。 */
    public <T> T getData(Class<T> type) {
        return type.cast(data);
    }

    public String getMessageKey() {
        return messageKey;
    }

    public String[] getPlaceholders() {
        return placeholders;
    }
}
