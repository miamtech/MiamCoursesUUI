package tech.miam.coursesuui.component


import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp

@Composable
fun CounterButton(
    icon: ImageVector,
    enable: Boolean,
    color: Color,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    IconButton(
        onClick = {
            onClick()
        },
        enabled = enable
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .background(color = Color.White, shape = CircleShape)
                .border(width = 2.dp, color = color, shape = CircleShape)
        ) {
            Icon(
                icon,
                modifier= modifier,
                contentDescription = "",
                tint = color
            )
        }
    }
}