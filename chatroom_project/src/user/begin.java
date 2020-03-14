package user;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

class subscribe extends  JFrame implements ActionListener{
    public JFrame jf = new JFrame("subscribe");
    public JPanel jp1 = new JPanel();
    public JPanel jp2 = new JPanel();
    public JPanel jp3 = new JPanel();
    public JTextArea jt1 = new JTextArea(6,12);
    public JTextArea jt2 = new JTextArea(6,12);
    public JButton jb1 = new JButton("subscribe_1");
    public JButton jb2 = new JButton("subscribe_2");
    public void init(){
        jp1.setBounds(10,10,330,150);
        jt1.setText("welcome to chatroom_1" +"\r\n"+
                "Ip is localhost");
        jt2.setText("welcome to chatroom_2" +"\r\n"+
                "Ip is 88888888");
        jb1.addActionListener(this);
        jb2.addActionListener(this);
        jp1.add(jt1);
        jp1.add(jt2);
        jp2.add(jb1);
        jp2.add(jb2);
        jp3.add(jp1);
        jp3.add(jp2);
        jf.add(jp3);
        jf.setLocation(200,200);
        jf.setSize(350,200);
        jf.setResizable(false);
        jf.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        jf.setVisible(true);
    }
    public void actionPerformed(ActionEvent event) {
        if(event.getActionCommand().equals("subscribe_1"))
        {
            jf.setVisible(false);
            Landen login = new Landen();
            login.init();
        }
        if(event.getActionCommand().equals("subscribe_2"))
        {
            jf.setVisible(false);
            Landen login = new Landen();
            login.init();
        }
    }
}
public class begin{
    public static void main(String[] args){
        new subscribe().init();
    }
}
