#!/bin/bash

#This assumes that the script and the dockerfile are co-located
docker build -t spark-base:2.4 .

echo "Input file location is : $INPUT_LOCATION"
read -p "Enter INPUT_LOCATION: " INPUT_LOCATION
echo "Input file location entered is : $INPUT_LOCATION"

read -p "Enter OUTPUT_LOCATION: " OUTPUT_LOCATION
echo "Output file location is : $OUTPUT_LOCATION"

read -p "Enter SPARK_JAR_LOCATION: " SPARK_JAR_LOCATION
echo "Spark Jar location is : $SPARK_JAR_LOCATION"

# INPUT_LOCATION=/Users/avenk3/Desktop/myGithubRepos/Gumgum-assignment/src/main/resources/input/
# OUTPUT_LOCATION=/Users/avenk3/Desktop/myGithubRepos/Gumgum-assignment/src/main/resources/
# SPARK_JAR_LOCATION=/Users/avenk3/Desktop/myGithubRepos/Gumgum-assignment/src/main/resources/spark-jars/


docker run -it -v $INPUT_LOCATION:/userdata/spark-input -v $OUTPUT_LOCATION:/userdata/spark-output -v $SPARK_JAR_LOCATION:/userdata/spark-jars spark-base:2.4 /spark/bin/spark-submit --class com.example.Gumgum /userdata/spark-jars/Gumgum-assignment-0.0.1.jar /userdata/spark-input/part-00000-732eeb74-f251-4f96-85d5-5c9ae95ba709-c000.txt /userdata/spark-output/output001  