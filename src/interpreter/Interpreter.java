package interpreter;

import java.util.TreeMap;
import java.util.NoSuchElementException;

import javafx.scene.control.TextArea;

public class Interpreter {
    
    private TreeMap<String, Executer> patternMapper = new TreeMap<>();
    private TreeMap<String, Number> variables = new TreeMap<>();
    private int lineNumber = 0;
    private TextArea output;
    private String[] lines;
    private String userInput = "";

    private String replaceVariable(String s){
        for(String name : variables.keySet()){
            s = s.replaceAll("(?<=^|\\W)" + name + "(?=$|\\W)", variables.get(name).toString());
        }
        return s;
    }

    private String makeStringCalculatable(String s){
        String filter = "[^\\+\\-\\*\\/\\^\\.\\%0-9]";
        for(String name : variables.keySet()){
            s = s.replaceAll("(?<=^|\\W)" + name + "(?=$|\\W)", variables.get(name).toString());
        }
        return s.replaceAll(filter, "");
    }

    public Boolean isFinish(){
        return lineNumber >= lines.length;
    }


    public Interpreter(){

        String filter1 = "[^\\+\\-\\*\\/\\%\\^\\w]";
        patternMapper.put("^print +.+", s -> {
            s = s.replaceFirst("print +", "");
            s = makeStringCalculatable(s);
            output.setText(output.getText() + Calculator.calculate(s) + '\n');
            return 0;
        });
        patternMapper.put("^input +.+", s ->{
            s = s.replaceFirst("input +", "");
            s = s.replaceAll(filter1, "");
            if(this.userInput.equals("")){
                return 1;
            }

            userInput = makeStringCalculatable(userInput);
            variables.put(s, Calculator.calculate(userInput));
            userInput = "";
            return 0;
        });
        patternMapper.put("\\w+.*=.+", s ->{
            String variable = s.split("=")[0].replaceAll("\\W", "");
            s = s.split("=")[1];
            s = makeStringCalculatable(s);
            variables.put(variable, Calculator.calculate(s));
            return 0;
        });
    }

    public Interpreter(TextArea output, String lines){
        this();
        this.output = output;
        this.lines = lines.split("\n");
    }
    
    public void execute(){
        try{
            if(output.getText().equals("")){
                output.setText("Start\n");
            }
            while(lineNumber < lines.length){
                String line = lines[lineNumber];
                System.out.println(line);
                for(String s : patternMapper.keySet()){
                    if(line.matches(s)){
                        int ret = patternMapper.get(s).execute(line);
                        if(ret == 1){
                            return;
                        }
                        break;
                    }
                }
                lineNumber++;
            }

        }catch (NoSuchElementException e ){
            e.printStackTrace();
        }
        output.setText(output.getText() + "End");
        return;
    }
    public void setUserInput(String input){
        this.userInput = input;
    }
}