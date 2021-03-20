package com.example.warehouse

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"
/**
 * A simple [Fragment] subclass.
 * Use the [TrackFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class TrackFragment : Fragment() {

    private var titlesList = mutableListOf<String>()
    private var detailsList = mutableListOf<String>()

    private  var layoutManager: RecyclerView.LayoutManager?=null
    private var adapter: RecyclerView.Adapter<RecyclerAdapter.ViewHolder>?=null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_track, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        postToList()
        val rv_track: RecyclerView = view.findViewById(R.id.rv_track)

        rv_track.apply {
            layoutManager= LinearLayoutManager(activity)
            adapter=RecyclerAdapter(titlesList,detailsList)
        }
    }

    private fun addToList(title:String,description:String){
        titlesList.add(title)
        detailsList.add(description)
    }

    private fun postToList(){
        for(i in 1..25){
            addToList("Title $i","Description $i")
        }
    }
    //https://medium.com/inside-ppl-b7/recyclerview-inside-fragment-with-android-studio-680cbed59d84https://medium.com/inside-ppl-b7/recyclerview-inside-fragment-with-android-studio-680cbed59d84

}
