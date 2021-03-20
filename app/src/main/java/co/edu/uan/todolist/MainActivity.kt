package co.edu.uan.todolist

import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import java.io.OutputStreamWriter
import java.io.PrintStream
import java.util.*

class MainActivity : AppCompatActivity() {

    var ListaArray = mutableListOf<String>()

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mostrar()
        val item = findViewById<EditText>(R.id.edt1)
        val agregar = findViewById<Button>(R.id.btn1)

        item.setOnClickListener{
            item.editableText.clear()
        }

        agregar.setOnClickListener{
            val adapter = ArrayAdapter<String>(applicationContext, android.R.layout.simple_list_item_1, ListaArray)
            lst1.adapter = adapter
            ListaArray.add(item.text.toString())
            adapter.notifyDataSetChanged()
            guardara()
        }

        lst1.setOnItemClickListener{ parent, view, position, id ->
            borrar(position)
        }
    }

    private fun borrar (position: Int) {

        ListaArray.removeAt(position)
        val adapter = ArrayAdapter<String>(applicationContext, android.R.layout.simple_list_item_1, ListaArray)
        lst1.adapter = adapter
        adapter.notifyDataSetChanged()
        guardarb(ListaArray)
    }

    fun guardara(){
        val archivo = OutputStreamWriter(openFileOutput(nombre, MODE_APPEND))
        archivo.write(edt1.text.toString())
        archivo.flush()
        archivo.close()
        Toast.makeText(this, "Tareas guardadas en: $filesDir", Toast.LENGTH_SHORT).show()
    }

    private fun guardarb(ListaArray: MutableList<String>){
        val output = PrintStream(openFileOutput(nombre, MODE_PRIVATE))
        ListaArray.forEach { output.println(it) }
        output.flush()
        output.close()
    }

    private fun mostrar(): String {
        var cadena = ""
        try {
            val leer = Scanner(openFileInput(nombre))
            ListaArray = mutableListOf(leer.nextLine())
            val list = findViewById<ListView>(R.id.lst1)

            while (leer.hasNextLine()){
                ListaArray.add(leer.nextLine())
            }
            val adapter = ArrayAdapter<String>(applicationContext, android.R.layout.simple_list_item_1, ListaArray)
            list.adapter = adapter

            leer.close()
        }catch (e: Exception){

        }

        return cadena
    }

    companion object {
        const val nombre = "pruebas.txt"
    }
}