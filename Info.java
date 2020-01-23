package user;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;

public class Info {
    public static void info(JFrame f,String name){
        System.out.println(name);
        final JDialog d = new JDialog(f,"用户信息",true);
        JPanel jp1 = new JPanel();
        JPanel jp2 = new JPanel();
        JPanel jp4 = new JPanel();
        JPanel jp5 = new JPanel();
        JLabel jl1 = new JLabel("姓名：");
        JLabel jl6 = new JLabel("性别：");
        JLabel jl2 = new JLabel("地址：");
        JLabel jl3 = new JLabel("端口：");
        JLabel jl4 = new JLabel("年龄：");
        JLabel jl5 = new JLabel("职业：");
        JTextField jtf1 = new JTextField(10);
        JTextField jtf2 = new JTextField(10);
        JTextField jtf3 = new JTextField(10);
        JTextField jtf4 = new JTextField(10);
        JTextField jtf5 = new JTextField(10);
        JTextField jtf6 = new JTextField(10);
        jp1.add(jl1);
        jp1.add(jtf1);
        jp1.add(jl6);
        jp1.add(jtf6);
        jp2.add(jl2);
        jp2.add(jtf2);
        jp2.add(jl3);
        jp2.add(jtf3);
        jp5.add(jl4);
        jp5.add(jtf4);
        jp5.add(jl5);
        jp5.add(jtf5);
        jp4.setLayout(new GridLayout(3,1));
        jp4.add(jp1);
        jp4.add(jp2);
        jp4.add(jp5);
        d.add(jp4);
        jtf2.setText("localhost");
        jtf2.setEditable(false);
        jtf3.setText("6666");
        jtf3.setEditable(false);
        d.setLocation(200,200);
        d.setSize(350,200);
        d.setResizable(false);
        d.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        d.setVisible(true);
        }
}
