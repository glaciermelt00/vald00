/*
 * Copyright Jo Harada. All Rights Reserved.
 *
 * For the full copyright and license information,
 * please view the LICENSE file that was distributed with this source code.
 */

package lib.udb.persistence.db


import slick.jdbc.JdbcProfile
import ixias.persistence.model.Table

import java.time.LocalDateTime
import lib.udb.model.{ Auth, User }

/**
 * Table Definition
 */
case class AuthTable[P <: JdbcProfile]()(implicit val driver: P)
    extends Table[Auth, P] with SlickColumnTypes[P] {
  import api._

  // --[ DSN ] -----------------------------------------------------------------
  lazy val dsn = Map(
    "master" -> DataSourceName("ixias.db.mysql://master/vald"),
    "slave"  -> DataSourceName("ixias.db.mysql://slave/vald")
  )

  // --[ Query ] ---------------------------------------------------------------
  lazy val query = new Query
  class Query extends BasicQuery(new Table(_))

  // --[ Table ] ---------------------------------------------------------------
  class Table(tag: Tag) extends BasicTable(tag, "auth") {
    import Auth._

    // Columns
    /* @1  */ def id            = column[Id]                  ("id",              O.UInt64, O.PrimaryKey, O.AutoInc)
    /* @2  */ def uid           = column[User.Id]             ("uid",             O.Int64)
    /* @3  */ def token         = column[Auth.Token]          ("token",           O.Utf8Char255)
    /* @4  */ def updatedAt     = column[LocalDateTime]       ("updated_at",      O.TsCurrent)
    /* @5  */ def createdAt     = column[LocalDateTime]       ("created_at",      O.Ts)

    /**
     * The bidirectional mappings.
     * 1) Tuple(table) => Model
     * 2) Model        => Tuple(table)
     */
    def * = (
      id.?, uid, token, updatedAt, createdAt
    ) <> (
      (Auth.apply   _).tupled,
      (Auth.unapply _).andThen(_.map(_.copy(
        _4 = LocalDateTime.now
      )))
    )
  }
}
