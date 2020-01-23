package client;

import java.net.*;
import java.io.*;
import user.G_Menu;


public class Socket_one {
    private static final int PORT = 6666;
    public static String user;
    public static Socket socket;

    public Socket_one(String user) {
        this.user = user;
        try {
            socket = new Socket("127.0.0.1", PORT);
            System.out.println("【" + user + "】欢迎来到聊天室!");
            Thread tt = new Thread(new Recove(socket, user));
            tt.start();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new Socket_one(user);
    }

    public class Recove implements Runnable {
        public String user;
        private Socket socket;
        public BufferedReader br;
        private String msg;
        G_Menu gm = new G_Menu();

        public Recove(Socket socket, String user) {
            try {
                this.socket = socket;
                this.user = user;
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

        public void run() {
            try {
                br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                while ((msg = br.readLine()) != null) {
                    String message = msg;
                    if (message.equals("1008611")) {
                        gm.listModel1.clear();
                        gm.jcomb.removeAllItems();
                        gm.jcomb.addItem("所有人");
                        message = br.readLine();
                        String[] str = message.split(":");
                        for (String ss : str) {
                            gm.listModel1.addElement(ss);
                            gm.jcomb.addItem(ss);
                        }
                    } else if (message.equals("841163574")) {
                        message = br.readLine();
                        System.out.println("收到：" + message);
                        gm.jta2.append(message + "\n");
                    } else if (message.equals("10010")) {
                        message = br.readLine();
                        System.out.println("收到：" + message);
                        gm.jta1.append(message + "\n");
                    } else if (message.equals("10086")) {
                        message = br.readLine();
                        gm.jta1.append(message + "\n");
                    } else if (message.equals("123654")) {
                        gm.listModel1.clear();
                        gm.jcomb.removeAllItems();
                        gm.jcomb.addItem("所有人");
                        message = br.readLine();
                        String[] sr = message.split(":");
                        for (String sst : sr) {
                            gm.listModel1.addElement(sst);
                            gm.jcomb.addItem(sst);
                        }
                    } else if (message.equals("456987")) {
                        message = br.readLine();
                        gm.jta1.append(message + "\n");
                    } else if (message.equals("45698711")){
                        gm.listModel1.clear();
                        gm.jcomb.removeAllItems();
                        gm.jcomb.addItem("所有人");
                        message = br.readLine();
                        String[] str = message.split(":");
                        for (String ss : str) {
                            gm.listModel1.addElement(ss);
                            gm.jcomb.addItem(ss);
                        }
                    }
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }

    }
}