package com.zenpath.dev.criminalintent

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

private const val TAG = "CrimeListFragment"

class CrimeListFragment : Fragment() {
    private lateinit var crimeRecyclerView: RecyclerView
    private var adapter: CrimeAdapter? = null

    companion object {
        const val DEFAULT_TYPE_VIEW = 0
        const val HARD_TYPE_VIEW = 1

        fun newInstance(): CrimeListFragment {
            return CrimeListFragment()
        }
    }

    private val crimeListViewModel: CrimeListViewModel by lazy {
        ViewModelProviders.of(this)[CrimeListViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "Total crimes: ${crimeListViewModel.crimes.size}")
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_crime_list, container, false)
        crimeRecyclerView = view.findViewById(R.id.crime_recycler_view) as RecyclerView
        crimeRecyclerView.layoutManager = LinearLayoutManager(context)

        updateUI()

        return view
    }

    private fun updateUI(){
        val crimes = crimeListViewModel.crimes
        adapter = CrimeAdapter(crimes)
        crimeRecyclerView.adapter = adapter
    }

    private inner class CrimeHolder(view: View): RecyclerView.ViewHolder(view), View.OnClickListener {

        private lateinit var crime: Crime
        val titleTextView: TextView = itemView.findViewById(R.id.crime_title)
        val dateTextView: TextView = itemView.findViewById(R.id.crime_date)

        init {
            itemView.setOnClickListener(this)
        }

        fun bind(crime: Crime){
            this.crime = crime
            titleTextView.text = this.crime.title
            dateTextView.text = this.crime.date.toString()
        }

        override fun onClick(v: View?) {
            Toast.makeText(context, "${crime.title} pressed!", Toast.LENGTH_SHORT).show()
        }
    }

    private inner class HardCrimeHolder(view: View): RecyclerView.ViewHolder(view), View.OnClickListener{
        private lateinit var hardCrime: Crime
        val hardInfoLayout: LinearLayout = itemView.findViewById(R.id.infoLayout)
        val hardTitleTextView: TextView = itemView.findViewById(R.id.hard_crime_title)
        val hardDateTextView: TextView = itemView.findViewById(R.id.hard_crime_date)
        val btnCallPolice: Button = itemView.findViewById(R.id.btn_call_police)

        init{
            hardInfoLayout.setOnClickListener(this)
            btnCallPolice.setOnClickListener(this)
        }

        fun bind(crime: Crime){
            this.hardCrime = crime
            hardTitleTextView.text = this.hardCrime.title
            hardDateTextView.text = this.hardCrime.date.toString()
        }
        override fun onClick(v: View?) {
            when (v?.id){
                R.id.infoLayout -> {
                    Toast.makeText(context, "${hardCrime.title} hard pressed!", Toast.LENGTH_SHORT).show()
                }
                R.id.btn_call_police -> {
                    Toast.makeText(context, "Called the police! Wait...", Toast.LENGTH_SHORT).show()
                }
            }
        }

    }

    private inner class CrimeAdapter(var crimes: List<Crime>) :RecyclerView.Adapter<RecyclerView.ViewHolder>(){

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
            if (viewType == 0){
                val view = layoutInflater.inflate(R.layout.list_item_crime, parent, false)
                return CrimeHolder(view)
            }else {
                val view = layoutInflater.inflate(R.layout.list_item_hard_crime, parent, false)
                return HardCrimeHolder(view)
            }
        }

        override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
            val crime = crimes[position]
            if(holder.itemViewType == DEFAULT_TYPE_VIEW){
                val crimeHolder: CrimeHolder = holder as CrimeHolder
                crimeHolder.bind(crime)
            }else{
                val hardCrimeHolder: HardCrimeHolder = holder as HardCrimeHolder
                hardCrimeHolder.bind(crime)
            }
        }

        override fun getItemCount() = crimes.size

        override fun getItemViewType(position: Int): Int {
            return if(!crimes[position].requiresPolice){
                DEFAULT_TYPE_VIEW
            }else{
                HARD_TYPE_VIEW
            }
        }
    }
}