package com.agussetiawan.sentinel.ui.theme

import android.annotation.SuppressLint
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.lifecycleScope
import com.agussetiawan.sentinel.R
import com.agussetiawan.sentinel.databinding.ActivityAddPostBinding
import com.agussetiawan.sentinel.getImageUri
import com.agussetiawan.sentinel.uriToFile
import com.google.gson.Gson
import com.agussetiawan.sentinel.data.api.ApiConfig
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore
import com.playdeadrespawn.trashtech.data.response.UploadResponse
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import retrofit2.HttpException

class AddPost : AppCompatActivity() {
    private lateinit var binding: ActivityAddPostBinding
    private var currentImageUri: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_add_post)
        setSupportActionBar(binding.topAppbar)
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowHomeEnabled(true)
        }

        binding.btnCamera.setOnClickListener { startCamera() }
        binding.btnGalery.setOnClickListener { startGallery() }
        binding.submit.setOnClickListener { uploadImage() }

    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return super.onSupportNavigateUp()
    }

    private fun startGallery() {
        launcherGallery.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
    }

    private val launcherGallery = registerForActivityResult(
        ActivityResultContracts.PickVisualMedia()
    ) { uri: Uri? ->
        if (uri != null) {
            currentImageUri = uri
            showImage()
        } else {
            Log.d("Photo Picker", "No media selected")
        }
    }

    private fun startCamera() {
        currentImageUri = getImageUri(this)
        launcherIntentCamera.launch(currentImageUri)
    }

    private val launcherIntentCamera = registerForActivityResult(
        ActivityResultContracts.TakePicture()
    ) { isSuccess ->
        if (isSuccess) {
            showImage()
        }
    }

    private fun showImage() {
        currentImageUri?.let {
            Log.d("Image URI", "showImage: $it")
            binding.imgPrev.setImageURI(it)
        }
    }

    private fun uploadImage() {
        currentImageUri?.let { uri ->
            val imageFile = uriToFile(uri, this)
            val requestImageFile = imageFile.asRequestBody("image/jpeg".toMediaType())
            val multipartBody = MultipartBody.Part.createFormData(
                "image",
                imageFile.name,
                requestImageFile
            )

            lifecycleScope.launch {
                try {
                    val apiService = ApiConfig.getApiService()
                    val successResponse = apiService.uploadImage(multipartBody)
                    val db = Firebase.firestore
                    val auth = Firebase.auth.currentUser

                fun FreshApple()
                {
                    val deskripsi = "Apel segar, kaya vitamin C, A, K, E, memberikan kesegaran dan manfaat gizi yang optimal."
                    val masa = "Apel yang belum dikupas dapat disimpan di suhu kamar selama beberapa hari. Apel yang sudah dikupas harus disimpan di lemari es dan dapat bertahan selama beberapa minggu."
                    val saran = "Untuk menjaga apel tetap segar, simpanlah di tempat yang sejuk dan kering. Jika disimpan di suhu kamar, letakkan apel di dalam keranjang atau wadah agar tidak tertekan. Jika disimpan di lemari es, letakkan apel di bagian bawah lemari es agar tidak terlalu dingin."
                    val data = hashMapOf(
                        "image" to successResponse.uploadedUrl,
                        "email" to auth?.email,
                        "name" to auth?.displayName,
                        "prediction" to successResponse.prediction,
                        "deskripsi" to deskripsi,
                        "masa" to masa,
                        "saran" to saran


                    )
                    binding.textPrediction.text=" Prediction : ${successResponse.prediction}"
                    binding.textDes.text="Deskripsi :\n$deskripsi"
                    binding.textMasa.text="Kadaluarsa :\n$masa"
                    binding.textSaran.text="Saran : \n$saran"

                    db.collection("post")
                        .add(data)
                        .addOnSuccessListener {

                        }
                }


                fun RottenApple(){
                    val deskripsi = "Gejala keracunan makanan akibat apel busuk dapat berupa mual, muntah, diare, sakit perut, dan demam. Dalam kasus yang parah, keracunan makanan dapat menyebabkan komplikasi serius, seperti dehidrasi, gagal ginjal, dan bahkan kematian."
                    val masa = "Sebaiknya jangan di konsumsi"
                    val saran = "\tTekstur: Apel busuk akan terasa lunak dan berair saat ditekan.\n" +
                            "\tWarna: Apel busuk akan berwarna lebih gelap dari biasanya, biasanya berwarna cokelat atau hitam.\n"
                    val data = hashMapOf(
                        "email" to auth?.email,
                        "name" to auth?.displayName,
                        "prediction" to successResponse.prediction,
                        "deskripsi" to deskripsi,
                        "masa" to masa,
                        "saran" to saran

                    )
                    binding.textPrediction.text=" Prediction : ${successResponse.prediction}"
                    binding.textDes.text="Deskripsi :\n$deskripsi"
                    binding.textMasa.text=masa
                    binding.textSaran.text=saran

                    db.collection("post")
                        .add(data)
                        .addOnSuccessListener {

                        }
                }

                fun FreshBanana(){
                    val deskripsi = "Pisang segar, penuh energi, mengandung vitamin B6, C, dan potassium untuk kesehatan dan kesegaran optimal."
                    val masa = "Pisang yang belum matang dapat disimpan di suhu kamar selama beberapa hari. Pisang yang sudah matang dapat disimpan di suhu kamar selama beberapa jam, atau di lemari es selama beberapa hari."
                    val saran = "Untuk menjaga pisang tetap segar, simpanlah di tempat yang sejuk dan kering. Jika disimpan di suhu kamar, letakkan pisang di dalam keranjang atau wadah agar tidak tertekan. Jika disimpan di lemari es, letakkan pisang di bagian bawah lemari es agar tidak terlalu dingin."
                    val data = hashMapOf(
                        "email" to auth?.email,
                        "name" to auth?.displayName,
                        "prediction" to successResponse.prediction,
                        "deskripsi" to deskripsi,
                        "masa" to masa,
                        "saran" to saran

                    )
                    binding.textPrediction.text=" Prediction : ${successResponse.prediction}"
                    binding.textDes.text="Deskripsi :\n$deskripsi"
                    binding.textMasa.text="Kadaluarsa :\n$masa"
                    binding.textSaran.text="Saran : \n$saran"

                    db.collection("post")
                        .add(data)
                        .addOnSuccessListener {

                        }
                }

                fun RottenBanana(){
                    val deskripsi = ": Gejala keracunan makanan akibat pisang busuk dapat berupa mual, muntah, diare, sakit perut, dan demam. Dalam kasus yang parah, keracunan makanan dapat menyebabkan komplikasi serius, seperti dehidrasi, gagal ginjal, dan bahkan kematian."
                    val masa = "Sebaiknya jangan di konsumsi"
                    val saran = "\tTekstur: Pisang busuk akan terasa lunak dan berair saat ditekan.\n" +
                            "\tWarna: Pisang busuk akan berwarna lebih gelap dari biasanya, biasanya berwarna cokelat atau hitam.\n" +
                            "\tBintik-bintik: Pisang busuk mungkin memiliki bintik-bintik atau bercak hitam atau coklat.\n" +
                            "\tBau: Pisang busuk akan mengeluarkan bau yang tidak sedap.\n"
                    val data = hashMapOf(
                        "email" to auth?.email,
                        "name" to auth?.displayName,
                        "prediction" to successResponse.prediction,
                        "deskripsi" to deskripsi,
                        "masa" to masa,
                        "saran" to saran

                    )
                    binding.textPrediction.text=" Prediction : ${successResponse.prediction}"
                    binding.textDes.text="Deskripsi :\n$deskripsi"
                    binding.textMasa.text=masa
                    binding.textSaran.text=saran

                    db.collection("post")
                        .add(data)
                        .addOnSuccessListener {

                        }
                }

                fun FreshBittergourd(){
                    val deskripsi = "Pare segar, kaya nutrisi, menyediakan vitamin penting, memberikan kesegaran dan manfaat kesehatan yang optimal."
                    val masa = ": Pare yang belum matang dapat disimpan di suhu kamar selama beberapa hari. Bittergourd yang sudah matang dapat disimpan di lemari es selama beberapa minggu."
                    val saran = ": Untuk menjaga bittergourd tetap segar, simpanlah di tempat yang sejuk dan kering. Jika disimpan di suhu kamar, letakkan bittergourd di dalam keranjang atau wadah agar tidak tertekan. Jika disimpan di lemari es, letakkan bittergourd di bagian bawah lemari es agar tidak terlalu dingin."
                    val data = hashMapOf(
                        "email" to auth?.email,
                        "name" to auth?.displayName,
                        "prediction" to successResponse.prediction,
                        "deskripsi" to deskripsi,
                        "masa" to masa,
                        "saran" to saran

                    )
                    binding.textPrediction.text=" Prediction : ${successResponse.prediction}"
                    binding.textDes.text="Deskripsi :\n$deskripsi"
                    binding.textMasa.text="Kadaluarsa :\n$masa"
                    binding.textSaran.text="Saran : \n$saran"

                    db.collection("post")
                        .add(data)
                        .addOnSuccessListener {

                        }
                }

                fun RottenBittergourd(){
                    val deskripsi = "Gejala keracunan makanan akibat pare busuk dapat berupa mual, muntah, diare, sakit perut, dan demam. Dalam kasus yang parah, keracunan makanan dapat menyebabkan komplikasi serius, seperti dehidrasi, gagal ginjal, dan bahkan kematian."
                    val masa = "Sebaiknya jangan di konsumsi"
                    val saran = "\tTekstur: Pare busuk akan terasa lunak dan berair saat ditekan.\n" +
                            "\tWarna: Pare busuk akan berwarna lebih gelap dari biasanya, biasanya berwarna cokelat atau hitam.\n" +
                            "\tBintik-bintik: Pare busuk mungkin memiliki bintik-bintik atau bercak hitam atau coklat.\n" +
                            "\tBau: Pare busuk akan mengeluarkan bau yang tidak sedap.\n"
                    val data = hashMapOf(
                        "email" to auth?.email,
                        "name" to auth?.displayName,
                        "prediction" to successResponse.prediction,
                        "deskripsi" to deskripsi,
                        "masa" to masa,
                        "saran" to saran

                    )
                    binding.textPrediction.text=" Prediction : ${successResponse.prediction}"
                    binding.textDes.text="Deskripsi :\n$deskripsi"
                    binding.textMasa.text=masa
                    binding.textSaran.text=saran

                    db.collection("post")
                        .add(data)
                        .addOnSuccessListener {

                        }
                }

                fun FreshCapsicum(){
                    val deskripsi = "Cabai penuh vitamin A, C, K, memberikan kelezatan dan kesehatan dalam setiap gigitan."
                    val masa = "Cabai yang belum matang dapat disimpan di suhu kamar selama beberapa hari. Cabai yang sudah matang dapat disimpan di lemari es selama beberapa minggu."
                    val saran = ": Untuk menjaga cabai tetap segar, simpanlah di tempat yang sejuk dan kering. Jika disimpan di suhu kamar, letakkan cabai di dalam keranjang atau wadah agar tidak tertekan. Jika disimpan di lemari es, letakkan cabai di bagian bawah lemari es agar tidak terlalu dingin."
                    val data = hashMapOf(
                        "email" to auth?.email,
                        "name" to auth?.displayName,
                        "prediction" to successResponse.prediction,
                        "deskripsi" to deskripsi,
                        "masa" to masa,
                        "saran" to saran

                    )
                    binding.textPrediction.text=" Prediction : ${successResponse.prediction}"
                    binding.textDes.text="Deskripsi :\n$deskripsi"
                    binding.textMasa.text="Kadaluarsa :\n$masa"
                    binding.textSaran.text="Saran : \n$saran"

                    db.collection("post")
                        .add(data)
                        .addOnSuccessListener {

                        }

                }

                fun RottenCapsicum(){
                    val deskripsi = "Gejala keracunan makanan akibat cabai busuk dapat berupa mual, muntah, diare, sakit perut, dan demam. Dalam kasus yang parah, keracunan makanan dapat menyebabkan komplikasi serius, seperti dehidrasi, gagal ginjal, dan bahkan kematian."
                    val masa = "Sebaiknya jangan di konsumsi"
                    val saran = "\tTekstur: Cabai busuk akan terasa lunak dan berair saat ditekan.\n" +
                            "\tWarna: Cabai busuk akan berwarna lebih gelap dari biasanya, biasanya berwarna cokelat atau hitam.\n" +
                            "\tBintik-bintik: Cabai busuk mungkin memiliki bintik-bintik atau bercak hitam atau coklat.\n" +
                            "\tBau: Cabai busuk akan mengeluarkan bau yang tidak sedap.\n"
                    val data = hashMapOf(
                        "email" to auth?.email,
                        "name" to auth?.displayName,
                        "prediction" to successResponse.prediction,
                        "deskripsi" to deskripsi,
                        "masa" to masa,
                        "saran" to saran

                    )
                    binding.textPrediction.text=" Prediction : ${successResponse.prediction}"
                    binding.textDes.text="Deskripsi :\n$deskripsi"
                    binding.textMasa.text=masa
                    binding.textSaran.text=saran

                    db.collection("post")
                        .add(data)
                        .addOnSuccessListener {

                        }
                }

                fun FreshCucumber(){
                    val deskripsi = "Mentimun segar, penuh vitamin C dan K, memberikan kesegaran dan nutrisi optimal untuk kesehatan kulit dan tubuh."
                    val masa = "Mentimun yang belum dikupas dapat disimpan di suhu kamar selama beberapa hari. Mentimun yang sudah dikupas harus disimpan di lemari es dan dapat bertahan selama beberapa minggu."
                    val saran = "Untuk menjaga mentimun tetap segar, simpanlah di tempat yang sejuk dan kering. Jika disimpan di suhu kamar, letakkan mentimun di dalam keranjang atau wadah agar tidak tertekan. Jika disimpan di lemari es, letakkan mentimun di bagian bawah lemari es agar tidak terlalu dingin."
                    val data = hashMapOf(
                        "email" to auth?.email,
                        "name" to auth?.displayName,
                        "prediction" to successResponse.prediction,
                        "deskripsi" to deskripsi,
                        "masa" to masa,
                        "saran" to saran

                    )
                    binding.textPrediction.text=" Prediction : ${successResponse.prediction}"
                    binding.textDes.text="Deskripsi :\n$deskripsi"
                    binding.textMasa.text="Kadaluarsa :\n$masa"
                    binding.textSaran.text="Saran : \n$saran"

                    db.collection("post")
                        .add(data)
                        .addOnSuccessListener {

                        }
                }

                fun RottenCucumber(){
                    val deskripsi = " Gejala keracunan makanan akibat mentimun busuk dapat berupa mual, muntah, diare, sakit perut, dan demam. Dalam kasus yang parah, keracunan makanan dapat menyebabkan komplikasi serius, seperti dehidrasi, gagal ginjal, dan bahkan kematian."
                    val masa = "Sebaiknya jangan di konsumsi"
                    val saran = "\tTekstur: Mentimun busuk akan terasa lunak dan berair saat ditekan.\n" +
                            "\tWarna: Mentimun busuk akan berwarna lebih gelap dari biasanya, biasanya berwarna kuning atau coklat.\n" +
                            "\tBintik-bintik: Mentimun busuk mungkin memiliki bintik-bintik atau bercak hitam atau coklat.\n" +
                            "\tBau: Mentimun busuk akan mengeluarkan bau yang tidak sedap.\n"
                    val data = hashMapOf(
                        "email" to auth?.email,
                        "name" to auth?.displayName,
                        "prediction" to successResponse.prediction,
                        "deskripsi" to deskripsi,
                        "masa" to masa,
                        "saran" to saran

                    )
                    binding.textPrediction.text=" Prediction : ${successResponse.prediction}"
                    binding.textDes.text= deskripsi
                    binding.textMasa.text=masa
                    binding.textSaran.text=saran

                    db.collection("post")
                        .add(data)
                        .addOnSuccessListener {

                        }

                }

                fun FreshMeat(){
                    val deskripsi = "Daging segar kaya protein dan vitamin B, mendukung pertumbuhan otot dan memberikan energi esensial untuk kesehatan tubuh secara menyeluruh."
                    val masa = "Daging mentah dapat disimpan di luar lemari es selama 1-2 hari, atau di freezer selama 3-4 bulan."
                    val saran = "Untuk menjaga daging tetap segar, simpanlah di tempat yang sejuk dan kering. Jika disimpan di lemari es, bungkus daging dengan rapat agar tidak kering. Jika disimpan di freezer, bungkus daging dengan rapat dan masukkan ke dalam wadah kedap udara"
                    val data = hashMapOf(
                        "email" to auth?.email,
                        "name" to auth?.displayName,
                        "prediction" to successResponse.prediction,
                        "deskripsi" to deskripsi,
                        "masa" to masa,
                        "saran" to saran

                    )
                    binding.textPrediction.text=" Prediction : ${successResponse.prediction}"
                    binding.textDes.text="Deskripsi :\n$deskripsi"
                    binding.textMasa.text="Kadaluarsa :\n$masa"
                    binding.textSaran.text="Saran : \n$saran"

                    db.collection("post")
                        .add(data)
                        .addOnSuccessListener {

                        }

                }

                fun RottenMeat(){
                    val deskripsi = "Gejala keracunan makanan akibat daging busuk dapat berupa mual, muntah, diare, sakit perut, dan demam. Dalam kasus yang parah, keracunan makanan dapat menyebabkan komplikasi serius, seperti dehidrasi, gagal ginjal, dan bahkan kematian."
                    val masa = "Sebaiknya jangan di konsumsi"
                    val saran = "\tWarna: Daging busuk akan berwarna lebih gelap dari biasanya, biasanya berwarna cokelat atau hitam.\n" +
                            "\tBau: Daging busuk akan mengeluarkan bau yang tidak sedap.\n" +
                            "\tTekstur: Daging busuk akan terasa lengket dan berlendir.\n" +
                            "\tBintik-bintik: Daging busuk mungkin memiliki bintik-bintik atau bercak hitam atau coklat.\n"
                    val data = hashMapOf(
                        "email" to auth?.email,
                        "name" to auth?.displayName,
                        "prediction" to successResponse.prediction,
                        "deskripsi" to deskripsi,
                        "masa" to masa,
                        "saran" to saran

                    )
                    binding.textPrediction.text=" Prediction : ${successResponse.prediction}"
                    binding.textDes.text= deskripsi
                    binding.textMasa.text=masa
                    binding.textSaran.text=saran

                    db.collection("post")
                        .add(data)
                        .addOnSuccessListener {

                        }

                }

                fun FreshOkra(){
                    val deskripsi = "Okra segar, sumber vitamin C dan K, memberikan kelezatan dan manfaat kesehatan yang tinggi untuk tubuh dan kulit."
                    val masa = "Okra yang belum matang dapat disimpan di suhu kamar selama beberapa hari. Okra yang sudah matang dapat disimpan di lemari es selama beberapa hari."
                    val saran = ": Untuk menjaga okra tetap segar, simpanlah di tempat yang sejuk dan kering. Jika disimpan di suhu kamar, letakkan okra di dalam keranjang atau wadah agar tidak tertekan. Jika disimpan di lemari es, letakkan okra di bagian bawah lemari es agar tidak terlalu dingin."
                    val data = hashMapOf(
                        "email" to auth?.email,
                        "name" to auth?.displayName,
                        "prediction" to successResponse.prediction,
                        "deskripsi" to deskripsi,
                        "masa" to masa,
                        "saran" to saran

                    )
                    binding.textPrediction.text=" Prediction : ${successResponse.prediction}"
                    binding.textDes.text="Deskripsi :\n$deskripsi"
                    binding.textMasa.text="Kadaluarsa :\n$masa"
                    binding.textSaran.text="Saran : \n$saran"

                    db.collection("post")
                        .add(data)
                        .addOnSuccessListener {

                        }

                }

                fun RottenOkra(){
                    val deskripsi = ": Selain kandungan nutrisi yang berkurang, okra busuk juga mengandung bakteri dan jamur yang dapat menyebabkan keracunan makanan. Gejala keracunan makanan akibat kentang busuk dapat berupa mual, muntah, diare, sakit perut, dan demam. Dalam kasus yang parah, keracunan makanan dapat menyebabkan komplikasi serius, seperti dehidrasi, gagal ginjal, dan bahkan kematian."
                    val masa = "Sebaiknya jangan di konsumsi"
                    val saran = "\tTekstur: Okra busuk akan terasa lunak dan berair saat ditekan.\n" +
                            "\tWarna: Okra busuk akan berwarna lebih gelap dari biasanya, biasanya berwarna cokelat atau hitam.\n" +
                            "\tBintik-bintik: Okra busuk mungkin memiliki bintik-bintik atau bercak hitam atau coklat.\n" +
                            "\tBau: Okra busuk akan mengeluarkan bau yang tidak sedap.\n"
                    val data = hashMapOf(
                        "email" to auth?.email,
                        "name" to auth?.displayName,
                        "prediction" to successResponse.prediction,
                        "deskripsi" to deskripsi,
                        "masa" to masa,
                        "saran" to saran

                    )
                    binding.textPrediction.text=" Prediction : ${successResponse.prediction}"
                    binding.textDes.text= deskripsi
                    binding.textMasa.text=masa
                    binding.textSaran.text=saran

                    db.collection("post")
                        .add(data)
                        .addOnSuccessListener {

                        }

                }

                fun FreshOrange(){
                    val deskripsi = "Jeruk segar, kaya vitamin C dan antioksidan, menyegarkan dan mendukung sistem kekebalan untuk kesehatan optimal dan keceriaan"
                    val masa = "Jeruk yang belum matang dapat disimpan di suhu kamar selama beberapa hari. Jeruk yang sudah matang dapat disimpan di lemari es selama beberapa minggu."
                    val saran = "Untuk menjaga jeruk tetap segar, simpanlah di tempat yang sejuk dan kering. Jika disimpan di suhu kamar, letakkan jeruk di dalam keranjang atau wadah agar tidak tertekan. Jika disimpan di lemari es, letakkan jeruk di bagian bawah lemari es agar tidak terlalu dingin."
                    val data = hashMapOf(
                        "email" to auth?.email,
                        "name" to auth?.displayName,
                        "prediction" to successResponse.prediction,
                        "deskripsi" to deskripsi,
                        "masa" to masa,
                        "saran" to saran

                    )
                    binding.textPrediction.text=" Prediction : ${successResponse.prediction}"
                    binding.textDes.text="Deskripsi :\n$deskripsi"
                    binding.textMasa.text="Kadaluarsa :\n$masa"
                    binding.textSaran.text="Saran : \n$saran"

                    db.collection("post")
                        .add(data)
                        .addOnSuccessListener {

                        }

                }

                fun RottenOrange(){
                    val deskripsi = ": Selain kandungan nutrisi yang berkurang, orange busuk juga mengandung bakteri dan jamur yang dapat menyebabkan keracunan makanan. Gejala keracunan makanan akibat kentang busuk dapat berupa mual, muntah, diare, sakit perut, dan demam. Dalam kasus yang parah, keracunan makanan dapat menyebabkan komplikasi serius, seperti dehidrasi, gagal ginjal, dan bahkan kematian."
                    val masa = "Sebaiknya jangan di konsumsi"
                    val saran = "\tTekstur: Jeruk busuk akan terasa lunak dan berair saat ditekan.\n" +
                            "\tWarna: Jeruk busuk akan berwarna lebih gelap dari biasanya, biasanya berwarna cokelat atau hitam.\n" +
                            "\tBintik-bintik: Jeruk busuk mungkin memiliki bintik-bintik atau bercak hitam atau coklat.\n" +
                            "\tBau: Jeruk busuk akan mengeluarkan bau yang tidak sedap.\n"
                    val data = hashMapOf(
                        "email" to auth?.email,
                        "name" to auth?.displayName,
                        "prediction" to successResponse.prediction,
                        "deskripsi" to deskripsi,
                        "masa" to masa,
                        "saran" to saran

                    )
                    binding.textPrediction.text=" Prediction : ${successResponse.prediction}"
                    binding.textDes.text= deskripsi
                    binding.textMasa.text=masa
                    binding.textSaran.text=saran

                    db.collection("post")
                        .add(data)
                        .addOnSuccessListener {

                        }


                }

                fun FreshPotato(){
                    val deskripsi = "Kentang segar, sumber vitamin B6 dan C, memberikan nutrisi esensial untuk kesehatan dan energi tubuh yang optimal."
                    val masa = " Kentang yang belum dikupas dapat disimpan di suhu kamar selama beberapa hari. Kentang yang sudah dikupas harus disimpan di lemari es dan dapat bertahan selama beberapa minggu."
                    val saran = "Untuk menjaga kentang tetap segar, simpanlah di tempat yang sejuk dan kering. Jika disimpan di suhu kamar, letakkan kentang di dalam keranjang atau wadah agar tidak tertekan. Jika disimpan di lemari es, letakkan kentang di bagian bawah lemari es agar tidak terlalu dingin."
                    val data = hashMapOf(
                        "email" to auth?.email,
                        "name" to auth?.displayName,
                        "prediction" to successResponse.prediction,
                        "deskripsi" to deskripsi,
                        "masa" to masa,
                        "saran" to saran

                    )
                    binding.textPrediction.text=" Prediction : ${successResponse.prediction}"
                    binding.textDes.text="Deskripsi :\n$deskripsi"
                    binding.textMasa.text="Kadaluarsa :\n$masa"
                    binding.textSaran.text="Saran : \n$saran"

                    db.collection("post")
                        .add(data)
                        .addOnSuccessListener {

                        }

                }

                fun RottenPotato(){
                    val deskripsi = "Selain kandungan nutrisi yang berkurang, kentang busuk juga mengandung bakteri dan jamur yang dapat menyebabkan keracunan makanan. Gejala keracunan makanan akibat kentang busuk dapat berupa mual, muntah, diare, sakit perut, dan demam. Dalam kasus yang parah, keracunan makanan dapat menyebabkan komplikasi serius, seperti dehidrasi, gagal ginjal, dan bahkan kematian."
                    val masa = "Sebaiknya jangan di konsumsi"
                    val saran = "\tKentang memiliki bintik-bintik atau bercak hitam atau coklat\n" +
                            "\tKentang terasa lunak atau berair\n" +
                            "\tKentang berbau busuk\n"
                    val data = hashMapOf(
                        "email" to auth?.email,
                        "name" to auth?.displayName,
                        "prediction" to successResponse.prediction,
                        "deskripsi" to deskripsi,
                        "masa" to masa,
                        "saran" to saran

                    )
                    binding.textPrediction.text=" Prediction : ${successResponse.prediction}"
                    binding.textDes.text= deskripsi
                    binding.textMasa.text=masa
                    binding.textSaran.text=saran

                    db.collection("post")
                        .add(data)
                        .addOnSuccessListener {

                        }

                }

                fun FreshTomato(){
                    val deskripsi = "Tomat segar, kaya vitamin C dan A, menyediakan cita rasa lezat serta manfaat nutrisi untuk kesehatan mata dan kulit."
                    val masa = "Tomat matang yang belum dipotong dapat disimpan di suhu kamar selama beberapa hari. Tomat matang yang sudah dipotong harus disimpan di lemari es dan dapat bertahan selama beberapa hari."
                    val saran = "Untuk menjaga tomat tetap segar, simpanlah di tempat yang sejuk dan kering. Jika disimpan di suhu kamar, letakkan tomat di dalam keranjang atau wadah agar tidak tertekan. Jika disimpan di lemari es, letakkan tomat di bagian bawah lemari es agar tidak terlalu dingin."
                    val data = hashMapOf(
                        "email" to auth?.email,
                        "name" to auth?.displayName,
                        "prediction" to successResponse.prediction,
                        "deskripsi" to deskripsi,
                        "masa" to masa,
                        "saran" to saran

                    )
                    binding.textPrediction.text=" Prediction : ${successResponse.prediction}"
                    binding.textDes.text="Deskripsi :\n$deskripsi"
                    binding.textMasa.text="Kadaluarsa :\n$masa"
                    binding.textSaran.text="Saran : \n$saran"

                    db.collection("post")
                        .add(data)
                        .addOnSuccessListener {

                        }

                }
                fun RottenTomato(){
                    val deskripsi = "Selain kandungan nutrisi yang berkurang, tomat busuk juga mengandung bakteri dan jamur yang dapat menyebabkan keracunan makanan. Gejala keracunan makanan akibat tomat busuk dapat berupa mual, muntah, diare, sakit perut, dan demam. Dalam kasus yang parah, keracunan makanan dapat menyebabkan komplikasi serius, seperti dehidrasi, gagal ginjal, dan bahkan kematian."
                    val masa = "Sebaiknya jangan di konsumsi"
                    val saran = "-\tTekstur: Tomat busuk akan terasa lunak dan berair saat ditekan.\n" +
                            "-\tWarna: Tomat busuk akan berwarna lebih gelap dari biasanya, biasanya berwarna cokelat atau hitam.\n"
                    val data = hashMapOf(
                        "email" to auth?.email,
                        "name" to auth?.displayName,
                        "prediction" to successResponse.prediction,
                        "deskripsi" to deskripsi,
                        "masa" to masa,
                        "saran" to saran

                    )
                    binding.textPrediction.text=" Prediction : ${successResponse.prediction}"
                    binding.textDes.text= deskripsi
                    binding.textMasa.text=masa
                    binding.textSaran.text=saran

                    db.collection("post")
                        .add(data)
                        .addOnSuccessListener {

                        }

                }













                    when (successResponse.prediction) {
                        "Fresh Apple" -> {
                            FreshApple()
                        }
                        "Rotten Apple" -> {
                            RottenApple()
                        }
                        "Fresh Banana" -> {
                            FreshBanana()

                        }
                        "Rotten Banana" -> {
                            RottenBanana()

                        }
                        "Fresh Bittergroud" -> {
                            FreshBittergourd()

                        }
                        "Rotten  Bittergroud" -> {
                            RottenBittergourd()

                        }
                        "Fresh Capsicum" -> {
                            FreshCapsicum()

                        }
                        "Rotten Capsicum" -> {
                            RottenCapsicum()

                        }
                        "Fresh Cucumber" -> {
                            FreshCucumber()

                        }
                        "Rotten Cucumber" -> {
                            RottenCucumber()

                        }
                        "Fresh Meat" -> {
                            FreshMeat()

                        }
                        "Rotten Meat" -> {
                            RottenMeat()

                        }
                        "Fresh Okra" -> {
                            FreshOkra()

                        }
                        "Rotten Okra" -> {
                            RottenOkra()

                        }
                        "Fresh Orange" -> {
                            FreshOrange()

                        }
                        "Rotten Oranges" -> {
                            RottenOrange()

                        }
                        "Fresh Potato" -> {
                            FreshPotato()

                        }
                        "Rotten Potato" -> {
                            RottenPotato()

                        }
                        "Fresh Tomato" -> {
                            FreshTomato()

                        }
                        "Rotten Tomato" -> {
                            RottenTomato()

                        }
                        else -> {
                            Toast.makeText(this@AddPost, "Gambar yang anda kirimkan mungkin belum ada di database kami \n segera akan kami tambahkan", Toast.LENGTH_SHORT).show()
                        }
                    }
                } catch (e: HttpException) {
                    val errorBody = e.response()?.errorBody()?.string()
                    val errorResponse = Gson().fromJson(errorBody, UploadResponse::class.java)
                    showToast("error")
                }
            }
        } ?: showToast("Kosong!")
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}