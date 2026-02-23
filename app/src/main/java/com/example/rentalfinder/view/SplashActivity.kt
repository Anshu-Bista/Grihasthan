package com.example.rentalfinder.view

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import com.example.rentalfinder.R
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.rentalfinder.ui.theme.Gold
import com.example.rentalfinder.ui.theme.MintGreen
import com.example.rentalfinder.ui.theme.SoftOlive
import com.example.rentalfinder.view.ui.theme.RentalFinderTheme
import kotlinx.coroutines.delay

class SplashActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SplashBody()
        }
    }
}

@Composable
fun SplashBody(){
    val context = LocalContext.current
    val activity = context as Activity

    var visisble by remember { mutableStateOf(false) }
    val alphaAnim by animateFloatAsState(if (visisble) 1f else 0f,
        animationSpec = tween (
            durationMillis = 2000
        )
    )

    LaunchedEffect(Unit) {
        visisble = true
        delay(3500)
        val intent = Intent(context, LoginActivity::class.java)
        context.startActivity(intent)
        activity.finish()
    }

    val gradientBrush = Brush.verticalGradient(
        colors = listOf(
            (SoftOlive),
            (MintGreen)
        )
    )

    Scaffold { padding ->
        Column(modifier = Modifier
            .fillMaxSize()
            .padding(padding)
            .background(MintGreen),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Image(
                painter = painterResource(R.drawable.logo),
                "",
                modifier = Modifier.size((350.dp))
                    .alpha(alphaAnim)
            )
            Spacer(modifier = Modifier.height(10.dp))
            CircularProgressIndicator(
                color = Gold,
                modifier = Modifier.alpha(alphaAnim)
                )
        }
    }
}