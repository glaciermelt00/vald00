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
import lib.udb.model.User

/**
 * User Information
 */
case class UserRepository[P <: JdbcProfile]()(implicit val driver: P)
    extends SlickRepository[User.Id, User, P]
    with db.SlickResourceProvider[P] {
  import api._

  // --[ Methods ]--------------------------------------------------------------
  /**
   * Get User data
   */
  def get(id: Id): Future[Option[EntityEmbeddedId]] =
    RunDBAction(UserTable, "slave") { _
      .filter(_.id === id)
      .result.headOption
    }

  /**
   * Get User data
   */
  def seek(cursor: Cursor): Future[Seq[EntityEmbeddedId]] =
    RunDBAction(UserTable, "slave") { _
      .sortBy(_.id.desc)
      .seek(cursor)
      .result
    }

  // --[ Methods ]--------------------------------------------------------------
  /**
   * Add User data
   */
  def add(data: EntityWithNoId): Future[Id] =
    RunDBAction(UserTable) { slick =>
      slick returning slick.map(_.id) += data.v
    }

  /**
   * Update User data
   */
  def update(data: EntityEmbeddedId): Future[Option[EntityEmbeddedId]] =
    RunDBAction(UserTable) { slick =>
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
   * Eliminate specified User data
   */
  def remove(id: Id): Future[Option[EntityEmbeddedId]] =
    RunDBAction(UserTable) { slick =>
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
