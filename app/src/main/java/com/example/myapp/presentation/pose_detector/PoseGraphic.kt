package com.example.myapp.presentation.pose_detector

import android.graphics.Paint
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.*
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
    showInFrameLikelihood: Boolean,
    exerciseName: String?
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

            val startX = translate(startLandmark)
            val startY = startLandmark.position.y
            val endX = translate(endLandmark)
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
        if(exerciseName == "Squats") {
            val yRightHand = rightWrist!!.position.y - rightShoulder!!.position.y
            val yLeftHand= leftWrist!!.position.y - leftShoulder!!.position.y
            val shoulderDistance = leftShoulder.position.x - rightShoulder.position.x
            val footDistance = leftAnkle!!.position.x - rightAnkle!!.position.x
            val angle23_25_27 = getAngle(leftHip, leftKnee, leftAnkle)
            val angle24_26_28 = getAngle(rightHip, rightKnee, rightAnkle)

            squatsClassification(yRightHand, yLeftHand, shoulderDistance, footDistance, angle23_25_27, angle24_26_28)
        }
        if(exerciseName == "Dumbbell") {
            val angle12_14_16 = getAngle(rightShoulder,rightElbow,rightWrist)
            val angle24_26_28 = getAngle(rightHip, rightKnee, rightAnkle)

            dumbbellClassification(angle12_14_16, angle24_26_28)
        }

        if(exerciseName == "Shoulder"){
            val angle12_14_16 = getAngle(rightShoulder, rightElbow, rightWrist)
            val angle23_25_27 = getAngle(leftHip, leftKnee, leftAnkle)
            val yRightHand = rightWrist!!.position.y - rightShoulder!!.position.y

            shoulderClassification(yRightHand, angle12_14_16, angle23_25_27)
        }

        if(exerciseName == "Arm"){
            val yRightHand = rightWrist!!.position.y - rightShoulder!!.position.y
            val yLeftHand = leftWrist!!.position.y - leftShoulder!!.position.y
            val angle23_25_27 = getAngle(leftHip, leftKnee, leftAnkle)
            val angle12_14_16 = getAngle(rightShoulder, rightElbow, rightWrist)

            armClassification(yRightHand, yLeftHand, angle23_25_27, angle12_14_16)

        }

        if(exerciseName == "Leg"){
            val angle23_25_27 = getAngle(leftHip, leftKnee, leftAnkle)
            val angle24_26_28 = getAngle(rightHip, rightKnee, rightAnkle)
            val angle12_24_26 = getAngle(rightShoulder, rightHip, rightKnee)

            legClassification(angle23_25_27, angle24_26_28, angle12_24_26)
        }

        drawText(Constants.text,1)
        drawText("Count:" +Constants.counter.toString(),2)

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
 Constants.text = ""
 Constants.stage = "none"
 Constants.counter = 0
 Constants.isCount = false
}




