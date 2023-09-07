package com.example.phinconattendance.ui.home

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.phinconattendance.DataInfo
import com.example.phinconattendance.ItemAdapter
import com.example.phinconattendance.Location
import com.example.phinconattendance.LocationAdapter
import com.example.phinconattendance.R
import com.example.phinconattendance.databinding.FragmentHomeBinding
import com.example.phinconattendance.ui.profile.ProfileFragment
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ServerTimestamp
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle
import java.util.Calendar
import java.util.Date
import kotlin.properties.Delegates


open class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private lateinit var dataInfo: DataInfo
    private lateinit var db: FirebaseFirestore
    private lateinit var recyclerView: LocationAdapter


    private var namePlace: String = ""
    private var image: Int = 0
    private var address: String = ""
    private var time: String = ""
    private var date by Delegates.notNull<Long>()
    private var status: String = ""

    private lateinit var listLocation: ArrayList<Location>
    private lateinit var homeViewModel: HomeViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (activity as AppCompatActivity?)!!.supportActionBar!!.hide()
    }


    @SuppressLint("SimpleDateFormat")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        homeViewModel = ViewModelProvider(this)[HomeViewModel::class.java]

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root


        val localDateTime: LocalDateTime = LocalDateTime.now()
        val zonedDateTime: java.time.ZonedDateTime = localDateTime.atZone(java.time.ZoneId.of("Asia/Jakarta"))
        val currentTimestamp: Long = zonedDateTime.toInstant().toEpochMilli()

        val sdfDate = SimpleDateFormat("dd MMMM yyyy")
        val sdfTime = SimpleDateFormat("HH:mm")
        val currentDate = sdfDate.format(Date())
        val currentTime = sdfTime.format(Date())
        binding.realtimedate.text = currentDate
        binding.realtimehour.text = currentTime
        time = currentTime
        date = currentTimestamp



        val dataPlace = resources.getStringArray(R.array.data_place)
        val dataAddress = resources.getStringArray(R.array.data_address)
        val dataPhoto = resources.obtainTypedArray(R.array.data_photo)
        listLocation = ArrayList()

        for (i in dataPlace.indices) {
            val location = Location(dataPhoto.getResourceId(i, -1), dataPlace[i], dataAddress[i])
            listLocation.add(location)
        }
        Log.d("ceklistLocation", listLocation.toString())

        recyclerView = LocationAdapter(listLocation)
        val rvLocation = binding.rvLocation

        recyclerView.setOnItemClickCallback(object : LocationAdapter.OnItemClickCallback{
            override fun onItemClicked(data: Location) {
                homeViewModel.setSelectedLocation(data)
                namePlace = data.item_namePlace
                image = data.item_Image
                address = data.item_address
            }
        })

        rvLocation.adapter = recyclerView
        rvLocation.layoutManager = LinearLayoutManager(this.context)


        db = FirebaseFirestore.getInstance()
        uploaddata()

        return root
    }


    @SuppressLint("ResourceType")
    private fun uploaddata() {
        binding.checkinoutButton.setOnClickListener {
            Log.d("cekklik", "klik berhasil")

            // Check if an item is selected
//            if (!recyclerView.isItemSelected()) {
//                // Show a Toast indicating that the user needs to choose a location first
//                Toast.makeText(context, "Please Choose a Location First", Toast.LENGTH_SHORT).show()
//                return@setOnClickListener
//            }

            if (homeViewModel.selectedLocation.value == null) {
                // Show a Toast indicating that the user needs to choose a location first
                Toast.makeText(context, "Please Choose a Location First", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            Log.d("cekstring1", binding.checkinoutButton.text.toString())
            Log.d("cekstring2", resources.getString(R.string.chcekout))

            //INI LOGIC BUAT CHECKOUT
             if (homeViewModel.buttonState) {

                 status = "Check Out"
                 addDatatoFirebase(address, date, image, namePlace, status, time)
                 Log.d("cekStatusout", status)

                 binding.checkinoutButton.setBackgroundColor(resources.getColor(R.color.checkin))
                 binding.checkinoutButton.setText(R.string.checkin)
                 binding.checkinoutButton.setBackgroundDrawable(resources.getDrawable(R.drawable.buttoncheckin))
                 homeViewModel.buttonState = false

                 val dataPlace = resources.getStringArray(R.array.data_place)
                 val dataAddress = resources.getStringArray(R.array.data_address)
                 val dataPhoto = resources.obtainTypedArray(R.array.data_photo)
                 listLocation = ArrayList()

                 for (i in dataPlace.indices) {
                     val location = Location(dataPhoto.getResourceId(i, -1), dataPlace[i], dataAddress[i])
                     listLocation.add(location)
                 }
                 Log.d("ceklistLocation", listLocation.toString())

                 recyclerView = LocationAdapter(listLocation)
                 val rvLocation = binding.rvLocation

                 recyclerView.setOnItemClickCallback(object : LocationAdapter.OnItemClickCallback{
                     override fun onItemClicked(data: Location) {
                         namePlace = data.item_namePlace
                         image = data.item_Image
                         address = data.item_address
                     }
                 })

                 rvLocation.adapter = recyclerView
                 rvLocation.layoutManager = LinearLayoutManager(this.context)

             }

            //INI LOGIC BUAT CHECKIN
            else {
                 status = "Check In"
                 addDatatoFirebase(address, date, image, namePlace, status, time)
                 Log.d("cekStatusin", status)

                 binding.checkinoutButton.setBackgroundColor(resources.getColor(R.color.checkout))
                 binding.checkinoutButton.setText(R.string.chcekout)
                 binding.checkinoutButton.setBackgroundDrawable(resources.getDrawable(R.drawable.buttoncheckout))
                 homeViewModel.buttonState = true

                 if (namePlace == "PT. Phincon") {
                     val dataPlace = resources.getStringArray(R.array.data_place)
                     val dataAddress = resources.getStringArray(R.array.data_address)
                     val dataPhoto = resources.obtainTypedArray(R.array.data_photo)

                         val location = Location(dataPhoto.getResourceId(0,-1), dataPlace[0], dataAddress[0])
                         listLocation.retainAll(listOf(location).toSet())

                     Log.d("ceklistLocation", listLocation.toString())
                     val newrecyclerView = ItemAdapter(listLocation)
                     val rvLocation = binding.rvLocation
                     rvLocation.adapter = newrecyclerView
                     rvLocation.layoutManager = LinearLayoutManager(this.context)
                 } else if (namePlace == "Telkomsel Smart Office") {
                     val dataPlace = resources.getStringArray(R.array.data_place)
                     val dataAddress = resources.getStringArray(R.array.data_address)
                     val dataPhoto = resources.obtainTypedArray(R.array.data_photo)

                     val location = Location(dataPhoto.getResourceId(1,-1), dataPlace[1], dataAddress[1])
                     listLocation.retainAll(listOf(location).toSet())

                     Log.d("ceklistLocation", listLocation.toString())
                     val newrecyclerView = ItemAdapter(listLocation)
                     val rvLocation = binding.rvLocation
                     rvLocation.adapter = newrecyclerView
                     rvLocation.layoutManager = LinearLayoutManager(this.context)
                 } else {
                     val dataPlace = resources.getStringArray(R.array.data_place)
                     val dataAddress = resources.getStringArray(R.array.data_address)
                     val dataPhoto = resources.obtainTypedArray(R.array.data_photo)

                     val location = Location(dataPhoto.getResourceId(2,-1), dataPlace[2], dataAddress[2])
                     listLocation.retainAll(listOf(location).toSet())

                     Log.d("ceklistLocation", listLocation.toString())
                     val newrecyclerView = ItemAdapter(listLocation)
                     val rvLocation = binding.rvLocation
                     rvLocation.adapter = newrecyclerView
                     rvLocation.layoutManager = LinearLayoutManager(this.context)
                 }
            }

        }
    }

    private fun addDatatoFirebase(address: String, date:Long, image:Int, namePlace: String, status:String, time:String) {

        val dbData = db.collection("Data")
        dataInfo = DataInfo(address, date, image, namePlace, status, time)
        Log.d("cekData", status)

        dbData.add(dataInfo)
            .addOnSuccessListener(OnSuccessListener<DocumentReference?> { // after the data addition is successful
                // we are displaying a success toast message.
                Toast.makeText(
                    context,
                    "Your Course has been added to Firebase Firestore",
                    Toast.LENGTH_SHORT
                ).show()
            })
            .addOnFailureListener(OnFailureListener { e -> // this method is called when the data addition process is failed.
                // displaying a toast message when data addition is failed.
                Toast.makeText(context, "Fail to add course \n$e", Toast.LENGTH_SHORT)
                    .show()
            })

    }


    @SuppressLint("ResourceType")
    override fun onResume() {
        super.onResume()

        if (!homeViewModel.buttonState) {
            binding.checkinoutButton.setBackgroundColor(resources.getColor(R.color.checkin))
            binding.checkinoutButton.setText(R.string.checkin)
            binding.checkinoutButton.setBackgroundDrawable(resources.getDrawable(R.drawable.buttoncheckin))
            homeViewModel.buttonState = false
        }

        //INI LOGIC BUAT CHECKIN
        else {
            binding.checkinoutButton.setBackgroundColor(resources.getColor(R.color.checkout))
            binding.checkinoutButton.setText(R.string.chcekout)
            binding.checkinoutButton.setBackgroundDrawable(resources.getDrawable(R.drawable.buttoncheckout))
            homeViewModel.buttonState = true

            namePlace = homeViewModel.namePlace.value ?: ""
            image = homeViewModel.image.value ?: 0
            address = homeViewModel.address.value ?: ""

            if (namePlace == "PT. Phincon") {
                val dataPlace = resources.getStringArray(R.array.data_place)
                val dataAddress = resources.getStringArray(R.array.data_address)
                val dataPhoto = resources.obtainTypedArray(R.array.data_photo)

                val location = Location(dataPhoto.getResourceId(0,-1), dataPlace[0], dataAddress[0])
                listLocation.retainAll(listOf(location).toSet())

                Log.d("ceklistLocation", listLocation.toString())
                val newrecyclerView = ItemAdapter(listLocation)
                val rvLocation = binding.rvLocation
                rvLocation.adapter = newrecyclerView
                rvLocation.layoutManager = LinearLayoutManager(this.context)
            } else if (namePlace == "Telkomsel Smart Office") {
                val dataPlace = resources.getStringArray(R.array.data_place)
                val dataAddress = resources.getStringArray(R.array.data_address)
                val dataPhoto = resources.obtainTypedArray(R.array.data_photo)

                val location = Location(dataPhoto.getResourceId(1,-1), dataPlace[1], dataAddress[1])
                listLocation.retainAll(listOf(location).toSet())

                Log.d("ceklistLocation", listLocation.toString())
                val newrecyclerView = ItemAdapter(listLocation)
                val rvLocation = binding.rvLocation
                rvLocation.adapter = newrecyclerView
                rvLocation.layoutManager = LinearLayoutManager(this.context)
            } else {
                val dataPlace = resources.getStringArray(R.array.data_place)
                val dataAddress = resources.getStringArray(R.array.data_address)
                val dataPhoto = resources.obtainTypedArray(R.array.data_photo)

                val location = Location(dataPhoto.getResourceId(2,-1), dataPlace[2], dataAddress[2])
                listLocation.retainAll(listOf(location).toSet())

                Log.d("ceklistLocation", listLocation.toString())
                val newrecyclerView = ItemAdapter(listLocation)
                val rvLocation = binding.rvLocation
                rvLocation.adapter = newrecyclerView
                rvLocation.layoutManager = LinearLayoutManager(this.context)
            }

        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}