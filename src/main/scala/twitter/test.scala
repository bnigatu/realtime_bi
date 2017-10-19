package twitter

import config.Settings
import org.apache.spark.streaming.dstream.DStream
import org.apache.spark.streaming.twitter.TwitterUtils
import org.apache.spark.streaming.{Seconds, StreamingContext}
import org.apache.spark.{SparkConf, SparkContext}
import twitter4j.Status
import utils.Utils._

object test extends App {

  // Set the system properties so that Twitter4j library used by Twitter stream
  // can use them to generate OAuth credentials
  System.setProperty("twitter4j.oauth.consumerKey", Settings.Twitter.consumerKey)
  System.setProperty("twitter4j.oauth.consumerSecret", Settings.Twitter.consumerSecret)
  System.setProperty("twitter4j.oauth.accessToken", Settings.Twitter.accessToken)
  System.setProperty("twitter4j.oauth.accessTokenSecret", Settings.Twitter.accessTokenSecret)

  // Set up the Spark configuration with our app name and any other config
  val sparkConfiguration = new SparkConf()
      .setAppName("Stock Market Streaming")
      .setMaster(sys.env.get("spark.master").getOrElse("local[*]"))


  // Let's create the Spark Context using the configuration we just created
  val sparkContext = new SparkContext(sparkConfiguration)

  /// Use the config to create a streaming context that creates a new RDD
  // with a batch interval of every 20 seconds.
  val streamingContext = new StreamingContext(sparkContext, Seconds(10))

  // Use the streaming context and the TwitterUtils to create the Twitter stream.
  val tweets: DStream[Status] =  TwitterUtils.createStream(streamingContext, None)
                                             .filter(_.getLang == "en")
  // search term

  // Each tweet comes as a twitter4j.Status object, which we can use to
  // extract hash tags. We use flatMap() since each status could have
  // ZERO OR MORE hashtags.
  val hashTags = tweets.flatMap(status => status.getHashtagEntities)


  // Convert hashtag to (hashtag, 1) pair for future reduction.
  val hashTagPairs = hashTags.map(hashtag => ("#" + hashtag.getText, 1))

  // Use reduceByKeyAndWindow to reduce our hashtag pairs by summing their
  // counts over the last 10 seconds of batch intervals (in this case, 2 RDDs).
  val topCounts10 = hashTagPairs.reduceByKeyAndWindow((l, r) => {l + r}, Seconds(10))

  // topCounts10 will provide a new RDD for every window. Calling transform()
  // on each of these RDDs gives us a per-window transformation. We use
  // this transformation to sort each RDD by the hashtag counts. The FALSE
  // flag tells the sortBy() function to sort in descending order.
  val sortedTopCounts10 = topCounts10.transform(rdd =>
    rdd.sortBy(hashtagPair => hashtagPair._2, false))

  // Print popular hashtags.
  sortedTopCounts10.foreachRDD(rdd => {
    val topList = rdd.take(10)
    println("\nPopular topics in last 10 seconds (%s total):".format(rdd.count()))
    topList.foreach{case (tag, count) => println("%s (%d tweets)".format(tag, count))}
  })


  /***************************************************/
  /****************************************************/
  // To compute the sentiment of a tweet we'll use different set of words used to
  // filter and score each word of a sentence. Since these lists are pretty small
  // it can be worthwhile to broadcast those across the cluster so that every
  // executor can access them locally
 /* val uselessWords = sparkContext.broadcast(load("/stop-words.dat"))
  val positiveWords = sparkContext.broadcast(load("/pos-words.dat"))
  val negativeWords = sparkContext.broadcast(load("/neg-words.dat"))

  // Let's extract the words of each tweet
  // We'll carry the tweet along in order to print it in the end
  val textAndSentences: DStream[(TweetText, Sentence)] =
    tweets.map(_.getText)
          .map(tweetText => (tweetText, wordsOf(tweetText)))

  // Apply several transformations that allow us to keep just meaningful sentences
  val textAndMeaningfulSentences: DStream[(TweetText, Sentence)] =
    textAndSentences.
      mapValues(toLowercase).
      mapValues(keepActualWords).
      mapValues(words => keepMeaningfulWords(words, uselessWords.value)).
      filter { case (_, sentence) => sentence.length > 0 }

  // Compute the score of each sentence and keep only the non-neutral ones
  val textAndNonNeutralScore: DStream[(TweetText, Int)] =
    textAndMeaningfulSentences.
      mapValues(sentence => computeScore(sentence, positiveWords.value, negativeWords.value)).
      filter { case (_, score) => score != 0 }

  // Transform the (tweet, score) pair into a readable string and print it
  textAndNonNeutralScore.map(makeReadable).print*/

  // Now that the streaming is defined, start it
  streamingContext.start()

  // Let's await the stream to end - forever
  streamingContext.awaitTermination()

}
