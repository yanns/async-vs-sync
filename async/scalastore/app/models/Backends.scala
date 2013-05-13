package models

import scala.concurrent.Future

case class ProductId(id: String) extends AnyVal
case class Email(value: String) extends AnyVal {
  override def toString: String = value
}

case class User(id: String, email: Email)
case class Order(productId: ProductId, email: Email)
case class Product(productId: ProductId, description: String)
case class Stock(productId: ProductId, quantity: Int)

trait Backends {

  def getUserById(userId: String): Future[User] =
    Future.successful(User(userId, Email("email@domain.com")))

  def getOrdersForUser(email: Email): Future[Seq[Order]] =
    Future.successful(List(Order(ProductId("abc"), email)))

  def getProductsForOrders(orders: Seq[Order]): Future[Seq[Product]] =
    Future.successful(
      for (order <- orders) yield Product(order.productId, "description")
    )

  def getStocksForProducts(products: Seq[Product]): Future[Seq[Stock]] =
    Future.successful(
      for (product <- products) yield Stock(product.productId, 3)
    )

}
