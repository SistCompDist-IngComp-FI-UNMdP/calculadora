package calculator;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;



////// Solver
@FunctionalInterface
interface Solver {
    double solve(double a, double b);
}

class Add implements Solver {
    @Override
    public double solve(double a, double b) {
        return a + b;
    }

}

class Sub implements Solver {
    @Override
    public double solve(double a, double b) {
        return a - b;
    }
}

class Mult implements Solver {
    @Override
    public double solve(double a, double b) {
        return a * b;
    }
}

class Div implements Solver {
    @Override
    public double solve(double a, double b) {
        return a / b;
    }  
}



public class Calculator extends JFrame {

    private final JButton[] jbtNum;
    private JButton jbtEqual;
    private final JButton jbtAdd;
    private final JButton jbtSubtract;
    private final JButton jbtMultiply;
    private final JButton jbtDivide;
    private final JButton jbtSolve;
    private final JButton jbtClear;
    private double pushed;
    private double result;
    private JTextField textDisplay;

    Solver solver;

    //String display = "";

    public Calculator() {

        JPanel p1 = new JPanel();
        p1.setLayout(new GridLayout(4, 3));
        
        jbtNum = new JButton[10];
        
        for (int i = 0; i < 10; i++){
            String si = String.valueOf(i);
            p1.add(jbtNum[i] = new JButton(si));
            jbtNum[i].addActionListener(new ListenerValue(si));
        }

        p1.add(jbtClear = new JButton("C"));
        p1.add(jbtSolve = new JButton("="));

        JPanel p2 = new JPanel();
        p2.setLayout(new FlowLayout());
        p2.add(textDisplay = new JTextField(20));
        textDisplay.setHorizontalAlignment(JTextField.RIGHT);
        textDisplay.setEditable(false);

        JPanel p3 = new JPanel();
        p3.setLayout(new GridLayout(4, 1));
        
        /*p3.add(jbtAdd =         makeButtonSolver("+", new Add()) );
        p3.add(jbtSubtract =    makeButtonSolver("-", new Sub() ) );
        p3.add(jbtMultiply =    makeButtonSolver("*", new Mult() ) );
        p3.add(jbtDivide =      makeButtonSolver("/", new Div() ) );*/
        
        p3.add(jbtAdd =         makeButtonSolver("+", (a, b) -> a + b ) );
        p3.add(jbtSubtract =    makeButtonSolver("-", (a, b) -> a - b ) );
        p3.add(jbtMultiply =    makeButtonSolver("*", (a, b) -> a * b ) );
        p3.add(jbtDivide =      makeButtonSolver("/", (a, b) -> a / b ) );
        
        JPanel p = new JPanel();
        p.setLayout(new GridLayout());
        p.add(p2, BorderLayout.NORTH);
        p.add(p1, BorderLayout.SOUTH);
        p.add(p3, BorderLayout.EAST);

        add(p);
        
        jbtSolve.addActionListener(     new ListenToSolve());
        jbtClear.addActionListener(     new ListenToClear());
    } //JavaCaluclator()
    
    final JButton makeButtonSolver(String symbol, Solver solver) {
        JButton b = new JButton(symbol);
        b.addActionListener(new ListenerSolver(solver));
        return b;
    }

    double getPushed() {
        try {
            return Double.parseDouble( textDisplay.getText() );
        } catch (NumberFormatException ex) {
            return 0;
        }
    }
    
    ////// Listeners

    class ListenerValue implements ActionListener {
        String svalue;

        ListenerValue(String svalue) {
            this.svalue = svalue;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            textDisplay.setText( textDisplay.getText() + this.svalue );
        }        
    }

    class ListenerSolver implements ActionListener {
        Solver mySolver;

        ListenerSolver(Solver s) {
            this.mySolver = s;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            pushed = getPushed();
            
            solver = this.mySolver;
            
            textDisplay.setText("");
        }
    }

    class ListenToSolve implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            result = getPushed();

            if(solver != null)
                result = solver.solve(pushed, result);
            
            textDisplay.setText( Double.toString(result) );

            solver = null;
        }
    }

    class ListenToClear implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            pushed = 0;
            result = 0;
            
            textDisplay.setText("");

            solver = null;

        }
    }

    public static void main(String[] args) {
        // TODO Auto-generated method stub
        Calculator calc = new Calculator();
        calc.pack();
        calc.setLocationRelativeTo(null);
                calc.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        calc.setVisible(true);
    }

} //JavaCalculator

