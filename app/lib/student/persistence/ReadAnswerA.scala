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
import lib.student.model.ReadAnswer

/**
 * ReadAnswerA Information
 */
case class ReadAnswerARepository[P <: JdbcProfile]()(implicit val driver: P)
    extends SlickRepository[ReadAnswer.Id, ReadAnswer, P]
    with db.SlickResourceProvider[P] {
  import api._

  // --[ Methods ]--------------------------------------------------------------
  /**
   * Get ReadAnswerA data
   */
  def get(id: Id): Future[Option[EntityEmbeddedId]] =
    RunDBAction(ReadAnswerATable, "slave") { _
      .filter(_.id === id)
      .result.headOption
    }

  /**
   * Get ReadAnswerA data
   */
  def seek(cursor: Cursor): Future[Seq[EntityEmbeddedId]] =
    RunDBAction(ReadAnswerATable, "slave") { _
      .sortBy(_.id.desc)
      .seek(cursor)
      .result
    }

  // --[ Methods ]--------------------------------------------------------------
  /**
   * Add ReadAnswerA data
   */
  def add(data: EntityWithNoId): Future[Id] =
    RunDBAction(ReadAnswerATable) { slick =>
      slick returning slick.map(_.id) += data.v
    }

  /**
   * Update ReadAnswerA data
   */
  def update(data: EntityEmbeddedId): Future[Option[EntityEmbeddedId]] =
    RunDBAction(ReadAnswerATable) { slick =>
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
   * Eliminate specified ReadAnswerA data
   */
  def remove(id: Id): Future[Option[EntityEmbeddedId]] =
    RunDBAction(ReadAnswerATable) { slick =>
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
