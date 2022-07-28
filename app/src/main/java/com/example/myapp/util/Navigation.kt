package com.example.myapp.util

import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.myapp.presentation.login.LoginScreen
import com.example.myapp.presentation.register.RegisterScreen


@Composable
fun Navigation(){
    val navController = rememberNavController()
    NavHost(navController = navController,
            startDestination = Screen.LoginScreen.route){

            composable(route = Screen.LoginScreen.route){
                    LoginScreen(navController = navController)
            }
        composable(route = Screen.RegisterScreen.route){
            RegisterScreen(navController = navController)
        }

    }
}

@Composable
fun MainScreen(navController: NavController){
    var text by remember{
        mutableStateOf("")
    }
    Column(
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 50.dp)
    ) {
        TextField(value =  text, onValueChange = {
            text= it
        },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))
        Button(onClick = {
            navController.navigate(Screen.LoginScreen.wirthArgs(text))
        },
            modifier = Modifier.align(Alignment.End)
            ) {
            Text(text = "To Signin Screen")
        }

    }
}
@Composable
fun SigninScreen(name: String?){
    Box(modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ){
        Text(text = "Hello, $name")
    }
}