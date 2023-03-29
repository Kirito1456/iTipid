package ph.edu.dlsu.mobdeve.mojicajera.itipid.dataclass

class User(
    var userId : String,
    var Username: String,
    var Password: String,
    var Transactions: ArrayList<Transactions>?,
    var Goals: ArrayList<Goals>,
    var Bills: ArrayList<Bills>?
) {
}