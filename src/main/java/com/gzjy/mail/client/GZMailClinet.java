/**
 * @author xuewenlong@cmss.chinamobile.com
 * @updated 2017年12月12日
 */
package com.gzjy.mail.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.gzjy.mail.service.MailService;

/**
 * @author xuewenlong@cmss.chinamobile.com
 * @updated 2017年12月12日
 */
@Component
public class GZMailClinet {
    @Autowired
    MailService mailService;
    

}
