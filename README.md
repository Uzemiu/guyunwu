# 古韵屋
五千年的中华文明为我们留下了无尽的宝藏，它们或是诗词歌赋，或是散文小说，而古文正是当代人继承和弘扬中国传统文化必不可少的工具。然而一方面学校和社会对古文学习的重视程度不够，一方面市面上也一直缺乏很好用的学习工具来帮助大家学习和理解古文。   
大部分的人到了大学之后就不再学习古文，不得不说这是一种遗憾。除此以外，小初高期间学习古文都是通过课堂或者纸质书本的传统形式，存在时空方面的不足。面对新时代新的需求，我们需要一款能帮助大家随时随地学习古文的APP，让传统文化重新走进大众生活。

[App简易使用文档](DOCS.md)
## 环境
Android 10+  
Project Java 11+  
Gradle Java 11+

## 后端
[guyunwu-backend](https://github.com/re20051/guyunwu)
## 配置
如果使用本小组已经部署好的后端，则前端无需任何配置，gradle构建完毕后可直接运行

如果自行部署后端，则需要修改 `/src/main/java/com/example/guyunwu/api/RequestModule` 中的 `BASE_URL` 为后端的地址  
如：

```java
public class RequestModule {
    // other code...
    public static final String BASE_URL = "http://10.0.2.2:8080/";  // 如果部署在本地
    // other code...
}
```
待gradle构建完毕后可直接运行
