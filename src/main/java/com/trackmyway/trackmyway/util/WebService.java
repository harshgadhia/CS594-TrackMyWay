package com.trackmyway.trackmyway.util;

/**
 * Created by Mitul on 6/10/2015.
 */
public class WebService {
    public String MIANURL = "http://trackmyway.uphero.com/index.php/api/";
    public String login = "login";
    public String verify = "verify";


    public String getConatctSynchURL(){
        return  MIANURL +"contactSynch";
    }
    public String getRejectUrl(){
        return  MIANURL +"reject";
    }
    public String getSendRequest(){
        return  MIANURL +"sendFriendRequest";
    }
    public String getAcceptRequestUrl(){
        return  MIANURL +"acceptFriendRequest";
    }

}
