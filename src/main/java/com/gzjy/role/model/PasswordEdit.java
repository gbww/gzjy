/**
 * @author xuewenlong@cmss.chinamobile.com
 * @updated 2017年8月20日
 */
package com.gzjy.role.model;

/**
 * @author xuewenlong@cmss.chinamobile.com
 * @updated 2017年8月20日
 */
public class PasswordEdit {
    private String oldPassword;
    private String newPassword;
    private String username;
    private String password;
    
    public String getUsername() {
      return username;
    }
    public void setUsername(String username) {
      this.username = username;
    }
    public String getPassword() {
      return password;
    }
    public void setPassword(String password) {
      this.password = password;
    }
    public String getOldPassword() {
      return oldPassword;
    }
    public void setOldPassword(String oldPassword) {
      this.oldPassword = oldPassword;
    }
    public String getNewPassword() {
      return newPassword;
    }
    public void setNewPassword(String newPassword) {
      this.newPassword = newPassword;
    }
    public boolean eaquals(String oldPassword, String newPassword) {
      return oldPassword.equals(newPassword);
    }
}
