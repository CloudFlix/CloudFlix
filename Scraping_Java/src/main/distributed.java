package main;

import java.util.regex.Matcher;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

	import org.apache.hadoop.fs.Path;
import org.apache.hadoop.conf.*;
import org.apache.hadoop.io.*;
import org.apache.hadoop.mapred.*;
import org.apache.hadoop.util.*;

import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
	//class that contains map and reduce
	public class distributed {
	
	    public static class Map extends MapReduceBase implements Mapper<LongWritable, Text, Text, Text> {
	      private final static IntWritable one = new IntWritable(1);
	      private String imdb_id;
	      private Text link_localID = new Text();
	      private String link = "http://www.imdb.com/title/tt/reviews?start=0";
	      
	      //map function makes (key,value) of (word,occurence) 
	      public void map(LongWritable key, Text value, OutputCollector<Text, Text> output, Reporter reporter) throws IOException {
	        String line = value.toString();
	        StringTokenizer tokenizer = new StringTokenizer(line,"::");
	        
	        if(tokenizer.hasMoreTokens()) 
	          imdb_id = tokenizer.nextToken();
	        
	        link.replaceAll("tt", "tt"+imdb_id);
	        
	        if(tokenizer.hasMoreTokens()) 
		      link_localID.set(link+"::"+tokenizer.nextToken());        
	                
	        output.collect(new Text(imdb_id), link_localID);        	        
	        
	      }
	    }
	
	    public static class Reduce extends MapReduceBase implements Reducer<Text, Text, Text, Text> {
	      public void reduce(Text key, Iterator<Text> values, OutputCollector<Text, Text> output, Reporter reporter) throws IOException {
	        String link;
	        String localID;
	        String reviews = "";
	        
	    	if(values.hasNext())
	    	{
	    	  String link_localID[]=values.next().toString().split("::");
	    	  link = link_localID[0];
	    	  localID = link_localID[1];
	    	
	    	
	    	  
	        //if () {
	        	final WebClient webClient = new WebClient();
	        	try {
	        		final HtmlPage page = webClient.getPage(link);
	        		final String page2 = page.asXml();
	        		//System.out.println(page2);
	        		//Pattern pattern = Pattern.compile("Author:.*?<p>\\s*(.*)\\s*?</p>");
	        		Pattern pattern = Pattern.compile("Author:.*?</div>.*?<p>(.*?)</p>", Pattern.DOTALL);    		
	        		Matcher matcher = pattern.matcher(page2);
	        		int i = 1;
	        		while (matcher.find()) {
	        			  System.out.printf("Review: %d\n", i);
	        		      //System.out.print("Start index: " + matcher.start());
	        		      //System.out.print(" End index: " + matcher.end() + " ");
	        		      String temp = matcher.group(1);
	        		      //System.out.println(temp);
	        		      temp =temp.replaceAll("<p>.*?This review may contain spoilers.*?</p>", "");
	        		      temp =temp.replaceAll("\\s*<.*?>\\s*", " ");
	        		      temp =temp.replaceAll("\\n", " ");
	        		      temp =temp.replaceAll("[^A-Za-z0-9 _\\.,:;\\!\"\'/$\\-\\(\\)&]", "");
	        		      temp =temp.replaceAll("\\s+", " ");
	        		      temp =temp.replaceAll("^\\s+", "");
	        		      temp =temp.replaceAll("\\s+$", "");
	        		      System.out.println(temp);
	        		      i++;
	        		      reviews += "||"+temp;
	        		}
	        		
	        	} catch (final FailingHttpStatusCodeException e) {
	                System.out.println("One");
	                e.printStackTrace();
	            } catch (final MalformedURLException e) {
	                System.out.println("Two");
	                e.printStackTrace();
	            } catch (final IOException e) {
	                System.out.println("Three");
	                e.printStackTrace();
	            } catch (final Exception e) {
	                System.out.println("Four");
	                e.printStackTrace();
	            }
	            //System.out.println("Finished");
	            

	            //final String pageAsXml = page.asXml();
	            //Assert.assertTrue(pageAsXml.contains("<body class=\"composite\">"));

	            //final String pageAsText = page.asText();
	            //Assert.assertTrue(pageAsText.contains("Support for the HTTP and HTTPS protocols"));

	            webClient.closeAllWindows();
	        
	        //}
	        output.collect(new Text(localID), new Text(reviews));
	    	}
	      }
	    }
	
	    public static void main(String[] args) throws Exception {
	      JobConf conf = new JobConf(distributed.class);
	      conf.setJobName("Distributed_Scraping");
	
	      //Set io types
	      conf.setOutputKeyClass(Text.class);
	      conf.setOutputValueClass(Text.class);
	
	      conf.setMapperClass(Map.class);
	      conf.setReducerClass(Reduce.class);
	
	      conf.setInputFormat(TextInputFormat.class);
	      conf.setOutputFormat(TextOutputFormat.class);
	
	      FileInputFormat.setInputPaths(conf, new Path(args[0]));
	      FileOutputFormat.setOutputPath(conf, new Path(args[1]));
	      //Start MapReduce
	      JobClient.runJob(conf);
	    }
	}
