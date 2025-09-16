import org.apache.spark.SparkContext
import org.apache.spark.SparkConf

object Main {
  def main(args: Array[String]) = {

    val conf = new SparkConf()
      .setAppName("test")
      .setMaster("local[4]") // run locally with parallelism of 4
    val sc = SparkContext.getOrCreate(conf)
    val rdd = sc.textFile("countries.txt")

    val averageNameLengthPerFirstLetter = rdd.map(x => (x(0), x))
      .aggregateByKey((0, 0))(
        { case ((count, length), name) => (count + 1, length + name.length) },
        { case ((count1, length1), (count2, length2)) => (count1 + count2, length1 + length2) }
      )
      .mapValues { case (count, length) => length.toDouble / count }

    val result = averageNameLengthPerFirstLetter.collectAsMap()
    System.out.println(result)

    sc.stop();
  }
}
