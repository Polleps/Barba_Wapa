/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Apppackage;

import com.restfb.Connection;
import com.restfb.DefaultFacebookClient;
import com.restfb.FacebookClient;
import com.restfb.Parameter;
import com.restfb.batch.BatchRequest;
import com.restfb.batch.BatchRequest.BatchRequestBuilder;
import com.restfb.batch.BatchResponse;
import com.restfb.types.Comment;
import com.restfb.types.Page;
import com.restfb.types.Post;
import java.util.List;

/**
 *
 * @author Polle
 */
public class FBAPI {
    
    public void getFbData(){
        FacebookClient fbClient = new DefaultFacebookClient("CAACEdEose0cBALhuVdtZAxErkkXU4FNS3XaxESHID0ozSrP7KUWwZBSAIqMxS1zGNw2PYDY5AdKTkRz6NZCljfIeE9ywKQWYqvvZAjxGKZCL9PuWQal0fnt1k2bhDyY1ZAljgtlaQYaktd8dE5S2VSCXHJzuOWMgUHBc8ZAqZCK5XhtEJPW92LBV529L9Xh37JkHDlzjf2Ehg7j58xP9rAGR");// HIER MOET EEN ACCES TOKEN
        Page page = fbClient.fetchObject("165327986836920", Page.class);  //ID van de pagina
        BatchRequest postsRequest =  new BatchRequestBuilder("ahoyrotterdam/feed").parameters(Parameter.with("limit", 5)).build();
        List<BatchResponse> batchResponses = fbClient.executeBatch(postsRequest);
        BatchResponse ahoyResponse = batchResponses.get(0);
        Connection<Post> ahoyPosts = new Connection<>(fbClient, ahoyResponse.getBody(), Post.class);
        for (List<Post> ahoyPostsConnectionPage : ahoyPosts){
            for (Post post : ahoyPostsConnectionPage){
                //System.out.println("=================================\n" + post.getMessage());
                Post.Comments coms = post.getComments();
                DatabaseManager dbManager = new DatabaseManager();
                dbManager.addCommentsToDB(coms);
                  
            }
        }
    }
}
