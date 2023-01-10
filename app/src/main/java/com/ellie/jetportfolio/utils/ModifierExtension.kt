package com.ellie.jetportfolio.utils

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.calculateTargetValue
import androidx.compose.animation.splineBasedDecay
import androidx.compose.foundation.gestures.awaitFirstDown
import androidx.compose.foundation.gestures.horizontalDrag
import androidx.compose.foundation.layout.offset
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.input.pointer.positionChange
import androidx.compose.ui.input.pointer.util.VelocityTracker
import androidx.compose.ui.unit.IntOffset
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlin.math.absoluteValue
import kotlin.math.roundToInt

/**
 * The modified element can be horizontally swiped away.
 *
 * @param onDismissed Called when the element is swiped to the edge of the screen.
 */
fun Modifier.swipeToDismiss(
    onDismissed: () -> Unit
): Modifier = composed {
    // DONE 6-1: Create an Animatable instance for the offset of the swiped element.
    val offsetX = remember { Animatable(0f) } // Add this line
    pointerInput(Unit) {
        // Used to calculate a settling position of a fling animation.
        val decay = splineBasedDecay<Float>(this)
        // Wrap in a coroutine scope to use suspend functions for touch events and animation.
        coroutineScope {
            while (true) {
                // Wait for a touch down event.
                val pointerId = awaitPointerEventScope { awaitFirstDown().id }
                // DONE 6-2: Touch detected; the animation should be stopped.
                offsetX.stop() // Add this line to cancel any on-going animations
                // Prepare for drag events and record velocity of a fling.
                val velocityTracker = VelocityTracker()
                // Wait for drag events.
                awaitPointerEventScope {
                    horizontalDrag(pointerId) { change ->
                        // DONE 6-3: Apply the drag change to the Animatable offset.
                        // Add these 4 lines
                        // Get the drag amount change to offset the item with
                        val horizontalDragOffset = offsetX.value + change.positionChange().x
                        // Need to call this in a launch block in order to run it separately outside of the awaitPointerEventScope
                        launch {
                            // Instantly set the Animable to the dragOffset to ensure its moving
                            // as the user's finger moves
                            offsetX.snapTo(horizontalDragOffset)
                        }
                        // Record the velocity of the drag.
                        velocityTracker.addPosition(change.uptimeMillis, change.position)
                        // Consume the gesture event, not passed to external
                        if (change.positionChange() != Offset.Zero) change.consume()
                    }
                }
                // Dragging finished. Calculate the velocity of the fling.
                val velocity = velocityTracker.calculateVelocity().x
                // DONE 6-4: Calculate the eventual position where the fling should settle
                //           based on the current offset value and velocity
                // Add this line to calculate where it would end up with
                // the current velocity and position
                val targetOffsetX = decay.calculateTargetValue(offsetX.value, velocity)
                // DONE 6-5: Set the upper and lower bounds so that the animation stops when it
                //           reaches the edge.
                offsetX.updateBounds(
                    lowerBound = -size.width.toFloat(), upperBound = size.width.toFloat()
                )
                launch {
                    // DONE 6-6: Slide back the element if the settling position does not go beyond
                    //           the size of the element. Remove the element if it does.
                    if (targetOffsetX.absoluteValue <= size.width) {
                        // Not enough velocity; Slide back.
                        offsetX.animateTo(targetValue = 0f, initialVelocity = velocity)
                    } else {
                        // Enough velocity to slide away the element to the edge.
                        offsetX.animateDecay(velocity, decay)
                        // The element was swiped away.
                        onDismissed()
                    }
                }
            }
        }
    }.offset {
        // DONE 6-7: Use the animating offset value here.
        IntOffset(offsetX.value.roundToInt(), 0)
    }
}