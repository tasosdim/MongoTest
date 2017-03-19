import org.mongodb.scala.bson.Document
import org.mongodb.scala.connection.ClusterSettings
import org.mongodb.scala.{Completed, MongoClient, MongoClientSettings, MongoCollection, MongoDatabase, Observable, Observer, ServerAddress}
import scala.collection.JavaConverters._
/**
  * Created by tasos on 17/3/2017.
  */
object Mongo {
  def main(args: Array[String]): Unit = {
    val clusterSettings: ClusterSettings = ClusterSettings.builder().hosts(List(new ServerAddress("localhost:27017")).asJava).build()
    val settings: MongoClientSettings = MongoClientSettings.builder().clusterSettings(clusterSettings).build()
    val mongoClient: MongoClient = MongoClient(settings)
    val database: MongoDatabase = mongoClient.getDatabase("test")


    val collection: MongoCollection[Document] = database.getCollection("xa")
    val doc: Document = Document("_id" -> 0, "name" -> "MongoDB", "type" -> "database",
      "count" -> 1, "info" -> Document("x" -> 203, "y" -> 102))

    val observable: Observable[Completed] = collection.insertOne(doc)
   
     

    observable.subscribe(new Observer[Completed] {

      override def onNext(result: Completed): Unit = println("Inserted")

      override def onError(e: Throwable): Unit = println("Failed")

      override def onComplete(): Unit = println("Completed")
    })
   observable.toFuture().onSuccess {
    case x:Seq[Completed] => println("O Bo gamaei")
  }
    print("xa")
  }
}
