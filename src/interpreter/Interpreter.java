package interpreter;

import java.util.TreeMap;
import ui.Console;
import java.util.Scanner;
import java.util.NoSuchElementException;

public class Interpreter {
    
    private TreeMap<String, Executer> patternMapper = new TreeMap<>();
    private TreeMap<String, Number> variables = new TreeMap<>();
    private Console console;
    private int lineNumber = 0;
    private String[] lines;
    private String userInput = "";

    public Interpreter(){

        String filter1 = "[^\\+\\-\\*\\/\\%\\^\\w]";
        String filter2 = "[^\\+\\-\\*\\/\\^\\.\\%0-9]";
        patternMapper.put("^print +.+", s -> {
            s = s.replaceFirst("print +", "");
            for(String name : variables.keySet()){
                s = s.replaceAll("(?<=^|\\W)" + name + "(?=$|\\W)", variables.get(name).toString());
            }
            s = s.replaceAll(filter2, "");
            //System.out.println(Calculator.calculate(s));
            console.appendLine(Calculator.calculate(s).toString());
            return 0;
        });
        patternMapper.put("^input +.+", s ->{
            s = s.replaceFirst("input +", "");
            s = s.replaceAll(filter1, "");
            if(this.userInput.equals("")){
                console.appendLine("Please enter " + s);
                return 1;
            }

            for(String name : variables.keySet()){
                userInput = userInput.replaceAll("(?<=^|\\W)" + name + "(?=$|\\W)", variables.get(name).toString());
            }

            userInput = userInput.replaceAll(filter2, "");
            //Scanner input = new Scanner(System.in);
            //String in = input.nextLine();
            //variables.put(s, Calculator.calculate(in));
            variables.put(s, Calculator.calculate(userInput));
            userInput = "";
            return 0;
        });
        patternMapper.put("\\w+=.+", s ->{
            String variable = s.split("=")[0].replaceAll("\\W", "");
            s = s.split("=")[1];
            for(String name : variables.keySet()){
                s = s.replaceAll("(?<=^|\\W)" + name + "(?=$|\\W)", variables.get(name).toString());
            }
            s = s.replaceAll(filter2, "");
            variables.put(variable, Calculator.calculate(s));
            return 0;
        });
    }

    public Interpreter(Console console, String lines){
        this();
        this.lines = lines.split("\n");
        this.console = console;
    }
    
    public int execute(){
        try{
        while(lineNumber < lines.length){
            String line = lines[lineNumber];
            System.out.println(line);
            for(String s : patternMapper.keySet()){
                if(line.matches(s)){
                    int ret = patternMapper.get(s).execute(line);
                    if(ret == 1){
                        return 1;
                    }
                    break;
                }
            }
            lineNumber++;
        }

    }catch (NoSuchElementException e ){
        e.printStackTrace();
    }
        console.appendLine("End");
        return 0;
    }
    public void setUserInput(String input){
        this.userInput = input;
    }
}