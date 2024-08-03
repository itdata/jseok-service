package cn.jseok.spring;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
//@Bean ,@Controller,@Service,@Repository,@Component
@Component(value = "testLog")
public class TestLog {

    @Autowired
    private JseokLog jseokLog;


    @PostConstruct
    public void populateMovieCache() {
        System.out.println("populates the movie cache upon initialization...");
    }

    @PreDestroy
    public void clearMovieCache() {
        System.out.println("clears the movie cache upon destruction...");
    }
    public void write(){
        jseokLog.write();
    }

}
