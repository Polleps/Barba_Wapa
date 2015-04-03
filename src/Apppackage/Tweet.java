
package Apppackage;

import static Database.Connectie.con;
 import java.util.ArrayList;
import java.util.List;
import twitter4j.Paging;
import twitter4j.ResponseList;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;


/**
 *
 * @author Eigenaar
*/
    
    
    public class Tweet {

        
    private final Twitter twitter = new TwitterFactory(configBuild().build()).getInstance();
    private static int limitRemaning = 0;
    private static List<String> foreigners = new ArrayList<String>();
    private static Paging pg = new Paging();
    private static int numTweets = 200;
    private static long lastID = Long.MAX_VALUE;
    private static ArrayList<Status> tweets = new ArrayList<Status>();
    private static List<Status> statuses;
    private static ResponseList<Status> status;
    
    
    public ConfigurationBuilder configBuild() {
    ConfigurationBuilder cb = new ConfigurationBuilder();
    cb.setDebugEnabled(true);
    cb.setOAuthConsumerKey("VClGylqCNZBD3uQLde4cqMPgQ");
    cb.setOAuthConsumerSecret("3zaxwvYoRoxxLbmzsnzMsywhOYA6xdnqS8pbWgoRuLODjy75U5");
    cb.setOAuthAccessToken("j5HB8XgfRq6IZnCb4ecItz08tSeWUmHRPHQVLhCpUuwxk");
    cb.setOAuthAccessTokenSecret("178840749-gJdWH0xzFrrb0wp2ENaYcjoBNUR7rrDM5D1Wm0Sk");
    return cb;
 }
    
     public void getTweets(String username) {
        while (tweets.size() < numTweets)
            try {
                tweets.addAll(twitter.getUserTimeline("ahoyrotterdam", pg));
                System.out.println("Gathered " + tweets.size() + " tweets");
                for (Status t : tweets) {
                    System.out.println(t.getCreatedAt());
                    if (t.getId() < lastID) {
                        lastID = t.getId();
                    }
                }
            } catch (TwitterException e) {
                System.out.println("Couldn't connect" + e);
            }
        pg.setMaxId(lastID - 1);
    }
     
