package com.leotorrealba.translator_kmp.android.splash

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.leotorrealba.translator_kmp.android.R
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(
    onSplashFinished: () -> Unit
) {
    var animationPhase by remember { mutableStateOf(0) }

    // Background color animation
    val backgroundColor by androidx.compose.animation.animateColorAsState(
        targetValue = when (animationPhase) {
            0 -> Color(0xFF1976D2) // Initial blue
            1 -> Color(0xFF2196F3) // Lighter blue
            else -> Color.White
        },
        animationSpec = tween(1000),
        label = "background_color"
    )

    // Logo animations with different phases
    val logoScale by animateFloatAsState(
        targetValue = when (animationPhase) {
            0 -> 0.3f
            1 -> 1.2f
            else -> 1f
        },
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessMedium
        ),
        label = "logo_scale"
    )

    val logoRotation by animateFloatAsState(
        targetValue = if (animationPhase >= 1) 360f else 0f,
        animationSpec = tween(
            durationMillis = 1000,
            easing = FastOutSlowInEasing
        ),
        label = "logo_rotation"
    )

    // Text slide up animation
    val textOffset by animateFloatAsState(
        targetValue = if (animationPhase >= 2) 0f else 100f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessMedium
        ),
        label = "text_offset"
    )

    val textAlpha by animateFloatAsState(
        targetValue = if (animationPhase >= 2) 1f else 0f,
        animationSpec = tween(500),
        label = "text_alpha"
    )

    LaunchedEffect(Unit) {
        // Animation sequence
        delay(300)
        animationPhase = 1 // Start logo animation
        delay(800)
        animationPhase = 2 // Start text animation
        delay(1500)
        animationPhase = 3 // Final phase
        delay(500)
        onSplashFinished()
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {

        Image(
            painter = painterResource(id = R.drawable.lt_logo),
            contentDescription = "App Logo",
            modifier = Modifier
                .size(120.dp)
                .align(Alignment.Center),
            contentScale = ContentScale.Fit
        )

        Text(
            text = stringResource(R.string.lt_website),
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 80.dp)
                .fillMaxWidth(),
            textAlign = TextAlign.Center,
            fontSize = 18.sp,
            fontWeight = FontWeight.Medium,
            color = MaterialTheme.colorScheme.primary
        )
    }
}