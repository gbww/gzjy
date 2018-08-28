/**
 * @author xuewenlong@cmss.chinamobile.com
 * @updated 2017年12月6日
 */
package com.gzjy.client.model;

/**
 * @author xuewenlong@cmss.chinamobile.com
 * @updated 2017年12月6日
 */
public enum SchedulerRepetition {
    NONE(0, "无"), EVERYDAY(1, "每天"), EVERYWEEK(2, "每周"), EVERYMONTH(3, "每月");

    private int code;

    private String name;

    private SchedulerRepetition(int code, String name) {
        this.code = code;
        this.name = name;

    }

    public int getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCode(int code) {
        this.code = code;
    }

}
