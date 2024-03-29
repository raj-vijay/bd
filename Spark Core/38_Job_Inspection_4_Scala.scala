// Databricks notebook source

//-------------------------------------
// IMPORTS
//-------------------------------------
import org.apache.spark.{SparkConf, SparkContext}
import org.apache.log4j.Level
import org.apache.commons.io.FileUtils
import java.io._
import com.databricks.dbutils_v1.DBUtilsHolder.dbutils
import org.apache.spark.rdd.RDD

//-------------------------------------
// OBJECT MyProgram
//-------------------------------------
object MyProgram {

    // ------------------------------------------
    // INLINE FUNCTION processLine
    // ------------------------------------------
    val processLine: (String) => List[String] = (line: String) => {
        // 1. We create the output variable
        var res : List[String] = List[String]()

        // 2. We set the line to be split by " "
        var newLine = line.replace("\n", "")
        newLine = newLine.trim()
        newLine = newLine.replace("\t", " ")

        // 3. We get rid of chars not being either a letter or a " "
        var totalSize = newLine.length()
        var index = totalSize - 1

        // 3.1. We traverse all characters
        while (index >= 0) {
            // 3.1.1. We get the ord of the character at position index
            val charVal : Int = (newLine.apply(index)).toInt

            // 3.1.2. If (1) char_val is not " " and (2) char_val is not an Upper Case letter and (3) char_val is not a Lower Case letter
            if ( (charVal != 32) && ((charVal < 65) || (charVal > 90)) && ((charVal < 97) || (charVal > 122)) ){
                // 3.1.2.1. We remove the index from the sentence
                newLine = newLine.substring(0, index) + newLine.substring(index+1, totalSize)
                totalSize = totalSize - 1
            }
            // 3.1.3. If the character was an upper case letter
            else if ((charVal >= 65) && (charVal <= 90)) {
                // 3.1.3.1. We add it as lower case
                newLine = newLine.substring(0, index) + ((charVal + 32).toChar).toString + newLine.substring(index+1, totalSize)
            }

            //3.1.4. We continue with the next index
            index = index - 1
        }

        // 4. We get the list of words
        val words = (newLine.split(" ")).toList

        // 4.1. We remove the empty ones
        res = words.filter(_.length() > 0)

        // 5. We return res
        res
    }

    // ------------------------------------------
    // FUNCTION myMain
    // ------------------------------------------
    def myMain(sc: SparkContext, myDatasetDir: String): Unit = {
        // 1. Operation C1: Creation 'textFile'
        val inputRDD: RDD[String] = sc.textFile(myDatasetDir)

        // 2. Operation T1: Transformation 'flatMap'
        val allWordsRDD: RDD[String] = inputRDD.flatMap(processLine)

        // 3. Operation T2: Transformation 'map'
        val pairWordsRDD: RDD[(String, Int)] = allWordsRDD.map( (x: String) => (x, 1) )

        // 4. Operation T3: Transformation 'reduceByKey'
        val countRDD: RDD[(String, Int)] = pairWordsRDD.reduceByKey( (x: Int, y: Int) => x + y )

        // 5. Operation T4: Transformation 'map', to screw up the partitioner
        val screwRDD: RDD[(Int, String)] = countRDD.map( (x: (String, Int)) => (x._2, x._1) ) 
      
        // 6. Operation T5: Transformation 'filter', to reduce the amount of words
        val filteredRDD: RDD[(Int, String)] = screwRDD.filter( (x: (Int, String)) => x._1 > 10000 ) 
      
        // 7. Operation T6: Transformation 'sortByKey', so as to order the entries by decreasing order in the number of appearances.
        val solutionRDD: RDD[(Int, String)] = filteredRDD.sortByKey()

        // 8. Operation A1: Action 'collect'
        val resVAL: Array[(Int, String)] = solutionRDD.collect()
        for (item <- resVAL){
          println(item)
        }

    }

    // ------------------------------------------
    // PROGRAM MAIN ENTRY POINT
    // ------------------------------------------
    def main(args: Array[String]) {
        // 1. We use as many input arguments as needed

        // 2. Local or Databricks
        val localFalseDatabricksTrue = true

        // 3. We setup the log level
        val logger = org.apache.log4j.Logger.getLogger("org")
        logger.setLevel(Level.WARN)

        // 4. We set the path to my_dataset and my_result
        val myLocalPath = "../../"
        val myDatabricksPath = "/"

        var myDatasetDir : String = "FileStore/tables/1_Spark_Core/my_dataset/"

        if (localFalseDatabricksTrue == false) {
            myDatasetDir = myLocalPath + myDatasetDir
        }
        else {
            myDatasetDir = myDatabricksPath + myDatasetDir
        }

        // 5. We configure the Spark Context sc
        var sc : SparkContext = null;

        // 5.1. Local mode
        if (localFalseDatabricksTrue == false){
            // 5.1.1. We create the configuration object
            val conf = new SparkConf()
            conf.setMaster("local")
            conf.setAppName("MyProgram")

            // 5.1.2. We initialise the Spark Context under such configuration
            sc = new SparkContext(conf)
        }
        // 5.2. Databricks Mode
        else{
            sc = SparkContext.getOrCreate()
        }
        println("\n\n\n");

        // 6. We call to myMain
        myMain(sc, myDatasetDir)
    }

}

//------------------------------------------------
// TRIGGER: Local (Comment) - Databricks (Enable)
//------------------------------------------------
MyProgram.main(Array())
