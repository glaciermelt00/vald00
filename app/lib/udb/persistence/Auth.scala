/*
 * Copyright Jo Harada. All Rights Reserved.
 *
 * For the full copyright and license information,
 * please view the LICENSE file that was distributed with this source code.
 */

package lib.udb.persistence


import slick.jdbc.JdbcProfile
import ixias.persistence.SlickRepository
import scala.concurrent.Future
import lib.udb.model.{ Auth, User }

/**
 * Auth Information
 */
case class AuthRepository[P <: JdbcProfile]()(implicit val driver: P)
    extends SlickRepository[Auth.Id, Auth, P]
    with db.SlickResourceProvider[P] {
  import api._

  // --[ Methods ]--------------------------------------------------------------
  /**
   * Get Auth data
   */
  def get(id: Id): Future[Option[EntityEmbeddedId]] =
    RunDBAction(AuthTable, "slave") { _
      .filter(_.id === id)
      .result.headOption
    }

  /**
   * Get Auth data
   */
  def seek(cursor: Cursor): Future[Seq[EntityEmbeddedId]] =
    RunDBAction(AuthTable, "slave") { _
      .sortBy(_.id.desc)
      .seek(cursor)
      .result
    }

  /**
   * Get Auth by user id
   */
  def findByUserId(uid: User.Id): Future[Option[EntityEmbeddedId]] =
    RunDBAction(AuthTable, "slave") { _
      .filter(_.uid === uid)
      .result.headOption
    }

  /**
   * Get Auth by token
   */
  def findByToken(token: Auth.Token): Future[Option[EntityEmbeddedId]] =
    RunDBAction(AuthTable, "slave") { _
      .filter(_.token === token)
      .result.headOption
    }

  // --[ Methods ]--------------------------------------------------------------
  /**
   * Add Auth data
   */
  def add(data: EntityWithNoId): Future[Id] =
    RunDBAction(AuthTable) { slick =>
      slick returning slick.map(_.id) += data.v
    }

  /**
   * Update Auth data
   */
  def update(data: EntityEmbeddedId): Future[Option[EntityEmbeddedId]] =
    RunDBAction(AuthTable) { slick =>
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
   * Eliminate specified Auth data
   */
  def remove(id: Id): Future[Option[EntityEmbeddedId]] =
    RunDBAction(AuthTable) { slick =>
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
