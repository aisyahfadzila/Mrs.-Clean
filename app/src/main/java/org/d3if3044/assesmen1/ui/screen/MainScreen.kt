package org.d3if3044.assesmen1.ui.screen

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import org.d3if3044.assesmen1.R
import org.d3if3044.assesmen1.model.Pakaian
import org.d3if3044.assesmen1.navigation.Screen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(navController: NavHostController) {
    Scaffold(
        topBar  = {
            CenterAlignedTopAppBar(
                title = {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = stringResource(R.string.app_name),
                            style = MaterialTheme.typography.displayLarge
                        )
                    }
                },
                colors = TopAppBarDefaults.mediumTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.secondaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                ),
                actions = {
                    IconButton(
                        onClick = { navController.navigate(Screen.About.route) }
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.Info,
                            contentDescription = stringResource(R.string.informasi_bantuan),
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                }
            )
        },
        containerColor = MaterialTheme.colorScheme.secondaryContainer
    ) {
        padding ->
        ScreenContent(Modifier.padding(padding))
    }
}

@SuppressLint("StringFormatMatches")
@Composable
fun ScreenContent(modifier: Modifier) {
    var atasan by rememberSaveable { mutableStateOf("") }
    var bawahan by rememberSaveable { mutableStateOf("") }
    var underwear by rememberSaveable { mutableStateOf("") }
    var jilbab by rememberSaveable { mutableStateOf("") }
    var kaoskaki by rememberSaveable { mutableStateOf("") }
    var berat by rememberSaveable { mutableStateOf("") }
    var beratError by rememberSaveable { mutableStateOf(false) }

    val tipe = listOf(
        stringResource(id = R.string.cuci_setrika),
        stringResource(id = R.string.cuci_kering),
        stringResource(id = R.string.setrika)
    )
    val layanan = listOf(
        stringResource(id = R.string.layanan_normal),
        stringResource(id = R.string.layanan_express)
    )
    var selectedTipe by rememberSaveable { mutableStateOf(tipe[0]) }
    var selectedLayanan by rememberSaveable { mutableStateOf(layanan[0]) }

    var hargaTotal by rememberSaveable { mutableFloatStateOf(0f) }

    val context = LocalContext.current

    Column (
        modifier = modifier
            .clip(MaterialTheme.shapes.large)
            .background(MaterialTheme.colorScheme.background)
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(id = R.string.type),
            style = MaterialTheme.typography.displayMedium,
            modifier = Modifier.fillMaxWidth()
        )
        Row(
            modifier = Modifier
                .padding(top = 6.dp, bottom = 8.dp)
                .fillMaxWidth()
        ) {
            tipe.forEach { text ->
                GenderOption(
                    label = text,
                    isSelected = selectedTipe == text,
                    modifier = Modifier
                        .selectable(
                            selected = selectedTipe == text,
                            onClick = { selectedTipe = text },
                            role = Role.RadioButton
                        )
                        .weight(1f),
                )
            }
        }
        Text(
            text = stringResource(id = R.string.layanan),
            style = MaterialTheme.typography.displayMedium,
            modifier = Modifier.fillMaxWidth()
        )
        Row(
            modifier = Modifier
                .padding(top = 6.dp)
        ) {
            layanan.forEach { text ->
                ServiceOption(
                    label = text,
                    isSelected = selectedLayanan == text,
                    modifier = Modifier
                        .selectable(
                            selected = selectedLayanan == text,
                            onClick = { selectedLayanan = text },
                            role = Role.RadioButton
                        )
                        .weight(1f),
                )
            }
        }
        Column (
            modifier = Modifier.padding(top = 32.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Row (
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ){
                DetailPakaian(Pakaian("Atasan", R.drawable.shirt))
                Text(
                    text = stringResource(id = R.string.atasan),
                    style = MaterialTheme.typography.displaySmall
                )
                Spacer(modifier = Modifier.weight(1f))
                OutlinedTextField(
                    shape = MaterialTheme.shapes.small,
                    value = atasan,
                    onValueChange = { atasan = it },
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Number,
                        imeAction = ImeAction.Next
                    ),
                    modifier = Modifier.width(80.dp)
                )
            }
            Row (
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ){
                DetailPakaian(Pakaian("Bawahan", R.drawable.pant))
                Text(
                    text = stringResource(id = R.string.bawahan),
                    style = MaterialTheme.typography.displaySmall
                )
                Spacer(modifier = Modifier.weight(1f))
                OutlinedTextField(
                    shape = MaterialTheme.shapes.small,
                    value = bawahan,
                    onValueChange = { bawahan = it },
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Number,
                        imeAction = ImeAction.Next
                    ),
                    modifier = Modifier.width(80.dp)
                )
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                DetailPakaian(Pakaian("Underwear", R.drawable.underwear))
                Text(
                    text = stringResource(id = R.string.underwear),
                    style = MaterialTheme.typography.displaySmall
                )
                Spacer(modifier = Modifier.weight(1f))
                OutlinedTextField(
                    shape = MaterialTheme.shapes.small,
                    value = underwear,
                    onValueChange = { underwear = it },
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Number,
                        imeAction = ImeAction.Next
                    ),
                    modifier = Modifier.width(80.dp)
                )
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                DetailPakaian(Pakaian("Jilbab", R.drawable.scarf))
                Text(
                    text = stringResource(id = R.string.jilbab),
                    style = MaterialTheme.typography.displaySmall
                )
                Spacer(modifier = Modifier.weight(1f))
                OutlinedTextField(
                    shape = MaterialTheme.shapes.small,
                    value = jilbab,
                    onValueChange = { jilbab = it },
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Number,
                        imeAction = ImeAction.Next
                    ),
                    modifier = Modifier.width(80.dp)
                )
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                DetailPakaian(Pakaian("Kaos Kaki", R.drawable.socks))
                Text(
                    text = stringResource(id = R.string.kaos_kaki),
                    style = MaterialTheme.typography.displaySmall
                )
                Spacer(modifier = Modifier.weight(1f))
                OutlinedTextField(
                    shape = MaterialTheme.shapes.small,
                    value = kaoskaki,
                    onValueChange = { kaoskaki = it },
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Number,
                        imeAction = ImeAction.Next
                    ),
                    modifier = Modifier.width(80.dp)
                )
            }
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = stringResource(id = R.string.berat),
                style = MaterialTheme.typography.displayMedium
            )
            Spacer(modifier = Modifier.weight(1f))
            OutlinedTextField(
                shape = MaterialTheme.shapes.medium,
                value = berat,
                onValueChange = { berat = it },
                isError = beratError,
                trailingIcon = { IconPicker(beratError, "kg" ) },
                supportingText = { ErrorHint(beratError)},
                singleLine = true,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Done
                ),
                modifier = Modifier.width(100.dp)
            )
        }
        Button(
            onClick = {
                beratError = (berat == "" || berat == "0")
                if (beratError) return@Button

                hargaTotal = hitungLaundry(berat.toFloat(), selectedTipe, selectedLayanan)
            },
            modifier = Modifier.padding(top = 8.dp),
            contentPadding = PaddingValues(horizontal = 32.dp, vertical = 16.dp)
        ) {
            Text(text = stringResource(R.string.proses))
        }
        if (hargaTotal != 0f) {
            Divider(
                modifier = Modifier.padding(vertical = 8.dp),
                thickness = 1.dp
            )
            Text(
                text = stringResource(R.string.harga, hargaTotal),
                style = MaterialTheme.typography.displayMedium
            )
            Button(
                onClick = {
                    shareData(
                        context = context,
                        message = context.getString(R.string.bagikan_template,
                            atasan, bawahan, underwear, jilbab, kaoskaki, selectedTipe, selectedLayanan, berat, hargaTotal)
                    )
                },
                modifier = Modifier.padding(top = 8.dp),
                contentPadding = PaddingValues(horizontal = 32.dp, vertical = 16.dp)
            ) {
                Text(text = stringResource(R.string.kirim))
            }
        }
    }
}

@Composable
fun GenderOption(label: String, isSelected: Boolean, modifier: Modifier) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        RadioButton(selected = isSelected, onClick = null )
        Text(
            text = label,
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.padding(start = 4.dp)
        )
    }
}
@Composable
fun ServiceOption(label: String, isSelected: Boolean, modifier: Modifier) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        RadioButton(selected = isSelected, onClick = null )
        Text(
            text = label,
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.padding(start = 4.dp)
        )
    }
}
@Composable
fun DetailPakaian(pakaian: Pakaian) {
    Column (
        modifier = Modifier
            .padding(8.dp)
    ) {
        Image(
            painter = painterResource(id = pakaian.imageResId),
            contentDescription = stringResource(R.string.gambar_pakaian, pakaian.nama),
            contentScale = ContentScale.Crop,
            modifier = Modifier.size(50.dp)
        )
    }
}
fun hitungLaundry(berat: Float, jenisCuci: String, jenisLayanan: String): Float {
    val hargaCuciSetrika = 5000f
    val hargaCuciKering = 3000f
    val hargaSetrika = 1000f
    val hargaLayananExpress = 1.5f

    var totalPrice = when (jenisCuci) {
        "Cuci Setrika" -> berat * hargaCuciSetrika
        "Cuci Kering" -> berat * hargaCuciKering
        "Setrika" -> berat * hargaSetrika
        else -> 0f // Tipe cucian tidak valid
    }

    if (jenisLayanan == "Express") {
        totalPrice *= hargaLayananExpress
    }

    return totalPrice
}

private fun shareData(context: Context, message: String) {
    val shareIntent = Intent(Intent.ACTION_SEND).apply {
        type = "text/plain/float"
        putExtra(Intent.EXTRA_TEXT, message)
    }
    if (shareIntent.resolveActivity(context.packageManager) != null) {
        context.startActivity(shareIntent)
    }
}
@Composable
fun IconPicker(isError: Boolean, unit: String) {
    if (isError) {
        Icon(imageVector = Icons.Filled.Warning, contentDescription = null)
    } else {
        Text(text = unit)
    }
}
@Composable
fun ErrorHint(isError: Boolean) {
    if (isError) {
        Text(text = stringResource(R.string.input_invalid))
    }
}