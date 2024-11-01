package pe.edu.idat.appcamara

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import pe.edu.idat.appcamara.databinding.ActivityMainBinding
import java.io.File
import java.text.SimpleDateFormat
import java.util.Date

class MainActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var binding: ActivityMainBinding
    private var rutaFotoActual = ""

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
            ejecutarCamara()
        }else{
            solicitarPermiso()
        }
    }
    private fun ejecutarCamara(){
        val tomarFotoCamara = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        if(tomarFotoCamara.resolveActivity(this.packageManager) != null){
            val fotoArchivo = crearArchivoTemporal()
            val fotoUri = obtenerContentUri(fotoArchivo)
            tomarFotoCamara.putExtra(MediaStore.EXTRA_OUTPUT, fotoUri)
            getResult.launch(tomarFotoCamara)
        }
    }
    private val getResult = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()){
        if(it.resultCode == Activity.RESULT_OK){
            mostrarFoto()
        }
    }

    private fun mostrarFoto() {
        val targetW: Int = binding.ivfoto.width
        val targetH: Int = binding.ivfoto.height
        val bmOptions = BitmapFactory.Options()

    }

    private fun crearArchivoTemporal(): File{
        val nombreImagen = "JPG_"+SimpleDateFormat("yyyyMMdd_HHmmss")
            .format(Date())
        val directorioImagenes: File = this.getExternalFilesDir(
            Environment.DIRECTORY_PICTURES)!!
        val archivoTempral : File = File.createTempFile(
            nombreImagen, ".jpg", directorioImagenes)
        rutaFotoActual = archivoTempral.absolutePath
        return archivoTempral
    }
    private fun obtenerContentUri(archivo: File): Uri{
        return FileProvider.getUriForFile(
            applicationContext,
            "pe.edu.idat.appcamara.fileprovider",
            archivo)
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