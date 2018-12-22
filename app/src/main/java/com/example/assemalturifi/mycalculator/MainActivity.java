package com.example.assemalturifi.mycalculator;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {


    private EditText output;//result
    private EditText input;//newNumber


//    private Button button0,button1,button2,button3,button4,button5,button6,tan,openBracket,closebracket,powerOff;
//    private Button button7,button8,button9,ac,divide,reminder,minus,multi,plus,dot,equal, sin,cos,square;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bindViews();


    }
        private void bindViews () {
            input = findViewById(R.id.output);
            output = findViewById(R.id.input);
//
//            button0 = (Button) findViewById(R.id.btn0);
//            button1 = (Button) findViewById(R.id.btn1);
//            button2 = (Button) findViewById(R.id.btn2);
//            button3 = (Button) findViewById(R.id.btn3);
//            button4 = (Button) findViewById(R.id.btn4);
//            button5 = (Button) findViewById(R.id.btn5);
//            button6 = (Button) findViewById(R.id.btn6);
//            button7 = (Button) findViewById(R.id.btn7);
//            button8 = (Button) findViewById(R.id.btn8);
//            button9 = (Button) findViewById(R.id.btn9);
//
//
//            ac = (Button) findViewById(R.id.ac);
//            divide = (Button) findViewById(R.id.divider);
//            reminder = (Button) findViewById(R.id.reminder);
//            minus = (Button) findViewById(R.id.sub);
//            multi = (Button) findViewById(R.id.multi);
//            plus = (Button) findViewById(R.id.plus);
//            dot = (Button) findViewById(R.id.dot);
//            equal = (Button) findViewById(R.id.equl);
//
//
//            sin = (Button) findViewById(R.id.buttonSin);
//            cos = (Button) findViewById(R.id.buttonCos);
//            tan = (Button) findViewById(R.id.buttontan);
//            openBracket = (Button) findViewById(R.id.openBracket);
//            closebracket = (Button) findViewById(R.id.closeBracket);
//
//            powerOff = (Button) findViewById(R.id.powerOff);
//            square = (Button) findViewById(R.id.square);


        }

    public void click(View view) {
        Button btn=(Button)view;

        String textClick=btn.getText().toString();

        if(textClick.equals("A/C")){
            input.setText("");
            output.setText("");
            return;
        }
        if (textClick.equals("=")) {

            Double res=eval(input.getText().toString());
            output.setText(res.toString());
            return;
        }


        input.setText(input.getText()+textClick);

    }


    public static double eval(final String str) {
        return new Object() {
            int pos = -1, ch;

            void nextChar() {
                ch = (++pos < str.length()) ? str.charAt(pos) : -1;
            }

            boolean eat(int charToEat) {
                while (ch == ' ') nextChar();
                if (ch == charToEat) {
                    nextChar();
                    return true;
                }
                return false;
            }

            double parse() {
                nextChar();
                double x = parseExpression();
                if (pos < str.length()) throw new RuntimeException("Unexpected: " + (char)ch);
                return x;
            }

            // Grammar:
            // expression = term | expression `+` term | expression `-` term
            // term = factor | term `*` factor | term `/` factor
            // factor = `+` factor | `-` factor | `(` expression `)`
            //        | number | functionName factor | factor `^` factor

            double parseExpression() {
                double x = parseTerm();
                for (;;) {
                    if      (eat('+')) x += parseTerm(); // addition
                    else if (eat('-')) x -= parseTerm(); // subtraction
                    else return x;
                }
            }

            double parseTerm() {
                double x = parseFactor();
                for (;;) {
                    if      (eat('*')) x *= parseFactor(); // multiplication
                    else if (eat('/')) x /= parseFactor(); // division
                    else return x;
                }
            }

            double parseFactor() {
                if (eat('+')) return parseFactor(); // unary plus
                if (eat('-')) return -parseFactor(); // unary minus

                double x;
                int startPos = this.pos;
                if (eat('(')) { // parentheses
                    x = parseExpression();
                    eat(')');
                } else if ((ch >= '0' && ch <= '9') || ch == '.') { // numbers
                    while ((ch >= '0' && ch <= '9') || ch == '.') nextChar();
                    x = Double.parseDouble(str.substring(startPos, this.pos));
                } else if (ch >= 'a' && ch <= 'z') { // functions
                    while (ch >= 'a' && ch <= 'z') nextChar();
                    String func = str.substring(startPos, this.pos);
                    x = parseFactor();
                    if (func.equals("sqrt")) x = Math.sqrt(x);
                    else if (func.equals("sin")) x = Math.sin(Math.toRadians(x));
                    else if (func.equals("cos")) x = Math.cos(Math.toRadians(x));
                    else if (func.equals("tan")) x = Math.tan(Math.toRadians(x));
                    else throw new RuntimeException("Unknown function: " + func);
                } else {
                    throw new RuntimeException("Unexpected: " + (char)ch);
                }

                if (eat('^')) x = Math.pow(x, parseFactor()); // exponentiation

                return x;
            }
        }.parse();
    }


    }

