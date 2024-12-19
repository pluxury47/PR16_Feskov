package com.example.applicationwithauthorization

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.FileProvider
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import java.io.File
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun Profile(navController: NavController) {
    val context = LocalContext.current
    var selectedPhotoUri by remember { mutableStateOf<Uri?>(null) }
    val galleryItems = remember { mutableStateListOf<Any>(R.drawable.image1, R.drawable.image2, R.drawable.image3, R.drawable.image4) }

    val cameraLauncher = rememberLauncherForActivityResult(ActivityResultContracts.TakePicture()) { isSaved ->
        if (isSaved) {
            selectedPhotoUri?.let { galleryItems.add(it) }
        }
    }

    fun generateImageFile(): File {
        val timestamp = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())
        val directory = context.getExternalFilesDir(null)
        return File.createTempFile("IMG_$timestamp", ".jpg", directory)
    }

    Box(modifier = Modifier.fillMaxSize().background(Color(0xFF253334))) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(Modifier.height(20.dp))
            Image(
                painter = rememberAsyncImagePainter(R.drawable.logo),
                contentDescription = null,
                modifier = Modifier.size(70.dp)
            )
            Spacer(Modifier.height(50.dp))
            Image(
                painter = rememberAsyncImagePainter(R.drawable.da93b0a73e803ae6f34f81d1692f695a),
                contentDescription = null,
                modifier = Modifier.size(190.dp).clip(CircleShape).clickable { navController.navigate("Menu") }
            )
            Text(
                text = "Эмиль",
                fontSize = 40.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                color = Color.White
            )
        }
        Column(modifier = Modifier.padding(10.dp).align(Alignment.TopStart)) {
            Image(
                painter = painterResource(R.drawable.menu),
                contentDescription = null,
                modifier = Modifier.size(45.dp).clickable { navController.navigate("Menu") }
            )
        }
        Column(modifier = Modifier.padding(290.dp, 40.dp).align(Alignment.TopEnd)) {
            Text(
                text = "exit",
                fontSize = 30.sp,
                color = Color.White,
                modifier = Modifier.clickable { navController.navigate("Main") }
            )
        }
        Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
            Spacer(Modifier.height(380.dp))
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                verticalArrangement = Arrangement.spacedBy(10.dp),
                horizontalArrangement = Arrangement.spacedBy(10.dp),
                modifier = Modifier.weight(1f)
            ) {
                items(galleryItems) { item ->
                    Box(
                        modifier = Modifier.size(100.dp).clip(RoundedCornerShape(16.dp)).background(Color.Gray).clickable {
                            val uri = when (item) {
                                is Int -> Uri.parse("android.resource://com.example.applicationwithauthorization/$item")
                                is Uri -> item
                                else -> null
                            }
                            uri?.let {
                                val encodedUri = Uri.encode(it.toString())
                                navController.navigate("Photo?photoUri=$encodedUri")
                            }
                        }
                    ) {
                        if (item is Int) {
                            Image(
                                painter = painterResource(item),
                                contentDescription = null,
                                modifier = Modifier.fillMaxSize(),
                                contentScale = ContentScale.Crop
                            )
                        } else if (item is Uri) {
                            Image(
                                painter = rememberAsyncImagePainter(item),
                                contentDescription = null,
                                modifier = Modifier.fillMaxSize(),
                                contentScale = ContentScale.Crop
                            )
                        }
                    }
                }
                item {
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier.size(100.dp).clip(RoundedCornerShape(16.dp)).background(Color.Green).clickable {
                            val imageFile = generateImageFile()
                            val uri = FileProvider.getUriForFile(context, "${context.packageName}.fileprovider", imageFile)
                            selectedPhotoUri = uri
                            cameraLauncher.launch(uri)
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Default.Add,
                            contentDescription = null,
                            tint = Color.White,
                            modifier = Modifier.size(40.dp)
                        )
                    }
                }
            }
        }
        Column(modifier = Modifier.padding(60.dp, 670.dp).align(Alignment.BottomStart)) {
            Spacer(Modifier.height(50.dp))
            Image(
                painter = painterResource(R.drawable.logov2),
                contentDescription = null,
                modifier = Modifier.size(40.dp).clickable { navController.navigate("Main") }
            )
        }
        Column(modifier = Modifier.align(Alignment.BottomCenter)) {
            Spacer(Modifier.height(717.dp))
            Image(
                painter = painterResource(R.drawable.music),
                contentDescription = null,
                modifier = Modifier.size(40.dp).clickable { navController.navigate("Music") }
            )
        }
        Column(modifier = Modifier.padding(255.dp, 672.dp).align(Alignment.BottomEnd)) {
            Spacer(Modifier.height(50.dp))
            Image(
                painter = painterResource(R.drawable.profilev2),
                contentDescription = null,
                modifier = Modifier.size(30.dp)
            )
        }
    }
}
