package utils

import java.util.Properties
import kafka.serializer.StringDecoder
import org.apache.kafka.clients.producer.{KafkaProducer, Producer, ProducerConfig}
import org.apache.kafka.common.serialization.StringSerializer
import config.Settings
object KafkaUtils {

  type Key = String
  type Value = String
/*
  def getStringStream(zookeepers: String, group: String, kafkaTopic: String): Observable[Record[String, String]] = {
    new RxConsumer(
      zookeepers = zookeepers,
      group = group,
      autocommit = true,
      startFromLatest = true
    ).getRecordStream(
      topic = kafkaTopic,
      keyDecoder = new StringDecoder,
      valueDecoder = new StringDecoder
    )
  }

  def getStringProducer(servers: String): Producer[Key, Value] = {
    val props = new Properties()
    props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, servers)
    props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, classOf[StringSerializer].getCanonicalName)
    props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, classOf[StringSerializer].getCanonicalName)
    props.put(ProducerConfig.ACKS_CONFIG, Settings.Kafka.acks_config)
    props.put(ProducerConfig.CLIENT_ID_CONFIG, Settings.Kafka.client_id_config)

    new KafkaProducer[String, String](props)
  }

  def formatKafkaRecord(record: Record[Key, Value]) = {
    s"[kafka] ${record.topic} => ${record.partition} => ${record.offset}"
  }
*/
}