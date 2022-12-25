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

/**
 * Group data
 */
import Group._
case class Group(
  id:        Option[Id]    = None,              // Id
  grade:     Grade         = Grade.ELEMENTARY,  // Grade of school
  clas:      Int,                               // Class in grade
  updatedAt: LocalDateTime = NOW,               // DateTime of updated
  createdAt: LocalDateTime = NOW                // DateTime of created
) extends EntityModel[Id]

/**
 * Companion object
 */
object Group {

  // --[ New Types ]------------------------------------------------------------
  val  Id = the[Identity[Id]]
  type Id = Long @@ Group

  // --[ Enum: Grade ]----------------------------------------------------------
  sealed abstract class Grade(val code: Short) extends EnumStatus
  object Grade extends EnumStatus.Of[Grade] {
    case object ELEMENTARY  extends Grade(code =  7)
    case object JUNIOR_HIGH extends Grade(code = 13)
    case object SENIOR_HIGH extends Grade(code = 16)
  }
}
