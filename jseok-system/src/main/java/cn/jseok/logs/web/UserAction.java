package cn.jseok.logs.web;

import cn.jseok.logs.bean.User;
import cn.jseok.logs.service.DefineUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

import static cn.jseok.security.JseokJwt.secretJwt;

@Controller
@RequestMapping("/user")

public class UserAction {

    @Autowired
    private DefineUserDetailsService defineUserDetailsService;


    public UserAction() {
        System.out.println("Bean 注入成功");
    }

    @GetMapping(value = "/login", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String Test(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {

        Map<String, String> map = new HashMap<>();
        map.put("username", "root");
        map.put("userid", "28302");
        String jwt = secretJwt(map);
        httpServletResponse.addHeader("Authorization", "Bearer " + jwt);
        User user = defineUserDetailsService.login();


        WebApplicationContext rootContext = ContextLoader.getCurrentWebApplicationContext();
        ConfigurableApplicationContext configurableContext = (ConfigurableApplicationContext) rootContext;

        int numContainers = 0;
        while (configurableContext != null) {
            numContainers++;
            configurableContext = (ConfigurableApplicationContext) configurableContext.getParent();
        }

        System.out.println("Number of Spring containers: " + numContainers);


        System.out.println("进入springMvc1");
        return user.getUsername();
    }

    @RequestMapping("/view1")
    public String requestMapping1() {
        return "redirect:/pages/redirecting.html";
    }

    @RequestMapping(value = "/admin", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String admin() {

        return "admin";
    }
}
