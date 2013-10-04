package models

import play.api.libs.ws.WS
import play.api.libs.json.JsObject
import scala.util.{Failure, Try}
import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global

trait StockQuery {

  def searchStock(query: String): Future[Try[Seq[JsObject]]] = {
    WS.url("http://localhost:9001/search").withQueryString("query" -> query).get().map { response =>
      response.status match {
        case 200 =>
          Try((response.json \ "results").as[Seq[JsObject]])

        case _ => Failure(new Exception(s"Error calling search service.\nResponse status ${response.status}\n"))
      }
    }
  }

}
