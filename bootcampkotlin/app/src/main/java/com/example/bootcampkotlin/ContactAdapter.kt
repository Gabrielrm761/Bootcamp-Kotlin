package com.example.bootcampkotlin

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

//A classe adapter existe para realizar o gerenciamento da lista como um todo
class ContactAdapter(var listener: ClickItemContactListener) :
    RecyclerView.Adapter<ContactAdapter.ContactAdapterViewHolder>() {

    private val list: MutableList<Contact> = mutableListOf()
//Responsavel por criar o item da lista (card,textview...)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactAdapterViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.contact_item, parent, false) //Indica qual o arquivo xml que vai ser responsavel por mostrar cada item na tela
        return ContactAdapterViewHolder(view, list, listener)
    }
//Responsavel por popular o item na lista (view)
    override fun onBindViewHolder(holder: ContactAdapterViewHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount(): Int {
        return list.size
    }

    //Acessa o adapter e passa a lista para ele
    fun updateList(list: List<Contact>){
        this.list.clear()
        this.list.addAll(list)
        notifyDataSetChanged() //notifica o adapter que lista que ele utiliza para fazer a renderização foi modificada.
    }

//Responsavel por gerenciar cada item da lista
    class ContactAdapterViewHolder(itemView: View, var list: List<Contact>, var listener: ClickItemContactListener) :
    RecyclerView.ViewHolder(itemView){
        private val tvName: TextView = itemView.findViewById(R.id.tv_name)
        private val tvPhone: TextView = itemView.findViewById(R.id.tv_phone)
        private val ivPhotograph: ImageView = itemView.findViewById(R.id.iv_photograph)

        init {
            itemView.setOnClickListener{
                listener.clickItemContact(list[adapterPosition])
            }
        }


        fun bind(contact: Contact){
            tvName.text = contact.name
            tvPhone.text = contact.phone
        }
    }
}