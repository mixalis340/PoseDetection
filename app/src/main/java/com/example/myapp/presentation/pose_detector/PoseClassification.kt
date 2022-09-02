package com.example.myapp.presentation.pose_detector

import com.example.myapp.Constants


    fun squatsClassification(yRightHand: Float, yLeftHand: Float, shoulderDistance: Float, footDistance: Float, angle23_25_27: Double, angle24_26_28: Double ) {
        val ratio = footDistance/shoulderDistance

        if(angle24_26_28 < 170 && yLeftHand > 0 ) {
            reInitParams()
            Constants.text = "Stand up"
        }else if(yLeftHand >0 || yRightHand > 0){
            reInitParams()
            Constants.text = "Put your hands behind your head"
        }else if(ratio < 0.5){
            reInitParams()
            Constants.text = "Spread your feet shoulder-width apart"
        }
        else if(angle24_26_28 > 170 ) {
            Constants.text = "Go down!"
            Constants.stage = "down"
        }else {
            if(angle24_26_28 < 110 && angle23_25_27 < 110 && Constants.stage == "down" ) {
                Constants.text = "Nice!"
                Constants.stage = "up"
                Constants.counter++
            }
        }
    }

    fun dumbbellClassification(angle12_14_16: Double, angle24_26_28: Double) {
        if(angle24_26_28 < 170) {
            reInitParams()
            Constants.text = "Stand up"
        }
        else  if(angle12_14_16 > 160) {
            Constants.stage = "down"
            Constants.text = "Move your wrist close to your arm"
        }

        else  if(angle12_14_16 < 30 &&   Constants.stage ==  "down") {
            Constants.stage= "up"
            Constants.text = "Stretch your arm"
            Constants.counter++
        }
    }

    fun shoulderClassification(yRightHand: Float, angle12_14_16: Double, angle23_25_27:Double) {
        if(angle23_25_27 < 170) {
            reInitParams()
            Constants.text = "Stand up!"
        }

        else if(yRightHand>0 && Constants.stage != "down") {
            Constants.text = "Hold your right arm in your right shoulder height"
        }

        else{
            if(angle12_14_16 > 150 && yRightHand<0) {
                Constants.text = "Nice!"
                Constants.stage = "down"
            }
            if(angle12_14_16 > 170 && Constants.stage == "down" && yRightHand>0) {
                Constants.stage = "up"
                Constants.counter++
            }
        }
    }

    fun armClassification(yRightHand: Float, yLeftHand: Float, angle23_25_27:Double, angle12_14_16: Double) {
        if(angle23_25_27 >160) {
            reInitParams()
            Constants.text = "Sit down!"
        }
        else if((yRightHand >0 || yLeftHand >0) && Constants.stage != "down") {
            Constants.text = "Stretch yours arms above your head!"
            Constants.isCount = false
        }
        else if(angle12_14_16 < 150 && Constants.stage != "down")
            Constants.text = "Stretch your hands more!"
        else if(angle12_14_16 >=150 && !Constants.isCount ) {
            Constants.isCount = true
            Constants.stage = "down"
            Constants.text = "Nice!"
        }
        else{
            if(angle12_14_16 > 170 && Constants.stage == "down") {
                Constants.stage = "up"
                Constants.counter++
            }
        }
    }

    fun legClassification(angle23_25_27: Double, angle24_26_28: Double, angle12_24_26:Double) {
        if(angle23_25_27 < 160) {
            reInitParams()
            Constants.text = "Stand up!"
        }
        else if(angle12_24_26 > 160 && angle24_26_28 > 150){
            Constants.stage = "up"
            Constants.text = "Lift your right leg"
        }
        else {
            if(angle12_24_26 < 140 && Constants.stage == "up" && angle24_26_28 > 150){
                Constants.stage = "down"
                Constants.text = "Nice!"
                Constants.counter++
            }
            else if(angle24_26_28 < 150 && Constants.stage == "up") {
                Constants.text = "Stretch your leg more!"
            }
        }
    }
