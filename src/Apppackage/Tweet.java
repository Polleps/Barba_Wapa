package Apppackage;

import static Database.Connectie.con;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import twitter4j.Paging;
import twitter4j.Query;
import twitter4j.QueryResult;
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

    private  Twitter twitter;
    private int limitRemaning = 0;
    private  List<String> foreigners = new ArrayList<String>();
    private  Paging pg = new Paging();
    private  int numTweets = 200;
    private  long lastID = Long.MAX_VALUE;
    private  List<Status> tweets;
    private  List<Status> statuses;
    private  ResponseList<Status> status;

    public ConfigurationBuilder configBuild() {
        
        ConfigurationBuilder cb = new ConfigurationBuilder();
        cb.setDebugEnabled(true);
        cb.setOAuthConsumerKey("yyWmEPUxeyRMgloAJO8OG8sOO");
        cb.setOAuthConsumerSecret("63eI6lEf1x5XiN6JaRzHB52nvWvuTNUuzZDLhtVPqrpuoYdPCF");
        cb.setOAuthAccessToken("388485636-s9zmeX3KFApKxO1xRCEYbmQYlEe6DrdsIB7UlGk0");
        cb.setOAuthAccessTokenSecret("rKY21nXqAUjeGTa6G5QoozQPxh0jhDt129o1bJMsABC0P");
        return cb;
    }
    public Tweet(){
        //this.twitter = new TwitterFactory(this.configBuild().build()).getInstance();
    }
    public void getTweets() {
        boolean cancel;
        /*while (tweets.size() < numTweets) {
            
            try {
                tweets.addAll(twitter.getUserTimeline("ahoyrotterdam", pg));
                System.out.println("Gathered " + tweets.size() + " tweets");
                for (Status t : tweets) {
                    
                    if (t.getId() < lastID) {
                        lastID = t.getId();
                        
                    }
                }
            } catch (TwitterException e) {
                System.out.println("Couldn't connect" + e);
            }
        }*/
        Twitter twitter = new TwitterFactory(configBuild().build()).getInstance();
        List<Long> curRS = getCurrentDB();
        try {
            Query query = new Query("#Ahoy");
            QueryResult result;
            do {
                result = twitter.search(query);
                tweets = result.getTweets();
                for (Status tweet : tweets) {
                    System.out.println("@" + tweet.getUser().getScreenName() + " - " + tweet.getText());
                    cancel = addToDB(tweet, curRS);
                    if(cancel)
                    {
                        System.out.println("No more new Tweeties.");
                        break;
                    }
                }
            } while ((query = result.nextQuery()) != null);
                
        } catch (TwitterException te) {
            te.printStackTrace();
            System.out.println("Failed to search tweets: " + te.getMessage());
            
        }
       
        
       
        System.out.println("Done");
    }

    private boolean addToDB(Status s, List<Long> r) {
        System.out.println("Connecting to Database");
        java.sql.Connection adderCon = null;
        try {
            for(int i = 0; i < r.size(); i++){
                if(r.get(i) == s.getId()){
                   return true;
                }
            }
            adderCon = DriverManager.getConnection("jdbc:mysql://db4free.net:3306/barbawapatest?useUnicode=true&amp;characterEncoding=UTF-8", "barba", "Ruggenmerg");
            Statement adderSt = adderCon.createStatement();
            long tweetID =  s.getId();
            String body = s.getText().replaceAll("'", ".");
            String mood = checkMood(body);
            System.out.println(mood);
            String tweetTime = (s.getCreatedAt().getYear() + 1900) + ":" + (s.getCreatedAt().getMonth() + 1) + ":" + s.getCreatedAt().getDate() + ":" + s.getCreatedAt().getHours() + ":" + s.getCreatedAt().getMinutes() + ":" + s.getCreatedAt().getSeconds() ;
            String qwu = "INSERT INTO tweets (tweet_id, tweet_body, dates, mood) VALUES ("+ tweetID +", '" + body + "', '" + tweetTime + "', '" + mood + "')";
            adderSt.execute(qwu);
            System.out.println(tweetID + " was added.");
        } catch (SQLException ex) {
            Logger.getLogger(Tweet.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    private List<Long> getCurrentDB() {
       ResultSet resDBC = null;
       java.sql.Connection conDBC = null;
       List<Long> result = new ArrayList<Long>();
       int count = 0;
        try {
             conDBC = DriverManager.getConnection("jdbc:mysql://db4free.net:3306/barbawapatest?useUnicode=true&amp;characterEncoding=UTF-8", "barba", "Ruggenmerg");
            Statement stDBC = conDBC.createStatement();
            resDBC = stDBC.executeQuery("SELECT tweet_id FROM tweets");
            while(resDBC.next()){
                result.add(resDBC.getLong("tweet_id"));
                
            }
            //conDBC.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            
        }
        return result;
    }
     private String checkMood(String c) {

        String mood;
        String mess = c.toLowerCase();
        String[] goodWords = {"goed ", "oke ", "vet ", "leuk ", "mooi ", "lachen ", "vet ", "prachtig ", "benieuwd ", "adembenemend ", "trots ",
            "zin in ", "geweldige ", "slim ", "gelukkig ", "geweldig ", "duimen ", "gezellig ", "top ", "gefeliciteerd ", "wauw ", "super ",
            "topper ", "yes ", " :) ", " :D ", " :-) ", "beste ", "leukste " , "wauw ", "wow ", "leuke ", " gaan ook", "gaaf ", "uitstekend ",
            "uitstekende ", "genieten ", "genoten ",
             "favoriete ", "ik ga", "(Y)", "plezier "};
        String[] badWords = {"slecht ", "vreselijk ", "stom ", "klote ", "vervelend ", "rot ", "teleurstelling ", "blûh ", "jammer ", "waanzinnig ",
            ":( ", ">:( ", "niet mijn ding ", "nee bedankt ", "kloten ",
            "klote ", "overbodig ", "gvd ", "belachelijk ", "ik kom niet", "schaam ", "shame ", "scande ", "achterlijke ", "zielig "};
        int goodCount = 0;
        int badCount = 0;
        for (int i = 0; i < goodWords.length; i++) {
            if (mess.contains(goodWords[i])) {
                goodCount++;
            }
        }
        for (int i = 0; i < badWords.length; i++) {
            if (mess.contains(badWords[i])) {
                badCount++;
            }
        }
        if (!(goodCount == 0 && badCount == 0)) {
            if (goodCount > badCount) {
                mood = "Positief";
            } else if (goodCount < badCount) {
                mood = "Negatief";
            } else {
                mood = "Undefined";
            }
        } else {
            mood = "No Keywords";
        }
        return mood;
    }
}
