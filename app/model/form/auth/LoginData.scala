/*
 * Copyright Jo Harada. All Rights Reserved.
 *
 * For the full copyright and license information,
 * please view the LICENSE file that was distributed with this source code.
 */

package model.form.auth

import play.api.data._
import play.api.data.Forms._

/**
 * Login Data
 */
case class FormValueLogin(
  userNumber: Int,
  password:   String
)

/**
 * Companion object
 */
object FormValueLogin {
  val form: Form[FormValueLogin] = Form(
    mapping(
      "userNumber" -> number(min= 0, max = 10000),
      "password"   -> nonEmptyText
    )(FormValueLogin.apply)(FormValueLogin.unapply)
  )
}