package com.example.watchme.app.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.watchme.R
import com.example.watchme.core.Routes
import com.example.watchme.ui.theme.AppBackground
import com.example.watchme.ui.theme.BottomBarBackground

@Composable
fun TopBar() {
    Column(
        Modifier
            .fillMaxWidth()
            .padding(top = 48.dp, bottom = 16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painterResource(R.drawable.ic_logo),
            contentDescription = stringResource(R.string.app_logo),
            Modifier.width(128.dp)
        )
    }
}

@Composable
fun BottomBar(
    destination: String,
    onItemClicked: (String) -> Unit
) {

    NavigationBar(
        containerColor = BottomBarBackground,
        tonalElevation = 16.dp,
        contentColor = Color.White,
        modifier = Modifier.fillMaxWidth()
    ) {
        NavigationBarItem(
            selected = destination == Routes.Home.route,
            onClick = {
                onItemClicked(Routes.Home.route)
            },
            icon = { Icon(Icons.Default.Home, contentDescription = stringResource(R.string.home)) },
        )
        NavigationBarItem(
            selected = destination == Routes.Search.route,
            onClick = {
                onItemClicked(Routes.Search.route)
            },
            icon = {
                Icon(
                    Icons.Default.Search,
                    contentDescription = stringResource(R.string.search)
                )
            },
        )
        NavigationBarItem(
            selected = destination == Routes.Profile.route,
            onClick = { onItemClicked(Routes.Profile.route) },
            icon = {
                Icon(
                    Icons.Default.AccountCircle,
                    contentDescription = stringResource(R.string.profile)
                )
            },
        )
    }
}