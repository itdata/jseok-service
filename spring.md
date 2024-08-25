# <center>Spring Framework</center>

## 一、Spring IoC Container and Beans

***

### 1、container和bean的核心包


ClassPathXmlApplicationContext  
FileSystemXmlApplicationContext  
GroovyWebApplicationContext  
AnnotationConfigWebApplicationContext  
StaticWebApplicationContext  
XmlWebApplicationContext
1. org.springframework.beans
2. org.springframework.context
3. ApplicationContext是BeanFactory子接口
4. ClassPathXmlApplicationContext
5. FileSystemXmlApplicationContext

### 2、关于注解

- @Configuration
- @Bean
- @Import
- @DependsOn

### 3、Configuration Metadata 基于XML配置

```<?xml version="1.0" encoding="UTF-8"?>
    <beans xmlns="http://www.springframework.org/schema/beans"
    xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
    xsi:schemaLocation="http://www.springframework.org/schema/beans
    https://www.springframework.org/schema/beans/spring-beans.xsd">
    <bean id="..." class="...">
      <!-- collaborators and configuration for this bean go here -->
      </bean>
      <bean id="..." class="...">
      <!-- collaborators and configuration for this bean go here -->
      </bean>
      <!-- more bean definitions go here -->
    </beans>
```

### 4、Instantiating a Container初始化容器

1. <span id="index">从本地文件系统或java类路径等加载配置元数据。</span>

```
ApplicationContext context = new ClassPathXmlApplicationContext("services.xml","daos.xml");
```

```
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
  xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
  xsi:schemaLocation="http://www.springframework.org/schema/beans
  https://www.springframework.org/schema/beans/spring-beans.xsd">
  <!-- services -->
  <bean id="petStore" class="org.springframework.samples.jpetstore.services.PetStoreServiceImpl">
      <property name="accountDao" ref="accountDao"/>
      <property name="itemDao" ref="itemDao"/>
      <!-- additional collaborators and configuration for this bean go here -->
  </bean>
  <!-- more bean definitions for services go here -->
</beans>
```

```
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
  xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
  xsi:schemaLocation="http://www.springframework.org/schema/beans
  https://www.springframework.org/schema/beans/spring-beans.xsd">
  <bean id="accountDao" class="org.springframework.samples.jpetstore.dao.jpa.JpaAccountDao">
        <!-- additional collaborators and configuration for this bean go here -->
  </bean>
  <bean id="itemDao" class="org.springframework.samples.jpetstore.dao.jpa.JpaItemDao">
        <!-- additional collaborators and configuration for this bean go here -->
  </bean>
  <!-- more bean definitions for data access objects go here -->
</beans>
```

2. 基于xml的配置，使用import或者在[构造函数](#index)中引入多个xml

```
<beans>
  <import resource="services.xml"/>
  <import resource="resources/messageSource.xml"/>
  <import resource="/resources/themeSource.xml"/>
  <bean id="bean1" class="..."/>
  <bean id="bean2" class="..."/>
</beans>
```

3. Using the Containers使用容器

```
// create and configure beans
ApplicationContext context = new ClassPathXmlApplicationContext("services.xml",
"daos.xml");
// retrieve configured instance
PetStoreService service = context.getBean("petStore", PetStoreService.class);
// use configured instance
List<String> userList = service.getUsernameList();
```

4. GenericApplicationContext和XmlBeanDefinitionReader

```
GenericApplicationContext context = new GenericApplicationContext();
new XmlBeanDefinitionReader(context).loadBeanDefinitions("services.xml", "daos.xml");
context.refresh();
```

### 5、Bean概述

1. 在容器内Bean定义表现为BeanDefinition对象。
    - 一个限定包的类名，Bean定义的实际实现类。
    - Bean的行为配置元素，包含作用域、生命回调周期等。
    - 定义Bean的其它引用，Bean的协作对象或依赖。
    - 用于设置新创建的对象。

   | 属性                       | 描述          |
         |--------------------------|-------------|
   | Class                    | 实例化Bean     |
   | Name                     | 命名Bean      |
   | Scope                    | Bean的作用域    |
   | Constructor arguments    | 构造方法依赖注入    |
   | Properties               | 属性成员依赖注入    |
   | Autowiring mode          | 自动装配模式      |
   | Lazy initialization mode | 延迟实例化Bean   |
   | Initialization method    | 初始化回调方法     |
   | Destruction method       | Bean销毁时回调方法 |
2. Naming Bean

- 可以使用id或name属性指定Bean标识符，name的值可用, ; 空格指定别名。您可以通过使用 id 属性指定的最多一个名称和 name
  属性中任意数量的其他名称的组合来为 Bean 提供多个名称。
- 定义Bean时不用指定id或name，但是要通过ref引用Bean，则必须提供一个名称。
- 在同一个容器中为定义好的Bean提供一个alias。

```
<alias name="fromName" alias="toName"/>
```

- 为嵌套类定义Bean使用（$)或（.）com.example.SomeThing$OtherThing or com.example.SomeThing.OtherThing.
- 通过构造方法实例化Bean，每个javaBean都有一个默认构造方法。

```
<bean id="exampleBean" class="examples.ExampleBean"/>
<bean name="anotherExample" class="examples.ExampleBeanTwo"/>
```

- 通过静态工厂方法实例化Bean

```
<bean id="clientService" class="examples.ClientService" factory-method="createInstance"/>

public class ClientService {
    private static ClientService clientService = new ClientService();
    private ClientService() {}
    public static ClientService createInstance() {
        return clientService;
    }
}
```

- 通过实例化工厂方法实例化Bean

```
<!-- the factory bean, which contains a method called createInstance() -->
<bean id="serviceLocator" class="examples.DefaultServiceLocator">
<!-- inject any dependencies required by this locator bean -->
</bean>
<!-- the bean to be created via the factory bean -->
<bean id="clientService"
factory-bean="serviceLocator"
factory-method="createClientServiceInstance"/>

public class DefaultServiceLocator {
    private static ClientService clientService = new ClientServiceImpl();
    public ClientService createClientServiceInstance() {
    return clientService;
    }
}
```

```
<bean id="serviceLocator" class="examples.DefaultServiceLocator">
<!-- inject any dependencies required by this locator bean -->
</bean>
<bean id="clientService" factory-bean="serviceLocator" 
factory-method="createClientServiceInstance"/>
<bean id="accountService" factory-bean="serviceLocator"
factory-method="createAccountServiceInstance"/>

public class DefaultServiceLocator {
    private static ClientService clientService = new ClientServiceImpl();
    private static AccountService accountService = new AccountServiceImpl();
    public ClientService createClientServiceInstance() {
         return clientService;
    }
    public AccountService createAccountServiceInstance() {
         return accountService;
    }
}
```   

3. 依赖注入

- 基于构造方法参数

基于构造函数的 DI 是通过容器调用带有多个参数的构造函数来实现的，每个参数代表一个依赖项。

```
public class SimpleMovieLister {
    // the SimpleMovieLister has a dependency on a MovieFinder
    private final MovieFinder movieFinder;
    // a constructor so that the Spring container can inject a MovieFinder
    public SimpleMovieLister(MovieFinder movieFinder) {
        this.movieFinder = movieFinder;
    }
    // business logic that actually uses the injected MovieFinder is omitted...
}
```

```
<beans>
<bean id="beanOne" class="x.y.ThingOne">
    <constructor-arg ref="beanTwo"/>
    <constructor-arg ref="beanThree"/>
</bean>
<bean id="beanTwo" class="x.y.ThingTwo"/>
<bean id="beanThree" class="x.y.ThingThree"/>
</beans>

public class ThingOne {
    public ThingOne(ThingTwo thingTwo, ThingThree thingThree) {
    // ...
    }
}

```

上述构造参数引用Bean时类型是已知的，可以发生正确匹配。但是使用基本类型时无法按类型进行匹配。
可以根据参数类型，参数索引，参数名称

```
<bean id="exampleBean" class="examples.ExampleBean">
  <constructor-arg type="int" value="7500000"/>
  <constructor-arg type="java.lang.String" value="42"/>
</bean>

<bean id="exampleBean" class="examples.ExampleBean">
  <constructor-arg index="0" value="7500000"/>
  <constructor-arg index="1" value="42"/>
</bean>

<bean id="exampleBean" class="examples.ExampleBean">
  <constructor-arg name="years" value="7500000"/>
  <constructor-arg name="ultimateAnswer" value="42"/>
</bean>

```

要做到开箱既用，取决于是否开启debug标志编译代码。可以使用@ConstructorProperties

```
public class ExampleBean {
  // Fields omitted
  @ConstructorProperties({"years", "ultimateAnswer"})
  public ExampleBean(int years, String ultimateAnswer) {
     this.years = years;
     this.ultimateAnswer = ultimateAnswer;
  }
}

```

- 基于setter的依赖注入

基于 Setter 的 DI 是通过容器在调用无参数构造函数或无参数静态工厂方法来实例化 Bean 后在 Bean 上调用 setter 方法来完成的。

```
public class SimpleMovieLister {
  // the SimpleMovieLister has a dependency on the MovieFinder
  private MovieFinder movieFinder;
  // a setter method so that the Spring container can inject a MovieFinder
  public void setMovieFinder(MovieFinder movieFinder) {
      this.movieFinder = movieFinder;
  }
  // business logic that actually uses the injected MovieFinder is omitted...
}
```

可以混合使用基于构造函数的依赖和基于setter的依赖   
使用@Required注解可用于属性称为必须得依赖

- 基于工厂方法参数

```
<bean id="exampleBean" class="examples.ExampleBean" factory-method="createInstance">
  <constructor-arg ref="anotherExampleBean"/>
  <constructor-arg ref="yetAnotherBean"/>
  <constructor-arg value="1"/>
</bean>
<bean id="anotherExampleBean" class="examples.AnotherBean"/>
<bean id="yetAnotherBean" class="examples.YetAnotherBean"/>
```

```
public class ExampleBean {
  // a private constructor
  private ExampleBean(...) {
  ...
  }
  // a static factory method; the arguments to this method can be
  // considered the dependencies of the bean that is returned,
  // regardless of how those arguments are actually used.
  public static ExampleBean createInstance (
  AnotherBean anotherBean, YetAnotherBean yetAnotherBean, int i) {
  ExampleBean eb = new ExampleBean (...);
  // some other operations...
  return eb;
  }
}

```

- 依赖注入解析过程
- 依赖关系和配置详情

字面量使用<property>

```
<bean id="myDataSource" class="org.apache.commons.dbcp.BasicDataSource" destroymethod="close">
  <!-- results in a setDriverClassName(String) call -->
  <property name="driverClassName" value="com.mysql.jdbc.Driver"/>
  <property name="url" value="jdbc:mysql://localhost:3306/mydb"/>
  <property name="username" value="root"/>
  <property name="password" value="misterkaoli"/>
</bean>
```

p-namespace

```
<beans xmlns="http://www.springframework.org/schema/beans"
  xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
  xmlns:p="http://www.springframework.org/schema/p"
  xsi:schemaLocation="http://www.springframework.org/schema/beans
  https://www.springframework.org/schema/beans/spring-beans.xsd">
  <bean id="myDataSource" class="org.apache.commons.dbcp.BasicDataSource"
  destroy-method="close"
  p:driverClassName="com.mysql.jdbc.Driver"
  p:url="jdbc:mysql://localhost:3306/mydb"
  p:username="root"
  p:password="misterkaoli"/>
</beans>
```

PropertySourcesPlaceholderConfigurer, java.util.Properties占位符配置。

```
<bean id="mappings"
  class="org.springframework.context.support.PropertySourcesPlaceholderConfigurer">
  <!-- typed as a java.util.Properties -->
  <property name="properties">
  <value>
  jdbc.driver.className=com.mysql.jdbc.Driver
  jdbc.url=jdbc:mysql://localhost:3306/mydb
  </value>
  </property>
</bean>

```

idref是一种防错误的方法。以下两种完全等效，第一种形式比第二种形式更可取，因为使用 idref 标记可以让容器在部署时验证引用的命名
Bean 是否确实存在

```
<bean id="theTargetBean" class="..."/>
<bean id="theClientBean" class="...">
  <property name="targetName">
  <idref bean="theTargetBean"/>
  </property>
</bean>

<bean id="theTargetBean" class="..." />
<bean id="client" class="...">
  <property name="targetName" value="theTargetBean"/>
</bean>

```

- 对其他Bean的引用

```
<!-- in the parent context -->
<bean id="accountService" class="com.something.SimpleAccountService">
  <!-- insert dependencies as required here -->
</bean>

<!-- in the child (descendant) context -->
<bean id="accountService" <!-- bean name is the same as the parent bean -->
  class="org.springframework.aop.framework.ProxyFactoryBean">
  <property name="target">
  <ref parent="accountService"/> <!-- notice how we refer to the parent bean -->
  </property>
  <!-- insert other configuration and dependencies as required here -->
</bean>
```

- Inner Beans

```
<bean id="outer" class="...">
  <!-- instead of using a reference to a target bean, simply define the target bean
inline -->
  <property name="target">
  <bean class="com.example.Person"> <!-- this is the inner bean -->
  <property name="name" value="Fiona Apple"/>
  <property name="age" value="25"/>
  </bean>
  </property>
</bean>
```

- Collections依赖配置

```
<bean id="moreComplexObject" class="example.ComplexObject">
  <!-- results in a setAdminEmails(java.util.Properties) call -->
  <property name="adminEmails">
  <props>
  <prop key="administrator">administrator@example.org</prop>
  <prop key="support">support@example.org</prop>
  <prop key="development">development@example.org</prop>
  </props>
  </property>
  <!-- results in a setSomeList(java.util.List) call -->
  <property name="someList">
  <list>
  <value>a list element followed by a reference</value>
  <ref bean="myDataSource" />
  </list>
  </property>
  <!-- results in a setSomeMap(java.util.Map) call -->
  <property name="someMap">
  <map>
  <entry key="an entry" value="just some string"/>
  <entry key="a ref" value-ref="myDataSource"/>
  </map>
  </property>
  <!-- results in a setSomeSet(java.util.Set) call -->
  <property name="someSet">
  <set>
  <value>just some string</value>
  <ref bean="myDataSource" />
  </set>
  </property>
</bean>
```

- 使用depends-on,显式和强制在初始化使用此元素前初始depends-on的Bean。

```
<bean id="beanOne" class="ExampleBean" depends-on="manager,accountDao">
  <property name="manager" ref="manager" />
</bean>
<bean id="manager" class="ManagerBean" />
<bean id="accountDao" class="x.y.jdbc.JdbcAccountDao" />
```

- 使用lazy-init延迟加载。在首次使用Bean时创建，而非初始化容器时创建。

注意：但是，当延迟初始化的 Bean 是未延迟初始化的单例 Bean 的依赖项时，
ApplicationContext 会在启动时创建延迟初始化的 bean，
因为它必须满足单例的依赖项。惰性初始化的 Bean 被注入到其他未延迟初始化的单例 Bean 中。

```
<bean id="lazy" class="com.something.ExpensiveToCreateBean" lazy-init="true"/>
<bean name="not.lazy" class="com.something.AnotherBean"/>
```

容器级别的延迟加载Bean

```
<beans default-lazy-init="true">
  <!-- no beans will be pre-instantiated... -->
</beans>
```

- 自动装配协作者

自动装配模式，no,byName,byType,constructor。

如果方法是抽象的，动态生成子类实现了这个方法，因此，子类方法重载原始类中定义的方法。

关于@Lookup

```
<!-- a stateful bean deployed as a prototype (non-singleton) -->
<bean id="myCommand" class="fiona.apple.AsyncCommand" scope="prototype">
  <!-- inject dependencies here as required -->
</bean>
<!-- commandProcessor uses statefulCommandHelper -->
<bean id="commandManager" class="fiona.apple.CommandManager">
  <lookup-method name="createCommand" bean="myCommand"/>
</bean>
```

- replaced-method的使用

```
<bean id="myValueCalculator" class="x.y.z.MyValueCalculator">
  <!-- arbitrary method replacement -->
  <replaced-method name="computeValue" replacer="replacementComputeValue">
  <arg-type>String</arg-type>
  </replaced-method>
</bean>
<bean id="replacementComputeValue" class="a.b.c.ReplacementComputeValue"/>
```

4. Bean的作用域

- IoC容器为Bean定义创建一个实例对象。
- IoC容器为Bean定义创建多个实例对象。
- IoC容器为每个HTTP请求创建一个实例对象。request生命周期
- IoC容器为每个HTTP session创建一个实例对象。session生命周期
- IoC容器为每个ServletContext创建一个实例对象。ServletContext生命周期
- 当将prototype作用域Bean注入singleton作用域Bean，原型Bean的实例是提供给单例作用域Bean的唯一实例。
  因为IoC容器只实例化singleton Bean一次。要想在每次运行时注入原型Bean的新实例，则使用方法注入或直接从容器返回需要的Bean（getBean）
- 关于Bean作用域的注解配置。
    * @RequestScope
    * @SessionScope
    * @ApplicationScope
    * @Scope(value = BeanDefinition.SCOPE_SINGLETON)
- 了解自定义作用域接口 org.springframework.beans.factory.config.Scope

5. 自定义bean的特征

- Spring框架包含了许多接口用于自定义Bean的性质
    * 生命周期回调
    * ApplicationContextAware and BeanNameAware
    * 其它一些Aware接口
- 关于生命周期回调接口InitializingBean and DisposableBean interfaces
- 初始化回调和预销毁回调 @PostConstruct @PreDestroy

```
<bean id="exampleInitBean" class="examples.ExampleBean" init-method="init"/>
<bean id="exampleInitBean" class="examples.ExampleBean" destroy-method="cleanup"/>
```

- 在容器级别默认的初始化回调和预销毁回调

```
<beans default-init-method="init">
  <bean id="blogService" class="com.something.DefaultBlogService">
  <property name="blogDao" ref="blogDao" />
  </bean>
</beans>

<beans default-destroy-method="init">
  <bean id="blogService" class="com.something.DefaultBlogService">
  <property name="blogDao" ref="blogDao" />
  </bean>
</beans>
```

- 调用顺序，1、方法注解。2、接口回调方法。3、自定义配置方法。
- 优雅的开启和关闭Bean回调以及关闭容器。  
  接口Lifecycle、ifecycleProcessor、SmartLifecycle、Phased、DefaultLifecycleProcessor、ConfigurableApplicationContext
- 让Bean感知容器环境，使用Aware接口家族。

5. Bean定义继承,父Bean不能单独实例化，所以不能通过ref或者getBean()调用。

```
<bean id="inheritedTestBean" abstract="true"
  class="org.springframework.beans.TestBean">
  <property name="name" value="parent"/>
  <property name="age" value="1"/>
</bean>
<bean id="inheritsWithDifferentClass"
  class="org.springframework.beans.DerivedTestBean"
  parent="inheritedTestBean" init-method="initialize"> ①
  <property name="name" value="override"/>
  <!-- the age property value of 1 will be inherited from parent -->
</bean>
```

6. 容器扩展点，集成接口。
- BeanPostProcessor和Ordered接口。BeanFactoryPostProcesseor和Ordered接口
- 关于属性占位符
```
<bean
class="org.springframework.context.support.PropertySourcesPlaceholderConfigurer">
  <property name="locations" value="classpath:com/something/jdbc.properties"/>
</bean>
<bean id="dataSource" destroy-method="close"
  class="org.apache.commons.dbcp.BasicDataSource">
  <property name="driverClassName" value="${jdbc.driverClassName}"/>
  <property name="url" value="${jdbc.url}"/>
  <property name="username" value="${jdbc.username}"/>
  <property name="password" value="${jdbc.password}"/>
</bean>

<context:property-placeholder location="classpath:com/something/jdbc.properties"/>
```
- 查看容器实际的FactoryBean，而不是生产的Bean。使用getBean("id"),返回FactoryBean生产的Bean，而getBean("&id"),返回FactoryBean实例对象本身。
7. 基于Java注解的容器配置
- <context:annotation-config/> 隐式的注册了以下处理器。开启注解配置以使用Java注解。
  * ConfigurationClassPostProcessor
  * AutowiredAnnotationBeanPostProcessor
  * CommonAnnotationBeanPostProcessor
  * PersistenceAnnotationBeanPostProcessor
  * EventListenerMethodProcessor
- @Required 注解和RequiredAnnotationBeanPostProcessor已经被弃用。应该使用构造方法或@PostConstruct方法
- @Autowired 能使用@Autowired的地方能被@Inject代替。   
可在构造函数、setter方法、任意方法、字段、数组集合容器上使用  
如果只有一个构造函数则不需要使用该注解，如果有多个构造函数必须指示容器使用那个函数。  
当没有匹配类型则自动装配失败，数组容器至少应该有一个匹配的元素。使用@Autowired(required = false)使容器跳过不满足的注入点。  
当有多个构造函数时，一个构造函数声明@Autowired(required = true),则其余的构造函数必须声明为@Autowired(required = false).
- 使用Java8的新特性和@Nullable,非必须注入依赖。
```
public class SimpleMovieLister {
  @Autowired
  public void setMovieFinder(Optional<MovieFinder> movieFinder) {
  ...
  }
}

public class SimpleMovieLister {
  @Autowired
  public void setMovieFinder(@Nullable MovieFinder movieFinder) {
  ...
  }
}

```
- @Autowired可用于BeanFactory, ApplicationContext, Environment, ResourceLoader, ApplicationEventPublisher, and
  MessageSource.解析依赖的接口或他们的扩展接口。
- 不能将@Autowired用于BeanPostProcessor或BeanFactoryPostProcessor类型
8. 