package user;

import java.util.*;
import java.net.*;
import java.io.*;
public class User
{
    private String name;
    private String sex;
    private Socket sock;
    public User(String name,String sex,Socket sock){
        setName(name);
        setSex(sex);
        setSock(sock);
    }
    public String getName(){
        return name;
    }
    public void setName(String name){
        this.name=name;
    }
    public String getSex(){
        return sex;
    }
    public void setSex(String sex){
        this.sex=sex;
    }
    public Socket getSock(){
        return sock;
    }
    public void setSock(Socket sock){
        this.sock = sock;
    }
}