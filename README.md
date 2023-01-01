# guyunwu
安卓项目-古韵屋  
一款学习古文的App  
## 环境
Android 10+  
project java 11+  
gradle java 11+  

## 配置
如果使用本小组已经部署好的后端，则前端无需任何配置，gradle构建完毕后可直接运行  

如果自行部署后端，则需要修改 `/src/main/java/com/example/guyunwu/api/RequestModule` 中的 `BASE_URL` 为后端的地址  
如： 

```java
public class RequestModule {
    // other code...
    public static final String BASE_URL = "http://localhost:8080/";
    // other code...
}
```
待gradle构建完毕后可直接运行
