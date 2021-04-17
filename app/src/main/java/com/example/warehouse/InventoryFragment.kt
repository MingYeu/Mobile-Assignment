package com.example.warehouse

import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Base64
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER


/**
 * A simple [Fragment] subclass.
 * Use the [InventoryFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class InventoryFragment(val rack:String) : Fragment() {
    private var layoutManager: RecyclerView.LayoutManager? = null
    private var adapter: RecyclerView.Adapter<InventoryAdapter.ViewHolder>? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_inventory, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // postToList()
        //val rvMap: RecyclerView = view.findViewById(R.id.rv_map)


        var items = ArrayList<Inventory>()
        val database = FirebaseDatabase.getInstance()
        val myRef = database.getReference("Stock")
        val InvList: RecyclerView = view.findViewById(R.id.InvList)

/*
        var prodName:String="Name"
        var prodQuantity:String="Qty"
        var prodID :String="Id"
*/

        var getData = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (c in snapshot.children) {
                    if (c.child("rack").getValue().toString().equals(rack)) {
                        var image = c.child("image").getValue().toString()
                        var prodName = c.child("name").getValue().toString()
                        var prodQuantity = c.child("quantity").getValue().toString()
                        var prodID = c.child("stockId").getValue().toString()



                        items.add(Inventory(image,prodID, prodName, prodQuantity))
                    }
                }
                InvList.adapter = InventoryAdapter(items, this@InventoryFragment.requireContext())
                InvList.layoutManager = LinearLayoutManager(activity)
                InvList.setHasFixedSize(true)

            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(
                    activity,
                    "An error has occurred",
                    Toast.LENGTH_SHORT
                ).show()
            }

        }
        myRef.addValueEventListener(getData)
    }
}