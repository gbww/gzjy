/**
 * @author xuewenlong@cmss.chinamobile.com
 * @updated 2017年12月6日
 */
package com.gzjy.client.model;

/**
 * @author xuewenlong@cmss.chinamobile.com
 * @updated 2017年12月6日
 */
public enum SchedulerRemind {
    REMIND_NONE(0,  "不提醒"), REMIND_PREDAY(1, "提前一天提醒"),REMIND_PRESIXHOUR(2, "提前6个小时提醒");

        private int code;

        private String name;

        private SchedulerRemind(int code, String name) {
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
