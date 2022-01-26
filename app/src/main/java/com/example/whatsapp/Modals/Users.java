package com.example.whatsapp.Modals;

public class Users
{
    String profilepic,mail,password,userId,lastmessage,username;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Users(String profilepic, String username, String mail, String password, String userId, String lastmessage) {
        this.profilepic = profilepic;
        this.mail = mail;
        this.username=username;
        this.password = password;
        this.userId = userId;
        this.lastmessage = lastmessage;
    }
    public Users()
    {

    }

    public String getUserId() {
        return userId;
    }

    public Users(String username, String mail, String password,String id) {
        this.username = username;
        this.mail = mail;
        this.password = password;
        this.userId=id;
    }

    public String getProfilepic() {
        return profilepic;
    }

    public void setProfilepic(String profilepic) {
        this.profilepic = profilepic;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserId(String key) {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getLastmessage() {
        return lastmessage;
    }

    public void setLastmessage(String lastmessage) {
        this.lastmessage = lastmessage;
    }
}
