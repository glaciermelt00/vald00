/*
 * Copyright Jo Harada. All Rights Reserved.
 *
 * For the full copyright and license information,
 * please view the LICENSE file that was distributed with this source code.
 */

package mvc

import scala.concurrent.Future
import scala.concurrent.Await
import scala.concurrent.duration.Duration

import slick.jdbc.MySQLProfile
import ixias.persistence.util.SlickToolProvider

/**
 * Create tables
 */
object CreateTable extends {
  val driver = MySQLProfile
} with SlickToolProvider[MySQLProfile] { self =>

  trait DriverProvider {
    implicit lazy val driver = self.driver
  }

//  object shop extends lib.shop.persistence.db.SlickResourceProvider[MySQLProfile] with DriverProvider
  object student extends lib.student.persistence.db.SlickResourceProvider[MySQLProfile] with DriverProvider
  object udb     extends lib.udb.persistence.db.SlickResourceProvider[MySQLProfile]     with DriverProvider

  /**
   * Execute process
   */
  def main(args: Array[String]): Unit = {
    val f = for {
//      _ <- Future.traverse(shop.AllTables)(createTable(_) recover { case _: Throwable => true })
      _ <- Future.traverse(student.AllTables) (createTable(_) recover { case _: Throwable => true })
      _ <- Future.traverse(udb.AllTables)     (createTable(_) recover { case _: Throwable => true })
    } yield ()
    Await.result(f, Duration.Inf)
  }
}
