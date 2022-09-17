package calculadora;

import java.util.ArrayList;

/**
 * Service that resolve the equations.
 */
public class CalculatorService {
    
    private ArrayList<String> elementsEq;    // List with the elements from the equation to resolv
    
    /**
     * Revolve the equation.
     * @param equation Equation to resolve.
     * @return Result of equation
     * @throws Exception
     */
    public Double resolveEquation(String equation) throws Exception {
        // Convert the string to a list
        this.elementsEq = this.stringToArrayList(equation);
        
        // Resolve the equation
        return this.S();
    }
    
    /**
     * Return if the param is a number or not.
     * @param number Number.
     * @return If the param is a number
     */
    private boolean isNumeric(String number) {
        try {
            Double.parseDouble(number);
            return true;
        } catch (NumberFormatException nfe) {
            return false;
        }
    }

    private ArrayList<String> stringToArrayList(String equation) {
        ArrayList<String> listEq = new ArrayList<>();
        int i = 0;
        while (i < equation.length()) {

            String elem = Character.toString(equation.charAt(i));

            if (isNumeric(elem)) { // If the character is a number
                String number = elem;
                i++;
                // While there are numbers, we add them
                while (i < equation.length() && isNumeric(Character.toString(equation.charAt(i)))) {
                    number += Character.toString(equation.charAt(i));
                    i++;
                }
                // Check if it is a float number
                if (i < equation.length()) {
                    String point = Character.toString(equation.charAt(i));
                    if (point.equals(Constants.POINT)) {
                        i++;
                        number += point;

                        while (i < equation.length() && isNumeric(Character.toString(equation.charAt(i)))) {
                            number += Character.toString(equation.charAt(i));
                            i++;
                        }
                    }
                }
                // Add number
                listEq.add(number);
            } else {
                listEq.add(elem);
                i++;
            }
        }
        listEq.add(Constants.EOF);
        return listEq;
    }
    
    private String getFirstElement() {
        return this.elementsEq.remove(0);
    }
    
    private void setFirstElement(String elem) {
        this.elementsEq.add(0, elem);
    }
    
    private Double error() throws Exception {
        throw new Exception(Constants.ERROR_MSG);
    }

    // Grammar:
    // <S> --> <S> +- <T> | <T> {<S>.v = <T>.v}
    // <T> --> <T> */ <N> | <N> {<T>.v = <N>.v}
    // <N> --> number | ( <S> ) | -<N>

    // Left-hand recursion must be eliminated
    // <S> --> <T> {<S2>.h = <T>.v} <S2> {<S>.v = <S2>.v}
    private double S() throws Exception {
        String elem = this.getFirstElement();
        
        if (isNumeric(elem) || elem.equals(Constants.OPENING_BRACKET) || elem.equals(Constants.SUBSTRACT)) {
            this.setFirstElement(elem);
            double v = this.T();
            return this.S2(v);
        } else {
            return this.error();
        }
    }

    // <S2>--> +- <T> {<S2>.h = <S2>.h +- <T>.v} <S2> | Lambda {<S2>.v = <S2>.h}
    private double S2(double h) throws Exception {
        String elem = this.getFirstElement();

        if (elem.equals(Constants.ADD)) {
            double v = this.T();
            return this.S2(h+v);
        } else if (elem.equals(Constants.SUBSTRACT)) {
            double v = this.T();
            return this.S2(h-v);
        } else if (elem.equals(Constants.CLOSING_BRACKET) || elem.equals(Constants.EOF)) {
            this.setFirstElement(elem);
            return h;
        } else {
            return this.error();
        }
    }

    // <T> --> <N> {<T2>.h = <N>.v} <T2> {<T>.v = <T2>.v}
    private double T() throws Exception {
        String elem = this.getFirstElement();

        if (isNumeric(elem) || elem.equals(Constants.OPENING_BRACKET) || elem.equals(Constants.SUBSTRACT)) {
            this.setFirstElement(elem);
            double v = this.N();
            return this.T2(v);
        } else {
            return this.error();
        }
    }

    // <T2>--> */ <N> {<T2>.h = <T2>.h */ <N>.v} <T2> {<T2>.v = <T2>.v} | Lambda {<T2>.v = <T2>.h}
    private double T2(double h) throws Exception {
        String elem = this.getFirstElement();

        if (elem.equals(Constants.PRODUCT)) {
            double v = this.N();
            return this.T2(h*v);
        } else if (elem.equals(Constants.DIVISION)) {
            double v = this.N();
            return this.T2(h/v);
        } else if (elem.equals(Constants.ADD) || elem.equals(Constants.SUBSTRACT) || elem.equals(Constants.CLOSING_BRACKET) || elem.equals(Constants.EOF)) {
            this.setFirstElement(elem);
            return h;
        } else {
            return this.error();
        }
    }

    // <N> --> number | ( <S> ) | -<N> {<N>.v = number | <S>.v | -<N>.v}
    private double N() throws Exception {
        String elem = this.getFirstElement();
        if (isNumeric(elem)) {
            return Double.parseDouble(elem);
        } else if (elem.equals(Constants.OPENING_BRACKET)) {
            double v = this.S();
            this.check(Constants.CLOSING_BRACKET);
            return v;
        } else if (elem.equals(Constants.SUBSTRACT)) {
            double v = this.N();
            return -1 * v;
        } else {
            return this.error();
        }
    }
    
    private double check(String e1) throws Exception {
        if (e1.equals(this.getFirstElement())) {
            return 1;
        } else {
            return this.error();
        }
    }
}
