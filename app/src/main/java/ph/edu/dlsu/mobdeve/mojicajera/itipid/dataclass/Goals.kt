package ph.edu.dlsu.mobdeve.mojicajera.itipid.dataclass

import java.util.Date

class Goals (
    val uid: String,
    val Day: Date,
    val Amount: Double,
    val label: String,
    val progress: Double,
    val isComplete: Boolean
        ) {
}