package com.example.phinconattendance

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.phinconattendance.databinding.FragmentMonthBinding
import com.example.phinconattendance.databinding.FragmentWeekBinding
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import java.util.Calendar
import java.util.Date

class MonthFragment : Fragment() {

    private var _binding: FragmentMonthBinding? = null
    private val binding get() = _binding!!
    private lateinit var listLogDay : ArrayList<LogDay>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (activity as AppCompatActivity?)!!.supportActionBar!!.hide()
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentMonthBinding.inflate(inflater, container, false)
        val root: View = binding.root

        listLogDay = ArrayList()

        getDataFromFirebase()

        return root
    }

    private fun getDataFromFirebase() {

        // Get the current date
        val currentDate = Date()

        // Calculate the date range for the past month
        val calendar = Calendar.getInstance()
        calendar.time = currentDate
        calendar.add(Calendar.MONTH, -1) // Subtract 1 month

        // Set the time to the start of the day
        calendar.set(Calendar.HOUR_OF_DAY, 0)
        calendar.set(Calendar.MINUTE, 0)
        calendar.set(Calendar.SECOND, 0)
        calendar.set(Calendar.MILLISECOND, 0)

        val oneMonthAgo = calendar.timeInMillis

        val firestore: FirebaseFirestore = FirebaseFirestore.getInstance()
        val recyclerView = DayAdapter(listLogDay)
        val rvLocation = binding.rvMonth

        rvLocation.adapter = recyclerView
        rvLocation.layoutManager = LinearLayoutManager(this.context)

        val eventsCollection = firestore.collection("Data")
        eventsCollection.whereGreaterThan("dataInfoDate", oneMonthAgo)
            .orderBy("dataInfoDate", Query.Direction.DESCENDING)
            .addSnapshotListener { value, error ->
            if (error != null) {
                // Handle error
                return@addSnapshotListener
            }

            val newLogDayList = mutableListOf<LogDay>()
            value?.documents?.forEach { document ->
                val day = LogDay(
                    document.getLong("dataInfoImage") ?: 0,
                    document.getString("dataInfoNamePlace") ?: "",
                    document.getString("dataInfoAddress") ?: "",
                    document.getString("dataInfoStatus") ?: "",
                    document.getString("dataInfoTime") ?: ""
                )
                newLogDayList.add(day)
            }

            if (listLogDay != newLogDayList) {
                listLogDay.clear()
                listLogDay.addAll(newLogDayList)
                recyclerView.notifyDataSetChanged()
            }
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}