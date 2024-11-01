package pe.edu.idat.appcamara

import android.content.pm.PackageManager
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import pe.edu.idat.appcamara.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.btncamara.setOnClickListener(this)
        binding.btncompartir.setOnClickListener(this)
    }
    override fun onClick(v: View) {
        when(v.id){
            R.id.btncamara -> tomarFoto()
            R.id.btncompartir -> compartirFoto()
        }
    }
    private fun compartirFoto() {

    }
    private fun tomarFoto() {
        if(validarPermiso()){
            Toast.makeText(applicationContext,
                "Puedo tomar foto", Toast.LENGTH_LONG).show()
        }else{
            solicitarPermiso()
        }
    }
    private fun intentCamara(){

    }

    private fun validarPermiso():Boolean{
        val result = ContextCompat.checkSelfPermission(
            applicationContext,
            android.Manifest.permission.WRITE_EXTERNAL_STORAGE
        )
        return result == PackageManager.PERMISSION_GRANTED
    }
    private fun solicitarPermiso(){
        ActivityCompat.requestPermissions(
            this, arrayOf(
                android.Manifest.permission.WRITE_EXTERNAL_STORAGE),
            1988)
    }
}