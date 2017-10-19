package config

import com.typesafe.config.ConfigFactory

/**
  * This will hold a config for twitter streaming.
  */
// Stock Market Streaming App Setting
object Settings {
  //val parsedConfig = ConfigFactory.parseFile(new File("src/main/resources/application.conf"))

  private val config = ConfigFactory.load()
 // Spark settings
  object Spark {
    private val spark = config.getConfig("sparkstream")

    lazy val checkpointDirectory = spark.getString("checkpoint_directory")
    lazy val casandraConnectionHost = spark.getString("casandra_connection_host")
  }
  // Spark settings
  object Twitter {
    private val spark = config.getConfig("twitterstream")

    lazy val consumerKey = spark.getString("consumer_key")
    lazy val consumerSecret = spark.getString("consumer_secret")
    lazy val accessToken = spark.getString("access_token")
    lazy val accessTokenSecret = spark.getString("access_token_secret")
  }

  // Kafka settings
  object Kafka {
    private val kafkaLog = config.getConfig("kafkastream")

    lazy val kafka= kafkaLog.getString("kafka")
    lazy val consumer_group = kafkaLog.getString("consumer_group")
    lazy val bootstrap_servers_config= kafkaLog.getString("bootstrap_servers_config")
    lazy val zookeeper_quorum = kafkaLog.getString("zookeeper_quorum")
    lazy val key_serializer_class_config= kafkaLog.getString("key_serializer_class_config")
    lazy val value_serializer_class_config= kafkaLog.getString("value_serializer_class_config")
    lazy val acks_config= kafkaLog.getString("acks_config")
    lazy val client_id_config= kafkaLog.getString("client_id_config")
  }
  // WebLogGen settings
  object WebLogGen {
    private val weblogGen = config.getConfig("clickstream")

    lazy val records = weblogGen.getInt("records")
    lazy val timeMultiplier = weblogGen.getInt("time_multiplier")
    lazy val pages = weblogGen.getInt("pages")
    lazy val visitors = weblogGen.getInt("visitors")
    lazy val filePath = weblogGen.getString("file_path")
    lazy val destPath = weblogGen.getString("dest_path")
    lazy val numberOfFiles = weblogGen.getInt("number_of_files")
    lazy val kafkaTopic = weblogGen.getString("kafka_topic")
    lazy val hdfsPath = weblogGen.getString("hdfs_path")
  }
}


// Get your Twitter credentials at https://apps.twitter.com/


