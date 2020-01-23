package user;

import client.Socket_one;
import server.Server;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.JList;
import javax.swing.border.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.io.*;

public class G_Menu extends JFrame implements ActionListener {
    JFrame jf = new JFrame("聊天室");
    public Socket_one soc;
    public PrintWriter pw;
    public JPanel jp1 = new JPanel();
    public JPanel jp2 = new JPanel();
    public JPanel jp3 = new JPanel();
    public JPanel jp4 = new JPanel();
    public JPanel jp5 = new JPanel();
    public JPanel jp6 = new JPanel();
    public JPanel jp7 = new JPanel();
    public JPanel jp8 = new JPanel();
    public static JTextArea jta1 = new JTextArea(12, 42);
    public static JTextArea jta2 = new JTextArea(12, 42);
    public JLabel jl1 = new JLabel("对");
    public static JComboBox jcomb = new JComboBox();
    public JCheckBox jcb = new JCheckBox("私聊");
    public JTextField jtf = new JTextField(36);
    public JButton jb1 = new JButton("发送");
    public JButton jb2 = new JButton("查看用户信息");
    public JButton jb3 = new JButton("退出");
    public static DefaultListModel listModel1;
    public static JList lst1;
    public String na;
    public String se;
    public String age;
    public String occuptaion;
    public String message;
    public String name2;
    public void getMenu(String name, String sex ,String age, String occupation) {
        jcomb.addItem("所有人");
        this.na = name;
        this.se = sex;
        this.age = age;
        this.occuptaion = occupation;
        User_JDBC user_jdbc = new User_JDBC();
        user_jdbc.add(name,sex,age,occupation);
        jta1.setEditable(false);
        jta2.setEditable(false);
        listModel1 = new DefaultListModel();
        lst1 = new JList(listModel1);
        lst1.addListSelectionListener((new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                int row = lst1.getSelectedIndex();
                User cha = Server.list1.get(row);
                name2 = cha.getName();
            }
        }));
        lst1.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        lst1.setVisibleRowCount(16);
        lst1.setFixedCellHeight(27);
        lst1.setFixedCellWidth(100);
        JScrollPane jsp1 = new JScrollPane(jta1);
        JScrollPane jsp2 = new JScrollPane(jta2);
        JScrollPane jsp3 = new JScrollPane(lst1);
        jsp3.setBorder(new TitledBorder("用户列表"));
        jsp1.setBorder(new TitledBorder("聊天室消息"));
        jsp2.setBorder(new TitledBorder("私聊消息"));
        jp1.setLayout(new GridLayout(2, 1));
        jp1.add(jsp1);
        jp1.add(jsp2);
        jp2.setLayout(new FlowLayout(FlowLayout.LEFT));
        jp2.add(jl1);
        jp2.add(jcomb);
        jp2.add(jcb);
        jp3.setLayout(new FlowLayout(FlowLayout.LEFT));
        jp3.add(jtf);
        jp3.add(jb1);
        jp4.setLayout(new GridLayout(2, 1));
        jp4.add(jp2);
        jp4.add(jp3);
        jp5.setLayout(new BorderLayout());
        jp5.add(jp1, BorderLayout.NORTH);
        jp5.add(jp4, BorderLayout.SOUTH);
        jp6.setLayout(new BorderLayout());
        jp8.setLayout(new GridLayout(2,1));
        jp8.add(jb2);
        jp8.add(jb3);
        jp6.add(jsp3, BorderLayout.NORTH);
        jp6.add(jp8, BorderLayout.SOUTH);
        jp7.setLayout(new FlowLayout(FlowLayout.LEFT));
        jp7.add(jp5);
        jp7.add(jp6);
        jf.add(jp7);
        jf.setLocation(200, 200);
        jf.setSize(700, 650);
        jf.setResizable(false);
        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jf.setVisible(true);
        jb1.addActionListener(this);
        jb2.addActionListener(this);
        jb3.addActionListener(this);
        jta1.setLineWrap(true);
        jta2.setLineWrap(true);
        jsp1.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        jsp1.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jsp2.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        jsp2.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jsp3.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        jsp3.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jf.pack();
    }
    public void sock() {
        try {
            String user = na + "(" + se + ")";
            soc = new Socket_one(user);
            pw = new PrintWriter(soc.socket.getOutputStream());
            pw.println("1008611");
            pw.println(na + ":" + se);
            pw.flush();
            pw.println("10086");
            pw.println("【" + na + "】" + "进入聊天室");
            pw.flush();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public G_Menu() {
        jb3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    pw = new PrintWriter(soc.socket.getOutputStream());
                    pw.println("456987");
                    pw.println(na + "离开聊天室");
                    pw.flush();
                    pw.println("45698711");
                    pw.println(na + ":" + se);
                    pw.flush();
                    jtf.setEditable(false);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });
    }

    public void actionPerformed(ActionEvent event) {
        jb1.setText("发送");
        jb2.setText("查看用户信息");
        jb3.setText("退出");
        try {
            pw = new PrintWriter(soc.socket.getOutputStream());
            if (event.getActionCommand().equals("发送")) {
                if (!jtf.getText().equals("")) {
                    if (jcb.isSelected()) {
                        String name1 = (String) jcomb.getSelectedItem();
                        message = "悄悄话" + na + "(" + se + ")" + "对" + name1 + "说：" + jtf.getText();
                        pw.println("841163574");
                        pw.println(na + ":" + name1 + "1072416535" + message);
                        pw.flush();
                    } else {
                        pw.println("10010");
                        pw.println(na + "说：" + jtf.getText());
                        pw.flush();
                    }
                }
            }
            if (event.getActionCommand().equals("查看用户信息")) {
                Info.info(jf, name2);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        jtf.setText("");
        jtf.requestFocus();
    }
}
