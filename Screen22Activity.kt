package com.example.myapplication

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.Spinner
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.storage.FirebaseStorage

class Screen22Activity : AppCompatActivity() {

    private val auth = FirebaseAuth.getInstance()
    private val userId = auth.currentUser?.uid ?: ""
    private var profilePictureUri: Uri? = null


    private lateinit var countrySpinner: Spinner
    private lateinit var citySpinner: Spinner


    private val pickImageResultLauncher = registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
        profilePictureUri = uri
        val profilePicture = findViewById<ImageView>(R.id.image1)
        Glide.with(this).load(uri)
            .error(R.drawable.ic_profile) // Assuming you have an error drawable
            .into(profilePicture)
    }

//
//    private val pickImageResultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
//        if (result.resultCode == Activity.RESULT_OK) {
//            result.data?.data?.let { uri ->
//                uploadImageToFirebase(uri)
//            }
//        }
//    }
    private fun openGalleryForImage() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        pickImageResultLauncher.launch(intent.toString()) // Correctly using the launcher
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.screen_22)

        // Initialize your Spinners here
        countrySpinner = findViewById(R.id.countryMenu22)
        citySpinner = findViewById(R.id.cityMenu22)

        setupSpinners(countrySpinner,citySpinner)
        fetchUserData()

        val backButton: ImageButton = findViewById(R.id.back_btn22)
        backButton.setOnClickListener {
            finish()
        }


        val imageView: ImageView = findViewById(R.id.image1)
        imageView.setOnClickListener {
            // Launch the gallery intent
            pickImageResultLauncher.launch("image/*") // Ensure this matches the MIME type for images
        }



        val updateProfileButton: Button = findViewById(R.id.update_profile_btn)
        updateProfileButton.setOnClickListener {
            updateProfile()
        }
    }

    private fun setupSpinners(countrySpinner: Spinner, citySpinner: Spinner) {
        // Example countries and cities - adjust as necessary
        val countries = arrayOf("Select Country", "Country 1", "Country 2")
        val cities = arrayOf("Select City", "City 1", "City 2")

        countrySpinner.adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, countries)
        citySpinner.adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, cities)
    }

    private fun fetchUserData() {
        val userRef = FirebaseDatabase.getInstance().getReference("users/$userId")
        userRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                snapshot.let {
                    val name = it.child("name").value as? String ?: ""
                    val email = it.child("email").value as? String ?: ""
                    val contact = it.child("contact").value as? String ?: ""
                    val country = it.child("country").value as? String ?: ""
                    val city = it.child("city").value as? String ?: ""

                    findViewById<EditText>(R.id.nameEditText).setText(name)
                    findViewById<EditText>(R.id.emailEditText).setText(email)
                    findViewById<EditText>(R.id.contact_number).setText(contact)

                    // Set country and city if using ArrayAdapter for Spinner
                    // Assume you have arrays or lists for countries and cities
                    val countryAdapter = countrySpinner.adapter as ArrayAdapter<String>
                    val cityAdapter = citySpinner.adapter as ArrayAdapter<String>
                    val countryPosition = countryAdapter.getPosition(country)
                    val cityPosition = cityAdapter.getPosition(city)

                    if (countryPosition != -1) countrySpinner.setSelection(countryPosition)
                    if (cityPosition != -1) citySpinner.setSelection(cityPosition)

                    // Load the profile image
                    val profileImageUrl = it.child("profileImageUrl").value as? String
                    if (profileImageUrl != null && profileImageUrl.isNotEmpty()) {
                        Glide.with(this@Screen22Activity).load(profileImageUrl).into(findViewById<ImageView>(R.id.image1))

                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@Screen22Activity, "Failed to fetch user data.", Toast.LENGTH_SHORT).show()
            }
        })
    }


    companion object {
        private const val IMAGE_PICK_CODE = 1000 // Request code for picking image
    }

    private fun uploadImageToFirebase(uri: Uri) {
        val userId = FirebaseAuth.getInstance().currentUser?.uid ?: return
        val storageRef = FirebaseStorage.getInstance().reference.child("profile_images/$userId.jpg")
        storageRef.putFile(uri).addOnSuccessListener { taskSnapshot ->
            taskSnapshot.storage.downloadUrl.addOnSuccessListener { downloadUri ->
                updateProfileImageUrl(downloadUri.toString())
            }
        }.addOnFailureListener {
            Toast.makeText(this, "Failed to upload image: ${it.message}", Toast.LENGTH_SHORT).show()
        }
    }


    private fun updateProfileImageUrl(imageUrl: String) {
        FirebaseDatabase.getInstance().getReference("users/$userId").child("profileImageUrl").setValue(imageUrl).addOnCompleteListener {
            if (it.isSuccessful) {
                Toast.makeText(this, "Profile image updated successfully", Toast.LENGTH_SHORT).show()
                Glide.with(this)
                    .load(imageUrl)
                    .placeholder(R.drawable.ic_cross) // Add a placeholder
                    .error(R.drawable.ic_profile) // Add an error drawable
                    .skipMemoryCache(true) // Skip memory cache
                    .diskCacheStrategy(DiskCacheStrategy.NONE) // Skip disk cache
                    .into(findViewById<ImageView>(R.id.image1))
            } else {
                Toast.makeText(this, "Failed to update profile image in database", Toast.LENGTH_SHORT).show()
            }
        }
    }


    private fun updateProfile() {
        val nameEditText: EditText = findViewById(R.id.nameEditText)
        val emailEditText: EditText = findViewById(R.id.emailEditText)
        val contactEditText: EditText = findViewById(R.id.contact_number)
        val country = countrySpinner.selectedItem.toString()
        val city = citySpinner.selectedItem.toString()

        val name = nameEditText.text.toString().trim()
        val email = emailEditText.text.toString().trim()
        val contact = contactEditText.text.toString().trim()

        // Update the profile information in the database
        updateProfileInDatabase(name, email, contact, country, city)
    }

    private fun updateProfileInDatabase(name: String, email: String, contact: String, country: String, city: String) {
        val userMap = mapOf(
            "name" to name,
            "email" to email,
            "contact" to contact,
            "country" to country,
            "city" to city
        )

        FirebaseDatabase.getInstance().reference
            .child("users")
            .child(userId)
            .updateChildren(userMap)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Toast.makeText(this, "Profile updated successfully", Toast.LENGTH_SHORT).show()
                    finish() // Finish the activity or navigate to another activity as needed
                } else {
                    Toast.makeText(this, "Failed to update profile in database", Toast.LENGTH_SHORT).show()
                }
            }
    }


}
