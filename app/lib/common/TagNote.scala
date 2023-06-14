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
 * Tag note
 */
case class TagNoteElement[E <: ixias.util.EnumBitFlags](
  key:   E,
  value: Option[String]
)

/**
 * Companion object
 */
abstract class TagNote[E <: ixias.util.EnumBitFlags](
  val enum: ixias.util.EnumBitFlags.Of[E]
) extends JsonEnvReads with JsonEnvWrites {

  // --[ Type definitions ]-----------------------------------------------------
  /**
   * The type of elemet list
   */
  type List    = Seq[TagNoteElement[E]]

  /**
   * The type of element
   */
  type Element = TagNoteElement[E]

  // --[ Json conbinator ]------------------------------------------------------
  implicit val reads  = enumSingleReads(enum)
  implicit val format = Json.format[TagNoteElement[E]]

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
      val tags  = enum(r._1)
      val notes = Json.parse(r._2).as[List]
        tags.map(key => {
          TagNoteElement(
            key   = key,
            value = notes.find(_.key == key).flatMap(_.value)
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
