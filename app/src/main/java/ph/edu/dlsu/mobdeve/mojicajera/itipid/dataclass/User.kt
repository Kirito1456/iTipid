package ph.edu.dlsu.mobdeve.mojicajera.itipid.dataclass

class User(
    var UID : String,
    var Name : String,
    var Username: String,
    var Password: String,
    var Transactions: ArrayList<Transactions>,
    var Goals: ArrayList<Goals>,
    var Bills: ArrayList<Bills>
) {
}