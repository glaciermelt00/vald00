/*
 * Copyright Jo Harada. All Rights Reserved.
 *
 * For the full copyright and license information,
 * please view the LICENSE file that was distributed with this source code.
 */

package lib.shop.persistence

import slick.jdbc.JdbcProfile
import ixias.persistence.SlickRepository
import scala.concurrent.Future

import lib.shop.model.{ Cart, CartItem, Product }

/**
 * Manage products added to the cart
 */
case class CartItemRepository[P <: JdbcProfile]
  (cartId: Cart.Id)(implicit val driver: P)
    extends SlickRepository[CartItem.Id, CartItem, P]
    with db.SlickResourceProvider[P] {
  import api._

  // --[ Methods ]--------------------------------------------------------------
  /**
   * Get item
   */
  def get(id: Id): Future[Option[EntityEmbeddedId]] =
    RunDBAction(CartItemTable) { _
      .filter(_.id === id)
      .filter(_.cartId === cartId)
      .result.headOption
    }

  /**
   * Get all items in cart
   */
  def all: Future[Seq[EntityEmbeddedId]] =
    RunDBAction(CartItemTable) { _
      .filter(_.cartId === cartId)
      .sortBy(_.id.asc)
      .result
    }

  // --[ Methods ]--------------------------------------------------------------
  /**
   * Add a item to cart
   */
  def addItem(productId: Product.Id): Future[Option[EntityEmbeddedId]] =
    RunDBAction(CartItemTable) { slick =>
      val row = slick
        .filter(_.cartId    === cartId)
        .filter(_.productId === productId)
      for {
        old <- row.result.headOption
        _   <- old.map(_.productNum) match {
          case Some(num) => row.map(_.productNum).update(num + 1)
          case None      => slick += CartItem(
            id        = None,
            cartId    = cartId,
            productId = productId,
          )
        }
      } yield old
    }

  /**
   * Update quantity of item in cart
   */
  def updateItemQuantity(productId: Product.Id, num: Int): Future[Option[EntityEmbeddedId]] =
    RunDBAction(CartItemTable) { slick =>
      val row = slick
        .filter(_.cartId    === cartId)
        .filter(_.productId === productId)
      for {
        old <- row.result.headOption
        _   <- old match {
          case Some(_) => row.map(_.productNum).update(num)
          case None    => DBIO.successful(0)
        }
      } yield old
    }

  /**
   * Remove items in the cart
   */
  def removeItem(productId: Product.Id): Future[Option[EntityEmbeddedId]] =
    RunDBAction(CartItemTable) { slick =>
      val row = slick
        .filter(_.cartId    === cartId)
        .filter(_.productId === productId)
      for {
        old <- row.result.headOption
        _   <- old match {
          case None    => DBIO.successful(0)
          case Some(_) => row.delete
        }
      } yield old
    }

  // --[ Methods ]--------------------------------------------------------------
  @deprecated("unsupported operation", "")
  def add(data: EntityWithNoId): Future[Id] =
    Future.failed(new UnsupportedOperationException)

  @deprecated("unsupported operation", "")
  def update(data: EntityEmbeddedId): Future[Option[EntityEmbeddedId]] =
    Future.failed(new UnsupportedOperationException)

  @deprecated("unsupported operation", "")
  def remove(id: Id): Future[Option[EntityEmbeddedId]] =
    Future.failed(new UnsupportedOperationException)
}
