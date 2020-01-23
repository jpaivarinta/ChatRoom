package server;
import java.net.*;
import java.io.*;
import java.util.*;
import java.io.IOException;
import user.User;
public class Server {
    private static final int PORT = 6666;
    private ServerSocket server;
    public static ArrayList<PrintWriter> list;
    public static String user;
    public static ArrayList<User> list1 = new ArrayList<>();
    public User uu;

    public Server(String user) {
        this.user = user;
    }

    public void getServer() {
        list = new ArrayList<>();
        try {
            server = new ServerSocket(PORT);
            System.out.println("服务器启动，开始监听");
            while (true) {
                Socket client = server.accept();
                PrintWriter writer = new PrintWriter(client.getOutputStream());
                list.add(writer);
                Thread t = new Thread(new Chat(client));
                t.start();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    public static void main(String[] args) {
        new Server(user).getServer();
    }


    class Chat implements Runnable {
        Socket socket;
        private BufferedReader br;
        private String msg;
        private String mssg = "";
        private String msssg = "";
        public Chat(Socket socket) {
            try {
                this.socket = socket;
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

        public void run() {
            try {
                br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                while ((msg = br.readLine()) != null) {
                    //有人进入聊天室，更新用户列表
                    if (msg.equals("1008611")) {
                        msg = br.readLine();
                        String[] st = msg.split(":");
                        uu = new User(st[0], st[1], socket);
                        list1.add(uu);
                        Iterator<User> it = list1.iterator();
                        while(it.hasNext()) {
                            User use = it.next();
                            msg = use.getName() + "(" + use.getSex() + "):";
                            mssg += msg;
                        }
                        sendMessage("1008611");
                        sendMessage(mssg);
                    }
                    //公布有人进入聊天室
                    else if (msg.equals("10086")) {
                        msg = br.readLine();
                        System.out.println(msg);
                        sendMessage("10086");
                        sendMessage(msg);
                    }
                    //有人发出群发消息
                    else if (msg.equals("10010")) {
                        msg = br.readLine();
                        System.out.println(msg);
                        sendMessage("10010");
                        sendMessage(msg);
                    }
                    //处理私发消息
                    else if (msg.equals("841163574")) {
                        msg = br.readLine();
                        String[] rt = msg.split("1072416535");
                        System.out.println(rt[1]);
                        String[] tg = rt[0].split(":");
                        Iterator<User> iu = list1.iterator();
                        while (iu.hasNext()) {
                            User se = iu.next();
                            if (tg[1].equals(se.getName() + "(" + se.getSex() + ")")) {
                                try {
                                    PrintWriter pwriter = new PrintWriter(se.getSock().getOutputStream());
                                    pwriter.println("841163574");
                                    pwriter.println(rt[1]);
                                    pwriter.flush();
                                    System.out.println(rt[1]);
                                } catch (Exception ex) {
                                    ex.printStackTrace();
                                }
                            } else if (tg[0].equals(se.getName())) {
                                try {
                                    PrintWriter pwr = new PrintWriter(se.getSock().getOutputStream());
                                    pwr.println("841163574");
                                    pwr.println(rt[1]);
                                    pwr.flush();
                                    System.out.println(rt[1]);
                                } catch (Exception ex) {
                                    ex.printStackTrace();
                                }
                            }
                        }
                    }
                    //公布有人离开聊天室
                    else if (msg.equals("456987")) {
                        msg = br.readLine();
                        System.out.println(msg);
                        sendMessage("456987");
                        sendMessage(msg);
                    }
                    //有人离开聊天室，更新用户列表
                    else if (msg.equals("45698711")) {
                        msg = br.readLine();
                        String[] si = msg.split(":");
                        int i;
                        Iterator<User> at = list1.iterator();
                        while (at.hasNext()) {
                            User sr = at.next();
                            if (sr.getName().equals(si[0])) {
                                sr.getSock().close();
                            }
                        }
                        for(i = 0;i<list1.size();i++){
                            if(list1.get(i).getName().equals(si[0])){
                                list1.remove(i);
                                list.remove(i);
                            }
                        }
                        Iterator<User> it = list1.iterator();
                        while(it.hasNext()) {
                            User use = it.next();
                            msg = use.getName() + "(" + use.getSex() + "):";
                            msssg += msg;
                        }
                        sendMessage("45698711");
                        sendMessage(msssg);
                    }
                    //要查看用户信息
                    else if (msg.equals("123654")) {
                        String mssge = "";
                        Iterator<User> iter = list1.iterator();
                        while (iter.hasNext()) {
                            User uus = iter.next();
                            msg = uus.getName() + "(" + uus.getSex() + "):";
                            mssge += msg;
                        }
                        sendMessage("123654");
                        sendMessage(mssge);
                    }
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }

        public void sendMessage(String message) {
            try {
                for (PrintWriter pw:list) {
                    pw.println(message);
                    pw.flush();
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }
}