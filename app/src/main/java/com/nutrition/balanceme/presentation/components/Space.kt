package com.nutrition.balanceme.presentation.components

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun TinySpace() = Spacer(modifier = Modifier.size(5.dp))

@Composable
fun SmallSpace() = Spacer(modifier = Modifier.size(20.dp))

@Composable
fun MediumSpace() = Spacer(modifier = Modifier.size(30.dp))