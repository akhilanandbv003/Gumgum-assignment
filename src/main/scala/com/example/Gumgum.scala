package com.example

import org.apache.spark.sql.{DataFrame, SparkSession}

object Gumgum {
  val spark = SparkSession
    .builder().master("local[4]").appName("ReadFromS3")
    .getOrCreate()

  import spark.implicits._

  def main(args: Array[String]): Unit = {
    val df = spark.read.json("src/main/resources/ad_data.json")
    nextPageUrl(df)
  }

  def nextPageUrl(df: DataFrame): DataFrame = {
    val data = df.as("e1").join(df.as("e2"), $"e1.visitorId" <=> $"e2.visitorId" && $"e2.timestamp" > $"e1.timestamp", "left")
    val output = data.select($"e1.*", $"e2.pageUrl".as("​nextPageUrl"))
    output.show()
    output
  }

}

