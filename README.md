# NGGS-POS

NGGS-POS is a simple point of sale software built with JavaFX aimed to manage the orders of food of festival/events managed by non profit org. In fact it prints non-fiscal receipts with just the list of ordered items.

## Build

There are 3 possible ways to build a JavaFX application.

### Java 8 (default)

Use the default pom.xml, it builds a java 8 application (In Oracle Java 8 the JavaFX part it is still included - Additionally Liberica offers Jdk 8, 11 and 17 with javafx included)

### Java 11
Use the pom-java11.xml, it builds a java 11 application where JavaFX libraries are included in the fat-jar.   
As explained on [JavaFX website](https://openjfx.io/openjfx-docs/#maven) it is required to create a Launcher class that doesn't extend from Application (like MainApp).   
Additionally javafx.graphics is platform dependent so all three versions (win,linux,mac) must be added to pom in order to build a portable application. Shade plugin must also be added to maven pom to specify the launche class and bundle all together.

### Jlink
It is possible to build a modular application using jlink. It requires that all the dependencies are modules (newer codebase).

To convert a jar to a modular jar

```shell
jlink namejar.jar
jlink --list-deps namejar.jar
jlink --generate-module-info . name.jar
```
The latest command generate a module-info file with the dependencies found in specified jar. It can be used as a starting point.