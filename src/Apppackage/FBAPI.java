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
        FacebookClient fbClient = new DefaultFacebookClient("CAACEdEose0cBAFaEHDiH3cwMNUE5xFd66zCS7mVN0uWG7wZCQJNmtSsNXoNP2opIPVafthAvGj0ZA5VvLwk8Pi2JuPKGVY0offJCGUbZBloXTTgGrZBda1HfyAcsoB9yXPhBVZC6FNQWYH3ZC5AOh1DJBXgEglzgxjVwMkAnZA3qTfH8LpdpXme3iLlmE1s6rvGUhZBv2C8aYcJFIXljyPnE");// HIER MOET EEN ACCES TOKEN
        Page page = fbClient.fetchObject("165327986836920", Page.class);  //ID van de pagina
        BatchRequest postsRequest =  new BatchRequestBuilder("ahoyrotterdam/posts").parameters(Parameter.with("limit", 10)).build();
        List<BatchResponse> batchResponses = fbClient.executeBatch(postsRequest);
        BatchResponse ahoyResponse = batchResponses.get(0);
        Connection<Post> ahoyPosts = new Connection<>(fbClient, ahoyResponse.getBody(), Post.class);
        
        for (List<Post> ahoyPostsConnectionPage : ahoyPosts){
            for (Post post : ahoyPostsConnectionPage){
                //System.out.println("=================================\n" + post.getMessage());
                Post.Comments coms = post.getComments();
                System.out.println(post.getCommentsCount());
                if(post.getCommentsCount() <= 0){
                    System.out.println("- " + post.getId() + " has no comments");
                }  
                else{
                    DatabaseManager dbManager = new DatabaseManager();
                    System.out.println("Adding comments of " + post.getId() + " to the Database.");
                    dbManager.addFBCommentsToDB(coms);
                }
            }
        }
    }
}
