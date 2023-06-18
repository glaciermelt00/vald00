/*
 * Copyright Jo Harada. All Rights Reserved.
 *
 * For the full copyright and license information,
 * please view the LICENSE file that was distributed with this source code.
 */

package model.form.student

import play.api.data._
import play.api.data.Forms._

import lib.udb.model.User
import lib.student.model._
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
) {

  /**
   * Create a new object
   */
  def create(uid: User.Id): ReadAnswer#WithNoId =
    ReadAnswer(
      id        = None,
      uid       = uid,
      answer    = Seq(
        AnswerElement(Question.IS_FIRST,  Some(answerFirst)),
        AnswerElement(Question.IS_SECOND, Some(answerSecond)),
        AnswerElement(Question.IS_THIRD,  Some(answerThird)),
        AnswerElement(Question.IS_FOURTH, Some(answerFourth)),
        AnswerElement(Question.IS_FIFTH,  Some(answerFifth))
      )
    ).toWithNoId
}

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
