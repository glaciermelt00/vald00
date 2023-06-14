/*
 * Copyright Jo Harada. All Rights Reserved.
 *
 * For the full copyright and license information,
 * please view the LICENSE file that was distributed with this source code.
 */

package lib.student.model

import play.api.libs.json._
import ixias.util.json.{ JsonEnvReads, JsonEnvWrites }
import ixias.util.EnumStatus

/**
  * Answer element
  */
case class AnswerElement[Q <: EnumStatus, C <: EnumStatus](
  key:   Q,
  value: Option[C]
)

/**
 * Answer
 */
abstract class Answer[Q <: EnumStatus, C <: EnumStatus](
  question: EnumStatus.Of[Q],
  choice:   EnumStatus.Of[C]
) extends JsonEnvReads with JsonEnvWrites {

  // --[ Type definitions ]-----------------------------------------------------
  /**
   * The type of element list
   */
  type List    = Seq[AnswerElement[Q, C]]

  // --[ Json combinator ]------------------------------------------------------
  implicit val readsT1 = enumReads(question)
  implicit val readsT2 = enumReads(choice)
  implicit val format  = Json.format[AnswerElement[Q, C]]

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
