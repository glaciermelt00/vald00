/*
 * Copyright Jo Harada. All Rights Reserved.
 *
 * For the full copyright and license information,
 * please view the LICENSE file that was distributed with this source code.
 */

package lib.student


package object persistence {

  /**
   * Repository definition specifying MySQL driver
   */
  object default {
    implicit val driver = slick.jdbc.MySQLProfile

    //- For Group
    val GroupRepository = persistence.GroupRepository()
  }
}
