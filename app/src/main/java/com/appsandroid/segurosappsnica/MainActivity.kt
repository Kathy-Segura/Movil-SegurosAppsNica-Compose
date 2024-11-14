@file:OptIn(ExperimentalFoundationApi::class)

package com.appsandroid.segurosappsnica

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Folder
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.PieChart
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.Typography
import androidx.compose.material3.lightColorScheme
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.appsandroid.segurosappsnica.login.LoginScreen
import com.appsandroid.segurosappsnica.modelo.LineChartData
import com.appsandroid.segurosappsnica.modelo.Seguro
import com.appsandroid.segurosappsnica.modelo.Siniestro
import com.appsandroid.segurosappsnica.network.AgentesRepository
import com.appsandroid.segurosappsnica.network.ClientesRepository
import com.appsandroid.segurosappsnica.network.PolizasRepository
import com.appsandroid.segurosappsnica.network.VentasRepository
import com.appsandroid.segurosappsnica.ui.theme.SegurosAppsNicaTheme
import kotlinx.coroutines.launch
import androidx.compose.animation.core.animateFloatAsState as animateFloatAsState1

/*class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            // Tema personalizado para la app
            SegurosAppsNicaTheme {
                // Superficie de fondo de la app
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    // Inicializamos el NavController para gestionar la navegación entre pantallas
                    val navController = rememberNavController()
                    MainScreen(navController = navController) //hasta aca antes del splasscreen
                }
            }
        }
    }
}


// CONFIGURACION DEL MENU Y NAVEGACION
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(navController: NavController) {
    // Estado del drawer (menú lateral) para manejar su visibilidad
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    // ModalNavigationDrawer para la implementación del menú lateral
    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            // Mostrar el contenido del drawer solo si está completamente abierto
            if (drawerState.isOpen) {
                DrawerContent(
                    navController = navController,
                    closeDrawer = { scope.launch { drawerState.close() } }
                )
            }
        }
    ) {
        Scaffold(
            // Barra superior con botón para abrir el menú
            topBar = {
                AppTopBar(onMenuClick = { scope.launch { drawerState.open() } })
            },
            // Barra inferior con derechos de autor
            bottomBar = {
                BottomAppBar(
                    containerColor = Color(0xFF6200EE)
                ) {
                    Text(
                        text = "© 2024 Seguros Apps Nica",
                        color = Color.White,
                        modifier = Modifier.padding(start = 16.dp)
                    )
                }
            },
            containerColor = Color.White
        ) { innerPadding ->
            // Configuración de la navegación entre pantallas
            NavHost(
                navController = navController as NavHostController,
                startDestination = "home",
                Modifier.padding(innerPadding)
            ) {
                try {
                composable("home") { HomeScreen(navController) }
                composable("menu") { MenuScreen() }
                composable("grafico1") { Grafico1Screen() }
                composable("grafico2") { Grafico2Screen() }
                composable("grafico3") { Grafico3Screen() }
                composable("grafico4") { Grafico4Screen() }
                composable("coleccion1") { Coleccion1Screen() } // Pantalla de colección 1
                composable("coleccion2") { Coleccion2Screen() } // Pantalla de colección 2
                // Agregar aquí nuevas pantallas o modificar las existentes si es necesario.
            }catch (e: Exception){
                println("Error al cargar la pantalla: ${e.message}")
            }

            }
        }
    }
}*/


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            // Tema personalizado para la app
            SegurosAppsNicaTheme {
                // Superficie de fondo de la app
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    // Inicializamos el NavController para gestionar la navegación entre pantallas
                    val navController = rememberNavController()
                    MainScreen(navController = navController)
                }
            }
        }
    }
}

// CONFIGURACIÓN DEL MENÚ Y NAVEGACIÓN
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(navController: NavController) {
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            if (drawerState.isOpen) {
                DrawerContent(
                    navController = navController,
                    closeDrawer = { scope.launch { drawerState.close() } }
                )
            }
        }
    ) {
        Scaffold(
            topBar = {
                AppTopBar(onMenuClick = { scope.launch { drawerState.open() } })
            },
            bottomBar = {
                BottomAppBar(
                    containerColor = Color(0xFF6200EE)
                ) {
                    Text(
                        text = "© 2024 Seguros Apps Nica",
                        color = Color.White,
                        modifier = Modifier.padding(start = 16.dp)
                    )
                }
            },
            containerColor = Color.White
        ) { innerPadding ->
            NavHost(
                navController = navController as NavHostController,
                startDestination = "login",
                Modifier.padding(innerPadding)
            ) {
                composable("login") { LoginScreen(navController) }
                composable("home") { HomeScreen(navController) }
                composable("menu") { MenuScreen() }
                composable("grafico1") { Grafico1Screen() }
                composable("grafico2") { Grafico2Screen() }
                composable("grafico3") { Grafico3Screen() }
                composable("grafico4") { Grafico4Screen() }
                composable("coleccion1") { Coleccion1Screen() }
                composable("coleccion2") { Coleccion2Screen() }
            }
        }
    }
}


//NAVBAR
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppTopBar(onMenuClick: () -> Unit) {
    // Barra superior con título de la app y botón de menú
    CenterAlignedTopAppBar(
        title = {
            Text(
                "Seguros Nicaragua S.A",
                style = MaterialTheme.typography.titleLarge.copy(color = Color.White, fontWeight = FontWeight.Bold)
            )
        },
        navigationIcon = {
            IconButton(onClick = onMenuClick) {
                Icon(Icons.Default.Menu, contentDescription = "Menú", tint = Color.White)
            }
        },
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(containerColor = Color(0xFF6200EE))
    )
}

//MENU
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DrawerContent(navController: NavController, closeDrawer: () -> Unit) {
    val currentRoute = navController.currentBackStackEntry?.destination?.route

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF5F5F5)) // Fondo suave para el drawer
            .padding(16.dp)
    ) {
        // Encabezado del menú lateral con detalles del usuario
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFF6200EE), shape = RoundedCornerShape(8.dp))
                .padding(16.dp)
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Image(
                    painter = painterResource(id = R.drawable.seguros_nic), // Imagen de perfil
                    contentDescription = "Profile Picture",
                    modifier = Modifier
                        .size(72.dp)
                        .clip(CircleShape)
                        .border(2.dp, Color.White, CircleShape)
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Seguros Nic.",
                    color = Color.White,
                    style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold)
                )
                Text(
                    text = "Seguridad que inspira confianza.",
                    color = Color.White.copy(alpha = 0.7f),
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }

        Spacer(modifier = Modifier.height(32.dp))

        // Opciones del menú lateral con íconos y navegación
        DrawerMenuItem("Inicio", Icons.Default.Home, Color(0xFF03DAC5), currentRoute == "home") {
            navController.navigate("home") { launchSingleTop = true }
            closeDrawer()
        }
        DrawerMenuItem("Gráfico 1", Icons.Default.PieChart, Color(0xFF6200EE), currentRoute == "grafico1") {
            navController.navigate("grafico1") { launchSingleTop = true }
            closeDrawer()
        }
        DrawerMenuItem("Gráfico 2", Icons.Default.PieChart, Color(0xFFFFA500), currentRoute == "grafico2") {
            navController.navigate("grafico2") { launchSingleTop = true }
            closeDrawer()
        }
        DrawerMenuItem("Gráfico 3", Icons.Default.PieChart, Color(0xFFA0D67C), currentRoute == "grafico3") {
            navController.navigate("grafico3") { launchSingleTop = true }
            closeDrawer()
        }
        DrawerMenuItem("Gráfico 4", Icons.Default.PieChart, Color(0xFF6200EE), currentRoute == "grafico4") {
            navController.navigate("grafico4") { launchSingleTop = true }
            closeDrawer()
        }

        // Divisor entre secciones del menú
        Divider(modifier = Modifier.padding(vertical = 16.dp))

        // Sección de "Colecciones" en el menú lateral
        Text(
            text = "Colecciones",
            style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold, color = Color.Gray),
            modifier = Modifier.padding(start = 16.dp, bottom = 8.dp)
        )

        DrawerMenuItem("Colección 1", Icons.Default.Folder, Color(0xFF6200EE), currentRoute == "coleccion1") {
            navController.navigate("coleccion1") { launchSingleTop = true }
            closeDrawer()
        }
        DrawerMenuItem("Colección 2", Icons.Default.Folder, Color(0xFF6200EE), currentRoute == "coleccion2") {
            navController.navigate("coleccion2") { launchSingleTop = true }
            closeDrawer()
        }
    }
}

// Componente para cada elemento del menú lateral con selección de pantalla actual
@Composable
fun DrawerMenuItem(
    text: String,
    icon: ImageVector,
    iconColor: Color,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .clickable(onClick = onClick)
            .background(
                color = if (isSelected) iconColor.copy(alpha = 0.2f) else Color.Transparent,
                shape = RoundedCornerShape(8.dp)
            )
            .padding(horizontal = 16.dp, vertical = 12.dp)
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = if (isSelected) iconColor else Color.Gray,
            modifier = Modifier.size(24.dp)
        )
        Spacer(modifier = Modifier.width(16.dp))
        Text(
            text = text,
            style = MaterialTheme.typography.bodyMedium.copy(
                color = if (isSelected) iconColor else Color.Black,
                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
            )
        )
    }
}

// Pantalla de inicio (Home)
@Composable
fun HomeScreen(navController: NavController) {
    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        Text(
            text = "Bienvenido a Seguros Nic",
            style = MaterialTheme.typography.headlineLarge.copy(fontSize = 26.sp, fontWeight = FontWeight.Bold, color = Color(0xFF6200EE)),
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(8.dp)
        )
        Text(
            text = "Explora los datos y gráficos de ventas de seguros.",
            style = MaterialTheme.typography.bodyLarge.copy(color = Color.Gray),
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(8.dp)
        )
        Spacer(modifier = Modifier.height(16.dp))
        Image(
            painter = painterResource(id = R.drawable.baseline_security_24),
            contentDescription = "Vista previa de gráficos",
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .clip(RoundedCornerShape(8.dp))
                .background(Color.White)
        )
    }
}

//=================================================================================================================//
  // DEFINICION DE FUNCIONES PARA LOS GRAFICOS Y SUS PROPIEDADES
//=================================================================================================================//


@Composable fun MenuScreen() { /* Implementar detalles aquí */ }
//@Composable fun Grafico1Screen() { /* Implementar detalles aquí */ }
//@Composable fun Grafico2Screen() { /* Implementar detalles aquí */ }
//@Composable fun Grafico3Screen() { /* Implementar detalles aquí */ }
//@Composable fun Grafico4Screen() { /* Implementar detalles aquí */ }
//@Composable fun Coleccion1Screen() { /* Implementar detalles aquí */ }
//@Composable fun Coleccion2Screen() { /* Implementar detalles aquí */ }

//----------------------------------------//
//GRAFICO 1 AGENTES X VENTAS TOTALES
//----------------------------------------//
@Composable
fun Grafico1Screen() {
    var valores by remember { mutableStateOf<List<Float>>(emptyList()) }
    var nombres by remember { mutableStateOf<List<String>>(emptyList()) }
    var colores by remember { mutableStateOf<List<Color>>(emptyList()) }
    var isLoading by remember { mutableStateOf(true) }
    var error by remember { mutableStateOf<String?>(null) }

    val coroutineScope = rememberCoroutineScope()

    // Cargar datos desde la API
    LaunchedEffect(Unit) {
        coroutineScope.launch {
            VentasRepository().obtenerVentasPorAgente(
                onResult = { datos ->

                    if (datos.isNotEmpty()) {
                        // Mapear los valores a listas de Float y String
                        valores = datos.map { it.totalVentas?.toFloat() ?: 0f }  // Si totalVentas es null, usar 0f
                        nombres = datos.map { it.nombreAgente ?: "Desconocido" }  // Si nombreAgente es null, usar "Desconocido"

                        // Asignar colores estáticos (puedes personalizar esta lista)
                        colores = listOf(
                            Color(0xFFBF72F2),  // Color #BF72F2
                            Color(0xFF5005F2),  // Color #5005F2
                            Color(0xFF615EBF),  // Color #615EBF
                            Color(0xFF04ADBF),  // Color #04ADBF
                            Color(0xFF3FA663)   // Color #3FA663
                        ).take(datos.size)  // Asegurarse de que la cantidad de colores coincida con los datos
                    } else {
                        error = "No hay datos disponibles"
                    }
                    isLoading = false
                },
                onError = { throwable ->
                    error = throwable.message
                    isLoading = false
                }
            )
        }
    }

    // UI
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Ventas por Agente",
            style = MaterialTheme.typography.headlineSmall.copy(
                fontWeight = FontWeight.Bold,
                color = Color(0xFF6200EE)
            )
        )
        Spacer(modifier = Modifier.height(16.dp))

        when {
            isLoading -> Text("Cargando datos...")
            error != null -> Text("Error: $error")
            valores.isNotEmpty() && colores.isNotEmpty() -> {
                GraficoCircular(valores = valores, colores = colores, nombres = nombres, tamaño = 250.dp)
            }
            else -> Text("No se encontraron datos")
        }
    }
}

@Composable
fun GraficoCircular(
    valores: List<Float>,
    colores: List<Color>,
    nombres: List<String>,
    tamaño: Dp = 250.dp
) {
    val sumaTotal = valores.sum()
    var inicioAngulo by remember { mutableStateOf(0f) }

    // Animación para el barrido del gráfico
    val animatedAngles = valores.mapIndexed { index, valor ->
        val anguloBarrido = (valor / sumaTotal) * 360f
        // Animamos el ángulo de barrido de cada segmento
        animateFloatAsState1(
            targetValue = anguloBarrido,
            animationSpec = tween(durationMillis = 1000, easing = LinearEasing)
        ).value
    }

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.size(tamaño)
    ) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            var currentStartAngle = 0f

            // Dibujar los segmentos del gráfico circular con animación
            animatedAngles.forEachIndexed { index, animatedSweepAngle ->
                drawArc(
                    color = colores.getOrElse(index) { Color.Gray },
                    startAngle = currentStartAngle,
                    sweepAngle = animatedSweepAngle,
                    useCenter = false, // Centro vacío para hacerlo un gráfico de dona
                    size = Size(size.width, size.height),
                    style = Stroke(width = 60f) // Borde más grueso para el gráfico de dona
                )
                currentStartAngle += animatedSweepAngle
            }
        }

        // Añadir texto en el centro o sobre el gráfico
        Text(
            text = "Total: ${"%.2f".format(sumaTotal)}",
            style = MaterialTheme.typography.bodyLarge.copy(
                fontWeight = FontWeight.Bold,
                color = Color(0xFF6200EE)
            )
        )
    }

    // Añadir etiquetas con los nombres y las ventas
    Column(
        modifier = Modifier
            .padding(top = 16.dp)
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        valores.forEachIndexed { index, valor ->
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(vertical = 4.dp)
            ) {
                Box(
                    modifier = Modifier
                        .size(20.dp)
                        .background(colores.getOrElse(index) { Color.Gray })
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "${nombres[index]}: ${"%.2f".format(valor)}",
                    style = MaterialTheme.typography.bodyMedium.copy(color = Color.Black)
                )
            }
        }
    }
}

//----------------------------------------//
//GRAFICO 2 POLIZAS X VENTAS TOTALES
//----------------------------------------//

@Composable
fun Grafico2Screen() {
    var valores by remember { mutableStateOf<List<Float>>(emptyList()) }
    var nombres by remember { mutableStateOf<List<String>>(emptyList()) }
    var isLoading by remember { mutableStateOf(true) }
    var error by remember { mutableStateOf<String?>(null) }

    val coroutineScope = rememberCoroutineScope()
    val repository = PolizasRepository()

    // Cargar datos desde la API
    LaunchedEffect(Unit) {
        coroutineScope.launch {
            repository.obtenerVentasPorPoliza(
                onResult = { datos: List<LineChartData> ->
                    if (datos.isNotEmpty()) {
                        // Mapear los valores a listas de Float y String
                        valores = datos.map { it.totalVentas }
                        nombres = datos.map { it.nombrePoliza }
                    } else {
                        error = "No hay datos disponibles"
                    }
                    isLoading = false
                },
                onError = { throwable: Throwable ->
                    error = throwable.message
                    isLoading = false
                }
            )
        }
    }

    // UI
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Ventas por Póliza",
            style = MaterialTheme.typography.headlineSmall.copy(
                fontWeight = FontWeight.Bold,
                color = Color(0xFF6200EE)
            )
        )
        Spacer(modifier = Modifier.height(16.dp))

        when {
            isLoading -> Text("Cargando datos...")
            error != null -> Text("Error: $error")
            valores.isNotEmpty() -> {
                LineChart(valores = valores, nombres = nombres)
            }
            else -> Text("No se encontraron datos")
        }
    }
}


@Composable
fun LineChart(
    valores: List<Float>,
    nombres: List<String>,
    tamaño: Dp = 250.dp
) {
    val maxValue = valores.maxOrNull() ?: 1f
    val animatedValues = valores.map { value ->
        animateFloatAsState1(
            targetValue = value,
            animationSpec = tween(durationMillis = 1000, easing = LinearOutSlowInEasing)
        ).value
    }

    Canvas(
        modifier = Modifier
            .fillMaxWidth()
            .height(tamaño)
            .padding(vertical = 16.dp)
    ) {
        val spaceBetweenPoints = size.width / (animatedValues.size - 1)

        // Dibujar el fondo del área
        val path = androidx.compose.ui.graphics.Path().apply {
            moveTo(0f, size.height)
            animatedValues.forEachIndexed { index, value ->
                val x = index * spaceBetweenPoints
                val y = size.height - (value / maxValue * size.height)
                lineTo(x, y)
            }
            lineTo(size.width, size.height)
            close()
        }

        drawPath(
            path = path,
            color = Color(0xFF04ADBF).copy(alpha = 0.3f) // Fondo con color turquesa claro
        )

        // Dibujar la línea del gráfico
        for (i in 0 until animatedValues.size - 1) {
            val startX = i * spaceBetweenPoints
            val startY = size.height - (animatedValues[i] / maxValue * size.height)
            val endX = (i + 1) * spaceBetweenPoints
            val endY = size.height - (animatedValues[i + 1] / maxValue * size.height)

            drawLine(
                color = Color(0xFF04ADBF), // Color turquesa
                start = Offset(startX, startY),
                end = Offset(endX, endY),
                strokeWidth = 4f,
                cap = StrokeCap.Round
            )
        }

        // Dibujar los puntos del gráfico y las etiquetas
        animatedValues.forEachIndexed { index, value ->
            val x = index * spaceBetweenPoints
            val y = size.height - (value / maxValue * size.height)

            // Dibujar el punto
            drawCircle(
                color = Color(0xFF04ADBF),
                radius = 8f,
                center = Offset(x, y)
            )

            // Dibujar la etiqueta del valor
            drawIntoCanvas { canvas ->
                val textPaint = android.graphics.Paint().apply {
                    color = android.graphics.Color.BLACK
                    textSize = 28f
                    textAlign = android.graphics.Paint.Align.CENTER
                }
                canvas.nativeCanvas.drawText(
                    String.format("%,d", value.toInt()),
                    x,
                    y - 20.dp.toPx(), // Ajusta la posición para que esté encima del punto
                    textPaint
                )
            }
        }
    }

    // Etiquetas debajo del gráfico
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        nombres.forEach { nombre ->
            Text(
                text = nombre,
                style = MaterialTheme.typography.labelSmall,
                color = Color.Gray,
                modifier = Modifier.width(tamaño / nombres.size)
            )
        }
    }
}


//----------------------------------------//
//GRAFICO 3 VENTAS TOTALES X MES en 2023
//----------------------------------------//

@Composable
fun Grafico3Screen() {
    var valores by remember { mutableStateOf<List<Float>>(emptyList()) }
    var nombres by remember { mutableStateOf<List<String>>(emptyList()) }
    var isLoading by remember { mutableStateOf(true) }
    var error by remember { mutableStateOf<String?>(null) }

    val coroutineScope = rememberCoroutineScope()

    // Cargar datos desde la API
    LaunchedEffect(Unit) {
        coroutineScope.launch {
            ClientesRepository().obtenerVentasPorMes(
                onResult = { datos ->
                    if (datos.isNotEmpty()) {
                        // Mapear valores y nombres
                        valores = datos.map { it.totalVentas }
                        nombres = datos.map { it.monthName }
                    } else {
                        error = "No hay datos disponibles"
                    }
                    isLoading = false
                },
                onError = { throwable ->
                    error = throwable.message
                    isLoading = false
                }
            )
        }
    }

    // UI
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Ventas por Mes 2023",
            style = MaterialTheme.typography.headlineSmall.copy(
                fontWeight = FontWeight.Bold,
                color = Color(0xFF6200EE)
            )
        )
        Spacer(modifier = Modifier.height(16.dp))

        when {
            isLoading -> Text("Cargando datos...")
            error != null -> Text("Error: $error")
            valores.isNotEmpty() -> {
                GraficoArea(valores = valores, nombres = nombres)
            }
            else -> Text("No se encontraron datos")
        }
    }
}


@Composable
fun GraficoArea(valores: List<Float>, nombres: List<String>) {
    val maxValor = valores.maxOrNull() ?: 1f
    val minValor = valores.minOrNull() ?: 0f

    Canvas(modifier = Modifier
        .fillMaxWidth()
        .height(300.dp)
        .padding(16.dp)
    ) {
        val espacioEntrePuntos = size.width / (valores.size - 1)

        // Crear el camino para el área
        val path = androidx.compose.ui.graphics.Path().apply {
            moveTo(0f, size.height)
            valores.forEachIndexed { index, valor ->
                val x = index * espacioEntrePuntos
                val y = size.height - (valor / maxValor) * size.height
                lineTo(x, y)
            }
            lineTo(size.width, size.height)
            close()
        }

        // Dibujar el área con relleno lila semi-transparente
        drawPath(
            path = path,
            color = Color(0xFF6200EE).copy(alpha = 0.3f) // Color lila con transparencia
        )

        // Crear el camino para la línea del gráfico
        val linePath = androidx.compose.ui.graphics.Path().apply {
            moveTo(0f, size.height - (valores.first() / maxValor) * size.height)
            valores.forEachIndexed { index, valor ->
                val x = index * espacioEntrePuntos
                val y = size.height - (valor / maxValor) * size.height
                lineTo(x, y)
            }
        }

        // Dibujar la línea encima del área
        drawPath(
            path = linePath,
            color = Color(0xFF6200EE), // Color lila
            style = Stroke(width = 3.dp.toPx())
        )

        // Dibujar los puntos, las etiquetas de valores y los nombres de los meses
        valores.forEachIndexed { index, valor ->
            val x = index * espacioEntrePuntos
            val y = size.height - (valor / maxValor) * size.height

            // Dibujar un círculo en cada punto
            drawCircle(
                color = Color(0xFF6200EE), // Color lila
                radius = 6.dp.toPx(),
                center = Offset(x, y)
            )

            // Dibujar la etiqueta del valor encima de cada punto
            drawIntoCanvas { canvas ->
                val textPaint = android.graphics.Paint().apply {
                    color = android.graphics.Color.BLACK
                    textSize = 28f
                    textAlign = android.graphics.Paint.Align.CENTER
                }
                canvas.nativeCanvas.drawText(
                    valor.toInt().toString(),
                    x,
                    y - 10.dp.toPx(),
                    textPaint
                )
            }

            // Dibujar el nombre del mes debajo de cada punto en el eje X
            drawIntoCanvas { canvas ->
                val monthPaint = android.graphics.Paint().apply {
                    color = android.graphics.Color.DKGRAY
                    textSize = 24f
                    textAlign = android.graphics.Paint.Align.CENTER
                }
                canvas.nativeCanvas.drawText(
                    nombres.getOrNull(index) ?: "", // Obtener el nombre del mes o dejar vacío si falta
                    x,
                    size.height + 20.dp.toPx(), // Colocar el texto un poco debajo del gráfico
                    monthPaint
                )
            }
        }
    }
}


//----------------------------------------//
//GRAFICO 4 AGENTES X COMISIION
//----------------------------------------//

@Composable
fun Grafico4Screen() {
    var comisiones by remember { mutableStateOf<List<Float>>(emptyList()) }
    var precios by remember { mutableStateOf<List<Float>>(emptyList()) }
    var nombresAgentes by remember { mutableStateOf<List<String>>(emptyList()) }
    var isLoading by remember { mutableStateOf(true) }
    var error by remember { mutableStateOf<String?>(null) }

    val coroutineScope = rememberCoroutineScope()

    // Cargar datos desde la API
    LaunchedEffect(Unit) {
        coroutineScope.launch {
            AgentesRepository().obtenerComisionesPorAgente(
                onResult = { datos ->
                    if (datos.isNotEmpty()) {
                        // Mapear comisiones, precios y nombres de agentes
                        comisiones = datos.map { it.montoComisionTotal }
                        precios = datos.map { it.precioTotal }
                        nombresAgentes = datos.map { it.nombreAgente }
                    } else {
                        error = "No hay datos disponibles"
                    }
                    isLoading = false
                },
                onError = { throwable ->
                    error = throwable.message
                    isLoading = false
                }
            )
        }
    }

    // UI
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Comisiones por Agente",
            style = MaterialTheme.typography.headlineSmall.copy(
                fontWeight = FontWeight.Bold,
                color = Color(0xFF6200EE)
            )
        )
        Spacer(modifier = Modifier.height(16.dp))

        when {
            isLoading -> Text("Cargando datos...")
            error != null -> Text("Error: $error")
            comisiones.isNotEmpty() && precios.isNotEmpty() -> {
                GraficoBarras(comisiones = comisiones, precios = precios, nombresAgentes = nombresAgentes)
            }
            else -> Text("No se encontraron datos")
        }
    }
}


@Composable
fun GraficoBarras(
    comisiones: List<Float>,
    precios: List<Float>,
    nombresAgentes: List<String>
) {
    val maxValor = (comisiones + precios).maxOrNull() ?: 1f

    Canvas(
        modifier = Modifier
            .fillMaxWidth()
            .height(300.dp)
            .padding(16.dp)
    ) {
        val espacioEntreBarras = size.width / (comisiones.size * 2 + 1) // Ajustar el espacio entre pares de barras
        val anchoBarra = espacioEntreBarras * 0.8f // Definir el ancho de cada barra

        val textPaint = Paint().asFrameworkPaint().apply {
            isAntiAlias = true
            textSize = 30f
            textAlign = android.graphics.Paint.Align.CENTER
            color = android.graphics.Color.BLACK
        }

        comisiones.forEachIndexed { index, comision ->
            val xOffset = index * 2 * espacioEntreBarras + espacioEntreBarras

            // Barra de comisiones en azul con bordes redondeados
            val alturaComision = (comision / maxValor) * size.height
            drawRoundRect(
                color = Color(0xFF2196F3),
                topLeft = Offset(xOffset, size.height - alturaComision),
                size = androidx.compose.ui.geometry.Size(anchoBarra, alturaComision),
                cornerRadius = CornerRadius(8f, 8f) // Bordes redondeados
            )

            // Etiqueta de comisiones encima de la barra
            drawContext.canvas.nativeCanvas.drawText(
                comision.toString(),
                xOffset + anchoBarra / 2,
                size.height - alturaComision - 8.dp.toPx(),
                textPaint
            )

            // Barra de precios en verde con bordes redondeados, junto a la barra de comisiones
            val alturaPrecio = (precios[index] / maxValor) * size.height
            drawRoundRect(
                color = Color(0xFF4CAF50),
                topLeft = Offset(xOffset + anchoBarra + espacioEntreBarras * 0.2f, size.height - alturaPrecio),
                size = androidx.compose.ui.geometry.Size(anchoBarra, alturaPrecio),
                cornerRadius = CornerRadius(8f, 8f)
            )

            // Etiqueta de precios encima de la barra
            drawContext.canvas.nativeCanvas.drawText(
                precios[index].toString(),
                xOffset + anchoBarra * 1.5f + espacioEntreBarras * 0.2f,
                size.height - alturaPrecio - 8.dp.toPx(),
                textPaint
            )
        }
    }

    // Etiquetas de nombres de agentes debajo de cada par de barras
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        nombresAgentes.forEach { nombre ->
            Text(
                text = nombre,
                style = MaterialTheme.typography.bodySmall,
                color = Color.Gray,
                modifier = Modifier.width(50.dp),
                textAlign = TextAlign.Center
            )
        }
    }
}

//=================================================================================================================//
// TABLAS DE LA API DE MONGO
//=================================================================================================================//

//----------------------------------------//
//-------- Tabla #1 Polizas ---------//
//----------------------------------------//

/*@Composable
fun Coleccion1Screen() {
    val viewModel: SeguroViewModel = viewModel() // Obtén la instancia de ViewModel

    // Llamar a la API cuando se entra en esta pantalla
    LaunchedEffect(Unit) {
        viewModel.fetchSeguro() // Llamada a la API
    }

    // Mostrar los datos obtenidos de la API
    if (viewModel.isLoading) {
        CircularProgressIndicator(modifier = Modifier.padding(16.dp)) // Indicador de carga
    } else if (viewModel.errorMessage != null) {
        // Mostrar el mensaje de error si ocurrió uno
        Text(
            text = viewModel.errorMessage ?: "Error desconocido",
            color = Color.Red, // Puedes personalizar el color del mensaje de error
            modifier = Modifier.padding(16.dp)
        )
    } else {
        // Si no hay error y los datos están disponibles
        viewModel.seguro?.let { seguro ->
            // Contenedor principal con scroll vertical para toda la pantalla
            Column(modifier = Modifier
                .verticalScroll(rememberScrollState())
                .padding(16.dp) // Margen alrededor de toda la pantalla
            ) {
                // Contenedor de la tabla con scroll horizontal para los primeros datos
                Row(
                    modifier = Modifier
                        .horizontalScroll(rememberScrollState()) // Permite el desplazamiento horizontal
                        .fillMaxWidth()
                ) {
                    // Cabecera de la tabla con bordes
                    Column(
                        modifier = Modifier
                            .border(1.dp, Color.Gray)
                            .padding(8.dp)
                    ) {
                        Text("ID", fontWeight = FontWeight.Bold, modifier = Modifier.padding(8.dp))
                        Text("Tipo", fontWeight = FontWeight.Bold, modifier = Modifier.padding(8.dp))
                        Text("Prima mensual", fontWeight = FontWeight.Bold, modifier = Modifier.padding(8.dp))
                        Text("Fecha inicio", fontWeight = FontWeight.Bold, modifier = Modifier.padding(8.dp))
                        Text("Fecha fin", fontWeight = FontWeight.Bold, modifier = Modifier.padding(8.dp))
                    }

                    // Fila de datos con bordes
                    Column(
                        modifier = Modifier
                            .border(1.dp, Color.Gray)
                            .padding(8.dp)
                    ) {
                        Text(seguro._id, modifier = Modifier.padding(8.dp))
                        Text(seguro.tipo, modifier = Modifier.padding(8.dp))
                        Text("${seguro.prima_mensual}", modifier = Modifier.padding(8.dp))
                        Text(seguro.fecha_inicio, modifier = Modifier.padding(8.dp))
                        Text(seguro.fecha_fin, modifier = Modifier.padding(8.dp))
                    }
                }

                // Mostrar coberturas en un Scroll Vertical
                Spacer(modifier = Modifier.height(16.dp))
                Text("Coberturas:", fontWeight = FontWeight.Bold, modifier = Modifier.padding(8.dp))

                // Mostrar coberturas
                Row(modifier = Modifier.fillMaxWidth().border(1.dp, Color.Gray)) {
                    Text("Incendio:", fontWeight = FontWeight.Bold, modifier = Modifier.weight(1f).padding(8.dp))
                    Text("${seguro.coberturas.incendio}", modifier = Modifier.weight(1f).padding(8.dp))
                }

                Row(modifier = Modifier.fillMaxWidth().border(1.dp, Color.Gray)) {
                    Text("Robo:", fontWeight = FontWeight.Bold, modifier = Modifier.weight(1f).padding(8.dp))
                    Text("${seguro.coberturas.robo}", modifier = Modifier.weight(1f).padding(8.dp))
                }

                Row(modifier = Modifier.fillMaxWidth().border(1.dp, Color.Gray)) {
                    Text("Terremoto:", fontWeight = FontWeight.Bold, modifier = Modifier.weight(1f).padding(8.dp))
                    Text("${seguro.coberturas.terremoto}", modifier = Modifier.weight(1f).padding(8.dp))
                }

                // Mostrar modificaciones
                if (seguro.modificaciones.isNotEmpty()) {
                    Spacer(modifier = Modifier.height(8.dp))
                    Text("Modificaciones:", fontWeight = FontWeight.Bold, modifier = Modifier.padding(8.dp))

                    seguro.modificaciones.forEach { modificacion ->
                        Row(modifier = Modifier.fillMaxWidth().border(1.dp, Color.Gray)) {
                            Text("Fecha:", fontWeight = FontWeight.Bold, modifier = Modifier.weight(1f).padding(8.dp))
                            Text(modificacion.fecha, modifier = Modifier.weight(1f).padding(8.dp))
                        }

                        Row(modifier = Modifier.fillMaxWidth().border(1.dp, Color.Gray)) {
                            Text("Detalles:", fontWeight = FontWeight.Bold, modifier = Modifier.weight(1f).padding(8.dp))
                            Text(modificacion.detalles, modifier = Modifier.weight(1f).padding(8.dp))
                        }
                    }
                }
            }
        }
    }
}*/


@Composable
fun Coleccion1Screen() {
    val viewModel: SeguroViewModel = viewModel() // Obtén la instancia de ViewModel

    // Llamar a la API cuando se entra en esta pantalla
    LaunchedEffect(Unit) {
        viewModel.fetchSeguro() // Llamada a la API
    }

    // Mostrar los datos obtenidos de la API
    if (viewModel.isLoading) {
        CircularProgressIndicator(modifier = Modifier.padding(16.dp)) // Indicador de carga
    } else if (viewModel.errorMessage != null) {
        // Mostrar el mensaje de error si ocurrió uno
        Text(
            text = viewModel.errorMessage ?: "Error desconocido",
            color = Color.Red, // Puedes personalizar el color del mensaje de error
            modifier = Modifier.padding(16.dp)
        )
    } else {
        // Si no hay error y los datos están disponibles
        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .padding(16.dp) // Margen alrededor de toda la pantalla
        ) {
            viewModel.seguros.forEach { seguro ->
                // Contenedor de cada seguro con scroll horizontal para la tabla de datos
                Column(
                    modifier = Modifier
                        .padding(bottom = 16.dp)
                        .border(1.dp, Color.LightGray)
                        .fillMaxWidth()
                        .padding(8.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .horizontalScroll(rememberScrollState()) // Permite el desplazamiento horizontal
                            .fillMaxWidth()
                    ) {
                        // Cabecera de la tabla con bordes
                        Column(
                            modifier = Modifier
                                .border(1.dp, Color.Gray)
                                .padding(8.dp)
                        ) {
                            Text("ID", fontWeight = FontWeight.Bold, modifier = Modifier.padding(8.dp))
                            Text("Tipo", fontWeight = FontWeight.Bold, modifier = Modifier.padding(8.dp))
                            Text("Prima mensual", fontWeight = FontWeight.Bold, modifier = Modifier.padding(8.dp))
                            Text("Fecha inicio", fontWeight = FontWeight.Bold, modifier = Modifier.padding(8.dp))
                            Text("Fecha fin", fontWeight = FontWeight.Bold, modifier = Modifier.padding(8.dp))
                        }

                        // Fila de datos con bordes
                        Column(
                            modifier = Modifier
                                .border(1.dp, Color.Gray)
                                .padding(8.dp)
                        ) {
                            Text(seguro._id, modifier = Modifier.padding(8.dp))
                            Text(seguro.tipo, modifier = Modifier.padding(8.dp))
                            Text("${seguro.prima_mensual}", modifier = Modifier.padding(8.dp))
                            Text(seguro.fecha_inicio, modifier = Modifier.padding(8.dp))
                            Text(seguro.fecha_fin, modifier = Modifier.padding(8.dp))
                        }
                    }

                    // Mostrar coberturas en un Scroll Vertical
                    Spacer(modifier = Modifier.height(16.dp))
                    Text("Coberturas:", fontWeight = FontWeight.Bold, modifier = Modifier.padding(8.dp))

                    Row(modifier = Modifier.fillMaxWidth().border(1.dp, Color.Gray)) {
                        Text("Incendio:", fontWeight = FontWeight.Bold, modifier = Modifier.weight(1f).padding(8.dp))
                        Text("${seguro.coberturas.incendio}", modifier = Modifier.weight(1f).padding(8.dp))
                    }

                    Row(modifier = Modifier.fillMaxWidth().border(1.dp, Color.Gray)) {
                        Text("Robo:", fontWeight = FontWeight.Bold, modifier = Modifier.weight(1f).padding(8.dp))
                        Text("${seguro.coberturas.robo}", modifier = Modifier.weight(1f).padding(8.dp))
                    }

                    Row(modifier = Modifier.fillMaxWidth().border(1.dp, Color.Gray)) {
                        Text("Terremoto:", fontWeight = FontWeight.Bold, modifier = Modifier.weight(1f).padding(8.dp))
                        Text("${seguro.coberturas.terremoto}", modifier = Modifier.weight(1f).padding(8.dp))
                    }

                    // Mostrar modificaciones
                    if (seguro.modificaciones.isNotEmpty()) {
                        Spacer(modifier = Modifier.height(8.dp))
                        Text("Modificaciones:", fontWeight = FontWeight.Bold, modifier = Modifier.padding(8.dp))

                        seguro.modificaciones.forEach { modificacion ->
                            Row(modifier = Modifier.fillMaxWidth().border(1.dp, Color.Gray)) {
                                Text("Fecha:", fontWeight = FontWeight.Bold, modifier = Modifier.weight(1f).padding(8.dp))
                                Text(modificacion.fecha, modifier = Modifier.weight(1f).padding(8.dp))
                            }

                            Row(modifier = Modifier.fillMaxWidth().border(1.dp, Color.Gray)) {
                                Text("Detalles:", fontWeight = FontWeight.Bold, modifier = Modifier.weight(1f).padding(8.dp))
                                Text(modificacion.detalles, modifier = Modifier.weight(1f).padding(8.dp))
                            }
                        }
                    }
                }
            }
        }
    }
}


@Composable
fun SeguroDetails(seguro: Seguro) {
    Column(modifier = Modifier.padding(16.dp)) {
        Text(text = "ID: ${seguro._id}")
        Text(text = "Tipo: ${seguro.tipo}")
        Text(text = "Prima mensual: ${seguro.prima_mensual}")
        Text(text = "Fecha inicio: ${seguro.fecha_inicio}")
        Text(text = "Fecha fin: ${seguro.fecha_fin}")

        // Mostrar coberturas
        Text(text = "Coberturas:")
        Text(text = "Incendio: ${seguro.coberturas.incendio}")
        Text(text = "Robo: ${seguro.coberturas.robo}")
        Text(text = "Terremoto: ${seguro.coberturas.terremoto}")

        // Mostrar modificaciones
        Text(text = "Modificaciones:")
        seguro.modificaciones.forEach { modificacion ->
            Text(text = "Fecha: ${modificacion.fecha}")
            Text(text = "Detalles: ${modificacion.detalles}")
        }
    }
}


//----------------------------------------//
//-------- Tabla #2 Siniestros ---------//
//----------------------------------------//

/*@Composable
fun Coleccion2Screen() {
    val viewModel: SiniestroViewModel = viewModel() // Obtén la instancia de ViewModel

    // Llamar a la API cuando se entra en esta pantalla
    LaunchedEffect(Unit) {
        viewModel.fetchSiniestro() // Llamada a la API
    }

    // Mostrar los datos obtenidos de la API
    if (viewModel.isLoading) {
        CircularProgressIndicator(modifier = Modifier.padding(16.dp)) // Indicador de carga
    } else if (viewModel.errorMessage != null) {
        // Mostrar el mensaje de error si ocurrió uno
        Text(
            text = viewModel.errorMessage ?: "Error desconocido",
            color = Color.Red, // Puedes personalizar el color del mensaje de error
            modifier = Modifier.padding(16.dp)
        )
    } else {
        // Si no hay error y los datos están disponibles
        viewModel.siniestro?.let { siniestro ->
            // Contenedor principal con scroll vertical para toda la pantalla
            Column(modifier = Modifier
                .verticalScroll(rememberScrollState())
                .padding(16.dp) // Margen alrededor de toda la pantalla
            ) {
                // Contenedor de la tabla con scroll horizontal para los primeros datos
                Row(
                    modifier = Modifier
                        .horizontalScroll(rememberScrollState()) // Permite el desplazamiento horizontal
                        .fillMaxWidth()
                ) {
                    // Cabecera de la tabla con bordes
                    Column(
                        modifier = Modifier
                            .border(1.dp, Color.Gray)
                            .padding(8.dp)
                    ) {
                        Text("Tipo de Seguro", fontWeight = FontWeight.Bold, modifier = Modifier.padding(8.dp))
                        Text("Fecha Incidente", fontWeight = FontWeight.Bold, modifier = Modifier.padding(8.dp))
                        Text("Ubicación", fontWeight = FontWeight.Bold, modifier = Modifier.padding(8.dp))
                        Text("Descripción", fontWeight = FontWeight.Bold, modifier = Modifier.padding(8.dp))
                        Text("Estado", fontWeight = FontWeight.Bold, modifier = Modifier.padding(8.dp))
                    }

                    // Fila de datos con bordes
                    Column(
                        modifier = Modifier
                            .border(1.dp, Color.Gray)
                            .padding(8.dp)
                    ) {
                        Text(siniestro.tipo_seguro, modifier = Modifier.padding(8.dp))
                        Text(siniestro.fecha_incidente, modifier = Modifier.padding(8.dp))
                        Text(siniestro.ubicacion, modifier = Modifier.padding(8.dp))
                        Text(siniestro.descripcion, modifier = Modifier.padding(8.dp))
                        Text(siniestro.estado, modifier = Modifier.padding(8.dp))
                    }
                }

                // Mostrar los detalles en un Scroll Vertical
                Spacer(modifier = Modifier.height(16.dp))

                // Encabezado para los detalles adicionales
                Text("Detalles Adicionales:", fontWeight = FontWeight.Bold, modifier = Modifier.padding(8.dp))

                // Fila de detalles con bordes
                Row(modifier = Modifier.fillMaxWidth().border(1.dp, Color.Gray)) {
                    Text("Tipo de Seguro:", fontWeight = FontWeight.Bold, modifier = Modifier.weight(1f).padding(8.dp))
                    Text(siniestro.tipo_seguro, modifier = Modifier.weight(1f).padding(8.dp))
                }

                Row(modifier = Modifier.fillMaxWidth().border(1.dp, Color.Gray)) {
                    Text("Fecha Incidente:", fontWeight = FontWeight.Bold, modifier = Modifier.weight(1f).padding(8.dp))
                    Text(siniestro.fecha_incidente, modifier = Modifier.weight(1f).padding(8.dp))
                }

                Row(modifier = Modifier.fillMaxWidth().border(1.dp, Color.Gray)) {
                    Text("Ubicación:", fontWeight = FontWeight.Bold, modifier = Modifier.weight(1f).padding(8.dp))
                    Text(siniestro.ubicacion, modifier = Modifier.weight(1f).padding(8.dp))
                }

                Row(modifier = Modifier.fillMaxWidth().border(1.dp, Color.Gray)) {
                    Text("Descripción:", fontWeight = FontWeight.Bold, modifier = Modifier.weight(1f).padding(8.dp))
                    Text(siniestro.descripcion, modifier = Modifier.weight(1f).padding(8.dp))
                }

                Row(modifier = Modifier.fillMaxWidth().border(1.dp, Color.Gray)) {
                    Text("Estado:", fontWeight = FontWeight.Bold, modifier = Modifier.weight(1f).padding(8.dp))
                    Text(siniestro.estado, modifier = Modifier.weight(1f).padding(8.dp))
                }

                // Mostrar documentos en un Scroll Vertical
                if (siniestro.documentos.isNotEmpty()) {
                    Spacer(modifier = Modifier.height(8.dp))
                    Text("Documentos:", fontWeight = FontWeight.Bold, modifier = Modifier.padding(8.dp))

                    siniestro.documentos.forEach { documento ->
                        Row(modifier = Modifier.fillMaxWidth().border(1.dp, Color.Gray)) {
                            Text("Tipo:", fontWeight = FontWeight.Bold, modifier = Modifier.weight(1f).padding(8.dp))
                            Text(documento.tipo, modifier = Modifier.weight(1f).padding(8.dp))
                        }

                        Row(modifier = Modifier.fillMaxWidth().border(1.dp, Color.Gray)) {
                            Text("URL:", fontWeight = FontWeight.Bold, modifier = Modifier.weight(1f).padding(8.dp))
                            Text(documento.url, modifier = Modifier.weight(1f).padding(8.dp))
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun SiniestroDetails(siniestro: Siniestro) {
    Column(modifier = Modifier.padding(16.dp)) {
        Text(text = "ID: ${siniestro._id}", maxLines = 1, overflow = TextOverflow.Ellipsis)
        Text(text = "Tipo de Seguro: ${siniestro.tipo_seguro}", maxLines = 1, overflow = TextOverflow.Ellipsis)
        Text(text = "Fecha del Incidente: ${siniestro.fecha_incidente}", maxLines = 1, overflow = TextOverflow.Ellipsis)
        Text(text = "Ubicación: ${siniestro.ubicacion}", maxLines = 1, overflow = TextOverflow.Ellipsis)
        Text(text = "Descripción: ${siniestro.descripcion}", maxLines = 2, overflow = TextOverflow.Ellipsis)
        Text(text = "Estado: ${siniestro.estado}", maxLines = 1, overflow = TextOverflow.Ellipsis)

        // Mostrar documentos
        Text(text = "Documentos:")
        siniestro.documentos.forEach { documento ->
            Text(text = "Tipo: ${documento.tipo}", maxLines = 1, overflow = TextOverflow.Ellipsis)
            Text(text = "URL: ${documento.url}", maxLines = 1, overflow = TextOverflow.Ellipsis)
        }
    }
}
*/


@Composable
fun Coleccion2Screen() {
    val viewModel: SiniestroViewModel = viewModel() // Obtén la instancia de ViewModel

    // Llamar a la API cuando se entra en esta pantalla
    LaunchedEffect(Unit) {
        viewModel.fetchSiniestro() // Llamada a la API
    }

    // Mostrar los datos obtenidos de la API
    if (viewModel.isLoading) {
        CircularProgressIndicator(modifier = Modifier.padding(16.dp)) // Indicador de carga
    } else if (viewModel.errorMessage != null) {
        // Mostrar el mensaje de error si ocurrió uno
        Text(
            text = viewModel.errorMessage ?: "Error desconocido",
            color = Color.Red, // Puedes personalizar el color del mensaje de error
            modifier = Modifier.padding(16.dp)
        )
    } else {
        // Si no hay error y los datos están disponibles
        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .padding(16.dp) // Margen alrededor de toda la pantalla
        ) {
            viewModel.siniestro.forEach { siniestro ->
                // Contenedor de cada siniestro con scroll horizontal para la tabla de datos
                Column(
                    modifier = Modifier
                        .padding(bottom = 16.dp)
                        .border(1.dp, Color.LightGray)
                        .fillMaxWidth()
                        .padding(8.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .horizontalScroll(rememberScrollState()) // Permite el desplazamiento horizontal
                            .fillMaxWidth()
                    ) {
                        // Cabecera de la tabla con bordes
                        Column(
                            modifier = Modifier
                                .border(1.dp, Color.Gray)
                                .padding(8.dp)
                        ) {
                            Text("Tipo de Seguro", fontWeight = FontWeight.Bold, modifier = Modifier.padding(8.dp))
                            Text("Fecha Incidente", fontWeight = FontWeight.Bold, modifier = Modifier.padding(8.dp))
                            Text("Ubicación", fontWeight = FontWeight.Bold, modifier = Modifier.padding(8.dp))
                            Text("Descripción", fontWeight = FontWeight.Bold, modifier = Modifier.padding(8.dp))
                            Text("Estado", fontWeight = FontWeight.Bold, modifier = Modifier.padding(8.dp))
                        }

                        // Fila de datos con bordes
                        Column(
                            modifier = Modifier
                                .border(1.dp, Color.Gray)
                                .padding(8.dp)
                        ) {
                            Text(siniestro.tipo_seguro, modifier = Modifier.padding(8.dp))
                            Text(siniestro.fecha_incidente, modifier = Modifier.padding(8.dp))
                            Text(siniestro.ubicacion, modifier = Modifier.padding(8.dp))
                            Text(siniestro.descripcion, modifier = Modifier.padding(8.dp))
                            Text(siniestro.estado, modifier = Modifier.padding(8.dp))
                        }
                    }

                    // Mostrar documentos en un Scroll Vertical
                    Spacer(modifier = Modifier.height(16.dp))
                    Text("Documentos:", fontWeight = FontWeight.Bold, modifier = Modifier.padding(8.dp))

                    siniestro.documentos.forEach { documento ->
                        // Mostrar cada documento en una fila
                        Row(modifier = Modifier.fillMaxWidth().border(1.dp, Color.Gray)) {
                            Text("Tipo:", fontWeight = FontWeight.Bold, modifier = Modifier.weight(1f).padding(8.dp))
                            Text(documento.tipo, modifier = Modifier.weight(1f).padding(8.dp))
                        }

                        Row(modifier = Modifier.fillMaxWidth().border(1.dp, Color.Gray)) {
                            Text("URL:", fontWeight = FontWeight.Bold, modifier = Modifier.weight(1f).padding(8.dp))
                            Text(documento.url, modifier = Modifier.weight(1f).padding(8.dp))
                        }
                    }

                    // Espacio entre cada siniestro
                    Spacer(modifier = Modifier.height(16.dp))
                }
            }
        }
    }
}


@Composable
fun SiniestroDetails(siniestro: Siniestro) {
    Column(modifier = Modifier.padding(8.dp)) {
        Text(text = "Tipo de Seguro: ${siniestro.tipo_seguro}", maxLines = 1, overflow = TextOverflow.Ellipsis)
        Text(text = "Fecha del Incidente: ${siniestro.fecha_incidente}", maxLines = 1, overflow = TextOverflow.Ellipsis)
        Text(text = "Ubicación: ${siniestro.ubicacion}", maxLines = 1, overflow = TextOverflow.Ellipsis)
        Text(text = "Descripción: ${siniestro.descripcion}", maxLines = 2, overflow = TextOverflow.Ellipsis)
        Text(text = "Estado: ${siniestro.estado}", maxLines = 1, overflow = TextOverflow.Ellipsis)

        // Mostrar documentos
        if (siniestro.documentos.isNotEmpty()) {
            Text(text = "Documentos:", fontWeight = FontWeight.Bold)
            siniestro.documentos.forEach { documento ->
                Text(text = "Tipo: ${documento.tipo}", maxLines = 1, overflow = TextOverflow.Ellipsis)
                Text(text = "URL: ${documento.url}", maxLines = 1, overflow = TextOverflow.Ellipsis)
            }
        }
    }
}


//=================================================================================================================//
// NAVEGACION PARA LOS GRAFICOS
//=================================================================================================================//
@Composable
fun AppNavigation(navController: NavController) {
    NavHost(navController = navController as NavHostController, startDestination = "grafico1") {
        // Navegación para gráficos
        composable("grafico1") { Grafico1Screen() }
        composable("grafico2") { Grafico2Screen() }
        composable("grafico3") { Grafico3Screen() }
        composable("grafico4") { Grafico4Screen() }

        // Navegación para colecciones
        composable("coleccion1") { Coleccion1Screen() }
        composable("coleccion2") { Coleccion2Screen() }
    }
}

// Configuración de tema personalizada
@Composable
fun SegurosAppsNicaTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = AppColorPalette,
        typography = Typography(
            headlineLarge = TextStyle(fontSize = 28.sp, fontWeight = FontWeight.ExtraBold, color = Color(0xFF6200EE)),
            headlineSmall = TextStyle(fontSize = 22.sp, fontWeight = FontWeight.Bold)
        ),
        content = content
    )
}

// Paleta de colores de la app
val AppColorPalette = lightColorScheme(
    primary = Color(0xFF6200EE),
    onPrimary = Color.White,
    secondary = Color(0xFF03DAC5),
    onSecondary = Color.Black,
    background = Color.White,
    surface = Color.White
)

@Preview(showBackground = true)
@Composable
fun PreviewMainScreen() {
    val navController = rememberNavController()
    MainScreen(navController = navController)
}

@Preview(showBackground = true)
@Composable
fun PreviewMenuScreen() {
    MenuScreen()
}