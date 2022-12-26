/*
 * Copyright Jo Harada. All Rights Reserved.
 *
 * For the full copyright and license information,
 * please view the LICENSE file that was distributed with this source code.
 */

package lib.udb.persistence.db


import slick.jdbc.JdbcProfile
import javax.mail.internet.InternetAddress
import com.google.i18n.phonenumbers.PhoneNumberUtil
import com.google.i18n.phonenumbers.Phonenumber.PhoneNumber

trait SlickColumnTypes[P <: JdbcProfile] {
  val driver: P
  import driver.api._

  // -- [ Mapped Column ] ------------------------------------------------------
  /**
   * Slick clumn mapping for Phonenumber
   */
  implicit val mcPhoneNumber =
    MappedColumnType.base[PhoneNumber, String](
      m => PhoneNumberUtil.getInstance.format(m, PhoneNumberUtil.PhoneNumberFormat.NATIONAL),
      v => PhoneNumberUtil.getInstance.parse (v, "JP")
    )

  /**
   * Slick clumn mapping for InternetAddress
   */
  implicit val mcInternetAddress =
    MappedColumnType.base[InternetAddress, String](
      m => m.getAddress,
      v => new InternetAddress(v)
    )
}
