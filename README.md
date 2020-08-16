# ms3-challenge
 MS3 Junior Coding Challenge Solution REPO. 
 Assignment is to consume a large csv file, add complete records to a sqlite database, add the incomplete records to a csv file, and to log the number of success and failures.

# Set Environment variables

- Set JAVA_HOME environment variable
- Set CLASSPATH: 

`export CLASSPATH=.:./dependency/sqlite-jdbc-3.30.1.jar:./dependency/commons-csv-1.8.jar`

# Directory Structure

All of the resources (csv files, database files, and logs) will be stored in the resources directory. The .jar files for the dependences (Apache Commons CSV and SQLITE JDBC) are located in the /dependency directory.

# Compilation and Running

After cloning this repo, from the ms3-app directory: 

`javac MS3.java`
`java MS3 <input file name>`

Example: 

`java MS3 ./resources/ms3Interview.csv`

# Approach and Design

Frameworks used: Apache Commons CSV, SQLite JDBC

I chose to use a framework to help consume the csv file because it is a more streamlined approach to working with the files. 

I completed the project by the following steps: 

1. I first set up my environment - including CLASSPATH and JAVA_HOME variables
2. I tested the sqlite jdbc framework by opening a test db. 
3. Commenting out the sqlite code, I tested the Apache Commons CSV framework to consume the first line of the test file. I chose to look at the line by itself, then to go through each cell to make sure that it was not a `null` value. 
4. I wrote the entire outline for the functionalities I knew I would need: adding bad records to the csv, adding complete records to the database, opening the database and creating the table, and creating the log file. While I filled in these functions later, it was important for me to map out where each function would occur and how they would all connect. 
5. After I was sure that both frameworks were working well independently, I combined the two methods to add the complete records to a database. First for 50 lines, then 500, then continuing in this way to test the approach thoroughly. 
6. After I was sure the database was being updated, I implemented the writing to a csv file for the incomplete records. 
7. Finally I did the functionality to write to the log file. 

# Author

Claire Kolln, ckolln55@gmail.com
