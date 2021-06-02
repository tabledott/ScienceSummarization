import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

public class Research {
	
	private static ArrayList<String> parseDocument(String docText)
	{
		final int CHUNK_SIZE = 1024;
		ArrayList<String> result = new ArrayList<String>();
		docText = docText.replaceAll("<.*?>", "");
		int counter = 0; 
		while(counter*CHUNK_SIZE <  docText.length())
		{
			result.add(docText.substring(counter*CHUNK_SIZE, Math.min(docText.length(), (counter+1)*CHUNK_SIZE)));
			counter++;
		}
		return result;
	}
	
    private static String readLineByLineJava8(String filePath) 
    {
        StringBuilder contentBuilder = new StringBuilder();
 
        try (Stream<String> stream = Files.lines( Paths.get(filePath), StandardCharsets.UTF_8)) 
        {
            stream.forEach(s -> contentBuilder.append(s).append("\n"));
        }
        catch (IOException e) 
        {
            e.printStackTrace();
        }
 
        return contentBuilder.toString();
    }

	
	private static void WriteText(String filePath, String fileName) throws IOException
	{
		String saveDir = "C:\\Users\\TodorTsonkov\\Desktop\\NewPaper";
		String articleText = readLineByLineJava8(filePath);
		ArrayList<String> parsedData = parseDocument(articleText);
		for (int i  = 0; i < parsedData.size(); i++ )
		{
		    BufferedWriter writer = new BufferedWriter(new FileWriter(saveDir + "\\" + fileName + i));
		    writer.write(parsedData.get(i));
		    writer.close();
		}
	}
	
	public static void main(String[] args) throws IOException
	{
		String dir = "C:\\Users\\TodorTsonkov\\Desktop\\NewPaper\\scisummnet_release1.1__20190413\\scisummnet_release1.1__20190413\\top1000_complete";
        File[] files = new File(dir).listFiles();
        for ( File f : files)
        {
			String filePath = dir + "\\" + f.getName().toString() + "\\Documents_xml\\" + f.getName() + ".xml";
			WriteText(filePath, f.getName().toString());
        }
	}

}
