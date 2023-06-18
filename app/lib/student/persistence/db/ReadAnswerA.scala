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

import lib.student.model._
import lib.udb.model.User

/**
 * Table Definition
 */
case class ReadAnswerATable[P <: JdbcProfile]()(implicit val driver: P)
    extends Table[ReadAnswer, P] with SlickColumnTypes[P] {
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
  class Table(tag: Tag) extends BasicTable(tag, "read_answer_a") {
    import ReadAnswer._

    // Columns
    /* @1  */ def id        = column[Id]                    ("id",         O.UInt64, O.PrimaryKey, O.AutoInc)
    /* @2  */ def uid       = column[User.Id]               ("uid",        O.Int64)
    /* @3  */ def answer    = column[String]                ("answer",     O.Text)
    /* @4  */ def textStart = column[Option[LocalDateTime]] ("text_start", O.DateTime)
    /* @5  */ def textEnd   = column[Option[LocalDateTime]] ("text_end",   O.DateTime)
    /* @6  */ def quizStart = column[Option[LocalDateTime]] ("quiz_start", O.DateTime)
    /* @7  */ def quizEnd   = column[Option[LocalDateTime]] ("quiz_end",   O.DateTime)
    /* @8  */ def updatedAt = column[LocalDateTime]         ("updated_at", O.TsCurrent)
    /* @9  */ def createdAt = column[LocalDateTime]         ("created_at", O.Ts)

    /**
     * The bidirectional mappings.
     * 1) Tuple(table) => Model
     * 2) Model        => Tuple(table)
     */
    def * = (
      id.?, uid, answer, textStart, textEnd, quizStart, quizEnd, updatedAt, createdAt
    ) <> (
      (ReadAnswer.apply   _).tupled.compose(
        t => t.copy(
          _3 = Answer.from(t._3)
        )
      ),
      (ReadAnswer.unapply _).andThen(_.map(
        t => t.copy(
          _3 = Answer.to(t._3),
          _8 = LocalDateTime.now
        )
      ))
    )
  }
}
