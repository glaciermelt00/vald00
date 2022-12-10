/*
 * Copyright Jo Harada. All Rights Reserved.
 *
 * For the full copyright and license information,
 * please view the LICENSE file that was distributed with this source code.
 */

package lib.shop.persistence.db

import slick.jdbc.JdbcProfile

trait SlickResourceProvider[P <: JdbcProfile]{

  implicit val driver: P

  // --[ Definition of Table ] -------------------------------------------------
  object ProductTable  extends ProductTable
  object CartTable     extends CartTable
  object CartItemTable extends CartItemTable

  // --[ Definition of All Table ] ---------------------------------------------
  lazy val AllTables = Seq(
    ProductTable,
    CartTable,
    CartItemTable
  )
}
