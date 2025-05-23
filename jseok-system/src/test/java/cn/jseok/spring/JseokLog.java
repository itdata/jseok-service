package cn.jseok.spring;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class JseokLog {
    private final Log logger = LogFactory.getLog(getClass());

    public void write(){
        logger.info("info*************");
        logger.debug("info*************");
        logger.error("info*************");
        System.out.println("进入");
    }

}
