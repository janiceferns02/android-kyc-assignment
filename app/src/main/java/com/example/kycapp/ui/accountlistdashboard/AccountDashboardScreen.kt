package com.example.kycapp.ui.accountlistdashboard

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.example.kycapp.domain.DashboardUiState
import com.example.kycapp.domain.KycStatus
import com.example.kycapp.domain.User

@Composable
fun AccountDashboardScreen(
    viewModel: DashboardViewmodel,
    onDoKycClick: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val selectedTab by viewModel.selectedTab.collectAsStateWithLifecycle()

    Scaffold(
        bottomBar = {
            DashboardBottomTabs(
                selectedTab = selectedTab,
                onTabSelected = { viewModel.onTabChanged(it) }
            )
        },
        modifier = modifier
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 16.dp)
        ) {
            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "Digital Bank",
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
            )
            Text(
                text = if (selectedTab == KycStatus.PENDING) "Pending KYC" else "Verified Accounts",
                fontSize = 14.sp,
                modifier = Modifier.padding(top = 4.dp)
            )

            Spacer(modifier = Modifier.height(20.dp))

            when (val state = uiState) {
                is DashboardUiState.Loading -> {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        CircularProgressIndicator()
                    }
                }
                is DashboardUiState.Error -> {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Text(text = state.message, color = MaterialTheme.colorScheme.error, textAlign = TextAlign.Center)
                    }
                }
                is DashboardUiState.Empty -> {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Text(
                            text = "No accounts found",
                            color = Color.Gray,
                            textAlign = TextAlign.Center
                        )
                    }
                }
                is DashboardUiState.Success -> {
                    LazyVerticalGrid(
                        columns = GridCells.Fixed(2),
                        horizontalArrangement = Arrangement.spacedBy(16.dp),
                        verticalArrangement = Arrangement.spacedBy(16.dp),
                        modifier = Modifier.weight(1f)
                    ) {
                        items(state.users, key = { it.id }) { user ->
                            UserCardItem(
                                user = user,
                                onDoKycClick = onDoKycClick
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun DashboardBottomTabs(
    selectedTab: KycStatus,
    onTabSelected: (KycStatus) -> Unit
) {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        color = Color.White,
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .windowInsetsPadding(WindowInsets.navigationBars)
                .height(64.dp)
        ) {
            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight()
                    .clickable { onTabSelected(KycStatus.VERIFIED) },
                contentAlignment = Alignment.Center
            ) {

                if (selectedTab == KycStatus.VERIFIED) {

                    Box(
                        modifier = Modifier
                            .size(35.dp, 2.dp)
                            .background(Color(0xFF2555D3))
                            .align(Alignment.TopCenter)
                    )
                }

                Column(horizontalAlignment = Alignment.CenterHorizontally) {

                    Text(
                        text = "VERIFIED",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        color = if (selectedTab == KycStatus.VERIFIED) Color(0xFF2555D3) else Color.Gray
                    )
                }
            }

            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight()
                    .clickable { onTabSelected(KycStatus.PENDING) },
                contentAlignment = Alignment.Center
            ) {

                if (selectedTab == KycStatus.PENDING) {
                    Box(
                        modifier = Modifier
                            .size(35.dp, 2.dp)
                            .background(Color(0xFF2555D3))
                            .align(Alignment.TopCenter)
                    )
                }

                Column(horizontalAlignment = Alignment.CenterHorizontally) {

                    Text(
                        text = "PENDING",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        color = if (selectedTab == KycStatus.PENDING) Color(0xFF2555D3) else Color.Gray
                    )
                }
            }
        }
    }
}

@Composable
fun UserCardItem(
    user: User,
    onDoKycClick: (Int) -> Unit,
    modifier: Modifier = Modifier
){
    OutlinedCard(
        modifier = modifier
            .fillMaxWidth(),
        shape = RoundedCornerShape(24.dp),
    ) {
        Column(
            modifier = modifier
                .fillMaxWidth()
                .padding(16.dp),
        ) {
            Row(
                modifier = modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                AsyncImage(
                    model = user.profileImage,
                    contentDescription = "",
                    modifier = Modifier
                        .size(56.dp)
                        .clip(CircleShape)
                )

                KycStatusBadge(status = user.kycStatus)
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = user.fullName,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
            )

            val maskedAccount = if (user.accountNumber.length >= 4) {
                "**** ${user.accountNumber.takeLast(4)}"
            } else {
                "**** ${user.accountNumber}"
            }

            Text(
                text = maskedAccount,
                fontSize = 14.sp,
                modifier = Modifier.padding(top = 4.dp),
                color = Color.LightGray
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Rs ${user.accountBalance}",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF1A1A1A)
            )

            if (user.kycStatus == KycStatus.PENDING) {
                Spacer(modifier = Modifier.height(12.dp))

                Button(
                    onClick = { onDoKycClick(user.id) },
                    shape = RoundedCornerShape(50),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF2555D3)),
                    contentPadding = PaddingValues(horizontal = 18.dp, vertical = 4.dp),
                    modifier = Modifier.height(40.dp)
                ) {
                    Text(
                        text = "Do KYC",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                }
            }
        }
    }
}

@Composable
fun KycStatusBadge(status: KycStatus) {
    val backgroundColor = when (status) {
        KycStatus.PENDING -> Color.Yellow
        KycStatus.VERIFIED -> Color.Green
    }

    Box(
        modifier = Modifier
            .background(backgroundColor)
            .padding(horizontal = 7.dp, vertical = 4.dp)
    ) {
        Text(
            text = if(status == KycStatus.VERIFIED) "KYC ${status.name}" else status.name,
            fontSize = 12.sp,
            fontWeight = FontWeight.Bold,
        )
    }
}

@Preview
@Composable
fun KycStatusPreview() {
    KycStatusBadge(KycStatus.VERIFIED)
}

@Preview
@Composable
fun UserCardItemPreview() {
    UserCardItem(User(1,"John", "", "12-09-2009", "IN", "1827187218278", "", 12000, KycStatus.PENDING), onDoKycClick = {})
}

@Preview
@Composable
fun DashboardBottomTabsPreview() {
    DashboardBottomTabs(KycStatus.VERIFIED, {})
}