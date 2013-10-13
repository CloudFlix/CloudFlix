import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;


public class MoviesFileGenerator {
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new FileReader("./input/movies.dat")); 
		FileWriter fw = new FileWriter(new File("./input/movies1.dat"));
		BufferedWriter bw = new BufferedWriter(fw);		
		String line;
		while( (line = br.readLine()) !=null){

			String find=line.substring(line.lastIndexOf(":")-8,line.lastIndexOf(":")-1);

			String year=line.substring(line.lastIndexOf(":")-6,line.lastIndexOf(":")-2);
			line= line.replace(find,"::"+year);
			bw.write(line+"\n");
			
			
	}
		bw.close();
		System.out.println("DONE!!!");
}
	
}
