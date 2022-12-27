/*
 * Copyright Jo Harada. All Rights Reserved.
 *
 * For the full copyright and license information,
 * please view the LICENSE file that was distributed with this source code.
 */

package lib.udb.persistence.db


import slick.jdbc.JdbcProfile

/**
 * The helper to provide slick resources
 */
trait SlickResourceProvider[P <: JdbcProfile] extends SlickColumnTypes[P] {

  implicit val driver: P

  // --[ Table ] ---------------------------------------------------------------
  object UserTable extends UserTable

  // --[ All Tables ] ----------------------------------------------------------
  lazy val AllTables = Seq(
    UserTable
  )
}
