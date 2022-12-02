import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

public class Panel extends JPanel {
    private JButton nums[] = new JButton[10];   // array for number buttons
    private Font font = new Font("Impact",Font.BOLD, 20);   //font
    private JTextField output = new JTextField();   // text field
    // Operation buttons:
    private JButton backspace = new JButton("C");
    private JButton eq = new JButton("=");
    private JButton plus = new JButton("+");
    private JButton min = new JButton("-");
    private JButton mul = new JButton("*");
    private JButton div = new JButton("/");
    public List<Double> numbers=new ArrayList<Double>();    // list of numbers that user writes in text field
    public List<Character> actions = new ArrayList<Character>(); // list of operations that user writes in text field
    public String txt ="";
    public Panel(){
        setLayout(null);
        setFocusable(true);
        grabFocus();

        // add buttons to panel
        add_button(backspace,10,250,50,50);
        add_button(eq,130,250,50,50);
        add_button(plus,190,70,50,50);
        add_button(min,190,130,50,50 );
        add_button(mul,190,190,50,50);
        add_button(div,190,250,50,50);

        nums[0] = new JButton("0");
        add_button(nums[0],70,250,50,50 );
        for(int i = 0; i<3; i++){
            for(int j=0;j<3;j++){
                nums[i*3+j+1] = new JButton(i*3+j+1+"");
                add_button(nums[i*3+j+1], i*60+10,j*60+70,50,50 );
            }
        }
        output.setBounds(10,10,250,50);
        output.setFont(font);
        output.setVisible(true);
        add(output);
        // action listener for writing numbers
        ActionListener l = (ActionEvent e) -> {
            JButton b = (JButton) e.getSource();
            txt += b.getText(); // add and save a number in txt string
            output.setText(txt);    // show the  text in text field
        };
        // action listener for backspace
        ActionListener bckspc = (ActionEvent e) -> {
            txt = "";
            output.setText(txt);
        };
        // action listener for math actions
        ActionListener act = (ActionEvent e) -> {
            numbers.add(Double.parseDouble(txt));   // convert all text written before the action into double and add it to the list of numbers
            JButton b = (JButton) e.getSource();
            actions.add(b.getText().charAt(0)); // add action to the list of operations
            output.setText(txt + b.getText());  // show all in text field
            txt = "";   // delete text
        };
        // action listener for '='
        ActionListener equal = (ActionEvent e) -> { // .for example, we have string "5+10*2". Before we pushed the button "=", we had a list of numbers [5, 10] and list of operations ['+', '*']
            numbers.add(Double.parseDouble(txt));   // add the last number to the list of numbers //.We have a number list [5, 10, 2]
            if(numbers.size()>=2 && actions.size() == numbers.size()-1){
                while (numbers.size()>1) {
                    while (actions.contains('*') == true || actions.contains('/') == true) {    // first of all, we solve multiplication and division
                        if (actions.contains('*')) {    // We have '*' in the list
                            double n = numbers.get(actions.indexOf('*')) * numbers.get(actions.indexOf('*') + 1);   // the index of '*' == 1, numbers[1] == 10, numbers[1+1] == 2, n = 10 * 2 = 20
                            change_lists(numbers, actions, '*', n); // delete 2 and put 20 in place of 10 and remove '*' from actions
                        }
                        if (actions.contains('/')) {
                            double n = numbers.get(actions.indexOf('/')) / numbers.get(actions.indexOf('/') + 1);
                            change_lists(numbers, actions, '/', n);
                        }
                    }
                    while (actions.contains('+') == true || actions.contains('-') == true) {    // then we solve addition and subtraction
                        if (actions.contains('+')) {    // We have '+' in the list
                            double n = numbers.get(actions.indexOf('+')) + numbers.get(actions.indexOf('+') + 1);   // index of '+' == 0, numbers[0] == 5, numbers[1] == 20, n = 5 + 20 = 25
                            change_lists(numbers, actions, '+', n);  // delete 20 and put 25 in place of 5 and remove '+' from actions
                        }
                        if (actions.contains('-')) {
                            double n = numbers.get(actions.indexOf('-')) - numbers.get(actions.indexOf('-') + 1);
                            change_lists(numbers, actions, '-', n);
                        }
                    }
                }
            }

            output.setText(String.valueOf(numbers.get(0)));     // When we have only one element in a number list, we show it //.25
            numbers = new ArrayList<Double>();
        };
        // add action listener
        for(JButton b:nums){
            b.addActionListener(l);
        }
        backspace.addActionListener(bckspc);
        plus.addActionListener(act);
        min.addActionListener(act);
        mul.addActionListener(act);
        div.addActionListener(act);
        eq.addActionListener(equal);

    }

    public void add_button(JButton button, int x, int y, int width, int height){    // function for adding button with given coordinates, width and height
        button.setBounds(x, y, width, height);
        button.setFont(font);
        add(button);
    }

    public void change_lists(List<Double> numbers, List<Character> actions, char c, double n){  // function for replacing a numbers
        numbers.remove(actions.indexOf(c)+1);
        numbers.set(actions.indexOf(c),n);
        actions.remove(actions.indexOf(c));
    }

}
