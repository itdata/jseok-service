package cn.jseok.spring;

import net.bytebuddy.implementation.FieldAccessor;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class JseokLog {
    private final Log logger = LogFactory.getLog(getClass());

    public void write(){
        logger.info("info*************");
        logger.debug("info*************");
        logger.error("info*************");
        System.out.println("进入");
    }

}
