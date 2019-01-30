package com.aslan.contactosapp

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import kotlinx.android.synthetic.main.activity_detalle.*


class Detalle : AppCompatActivity() {

    //Indice del contacto
    var index = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detalle)
        setSupportActionBar(toolbar)

        val actionBar = supportActionBar
        actionBar?.setDisplayHomeAsUpEnabled(true)

        index = intent.getStringExtra("ID").toInt()
        mapearDatos()

    }

    private fun mapearDatos() {
        val contacto = MainActivity.obtenerContacto(index)
        etNombre.text = contacto.nombre + " " + contacto.apellido
        etOcupacion.text = contacto.ocupacion
        etEdad.text = contacto.edad.toString() + " años"
        tvPeso.text = contacto.peso.toString() + " kg"
        etTelefono.text = contacto.telefono
        tvEmail.text = contacto.email
        tvDireccion.text = contacto.direccion
        ivFoto.setImageResource(contacto.foto)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_detalles_contacto,menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when(item?.itemId) {
            android.R.id.home -> {
                finish()
                return true
            }
            R.id.btnEditarContacto -> {
                //Abrimos la actividad nuevo contacto pero en lugar de crear
                //la reutilizaremos para editar un contacto existente por medio de su ID
                val intent = Intent(this,NuevoContacto::class.java)
                intent.putExtra("ID",index.toString())
                startActivity(intent)
                return true
            }
            R.id.btnEliminarContacto -> {
                MainActivity.eliminarContacto(index)
                finish()
                return true
            }
            else -> {
                return super.onOptionsItemSelected(item)
            }
        }
    }

    override fun onResume() {
        super.onResume()
        //Mostrar datos actualizados cuando se regresa a esta actividad
        mapearDatos()
    }
}
