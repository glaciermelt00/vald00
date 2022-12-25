/*
 * Copyright Jo Harada. All Rights Reserved.
 *
 * For the full copyright and license information,
 * please view the LICENSE file that was distributed with this source code.
 */

package lib.student.model

import ixias.model._
import ixias.util.EnumBitFlags
import java.time.LocalDateTime
import lib.common.AnswerScore
import lib.udb.model.User

/**
 * Answer data of problem A
 */
import AnswerA._
case class AnswerA(
  id:        Option[Id]       = None,           // Id
  uid:       User.Id,                           // Id of user
  scores:    AnswerScore.List = Nil,            // Score of each answers
  updatedAt: LocalDateTime    = NOW,            // DateTime of updated
  createdAt: LocalDateTime    = NOW             // DateTime of created
) extends EntityModel[Id]

/**
 * Companion object
 */
object AnswerA {

  // --[ New Types ]------------------------------------------------------------
  val  Id = the[Identity[Id]]
  type Id = Long @@ AnswerA

  // --[ Enum ]-----------------------------------------------------------------
  /**
   * Answer score
   */
  object AnswerScore extends AnswerScore(enum = Answer)

  /**
   * Answer
   */
  sealed abstract class Answer(val code: Long, val name: String) extends EnumBitFlags
  object Answer extends EnumBitFlags.Of[Answer] {
    case object COMPREHENSION        extends Answer(code = 1 << 0, name = "職員給食")
    case object SPEED_SILENT_READING extends Answer(code = 1 << 1, name = "制服")
    case object SPEED_REPLY          extends Answer(code = 1 << 2, name = "園庭")
    case object SPEED_ORAL_READING   extends Answer(code = 1 << 3, name = "母子同園")
  }
}
