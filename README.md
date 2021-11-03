# TrashEditer
 
## compile command:
```bash
javac -cp ./bin -d ./bin --module-path %PATH_TO_FX% --add-modules javafx.controls src/*.java src/ui/*.java src/interpreter/*.java
```

## run command:
```bash
java -cp ./bin --module-path %PATH_TO_FX% --add-modules javafx.controls Main
```

This can execute command below
1. input expression
2. print expression
3. variable=expression

and this can save, open, and create new file.