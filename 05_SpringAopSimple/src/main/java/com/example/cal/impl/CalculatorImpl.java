package com.example.cal.impl;

import com.example.cal.Calculator;
import org.springframework.stereotype.Component;

@Component
public class CalculatorImpl implements Calculator {

    @Override
    public int add(int a, int b) {
        int result = a + b;
        System.out.println("CalculatorImpl: add(), result=" + result);
        return result;
    }

    @Override
    public int subtract(int a, int b) {
        int result = a - b;
        System.out.println("CalculatorImpl: subtract(), result=" + result);
        return result;
    }

    @Override
    public int multiply(int a, int b) {
        int result = a * b;
        System.out.println("CalculatorImpl: multiply(), result=" + result);
        return result;
    }

    @Override
    public int divide(int a, int b) {
        int result = a / b;
        System.out.println("CalculatorImpl: divide(), result=" + result);
        return result;
    }

}
