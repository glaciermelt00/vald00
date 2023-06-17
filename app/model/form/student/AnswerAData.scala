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
  answerSecond: Choice,
  answerThird:  Choice,
  answerFourth: Choice,
  answerFifth:  Choice
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

  /**
   * Form mapping
   */
  val form: Form[FormValueAnswerA] = Form(
    mapping(
      "answerFirst"  -> choiceMapping(number),
      "answerSecond" -> choiceMapping(number),
      "answerThird"  -> choiceMapping(number),
      "answerFourth" -> choiceMapping(number),
      "answerFifth"  -> choiceMapping(number)
    )(FormValueAnswerA.apply)(FormValueAnswerA.unapply)
  )
}
