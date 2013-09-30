package controllers

import play.api.mvc.{Action, Controller}
import models.Backends
import play.api.libs.concurrent.Execution.Implicits._

object ApplicationWithMultipleBackends extends Controller with Backends {

  def index(userId: String) = Action.async {
    for {
      user <- getUserById(userId)
      orders <- getOrdersForUser(user.email)
      products <- getProductsForOrders(orders)
      stocks <- getStocksForProducts(products)
    } yield Ok(s"${orders.size} order(s) for user ${user.email}")
  }

}
