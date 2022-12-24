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

import lib.shop.model.Product

/**
 * Table Definition
 */
case class ProductTable[P <: JdbcProfile]()
  (implicit val driver: P) extends Table[Product, P] {
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
  class Table(tag: Tag) extends BasicTable(tag, "shop_product") {
    import Product._

    // Columns
    /* @1 */ def id        = column[Id]            ("id",         O.UInt64, O.PrimaryKey, O.AutoInc)
    /* @2 */ def name      = column[String]        ("name",       O.Utf8Char255)
    /* @3 */ def price     = column[Int]           ("price",      O.Int32)
    /* @4 */ def taxRate   = column[BigDecimal]    ("tax_rate",   O.Decimal(5, 4))
    /* @5 */ def state     = column[Status]        ("state",      O.Int8)
    /* @6 */ def updatedAt = column[LocalDateTime] ("updated_at", O.TsCurrent)
    /* @7 */ def createdAt = column[LocalDateTime] ("created_at", O.Ts)

    /**
     * The bidirectional mappings.
     * 1) Tuple(table) => Model
     * 2) Model        => Tuple(table)
     */
    def * = (
      id.?, name, price, taxRate, state, updatedAt, createdAt
    ) <> (
      (Product.apply   _).tupled,
      (Product.unapply _).andThen(_.map(_.copy(
        _6 = LocalDateTime.now
      )))
    )
  }
}
