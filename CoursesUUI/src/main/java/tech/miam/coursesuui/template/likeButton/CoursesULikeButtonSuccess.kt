package tech.miam.coursesuui.template.likeButton

import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.miam.kmm_miam_sdk.android.ressource.Image
import com.miam.sdk.components.baseComponent.likeButton.success.LikeButtonSuccess
import com.miam.sdk.components.baseComponent.likeButton.success.LikeButtonSuccessParameters

class CoursesULikeButtonSuccess: LikeButtonSuccess {

    @Composable
    override fun Content(param: LikeButtonSuccessParameters) {
        val isLiked by remember { mutableStateOf(param.isLiked) }
        Box {
            Surface(
                shape = CircleShape,
                color =  Color.Transparent ,
                modifier = Modifier
                    .align(Alignment.Center)
                    .size(40.dp)
                    .clickable { param.likeAction() }
            ) {}
            Crossfade(modifier = Modifier.align(Alignment.Center),targetState = isLiked, animationSpec = tween(750, 0), label = "Fading 750ms") { currentIsLiked ->
                if (currentIsLiked) {
                    Image(
                        painter = painterResource(Image.favoriteFilled),
                        contentDescription = "favorite filled",
                        modifier = Modifier.size(20.dp)
                    )
                } else {
                    Image(
                        painter = painterResource(Image.favorite),
                        contentDescription = "favorite unfilled",
                        modifier = Modifier.size(20.dp)
                                .padding(start = 2.dp),
                        )
                }
            }
        }
    }
}