/*
 * Copyright 2022 The TensorFlow Authors. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.tensorflow.lite.examples.objectdetection

import android.app.Service
import android.content.Context
import android.graphics.*
import android.media.MediaPlayer
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import android.speech.tts.TextToSpeech
import android.util.AttributeSet
import android.view.View
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.getSystemService
import org.tensorflow.lite.task.vision.detector.Detection
import java.util.*
import kotlin.math.max

// 框框和 text
class OverlayView(context: Context?, attrs: AttributeSet?) : View(context, attrs) {

    private var results: List<Detection> = LinkedList<Detection>()
    private var boxPaint = Paint()
    private var textBackgroundPaint = Paint()
    private var textPaint = Paint()

    private var scaleFactor: Float = 1f
    private var beep1: MediaPlayer? = null
    private var beep2: MediaPlayer? = null
    private var beep3: MediaPlayer? = null





    private var bounds = Rect()

//    lateinit var tts:TextToSpeech

    init {
        initPaints()
    }

    fun clear() {
        textPaint.reset()
        textBackgroundPaint.reset()
        boxPaint.reset()
        invalidate()
        initPaints()
    }


    private fun initPaints() {
        textBackgroundPaint.color = Color.BLACK
        textBackgroundPaint.style = Paint.Style.FILL
        textBackgroundPaint.textSize = 50f

        textPaint.color = Color.WHITE
        textPaint.style = Paint.Style.FILL
        textPaint.textSize = 50f

        boxPaint.color = ContextCompat.getColor(context!!, R.color.bounding_box_color)
        boxPaint.strokeWidth = 8F
        boxPaint.style = Paint.Style.STROKE

        beep1 = MediaPlayer.create( getContext(), R.raw.beep1)
        beep2 = MediaPlayer.create( getContext(), R.raw.beep2)
        beep3 = MediaPlayer.create( getContext(), R.raw.beep4)


    }


    override fun draw(canvas: Canvas) {
        super.draw(canvas)

        for (result in results) {
            val boundingBox = result.boundingBox

            val top = boundingBox.top * scaleFactor
            val bottom = boundingBox.bottom * scaleFactor
            val left = boundingBox.left * scaleFactor
            val right = boundingBox.right * scaleFactor

            // Draw bounding box around detected objects
            val drawableRect = RectF(left, top, right, bottom)
            canvas.drawRect(drawableRect, boxPaint)

            // Create text to display alongside detected objects
            val drawableText =
                result.categories[0].label + " " +
                        String.format("%.2f", result.categories[0].score)


            // Draw rect behind display text
            textBackgroundPaint.getTextBounds(drawableText, 0, drawableText.length, bounds)
            val textWidth = bounds.width()
            val textHeight = bounds.height()
            canvas.drawRect(
                left,
                top,
                left + textWidth + Companion.BOUNDING_RECT_TEXT_PADDING,
                top + textHeight + Companion.BOUNDING_RECT_TEXT_PADDING,
                textBackgroundPaint
            )

            // Draw text for detected object
            canvas.drawText(drawableText, left, top + bounds.height(), textPaint)


            if(result.categories[0].label == "person" ) {
                val area = (left - right) * ( top - bottom )
                val barea = area.toString()
                canvas.drawText(barea, left+500, top + bounds.height(), textPaint)
                if(area >= 2000000){
                    beep1?.start()
                }
                else if(area >= 1000000){
                    beep2?.start()
                }
                else if(area >= 500000){
                    beep3?.start()
                }
            }
        }
    }

//    fun sound_output(Text_print:String)
//    {
//        tts = TextToSpeech( getContext() ,TextToSpeech.OnInitListener {
//            if(it == TextToSpeech.SUCCESS) {
//                tts.language= Locale.US
//                tts.setSpeechRate(1.0f)
//                tts!!.speak(Text_print, TextToSpeech.QUEUE_FLUSH, null,"")
//            }
//        })
//    }


    fun setResults(
        detectionResults: MutableList<Detection>,
        imageHeight: Int,
        imageWidth: Int,
    ) {

        results = detectionResults

        // PreviewView is in FILL_START mode. So we need to scale up the bounding box to match with
        // the size that the captured images will be displayed.
        scaleFactor = max(width * 1f / imageWidth, height * 1f / imageHeight)

    }

    companion object {
        private const val BOUNDING_RECT_TEXT_PADDING = 8
    }

}
