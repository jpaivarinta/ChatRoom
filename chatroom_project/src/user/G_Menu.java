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
import java.sql.Timestamp;
import java.util.Date;
import java.util.ArrayList;
import java.util.List;

public class G_Menu extends JFrame implements ActionListener {
    JFrame jf = new JFrame("chat room");
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
    public JLabel jl1 = new JLabel("to");
    public static JComboBox jcomb = new JComboBox();
    public JCheckBox jcb = new JCheckBox("private");
    public JTextField jtf = new JTextField(36);
    public JButton jb1 = new JButton("send");
    public JButton jb2 = new JButton("user information");
    public JButton jb3 = new JButton("leave");
    public JButton jb4 = new JButton("crash");
    public static DefaultListModel listModel1;
    public static JList lst1;
    public String na;
    public String se;
    public String age;
    public String occuptaion;
    public String message;
    public String name2;
    public User_JDBC user_jdbc = new User_JDBC();
    public Date utildate = new Date();
    public Timestamp date = new Timestamp(utildate.getTime());
    public void getMenu(String name, String sex ,String age, String occupation) {
        jcomb.addItem("all");
        this.na = name;
        this.se = sex;
        this.age = age;
        this.occuptaion = occupation;
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
        lst1.setFixedCellHeight(25);
        lst1.setFixedCellWidth(100);
        JScrollPane jsp1 = new JScrollPane(jta1);
        JScrollPane jsp2 = new JScrollPane(jta2);
        JScrollPane jsp3 = new JScrollPane(lst1);
        jsp3.setBorder(new TitledBorder("user list"));
        jsp1.setBorder(new TitledBorder("messages"));
        jsp2.setBorder(new TitledBorder("private"));
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
        jp8.setLayout(new GridLayout(3,1));
        jp8.add(jb2);
        jp8.add(jb3);
        jp8.add(jb4);
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
        jb4.addActionListener(this);
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
            List<String> ls = user_jdbc.find_crash();
            int i;
            int j;
            for (i=0 ; i< ls.size(); i++ ) {
                String name_crash = ls.get(i);
                if (name_crash.equals(na)) {
                    System.out.println("这个人崩溃了");
                    List<String> rm = user_jdbc.revert_message();
                    for(j=0; j<rm.size(); j++){
                        jta1.append(rm.get(j));
                        jta1.append("\n");
                    }
                    break;
                }
            }
            if(i >= ls.size()) {
                pw.println("10086");
                String msg = "【" + na + "】" + "come in";
                pw.println(msg);
                pw.flush();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /*public G_Menu() {
        jb3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    pw = new PrintWriter(soc.socket.getOutputStream());
                    pw.println("456987");
                    pw.println(na + " leaves");
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
    }*/

    public void actionPerformed(ActionEvent event) {
        jb1.setText("send");
        jb2.setText("user information");
        jb3.setText("leave");
        jb4.setText("crash");
        try {
            pw = new PrintWriter(soc.socket.getOutputStream());
            if (event.getActionCommand().equals("send")) {
                if (!jtf.getText().equals("")) {
                    if (jcb.isSelected()) {
                        String name1 = (String) jcomb.getSelectedItem();
                        message = "private chat " + na + "(" + se + ")" + " to " + name1 + " says：" + jtf.getText();
                        pw.println("841163574");
                        pw.println(na + ":" + name1 + "1072416535" + message);
                        pw.flush();
                    } else {
                        pw.println("10010");
                        pw.println(na + "(" + se + ")" + " says：" + jtf.getText());
                        pw.flush();
                    }
                }
            }
            if (event.getActionCommand().equals("user information")) {
                Info.info(jf, name2);
            }
            if (event.getActionCommand().equals("leave")){
                pw.println("456987");
                pw.println(na + " leaves");
                pw.flush();
                pw.println("45698711");
                pw.println(na + ":" + se);
                pw.flush();
                jtf.setEditable(false);
                user_jdbc.delete_crash(na);
            }
            if (event.getActionCommand().equals("crash")){
                user_jdbc.crash(na);
                pw.println("45698712");
                pw.println(na);
                pw.flush();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        jtf.setText("");
        jtf.requestFocus();
    }
}
