import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class Processor {
	
	private static HashMap<String, List<String>> documentsBySummary;
	
	private static void prepareDocumentsById(String row){
		try {
			String[] data = row.split(",");
			String key = data[0];
			String[] parsedFullTextHeaders = data[1].split(" ");
			documentsBySummary.put(key, Arrays.asList(parsedFullTextHeaders));
		}
		catch(Exception ex)
		{
			ex.printStackTrace();  
		}
	}
	
	private static void ReadIdentifiers() {
		String fileName = "E:\\Valio-Summarizations\\data\\identifiers_cleaned.csv";
		File file=new File(fileName);    //creates a new file instance  
		
		try {
			FileReader fr=new FileReader(file);   //reads the file  
			BufferedReader br=new BufferedReader(fr);  //creates a buffering character input stream  
			String line;  
			while((line=br.readLine())!=null)  
			{  
				prepareDocumentsById(line);
			}  		
			fr.close();  
		}//closes the stream and release the resources    
		catch(IOException e) {  
			e.printStackTrace();  
		}  
	}
	
	private static double calculateRatio()
	{
		int document_size = 0;
		double result = 0.0;

		try {
			ReadIdentifiers();
	        for (var entry : documentsBySummary.entrySet()) {
	            
	            String fileSummary = "E:\\Valio-Summarizations\\txt_files\\summary\\" + entry.getKey() + ".txt";
	            var summarySize = Files.readString(Path.of(fileSummary)).length();
	            for(var fullTextName : entry.getValue())
	            {
	            	String fullTextFile =  "E:\\Valio-Summarizations\\txt_files\\full\\" + fullTextName + ".txt";
		            var fullTextSize = Files.readString(Path.of(fullTextFile)).length();
		            double ratio = (double)summarySize/fullTextSize;
		            if (ratio < 1) {
		            	result+= ratio;
		            	document_size++;
			            System.out.println(ratio + " " + document_size + " " + result);
		            }
	            }
	        }
		}
		catch(Exception ex) {
			ex.printStackTrace();  			
		}
        if (document_size!=0) {
    		return result/document_size;        	
        }
        return 0.0;
	}
	
	public static void main(String[] args)
	{
		documentsBySummary = new HashMap<String, List<String>>();
		System.out.println(calculateRatio());
	}

}
