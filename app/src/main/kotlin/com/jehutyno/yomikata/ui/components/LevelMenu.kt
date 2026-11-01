package com.jehutyno.yomikata.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.jehutyno.yomikata.R
import com.jehutyno.yomikata.ui.theme.BorderDefault
import com.jehutyno.yomikata.ui.theme.RadiusMd
import com.jehutyno.yomikata.ui.theme.SurfacePrimary
import com.jehutyno.yomikata.ui.theme.TextMuted
import com.jehutyno.yomikata.ui.theme.TextPrimary
import com.jehutyno.yomikata.ui.theme.YomikataTheme
import com.jehutyno.yomikata.ui.word.levelColor
import com.jehutyno.yomikata.util.quiz.Level
import kotlin.Unit


fun Level.masteryLabelRes(): Int = when (this) {
    Level.LOW -> R.string.red_review
    Level.MEDIUM -> R.string.orange_review
    Level.HIGH -> R.string.yellow_review
    Level.MASTER -> R.string.green_review
}

/**
 * Reusable dropdown menu component with progress icon and options
 * to inspect or change a word's mastery level.
 */
@Composable
fun LevelMenu(
    currentLevel: Level?,
    onLevelSelected: (Level) -> Unit,
    modifier: Modifier = Modifier,
    iconTint: Color = TextMuted,
    buttonSize: Dp = 36.dp,
    iconSize: Dp = 18.dp,
    initiallyExpanded: Boolean = false,
) {
    var isExpanded by remember { mutableStateOf(initiallyExpanded) }

    Box(modifier = modifier) {
        IconButton(
            onClick = { isExpanded = true },
            modifier = Modifier.size(buttonSize),
        ) {
            Icon(
                painter = painterResource(R.drawable.ic_progress),
                contentDescription = stringResource(R.string.tuto_progress),
                tint = iconTint,
                modifier = Modifier.size(iconSize),
            )
        }
        if (isExpanded) {
            if (LocalInspectionMode.current) {
                Surface(
                    modifier = Modifier.padding(top = buttonSize),
                    shape = RoundedCornerShape(RadiusMd),
                    color = SurfacePrimary,
                    border = BorderStroke(1.dp, BorderDefault),
                ) {
                    Column(modifier = Modifier.padding(4.dp)) {
                        LevelMenuContent(currentLevel, onLevelSelected) { isExpanded = false }
                    }
                }
            } else {
                DropdownMenu(
                    expanded = isExpanded,
                    onDismissRequest = { isExpanded = false },
                ) {
                    LevelMenuContent(currentLevel, onLevelSelected) { isExpanded = false }
                }
            }
        }
    }
}

@Composable
private fun LevelMenuContent(
    currentLevel: Level?,
    onLevelSelected: (Level) -> Unit,
    onDismissRequest: () -> Unit,
) {
    Level.entries.forEach { level ->
        DropdownMenuItem(
            text = {
                Text(
                    text = stringResource(level.masteryLabelRes()),
                    color = TextPrimary,
                )
            },
            leadingIcon = {
                RadioButton(
                    selected = currentLevel == level,
                    onClick = null,
                    colors = RadioButtonDefaults.colors(
                        selectedColor = levelColor(level),
                        unselectedColor = TextMuted,
                    ),
                )
            },
            onClick = {
                onLevelSelected(level)
                onDismissRequest()
            },
        )
    }
}

@Preview(name = "LevelMenu — LOW", showBackground = true, backgroundColor = 0xFF0A0E17, widthDp = 300, heightDp=400)
@Composable
private fun LevelMenuLowPreview() {
    YomikataTheme {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
        ) {
            LevelMenu(currentLevel = Level.LOW, onLevelSelected = {}, initiallyExpanded = true)
        }
    }
}

@Preview(name = "LevelMenu — HIGH", showBackground = true, backgroundColor = 0xFF0A0E17, widthDp = 300, heightDp=400)
@Composable
private fun LevelMenuHighPreview() {
    YomikataTheme {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
        ) {
            LevelMenu(currentLevel = Level.HIGH, onLevelSelected = {}, initiallyExpanded = true)
        }
    }
}
