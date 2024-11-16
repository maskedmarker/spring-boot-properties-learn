# 关于spring的配置文件


## .properties文件

In a Spring Boot application, the default character encoding of properties files (application.properties) is UTF-8. 
This differs from Java's default for .properties files, which uses ISO-8859-1.
Spring Boot opts for UTF-8 because it's a more modern and flexible encoding standard, supporting a wide range of characters from various languages, which is often necessary in web applications with internationalization.

spring-boot默认.properties文件的编码是UTF-8,而非java默认的ISO-8859-1.这样做主要是为了国际化,可以直观的查看配置文件.如果采用ISO-8859-1编码,则配置文件的内容需要先转码,不直观.

