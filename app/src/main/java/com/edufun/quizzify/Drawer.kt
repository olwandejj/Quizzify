package com.edufun.quizzify

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Logout
import androidx.compose.material.icons.outlined.Menu
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.draw.clip
import androidx.compose.foundation.Image
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.edufun.quizzify.ui.theme.*
import kotlinx.coroutines.launch

// AppBar
@Composable
fun AppBar(drawerState: DrawerState, modifier: Modifier = Modifier) {
    val scope = rememberCoroutineScope()
    Column {
        Row(
            modifier = modifier
                .fillMaxWidth()
                .background(Purple40),

            ) {
            IconButton(
                onClick = {
                    scope.launch {
                        drawerState.open()
                    }
                },
                modifier = Modifier.align(Alignment.CenterVertically),
            ) {
                Icon(
                    Icons.Outlined.Menu,
                    tint = Color.White,
                    contentDescription = null,
                    modifier = Modifier.size(25.dp).align(Alignment.Bottom),
                )

            }
            Text(
                text = "Quizzify",
                style = MaterialTheme.typography.headlineSmall,
                modifier = Modifier.padding(12.dp)
            )

        }
//        Text(
//            text = "Menu",
//            style = MaterialTheme.typography.headlineSmall,
//            modifier = Modifier.padding(10.dp).align(Alignment.CenterHorizontally)
//        )
    }
}

// Drawer
@Composable
fun DrawerTab(onQuizSelected: (String) -> Unit, onLogout: () -> Unit, onProfile: () -> Unit) {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val dockerWidth = LocalConfiguration.current.screenWidthDp * 0.75
    var showDialog by remember { mutableStateOf(false) }

    ModalNavigationDrawer(
        drawerContent = {
            ModalDrawerSheet(
                drawerContainerColor = Color.Black,
                drawerTonalElevation = Dp.Hairline,

                modifier = Modifier
                    .width(dockerWidth.dp)
                    .drawWithContent {
                        drawContent()
                        drawLine(
                            color = Color.White,
                            start = Offset(size.width, 0f),
                            end = Offset(size.width, size.height),
                            strokeWidth = 0.3.dp.toPx()
                        )
                    }
            ) {

                Column(modifier = Modifier.padding(20.dp)) {
                    Image( // TODO: Profile Image
                        painter = painterResource(R.drawable.logo),
                        contentDescription = null,
                        alignment = Alignment.TopStart,
                        modifier = Modifier
                            .wrapContentWidth()
                            .size(40.dp)
                            .clip(shape = CircleShape)
                    )
                    Spacer(modifier = Modifier.height(6.dp))

                    Text(
                        text = "John Doe!!", // TODO
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier
                    )
                    Text(
                        text = "@JustMeHopeless", // TODO
                        color = Color.Gray,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier
                    )
                    Spacer(modifier = Modifier.height(6.dp))

                    Column(
                        modifier = Modifier.fillMaxWidth(),
//                                horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Row() {
                            Text(
                                text = "3", // TODO
                                fontSize = 14.sp,
                                color = Color.White,
                                modifier = Modifier
                            )
                            Text(
                                text = " Courses Enrolled",
                                fontSize = 14.sp,
                                color = Color.Gray,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis,
                                modifier = Modifier
                            )
                        }
                        Spacer(modifier = Modifier.width(3.dp))

                        Row {
                            Text(
                                text = "16", // TODO
                                fontSize = 14.sp,
                                color = Color.White,
                                modifier = Modifier
                            )
                            Text(
                                text = " Quizzes done",
                                fontSize = 14.sp,
                                color = Color.Gray,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis,
                                modifier = Modifier
                            )
                        }
                    }
                }

//                Spacer(modifier = Modifier.height(10.dp))


                Spacer(modifier = Modifier.height(30.dp))
                LazyColumn(
                    state = rememberLazyListState(),
//                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight()
                        .padding(20.dp)


                ) {

                    item {
                        TextButton(
                            onClick = onProfile,
                        ) {
                            Row {
                                Icon(
                                    Icons.Outlined.Person,
                                    tint = Color.White,
                                    contentDescription = null,
                                    modifier = Modifier.size(25.dp),
                                )
                                Text(
                                    text = "Profile",
                                    color = Color.White,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 20.sp,
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis,
                                    modifier = Modifier
                                        .height(50.dp)
                                        .padding(start = 20.dp)
                                )
                            }
                        }
                    }
                    item {
                        TextButton(
                            onClick = { showDialog = true }
//                            onClick = onLogout,
                        ) {
                            Row {
                                Icon(
                                    Icons.Outlined.Logout,
                                    tint = Color.White,
                                    contentDescription = null,
                                )
                                Text(
                                    text = "Logout", color = Color.White,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 20.sp,
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis,
                                    modifier = Modifier
                                        .height(50.dp)
                                        .padding(start = 20.dp)
                                )
                            }
                        }

                    }
                }
            }
        },
        drawerState = drawerState
    ) {
        Column{
            AppBar(drawerState)
            MenuScreen(
                onQuizSelected = onQuizSelected,
            )
            // AlertDialog
            if (showDialog) {
                AlertDialog(
                    onDismissRequest = { showDialog = false }, // Triggered when clicking outside the dialog
                    title = {
                        Text(text = "Confirm Action")
                    },
                    text = {
                        Text(text = "Are you sure you want to Logout?")
                    },
                    confirmButton = {
                        Button(
                            onClick = onLogout, // This is now the correct type () -> Unit
                            colors = ButtonDefaults.buttonColors(containerColor = Purple40)
                        ) {
                            Text("Logout")
                        }
                    },
                    dismissButton = {
                        TextButton(
                            onClick = {
                                showDialog = false
                            }
                        ) {
                            Text("Dismiss", color = Orange)
                        }
                    }
                )
            }
        }
    }
}
