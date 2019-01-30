package com.aslan.contactosapp

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.AdapterView
import android.widget.SearchView
import android.widget.Switch
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    companion object {
        var contactos:ArrayList<Contacto>?=null
        var adaptador:AdaptadorPersonalizado? = null
        var adaptadorGrid:AdaptadorPersonalizadoGrid? = null

        fun agregarContacto(contacto: Contacto) {
            adaptador?.addItem(contacto)
            adaptadorGrid?.addItem(contacto)
        }

        fun obtenerContacto(index:Int):Contacto {
            return adaptador?.getItem(index) as Contacto
            return adaptadorGrid?.getItem(index) as Contacto
        }

        fun eliminarContacto(index:Int){
            adaptador?.removeItem(index)
            adaptadorGrid?.removeItem(index)
        }

        fun actualiazarContacto(index:Int, nuevoContacto: Contacto) {
            adaptador?.updateItem(index,nuevoContacto)
            adaptadorGrid?.updateItem(index,nuevoContacto)
        }
    }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        contactos = ArrayList()
        contactos!!.add(Contacto("Raymundo","Leon","Desarrador Movil",30,67.2f,"Mi casa","6644374117","ray@email.com",R.drawable.foto_03))
        contactos!!.add(Contacto("Maylen","Leon","Mecatronico",27,70.0f,"Su casa","6618501166","may@email.com",R.drawable.foto_05))
        contactos!!.add(Contacto("Ramiro","Diaz","Diseñador",31,90.0f,"Colonia 765","661937285","ram@email.com",R.drawable.foto_01))
        contactos!!.add(Contacto("Alfredo","Perez","Desarrollador Web",28,70.0f,"Casa 32","664928467","alf@email.com",R.drawable.foto_02))
        contactos!!.add(Contacto("Maria","Lopez","Nutriologa",25,50.0f,"Espana 834","664287493","mar@email.com",R.drawable.foto_06))


        //Inicializamos los adaptadores
        adaptador = AdaptadorPersonalizado(this, contactos!!)
        lvContactos.adapter =  adaptador

        adaptadorGrid = AdaptadorPersonalizadoGrid(this, contactos!!)
        gridContactos.adapter = adaptadorGrid

        lvContactos.onItemClickListener = AdapterView.OnItemClickListener { parent, view, position, id ->
            val intent = Intent(this,Detalle::class.java)
            intent.putExtra("ID",position.toString())
            startActivity(intent)
        }

        gridContactos.onItemClickListener =  AdapterView.OnItemClickListener { parent, view, position, id ->
            val intent = Intent(this,Detalle::class.java)
            intent.putExtra("ID",position.toString())
            startActivity(intent)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_contactos,menu)

        //Mapeamos el switch del menu
        val itemSwitch = menu?.findItem(R.id.switchVistaContacto)
        //La plantilla que el switch va a tomar para renderizarlo
        itemSwitch?.setActionView(R.layout.switch_item)
        //Elemento switch que se va a usar para las acciones
        val switchView = itemSwitch?.actionView?.findViewById<Switch>(R.id.switchCambiarVista)
        //Eventos del switch
        switchView?.setOnCheckedChangeListener { buttonView, isChecked ->
            cambiarVistas.showNext()
        }

        // PARTE DE LA BUSQUEDA DE CONTACTOS //
        //Servicio que permite implementar la busqueda
        val searManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        //Mapeamos el buscador del menu contactos
        val itemBusqueda = menu?.findItem(R.id.etBuscarContacto)
        val searchView = itemBusqueda?.actionView as SearchView
        //Trabajamos con el searchview
        searchView.setSearchableInfo(searManager.getSearchableInfo(componentName))
        searchView.queryHint = "Buscar contacto"
        searchView.setOnQueryTextFocusChangeListener { v, hasFocus ->
            //Preparar los datos
        }
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                //filtrar datos finales
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                //filtrar datos parciales
                adaptador?.filtrar(newText!!)
                adaptadorGrid?.filtrar(newText!!)
                return true
            }

        })

        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when(item?.itemId) {
            R.id.btnAgregarContacto -> {
                val intent = Intent(this,NuevoContacto::class.java)
                startActivity(intent)
                return true
            }
            else -> {
                return super.onOptionsItemSelected(item)
            }
        }
    }

    override fun onResume() {
        super.onResume()
        adaptador?.notifyDataSetChanged()
        adaptadorGrid?.notifyDataSetChanged()
    }
}
