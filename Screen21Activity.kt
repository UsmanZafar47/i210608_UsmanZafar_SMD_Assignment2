package com.example.myapplication

import android.app.Activity.RESULT_OK
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import android.content.pm.PackageManager
import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.app.ActivityCompat.startActivityForResult
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.storage.FirebaseStorage



class Screen21Activity : AppCompatActivity() {

    private var imageUri: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.screen_21)

        val profileImageView = findViewById<ImageView>(R.id.profile_icon)
        val editButton2 = findViewById<ImageButton>(R.id.edit2_btn21) // cover btn

        displayUserName()
        displayUserCoverImage()

        findViewById<ImageButton>(R.id.edit2_btn21).setOnClickListener {
            // Request permissions or open gallery
            checkPermissionAndOpenGallery()
        }

        editButton2.setOnClickListener {
            if (ContextCompat.checkSelfPermission(
                    this,
                    android.Manifest.permission.READ_EXTERNAL_STORAGE
                ) == PackageManager.PERMISSION_GRANTED
            ) {
                openGallery()
            } else {
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE),
                    PERMISSION_CODE
                )
            }
        }



        //home btnnn
        val homeButton = findViewById<ImageView>(R.id.home_btn21)

        homeButton.setOnClickListener {
            val intent = Intent(this, Screen7Activity::class.java)
            startActivity(intent)
        }

        //Search btnnn
        val sButton = findViewById<ImageView>(R.id.search_btnn21)

        sButton.setOnClickListener {
            val intent = Intent(this, SearchActivity::class.java)
            startActivity(intent)
        }



        //Add btnnn
        val adButton = findViewById<TextView>(R.id.add_btn21)

        adButton.setOnClickListener {
            val intent = Intent(this, AddMentorActivity::class.java)
            startActivity(intent)
        }


        //chat btnnn
        val chatButton = findViewById<ImageView>(R.id.chat_btn21)

        chatButton.setOnClickListener {
            val intent = Intent(this, Chat13Activity::class.java)
            startActivity(intent)
        }

        val bookedButton = findViewById<Button>(R.id.Booked_btn)

        bookedButton.setOnClickListener {
            val intent = Intent(this, Screen23Activity::class.java)
            startActivity(intent)
        }


        val editButton = findViewById<ImageButton>(R.id.edit_btn21)

        editButton.setOnClickListener {
            val intent = Intent(this, Screen22Activity::class.java)
            startActivity(intent)
        }





    }

    private fun checkPermissionAndOpenGallery() {
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            openGallery()
        } else {
            ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE), PERMISSION_CODE)
        }
    }


    private fun displayUserName() {
        val user = FirebaseAuth.getInstance().currentUser
        if (user != null) {
            val userId = user.uid
            FirebaseDatabase.getInstance().getReference("users/$userId/name")
                .addListenerForSingleValueEvent(object : ValueEventListener {
                    override fun onDataChange(dataSnapshot: DataSnapshot) {
                        val name = dataSnapshot.value as? String
                        findViewById<TextView>(R.id.Name_view).text = name ?: "No Name"
                    }

                    override fun onCancelled(databaseError: DatabaseError) {
                        Log.w("Screen21Activity", "loadUserName:onCancelled", databaseError.toException())
                        Toast.makeText(baseContext, "Failed to load user name.", Toast.LENGTH_SHORT).show()
                    }
                })
        } else {
            findViewById<TextView>(R.id.Name_view).text = "No User"
        }
    }






    private fun openGallery() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, IMAGE_PICK_CODE)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == PERMISSION_CODE && grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            openGallery()
        } else {
            Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK && requestCode == IMAGE_PICK_CODE) {
            imageUri = data?.data
            findViewById<ImageView>(R.id.cover).setImageURI(imageUri)
            imageUri?.let { uploadImageToFirebase(it) }
        }
    }


    private fun uploadProfileImageToFirebase(imageUri: Uri) {
        val userId = FirebaseAuth.getInstance().currentUser?.uid ?: return
        val storageRef = FirebaseStorage.getInstance().reference.child("profile_images/$userId.jpg")
        storageRef.putFile(imageUri).addOnSuccessListener {
            it.storage.downloadUrl.addOnSuccessListener { uri ->
                val imageUrl = uri.toString()
                FirebaseDatabase.getInstance().getReference("users/$userId")
                    .child("profileIconUrl").setValue(imageUrl) // Use a clear, consistent key
                    .addOnSuccessListener {
                        Toast.makeText(this, "Profile image updated successfully", Toast.LENGTH_SHORT).show()
                        // Optionally, finish the activity or update UI
                    }
                    .addOnFailureListener { e ->
                        Toast.makeText(this, "Failed to save image URL in database: ${e.message}", Toast.LENGTH_SHORT).show()
                    }
            }
        }.addOnFailureListener { e ->
            Toast.makeText(this, "Failed to upload image: ${e.message}", Toast.LENGTH_SHORT).show()
        }
    }

    private fun displayUserProfileIcon() {
        val userId = FirebaseAuth.getInstance().currentUser?.uid ?: return
        FirebaseDatabase.getInstance().getReference("users/$userId/profileIconUrl") // Adjust the path to match the key used in Screen22Activity
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    val imageUrl = dataSnapshot.value as String?
                    if (imageUrl.isNullOrEmpty()) {
                        Log.e("Screen21Activity", "Profile icon URL is null or empty")
                        // Handle case where URL is not available, maybe set a default image
                    } else {
                        Glide.with(this@Screen21Activity)
                            .load(imageUrl)
                            .into(findViewById<ImageView>(R.id.profile_icon)) // Make sure this is your profile icon ImageView ID
                    }
                }

                override fun onCancelled(databaseError: DatabaseError) {
                    Log.e("Screen21Activity", "Failed to read profile icon URL", databaseError.toException())
                }
            })
    }




    private fun displayUserCoverImage() {
        val userId = FirebaseAuth.getInstance().currentUser?.uid ?: return
        FirebaseDatabase.getInstance().getReference("users/$userId/coverImageUrl") // Ensure this matches the key used in uploadImageToFirebase
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    val imageUrl = dataSnapshot.value as String?
                    if (imageUrl.isNullOrEmpty()) {
                        Log.e("Screen21Activity", "Image URL is null or empty")
                        // Handle case where URL is not available
                    } else {
                        Glide.with(this@Screen21Activity)
                            .load(imageUrl)
                            .into(findViewById(R.id.cover)) // Ensure this is your ImageView ID
                    }
                }

                override fun onCancelled(databaseError: DatabaseError) {
                    Log.e("Screen21Activity", "Failed to read image URL", databaseError.toException())
                }
            })
    }



    private fun uploadImageToFirebase(fileUri: Uri) {
        val userId = FirebaseAuth.getInstance().currentUser?.uid ?: return
        val storageReference = FirebaseStorage.getInstance().getReference("images/$userId/cover_picture")

        storageReference.putFile(fileUri).addOnSuccessListener { taskSnapshot ->
            taskSnapshot.storage.downloadUrl.addOnSuccessListener { uri ->
                val imageUrl = uri.toString()
                FirebaseDatabase.getInstance().getReference("users/$userId")
                    .child("coverImageUrl").setValue(imageUrl) // Make sure this key matches the one used in displayUserCoverImage
                    .addOnSuccessListener {
                        Toast.makeText(this, "Image uploaded successfully and saved to database", Toast.LENGTH_SHORT).show()
                        displayUserCoverImage() // Refresh the cover image display
                    }
                    .addOnFailureListener { e ->
                        Toast.makeText(this, "Failed to save image URL in database: ${e.message}", Toast.LENGTH_SHORT).show()
                    }
            }
        }.addOnFailureListener { e ->
            Toast.makeText(this, "Upload failed: ${e.message}", Toast.LENGTH_SHORT).show()
        }
    }



    companion object {
        private const val IMAGE_PICK_CODE = 102
        private const val PERMISSION_CODE = 101
    }

}
