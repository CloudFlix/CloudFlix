package filemaker;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;



public class DirectorFileGenerator {
	
	private static HashMap<String,String> patternsToFind = new HashMap<String,String>();

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new FileReader("./input/directors4a.dat")); 
		BufferedReader br1 = new BufferedReader(new FileReader("./input/movies1.dat")); 
		
		FileWriter fw = new FileWriter(new File("./input/directors4aa.dat"));
		BufferedWriter bw = new BufferedWriter(fw);		
		String line;
		
		while( (line = br1.readLine()) !=null){
			String[] patternWords = line.trim().split("::");
			
			if(patternWords[1].contains(", The")){
				patternWords[1]= patternWords[1].replace(", The","");
				patternWords[1]= "The "+patternWords[1];
				System.out.println(patternWords[1]);
			}
			patternsToFind.put(patternWords[1], patternWords[0]);
			
		}
		System.out.println(patternsToFind.toString());
		while( (line = br.readLine()) !=null){
			
			String[] array = line.split("[|]");
			for (int i = 1; i < array.length; i++) {
				
				if(patternsToFind.containsKey(array[i]) && array[0].contains(",")&&!array[0].contains(")")){
					bw.write(patternsToFind.get(array[i])+ "|"+ array[0]+"\n");
				}
			}
		}
bw.close();
		System.out.println("DONE!!!");
}
}