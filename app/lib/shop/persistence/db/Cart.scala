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

import lib.shop.model.Cart

/**
 * Table Definition
 */
case class CartTable[P <: JdbcProfile]()(implicit val driver: P)
    extends Table[Cart, P] {
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
  class Table(tag: Tag) extends BasicTable(tag, "shop_cart") {
    import Cart._

    // Columns
    /* @1 */ def id        = column[Id]            ("id",           O.UInt64, O.PrimaryKey, O.AutoInc)
    /* @2 */ def token     = column[Token]         ("token",        O.AsciiChar128)
    /* @3 */ def state     = column[Status]        ("state",        O.Int8)
    /* @4 */ def expiry    = column[LocalDateTime] ("expiry",       O.DateTime)
    /* @5 */ def updatedAt = column[LocalDateTime] ("updated_at",   O.TsCurrent)
    /* @6 */ def createdAt = column[LocalDateTime] ("created_at",   O.Ts)

    /**
     * The bidirectional mappings.
     * 1) Tuple(table) => Model
     * 2) Model        => Tuple(table)
     */
    def * = (
      id.?, token, state, expiry, updatedAt, createdAt
    ) <> (
      (Cart.apply   _).tupled,
      (Cart.unapply _).andThen(_.map(_.copy(
        _5 = LocalDateTime.now
      )))
    )
  }
}
