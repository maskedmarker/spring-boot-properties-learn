# spring的Environment


## EnvironmentPostProcessor
1. Allows for customization of the application's Environment prior to the application context being refreshed.
2. EnvironmentPostProcessor implementations have to be registered in META-INF/spring.factories, using the fully qualified name of this class as the key.

在refreshed之前执行.refresh中实例化bean时,会基于beanDefinition提供的metadata和environment中的PropertySources为bean实例注入合适的属性值.



