/*
 * Copyright Jo Harada. All Rights Reserved.
 *
 * For the full copyright and license information,
 * please view the LICENSE file that was distributed with this source code.
 */

package lib.student.model

import play.api.libs.json._
import ixias.util.json.{ JsonEnvReads, JsonEnvWrites }

import lib.student.model.ReadAnswer._

/**
  * Answer element
  */
case class AnswerElement(
  key:   Question,
  value: Option[Choice]
)

/**
 * Answer
 */
object Answer extends JsonEnvReads with JsonEnvWrites {

  // --[ Type definitions ]-----------------------------------------------------
  /**
   * The type of element list
   */
  type List    = Seq[AnswerElement]

  /**
   * The type of element
   */
  type Element = AnswerElement

  // --[ Json combinator ]------------------------------------------------------
  implicit val readsT1 = enumReads(Question)
  implicit val readsT2 = enumReads(Choice)
  implicit val format  = Json.format[AnswerElement]

  // --[ Function ]------------------------------------------------------------
  /**
    * Convert an instance of the concrete type
    */
  def to(t: List): String =
    Json.toJson(t.filter(_.value.isDefined)).toString

  /**
    * Convert an instance of the generic representation
    */
  def from(s: String): List =
    Json.parse(s).as[List]
}
