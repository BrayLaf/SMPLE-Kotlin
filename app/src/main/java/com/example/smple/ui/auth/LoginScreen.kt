package com.example.smple.ui.auth

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.Image
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import com.example.smple.R

@Composable
fun LoginScreen(
    onLoginSuccess: () -> Unit,
    onSignUpClick: () -> Unit,
    onForgotPasswordClick: () -> Unit,
) {
    var email by rememberSaveable { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .imePadding(),
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(210.dp)
                .background(
                    Brush.verticalGradient(
                        colorStops = arrayOf(
                            0f to Color(0xFF33D65F),
                            0.85f to Color(0xFFE7F2E9),
                            1f to Color.White,
                        )
                    )
                )
                .statusBarsPadding(),
        ) {
            Image(
                painter = painterResource(id = R.drawable.smple_logo),
                contentDescription = stringResource(R.string.smple_logo_content_description),
                contentScale = ContentScale.Fit,
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .padding(start = 20.dp, top = 10.dp)
                    .fillMaxWidth(0.55f)
                    .height(64.dp),
            )

            DecorativeBarsTopRight(
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(top = 20.dp)
            )
        }

        Box(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .height(260.dp)
                .background(
                    Brush.verticalGradient(
                        colorStops = arrayOf(
                            0f to Color.Transparent,
                            0.35f to Color(0xFFF3F9F4),
                            0.72f to Color(0xFFDDF0E1),
                            1f to Color(0xFF33D65F),
                        )
                    )
                ),
        )

        DecorativeBarsBottomLeft(
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(bottom = 22.dp)
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 32.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top,
        ) {
            Spacer(modifier = Modifier.height(250.dp))

            TextField(
                value = email,
                onValueChange = { email = it },
                singleLine = true,
                textStyle = TextStyle(color = Color(0xFF222222)),
                placeholder = {
                    Text(
                        text = stringResource(R.string.email_hint),
                        color = Color(0xFFB9B9B9),
                    )
                },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Email,
                    imeAction = ImeAction.Next,
                ),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent,
                    disabledContainerColor = Color.Transparent,
                    focusedIndicatorColor = Color(0xFFE3E3E3),
                    unfocusedIndicatorColor = Color(0xFFE3E3E3),
                ),
                modifier = Modifier.fillMaxWidth(),
            )

            Spacer(modifier = Modifier.height(14.dp))

            TextField(
                value = password,
                onValueChange = { password = it },
                singleLine = true,
                textStyle = TextStyle(color = Color(0xFF222222)),
                visualTransformation = PasswordVisualTransformation(),
                placeholder = {
                    Text(
                        text = stringResource(R.string.password_hint),
                        color = Color(0xFFB9B9B9),
                    )
                },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Password,
                    imeAction = ImeAction.Done,
                ),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent,
                    disabledContainerColor = Color.Transparent,
                    focusedIndicatorColor = Color(0xFFE3E3E3),
                    unfocusedIndicatorColor = Color(0xFFE3E3E3),
                ),
                modifier = Modifier.fillMaxWidth(),
            )

            Text(
                text = stringResource(R.string.forgot_password),
                color = Color(0xFF1E1E1E),
                style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.SemiBold),
                modifier = Modifier
                    .align(Alignment.End)
                    .padding(top = 8.dp)
                    .clickable(onClick = onForgotPasswordClick),
            )

            Spacer(modifier = Modifier.height(48.dp))

            Button(
                onClick = onLoginSuccess,
                shape = RoundedCornerShape(28.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Black,
                    contentColor = Color.White,
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
            ) {
                Text(
                    text = stringResource(R.string.login),
                    style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                )
            }

            Spacer(modifier = Modifier.height(10.dp))

            Row(horizontalArrangement = Arrangement.Center, verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = stringResource(R.string.no_account),
                    color = Color(0xFF262626),
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = stringResource(R.string.sign_up),
                    color = Color(0xFF34C759),
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier.clickable(onClick = onSignUpClick),
                )
            }
        }
    }
}

@Composable
private fun DecorativeBarsTopRight(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.End,
        verticalArrangement = Arrangement.spacedBy(18.dp),
    ) {
        Box(
            modifier = Modifier
                .width(186.dp)
                .height(10.dp)
                .background(Color.Black, RoundedCornerShape(topStart = 18.dp, bottomStart = 18.dp)),
        )
        Box(
            modifier = Modifier
                .width(132.dp)
                .height(10.dp)
                .background(Color.Black, RoundedCornerShape(topStart = 18.dp, bottomStart = 18.dp)),
        )
    }
}

@Composable
private fun DecorativeBarsBottomLeft(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.spacedBy(18.dp),
    ) {
        Box(
            modifier = Modifier
                .width(132.dp)
                .height(10.dp)
                .background(Color.Black, RoundedCornerShape(topEnd = 18.dp, bottomEnd = 18.dp)),
        )
        Box(
            modifier = Modifier
                .width(186.dp)
                .height(10.dp)
                .background(Color.Black, RoundedCornerShape(topEnd = 18.dp, bottomEnd = 18.dp)),
        )
    }
}

