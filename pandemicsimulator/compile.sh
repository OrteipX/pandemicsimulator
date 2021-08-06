#!/bin/bash
cwd=$PWD

javac -d $cwd/classes/ $cwd/src/person/PersonStatus.java
javac -d $cwd/classes/ $cwd/src/person/VaccinationStatus.java
javac -d $cwd/classes/ -cp $cwd/classes/ $cwd/src/person/Person.java
javac -d $cwd/classes/ -cp $cwd/classes/ $cwd/src/ui/Ball.java
javac -d $cwd/classes/ -cp $cwd/classes/ $cwd/src/ui/Simulator.java
javac -d $cwd/classes/ -cp $cwd/classes/ $cwd/src/PandemicSimulator.java
jar cfm $cwd/PandemicSimulator.jar $cwd/manifest.txt -C $cwd/classes/ ./
