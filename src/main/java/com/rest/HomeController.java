package com.rest;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.json.JSONObject;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rest.exceptions.ClientException;
import com.rest.exceptions.NotFoundException;
import com.rest.exceptions.WorkFlowException;
import com.rest.utils.PostUtils;

@RestController
@RequestMapping("/")
public class HomeController {

    public static CloseableHttpClient httpclient = HttpClients.createDefault();
	
	@RequestMapping("/posts")
	public String getUsers() throws ClientProtocolException, IOException {
				  
    	HttpGet httpget = new HttpGet("https://jsonplaceholder.typicode.com/posts");
    	HttpResponse response = httpclient.execute(httpget);
    	String responseString = new BasicResponseHandler().handleResponse(response);
   	
		return responseString;
	}
	
	@RequestMapping(value = "/posts/{id}", method = RequestMethod.GET)
	public Post getPostById(@PathVariable long id) throws JsonParseException, JsonMappingException, IOException{
	
		HttpGet httpget = new HttpGet("https://jsonplaceholder.typicode.com/posts/"+id);
    	HttpResponse response;
    	String responseString = "";
		try {
			response = httpclient.execute(httpget);
			responseString = new BasicResponseHandler().handleResponse(response);
		} catch (IOException e) {
			throw new NotFoundException("Couldn't find post with id : " + id);
		}
   	
		Post post = new ObjectMapper().readValue(responseString, Post.class);
		
		return post;		
	}

	@RequestMapping(value = "/posts", method = RequestMethod.POST)
    public Response createPost(@RequestBody Post post) throws ClientException, WorkFlowException {

        PostUtils.validatePost(post);
        
        httpclient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost("https://jsonplaceholder.typicode.com/posts");
        
        ObjectMapper mapper = new ObjectMapper();
        try {
        	String stringPost = mapper.writeValueAsString(post);       	
        	StringEntity entity = new StringEntity(stringPost);
        	httpPost.setEntity(entity);
        	
        	CloseableHttpResponse response = httpclient.execute(httpPost);
        	if(response.getStatusLine().getStatusCode() == 404) 
        	    throw new NotFoundException("Post not found");
        	else {
        		return new Response(response.getStatusLine().getStatusCode(),response.getStatusLine().getReasonPhrase());
        	}
        } catch(IOException e) {
        	throw new WorkFlowException(e.getMessage());
        }
    }
	@RequestMapping(value="/posts/{id}", method = RequestMethod.PUT)
	public String updatePost(@PathVariable long id, @RequestBody Post post) throws ClientProtocolException {
		
		httpclient = HttpClients.createDefault();
		HttpGet httpGet = new HttpGet("https://jsonplaceholder.typicode.com/posts/"+id);
		String putResponseString = null;
		try {
		  CloseableHttpResponse getResponse = httpclient.execute(httpGet);
		  String responseString = new BasicResponseHandler().handleResponse(getResponse);
		
		  Post existingPost = new ObjectMapper().readValue(responseString, Post.class);
		
	      Post postToPut = PostUtils.validateUpdatedPost(post, existingPost);
		
		  ObjectMapper mapper = new ObjectMapper();
		  String postToPutString = mapper.writeValueAsString(postToPut);
		
		  HttpPut httpPut = new HttpPut("https://jsonplaceholder.typicode.com/posts/"+id);
		  StringEntity entity = new StringEntity(postToPutString);
		  httpPut.setEntity(entity);
		
		  CloseableHttpResponse putResponse = httpclient.execute(httpPut);
		  putResponseString = new BasicResponseHandler().handleResponse(putResponse);
		} catch(IOException e) {
			throw new WorkFlowException(e.getMessage());
		}
		  return putResponseString;		
	}
	
	@RequestMapping(value="/posts/{id}", method = RequestMethod.DELETE)
	public Response deletePost(@PathVariable long id) throws ClientProtocolException, IOException {
		
		httpclient = HttpClients.createDefault();
		HttpDelete httpDelete = new HttpDelete("https://jsonplaceholder.typicode.com/posts/"+id);
		
		CloseableHttpResponse response = httpclient.execute(httpDelete);
		
		if(response.getStatusLine().getStatusCode() == 200) {
			return new Response(response.getStatusLine().getStatusCode(), "Post successfully deleted!");
		} else {
			return new Response(response.getStatusLine().getStatusCode(),response.getStatusLine().getReasonPhrase());
		}
	}
}
