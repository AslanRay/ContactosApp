package com.aslan.contactosapp

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.view.Menu
import android.view.MenuItem
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_nuevo_contacto.*

class NuevoContacto : AppCompatActivity() {

    var foto:ImageView? = null

    //Indice del contacto que se va editar
    var index = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_nuevo_contacto)

        setSupportActionBar(toolbar)

        val actionBar = supportActionBar
        actionBar?.setDisplayHomeAsUpEnabled(true)

        foto = findViewById<ImageView>(R.id.ivFoto)
        foto?.setOnClickListener {
            seleccionarFoto()
        }

        //Acciones que se haran cuando se va editar un cliente en lugar de crear uno nuevo
        if(intent.hasExtra("ID")){
            index = intent.getStringExtra("ID").toInt()
            rellenarDatos(index)
        }
    }

    private fun rellenarDatos(index: Int) {
        val contacto = MainActivity.obtenerContacto(index)

        etNombre.setText(contacto.nombre,TextView.BufferType.EDITABLE)
        etApellido.setText(contacto.apellido,TextView.BufferType.EDITABLE)
        etOcupacion.setText(contacto.ocupacion,TextView.BufferType.EDITABLE)
        etEdad.setText(contacto.edad.toString(),TextView.BufferType.EDITABLE)
        etPeso.setText(contacto.peso.toString(),TextView.BufferType.EDITABLE)
        etTelefono.setText(contacto.nombre,TextView.BufferType.EDITABLE)
        etEmail.setText(contacto.email,TextView.BufferType.EDITABLE)
        etDireccion.setText(contacto.direccion,TextView.BufferType.EDITABLE)
        ivFoto.setImageResource(contacto.foto)

        var posicion = -1
        for(foto in fotos) {
            if(contacto.foto ==  foto) {
                fotoIndex = posicion
            }
            posicion++
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_guardar_contacto,menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when(item?.itemId) {
            android.R.id.home -> {
                finish()
                return true
            }
            R.id.btnCrearNuevoContacto -> {
                //Validar campos
                var campos = ArrayList<String>()
                campos.add(etNombre.text.toString())
                campos.add(etApellido.text.toString())
                campos.add(etOcupacion.text.toString())
                campos.add(etEdad.text.toString())
                campos.add(etPeso.text.toString())
                campos.add(etTelefono.text.toString())
                campos.add(etEmail.text.toString())

                var flag = 0
                for (campo in campos) {
                    if(campo.isNullOrEmpty()){
                        flag++
                    }
                }
               if(flag > 0) {
                   Toast.makeText(this,"No puede haber campos vacios",Toast.LENGTH_SHORT).show()
               } else {
                //ACtualiar contacto existente
                   if(index > -1) {
                       MainActivity.actualiazarContacto(index,Contacto(etNombre.text.toString(),etApellido.text.toString(),etOcupacion.text.toString(),etEdad.text.toString().toInt(),etPeso.text.toString().toFloat(),etDireccion.text.toString(),etTelefono.text.toString(),etEmail.text.toString(),obtenerFoto(fotoIndex)))
                   } else {
                       //Crear nuevo elemento de tipo contacto
                       MainActivity.agregarContacto(Contacto(etNombre.text.toString(),etApellido.text.toString(),etOcupacion.text.toString(),etEdad.text.toString().toInt(),etPeso.text.toString().toFloat(),etDireccion.text.toString(),etTelefono.text.toString(),etEmail.text.toString(),obtenerFoto(fotoIndex)))
                   }
                finish()
               }
                return true
            }
            else -> {
                return super.onOptionsItemSelected(item)
            }
        }
    }

    var fotoIndex = 0
    val fotos = arrayOf(R.drawable.foto_01,R.drawable.foto_02,R.drawable.foto_03,R.drawable.foto_04,R.drawable.foto_05,R.drawable.foto_06)
    fun seleccionarFoto() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Selecciona imagen de perfil")

        val adaptadorDialogo = ArrayAdapter<String>(this,android.R.layout.simple_selectable_list_item)
        adaptadorDialogo.add("Foto 01")
        adaptadorDialogo.add("Foto 02")
        adaptadorDialogo.add("Foto 03")
        adaptadorDialogo.add("Foto 04")
        adaptadorDialogo.add("Foto 05")
        adaptadorDialogo.add("Foto 06")

        builder.setAdapter(adaptadorDialogo) {
            dialog, which ->
            fotoIndex = which
            foto?.setImageResource(obtenerFoto(fotoIndex))
        }

        builder.setNegativeButton("Cancelar") {
            dialog, which ->
            dialog.dismiss()
        }

        builder.show()
    }

    fun obtenerFoto(index:Int):Int {
        return fotos.get(index)
    }

}
