import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class DirectorFileGenerator {
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new FileReader("./input/directors4f.dat")); 

		FileWriter fw = new FileWriter(new File("./input/directors4a.dat"));
				
		String line;
		
		String director= new String();
		while( (line = br.readLine()) !=null){
		line=line.replaceAll("^[|]", "");
		fw.write(line+"\n");
			
			
			
			
	}
fw.flush();		
fw.close();
		System.out.println("DONE!!!");
}
}