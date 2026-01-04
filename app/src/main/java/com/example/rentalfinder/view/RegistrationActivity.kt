package com.example.rentalfinder.view

import android.app.Activity
import android.app.DatePickerDialog
import android.content.Context
import android.content.Intent
import android.icu.util.Calendar
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.IntRect
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.rentalfinder.R
import com.example.rentalfinder.model.UserModel
import com.example.rentalfinder.repository.UserRepoImpl
import com.example.rentalfinder.ui.theme.Blue
import com.example.rentalfinder.ui.theme.DarkGrey
import com.example.rentalfinder.ui.theme.ForestGreen
import com.example.rentalfinder.ui.theme.Gold
import com.example.rentalfinder.ui.theme.MintGreen
import com.example.rentalfinder.ui.theme.OffWhite
import com.example.rentalfinder.ui.theme.Purple80
import com.example.rentalfinder.ui.theme.RentalFinderTheme
import com.example.rentalfinder.ui.theme.SandBiege
import com.example.rentalfinder.ui.theme.White
import com.example.rentalfinder.viewmodel.UserViewModel

class RegistrationActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            RegistrationBody()
        }
    }

}


@Composable
fun RegistrationBody(){

    val userViewModel = remember { UserViewModel(UserRepoImpl())}

    var email by remember { mutableStateOf("") }    //ui recompose
    var password by remember { mutableStateOf("") }
    var newpassword by remember { mutableStateOf(value = "") }

    var visibility by remember { mutableStateOf(false) }

    var checkbox by remember { mutableStateOf(false) }

    val context = LocalContext.current
    val activity = context as Activity

    LazyColumn(modifier = Modifier.fillMaxSize()
        .background(color = MintGreen),
        contentPadding = PaddingValues(top = 120.dp)) {
        item {
            Box(modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
            ){
                Column(modifier = Modifier
                    .fillMaxWidth()
                    .padding(end = 60.dp),
                    horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        "Let's Get Started",
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(10.dp),
                        style = TextStyle(
                            fontSize = 36.sp,
                            color = ForestGreen,
                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.Center
                        )
                    )
                    Text("Find a place that feels like home",
                        style = TextStyle(
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Medium,
                            color = DarkGrey,
                            textAlign = TextAlign.Center
                        ),
                        modifier = Modifier.padding(vertical = 20.dp)
                    )
                }
                Image(painter = painterResource(R.drawable.homes),
                    contentDescription = null,
                    modifier = Modifier
                        .size(200.dp)
                        .align(Alignment.CenterEnd)
                        .alpha(0.50f))
            }
        }
        item {
            Column {
                OutlinedTextField(
                    value = email,
                    onValueChange = { data -> email = data },
                    placeholder = { Text("Email") },
                    colors = TextFieldDefaults.colors(
                        unfocusedContainerColor = OffWhite,
                        focusedContainerColor = OffWhite,
                        focusedIndicatorColor = SandBiege,
                        unfocusedIndicatorColor = Color.Transparent

                    ),
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier.fillMaxWidth()
                        .padding(horizontal = 30.dp),
                    leadingIcon = {
                        Icon(painter = painterResource(R.drawable.baseline_email_24),
                            contentDescription = null,
                            tint = Gold )
                    }
                )

                Spacer(modifier = Modifier.height(20.dp))

                OutlinedTextField(
                    value = password,
                    onValueChange = { data -> password = data },
                    placeholder = { Text("Password") },
                    colors = TextFieldDefaults.colors(
                        unfocusedContainerColor = OffWhite,
                        focusedContainerColor = OffWhite,
                        focusedIndicatorColor = SandBiege,
                        unfocusedIndicatorColor = Color.Transparent
                    ),
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier.fillMaxWidth()
                        .padding(horizontal = 30.dp),
                    visualTransformation = if (visibility) VisualTransformation.None
                    else
                        PasswordVisualTransformation(),
                    leadingIcon = {
                        Icon(painter = painterResource(R.drawable.baseline_lock_24),
                            contentDescription = null,
                            tint = Gold)
                    },
                    trailingIcon = {
                        IconButton(onClick = {visibility = !visibility}){
                            Icon(painter = if (visibility)
                                painterResource(R.drawable.baseline_visibility_off_24)
                            else
                                painterResource(R.drawable.baseline_visibility_24),
                                contentDescription = null,
                                tint = Gold)
                        }
                    }
                )

                Spacer(modifier = Modifier.height(20.dp))

                OutlinedTextField(
                    value = newpassword,
                    onValueChange = { data -> newpassword = data },
                    placeholder = { Text("Confirm password") },
                    colors = TextFieldDefaults.colors(
                        unfocusedContainerColor = OffWhite,
                        focusedContainerColor = OffWhite,
                        focusedIndicatorColor = SandBiege,
                        unfocusedIndicatorColor = Color.Transparent
                    ),
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier.fillMaxWidth()
                        .padding(horizontal = 30.dp),
                    visualTransformation = if (visibility) VisualTransformation.None
                    else
                        PasswordVisualTransformation(),
                    leadingIcon = {
                        Icon(painter = painterResource(R.drawable.baseline_lock_24),
                            contentDescription = null,
                            tint = Gold)
                    },
                    trailingIcon = {
                        IconButton(onClick = {visibility = !visibility}){
                            Icon(painter = if (visibility)
                                painterResource(R.drawable.baseline_visibility_off_24)
                            else
                                painterResource(R.drawable.baseline_visibility_24),
                                contentDescription = null,
                                tint = Gold)
                        }
                    }
                )
            }

            Row(modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 30.dp),
                verticalAlignment = Alignment.CenterVertically) {
                Checkbox(checked = checkbox,
                    onCheckedChange = {checkbox=it},
                    colors = CheckboxDefaults.colors(
                        checkedColor = ForestGreen,
                        checkmarkColor = SandBiege
                    ))
                Text("I agree to terms & conditions.",
                    style = TextStyle(fontSize = 18.sp,
                    color = ForestGreen
                ))
            }


            Column (modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 30.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)){
                OutlinedButton(onClick = {
                    if (!checkbox){
                        Toast.makeText(
                            context,
                            "Please agree to the terms and conditions",
                            Toast.LENGTH_SHORT
                        ).show()
                    }else{
                        userViewModel.register(email, password){
                                success, message, userId->
                            if(success){
                                val model = UserModel(
                                    id = userId,
                                    email = email,
                                )
                                userViewModel.addUserToDatabase(userId,model){
                                        success, message->
                                    Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
                                    if (success){
                                        val intent = Intent(context, LoginActivity::class.java)
                                        context.startActivity(intent)
                                    }else{
                                        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
                                    }
                                }
                            }else{
                                Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
                            }
                        }

                    }
                },
                    border = BorderStroke(0.dp, Color.Transparent),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 20.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Gold),

                ) {
                    Text("Sign Up",
                        style = TextStyle(fontSize = 20.sp,
                            fontWeight = FontWeight.ExtraBold))
                }
                Row(modifier = Modifier.fillMaxWidth()
                    .padding(horizontal = 15.dp),
                    horizontalArrangement = Arrangement.End,
                    verticalAlignment = Alignment.CenterVertically) {
                    Text("Already have an account? ",
                        style = TextStyle(fontSize = 16.sp))
                    Text(" Log in",
                        style = TextStyle(fontSize = 18.sp,
                            color = ForestGreen,
                            fontWeight = FontWeight.Bold
                        ),
                        modifier = Modifier.clickable{
                            val intent = Intent(context, LoginActivity::class.java)
                            context.startActivity((intent))
                        })
                }

            }
        }
    }
}
