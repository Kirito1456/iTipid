package ph.edu.dlsu.mobdeve.mojicajera.itipid.dataclass

import java.time.LocalDate
import java.util.*

class Bills(
    val uid: String?= null,
    val label: String?= null,
    val amount: Double?= null,
    val dueDate: LocalDate?= null,
    val status: String?= null
    ) {

}