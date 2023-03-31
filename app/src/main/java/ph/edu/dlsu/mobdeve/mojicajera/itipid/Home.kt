package ph.edu.dlsu.mobdeve.mojicajera.itipid

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.ImageButton
import android.widget.TableLayout
import android.widget.TextView
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayout.OnTabSelectedListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import ph.edu.dlsu.mobdeve.mojicajera.itipid.adapters.FragmentPageAdapter
import ph.edu.dlsu.mobdeve.mojicajera.itipid.adapters.RecyclerViewAdapter
import ph.edu.dlsu.mobdeve.mojicajera.itipid.dataclass.Transactions

class Home : AppCompatActivity() {

     lateinit var tabLayout: TabLayout
     lateinit var viewPager2: ViewPager2
     lateinit var adapter : FragmentPageAdapter
     lateinit var mAuth: FirebaseAuth
    private lateinit var mainButton: FloatingActionButton
    private lateinit var addTransaction: FloatingActionButton
    private lateinit var addBillsButton: FloatingActionButton
    private lateinit var addGoalsButton: FloatingActionButton
    private lateinit var transactionList: ArrayList<Transactions>
    private lateinit var database: DatabaseReference
    //private lateinit var totalAmount: TextView

     private val rotateOpen: Animation by lazy {AnimationUtils.loadAnimation(this, R.anim.rotate_open_anim)}
     private val rotateClose: Animation by lazy {AnimationUtils.loadAnimation(this, R.anim.rotate_close_anim)}
     private val fromBottom: Animation by lazy {AnimationUtils.loadAnimation(this, R.anim.from_bottom_anim)}
     private val toBottom: Animation by lazy {AnimationUtils.loadAnimation(this, R.anim.to_bottom_anim)}

    private var clicked = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

         val reportsButton: ImageButton = findViewById<ImageButton>(R.id.reportsButton)

        //Balance Edit TextView
        //


        addTransaction = findViewById(R.id.addTransaction)
        addBillsButton = findViewById(R.id.addBill)
        addGoalsButton = findViewById(R.id.addGoal)
        mainButton = findViewById(R.id.mainButton)




        mAuth = FirebaseAuth.getInstance()

        totalAmount()

        tabLayout = findViewById(R.id.tabLayout)
        viewPager2 = findViewById(R.id.viewPager2)

        adapter = FragmentPageAdapter(supportFragmentManager, lifecycle)
        tabLayout.addTab(tabLayout.newTab().setText("Daily"))
        tabLayout.addTab(tabLayout.newTab().setText("Calendar"))
        tabLayout.addTab(tabLayout.newTab().setText("Bills"))
        tabLayout.addTab(tabLayout.newTab().setText("Summary"))
        tabLayout.addTab(tabLayout.newTab().setText("Goals"))

        viewPager2.adapter = adapter

        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener{
            override fun onTabSelected(tab: TabLayout.Tab?) {
                if (tab != null) {
                    viewPager2.currentItem = tab.position
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {

            }

            override fun onTabReselected(tab: TabLayout.Tab?) {

            }
        })




        // logout Button
        val settingsButton = findViewById<ImageButton>(R.id.logoutButton)
        settingsButton.setOnClickListener(){
            mAuth.signOut()
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }

        // add Button
//        val addButton = findViewById<ImageButton>(R.id.addButton)
//        addButton.setOnClickListener(){
//            val intent = Intent(this, AddBalance::class.java)
//            startActivity(intent)
//        }

        // Reports Button

        reportsButton.setOnClickListener(){
            val intent = Intent(this, Reports::class.java)
            startActivity(intent)
        }

        // Add Transaction Button

        addTransaction.setOnClickListener(){
            val intent = Intent(this, AddBalance::class.java)
            startActivity(intent)
        }
        // Add Goals Button

        addGoalsButton.setOnClickListener(){
            val intent = Intent(this, AddGoals::class.java)
            startActivity(intent)
        }
        // Add Bill Button
        val addBillButton = findViewById<ImageButton>(R.id.addBill)
        addBillButton.setOnClickListener(){
            val intent = Intent(this, AddBills::class.java)
            startActivity(intent)
        }

//        val balanceButton = findViewById<ImageButton>(R.id.balanceButton)
//        balanceButton.setOnClickListener(){
//            val intent = Intent(this, )
//        }


        mainButton.setOnClickListener{
            onAddButtonClick()
        }

        viewPager2.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback(){
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                tabLayout.selectTab(tabLayout.getTabAt(position))
            }
        })
    }

    private fun onAddButtonClick() {
        setVisibility(clicked)
        setAnimation(clicked)

        clicked = !clicked
    }

    private fun setAnimation(clicked : Boolean) {
        if (!clicked){
            addGoalsButton.startAnimation(fromBottom)
            addTransaction.startAnimation(fromBottom)
            addBillsButton.startAnimation(fromBottom)
        } else {
            addGoalsButton.startAnimation(toBottom)
            addTransaction.startAnimation(toBottom)
            addBillsButton.startAnimation(toBottom)
        }
    }

    private fun setVisibility(clicked: Boolean) {
        if(!clicked){
            addGoalsButton.visibility = View.VISIBLE
            addTransaction.visibility = View.VISIBLE
            addBillsButton.visibility = View.VISIBLE
        } else{
            addGoalsButton.visibility = View.INVISIBLE
            addTransaction.visibility = View.INVISIBLE
            addBillsButton.visibility = View.INVISIBLE
        }
    }

    private fun totalAmount(){
        database = FirebaseDatabase.getInstance().getReference("Transactions")

        var total  = 0.0

        database.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                transactionList.clear()
                if (snapshot.exists()) {
                    for (snap in snapshot.children) {
                        val transacData = snap.getValue(Transactions::class.java)
                        transactionList.add(transacData!!)
                    }
                    for (i in transactionList) {
                        if (i.description == "Income") {
                            total += i.amount!!
                        }else{
                            total -= i.amount!!
                        }
                    }
                    var totalAmount = findViewById<TextView>(R.id.Balance)
                    totalAmount.text = total.toString()
            }


        }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })

}}