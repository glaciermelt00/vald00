/*
 * Copyright Jo Harada. All Rights Reserved.
 *
 * For the full copyright and license information,
 * please view the LICENSE file that was distributed with this source code.
 */

package model.form.student

import play.api.data._
import play.api.data.Forms._

/**
 * Answer A Data
 */
case class FormValueAnswerA(
  answer_first:  String,
  answer_second: String,
  answer_third:  String,
  answer_fourth: String,
  answer_fifth:  String
)

/**
 * Companion object
 */
object FormValueAnswerA {
  val form: Form[FormValueAnswerA] = Form(
    mapping(
      "answer_first"  -> nonEmptyText,
      "answer_second" -> nonEmptyText,
      "answer_third"  -> nonEmptyText,
      "answer_fourth" -> nonEmptyText,
      "answer_fifth"  -> nonEmptyText
    )(FormValueAnswerA.apply)(FormValueAnswerA.unapply)
  )
}
