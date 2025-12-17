package com.example.rentalfinder.view

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.rentalfinder.R
import com.example.rentalfinder.repository.UserRepoImpl
import com.example.rentalfinder.ui.theme.Blue
import com.example.rentalfinder.ui.theme.Gold
import com.example.rentalfinder.ui.theme.Gray
import com.example.rentalfinder.ui.theme.MintGreen
import com.example.rentalfinder.ui.theme.OffWhite
import com.example.rentalfinder.ui.theme.Purple80
import com.example.rentalfinder.ui.theme.SandBiege
import com.example.rentalfinder.viewmodel.UserViewModel

class LoginActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            LoginBody()
        }
    }
}

@Composable
fun LoginBody(){

    val userViewModel = remember { UserViewModel(UserRepoImpl())}

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    var visibility by remember { mutableStateOf(false) }

    val context = LocalContext.current
    val activity = context as Activity

    LazyColumn(modifier = Modifier.fillMaxSize()
        .background(color = MintGreen)) {
        item{
            Column {
                Text(
                    "Sign in",
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp),
                    style = TextStyle(
                        fontSize = 30.sp,
                        color = Blue,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center
                    )
                )
                Text("This is a rental site finder platform. " +
                        "Sign up to browse your new home",
                    style = TextStyle(
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Light,
                        color = Gray,
                        textAlign = TextAlign.Center
                    ),
                    modifier = Modifier.padding(vertical = 20.dp)
                )
                Spacer(modifier = Modifier.height(30.dp))

                OutlinedTextField(
                    value = email,
                    onValueChange = { data -> email = data },
                    placeholder = { Text("abc@gmail.com") },
                    colors = TextFieldDefaults.colors(
                        unfocusedContainerColor = OffWhite,
                        focusedContainerColor = OffWhite,
                        focusedIndicatorColor = SandBiege,
                        unfocusedIndicatorColor = Color.DarkGray
                    ),
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier.fillMaxWidth()
                        .padding(horizontal = 15.dp)
                )

                Spacer(modifier = Modifier.height(30.dp))

                OutlinedTextField(
                    value = password,
                    onValueChange = { data -> password = data },
                    placeholder = { Text("*********") },
                    colors = TextFieldDefaults.colors(
                        unfocusedContainerColor = OffWhite,
                        focusedContainerColor = OffWhite,
                        focusedIndicatorColor = SandBiege,
                        unfocusedIndicatorColor = Color.DarkGray
                    ),
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier.fillMaxWidth()
                        .padding(horizontal = 15.dp),
                    visualTransformation = if (visibility) VisualTransformation.None
                    else
                        PasswordVisualTransformation(),
                    trailingIcon = {
                        IconButton(onClick = {visibility = !visibility}){
                            Icon(painter = if (visibility)
                                painterResource(R.drawable.baseline_visibility_off_24)
                            else
                                painterResource(R.drawable.baseline_visibility_24),
                                contentDescription = null)
                        }
                    }
                )

            }

            Column(modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 15.dp)) {
                Text("Forget Password?",
                    style = TextStyle(textAlign = TextAlign.Right,
                        fontSize = 16.sp,
                        color = Gray),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp)
                        .clickable {
                            userViewModel.forgetPassword(email) { success, message ->
                                Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
                            }
                        }
                )

                OutlinedButton(onClick = {
                        userViewModel.login(email, password){
                                success, message->
                            if(success){
                                Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
                                activity.finish()
                            }else{
                                Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
                            }
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 15.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Gold)) {
                    Text("Log in",
                        style = TextStyle(fontSize = 20.sp,
                            fontWeight = FontWeight.ExtraBold))
                }

                Row {
                    Text("Don't have account? ",
                        style = TextStyle(fontSize = 18.sp))
                    Text(" Sign Up",
                        style = TextStyle(fontSize = 18.sp,
                            color = Blue),
                        modifier = Modifier.clickable{
                            val intent = Intent(context, RegistrationActivity::class.java)
                            context.startActivity((intent))
                        })
                }
            }
        }
    }

}