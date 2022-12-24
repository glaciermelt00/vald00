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

import lib.shop.model.Cart

/**
 * Cart and session management
 */
case class CartRepository[P <: JdbcProfile]()(implicit val driver: P)
    extends SlickRepository[Cart.Id, Cart, P]
    with db.SlickResourceProvider[P] {
  import api._

  // --[ Methods ]--------------------------------------------------------------
  /**
   * Get cart
   */
  def get(id: Id): Future[Option[EntityEmbeddedId]] =
    RunDBAction(CartTable, "slave") { _
      .filter(_.id === id)
      .result.headOption
    }

  /**
   * Get cart By token
   */
  def findByToken(token: Cart.Token): Future[Seq[EntityEmbeddedId]] =
    RunDBAction(CartTable, "slave") { _
      .filter(_.token === token)
      .result
    }

  // --[ Methods ]--------------------------------------------------------------
  /**
   * Add a cart
   */
  def add(data: EntityWithNoId): Future[Id] =
    RunDBAction(CartTable) { slick =>
      slick returning slick.map(_.id) += data.v
    }

  /**
   * Update cart
   */
  def update(data: EntityEmbeddedId): Future[Option[EntityEmbeddedId]] =
    RunDBAction(CartTable) { slick =>
      val row = slick.filter(_.id === data.id)
      for {
        old <- row.result.headOption
        _   <- old match {
          case None    => DBIO.successful(0)
          case Some(_) => row.update(data.v)
        }
      } yield old
    }

  /**
   * Make cart state for processed payment done
   */
  def done(id: Id): Future[Boolean] =
    RunDBAction(CartTable) { slick =>
      val row = slick.filter(_.id === id)
      for {
        old <- row.result.headOption
        _   <- old match {
          case None    => DBIO.successful(false)
          case Some(_) => row.map(_.state).update(Cart.Status.IS_DONE)
        }
      } yield true
    }

  @deprecated("unsupported operation", "close")
  def remove(id: Id): Future[Option[EntityEmbeddedId]] =
    Future.failed(new UnsupportedOperationException)
}
