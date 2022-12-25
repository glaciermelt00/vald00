/*
 * Copyright Jo Harada. All Rights Reserved.
 *
 * For the full copyright and license information,
 * please view the LICENSE file that was distributed with this source code.
 */

package lib.common

import play.api.libs.json._
import ixias.util.json.{ JsonEnvReads, JsonEnvWrites }

/**
 * Answer score
 */
case class AnswerScoreElement[E <: ixias.util.EnumBitFlags](
  key:   E,
  value: Option[String]
)

/**
 * Companion object
 */
abstract class AnswerScore[E <: ixias.util.EnumBitFlags](
  val enum: ixias.util.EnumBitFlags.Of[E]
) extends JsonEnvReads with JsonEnvWrites {

  // --[ Type definitions ]-----------------------------------------------------
  /**
   * The type of elemet list
   */
  type List    = Seq[AnswerScoreElement[E]]

  /**
   * The type of element
   */
  type Element = AnswerScoreElement[E]

  // --[ Json conbinator ]------------------------------------------------------
  implicit val reads  = enumSingleReads(enum)
  implicit val reads2 = Json.reads[ixias.util.EnumBitFlags]
  implicit def format[E2 <: ixias.util.EnumBitFlags]: Format[AnswerScoreElement[E2]] = new Format[AnswerScoreElement[E2]] {
    def reads(json: JsValue): JsResult[AnswerScoreElement[E2]] = JsSuccess(AnswerScoreElement[E2](
      key = (json \ "key").as[E2],
      value = (json \ "value").asOpt[String]
    ))
  }

//  implicit val format = Json.format[AnswerScoreElement[E]]

  // --[ Generic ]------------------------------------------------------------
  /**
   * Generic value of `Content`
   */
  object SimpleValue extends shapeless.Generic[List]  {

    /**
     * The generic representation type
     */
    type Repr = (Long, String)

    // --[ Generic ]------------------------------------------------------------
    /**
     * Convert an instance of the concrete type
     * to the generic value representation
     */
    def to(t: List): Repr =
      Tuple2(
        enum.toBitset(t.map(_.key)),
        Json.toJson(t.filter(_.value.isDefined)).toString
      )

    /**
     * Convert an instance of the generic representation
     * to an instance of the concrete type
     */
    def from(r: Repr): List = {
      val answers  = enum(r._1)
      val scores = Json.parse(r._2).as[List]
        answers.map(key => {
          AnswerScoreElement(
            key   = key,
            value = scores.find(_.key == key).flatMap(_.value)
          )
        })
    }
  }

  /**
   * Deserializer for ixias.util.EnumBitFlags
   */
  def enumSingleReads(enum: ixias.util.EnumBitFlags.Of[E]): Reads[E] =
    new Reads[E] {
      def reads(json: JsValue) = json match {
        case JsNumber(n) if n.isValidLong => JsSuccess(enum(n.toLong).head)
        case JsNumber(n) => JsError("error.expected.enum.long")
        case _           => JsError("error.expected.enum.jsnumber")
      }
    }
}
