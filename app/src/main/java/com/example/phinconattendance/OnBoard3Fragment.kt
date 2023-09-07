package com.example.phinconattendance

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.phinconattendance.databinding.FragmentOnBoard1Binding
import com.example.phinconattendance.databinding.FragmentOnBoard3Binding


private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class OnBoard3Fragment : Fragment() {

    private var param1: String? = null
    private var param2: String? = null
    private var _binding: FragmentOnBoard3Binding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_on_board3, container, false)
        _binding = FragmentOnBoard3Binding.inflate(inflater, container, false)

        binding.skip.setOnClickListener {
            val intentToLogin = Intent(this.context, LoginActivity::class.java)
            startActivity(intentToLogin)
        }

        val view = binding.root
        return view
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            OnBoard3Fragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}