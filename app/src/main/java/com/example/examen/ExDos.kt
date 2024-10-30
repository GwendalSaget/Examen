package com.example.examen

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.ui.platform.LocalContext
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.examen.ui.theme.ExamenTheme

class ExDos : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ExamenTheme {
                ShoppingListInterface() // Appel de l'interface de la liste de courses
            }
        }
    }
}

data class Product(
    val name: String,
    var quantity: Int = 0,
    var price: Double? = null
)

@Composable
fun ShoppingListInterface(modifier: Modifier = Modifier) {
    var newProductName by remember { mutableStateOf("") }
    var newProductQuantity by remember { mutableStateOf("") } // Quantité en string
    var newProductPrice by remember { mutableStateOf("") }    // Prix en string
    var productList by remember { mutableStateOf(listOf<Product>()) }
    val totalItems = productList.size
    val totalPrice = productList.filter { it.price != null }.sumOf { it.price!! * it.quantity }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            val context = LocalContext.current
            Button(
                onClick = {

                    val intent = Intent(context, PantallaInicio::class.java)
                    context.startActivity(intent)
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = stringResource(R.string.home), fontSize = 20.sp)
            }

            Text(
                text = "Lista de la compra",
                fontSize = 32.sp,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            TextField(
                value = newProductName,
                onValueChange = { newProductName = it },
                label = { Text("Nombre del producto") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp),
                singleLine = true
            )

            TextField(
                value = newProductQuantity,
                onValueChange = { if (it.all { char -> char.isDigit() }) newProductQuantity = it },
                label = { Text("Cantidad") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp),
                singleLine = true
            )

            TextField(
                value = newProductPrice,
                onValueChange = { if (it.all { char -> char.isDigit() || char == '.' }) newProductPrice = it },
                label = { Text("Precio aproximado") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
                singleLine = true
            )

            Button(
                onClick = {
                    if (newProductName.isNotBlank()) {
                        val quantity = newProductQuantity.toIntOrNull() ?: 0
                        val price = newProductPrice.toDoubleOrNull()
                        val newProduct = Product(name = newProductName, quantity = quantity, price = price)
                        productList = productList + newProduct
                        newProductName = ""
                        newProductQuantity = ""
                        newProductPrice = ""
                    }
                },
                modifier = Modifier.padding(vertical = 16.dp)
            ) {
                Text("Añadir producto")
            }

            Text("Número total de productos: $totalItems", fontSize = 18.sp)
            Text("Precio total: €$totalPrice", fontSize = 18.sp)

            LazyColumn(modifier = Modifier.fillMaxSize().padding(top = 16.dp)) {
                items(productList) { product ->
                    ProductItem(
                        product = product,
                        onDelete = {
                            productList = productList - product
                        }
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

        }
    }
}

@Composable
fun ProductItem(product: Product, onDelete: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = "${product.name} (x${product.quantity}) - €${product.price ?: "Sin precio"}",
            fontSize = 18.sp
        )

        Button(onClick = onDelete) {
            Text("Eliminar")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun Preview() {
    ExamenTheme {
        ShoppingListInterface()
    }
}
