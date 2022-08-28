package com.example.myapp.presentation.pose_detector

import android.graphics.Paint
import android.util.Log
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.*
import androidx.compose.ui.unit.dp
import com.example.myapp.Constants
import com.google.firebase.crashlytics.buildtools.reloc.com.google.common.primitives.Ints
import com.google.mlkit.vision.pose.Pose
import com.google.mlkit.vision.pose.PoseLandmark

import java.util.*
import kotlin.math.atan2

@Composable
fun DetectedPose(
    pose: Pose?,
    sourceInfo: SourceInfo,
    showInFrameLikelihood: Boolean
) {
    if (pose == null)
        return

    val landmarks = pose.allPoseLandmarks

    if(landmarks.isEmpty())
        return

    androidx.compose.foundation.Canvas(modifier = Modifier.fillMaxSize() ) {

        val whitePaint = Color.White
        val leftPaint = Color.Green
        val rightPaint = Color.Yellow
        val textPaint: Paint = Paint()
        textPaint.color = 0xFFEEEEEE.toInt()
        textPaint.textSize = 25.0f

        val needToMirror = sourceInfo.isImageFlipped

        fun translate(landmark: PoseLandmark): Float {
            if(needToMirror)
                return size.width - landmark.position.x
            return landmark.position.x
        }


      fun drawPoint(landmark: PoseLandmark, paint: Color) {
          /*
          val startX =
              if (needToMirror) size.width - landmark.position.x else landmark.position.x
          val startY = landmark.position.y
            drawCircle(paint, 2.0f, center = Offset(startX,startY)  )*/
          val startX = translate(landmark)
          val startY = landmark.position.y
          drawCircle(paint, Constants.DOT_RADIUS, center = Offset(startX,startY))
        }

        for (landmark in landmarks) {
            drawPoint(landmark, whitePaint)
        }

        val nose = pose.getPoseLandmark(PoseLandmark.NOSE)
        val leftEyeInner = pose.getPoseLandmark(PoseLandmark.LEFT_EYE_INNER)
        val leftEye = pose.getPoseLandmark(PoseLandmark.LEFT_EYE)
        val leftEyeOuter = pose.getPoseLandmark(PoseLandmark.LEFT_EYE_OUTER)
        val rightEyeInner = pose.getPoseLandmark(PoseLandmark.RIGHT_EYE_INNER)
        val rightEye = pose.getPoseLandmark(PoseLandmark.RIGHT_EYE)
        val rightEyeOuter = pose.getPoseLandmark(PoseLandmark.RIGHT_EYE_OUTER)
        val leftEar = pose.getPoseLandmark(PoseLandmark.LEFT_EAR)
        val rightEar = pose.getPoseLandmark(PoseLandmark.RIGHT_EAR)
        val leftMouth = pose.getPoseLandmark(PoseLandmark.LEFT_MOUTH)
        val rightMouth = pose.getPoseLandmark(PoseLandmark.RIGHT_MOUTH)

        val leftShoulder = pose.getPoseLandmark(PoseLandmark.LEFT_SHOULDER)
        val rightShoulder = pose.getPoseLandmark(PoseLandmark.RIGHT_SHOULDER)
        val leftElbow = pose.getPoseLandmark(PoseLandmark.LEFT_ELBOW)
        val rightElbow = pose.getPoseLandmark(PoseLandmark.RIGHT_ELBOW)
        val leftWrist = pose.getPoseLandmark(PoseLandmark.LEFT_WRIST)
        val rightWrist = pose.getPoseLandmark(PoseLandmark.RIGHT_WRIST)
        val leftHip = pose.getPoseLandmark(PoseLandmark.LEFT_HIP)
        val rightHip = pose.getPoseLandmark(PoseLandmark.RIGHT_HIP)
        val leftKnee = pose.getPoseLandmark(PoseLandmark.LEFT_KNEE)
        val rightKnee = pose.getPoseLandmark(PoseLandmark.RIGHT_KNEE)
        val leftAnkle = pose.getPoseLandmark(PoseLandmark.LEFT_ANKLE)
        val rightAnkle = pose.getPoseLandmark(PoseLandmark.RIGHT_ANKLE)

        val leftPinky = pose.getPoseLandmark(PoseLandmark.LEFT_PINKY)
        val rightPinky = pose.getPoseLandmark(PoseLandmark.RIGHT_PINKY)
        val leftIndex = pose.getPoseLandmark(PoseLandmark.LEFT_INDEX)
        val rightIndex = pose.getPoseLandmark(PoseLandmark.RIGHT_INDEX)
        val leftThumb = pose.getPoseLandmark(PoseLandmark.LEFT_THUMB)
        val rightThumb = pose.getPoseLandmark(PoseLandmark.RIGHT_THUMB)
        val leftHeel = pose.getPoseLandmark(PoseLandmark.LEFT_HEEL)
        val rightHeel = pose.getPoseLandmark(PoseLandmark.RIGHT_HEEL)
        val leftFootIndex = pose.getPoseLandmark(PoseLandmark.LEFT_FOOT_INDEX)
        val rightFootIndex = pose.getPoseLandmark(PoseLandmark.RIGHT_FOOT_INDEX)



        fun maybeUpdateColor(
            zInImagePixel: Float,
            paint: Color,
            ) {
            // When visualizeZ is true, sets up the paint to different colors based on z values.
            // Gets the range of z value.
            val zLowerBoundInScreenPixel: Float
            val zUpperBoundInScreenPixel: Float

            // By default, assume the range of z value in screen pixel is [-canvasWidth, canvasWidth].
            val defaultRangeFactor = 1f
            zLowerBoundInScreenPixel = -defaultRangeFactor * size.width
            zUpperBoundInScreenPixel = defaultRangeFactor * size.width

            val zInScreenPixel = zInImagePixel * 1.0f

            if(zInScreenPixel < 0) {
                // Sets up the paint to draw the body line in red if it is in front of the z origin.
                // Maps values within [zLowerBoundInScreenPixel, 0) to [255, 0) and use it to control the
                // color. The larger the value is, the more red it will be.

                var v = (zInScreenPixel / zLowerBoundInScreenPixel * 255).toInt()
                v = Ints.constrainToRange(v, 0, 255)
                //paint
            } else{
                // Sets up the paint to draw the body line in blue if it is behind the z origin.
                // Maps values within [0, zUpperBoundInScreenPixel] to [0, 255] and use it to control the
                // color. The larger the value is, the more blue it will be.
                var v = (zInScreenPixel / zUpperBoundInScreenPixel * 255).toInt()
                v = Ints.constrainToRange(v, 0, 255)
                //paint.setARGB(255, 255 - v, 255 - v, 255)
            }
        }
        fun drawLine(
            startLandmark: PoseLandmark?,
            endLandmark: PoseLandmark?,
            //paint: Brush,
            paint: Color
        ) {
            if(startLandmark == null || endLandmark == null)
                return

            val startX =
                if (needToMirror) size.width - startLandmark.position.x else startLandmark.position.x
            val startY = startLandmark.position.y
            val endX =
                if (needToMirror) size.width - endLandmark.position.x else endLandmark.position.x
            val endY = endLandmark.position.y

            val avgZInImagePixel = (startLandmark.position3D.z + endLandmark.position3D.z) / 2
            //maybeUpdateColor(avgZInImagePixel , p)

            drawLine(
                color = paint,
                start = Offset(startX,startY),
                end = Offset(endX, endY),
                strokeWidth = Constants.STROKE_WIDTH
            )
        }

        fun drawText(
            text: String,
            line: Int
        ) {
            drawContext.canvas.nativeCanvas.apply {
                    drawText(
                        text,
                        size.width/2,
                        30.0f + 30.0f*line,
                        Paint().apply {
                            textSize = 25.0f
                            color = 0xFFEEEEEE.toInt()
                            textAlign = Paint.Align.CENTER


                        }
                    )
             }
        }
/*
        val angle = getAngle(rightShoulder,rightElbow,rightWrist)

        if(angle > 160)
            Constants.STAGE = "down"

        if(angle < 30 &&   Constants.STAGE ==  "down") {
            Constants.STAGE = "up"
            Constants.COUNTER++
        }*/
        val angle24_26_28 = getAngle(rightHip, rightKnee, rightAnkle)
        val yRightHand = rightWrist!!.position.y - rightShoulder!!.position.y
        val yLeftHand= leftWrist!!.position.y - leftShoulder!!.position.y
        val angle23_25_27 = getAngle(leftHip, leftKnee, leftAnkle)

        if(angle24_26_28 < 170 && Constants.STAGE !="Nice!") {
            Constants.STAGE = "Stand up"
        }
        else if(yLeftHand >0 || yRightHand > 0){
            Constants.STAGE = "Put your hands behind your head"
        }
       else {
           Constants.STAGE = "Nice!"
            if(angle23_25_27 < 120 && angle23_25_27 < 120 ) {
                Constants.COUNTER++
                Constants.STAGE = "Nice!"
            }
        }

       // Log.i("Angle",angle.toString())
        //drawText(Constants.STAGE,1)
       // drawText("Reps:"+ Constants.COUNTER.toString(),1)
       // drawText("Text",2)
        drawText(Constants.STAGE,1)
        drawText("Reps:" +Constants.COUNTER.toString(),2)
        drawText(angle23_25_27.toString(),3)
        reInitParams()
        // Face
        drawLine(nose, leftEyeInner, whitePaint)
        drawLine( leftEyeInner, leftEye, whitePaint)
        drawLine( leftEye, leftEyeOuter, whitePaint)
        drawLine(leftEyeOuter, leftEar, whitePaint)
        drawLine( nose, rightEyeInner, whitePaint)
        drawLine( rightEyeInner, rightEye, whitePaint)
        drawLine( rightEye, rightEyeOuter, whitePaint)
        drawLine(rightEyeOuter, rightEar, whitePaint)
        drawLine( leftMouth, rightMouth, whitePaint)

        drawLine(leftShoulder, rightShoulder, whitePaint)
        drawLine(leftHip, rightHip, whitePaint)
        // Left body
        drawLine(leftShoulder, leftElbow, leftPaint)
        drawLine(leftElbow, leftWrist, leftPaint)
        drawLine(leftShoulder, leftHip, leftPaint)
        drawLine(leftHip, leftKnee, leftPaint)
        drawLine(leftKnee, leftAnkle, leftPaint)
        drawLine(leftWrist, leftThumb, leftPaint)
        drawLine(leftWrist, leftPinky, leftPaint)
        drawLine(leftWrist, leftIndex, leftPaint)
        drawLine(leftIndex, leftPinky, leftPaint)
        drawLine(leftAnkle, leftHeel, leftPaint)
        drawLine(leftHeel, leftFootIndex, leftPaint)
        // Right body
        drawLine(rightShoulder, rightElbow, rightPaint)
        drawLine(rightElbow, rightWrist, rightPaint)
        drawLine(rightShoulder, rightHip, rightPaint)
        drawLine(rightHip, rightKnee, rightPaint)
        drawLine(rightKnee, rightAnkle, rightPaint)
        drawLine(rightWrist, rightThumb, rightPaint)
        drawLine(rightWrist, rightPinky, rightPaint)
        drawLine(rightWrist, rightIndex, rightPaint)
        drawLine(rightIndex, rightPinky, rightPaint)
        drawLine(rightAnkle, rightHeel, rightPaint)
        drawLine(rightHeel, rightFootIndex, rightPaint)

        if(showInFrameLikelihood) {
            for(landmark in landmarks) {
                drawContext.canvas.nativeCanvas.apply {
                    drawText(
                        String.format(Locale.US,"%.2f",landmark.inFrameLikelihood),
                        translate(landmark),
                        landmark.position.y,
                        Paint().apply {
                            textSize = 10.0f
                            color = 0xFFEEEEEE.toInt()
                        }
                    )
                }
            }
        }
    }
}
fun reInitParams(){
 Constants.STAGE = ""
}

fun getAngle(firstPoint: PoseLandmark?, midPoint: PoseLandmark?, lastPoint: PoseLandmark?): Double {

    var angle = Math.toDegrees(atan2(1.0* lastPoint!!.position.y - midPoint!!.position.y,
               1.0*lastPoint.position.x - midPoint.position.x)
                - atan2(firstPoint!!.position.y - midPoint.position.y,
                firstPoint.position.x - midPoint.position.x))
    angle = Math.abs(angle) // Angle should never be negative
    if(angle > 180)
        angle = 360.0 - angle // Always get the acute representation of the angle

    return  angle
}



