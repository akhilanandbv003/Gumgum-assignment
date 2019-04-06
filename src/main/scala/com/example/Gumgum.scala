package com.example

import org.apache.spark.sql.{DataFrame, SparkSession}

object Gumgum {
  val spark = SparkSession
    .builder().master("local[*]").appName("Gumgum-assignment")
    .getOrCreate()

  import spark.implicits._

  def main(args: Array[String]): Unit = {
    val fileLocation = args(0)
    //    val fileLocation = "src/main/resources/part-00000-732eeb74-f251-4f96-85d5-5c9ae95ba709-c000.txt"

    val inputDf = readJsonFile(spark, fileLocation)
    val output: DataFrame = nextPageUrl(inputDf)
    saveAsJsonFile(output, fileLocation)
  }

  def readJsonFile(spark: SparkSession, fileLocation: String) = {
    spark.read.json(fileLocation)
  }

  def nextPageUrl(df: DataFrame): DataFrame = {
    val data = df.as("e1").join(df.as("e2"), $"e1.visitorId" === $"e2.visitorId" && $"e2.timestamp" > $"e1.timestamp", "left") //82145
    //    val data = df.as("e1").join(df.as("e2"), $"e1.visitorId" <=> $"e2.visitorId" && $"e2.timestamp" > $"e1.timestamp", "left") //134,231,789
    //    val data = df.as("e1").join(df.as("e2"), $"e1.visitorId" === $"e2.visitorId", "left").filter($"e2.timestamp" > $"e1.timestamp") //22,289 same as sql
    val output = data.select($"e1.*", $"e2.pageUrl".as("nextPageUrl"))
    output
  }

  def saveAsJsonFile(df: DataFrame, location: String) = {
    df.write.json(location)
    //    output.write.mode("overwrite").option("compression", "gzip").json(args(1))
    //    output.coalesce(1).write.mode("overwrite").json("src/main/resources/output")
  }


}

