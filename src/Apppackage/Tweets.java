package Apppackage;


import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import twitter4j.Paging;

import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.ResponseList;
import twitter4j.Status;

import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;
import twitter4j.auth.RequestToken;
import twitter4j.conf.ConfigurationBuilder;

/**
 * Twitter application using Twitter4J
 */
public class Tweets {
    private final Logger logger = Logger.getLogger(Tweets.class.getName());

    public static void main(String[] args) {
        new Tweets().retrieve();
    }

    public void retrieve() {
        logger.info("Retrieving tweets...");
        Twitter twitter = new TwitterFactory().getInstance();
        String user = "ahoyrotterdam";
        Query query = new Query("from:"+user);
        query.setCount(100);
        query.setSince("2015-01-01");
        try {
            QueryResult result = twitter.search(query);
            System.out.println("Count : " + result.getTweets().size()) ;
            for (Status tweet : result.getTweets()) {
                System.out.println("text : " + tweet.getText());
            }
        } catch (TwitterException e) {
            e.printStackTrace();
        }
        logger.info("done! ");
    }
}