# 关于spring通过注解将配置项绑定到bean的属性

spring提供了一套机制,开发者可以基于注解声明,让spring将配置项的值设置为其管理的bean对象属性值.
其中"设置"这个动作在spring框架中称为binding.
配置项的值来自于外置的配置(比如文件/环境变量等).


## @ConfigurationProperties

Annotation for externalized configuration.
Add this to a class definition or a @Bean method in a @Configuration class if you want to bind and validate some external Properties (e.g. from a .properties file).
Binding is either performed by calling setters on the annotated class or, if @ConstructorBinding is in use, by binding to the constructor parameters.
Note that contrary to @Value, SpEL expressions are not evaluated since property values are externalized.
注意该注解不支持SpEL expressions.

@ConfigurationProperties注解只是声明了spring在初始化bean时,spring需要将externalized configuration值绑定到bean的属性上.
如果@ConfigurationProperties注解作用于@Bean method上,因为@Bean注解的存在,spring需要负责管理该bean的实例化和初始化.
如果@ConfigurationProperties注解作用于@Configuration class上,因为@Configuration注解的存在,spring需要负责管理该bean的实例化和初始化.
如果@ConfigurationProperties注解作用于普通的class上面,spring并不认为这个class的实例需要被管理,所以既不会为该class实例化bean,更不会为bean赋值属性.

### @ConfigurationProperties的工作原理

@ConfigurationProperties注解的工作原理涉及到bean的初始化阶段.
spring通过内置的BeanPostProcessor在bean初始化阶段,为bean设置合适的属性值.


#### bean注册阶段
将bean注册到spring容器中,此处主要是通过向容器中注册BeanDefinition.
- 通过@Component(包含@Controller/@Service/@Configuration等)向spring容器注册BeanDefinition.
- 通过@Bean method向spring容器注册BeanDefinition.
- 通过@Import+ImportBeanDefinitionRegistrar向spring容器注册BeanDefinition.

仅仅在class上声明@ConfigurationProperties注解是无法向容器中注册BeanDefinition.通常开发者提供的配置信息的类需要通过@Configuration+@ConfigurationProperties来完成.
为此spring提供了更方便的注解方式@EnableConfigurationProperties.
例如下:
```java
@Configuration(proxyBeanMethods = false)
@EnableConfigurationProperties(RabbitProperties.class)
public class RabbitAutoConfiguration {
    // ...
}

@ConfigurationProperties(prefix = "spring.rabbitmq")
public class RabbitProperties {
    // ...
}
```
注意: RabbitProperties上的@ConfigurationProperties是不能省略的.仅仅是将独立的@Configuration换成了非独立的@EnableConfigurationProperties

@EnableConfigurationProperties的工作原理就是通过@Import+ImportBeanDefinitionRegistrar向spring容器注册BeanDefinition.
源码如下:
```java
@Import(EnableConfigurationPropertiesRegistrar.class)
public @interface EnableConfigurationProperties {
    // ...
}
```


#### bean属性赋值阶段
spring基于BeanDefinition构造bean对象时,在bean实例化完成后的初始化阶段,由ConfigurationPropertiesBindingPostProcessor作为BeanPostProcessor完成初始化的部分工作.
ConfigurationPropertiesBindingPostProcessor查找到对应的@ConfigurationProperties注解实例信息(即ConfigurationPropertiesBean),
然后用ConfigurationPropertiesBinder基于容器信息和ConfigurationPropertiesBean将符合bean对象属性名的配置项值赋值到bean对象属性上.






