package com.aslan.contactosapp

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView

class AdaptadorPersonalizado(var context: Context, items:ArrayList<Contacto>): BaseAdapter() {

    //Almacenar los elementos que se van a mostrar en el ListView
    var items:ArrayList<Contacto>? = null
    var copiaItems:ArrayList<Contacto>? = null

    init {
        this.items = ArrayList(items) //DeepCopy o copia completamente nueva
        this.copiaItems = items //Copia intacta o inmutable
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        var holder:ViewHolder? = null
        var vista:View? = convertView

        if(vista == null){
            vista = LayoutInflater.from(context).inflate(R.layout.plantilla_contactos,null)
            holder = ViewHolder(vista)
            vista.tag = holder
        } else {
            holder = vista.tag as? ViewHolder
        }

        val item = getItem(position) as Contacto
        //Asignacion de valores a elementos graficos
        holder?.nombre?.text = item.nombre + " " + item.apellido
        holder?.puesto?.text = item.ocupacion
        holder?.imagen?.setImageResource(item.foto)

        return vista!!
    }

    override fun getItem(position: Int): Any {
        return items?.get(position)!!
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getCount(): Int {
        return items?.count()!!
    }

    private class ViewHolder(vista:View){
        var nombre: TextView? = null
        var imagen: ImageView? = null
        var puesto:TextView?= null

        init {
            nombre = vista.findViewById(R.id.tvNombreContacto)
            imagen = vista.findViewById(R.id.ivFotoContacto)
            puesto = vista.findViewById(R.id.tvInfoContacto)
        }
    }

    fun filtrar(str:String) {
        items?.clear()

        if(str.isEmpty()) {
            items = ArrayList(copiaItems)
            notifyDataSetChanged()
            return
        }
        var busqueda = str
        busqueda = busqueda.toLowerCase() //Busqueda en minisculas para evitar conflictos

        for (item in copiaItems!!) {
            //Empezar a buscar contacto por su nombre
            val nombre = item.nombre.toLowerCase()
            if(nombre.contains(busqueda)) {
                items?.add(item)
            }
        }

        notifyDataSetChanged()
    }

    fun addItem(item:Contacto) {
        copiaItems?.add(item)
        items = ArrayList(copiaItems)
        notifyDataSetChanged()
    }

    fun removeItem(index:Int) {
        copiaItems?.removeAt(index)
        items = ArrayList(copiaItems)
        notifyDataSetChanged()
    }

    fun updateItem(index:Int, newItem:Contacto) {
        copiaItems?.set(index,newItem)
        items = ArrayList(copiaItems)
        notifyDataSetChanged()
    }
}