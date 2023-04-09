# smart-doc的jar包可执行版
## 说明
对原项目的修改，使其可以打包成可执行jar包，脱离mvn或其他运行，兼容原版json配置文件，在配置文件中写入源码目录

## 针对源码的修改
pom.xml增加
```xml
            <!-- assembly -->
            <plugin>
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-assembly-plugin</artifactId>
                <configuration>
                    <archive>
                        <manifest>
                            <mainClass>com.power.doc.Main</mainClass>
                        </manifest>
                    </archive>
                    <descriptorRefs>
                        <descriptorRef>jar-with-dependencies</descriptorRef>
                    </descriptorRefs>
                </configuration>
                <executions>
                    <execution>
                        <id>make-assembly</id>
                        <phase>package</phase>
                        <goals>
                            <goal>single</goal>
                        </goals>
                    </execution>
                </executions>
            </plugin>
```
Java文件增加`src/main/java/com/power/doc/Main.java`

## 打包方法
```
mvn clean package -Dmaven.test.skip=true
```

## 使用方法
```
java -jar [ApiDocType] [SettingJsonFile]
```