package Apppackage;

import java.io.FileNotFoundException;


public class Main {
    public static void main(String args[]) throws FileNotFoundException {
        System.out.println(TwitterCrawler.getFilteredResults("Ahoy", "search"));
        //System.out.println(TwitterCrawler.filteredTweetCount);
        //TwitterCrawler.getSearchResults("markthal");
    }
    
}
