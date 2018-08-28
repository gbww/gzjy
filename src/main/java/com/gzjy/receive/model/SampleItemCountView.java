/**
 * @author xuewenlong@cmss.chinamobile.com
 * @updated 2017年12月14日
 */
package com.gzjy.receive.model;

import java.util.Date;

/**
 * @author xuewenlong@cmss.chinamobile.com
 * @updated 2017年12月14日
 */
public class SampleItemCountView {
    private Integer itemCount;
    private String testRoom;
    private Integer status;
    private Date updatedAt;
    
    
    public Integer getItemCount() {
        return itemCount;
    }
    public void setItemCount(Integer itemCount) {
        this.itemCount = itemCount;
    }
    public String getTestRoom() {
        return testRoom;
    }
    public void setTestRoom(String testRoom) {
        this.testRoom = testRoom;
    }
    public Integer getStatus() {
        return status;
    }
    public void setStatus(Integer status) {
        this.status = status;
    }
    public Date getUpdatedAt() {
        return updatedAt;
    }
    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }
    

}
