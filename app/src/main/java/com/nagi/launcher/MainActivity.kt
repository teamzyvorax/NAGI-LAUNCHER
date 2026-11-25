package com.nagi.launcher

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

private val Background = Color(0xFF09090D)
private val Panel = Color(0xFF17171F)
private val Red = Color(0xFFE53945)
private val Muted = Color(0xFFAAAAB5)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                NagiLauncherApp()
            }
        }
    }
}

@Composable
fun NagiLauncherApp() {
    var showMessage by remember { mutableStateOf(false) }
    var selectedTab by remember { mutableStateOf("Home") }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Background
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        listOf(Color(0xFF211015), Background, Background)
                    )
                )
                .padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(18.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text("NAGI", color = Color.White, fontSize = 28.sp, fontWeight = FontWeight.Black)
                    Text("JAVA EDITION LAUNCHER", color = Red, fontSize = 10.sp, fontWeight = FontWeight.Bold)
                }
                Box(
                    modifier = Modifier
                        .background(Color(0xFF29151A), RoundedCornerShape(50))
                        .padding(horizontal = 12.dp, vertical = 8.dp)
                ) {
                    Text("ANDROID • BETA", color = Color(0xFFFF858C), fontSize = 10.sp, fontWeight = FontWeight.Bold)
                }
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Panel, RoundedCornerShape(22.dp))
                    .padding(20.dp)
            ) {
                Text("WELCOME BACK", color = Muted, fontSize = 11.sp, fontWeight = FontWeight.Bold)
                Spacer(Modifier.height(8.dp))
                Text("Your next adventure\nstarts here.", color = Color.White, fontSize = 27.sp, fontWeight = FontWeight.ExtraBold, lineHeight = 32.sp)
                Spacer(Modifier.height(12.dp))
                Text("Manage Java Edition profiles, versions and mods from one place.", color = Muted, fontSize = 13.sp, lineHeight = 19.sp)
                Spacer(Modifier.height(20.dp))
                Button(
                    onClick = { showMessage = true },
                    modifier = Modifier.fillMaxWidth().height(52.dp),
                    shape = RoundedCornerShape(14.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Red)
                ) {
                    Text("PLAY MINECRAFT", fontWeight = FontWeight.ExtraBold, letterSpacing = 1.sp)
                }
                Spacer(Modifier.height(8.dp))
                Text("Game engine integration is the next development milestone.", color = Muted, fontSize = 10.sp)
            }

            Text("QUICK ACCESS", color = Color.White, fontSize = 13.sp, fontWeight = FontWeight.Bold)
            Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                QuickCard("VERSIONS", "Manage game builds", Modifier.weight(1f)) { selectedTab = "Versions" }
                QuickCard("MODS", "Loaders & add-ons", Modifier.weight(1f)) { selectedTab = "Mods" }
            }
            Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                QuickCard("ACCOUNTS", "Player profiles", Modifier.weight(1f)) { selectedTab = "Accounts" }
                QuickCard("SETTINGS", "Memory & controls", Modifier.weight(1f)) { selectedTab = "Settings" }
            }

            Spacer(Modifier.weight(1f))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                listOf("Home", "Versions", "Mods", "Settings").forEach { tab ->
                    TextButton(onClick = { selectedTab = tab }) {
                        Text(tab, color = if (selectedTab == tab) Red else Muted, fontWeight = FontWeight.Bold)
                    }
                }
            }
            Text("NAGI LAUNCHER  •  v0.1.0  •  UI PREVIEW", color = Color(0xFF666671), fontSize = 9.sp, modifier = Modifier.align(Alignment.CenterHorizontally))
        }
    }

    if (showMessage) {
        AlertDialog(
            onDismissRequest = { showMessage = false },
            containerColor = Panel,
            title = { Text("Minecraft engine not connected yet", color = Color.White) },
            text = { Text("This first build is the working launcher interface. Next we will add Android runtime, rendering and legitimate Minecraft account/game integration.", color = Muted) },
            confirmButton = {
                TextButton(onClick = { showMessage = false }) {
                    Text("GOT IT", color = Red, fontWeight = FontWeight.Bold)
                }
            }
        )
    }
}

@Composable
fun QuickCard(title: String, subtitle: String, modifier: Modifier = Modifier, onClick: () -> Unit) {
    Surface(
        onClick = onClick,
        modifier = modifier.height(94.dp),
        color = Panel,
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(
            modifier = Modifier.padding(14.dp),
            verticalArrangement = Arrangement.Center
        ) {
            Text(title, color = Color.White, fontWeight = FontWeight.ExtraBold, fontSize = 12.sp)
            Spacer(Modifier.height(5.dp))
            Text(subtitle, color = Muted, fontSize = 10.sp)
        }
    }
}
