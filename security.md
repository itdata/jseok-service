# <center>Security</center>

## 1.ServletContext加载顺序
ServletContext -> listener -> filter -> servlet
## 2.DelegatingFilterProxy   
spring提供的一个Filter实现   
一个委托代理Filter用于适配Filter和FilterChain
## 3.FilterChainProxy
FilterChainProxy是一个特殊的过滤器，它允许通过SecurityFilterChain委托给多个过滤器实例。   
FilterChainProxy 使用 SecurityFilterChain 来确定应为当前请求调用哪些 Spring Security Filter实例   
Security Filters 在SecurityFilterChain中是一个Bean，但是是注册在FilterChainProxy中而不是注册在DelegatingFilterProxy中。    
将调式点设置在FilterChainProxy中是一个好的选择。
## 4.SecurityFilterChain
HttpFirewall用于提供对应用程序的免受一些攻击。   
FilterOrderRegistration注意SecurityFilterChain调用顺序。
## 5.FilterRegistrationBean
使用FilterRegistrationBean避免在spring中注册导致Filter调用两次。