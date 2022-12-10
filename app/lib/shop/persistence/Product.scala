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

import lib.shop.model.Product

/**
 * The product management
 */
case class ProductRepository[P <: JdbcProfile]()(implicit val driver: P)
    extends SlickRepository[Product.Id, Product, P]
    with db.SlickResourceProvider[P] {
  import api._

  // --[ Methods ]--------------------------------------------------------------
  /**
   * Get product
   */
  def get(id: Id): Future[Option[EntityEmbeddedId]] =
    RunDBAction(ProductTable, "slave") { _
      .filter(_.id === id)
      .result.headOption
    }

  // --[ Methods ]--------------------------------------------------------------
  /**
   * Get product by ids
   */
  def filterByIds(ids: Seq[Id]): Future[Seq[EntityEmbeddedId]] =
    RunDBAction(ProductTable, "slave") { _
      .filter(_.id inSet ids)
      .result
    }

  // --[ Methods ]--------------------------------------------------------------
  /**
   * Add a product
   */
  def add(data: EntityWithNoId): Future[Id] =
    RunDBAction(ProductTable) { slick =>
      slick returning slick.map(_.id) += data.v
    }

  /**
   * Update product
   */
  def update(data: EntityEmbeddedId): Future[Option[EntityEmbeddedId]] =
    RunDBAction(ProductTable) { slick =>
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
   * Archive product
   */
  def remove(id: Id): Future[Option[EntityEmbeddedId]] =
    RunDBAction(ProductTable) { slick =>
      val row = slick.filter(_.id === id)
      for {
        old <- row.result.headOption
        _   <- old match {
          case None    => DBIO.successful(0)
          case Some(_) => row.map(_.state).update(Product.Status.IS_ARCHIVED)
        }
      } yield old
    }
}

