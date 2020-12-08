# jmtrace
软件工程导引第二次编程作业-动态程序分析之jmtrace
# 依赖
* JDK15
* ASM9
# 使用方法
1. 将src中的源码打包为javaagent.jar包，注意在选择MF文件时，**必须选用src中的MF文件。同时，还需要将依赖包asm9.0也打包进jar中。**  
2. 打包完成后，执行命令行操作。  
`java -javaagent:javaagent.jar -jar yourJAR`  
