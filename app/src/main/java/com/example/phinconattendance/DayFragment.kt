package com.example.phinconattendance

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.phinconattendance.databinding.FragmentDayBinding
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import java.util.Calendar


class DayFragment : Fragment() {

    private var _binding: FragmentDayBinding? = null
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

        _binding = FragmentDayBinding.inflate(inflater, container, false)
        val root: View = binding.root

        listLogDay = ArrayList()

        getDataFromFirebase()

        return root
    }

    private fun getDataFromFirebase() {

        // Get the current date in milliseconds
        val currentDate = Calendar.getInstance()
        val currentMillis = currentDate.timeInMillis

// Set the time to the start of the day
        currentDate.set(Calendar.HOUR_OF_DAY, 0)
        currentDate.set(Calendar.MINUTE, 0)
        currentDate.set(Calendar.SECOND, 0)
        currentDate.set(Calendar.MILLISECOND, 0)

// Calculate the start and end timestamps for today
        val startOfDayMillis = currentDate.timeInMillis
        val endOfDayMillis = startOfDayMillis + (24 * 60 * 60 * 1000) // Add 24 hours in milliseconds

        val firestore: FirebaseFirestore = FirebaseFirestore.getInstance()
        val recyclerView = DayAdapter(listLogDay)
        val rvLocation = binding.rvDay

        rvLocation.adapter = recyclerView
        rvLocation.layoutManager = LinearLayoutManager(this.context)

        val eventsCollection = firestore.collection("Data")
        eventsCollection.whereGreaterThanOrEqualTo("dataInfoDate", startOfDayMillis)
            .whereLessThan("dataInfoDate", endOfDayMillis)
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

            Log.d("cekGetData", listLogDay.toString())
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}