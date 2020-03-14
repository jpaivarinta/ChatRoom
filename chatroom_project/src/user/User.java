package user;

import java.util.*;
import java.net.*;
import java.io.*;
public class User
{
    private String name;
    private String gender;
    private Socket sock;
    public User(String name,String gender,Socket sock){
        setName(name);
        setSex(gender);
        setSock(sock);
    }
    public String getName(){
        return name;
    }
    public void setName(String name){
        this.name=name;
    }
    public String getSex(){
        return gender;
    }
    public void setSex(String gender){
        this.gender=gender;
    }
    public Socket getSock(){
        return sock;
    }
    public void setSock(Socket sock){
        this.sock = sock;
    }
}