package com.ljp.wanandroid.extensions

import androidx.constraintlayout.motion.widget.MotionLayout
import com.ljp.wanandroid.utils.LOG


/*
 *@创建者       L_jp
 *@创建时间     2022/6/30 10:46.
 *@描述
 */

fun MotionLayout.setAnimStatusListener(l: MotionLayoutListener.() -> Unit) {
    val listener = MotionLayoutListener().also(l)
    setTransitionListener(object : MotionLayout.TransitionListener {
        override fun onTransitionStarted(motionLayout: MotionLayout?, startId: Int, endId: Int) {
            //有bug motionLayout?.currentState几次会会变成-1
            if (motionLayout?.currentState == startId) {
                listener.onEndAnimStart.invoke()
            } else if (motionLayout?.currentState == endId) {
                listener.onStartAnimStart.invoke()
            }
        }

        override fun onTransitionChange(motionLayout: MotionLayout?, startId: Int, endId: Int, progress: Float) {
            listener.onProgressListener.invoke(progress)
        }

        override fun onTransitionCompleted(motionLayout: MotionLayout?, currentId: Int) {
            LOG.d(currentId)
            if (motionLayout?.endState == currentId) {
                //从开始到结束的动画完成
                listener.onStartAnimCompleted.invoke()
            } else if (motionLayout?.startState == currentId) {
                //从结束到开始的动画完成
                listener.onEndAnimCompleted.invoke()
            }
        }

        override fun onTransitionTrigger(
            motionLayout: MotionLayout?,
            triggerId: Int,
            positive: Boolean,
            progress: Float
        ) {

        }

    })
}

class MotionLayoutListener {
    var onStartAnimStart: () -> Unit = {}
    var onStartAnimCompleted: () -> Unit = {}

    var onEndAnimStart: () -> Unit = {}
    var onEndAnimCompleted: () -> Unit = {}

    var onProgressListener: (Float) -> Unit = {}


}
