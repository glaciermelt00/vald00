/*
 * Copyright Jo Harada. All Rights Reserved.
 *
 * For the full copyright and license information,
 * please view the LICENSE file that was distributed with this source code.
 */

package model.auth

import play.api.data._
import play.api.data.Forms._
import play.api.libs.json._

/**
 * Login Data
 */
case class SendLogin(
  userNumber: Int,
  password:   String
)

/**
 * Companion object
 */
object SendLogin {

  // --[ Form: Binding ]--------------------------------------------------------
  val form: Form[SendLogin] = Form(
    mapping(
      "userNumber" -> number(min= 0, max = 10000),
      "password"   -> nonEmptyText
    )(SendLogin.apply)(SendLogin.unapply)
  )

  // --[ JSON : Combinator ]----------------------------------------------------
  implicit val reads = Json.reads[SendLogin]
}
