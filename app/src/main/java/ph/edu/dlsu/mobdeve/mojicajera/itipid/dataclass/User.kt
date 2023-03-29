package ph.edu.dlsu.mobdeve.mojicajera.itipid.dataclass

class User(
    var userId : String? = null,
    var Username: String?= null,
    var Password: String?= null,
    var Transactions: ArrayList<Transactions>?= null,
    var Goals: ArrayList<Goals>?= null,
    var Bills: ArrayList<Bills>?= null
) {
}