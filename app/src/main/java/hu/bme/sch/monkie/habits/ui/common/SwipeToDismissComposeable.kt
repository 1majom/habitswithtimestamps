package hu.bme.sch.monkie.habits.ui.common

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SwipeToDismiss
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.Text
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import hu.bme.sch.monkie.habits.ui.theme.AppTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SwipeListItem(
    modifier: Modifier = Modifier,
    animationId: String? = null,
    onSwipedToStart: (() -> Unit)? = null,
    swipedToStartColor: Color = MaterialTheme.colorScheme.background,
    swipedToStartIcon: ImageVector? = null,
    onSwipedToEnd: (() -> Unit)? = null,
    swipedToEndColor: Color = MaterialTheme.colorScheme.background,
    swipedToEndIcon: ImageVector? = null,
    swipeBackgroundColor: Color = MaterialTheme.colorScheme.background,
    headlineContent: @Composable () -> Unit,
    overlineContent: @Composable (() -> Unit)? = null,
    trailingContent: @Composable (() -> Unit)? = null,
    supportingContent: @Composable (() -> Unit)? = null,
    leadingContent: @Composable (() -> Unit)? = null,
) {
    val dismissState = rememberSwipeToDismissBoxState()
    dismissState.currentValue

    val direction = mutableSetOf<SwipeToDismissBoxValue>()

    if (onSwipedToStart != null) {
        direction.add(SwipeToDismissBoxValue.EndToStart)
    }
    if (onSwipedToEnd != null) {
        direction.add(SwipeToDismissBoxValue.StartToEnd)
    }

    LaunchedEffect(dismissState.currentValue) {
        if (dismissState.currentValue == SwipeToDismissBoxValue.EndToStart) {
            dismissState.snapTo(SwipeToDismissBoxValue.Settled)
        } else if (dismissState.currentValue == SwipeToDismissBoxValue.StartToEnd) {
            dismissState.snapTo(SwipeToDismissBoxValue.Settled)
        }
    }

    SwipeToDismiss(
        modifier = modifier,
        state = dismissState,
        background = {
            if (onSwipedToStart != null
                && dismissState.currentValue == SwipeToDismissBoxValue.EndToStart
            ) {
                onSwipedToStart()
            } else if (onSwipedToEnd != null
                && dismissState.currentValue == SwipeToDismissBoxValue.StartToEnd
            ) {
                onSwipedToEnd()
            }

            val backgroundColor by animateColorAsState(
                when (dismissState.targetValue) {
                    SwipeToDismissBoxValue.StartToEnd -> swipedToEndColor
                    SwipeToDismissBoxValue.EndToStart -> swipedToStartColor
                    SwipeToDismissBoxValue.Settled -> swipeBackgroundColor
                }, label = "SwipeBackgroundAnimation$animationId"
            )

            val iconScale by animateFloatAsState(
                targetValue = if (dismissState.targetValue == SwipeToDismissBoxValue.Settled) 0.5f else 1.3f,
                label = "SwipeIconScaleAnimation$animationId"
            )

            val iconImageVector = when (dismissState.targetValue) {
                SwipeToDismissBoxValue.StartToEnd -> swipedToEndIcon
                SwipeToDismissBoxValue.EndToStart -> swipedToStartIcon
                SwipeToDismissBoxValue.Settled -> null
            }

            val iconAlignment = when (dismissState.targetValue) {
                SwipeToDismissBoxValue.StartToEnd -> Alignment.CenterStart
                SwipeToDismissBoxValue.EndToStart -> Alignment.CenterEnd
                SwipeToDismissBoxValue.Settled -> null
            }


            if (iconImageVector != null && iconAlignment != null) {
                Box(
                    Modifier
                        .fillMaxSize()
                        .background(color = backgroundColor)
                        .padding(start = 16.dp, end = 16.dp), // inner padding
                    contentAlignment = iconAlignment
                ) {
                    Icon(
                        modifier = Modifier.scale(iconScale),
                        imageVector = iconImageVector,
                        contentDescription = null,
                        tint = Color.White
                    )
                }
            }
        },
        dismissContent = {
            ListItem(
                modifier = Modifier,
                leadingContent = leadingContent,
                headlineContent = headlineContent,
                supportingContent = supportingContent,
                overlineContent = overlineContent,
                trailingContent = trailingContent
            )
        },
        directions = direction,
    )
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ClickableSwipeListItem(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    onLongClick: () -> Unit,
    animationId: String? = null,
    onSwipedToStart: (() -> Unit)? = null,
    swipedToStartColor: Color = MaterialTheme.colorScheme.background,
    swipedToStartIcon: ImageVector? = null,
    onSwipedToEnd: (() -> Unit)? = null,
    swipedToEndColor: Color = MaterialTheme.colorScheme.background,
    swipedToEndIcon: ImageVector? = null,
    swipeBackgroundColor: Color = MaterialTheme.colorScheme.background,
    headlineContent: @Composable () -> Unit,
    overlineContent: @Composable (() -> Unit)? = null,
    trailingContent: @Composable (() -> Unit)? = null,
    supportingContent: @Composable (() -> Unit)? = null,
    leadingContent: @Composable (() -> Unit)? = null,
) {
    SwipeListItem(
        modifier.combinedClickable (
            onClick = onClick,
            onLongClick = onLongClick
        ),
        animationId,
        onSwipedToStart,
        swipedToStartColor,
        swipedToStartIcon,
        onSwipedToEnd,
        swipedToEndColor,
        swipedToEndIcon,
        swipeBackgroundColor,
        headlineContent,
        overlineContent,
        trailingContent,
        supportingContent,
        leadingContent
    )
}

@Preview(uiMode = UI_MODE_NIGHT_YES)
@Composable
fun SwipeListItemPreview() {
    AppTheme {
        Scaffold {
            Column(modifier = Modifier.padding(it)) {
                ClickableSwipeListItem(
                    onClick = {},
                    onLongClick = {},
                    headlineContent = { Text(text = "Preview") },
                    onSwipedToEnd = {},
                    onSwipedToStart = {},
                )
            }
        }
    }
}
