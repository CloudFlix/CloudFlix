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
		BufferedWriter bw = new BufferedWriter(fw);		
		String line;
		
		String director= new String();
		while( (line = br.readLine()) !=null){
		line=line.replaceAll("^[|]", "");
		bw.write(line+"\n");
			
			
			
			
	}
		bw.close();
		System.out.println("DONE!!!");
}
}