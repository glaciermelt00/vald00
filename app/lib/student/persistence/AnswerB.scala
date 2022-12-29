/*
 * Copyright Jo Harada. All Rights Reserved.
 *
 * For the full copyright and license information,
 * please view the LICENSE file that was distributed with this source code.
 */

package lib.student.persistence


import slick.jdbc.JdbcProfile
import ixias.persistence.SlickRepository
import scala.concurrent.Future
import lib.student.model.AnswerB

/**
 * AnswerB Information
 */
case class AnswerBRepository[P <: JdbcProfile]()(implicit val driver: P)
    extends SlickRepository[AnswerB.Id, AnswerB, P]
    with db.SlickResourceProvider[P] {
  import api._

  // --[ Methods ]--------------------------------------------------------------
  /**
   * Get AnswerB data
   */
  def get(id: Id): Future[Option[EntityEmbeddedId]] =
    RunDBAction(AnswerBTable, "slave") { _
      .filter(_.id === id)
      .result.headOption
    }

  /**
   * Get AnswerB data
   */
  def seek(cursor: Cursor): Future[Seq[EntityEmbeddedId]] =
    RunDBAction(AnswerBTable, "slave") { _
      .sortBy(_.id.desc)
      .seek(cursor)
      .result
    }

  // --[ Methods ]--------------------------------------------------------------
  /**
   * Add AnswerB data
   */
  def add(data: EntityWithNoId): Future[Id] =
    RunDBAction(AnswerBTable) { slick =>
      slick returning slick.map(_.id) += data.v
    }

  /**
   * Update AnswerB data
   */
  def update(data: EntityEmbeddedId): Future[Option[EntityEmbeddedId]] =
    RunDBAction(AnswerBTable) { slick =>
      val row = slick.filter(_.id === data.id)
      for {
        old <- row.result.headOption
        _   <- old match {
          case None     => DBIO.successful(0)
          case Some(id) => row.update(data.v)
        }
      } yield old
    }

  /**
   * Eliminate specified AnswerB data
   */
  def remove(id: Id): Future[Option[EntityEmbeddedId]] =
    RunDBAction(AnswerBTable) { slick =>
      val row = slick.filter(_.id === id)
      for {
        old <- row.result.headOption
        _   <- old match {
          case None    => DBIO.successful(0)
          case Some(_) => row.delete
        }
      } yield old
    }
}
