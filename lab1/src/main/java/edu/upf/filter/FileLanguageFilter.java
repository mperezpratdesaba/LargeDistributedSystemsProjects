package edu.upf.filter;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.Optional;

import edu.upf.parser.SimplifiedTweet;

public class FileLanguageFilter implements LanguageFilter{
    final String inputFile;
    final String outputFile;
    int counter;

    public FileLanguageFilter (String inputFile, String outputFile){
        this.inputFile = inputFile;
        this.outputFile = outputFile;
        this.counter = 0;
    }

    @Override
    public void filterLanguage (String language){        
        try{
            BufferedReader bReader = new BufferedReader(new FileReader(this.inputFile));
            BufferedWriter bWriter = new BufferedWriter(new FileWriter(this.outputFile));
            for (String line = bReader.readLine(); line != null; line = bReader.readLine()) {
                Optional<SimplifiedTweet> tweet = SimplifiedTweet.fromJson(line);
                if (tweet.isPresent() && tweet.get().getLanguage().equals(language)){
                    bWriter.write(tweet.get().toString());
                    bWriter.write("\n");
                    this.counter++;
                }
            }
        
            bReader.close();
            bWriter.close();
        }
        catch(Exception e){
            System.err.println("Error: Target File Cannot Be Read");
        }
    }

    public int getCounter(){
        return this.counter;
    }
}
