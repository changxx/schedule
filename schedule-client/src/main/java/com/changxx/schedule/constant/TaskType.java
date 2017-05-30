package com.changxx.schedule.constant;

/**
 * TaskType
 *
 * @author changxiangxiang
 * @date 2017/5/28
 */
public enum TaskType {

    ALL_ONE(1, "所有的机器上只有一台执行"),
    ALL(2, "所有机器都执行"),
    ALL_ASSIGN1(3, "指定机器上全部执行"),
    ASSIGN_ONE(4, "指定机器只有一台执行");

    TaskType(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public static TaskType getByCode(Integer code) {
        for (TaskType taskType : TaskType.values()) {
            if (taskType.getCode().equals(code)) {
                return taskType;
            }
        }
        return null;
    }


    private Integer code;

    private String desc;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
