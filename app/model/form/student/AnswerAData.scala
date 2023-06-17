/*
 * Copyright Jo Harada. All Rights Reserved.
 *
 * For the full copyright and license information,
 * please view the LICENSE file that was distributed with this source code.
 */

package model.form.student

import play.api.data._
import play.api.data.Forms._

import lib.student.model.ReadAnswer._

/**
 * Answer A Data
 */
case class FormValueAnswerA(
  answerFirst:  Choice,
  answerSecond: String,
  answerThird:  String,
  answerFourth: String,
  answerFifth:  String
)

/**
 * Companion object
 */
object FormValueAnswerA {

  /**
   * Transform mapping to Choice
   */
  def choiceMapping(number: Mapping[Int]): Mapping[Choice] =
    number.transform[Choice](
      v => Choice(v.toShort),
      c => c.code.toInt
    )

  val form: Form[FormValueAnswerA] = Form(
    mapping(
      "answerFirst"  -> choiceMapping(number),
      "answerSecond" -> nonEmptyText,
      "answerThird"  -> nonEmptyText,
      "answerFourth" -> nonEmptyText,
      "answerFifth"  -> nonEmptyText
    )(FormValueAnswerA.apply)(FormValueAnswerA.unapply)
  )
}
