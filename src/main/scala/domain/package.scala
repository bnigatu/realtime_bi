/**
  * Created by Ahmad Alkilani on 5/1/2016.
  */
package object domain {

  case class Account(number: String,
                     firstName: String,
                     lastName: String)
  case class Transaction(id: Long,
                         account: Account,
                         date: java.sql.Date,
                         amount: Double,
                         description: String)
  case class TransactionForAverage(accountNumber: String,
                                   amount: Double,
                                   description: String,
                                   date: java.sql.Date)
  case class SimpleTransaction(id: Long,
                               account_number: String,
                               amount: Double,
                               date: java.sql.Date,
                               description: String)
  case class UnparsableTransaction(id: Option[Long],
                                   originalMessage: String,
                                   exception: Throwable)
  case class AggregateData(totalSpending: Double,
                           numTx: Int,
                           windowSpendingAvg: Double) {
    val averageTx = if(numTx > 0) totalSpending / numTx else 0
  }
  case class EvaluatedSimpleTransaction(tx: SimpleTransaction,
                                        isPossibleFraud: Boolean)
  case class Activity(timestamp_hour: Long,
                      referrer: String,
                      action: String,
                      prevPage: String,
                      visitor: String,
                      page: String,
                      product: String,
                      inputProps: Map[String, String] = Map()
                     )

  case class ActivityByProduct (product : String,
                                timestamp_hour : Long,
                                purchase_count : Long,
                                add_to_cart_count : Long,
                                page_view_count : Long)

  case class VisitorsByProduct (product : String, timestamp_hour : Long, unique_visitors : Long)
}
