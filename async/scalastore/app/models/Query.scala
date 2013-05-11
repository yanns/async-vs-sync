package models

import play.api.libs.ws.WS
import play.api.libs.json.JsObject
import scala.util.{Failure, Success, Try}
import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global

trait Query {

  def searchStock(query: String): Future[Try[Seq[JsObject]]] = {
    WS.url("http://localhost:9001/search?query=" + query).get().map { response =>
      response.status match {
        case 200 => {
          val results = (response.json \ "results").as[Seq[JsObject]]
          Success(results)
        }
        case _ => Failure(new Exception(s"Error calling search service.\nResponse status ${response.status}\n"))
      }
    }
  }

}
