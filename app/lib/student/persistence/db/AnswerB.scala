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
import lib.student.model.Answer
import lib.udb.model.User

/**
 * Table Definition
 */
case class AnswerBTable[P <: JdbcProfile]()(implicit val driver: P)
    extends Table[Answer, P] with SlickColumnTypes[P] {
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
  class Table(tag: Tag) extends BasicTable(tag, "answer_b") {
    import Answer._

    // Columns
    /* @1  */ def id            = column[Id]                  ("id",              O.UInt64, O.PrimaryKey, O.AutoInc)
    /* @2  */ def uid           = column[User.Id]             ("uid",             O.Int64)
    /* @3  */ def readScore     = column[Long]                ("read_score",      O.Int64)
    /* @4  */ def speedSilent   = column[Int]                 ("speed_silent",    O.Int32)
    /* @5  */ def speedReply    = column[Int]                 ("speed_reply",     O.Int32)
    /* @6  */ def speedOral     = column[Int]                 ("speed_oral",      O.Int32)
    /* @7  */ def updatedAt     = column[LocalDateTime]       ("updated_at",      O.TsCurrent)
    /* @8  */ def createdAt     = column[LocalDateTime]       ("created_at",      O.Ts)

    val enum: ixias.util.EnumBitFlags.Of[Answer.ReadAnswer]
    /**
     * The bidirectional mappings.
     * 1) Tuple(table) => Model
     * 2) Model        => Tuple(table)
     */
    def * = (
      id.?, uid, readScore, speedSilent.?, speedReply.?, speedOral.?, updatedAt, createdAt
    ) <> (
      (Answer.apply   _).tupled.compose(
        t => t.copy(
          _3 = enum(t._3)
        )
      ),
      (Answer.unapply _).andThen(_.map(
        t => t.copy(
          _3 = enum.toBitset(t._3),
          _7 = LocalDateTime.now
        )
      ))
    )
  }
}
