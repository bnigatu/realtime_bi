package actor


object Cells {

  import java.util.Properties
  import org.apache.kafka.clients.producer.{KafkaProducer,ProducerConfig}


  val URLBase = "http://download.finance.yahoo.com/d/quotes.csv?e=.csv&f="
  val URLEnd = "&s="
  val stockDayFormat = "lsnpd1oml1vq"


  val kafkaIP = "localhost"
  val props = new Properties()
  props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, s"${kafkaIP}:9092")
  props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer")
  props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer")
  val producer = new KafkaProducer[String, String](props)






  //Schedules to send the "foo"-message to the testActor after 50ms
  val consideredSymbols = List(
    "DIA","AAPL","AXP","BA","CAT","CSCO","CVX","DD","DIS","GE","GS","HD","IBM","INTC","JNJ",
    "JPM","KO","MCD","MMM","MRK","MSFT","NKE","PFE","PG","TRV","UNH","UTX","VZ","WMT","XOM"
  )

}
