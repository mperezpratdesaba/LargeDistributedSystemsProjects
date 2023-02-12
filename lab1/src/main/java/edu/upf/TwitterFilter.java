package edu.upf;

import edu.upf.filter.FileLanguageFilter;
import edu.upf.uploader.S3Uploader;

import java.io.IOException;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.Arrays;
import java.util.List;

//TwitterFilter acts as the main class

public class TwitterFilter {
    public static void main( String[] args ) throws IOException {
        List<String> argsList = Arrays.asList(args);
        String language = argsList.get(0);
        String outputFile = argsList.get(1);
        String bucket = argsList.get(2);
        System.out.println("Language: " + language + ". Output file: " + outputFile + ". Destination bucket: " + bucket);

        int counter = 0;
        long start = System.currentTimeMillis();

        for(String inputFile: argsList.subList(3, argsList.size())) {
            System.out.println("Processing: " + inputFile);
            final FileLanguageFilter filter = new FileLanguageFilter(inputFile, outputFile);
            filter.filterLanguage(language);
            counter = counter + filter.getCounter();
        }     

        final S3Uploader uploader = new S3Uploader(bucket, language);
        uploader.upload(Arrays.asList(outputFile));

        long stop = System.currentTimeMillis();
        System.out.println("Done in ms: " + (stop-start));
        System.out.println("Tweets count: " + counter);
    }
}
