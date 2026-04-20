package com.example.smple.ui.auth

import androidx.compose.foundation.background
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.Image
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.smple.R

@Composable
fun OnboardingScreen(
    onSignUpClick: () -> Unit,
    onLoginClick: () -> Unit,
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colorStops = arrayOf(
                        0f to Color(0xFF33D65F),
                        0.42f to Color(0xFFDDEEE0),
                        1f to Color(0xFF33D65F),
                    )
                )
            )
            .statusBarsPadding()
            .navigationBarsPadding(),
    ) {
        DecorativeBarsTopRight(modifier = Modifier.align(Alignment.TopEnd))
        DecorativeBarsBottomLeft(modifier = Modifier.align(Alignment.BottomStart))

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 28.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {
            LogoLockup()

            Spacer(modifier = Modifier.height(48.dp))

            Text(
                text = stringResource(R.string.onboarding_tagline),
                style = MaterialTheme.typography.titleLarge.copy(
                    fontWeight = FontWeight.Bold,
                    shadow = Shadow(
                        color = Color.Black.copy(alpha = 0.35f),
                        blurRadius = 4f,
                    ),
                ),
                color = Color(0xFF141414),
            )

            Spacer(modifier = Modifier.height(72.dp))

            OutlinedButton(
                onClick = onLoginClick,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                shape = RoundedCornerShape(30.dp),
                colors = ButtonDefaults.outlinedButtonColors(contentColor = Color.White),
                border = BorderStroke(2.dp, Color.White.copy(alpha = 0.9f)),
            ) {
                Text(
                    text = stringResource(R.string.login),
                    style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold),
                    color = Color.White,
                )
            }

            Spacer(modifier = Modifier.height(14.dp))

            Button(
                onClick = onSignUpClick,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                shape = RoundedCornerShape(30.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Black,
                    contentColor = Color.White,
                ),
            ) {
                Text(
                    text = stringResource(R.string.sign_up),
                    style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold),
                )
            }
        }
    }
}


@Composable
private fun LogoLockup() {
    Image(
        painter = painterResource(id = R.drawable.smple_logo),
        contentDescription = stringResource(R.string.smple_logo_content_description),
        contentScale = ContentScale.Fit,
        modifier = Modifier
            .fillMaxWidth()
            .height(100.dp),
    )
}

@Composable
private fun DecorativeBarsTopRight(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier.padding(top = 18.dp),
        horizontalAlignment = Alignment.End,
        verticalArrangement = Arrangement.spacedBy(18.dp),
    ) {
        Box(
            modifier = Modifier
                .width(252.dp)
                .height(10.dp)
                .background(Color.Black, RoundedCornerShape(topStart = 18.dp, bottomStart = 18.dp)),
        )
        Box(
            modifier = Modifier
                .width(194.dp)
                .height(10.dp)
                .background(Color.Black, RoundedCornerShape(topStart = 18.dp, bottomStart = 18.dp)),
        )
        Box(
            modifier = Modifier
                .width(138.dp)
                .height(10.dp)
                .background(Color.Black, RoundedCornerShape(topStart = 18.dp, bottomStart = 18.dp)),
        )
    }
}

@Composable
private fun DecorativeBarsBottomLeft(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier.padding(bottom = 24.dp),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.spacedBy(18.dp),
    ) {
        Box(
            modifier = Modifier
                .width(96.dp)
                .height(10.dp)
                .background(Color.Black, RoundedCornerShape(topEnd = 18.dp, bottomEnd = 18.dp)),
        )
        Box(
            modifier = Modifier
                .width(170.dp)
                .height(10.dp)
                .background(Color.Black, RoundedCornerShape(topEnd = 18.dp, bottomEnd = 18.dp)),
        )
        Box(
            modifier = Modifier
                .width(252.dp)
                .height(10.dp)
                .background(Color.Black, RoundedCornerShape(topEnd = 18.dp, bottomEnd = 18.dp)),
        )
    }
}
