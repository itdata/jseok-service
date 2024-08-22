package cn.jseok.config;


import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
@ComponentScan(basePackages = "cn.jseok.logs.web")
public class WebMvcConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {

        //spring mvc 全局配置跨域
        registry.addMapping("/**").allowedOrigins("http://localhost:3000").allowedMethods("*").allowedHeaders("*").allowCredentials(true);

    }
}
