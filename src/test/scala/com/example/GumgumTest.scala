package com.example

import com.github.mrpowers.spark.fast.tests.DatasetComparer
import org.scalatest.FunSuite

class GumgumTest extends FunSuite with SparkSessionTestWrapper with DatasetComparer {

test("Assert nextPageUrl returns the right ​nextPageUrl"){
  val sourceDF = spark.createDataFrame(Seq(
    ("1","001","view","user001","abc.com"),
    ("2","002","view","user002","xyz.com"),
    ("3","003","view","user001","xyz.com")
  )).toDF("id","timestamp","type","visitorId","pageUrl")

  val actualDF = Gumgum.nextPageUrl(sourceDF)

  val expectedDF = spark.createDataFrame(Seq(
    ("1","001","view","user001","abc.com","xyz.com"),
    ("2","002","view","user002","xyz.com",null),
    ("3","003","view","user001","xyz.com",null)
  )).toDF("id","timestamp","type","visitorId","pageUrl","nextPageUrl")

  assertSmallDatasetEquality(actualDF, expectedDF)
}
}
