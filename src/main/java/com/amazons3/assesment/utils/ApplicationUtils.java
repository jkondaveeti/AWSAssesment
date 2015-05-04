package com.amazons3.assesment.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;
/**
 * Provides static utility methods for application which can be called using class name reference
 * @author andromeda
 *
 */
public class ApplicationUtils {
	
	 /**
	  * creates a sample file with the given file name and the list of strings as data.
	  * @param fileName
	  * @param fileDetails
	  * @return
	  * @throws IOException
	  */
	 public static File createSampleFile(String fileName, List<String> fileDetails) throws IOException {
	        File file = File.createTempFile(fileName, ".txt");
	        file.deleteOnExit();
	        Writer writer = new OutputStreamWriter(new FileOutputStream(file));
	        for(String line : fileDetails) {
	        	writer.write(line+"\n");
	        }      
	        writer.close();
	        return file;
	    }
	 
	 /**
	  * Utility method which return a list of string 
	  * @param numberOfLines
	  * @return
	  */
	 public static List<String> createSampleData(int numberOfLines) {		
		 List<String> sampleData = new ArrayList<String>();	 
		 for(int i=0; i< numberOfLines; i++) {
			 sampleData.add("Sample message text");
		 }
		 return sampleData;
	 }
}
