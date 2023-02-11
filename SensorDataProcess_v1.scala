import scala.io.Source
import java.io.File

object SensorDataProcess_v1 extends App{
  
  var key = "example_key"
  var value = 42.0
  var c = 0
  var total = 0.0 
  var avg = 0.0
  var min = 0.0
  var max = 0.0
  var map = Map[String, List[Double]]() 
  var totalFiles = 0
  var totalLines = 0
  
  // Replace the folder path 
  val folderPath = "C:\\Users\\nagar\\OneDrive\\Documents\\Veeresh\\dataset\\dataset\\sensor_datafiles"

  val folder = new File(folderPath)
  val files = folder.listFiles.filter(_.getName.endsWith(".csv"))
  
  for (file <- files) {
    val source = Source.fromFile(file)
    val lines = source.getLines.toList.drop(1) // remove header
    
    for (line <- lines) {
      key = line.split(",")(0)
      value = line.split(",")(1).toDouble
      if ( line.split(",")(1) == "NaN" ) {
        c = c + 1
      }

      if (map.contains(key)) {
        map = map + (key -> (map(key) :+ value))
      } else {
        map = map + (key -> List(value))
      }      
    }
    source.close
    
    totalFiles += 1
    totalLines += lines.length
  }
  
  println("sensor-id,min,avg,max")  
  for ((key, value) <- map) {
      val numbers = value.filter {
        case i: Double => true
        case _ => false
      }.map {
        case i: Double => i
      }
      
    
      total = numbers.sum
      max = numbers.max
      avg = total / numbers.size
      min = numbers.min   

      println(s"$key, $min,$avg,$max")
  }

  println("*********************************************")
  println(s"Num of processed files: $totalFiles")
  println(s"Num of processed measurements: $totalLines")
  println(s"Number of failed meaasurements: $c")
}