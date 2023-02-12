
Names:

Marc Pérez Pratdesaba

Gael Ribes Victor

Sergi Hernández Burbano de Lara


# LSDS2021-lab1

This is a java project to parse the data of some Tweets related to Eurovision 2018.

The program goes through 6 GB of Twitter data, filters by language and simplifies the contents. This way we get a smaller .json of a few MB (around 30 MB). Then, we upload the file to an S3 bucket.

## Execution

```
java -cp <jarfile> edu.upf.TwitterFilter <language> <outputFile> <bucketName> <inputFile1> <inputFile2> ... <inputFileN>
```



