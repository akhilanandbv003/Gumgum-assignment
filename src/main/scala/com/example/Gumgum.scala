package com.example

import org.apache.spark.sql.{DataFrame, SparkSession}

object Gumgum {
  val spark = SparkSession
    .builder().master("local[4]").appName("ReadFromS3")
    .getOrCreate()

  import spark.implicits._

  def main(args: Array[String]): Unit = {
//    val fileLocation = args(0)
    val df = spark.read.json("src/main/resources/part-00000-732eeb74-f251-4f96-85d5-5c9ae95ba709-c000.txt")
//    val df = spark.read.json(fileLocation)
    val output = nextPageUrl(df)
//    output.write.mode("overwrite").json(args(1))
    output.coalesce(1).write.mode("overwrite").json("src/main/resources/output")
  }

  def nextPageUrl(df: DataFrame): DataFrame = {
    val data = df.as("e1").join(df.as("e2"), ($"e1.visitorId" <=> $"e2.visitorId") && ($"e2.timestamp" > $"e1.timestamp"), "left")
    val output = data.select($"e1.*", $"e2.pageUrl".as("nextPageUrl"))
    output.count()
    output.show()
    output
  }

  //



}

