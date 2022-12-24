/*
 * Copyright Jo Harada. All Rights Reserved.
 *
 * For the full copyright and license information,
 * please view the LICENSE file that was distributed with this source code.
 */

package lib.shop.persistence.db

import slick.jdbc.JdbcProfile
import ixias.persistence.model.Table
import java.time.LocalDateTime

import lib.shop.model.{ Cart, CartItem, Product }

/**
 * Table Definition
 */
case class CartItemTable[P <: JdbcProfile]()(implicit val driver: P)
    extends Table[CartItem, P] {
  import api._

  // --[ DNS ] -----------------------------------------------------------------
  lazy val dsn = Map(
    "master" -> DataSourceName("ixias.db.mysql://master/edu_shop"),
    "slave"  -> DataSourceName("ixias.db.mysql://slave/edu_shop")
  )

  // --[ Query ] ---------------------------------------------------------------
  class Query extends BasicQuery(new Table(_))
  lazy val query = new Query

  // --[ Table ] ---------------------------------------------------------------
  class Table(tag: Tag) extends BasicTable(tag, "shop_cart_item") {
    import CartItem._

    // Columns
    /* @1 */ def id         = column[Id]            ("id",         O.UInt64, O.PrimaryKey, O.AutoInc)
    /* @2 */ def cartId     = column[Cart.Id]       ("cart_id",    O.UInt64)
    /* @3 */ def productId  = column[Product.Id]    ("product_id", O.UInt64)
    /* @4 */ def productNum = column[Int]           ("num",        O.Int32)
    /* @5 */ def updatedAt  = column[LocalDateTime] ("updated_at", O.TsCurrent)
    /* @6 */ def createdAt  = column[LocalDateTime] ("created_at", O.Ts)

    // Indexes
    def ukey01 = index("ukey01", (cartId, productId), unique = true)

    /**
     * The bidirectional mappings.
     * 1) Tuple(table) => Model
     * 2) Model        => Tuple(table)
     */
    def * = (
      id.?, cartId, productId, productNum, updatedAt, createdAt
    ) <> (
      (CartItem.apply   _).tupled,
      (CartItem.unapply _).andThen(_.map(_.copy(
        _5 = LocalDateTime.now
      )))
    )
  }
}
