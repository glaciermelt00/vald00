/*
 * Copyright Jo Harada. All Rights Reserved.
 *
 * For the full copyright and license information,
 * please view the LICENSE file that was distributed with this source code.
 */

package lib.student.persistence.db


import slick.jdbc.JdbcProfile
import ixias.persistence.model.Table

import java.time.LocalDateTime
import lib.student.model.AnswerA
import lib.udb.model.User

/**
 * Table Definition
 */
case class AnswerATable[P <: JdbcProfile]()(implicit val driver: P)
    extends Table[AnswerA, P] with SlickColumnTypes[P] {
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
  class Table(tag: Tag) extends BasicTable(tag, "answer_a") {
    import AnswerA._

    // Columns
    /* @1  */ def id            = column[Id]                  ("id",              O.UInt64, O.PrimaryKey, O.AutoInc)
    /* @2  */ def uid           = column[User.Id]             ("uid",             O.Int64)
    /* @3  */ def readScore     = column[Int]                 ("read_score",      O.Int32)
    /* @4  */ def speedSilent   = column[Int]                 ("speed_silent",    O.Int32)
    /* @5  */ def speedReply    = column[Int]                 ("speed_reply",     O.Int32)
    /* @6  */ def speedOral     = column[Int]                 ("speed_oral",      O.Int32)
    /* @7  */ def updatedAt     = column[LocalDateTime]       ("updated_at",      O.TsCurrent)
    /* @8  */ def createdAt     = column[LocalDateTime]       ("created_at",      O.Ts)

    /**
     * The bidirectional mappings.
     * 1) Tuple(table) => Model
     * 2) Model        => Tuple(table)
     */
    def * = (
      id.?, uid, readScore.?, speedSilent.?, speedReply.?, speedOral.?, updatedAt, createdAt
    ) <> (
      (AnswerA.apply   _).tupled,
      (AnswerA.unapply _).andThen(_.map(_.copy(
        _7 = LocalDateTime.now
      )))
    )
  }
}
