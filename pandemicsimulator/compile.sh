#!/bin/bash
cwd=$PWD

javac -d $cwd/classes/ $cwd/src/person/Immunity.java
javac -d $cwd/classes/ -cp $cwd/classes/ $cwd/src/person/Person.java
javac -d $cwd/classes/ -cp $cwd/classes/ $cwd/src/person/Person.java
javac -d $cwd/classes/ -cp $cwd/classes/ $cwd/src/ui/Ball.java
javac -d $cwd/classes/ -cp $cwd/classes/ $cwd/src/ui/Simulator.java
javac -d $cwd/classes/ -cp $cwd/classes/ $cwd/src/Main.java
jar cfm $cwd/Main.jar $cwd/manifest.txt -C $cwd/classes/ ./
