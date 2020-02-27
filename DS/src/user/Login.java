package user;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;
class Landen extends  JFrame implements ActionListener {
    JFrame jf = new JFrame("log in");
    JPanel jp1 = new JPanel();
    JPanel jp2 = new JPanel();
    JPanel jp3 = new JPanel();
    JPanel jp4 = new JPanel();
    JPanel jp5 = new JPanel();
    JLabel jl1 = new JLabel("Username：");
    JLabel jl2 = new JLabel("Host：");
    JLabel jl3 = new JLabel("Port：");
    JLabel jl4 = new JLabel("Age：");
    JLabel jl5 = new JLabel("Occupation：");
    JRadioButton jrb1 = new JRadioButton("Male");
    JRadioButton jrb2 = new JRadioButton("Female");
    JRadioButton jrb3 = new JRadioButton("Undefined");
    public JTextField jtf1 = new JTextField(10);
    public JTextField jtf2 = new JTextField(10);
    public JTextField jtf3 = new JTextField(10);
    public JTextField jtf4 = new JTextField(10);
    public JTextField jtf5 = new JTextField(10);
    JButton jb1 = new JButton("Log in");
    JButton jb2 = new JButton("Leave");
    TitledBorder tb = new TitledBorder("");
    ButtonGroup gb = new ButtonGroup();
    public void init(){
        jb1.addActionListener(this);
        jb2.addActionListener(this);
        jp1.add(jl1);
        jp1.add(jtf1);
        jp1.add(jrb1);
        jp1.add(jrb2);
        jp1.add(jrb3);
        jp2.add(jl2);
        jp2.add(jtf2);
        jp2.add(jl3);
        jp2.add(jtf3);
        jp3.add(jb1);
        jp3.add(jb2);
        jp5.add(jl4);
        jp5.add(jtf4);
        jp5.add(jl5);
        jp5.add(jtf5);
        jp4.setLayout(new GridLayout(4,1));
        jp4.add(jp1);
        jp4.add(jp2);
        jp4.add(jp5);
        jp4.add(jp3);
        jf.add(jp4);
        jtf2.setText("Localhost");
        jtf2.setEditable(false);
        jtf3.setText("6666");
        jtf3.setEditable(false);
        gb.add(jrb1);
        gb.add(jrb2);
        gb.add(jrb3);
        jf.setLocation(200,200);
        jf.setSize(350,250);
        jf.setResizable(false);
        jf.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        jf.setVisible(true);
    }
    public void actionPerformed(ActionEvent event){
        jb1.setText("Log in");
        jb2.setText("Leave");
        String s1 = null;
        if(event.getActionCommand().equals("Leave"))
        {
            System.exit(0);
        }
        if(event.getActionCommand().equals("Log in")){
            if(jtf1.getText().equals("")){
                JOptionPane.showMessageDialog(null,"Please type in username！");
            }else if(!jrb1.isSelected()&&!jrb2.isSelected()&&!jrb3.isSelected()){
                JOptionPane.showMessageDialog(null,"please choose gender！");
            }else{
                jf.setVisible(false);
                if(jrb1.isSelected()){
                    s1 = "Male";
                }
                else if(jrb2.isSelected()){
                    s1 = "Female";
                }
                else if(jrb3.isSelected()){
                    s1 = "Undefined";
                }
                G_Menu gmu = new G_Menu();
                gmu.getMenu(jtf1.getText(),s1,jtf4.getText(),jtf5.getText());
                gmu.sock();
            }
        }
    }
}
public class Login{
    public static void main(String[] args){
        new Landen().init();
    }
}
