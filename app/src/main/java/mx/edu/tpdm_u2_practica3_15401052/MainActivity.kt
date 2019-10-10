package mx.edu.tpdm_u2_practica3_15401052

import android.app.Activity
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.AsyncTask
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import java.io.OutputStreamWriter
import kotlin.random.Random


class MainActivity : AppCompatActivity() {

    var editN : EditText ?= null
    var editM : EditText ?= null
    var ejecutar : Button ?= null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        editN = findViewById(R.id.editN)
        editM = findViewById(R.id.editM)
        ejecutar = findViewById(R.id.btnEjecutar)

        ejecutar?.setOnClickListener {
            //Ejecutar la producción de números de N a M
            prodNumeros(editN?.text.toString(), editM?.text.toString(), applicationContext).execute()
            limpiarCampos()
            AlertDialog.Builder(this).setTitle("EXITO").setMessage("SE HA GENERADO UN ARCHIVO TXT DE NÚMEROS PRIMOS")
                .setPositiveButton("Aceptar"){dialog, which -> }.show()
        }
    }
        class prodNumeros(n: String, m: String, c: Context): AsyncTask<Void, Void, List<Int>>(){
            var n = n.toInt()
            var m = m.toInt()
            var x = c
            override fun doInBackground(vararg p0: Void?): List<Int> {
                var lista = List(2000){Random.nextInt(n, m)}
                return lista
        }

            override fun onPostExecute(result: List<Int>?) {
                super.onPostExecute(result)
                var acum = 0
                var dig = ""
                var cadena = ""

                for(i in 0..1999){
                    acum = 0
                    dig = result?.get(i).toString()
                    (1..dig.toInt()).forEach{
                        if(dig.toInt() % it == 0){ acum++ }
                    }
                    if(acum <=2 && dig.toInt()>1){
                        cadena+= dig+", "
                    }
                }

                var txt = OutputStreamWriter(x.openFileOutput("primos.txt", Activity.MODE_PRIVATE))
                txt.write(cadena)
                txt.flush()
                txt.close()
            }
    }

    fun limpiarCampos(){
        editN?.setText("")
        editM?.setText("")
    }
}
