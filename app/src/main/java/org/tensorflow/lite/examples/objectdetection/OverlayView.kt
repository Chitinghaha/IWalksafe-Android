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

import android.content.Context
import android.graphics.*
import android.media.MediaPlayer
import android.speech.tts.TextToSpeech
import android.util.AttributeSet
import android.view.View
import androidx.core.content.ContextCompat
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
    private var sound_check = "0"


    private var bounds = Rect()
    private var mp: MediaPlayer? = null
    private var mp1: MediaPlayer? = null
    private var mp2: MediaPlayer? = null
    private var mp3: MediaPlayer? = null
    private var mp4: MediaPlayer? = null
    private var mp5: MediaPlayer? = null
    lateinit var tts:TextToSpeech

    init {
        mp = MediaPlayer.create( getContext(), R.raw.mouse)
        mp1 = MediaPlayer.create( getContext(), R.raw.keyboard)
        mp2 = MediaPlayer.create( getContext(), R.raw.cell_phone)
        mp3 = MediaPlayer.create( getContext(), R.raw.cup)
        mp4 = MediaPlayer.create( getContext(), R.raw.person)
        mp5 = MediaPlayer.create( getContext(), R.raw.tv)

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

            //sound_output(result.categories[0].label)


            if( result.categories[0].label == "mouse" && sound_check == "Sound_Open" )
            {
                mp?.start()
            }

            else if(result.categories[0].label == "keyboard" && sound_check == "Sound_Open")
            {
                mp1?.start()
            }
            else if(result.categories[0].label == "cell phone" && sound_check == "Sound_Open")
            {
                mp2?.start()
            }
            else if(result.categories[0].label == "cup" && sound_check == "Sound_Open")
            {
                mp3?.start()
            }
            else if(result.categories[0].label == "person" && sound_check == "Sound_Open")
            {
                mp4?.start()
            }
            else if(result.categories[0].label == "tv" && sound_check == "Sound_Open")
            {
                mp5?.start()
            }

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
        }
    }

    fun sound_output(Text_print:String)
    {
        tts = TextToSpeech( getContext() ,TextToSpeech.OnInitListener {
            if(it == TextToSpeech.SUCCESS) {
                tts.language= Locale.US
                tts.setSpeechRate(1.0f)
                tts!!.speak(Text_print, TextToSpeech.QUEUE_FLUSH, null,"")
            }
        })
    }


    fun setResults(
        detectionResults: MutableList<Detection>,
        imageHeight: Int,
        imageWidth: Int,
        sound: String
    ) {
        sound_check=sound

        results = detectionResults

        // PreviewView is in FILL_START mode. So we need to scale up the bounding box to match with
        // the size that the captured images will be displayed.
        scaleFactor = max(width * 1f / imageWidth, height * 1f / imageHeight)

    }

    companion object {
        private const val BOUNDING_RECT_TEXT_PADDING = 8
    }

}
