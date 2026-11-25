package com.nagi.launcher

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
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
        setContent { MaterialTheme { NagiLauncherApp() } }
    }
}

@Composable
fun NagiLauncherApp() {
    val context = LocalContext.current
    var selectedTab by remember { mutableStateOf("Home") }
    var deviceInfo by remember { mutableStateOf<RuntimeDeviceInfo?>(null) }

    LaunchedEffect(Unit) { deviceInfo = RuntimeEnvironment.inspect(context) }

    Surface(Modifier.fillMaxSize(), color = Background) {
        Column(
            Modifier.fillMaxSize()
                .background(Brush.verticalGradient(listOf(Color(0xFF211015), Background, Background)))
                .verticalScroll(rememberScrollState()).padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                Column {
                    Text("NAGI", color = Color.White, fontSize = 28.sp, fontWeight = FontWeight.Black)
                    Text("JAVA EDITION LAUNCHER", color = Red, fontSize = 10.sp, fontWeight = FontWeight.Bold)
                }
                Text("ANDROID • BETA", color = Color(0xFFFF858C), fontSize = 10.sp, fontWeight = FontWeight.Bold)
            }

            when (selectedTab) {
                "Home" -> HomeScreen({ selectedTab = "Runtime" }, { selectedTab = it })
                "Runtime" -> RuntimeScreen(deviceInfo) { deviceInfo = RuntimeEnvironment.inspect(context) }
                "Versions" -> PlaceholderScreen("VERSION MANAGER", "Minecraft version selection and downloads will be connected after the Android runtime and game file manager are integrated.")
                "Mods" -> PlaceholderScreen("MOD MANAGER", "Fabric, Forge and mod installation are planned after the base Minecraft runtime is working.")
            }

            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                listOf("Home", "Versions", "Mods", "Runtime").forEach { tab ->
                    TextButton(onClick = { selectedTab = tab }) {
                        Text(tab, color = if (selectedTab == tab) Red else Muted, fontWeight = FontWeight.Bold, fontSize = 11.sp)
                    }
                }
            }
            Text("NAGI LAUNCHER  •  v0.2.0  •  RUNTIME FOUNDATION", color = Color(0xFF666671), fontSize = 9.sp, modifier = Modifier.align(Alignment.CenterHorizontally))
        }
    }
}

@Composable
private fun HomeScreen(onPlay: () -> Unit, onNavigate: (String) -> Unit) {
    Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
        Column(Modifier.fillMaxWidth().background(Panel, RoundedCornerShape(22.dp)).padding(20.dp)) {
            Text("WELCOME BACK", color = Muted, fontSize = 11.sp, fontWeight = FontWeight.Bold)
            Spacer(Modifier.height(8.dp))
            Text("Your next adventure starts here.", color = Color.White, fontSize = 27.sp, fontWeight = FontWeight.ExtraBold, lineHeight = 32.sp)
            Spacer(Modifier.height(12.dp))
            Text("The Android runtime foundation is being prepared.", color = Muted, fontSize = 13.sp)
            Spacer(Modifier.height(20.dp))
            Button(onClick = onPlay, modifier = Modifier.fillMaxWidth().height(52.dp), shape = RoundedCornerShape(14.dp), colors = ButtonDefaults.buttonColors(containerColor = Red)) {
                Text("OPEN RUNTIME SETUP", fontWeight = FontWeight.ExtraBold, letterSpacing = 1.sp)
            }
            Spacer(Modifier.height(8.dp))
            Text("Minecraft cannot launch until the Java runtime and renderer are integrated.", color = Muted, fontSize = 10.sp)
        }
        Text("QUICK ACCESS", color = Color.White, fontSize = 13.sp, fontWeight = FontWeight.Bold)
        Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
            QuickCard("VERSIONS", "Game builds", Modifier.weight(1f)) { onNavigate("Versions") }
            QuickCard("MODS", "Loaders & add-ons", Modifier.weight(1f)) { onNavigate("Mods") }
        }
        Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
            QuickCard("RUNTIME", "Device readiness", Modifier.weight(1f)) { onNavigate("Runtime") }
            QuickCard("SETTINGS", "Coming next", Modifier.weight(1f)) { onNavigate("Runtime") }
        }
    }
}

@Composable
private fun RuntimeScreen(info: RuntimeDeviceInfo?, onRefresh: () -> Unit) {
    Column(Modifier.fillMaxWidth().background(Panel, RoundedCornerShape(22.dp)).padding(20.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
        Text("ANDROID RUNTIME", color = Red, fontSize = 12.sp, fontWeight = FontWeight.Bold)
        Text("Device readiness", color = Color.White, fontSize = 25.sp, fontWeight = FontWeight.ExtraBold)
        Text("This check reads your device architecture, RAM and free app storage. It does not install Java or download Minecraft.", color = Muted, fontSize = 12.sp)
        if (info == null) {
            Text("Checking device…", color = Muted)
        } else {
            InfoLine("CPU architecture", info.primaryAbi)
            InfoLine("64-bit ARM support", if (info.supportsArm64) "Available" else "Not detected")
            InfoLine("Total device RAM", (info.totalRamMb / 1024).toString() + " GB")
            InfoLine("Free app storage", info.freeStorageMb.toString() + " MB")
            InfoLine("Game data folder", if (info.gameDirectory.exists()) "Prepared" else "Not ready")
            InfoLine("Runtime folder", if (info.runtimeDirectory.exists()) "Prepared" else "Not ready")
        }
        Text("RUNTIME ENGINE: NOT INSTALLED", color = Color(0xFFFF858C), fontSize = 12.sp, fontWeight = FontWeight.Bold)
        Text("Next: integrate a compatible Android Java runtime, LWJGL layer and graphics renderer. This requires native libraries as well as Java files.", color = Muted, fontSize = 11.sp)
        Button(onClick = onRefresh, modifier = Modifier.fillMaxWidth(), colors = ButtonDefaults.buttonColors(containerColor = Red)) {
            Text("REFRESH DEVICE CHECK", fontWeight = FontWeight.Bold)
        }
    }
}

@Composable
private fun InfoLine(label: String, value: String) {
    Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
        Text(label, color = Muted, fontSize = 11.sp)
        Text(value, color = Color.White, fontSize = 11.sp, fontWeight = FontWeight.Bold)
    }
}

@Composable
private fun PlaceholderScreen(title: String, description: String) {
    Column(Modifier.fillMaxWidth().background(Panel, RoundedCornerShape(22.dp)).padding(20.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
        Text(title, color = Red, fontSize = 12.sp, fontWeight = FontWeight.Bold)
        Text("Coming soon", color = Color.White, fontSize = 25.sp, fontWeight = FontWeight.ExtraBold)
        Text(description, color = Muted, fontSize = 13.sp, lineHeight = 19.sp)
    }
}

@Composable
fun QuickCard(title: String, subtitle: String, modifier: Modifier = Modifier, onClick: () -> Unit) {
    Surface(onClick = onClick, modifier = modifier.height(94.dp), color = Panel, shape = RoundedCornerShape(16.dp)) {
        Column(Modifier.padding(14.dp), verticalArrangement = Arrangement.Center) {
            Text(title, color = Color.White, fontWeight = FontWeight.ExtraBold, fontSize = 12.sp)
            Spacer(Modifier.height(5.dp))
            Text(subtitle, color = Muted, fontSize = 10.sp)
        }
    }
}
