package com.example.myapp.presentation.pose_detector

import com.example.myapp.Constants
import com.example.myapp.R
import com.example.myapp.presentation.UiText


fun squatsClassification(yRightHand: Float, yLeftHand: Float, shoulderDistance: Float, footDistance: Float, angle23_25_27: Double, angle24_26_28: Double ) {
        val ratio = footDistance/shoulderDistance

        if(angle24_26_28 < 170 && yLeftHand > 0 ) {
            reInitParams()
            Constants.text = UiText.StringResource(R.string.stand_up)
        }else if(yLeftHand >0 || yRightHand > 0){
            reInitParams()
            Constants.text = UiText.StringResource(R.string.hands_behind_head)
        }else if(ratio < 0.5){
            reInitParams()
            Constants.text = UiText.StringResource(R.string.spread_your_feet)
        }
        else if(angle24_26_28 > 170 ) {
            Constants.text = UiText.StringResource(R.string.go_down)
            Constants.stage = "down"
        }else {
            if(angle24_26_28 < 110 && angle23_25_27 < 110 && Constants.stage == "down" ) {
                Constants.text = UiText.StringResource(R.string.nice)
                Constants.stage = "up"
                Constants.squatsCounter++
            }
        }
    }

    fun dumbbellClassification(angle12_14_16: Double, angle24_26_28: Double) {
        if(angle24_26_28 < 170) {
            reInitParams()
            Constants.text = UiText.StringResource(R.string.stand_up)
        }
        else  if(angle12_14_16 > 160) {
            Constants.stage = "down"
            Constants.text = UiText.StringResource(R.string.more_wrist_to_arm)
        }

        else  if(angle12_14_16 < 30 &&   Constants.stage ==  "down") {
            Constants.stage= "up"
            Constants.text = UiText.StringResource(R.string.stretch_arm)
            Constants.dumbbellCounter++
        }
    }

    fun shoulderClassification(yRightHand: Float, angle12_14_16: Double, angle23_25_27:Double) {
        if(angle23_25_27 < 170) {
            reInitParams()
            Constants.text = UiText.StringResource(R.string.stand_up)
        }

        else if(yRightHand>0 && Constants.stage != "down") {
            Constants.text = UiText.StringResource(R.string.hold_right_arm_up)
        }

        else{
            if(angle12_14_16 > 150 && yRightHand<0) {
                Constants.text = UiText.StringResource(R.string.nice)
                Constants.stage = "down"
            }
            if(angle12_14_16 > 170 && Constants.stage == "down" && yRightHand>0) {
                Constants.stage = "up"
                Constants.shoulderCounter++
            }
        }
    }

    fun armClassification(yRightHand: Float, yLeftHand: Float, angle23_25_27:Double, angle12_14_16: Double, angle11_13_15: Double) {
        if(angle23_25_27 > 160) {
            reInitParams()
            Constants.text = UiText.StringResource(R.string.sit_down)
        }
        else if((yRightHand >0 || yLeftHand >0) && Constants.stage != "down") {
            Constants.text = UiText.StringResource(R.string.stretch_arms_above_head)
            Constants.isCount = false
        }
        else if((angle12_14_16 < 150 || angle11_13_15 < 150) && Constants.stage != "down")
            Constants.text = UiText.StringResource(R.string.stretch_hands_more)
        else if(angle12_14_16 >=150 && !Constants.isCount ) {
            Constants.isCount = true
            Constants.stage = "down"
            Constants.text = UiText.StringResource(R.string.nice)
        }
        else{
            if(angle12_14_16 > 170 && Constants.stage == "down") {
                Constants.stage = "up"
                Constants.armCounter++
            }
        }
    }

    fun legClassification(angle23_25_27: Double, angle24_26_28: Double, angle12_24_26:Double) {
        if(angle23_25_27 < 160) {
            reInitParams()
            Constants.text = UiText.StringResource(R.string.stand_up)
        }
        else if(angle12_24_26 > 160 && angle24_26_28 > 150){
            Constants.stage = "up"
            Constants.text = UiText.StringResource(R.string.lift_right_leg)
        }
        else {
            if(angle12_24_26 < 140 && Constants.stage == "up" && angle24_26_28 > 150){
                Constants.stage = "down"
                Constants.text = UiText.StringResource(R.string.nice)
                Constants.legCounter++
            }
            else if(angle24_26_28 < 150 && Constants.stage == "up") {
                Constants.text = UiText.StringResource(R.string.strech_leg_more)
            }
        }
    }
