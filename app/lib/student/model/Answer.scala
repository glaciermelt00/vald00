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
import lib.udb.model.User

/**
 * Answer data of problem
 */
import Answer._
case class Answer(
  id:          Option[Id]      = None,  // Id
  uid:         User.Id,               // Id of user
  readAnswer:  Seq[ReadAnswer] = Nil,   // Answer of reading
  speedSilent: Option[Int]     = None,  // Speed of silent reading
  speedReply:  Option[Int]     = None,  // Speed of reply
  speedOral:   Option[Int]     = None,  // Speed of silent reading
  updatedAt:   LocalDateTime   = NOW,   // DateTime of updated
  createdAt:   LocalDateTime   = NOW    // DateTime of created
) extends EntityModel[Id]

/**
 * Companion object
 */
object Answer {

  // --[ New Types ]------------------------------------------------------------
  val  Id = the[Identity[Id]]
  type Id = Long @@ Answer

  /**
   * ReadAnswer
   */
  sealed abstract class ReadAnswer(val code: Long, val name: String) extends EnumBitFlags
  object ReadAnswer extends EnumBitFlags.Of[ReadAnswer] {
    case object IS_CORRECT_ON_ONE    extends ReadAnswer(code = 1 << 0, name = "問一正解")
    case object IS_CORRECT_ON_TWO    extends ReadAnswer(code = 1 << 1, name = "問二正解")
    case object IS_CORRECT_ON_THREE  extends ReadAnswer(code = 1 << 2, name = "問三正解")
    case object IS_CORRECT_ON_FOUR   extends ReadAnswer(code = 1 << 3, name = "問四正解")
    case object IS_CORRECT_ON_FIVE   extends ReadAnswer(code = 1 << 4, name = "問五正解")
  }
}
