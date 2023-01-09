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
  answer_first:  Int,
  answer_second: Int,
  answer_third:  Int,
  answer_fourth: Int,
  answer_fifth:  Int
)

/**
 * Companion object
 */
object FormValueAnswerA {
  val formMapping: Seq[Tuple2[String, Mapping[Int]]] = Seq(
    "answer_first"  -> number,
    "answer_second" -> number,
    "answer_third"  -> number,
    "answer_fourth" -> number,
    "answer_fifth"  -> number
  )

  val form: Form[FormValueAnswerA] = Form(
    mapping(
      formMapping(0),
      formMapping(1),
      formMapping(2),
      formMapping(3),
      formMapping(4)
    )(FormValueAnswerA.apply)(FormValueAnswerA.unapply)
  )
}
