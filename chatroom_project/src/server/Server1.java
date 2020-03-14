package server;
import java.net.*;
import java.io.*;
import java.nio.channels.Channel;
import java.util.*;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import user.User;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPubSub;

public class Server1 {
    private static final int PORT = 6666;
    private ServerSocket server;
    public Socket aclient;
    public static ArrayList<PrintWriter> list;
    public static ArrayList<User> list1 = new ArrayList<>();
    public static String user;
    public User uu;
    public ArrayList<String> messageContainer = new ArrayList<>();
    Jedis jedis = new Jedis();
    Jedis jedis1 = new Jedis();


    public Server1(String user) {
        this.user = user;
    }


    public static void main(String[] args) throws InterruptedException {

        new Server1(user).getServer();
    }


    public void getServer() throws InterruptedException {
        list = new ArrayList<>();

        // I made a new thread for subscribing to redis, because it will jam the whole code otherwise. I'm not sure if this is the best way, but it works
        /*new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                        Jedis jedis = new Jedis();
                        jedis.info();   //to test if Redis is running.
                        System.out.println("Server connected to Redis.");
                        //Here is the sunscription to channel "C1"
                        jedis.subscribe(jedisPubSub, "C1");
                } catch (Exception e) {
                    System.out.println("Failed to connect Redis. Redis-server may not be running.");
                }
            }
        }, "subscriberThread").start();*/
        Thread t2 = new Thread(new subscribe(jedis));
        t2.start();
        try {
            server = new ServerSocket(PORT);
            System.out.println("Server is running.\nWaiting for connections..");
            while (true) {
                Socket client = server.accept();
                aclient = client;
                if(client.isConnected())
                    System.out.println("New client connected to server.");
                PrintWriter writer = new PrintWriter(client.getOutputStream());
                list.add(writer);
                Thread t1 = new Thread(new Reader(client));
                t1.start();
                //Thread.sleep(100);
                //Thread t = new Thread(new Chat(client));
                //t.start();
                //t.join();
            }
        } catch (Exception e) {
            System.out.println("Exception: " + e);
        }
    }

    JedisPubSub jedisPubSub = new JedisPubSub() {
        //This is the function that receives Strings from redis
        //When String is received from redis, it stores it to ArrayList messageContainer.
        // Chat run() reads messages from this ArrayList then
        @Override
        public void onMessage(String channel, String message) {
            System.out.println("Channel " + channel + " has sent a message : " + message);
            messageContainer.add(message);
            while(messageContainer.size()==2){
                Chat chat = new Chat(aclient);
                chat.run();
            }
            System.out.println(messageContainer.size() + " size");
        }

        @Override
        public void onSubscribe(String channel, int subscribedChannels) {
            System.out.println("Server subscribed Redis-channel: "+ channel);
        }

        @Override
        public void onUnsubscribe(String channel, int subscribedChannels) {
            System.out.println("Server unsubscribed Redis-channel: "+ channel);

        }

    };
    // I made a new class Reader, which reads the messages from client just like in class Chat before.
    class subscribe implements  Runnable{
        Jedis jedis;
        public subscribe(Jedis jedis){
            this.jedis= jedis;
        }
        public void run() {

                try {
                    //Jedis jedis2 = new Jedis();
                    jedis.info();   //to test if Redis is running.
                    System.out.println("Server connected to Redis.");
                    //Here is the sunscription to channel "C1"
                    jedis.subscribe(jedisPubSub, "C1");

                } catch (Exception e) {
                    System.out.println("Failed to connect Redis. Redis-server may not be running.");
                }

        }


    }
    class Reader implements Runnable {
        Socket socket;
        private BufferedReader br;
        private String msg;

        public Reader(Socket socket) {
            try {
                this.socket = socket;
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

        // Reads the clients messages using bufferedReader and publishes them on redis
        public void run() {
            try {
                br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                while ((msg = br.readLine()) != null) {
                    jedis1.publish("C1", msg); // Pushes message to redis on Channel 1 ("C1")
                }
            } catch(IOException e) {
                e.printStackTrace();
            }
        }

    }

    class Chat implements Runnable {

        Socket socket;
        public Chat(Socket socket) {
            try {
                this.socket = socket;
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

        public void run() {
            Iterator<String> Itmsg = messageContainer.iterator();
                try {
                    //Checks if container has messages
                    //The redis is working correctly and is publishing the right messages correctly in the Reader run() function, it can be seen in console when running
                    //The problem is getting the messages from redis.
                    //The messages are pushed to this container in the jedisPubSub, but it's not working and I don't know why
                    while (Itmsg.hasNext()) {
                        String msg = Itmsg.next();
                        //Its getting an error for null reference
                        //I don't know how to get the items from the arraylist in the right order and how to delete them afterwards
                        //I think this should use somekind of LIFO(Last In First Out), but I'm not sure how to implement one

                        // These if statements should now compare to the strings gotten from the container
                        //Someone enters the chat room and updates the user list
                        if (msg.equals("1008611")) {
                            //msg = br.readLine();
                            String[] st = Itmsg.next().split(":");
                            uu = new User(st[0], st[1],socket);
                            list1.add(uu);
                            Iterator<User> it = list1.iterator();
                            String mssg = "";
                            while (it.hasNext()) {
                                User use = it.next();
                                msg = use.getName() + "(" + use.getSex() + "):";
                                mssg += msg;
                            }
                            sendMessage("1008611");
                            sendMessage(mssg);
                        }
                        //Announcing someone entering the chat room
                        else if (msg.equals("10086")) {
                            msg = Itmsg.next();
                            sendMessage("10086");
                            sendMessage(msg);
                        }
                        //Group message
                        else if (msg.equals("10010")) {
                            msg = Itmsg.next();
                            sendMessage("10010");
                            sendMessage(msg);
                        }
                        //Handle private messages
                        else if (msg.equals("841163574")) {
                            msg = Itmsg.next();
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
                        //Announcing someone leaving the chat room
                        else if (msg.equals("456987")) {
                            msg = Itmsg.next();
                            sendMessage("456987");
                            sendMessage(msg);
                        }
                        //
                        //Someone left the chat room to update the user list
                        else if (msg.equals("45698711")) {
                            msg = Itmsg.next();
                            String[] si = msg.split(":");
                            int i;
                            Iterator<User> at = list1.iterator();
                            while (at.hasNext()) {
                                User sr = at.next();
                                if (sr.getName().equals(si[0])) {
                                    sr.getSock().close();
                                }
                            }
                            for (i = 0; i < list1.size(); i++) {
                                if (list1.get(i).getName().equals(si[0])) {
                                    list1.remove(i);
                                    list.remove(i);
                                }
                            }
                            Iterator<User> it = list1.iterator();
                            String msssg = "";
                            while (it.hasNext()) {
                                User use = it.next();
                                msg = use.getName() + "(" + use.getSex() + "):";
                                msssg += msg;
                            }
                            sendMessage("45698711");
                            sendMessage(msssg);
                        }


                        //To view user information
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
                    messageContainer.clear();
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