/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Apppackage;


/**
 * This class functions as a data object to store and print the username and tweet.
 *
 * @author aevitus
 */
public class Tweet {
    private String username;
    private String tweet;

    /**
     * Default constructor.
     * 
     * @param username username of tweeter.
     * @param tweet actual tweet.
     */
    public Tweet(String username, String tweet) {
        this.username = username;
        this.tweet = tweet;
    }

    
    /**
     * Getters and setters.
     * 
     * @return 
     */
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getTweet() {
        return tweet;
    }

    public void setTweet(String tweet) {
        this.tweet = tweet;
    } 

    /**
     * Customized toString.
     * 
     * @return 
     */
    @Override
    public String toString() {
        return "@" + username + ": " + tweet + "\n\n";
    }
    
}
