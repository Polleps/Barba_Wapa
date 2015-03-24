package Apppackage;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import java.util.Scanner;

import java.io.File;
import java.io.FileNotFoundException;

import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;


public class TwitterCrawler {

    private static final Logger logger = Logger.getLogger(TwitterCrawler.class.getName());

    public static int filteredTweetCount = 0; // refers to number of filtered tweets.

    /**
     * This function extracts tweets from the timeline of a specific user.
     *
     * @param user this variable refers to the username.
     * @return List containing tweets.
     */
    public static List getTimeline(String user) {
        List<Tweet> tweets = new ArrayList<>();

        Twitter twitter = TwitterFactory.getSingleton();
        try {
            List<Status> statuses = twitter.getUserTimeline(user);
            for (Status status : statuses) {
                Tweet tweet = new Tweet(status.getUser().getName(), status.getText());
                tweets.add(tweet);
            }
        } catch (TwitterException e) {
            e.printStackTrace();
        }
        return tweets;
    }

    /**
     * This function extracts tweets based on searching with a keyword.
     *
     * @param keyword this variable refers to the word used to search.
     * @return List containing tweets.
     */
    public static List getSearchResults(String keyword) {
        List<Tweet> tweets = new ArrayList<Tweet>();

        Twitter twitter = TwitterFactory.getSingleton();
        Query query = new Query(keyword);
        try {
            QueryResult result = twitter.search(query);
            for (Status status : result.getTweets()) {
                Tweet tweet = new Tweet(status.getUser().getScreenName(), status.getText());
                tweets.add(tweet);
            }
        } catch (TwitterException e) {
            e.printStackTrace();
        }
        return tweets;
    }

    /**
     * This function extracts tweets based on a filtered search, the words to be
     * filtered can be found in src/files/filter.txt.
     *
     * @param keyword this variable refers to the word used to search.
     * @param option this variable refers to using timeline or search.
     * @return String containing filtered tweets.
     * @throws FileNotFoundException
     */
    public static String getFilteredResults(String keyword, String option) throws FileNotFoundException {
        List<Tweet> tweets = null;

        switch (option) {
            case "search":
                tweets = getSearchResults(keyword);
                break;
            case "timeline":
                tweets = getTimeline(keyword);
                break;
        }

        /** Scanner to read text file into a list **/
        ArrayList<String> filterWords;
        try (Scanner s = new Scanner(new File("src\\files\\filter.txt"))) {
            filterWords = new ArrayList<>();
            while (s.hasNext()) {
                filterWords.add(s.next());
            }
        }
        /** End of scanner  **/

        String result = "";

        for (Tweet tweet : tweets) {
            String[] contains = tweet.toString().split(" ");

            for (String contain : contains) {
                for (String filterWord : filterWords) {
                    if (contain.equalsIgnoreCase(filterWord)) {
                        result += tweet.toString();
                        filteredTweetCount ++;
                        break;
                    }
                }
            }
        }
        return result;
    }

    /**
     * This function converts a list of objects to a String.
     *
     * @param list refers to a list of tweets.
     * @return String of tweets.
     */
    public static String objectListToString(List list) {
        List<Tweet> tweets = list;

        String result = "";

        for (Tweet tweet : tweets) {
            result += tweets.toString();
        }

        return result;
    }

}
