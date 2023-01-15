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
  answerFirst:  String,
  answerSecond: String,
  answerThird:  String,
  answerFourth: String,
  answerFifth:  String
)

/**
 * Companion object
 */
object FormValueAnswerA {
  val form: Form[FormValueAnswerA] = Form(
    mapping(
      "answerFirst"  -> nonEmptyText,
      "answerSecond" -> nonEmptyText,
      "answerThird"  -> nonEmptyText,
      "answerFourth" -> nonEmptyText,
      "answerFifth"  -> nonEmptyText
    )(FormValueAnswerA.apply)(FormValueAnswerA.unapply)
  )
}
