package interpreter;

import java.util.LinkedList;
import java.math.BigDecimal;
import static java.math.RoundingMode.HALF_DOWN;

public class Calculator {

    public Calculator() {

    }


    private static int priority(char a) {
        switch(a) {
        case '+':
        case '-':
            return 1;
        case '*':
        case '/':
        case '%':
            return 2;
        case '^':
            return 3;
        case '(':
            return 0;
        case ')':
            return 0;
        }
        return 0;
    }

    private static BigDecimal calculate(char ope, BigDecimal a, BigDecimal b) {
        switch(ope) {
        case '+':
            return a.add(b);
        case '-':
            return a.subtract(b);
        case '*':
            return a.multiply(b);
        case '/':
            return a.divide(b, 3, HALF_DOWN);
        case '%':
            return new BigDecimal(a.intValue() % b.intValue());
        case '^':
            return a.pow(b.intValue());
        }
        return null;
    }


    public static BigDecimal calculate(String input) {
        if(input.equals("")){
            return BigDecimal.ZERO;
        }
        LinkedList<Character> operators = new LinkedList<>();
        LinkedList<BigDecimal> ret = new LinkedList<>();

        BigDecimal current = BigDecimal.ZERO;
        final int DIGIT = 0;
        final int OPE = 1;
        final int DECIMAL = -1;
        BigDecimal move = BigDecimal.ZERO;
        int currentType = OPE;
        for(int i = 0 ; i < input.length() ; i++) {
            char c = input.charAt(i);
            if(c <= '9' && c >= '0') {
                if(currentType == OPE) {
                    currentType = DIGIT;
                }
                if(currentType == DECIMAL) {
                    current = current.add(new BigDecimal(c - '0').multiply(move));
                    move = move.divide(BigDecimal.TEN);
                }
                else {
                    current = current.multiply(BigDecimal.TEN).add(new BigDecimal(c - '0'));
                }
            }
            else if(c == '.') {
                currentType = DECIMAL;
                move = BigDecimal.ONE.divide(BigDecimal.TEN);
            }
            else if("+-*/^%".contains(String.valueOf(c))) {
                if(currentType != OPE) {
                    ret.push(current);
                }
                currentType = OPE;
                current = BigDecimal.ZERO;
                if(operators.isEmpty() || c == '(') {
                    operators.push(c);
                }
                else if(c == ')') {
                    char c2;
                    while((c2 = operators.pop()) != '(') {
                        BigDecimal val1 = ret.pop();
                        BigDecimal val2 = ret.pop();
                        BigDecimal result = calculate(c2, val2, val1);
                        if(result != null) {
                            ret.push(result);
                        }
                    }
                }
                else if(priority(c) > priority(operators.peek())) {
                    operators.push(c);
                }
                else {
                    while(priority(operators.peek()) > priority(c)) {
                        char c2 = operators.pop();
                        BigDecimal val1 = ret.pop();
                        BigDecimal val2 = ret.pop();
                        BigDecimal result = calculate(c2, val2, val1);
                        if(result != null) {
                            ret.push(result);
                        }
                    }
                    operators.push(c);
                }
            }
        }
        if(currentType != OPE) {
            ret.push(current);
        }
        while(!operators.isEmpty()) {
            BigDecimal val1 = ret.pop();
            BigDecimal val2 = ret.pop();
            ret.push(calculate(operators.pop(), val2, val1));
        }
        return ret.get(0);
    }

}