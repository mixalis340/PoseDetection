package com.example.myapp.presentation.main_screen

import android.content.Context
import android.util.Log
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageProxy
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.sharp.FlipCameraAndroid
import androidx.compose.material.icons.sharp.Settings
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.LifecycleOwner
import androidx.navigation.NavController
import com.example.myapp.R
import com.example.myapp.presentation.pose_detector.DetectedPose
import com.example.myapp.presentation.pose_detector.PoseDetectorProcessor
import com.example.myapp.presentation.pose_detector.SourceInfo
import com.example.myapp.presentation.settings.SettingsViewModel
import com.example.myapp.presentation.util.Screen
import com.google.android.gms.tasks.TaskExecutors
import com.google.common.util.concurrent.ListenableFuture
import com.google.mlkit.common.MlKitException
import com.google.mlkit.vision.pose.Pose
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine


@Composable
fun CameraView(
    navController: NavController
) {
    val context = LocalContext.current
    var lensFacing by remember { mutableStateOf(CameraSelector.LENS_FACING_BACK)}

  CameraPreviewView(
      navController,
      lensFacing,
  ) { cameraUIAction ->
      when(cameraUIAction) {
          is CameraUIAction.OnSwitchCameraClick -> {
              lensFacing =
                  if (lensFacing == CameraSelector.LENS_FACING_BACK) CameraSelector.LENS_FACING_FRONT
              else
                  CameraSelector.LENS_FACING_BACK
          }
      }
  }
}

@Composable
private fun CameraPreviewView(
     navController: NavController,
    lensFacing: Int = CameraSelector.LENS_FACING_BACK,
    cameraUIAction: (CameraUIAction) -> Unit
) {

    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current

    //Build the preview
    val preview = Preview.Builder().build()

    //By default is back camera
    val cameraSelector = CameraSelector.Builder()
        .requireLensFacing(lensFacing)
        .build()

    val previewView = remember {
        PreviewView(context)
    }
    var detectedPose by remember { mutableStateOf<Pose?>(null) }
    var sourceInfo by remember { mutableStateOf(SourceInfo(10, 10, true)) }

    val analysis =
        bindAnalysisUseCase(lensFacing, setSourceInfo = { sourceInfo = it }, onPoseDetected = { detectedPose = it })

    //Listen for changes to lensFacing
    LaunchedEffect(lensFacing) {
        val cameraProvider = context.getCameraProvider()
        cameraProvider.unbindAll()
        cameraProvider.bindToLifecycle(
            lifecycleOwner,
            cameraSelector,
            preview,
            analysis
        )
        //To see the changes
        preview.setSurfaceProvider(previewView.surfaceProvider)
    }

    Box(modifier = Modifier.fillMaxSize()) {
        AndroidView({ previewView }, modifier = Modifier.fillMaxSize()) {
        }
        BoxWithConstraints(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            with(LocalDensity.current) {
                Box(
                    modifier = Modifier
                        .size(
                            height = sourceInfo.height.toDp(),
                            width = sourceInfo.width.toDp()
                        )
                        .scale(
                            calculateScale(
                                constraints,
                                sourceInfo,
                                PreviewScaleType.CENTER_CROP
                            )
                        )
                )
                {
                    DetectedPose(pose = detectedPose, sourceInfo = sourceInfo, true)
                }
            }
        }

        Column(
            modifier = Modifier.align(Alignment.BottomCenter),
            verticalArrangement = Arrangement.Bottom
        ) {
            CameraControls(cameraUIAction, navController)
        }
    }

}

@Composable
fun CameraControls(cameraUIAction: (CameraUIAction) -> Unit, navController: NavController) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colors.surface),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {
        CameraControl(
            Icons.Sharp.FlipCameraAndroid,
            R.string.icn_camera_view_switch_camera_content_description,
            modifier= Modifier.size(50.dp),
            onClick = { cameraUIAction(CameraUIAction.OnSwitchCameraClick) }
        )
        CameraControl(
            Icons.Sharp.Settings,
            R.string.icn_camera_view_settings,
            modifier= Modifier.size(50.dp),
            onClick = {navController.navigate(Screen.SettingsScreen.route)}
        )
    }
}

@Composable
fun CameraControl(
    imageVector: ImageVector,
    contentDescId: Int,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    IconButton(
        onClick = onClick,
        modifier = modifier
    ) {
        Icon(
            imageVector = imageVector,
            contentDescription = stringResource(id = contentDescId),
            modifier = modifier,
            tint = Color.White
        )
    }
}

suspend fun Context.getCameraProvider(): ProcessCameraProvider = suspendCoroutine { continuation ->
    ProcessCameraProvider.getInstance(this).also { cameraProvider ->
        cameraProvider.addListener({
            continuation.resume(cameraProvider.get())
        }, ContextCompat.getMainExecutor(this))
    }
}

private fun bindAnalysisUseCase(
    lensFacing: Int,
    setSourceInfo: (SourceInfo) -> Unit,
    onPoseDetected: (Pose) -> Unit
): ImageAnalysis? {
    val poseProcessor = try {
        PoseDetectorProcessor()
    } catch (e: Exception) {
        Log.e("CAMERA", "Can not create pose processor", e)
        return null
    }
    val builder = ImageAnalysis.Builder()
    val analysisUseCase = builder.build()

    var sourceInfoUpdated = false

    analysisUseCase.setAnalyzer(
        TaskExecutors.MAIN_THREAD
    ) { imageProxy: ImageProxy ->
        if (!sourceInfoUpdated) {
            setSourceInfo(obtainSourceInfo(lensFacing, imageProxy))
            sourceInfoUpdated = true
        }
        try {
            poseProcessor.processImageProxy(imageProxy, onPoseDetected)
        } catch (e: MlKitException) {
            Log.e(
                "CAMERA", "Failed to process image. Error: " + e.localizedMessage
            )
        }
    }
    return analysisUseCase
}

private fun obtainSourceInfo(lens: Int, imageProxy: ImageProxy): SourceInfo {
    val isImageFlipped = lens == CameraSelector.LENS_FACING_FRONT
    val rotationDegrees = imageProxy.imageInfo.rotationDegrees
    return if (rotationDegrees == 0 || rotationDegrees == 180) {
        SourceInfo(
            height = imageProxy.height, width = imageProxy.width, isImageFlipped = isImageFlipped
        )
    } else {
        SourceInfo(
            height = imageProxy.width, width = imageProxy.height, isImageFlipped = isImageFlipped
        )
    }
}
private fun calculateScale(
    constraints: Constraints,
    sourceInfo: SourceInfo,
    scaleType: PreviewScaleType
): Float {
    val heightRatio = constraints.maxHeight.toFloat() / sourceInfo.height
    val widthRatio = constraints.maxWidth.toFloat() / sourceInfo.width
    return when (scaleType) {
        PreviewScaleType.FIT_CENTER -> kotlin.math.min(heightRatio, widthRatio)
        PreviewScaleType.CENTER_CROP -> kotlin.math.max(heightRatio, widthRatio)
    }
}
private enum class PreviewScaleType {
    FIT_CENTER,
    CENTER_CROP
}
