package main;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.io.IOException;
import java.net.MalformedURLException;

import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlDivision;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

public class Test {

    public static void main(final String[] args) {
    	final WebClient webClient = new WebClient();
    	
    	//String chk = "HtmlUnit - Welcome to HtmlUnit";
    	
    	try {
    		final HtmlPage page = webClient.getPage("http://www.imdb.com/title/tt0405159/reviews?start=0");
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
    }

}