/*
 * Copyright Jo Harada. All Rights Reserved.
 *
 * For the full copyright and license information,
 * please view the LICENSE file that was distributed with this source code.
 */

package lib.student.model

import ixias.model._
import java.time.LocalDateTime
import lib.udb.model.User

/**
 * Answer data of problem A
 */
import AnswerA._
case class AnswerA(
  id:          Option[Id]    = None,  // Id
  uid:         User.Id,               // Id of user
  readScore:   Option[Int]   = None,  // Score of reading
  speedSilent: Option[Int]   = None,  // Speed of silent reading
  speedReply:  Option[Int]   = None,  // Speed of reply
  speedOral:   Option[Int]   = None,  // Speed of silent reading
  updatedAt:   LocalDateTime = NOW,   // DateTime of updated
  createdAt:   LocalDateTime = NOW    // DateTime of created
) extends EntityModel[Id]

/**
 * Companion object
 */
object AnswerA {

  // --[ New Types ]------------------------------------------------------------
  val  Id = the[Identity[Id]]
  type Id = Long @@ AnswerA
}
