package com.example.smple.ui.auth

import androidx.compose.foundation.Image
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
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
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.smple.R

@Composable
fun SignUpScreen(
    viewModel: AuthViewModel,
    onSignUpSuccess: () -> Unit,
    onLoginClick: () -> Unit,
) {
    var email by rememberSaveable { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }
    var confirmPassword by rememberSaveable { mutableStateOf("") }
    var localError by rememberSaveable { mutableStateOf("") }
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val isLoading = uiState is AuthViewModel.UiState.Loading

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
                modifier = Modifier.align(Alignment.TopEnd).padding(top = 20.dp)
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
            modifier = Modifier.align(Alignment.BottomStart).padding(bottom = 22.dp)
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 32.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top,
        ) {
            Spacer(modifier = Modifier.height(250.dp))

            AuthField(value = email, onValueChange = { email = it; viewModel.clearError() }, hint = stringResource(R.string.email_hint), keyboardType = KeyboardType.Email, imeAction = ImeAction.Next)
            Spacer(modifier = Modifier.height(14.dp))
            AuthField(value = password, onValueChange = { password = it; viewModel.clearError() }, hint = stringResource(R.string.password_hint), keyboardType = KeyboardType.Password, imeAction = ImeAction.Next, isPassword = true)
            Spacer(modifier = Modifier.height(14.dp))
            AuthField(value = confirmPassword, onValueChange = { confirmPassword = it; localError = "" }, hint = stringResource(R.string.confirm_password_hint), keyboardType = KeyboardType.Password, imeAction = ImeAction.Done, isPassword = true)

            val errorText = localError.ifBlank {
                if (uiState is AuthViewModel.UiState.Error) (uiState as AuthViewModel.UiState.Error).message else ""
            }
            if (errorText.isNotBlank()) {
                Text(
                    text = errorText,
                    color = Color(0xFFD32F2F),
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier.padding(top = 8.dp).align(Alignment.Start),
                )
            }

            Spacer(modifier = Modifier.height(34.dp))

            Button(
                onClick = {
                    if (password != confirmPassword) { localError = "Passwords do not match"; return@Button }
                    viewModel.signUp(email, password, onSignUpSuccess)
                },
                enabled = !isLoading,
                shape = RoundedCornerShape(28.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color.Black, contentColor = Color.White),
                modifier = Modifier.fillMaxWidth().height(56.dp),
            ) {
                if (isLoading) {
                    CircularProgressIndicator(modifier = Modifier.size(24.dp), color = Color.White, strokeWidth = 2.dp)
                } else {
                    Text(text = stringResource(R.string.sign_up), style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold))
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            Row(horizontalArrangement = Arrangement.Center, verticalAlignment = Alignment.CenterVertically) {
                Text(text = stringResource(R.string.already_account), color = Color(0xFF262626))
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = stringResource(R.string.login),
                    color = Color(0xFF34C759),
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier.clickable(onClick = onLoginClick),
                )
            }
        }
    }
}

@Composable
private fun AuthField(value: String, onValueChange: (String) -> Unit, hint: String, keyboardType: KeyboardType, imeAction: ImeAction, isPassword: Boolean = false) {
    TextField(
        value = value,
        onValueChange = onValueChange,
        singleLine = true,
        textStyle = TextStyle(color = Color(0xFF222222)),
        visualTransformation = if (isPassword) PasswordVisualTransformation() else VisualTransformation.None,
        placeholder = { Text(text = hint, color = Color(0xFFB9B9B9)) },
        keyboardOptions = KeyboardOptions(keyboardType = keyboardType, imeAction = imeAction),
        colors = TextFieldDefaults.colors(
            focusedContainerColor = Color.Transparent,
            unfocusedContainerColor = Color.Transparent,
            disabledContainerColor = Color.Transparent,
            focusedIndicatorColor = Color(0xFFE3E3E3),
            unfocusedIndicatorColor = Color(0xFFE3E3E3),
        ),
        modifier = Modifier.fillMaxWidth(),
    )
}

@Composable
private fun DecorativeBarsTopRight(modifier: Modifier = Modifier) {
    Column(modifier = modifier, horizontalAlignment = Alignment.End, verticalArrangement = Arrangement.spacedBy(18.dp)) {
        Box(modifier = Modifier.width(186.dp).height(10.dp).background(Color.Black, RoundedCornerShape(topStart = 18.dp, bottomStart = 18.dp)))
        Box(modifier = Modifier.width(132.dp).height(10.dp).background(Color.Black, RoundedCornerShape(topStart = 18.dp, bottomStart = 18.dp)))
    }
}

@Composable
private fun DecorativeBarsBottomLeft(modifier: Modifier = Modifier) {
    Column(modifier = modifier, horizontalAlignment = Alignment.Start, verticalArrangement = Arrangement.spacedBy(18.dp)) {
        Box(modifier = Modifier.width(132.dp).height(10.dp).background(Color.Black, RoundedCornerShape(topEnd = 18.dp, bottomEnd = 18.dp)))
        Box(modifier = Modifier.width(186.dp).height(10.dp).background(Color.Black, RoundedCornerShape(topEnd = 18.dp, bottomEnd = 18.dp)))
    }
}
