package calculadora;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Arrays;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * Represents the GUI of the calculator
 */
public class GUI extends JPanel implements ActionListener, KeyListener {
    
    // Buttons size
    private final int widthButton;
    private final int heightButton;

    // Numbers buttons
    private final JButton b0;
    private final JButton b1;
    private final JButton b2;
    private final JButton b3;
    private final JButton b4;
    private final JButton b5;
    private final JButton b6;
    private final JButton b7;
    private final JButton b8;
    private final JButton b9;
    
    private final JButton addButton;              // Add button
    private final JButton subtractButton;         // Substract button
    private final JButton productButton;          // Product button
    private final JButton divisionButton;         // Division button
    private final JButton pointButton;            // Point float button
    private final JButton closingBracketButton;   // Colsing bracket button
    private final JButton openingBracketButton;   // Opening button
    private final JButton equalButton;            // Equal button
    private final JButton delButton;              // Delete button
    private final JButton acButton;               // Delete all button

    private final JTextField textBox;             // Textbox with the equation
    
    private String equation;    // Stores the current formula
    
    private CalculatorService calculatorService;  // Service that processes the equation
    
    /**
     * Init the GUI. Places the elements.
     */
    public GUI() {
        setLayout(null); // Remove default component position
        
        // Init elements
        equation = Constants.EMPTY;
        calculatorService = new CalculatorService();

        widthButton = 60;
        heightButton = 50;

        // Initialises and places the elements
        textBox = new JTextField();
        textBox.setBounds(100, 100, 360, heightButton);
        textBox.setEditable(false);
        this.add(textBox);

        b1 = new JButton("1");
        b1.setBounds(100, 200, widthButton, heightButton);
        b1.setFocusable(false);
        this.add(b1);

        b2 = new JButton("2");
        b2.setBounds(200, 200, widthButton, heightButton);
        b2.setFocusable(false);
        this.add(b2);

        b3 = new JButton("3");
        b3.setBounds(300, 200, widthButton, heightButton);
        b3.setFocusable(false);
        this.add(b3);

        b4 = new JButton("4");
        b4.setBounds(100, 300, widthButton, heightButton);
        b4.setFocusable(false);
        this.add(b4);

        b5 = new JButton("5");
        b5.setBounds(200, 300, widthButton, heightButton);
        b5.setFocusable(false);
        this.add(b5);

        b6 = new JButton("6");
        b6.setBounds(300, 300, widthButton, heightButton);
        b6.setFocusable(false);
        this.add(b6);

        b7 = new JButton("7");
        b7.setBounds(100, 400, widthButton, heightButton);
        b7.setFocusable(false);
        this.add(b7);

        b8 = new JButton("8");
        b8.setBounds(200, 400, widthButton, heightButton);
        b8.setFocusable(false);
        this.add(b8);

        b9 = new JButton("9");
        b9.setBounds(300, 400, widthButton, heightButton);
        b9.setFocusable(false);
        this.add(b9);

        b0 = new JButton("0");
        b0.setBounds(200, 500, widthButton, heightButton);
        b0.setFocusable(false);
        this.add(b0);

        addButton = new JButton(Constants.ADD);
        addButton.setBounds(400, 200, widthButton, heightButton);
        addButton.setFocusable(false);
        this.add(addButton);

        subtractButton = new JButton(Constants.SUBSTRACT);
        subtractButton.setBounds(400, 300, widthButton, heightButton);
        subtractButton.setFocusable(false);
        this.add(subtractButton);

        productButton = new JButton(Constants.PRODUCT);
        productButton.setBounds(400, 400, widthButton, heightButton);
        productButton.setFocusable(false);
        this.add(productButton);

        divisionButton = new JButton(Constants.DIVISION);
        divisionButton.setBounds(400, 500, widthButton, heightButton);
        divisionButton.setFocusable(false);
        this.add(divisionButton);

        equalButton = new JButton(Constants.EQUAL);
        equalButton.setBounds(400, 600, widthButton, heightButton);
        equalButton.setFocusable(false);
        this.add(equalButton);

        pointButton = new JButton(Constants.POINT);
        pointButton.setBounds(100, 600, widthButton, heightButton);
        pointButton.setFocusable(false);
        this.add(pointButton);

        openingBracketButton = new JButton(Constants.OPENING_BRACKET);
        openingBracketButton.setBounds(100, 500, widthButton, heightButton);
        openingBracketButton.setFocusable(false);
        this.add(openingBracketButton);

        closingBracketButton = new JButton(Constants.CLOSING_BRACKET);
        closingBracketButton.setBounds(300, 500, widthButton, heightButton);
        closingBracketButton.setFocusable(false);
        this.add(closingBracketButton);

        delButton = new JButton(Constants.DEL);
        delButton.setBounds(200, 600, widthButton, heightButton);
        delButton.setFocusable(false);
        this.add(delButton);

        acButton = new JButton(Constants.AC);
        acButton.setBounds(300, 600, widthButton, heightButton);
        acButton.setFocusable(false);
        this.add(acButton);
        
        // The actionListener is assigned
        b1.addActionListener(this);
        b2.addActionListener(this);
        b3.addActionListener(this);
        b4.addActionListener(this);
        b5.addActionListener(this);
        b6.addActionListener(this);
        b7.addActionListener(this);
        b8.addActionListener(this);
        b9.addActionListener(this);
        b0.addActionListener(this);

        addButton.addActionListener(this);
        subtractButton.addActionListener(this);
        productButton.addActionListener(this);
        divisionButton.addActionListener(this);
        pointButton.addActionListener(this);
        equalButton.addActionListener(this);
        openingBracketButton.addActionListener(this);
        closingBracketButton.addActionListener(this);
        delButton.addActionListener(this);
        acButton.addActionListener(this);

        textBox.addKeyListener(this);

    }
    
    /**
     * Adds a new element to the equation
     * @param value Value to add to the equation
     */
    private void loadData(String value) {
        
        List<String> simbols = Arrays.asList(Constants.ADD,Constants.SUBSTRACT,Constants.PRODUCT,
                Constants.DIVISION,Constants.OPENING_BRACKET,Constants.CLOSING_BRACKET,Constants.POINT);
        // If any button with a number or symbol is pressed
        if (Constants.NUMBERS.contains(value) || simbols.contains(value)) {
            // The selected symbol is added to the equation
            equation += value;
            this.textBox.setText(equation);

        } else if (value.equals(Constants.EQUAL) || value.equals("\n")) {
            // If equal button is pressed, the equation is solved
            try {
                String solution = String.valueOf(this.calculatorService.resolveEquation(equation));
                this.equation = solution;
                this.textBox.setText(solution);
            } catch (Exception ex) {
                this.textBox.setText(ex.getMessage());
                this.equation = Constants.EMPTY;
            }

        } else if (value.equals(Constants.DEL) || value.charAt(0)==KeyEvent.VK_BACK_SPACE) {
            // If the delete button is pressed, the last sign is removed from the equation
            if (equation.length() > 0) {
                equation = equation.substring(0, equation.length() - 1);
            }
            this.textBox.setText(equation);

        } else if (value.equals(Constants.AC)) {

            // If the delete all button is pressed, equation is eliminated
            equation = Constants.EMPTY;
            this.textBox.setText(equation);

        }
    }
    
    /**
     * When any button is pressed
     * @param e 
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        this.loadData(e.getActionCommand());
    }

    /**
     * When any key is pressed
     * @param e 
     */
    @Override
    public void keyPressed(KeyEvent e) {
        this.loadData(String.valueOf(e.getKeyChar()));
    }
    
    @Override
    public void keyTyped(KeyEvent e) { }

    @Override
    public void keyReleased(KeyEvent e) { }
    
    
    /**
     * Launch the GUI.
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        JFrame window = new JFrame("Calculadora");
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setSize(560+18, 800);

        window.add(new GUI());

        window.setVisible(true);
    }
    
}
