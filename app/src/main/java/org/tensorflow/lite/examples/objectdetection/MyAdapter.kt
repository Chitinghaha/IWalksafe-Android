package org.tensorflow.lite.examples.objectdetection

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView

class MyAdapter(
    it1: Context,
    private val data: MutableList<String>,
    val onClickDelete: (Int) -> Unit,
): RecyclerView.Adapter<MyAdapter.MyViewHolder>() {

    val con = it1
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
//            when(listdata[index]){
//                "家"-> {
//                    val gmmIntentUri =
//                        Uri.parse("google.navigation:q=24°47'12 120°59'49&mode=w")
//                    val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
//                    mapIntent.setPackage("com.google.android.apps.maps")
//
//                    // check have google map app
//                    view.context?.let {
//                        mapIntent.resolveActivity(it.packageManager)?.let {
//                            view.context.startActivity(mapIntent)
//                        }
//                    }
//                }
//            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        //載入項目模板
        val inflater = LayoutInflater.from(parent.context)
        val example = inflater.inflate(R.layout.items_example, parent, false)
        return MyViewHolder(example)
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
            when(listdata[position]){
                "家"-> {
                    val gmmIntentUri =
                        Uri.parse("google.navigation:q=24°47'12 120°59'49&mode=w")
                    val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
                    mapIntent.setPackage("com.google.android.apps.maps")

                    // check have google map app
                    holder.itemView.context?.let {
                        mapIntent.resolveActivity(it.packageManager)?.let {
                            holder.itemView.context.startActivity(mapIntent)
                        }
                    }
                }
                "故宮"->{

                    val gmmIntentUri =
                        Uri.parse("google.navigation:q=25.0937761,121.4808181&mode=w")
                    val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
                    mapIntent.setPackage("com.google.android.apps.maps")

                    // check have google map app
                    holder.itemView.context?.let {
                        mapIntent.resolveActivity(it.packageManager)?.let {
                            holder.itemView.context.startActivity(mapIntent)
                        }
                    }
                }
                else->{
                    val str = listdata[position]
                    val gmmIntentUri =
                        Uri.parse("google.navigation:q=$str&mode=w")
                    val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
                    mapIntent.setPackage("com.google.android.apps.maps")

                    // check have google map app
                    holder.itemView.context?.let {
                        mapIntent.resolveActivity(it.packageManager)?.let {
                            holder.itemView.context.startActivity(mapIntent)
                        }
                    }

                }
            }
        }
    }



    override fun getItemCount(): Int = listdata.size


    fun setItems(items:MutableList<String>){
        listdata = items
        notifyDataSetChanged()
    }
}