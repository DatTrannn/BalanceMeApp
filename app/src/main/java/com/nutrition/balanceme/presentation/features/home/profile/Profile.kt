package com.nutrition.balanceme.presentation.features.home.profile

import android.app.Activity
import android.content.Intent
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts.StartActivityForResult
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement.Center
import androidx.compose.foundation.layout.Arrangement.End
import androidx.compose.foundation.layout.Arrangement.SpaceAround
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.MaterialTheme.colors
import androidx.compose.material.MaterialTheme.typography
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.Icons.Outlined
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.outlined.CameraAlt
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight.Companion.SemiBold
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import coil.transform.CircleCropTransformation
import com.github.dhaval2404.imagepicker.ImagePicker
import com.google.accompanist.pager.ExperimentalPagerApi
import com.nutrition.balanceme.R
import com.nutrition.balanceme.data.remote.UploadState
import com.nutrition.balanceme.presentation.components.*
import com.nutrition.balanceme.presentation.features.auth.AuthActivity
import com.nutrition.balanceme.util.getActivity
import com.nutrition.balanceme.util.toast


@Composable
@ExperimentalCoilApi
@ExperimentalPagerApi
@ExperimentalMaterialApi
@ExperimentalAnimationApi
@ExperimentalFoundationApi
fun Profile(controller: NavHostController) {
    val viewmodel: ProfileViewmodel = hiltViewModel()
    viewmodel.getData()

    val error by remember { viewmodel.error }
    error?.let { LocalContext.current.toast(it) }

    val recipes by remember { viewmodel.recipes }
    val favorites by remember { viewmodel.favorites }
    val user by viewmodel.profile.collectAsState(initial = null)

    var open by remember { mutableStateOf(false) }

    user?.let { profile ->

        if (open)
            AlertDialog(
                text = null,
                title = null,
                modifier = Modifier.fillMaxWidth(),
                backgroundColor = colors.background,
                onDismissRequest = { open = false },
                buttons = { ProfileDialog(viewmodel) },
            )

        Scaffold(floatingActionButton = { Fab(controller) }) { padding ->
            ScrollableColumn(modifier = Modifier.padding(10.dp)) {
                SmallSpace()
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = CenterVertically,
                    horizontalArrangement = SpaceAround
                ) {
                    TinySpace()
                    Image(
                        painter = rememberAsyncImagePainter(ImageRequest.Builder(LocalContext.current)
                            .data(data = profile.avatar).apply(block = fun ImageRequest.Builder.() {
                                transformations(CircleCropTransformation())
                            }).build()),
                        contentDescription = "user avatar",
                        modifier = Modifier
                            .size(100.dp)
                            .clip(CircleShape)
                            .clickable { open = true }
                    )
                    TinySpace()
                    Column(horizontalAlignment = CenterHorizontally) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = SpaceAround
                        ) {
                            Column(horizontalAlignment = CenterHorizontally) {
                                Text(
                                    text = recipes.size.toString(),
                                    style = typography.body1.copy(fontWeight = SemiBold)
                                )
                                Text("Recipes", style = typography.h6.copy(fontSize = 16.sp))
                            }
                            Column(horizontalAlignment = CenterHorizontally) {
                                Text("0", style = typography.body1.copy(fontWeight = SemiBold))
                                Text("Likes", style = typography.h6.copy(fontSize = 16.sp))
                            }
                        }
                        SmallSpace()
                        Text(profile.description, textAlign = TextAlign.Center)
                    }
                    TinySpace()
                }
                SmallSpace()
                if (recipes.isEmpty()) Empty() else Recipes(recipes, controller)
            }
        }
    }
}

@Composable
fun Empty() {
    Column(
        verticalArrangement = Center,
        horizontalAlignment = CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .padding(10.dp),
    ) {
        Image(
            contentDescription = "empty",
            modifier = Modifier.size(100.dp),
            painter = painterResource(id = R.drawable.desert),
        )
        SmallSpace()
        Text("No recipes around here...")
    }
}


@Composable
@ExperimentalPagerApi
@ExperimentalMaterialApi
@ExperimentalAnimationApi
@ExperimentalFoundationApi
fun ProfileDialog(viewmodel: ProfileViewmodel) {
    val context = LocalContext.current
    val activity = context.getActivity()!!

    val url by remember { viewmodel.url }
    val loading by remember { viewmodel.loading }

    val progress by viewmodel.progress.observeAsState()
    val user by viewmodel.profile.collectAsState(initial = null)

    var button by remember { mutableStateOf("") }
    button = if (loading) "Updating profile..." else "Update profile"

    val launcher = rememberLauncherForActivityResult(StartActivityForResult()) {
        val data = it.data
        when (it.resultCode) {
            Activity.RESULT_OK -> viewmodel.uploadAvatar(data?.data!!)
            ImagePicker.RESULT_ERROR -> context.toast(ImagePicker.getError(data))
            else -> context.toast("Image upload cancelled")
        }
    }

    fun pick() = ImagePicker.with(activity).galleryOnly().createIntent { launcher.launch(it) }

    user?.let { profile ->
        val nameState =
            remember { TextFieldState<String>(profile.username, validators = listOf(Required())) }
        val descriptionState = remember {
            TextFieldState<String>(profile.description,
                validators = listOf(Required()))
        }

        when (progress) {
            is UploadState.Error -> context.toast((progress as UploadState.Error).message)
            is UploadState.Success -> viewmodel.updateProfile(profile.copy(avatar = (progress as UploadState.Success).url))
            is UploadState.Loading -> {
                val percentage =
                    ((progress as UploadState.Loading).current.toDouble() / (progress as UploadState.Loading).total.toDouble()) * 100
                button = "Uploading image...${percentage.toInt()}%"
            }
            UploadState.Idle -> TODO()
            null -> TODO()
        }

        Column(horizontalAlignment = CenterHorizontally, modifier = Modifier.padding(10.dp)) {
            SmallSpace()
            Box(modifier = Modifier
                .size(100.dp)
                .clip(CircleShape)
                .clickable { pick() }) {
                Image(
                    painter = rememberAsyncImagePainter(ImageRequest.Builder(LocalContext.current)
                        .data(data = url ?: profile.avatar)
                        .apply(block = fun ImageRequest.Builder.() {
                            transformations(CircleCropTransformation())
                        }).build()),
                    contentDescription = "user avatar",
                )
                Icon(
                    Outlined.CameraAlt,
                    tint = colors.primaryVariant,
                    contentDescription = "camera icon",
                    modifier = Modifier.align(Alignment.Center)
                )
            }
            SmallSpace()
            OutlinedInput(label = "username", type = KeyboardType.Text, state = nameState)
            OutlinedInput(label = "tagline", type = KeyboardType.Text, state = descriptionState)
            TinySpace()
            FilledButton(text = button, modifier = Modifier.fillMaxWidth()) {
                if (descriptionState.validate() && nameState.validate() && !loading!!) {
                    val update =
                        profile.copy(username = nameState.text, description = descriptionState.text)
                    viewmodel.updateProfile(update)
                }
            }
            SmallSpace()
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = End) {
                TextButton(text = "Logout") {
                    viewmodel.logout()
                    context.startActivity(Intent(context, AuthActivity::class.java))
                    context.getActivity().let { it!!.finish() }
                }
            }
        }
    }
}

@Composable
fun Fab(controller: NavHostController) {
    FloatingActionButton(onClick = { controller.navigate("/add") }) {
        Icon(Icons.Default.Add, contentDescription = "add icon")
    }
}