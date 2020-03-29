package com.example.android.calculator;

import java.util.Stack;

public class Calculation {

    //Converts the infix expression to postfix expression
    public String convertToPostfix(String inputExp) {
        //Initialize variables
        StackReferenceBased Stack = new StackReferenceBased();
        String postfixExp = "";
        Stack.createStack();
        Object operator = ' ';
        char ch = ' ';

        //For loop to convert infix to postfix
        for (int i = 0; i < inputExp.length(); i++) {
            //Looking at each character in the string
            ch = inputExp.charAt(i);
            //Case 1: If the character is a number
            if ((ch >= '0' && ch <= '9') || ch == '.')  {
                //Add it to the new string
                postfixExp += ch;
                //While for when there is a multidigit number and if so that it does not go out of bounds
                if (i != inputExp.length()-1) {
                    while ((Character.isDigit(inputExp.charAt(i+1))) || inputExp.charAt(i+1) == '.') {
                        postfixExp += inputExp.charAt(i+1);
                        i++;
                        if (i == inputExp.length()-1)
                            break;
                    }
                }
                //Add spacing between operands and operators
                postfixExp += " ";
            }
            //Case 2: If character is closing bracket
            else if (ch == ')') {
                //Keep popping whats in the stack until '(' is found and append to postfix string
                while (!Stack.peek().equals('(')) {
                    operator = Stack.peek();
                    Stack.pop();
                    postfixExp += operator;
                    postfixExp += " ";
                }
                //Pop opening bracket
                Stack.pop();
            }
            //Case 3: If character is opening bracket
            else if (ch == '(')
                Stack.push(ch);
                //Case 4: If character is operator
            else if (ch == '/' || ch == 'x' || ch == '+' || ch == '-'){
                //Keep popping whats in the stack until and appending to postfix until restrictions are met
                while ((!Stack.isEmpty()) && (!Stack.peek().equals('(')) && (precedence(ch) <= precedence(Stack.peek().toString().charAt(0)))) {
                    operator = Stack.peek();
                    Stack.pop();
                    postfixExp += operator;
                    postfixExp += " ";
                }
                Stack.push(ch);
            }
            else {
                //Case 5: if valid input - non number or non operator sign
                return "Invalid Input";
            }
        }

        //Case 5: Place remaining operations in postfix if any operators left in stack
        while (!Stack.isEmpty()) {
            operator = Stack.peek();
            Stack.pop();
            postfixExp += operator;
            postfixExp += " ";
        }
        //Returning string with final post fix expression
        return postfixExp;
    }

    //Private method to check precedence of operators
    private static int precedence (char c) {
        if (c == 'x')
            return 4;
        else if (c == '/')
            return 3;
        else if (c == '-')
            return 2;
        else
            return 1;
    }

    //Method to calculate the postfix expression
    public float calculateExpression(String inputExp) {
        //Initializing variables
        StackReferenceBased Stack = new StackReferenceBased();
        Stack.createStack();
        float sum = 0;
        //Calling convert method to convert infix to postfix
        String infix = convertToPostfix(inputExp);
        //Split string to enter each operator or operand in array for easy use
        String[] postfix = infix.split(" ");
        //Method to calculate postfix
        for (int i = 0; i < postfix.length; i++) {
            //Case 1: if operand, push to stack
            if (!postfix[i].equals("/") && !postfix[i].equals("x") && !postfix[i].equals("+") && !postfix[i].equals("-"))
                Stack.push(postfix[i]);
                //Case 2: if operator found, pop last 2 and perform operation on them
            else {
                float op2 = Float.parseFloat(Stack.peek().toString());
                Stack.pop();
                float op1 = Float.parseFloat(Stack.peek().toString());
                Stack.pop();
                if (postfix[i].equals("x"))
                    sum = op1 * op2;
                if (postfix[i].equals("/"))
                    sum = op1 / op2;
                if (postfix[i].equals("+"))
                    sum = op1 + op2;
                if (postfix[i].equals("-"))
                    sum = op1 - op2;
                //Push the value in the array to perform other operations with new value
                Stack.push(sum);
            }
        }
        //Return last value in Stack - sum
        return sum;
    }
}
