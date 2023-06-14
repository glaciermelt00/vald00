/*
 * Copyright Jo Harada. All Rights Reserved.
 *
 * For the full copyright and license information,
 * please view the LICENSE file that was distributed with this source code.
 */

package lib.student.model

import ixias.model._
import ixias.util.EnumStatus
import java.time.LocalDateTime
import lib.udb.model.User

/**
 * Answer data of problem
 */
import Answer._
case class Answer(
  id:            Option[Id]                      = None,  // Id
  uid:           User.Id,                                 // Id of user
  readAnswer:    Seq[(Question, Option[Choice])] = Nil,   // Answer of reading
  readTextStart: Option[LocalDateTime]           = None,  // DateTime of start reading text
  readTextEnd:   Option[LocalDateTime]           = None,  // DateTime of end reading text
  readQuizStart: Option[LocalDateTime]           = None,  // DateTime of start reading quiz
  readQuizEnd:   Option[LocalDateTime]           = None,  // DateTime of end reading quiz
  updatedAt:     LocalDateTime                   = NOW,   // DateTime of updated
  createdAt:     LocalDateTime                   = NOW    // DateTime of created
) extends EntityModel[Id]

/**
 * Companion object
 */
object Answer {

  // --[ New Types ]------------------------------------------------------------
  val  Id = the[Identity[Id]]
  type Id = Long @@ Answer

  // --[ Enum ]-----------------------------------------------------------------
  /**
   * Question enum
   */
  sealed abstract class Question(val code: Short) extends EnumStatus
  object Question extends EnumStatus.Of[Question] {
    case object IS_FIRST  extends Question(code = 1)
    case object IS_SECOND extends Question(code = 2)
    case object IS_THIRD  extends Question(code = 3)
    case object IS_FOURTH extends Question(code = 4)
    case object IS_FIFTH  extends Question(code = 5)
  }

  // --[ Enum: Choice ]---------------------------------------------------------
  /**
    * Choice enum
    */
  sealed abstract class Choice(val code: Short) extends EnumStatus
  object Choice extends EnumStatus.Of[Choice] {
    case object IS_FIRST  extends Choice(code = 1)
    case object IS_SECOND extends Choice(code = 2)
    case object IS_THIRD  extends Choice(code = 3)
  }
}
