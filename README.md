1) What did the system do? 
- This utility is aimed to configure multirelease jar, where depending on the client runtime classes can be used
by clients. Lets say the base of the project is Java 11, however, there are some features from Java 22, that are useful.
So we prepare a jar where class files are present depending on Java version. When clients use this jar, they pick up 
classes depending on Java versions.


2) What other systems have you seen in the wild like that?
- Roaring bitmap

3) How do you approach the development problem?
- The solution for this is already mentioned here. https://maven.apache.org/plugins/maven-compiler-plugin/multirelease.html#pattern-1-maven-multimodule
However, the reason to choose this option, is because of IDE support for higher version class files and testability.
Although this did come with complex maven configuration, using assembly plugin
to build this, however, it serves the purpose of unit tests and IDE recognises all the files in the source folder.

4) What were interesting aspects where you copied code from Stack Overflow?
- The Stack overflow, itself re-directed to change the approach primarily because of the pros
for this. https://stackoverflow.com/questions/70541340/setting-up-multi-release-jar-unit-tests. Not exactly code, 
but stackoverflow helped me to choose this option, and the entire project is picked up from
https://github.com/apache/maven-compiler-plugin/tree/master/src/it/multirelease-patterns.
Earlier, I used single project runtime option, however due to lack of IDE support, 
where it does not recognize `src/main/java22` folder, and no Unit test possible, I switched to this.

5) What did you learn from some very specific copy paste? Mention explicitly some
of them.

- Using separate profiles specific to parameters like JDK or OS. 
Although not used in multi module solution, but is used when we solve this using single project 
runtime. It a good use case, to pick up profiles depending on some parameters like JDK, the maven is pointing to. 
This gives us complete control on what goals corresponding to each phase, we need to execute,
on the basis of JDK detected during compile time. The below snippet will trigger the profile, 
if maven is pointing to JDK greater than or equal to 9.
```
<profile>
    <id>jdk9</id>
    <activation>
        <jdk>[9,)</jdk>
    </activation>
....
```

6) We built this using Java 22, and the aim of this was to include APIs available
in Java 22 to be used by clients having Java 22 runtime. If they don't have Java 22, then
base JDK(minimum JDK across all the modules), Java 11 class files will be available. 
Below is the folder structure for the jar, after executing `mvn clean package` 
when maven is pointing to the highest JDK across all the module, in this case its 22.

````
jar
-ShirtA 
-ShirtB
-META-INF
--versions
---22
----ShirtA
----ShirtC
````

7) After preparing the jar, we can see the expected output when maven is pointing to Java 11, by `cd java11-java22-multirelease` 
```
java -jar java11-java22-base/target/java11-java22-base-1.0-SNAPSHOT.jar
```
Output
```
ShirtB from java11-module constructed
this is ShirtA from java11-module :11
```
And when maven is pointing to Java 22
```
ShirtC from java22-module constructed
this is ShirtA from java22-module :22
```


