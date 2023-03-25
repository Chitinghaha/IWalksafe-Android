package org.tensorflow.lite.examples.objectdetection

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import org.tensorflow.lite.examples.objectdetection.fragments.CameraFragment

class MyAdapterForObject(
    private val context: Context,
    private val data: MutableList<String>,
    val onClickDelete: (Int) -> Unit
): RecyclerView.Adapter<MyAdapterForObject.MyViewHolder>() {


    private var listdata =data
    inner class MyViewHolder(val view: View): RecyclerView.ViewHolder(view){
        fun bind(text:String , index:Int){
            val tv = view.findViewById<TextView>(R.id.textId)
            val but = view.findViewById<Button>(R.id.btnClick)
            but.setOnClickListener {
                onClickDelete(index)
//                Toast.makeText(getContext(), "按下第 ${position+ 1} 個", Toast.LENGTH_SHORT).show()
                // position 從零開始的
            }
            tv.text = text
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        //載入項目模板
        val inflater = LayoutInflater.from(parent.context)
        val example = inflater.inflate(R.layout.items_example, parent, false)
        return MyViewHolder(example)
    }



    override fun getItemCount(): Int = listdata.size


    fun setItems(items:MutableList<String>){
        listdata = items
        notifyDataSetChanged()


    }


    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(listdata[position] , position)
//            holder.apply{
//                textId.text= listdata[position]
//                btnClick.setOnClickListener {
//                    Toast.makeText(context, "按下第 ${position+ 1} 個", Toast.LENGTH_SHORT).show()
//                    // position 從零開始的
//                }
        holder.itemView.setOnClickListener {
            Toast.makeText(it.context, listdata[position] , Toast.LENGTH_SHORT).show()
            //Manager
            val fragmentManager = (context as AppCompatActivity).supportFragmentManager
            val fragmentTransition = fragmentManager.beginTransaction()

            fragmentTransition.remove(CameraFragment())

            // Creating the new Fragment with the name passed in.
            val bundle = Bundle()
            val fragment = CameraFragment()
            bundle.putString("String", listdata[position])
            fragment.arguments = bundle
            fragmentTransition.add(R.id.fragment_container, fragment,"A")
            fragmentTransition.commit()
        }
    }
}